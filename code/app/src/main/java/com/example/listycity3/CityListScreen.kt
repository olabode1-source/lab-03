package com.example.listycity3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme

@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    onModifyCity: (Int, City) -> Unit,
    modifier: Modifier = Modifier
) {
    var cityNameInput by remember { mutableStateOf("") }
    var provinceNameInput by remember { mutableStateOf("") }
    var showInputFields by remember { mutableStateOf(false) }
    var selectedCityIndex by remember { mutableStateOf<Int?>(null) }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    selectedCityIndex = null
                    cityNameInput = ""
                    provinceNameInput = ""
                    showInputFields = !showInputFields
                }
            ) {
                Text(if (showInputFields && selectedCityIndex == null) "X" else "+")
            }
        }

        if (showInputFields) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = cityNameInput,
                    onValueChange = { cityNameInput = it },
                    label = { Text("City") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = provinceNameInput,
                    onValueChange = { provinceNameInput = it },
                    label = { Text("Province") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            if (cityNameInput.isNotBlank() && provinceNameInput.isNotBlank()) {
                                val currentCity = City(name = cityNameInput, province = provinceNameInput)

                                if (selectedCityIndex != null) {
                                    onModifyCity(selectedCityIndex!!, currentCity)
                                } else {
                                    onAddCity(currentCity)
                                }

                                cityNameInput = ""
                                provinceNameInput = ""
                                showInputFields = false
                                selectedCityIndex = null
                            }
                        }
                    ) {
                        Text(if (selectedCityIndex != null) "Update Details" else "Add City")
                    }
                }
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(cities) { index, city ->
                CityRow(
                    city = city,
                    modifier = Modifier.clickable {
                        selectedCityIndex = index
                        cityNameInput = city.name
                        provinceNameInput = city.province
                        showInputFields = true
                    }
                )

                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun CityRow(
    city: City,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = {},
            onModifyCity = { _, _ -> }
        )
    }
}
