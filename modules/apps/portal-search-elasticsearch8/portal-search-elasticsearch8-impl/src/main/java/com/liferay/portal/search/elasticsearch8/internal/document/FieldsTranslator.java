/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.document;

import co.elastic.clients.json.JsonData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.elasticsearch8.internal.util.JsonpUtil;
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.geolocation.GeoLocationPoint;

import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bryan Engler
 * @author Petteri Karttunen
 */
public class FieldsTranslator {

	public void populateAlternateUID(
		String alternateUidFieldName, DocumentBuilder documentBuilder,
		Map<String, JsonData> jsonDatas) {

		if (MapUtil.isEmpty(jsonDatas) ||
			jsonDatas.containsKey(_UID_FIELD_NAME) ||
			Validator.isBlank(alternateUidFieldName)) {

			return;
		}

		JsonData jsonData = jsonDatas.get(alternateUidFieldName);

		if (jsonData != null) {
			documentBuilder.setValues(
				_UID_FIELD_NAME,
				_toCollectionValue(
					jsonData.toJson(JsonpUtil.getJsonpMapper())));
		}
	}

	public void translateFields(
		DocumentBuilder documentBuilder, Map<String, JsonData> jsonDatas) {

		if (MapUtil.isEmpty(jsonDatas)) {
			return;
		}

		jsonDatas.forEach(
			(fieldName, jsonData) -> translateField(
				documentBuilder, fieldName, jsonData, jsonDatas));
	}

	public void translateSource(
		DocumentBuilder documentBuilder, JsonData jsonData) {

		if (jsonData == null) {
			return;
		}

		JsonValue jsonValue = jsonData.toJson(JsonpUtil.getJsonpMapper());

		JsonObject jsonObject = jsonValue.asJsonObject();

		jsonObject.forEach(
			(fieldName, value) -> translateSourceField(
				documentBuilder, fieldName, value));
	}

	protected void translateField(
		DocumentBuilder documentBuilder, String fieldName, JsonData jsonData,
		Map<String, JsonData> jsonDatas) {

		if (fieldName.endsWith(_GEOPOINT_SUFFIX)) {
			return;
		}

		if (jsonDatas.get(fieldName.concat(_GEOPOINT_SUFFIX)) != null) {
			_translateGeoPoint(
				documentBuilder, fieldName, jsonDatas.get(fieldName));
		}
		else {
			documentBuilder.setValues(
				fieldName,
				_toCollectionValue(
					jsonData.toJson(JsonpUtil.getJsonpMapper())));
		}
	}

	protected void translateSourceField(
		DocumentBuilder documentBuilder, String fieldName,
		JsonValue jsonValue) {

		if (fieldName.endsWith(_GEOPOINT_SUFFIX)) {
			documentBuilder.setGeoLocationPoint(
				fieldName,
				GeoBuilders.INSTANCE.geoLocationPoint(jsonValue.toString()));
		}
		else {
			JsonValue.ValueType valueType = jsonValue.getValueType();

			if ((valueType == JsonValue.ValueType.ARRAY) ||
				(valueType == JsonValue.ValueType.OBJECT)) {

				documentBuilder.setValues(
					fieldName, _toCollectionValue(jsonValue));
			}
			else {
				documentBuilder.setValue(fieldName, _toSingleValue(jsonValue));
			}
		}
	}

	private GeoLocationPoint _getGeoLocationPoint(JsonValue jsonValue) {
		JsonValue.ValueType valueType = jsonValue.getValueType();

		if (valueType == JsonValue.ValueType.OBJECT) {
			JsonObject jsonObject = jsonValue.asJsonObject();

			JsonArray jsonArray = jsonObject.getJsonArray("coordinates");

			JsonNumber latitudeJsonNumber = jsonArray.getJsonNumber(1);
			JsonNumber longitudeJsonNumber = jsonArray.getJsonNumber(0);

			return GeoBuilders.INSTANCE.geoLocationPoint(
				latitudeJsonNumber.doubleValue(),
				longitudeJsonNumber.doubleValue());
		}

		String coordinates = jsonValue.toString();

		String[] coordinatesParts = coordinates.split(",");

		return GeoBuilders.INSTANCE.geoLocationPoint(
			Double.valueOf(coordinatesParts[0]),
			Double.valueOf(coordinatesParts[1]));
	}

	private Collection<Object> _toCollectionValue(JsonValue jsonValue) {
		List<Object> values = new ArrayList<>();

		JsonValue.ValueType valueType = jsonValue.getValueType();

		if (valueType == JsonValue.ValueType.ARRAY) {
			JsonArray jsonArray = jsonValue.asJsonArray();

			jsonArray.forEach(value -> values.add(_toSingleValue(value)));
		}
		else {
			values.add(_toSingleValue(jsonValue));
		}

		return values;
	}

	private Map<String, Object> _toMap(JsonObject jsonObject) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			TypeReference<HashMap<String, Object>> typeReference =
				new TypeReference<HashMap<String, Object>>() {
				};

			return objectMapper.readValue(jsonObject.toString(), typeReference);
		}
		catch (JsonProcessingException jsonProcessingException) {
			throw new RuntimeException(jsonProcessingException);
		}
	}

	private Object _toSingleValue(JsonValue jsonValue) {
		JsonValue.ValueType valueType = jsonValue.getValueType();

		if ((valueType == JsonValue.ValueType.FALSE) ||
			(valueType == JsonValue.ValueType.TRUE)) {

			return Boolean.valueOf(jsonValue.toString());
		}
		else if (valueType == JsonValue.ValueType.NULL) {
			return null;
		}
		else if (valueType == JsonValue.ValueType.NUMBER) {
			JsonNumber jsonNumber = (JsonNumber)jsonValue;

			return jsonNumber.numberValue();
		}
		else if (valueType == JsonValue.ValueType.OBJECT) {
			return _toMap((JsonObject)jsonValue);
		}
		else if (valueType == JsonValue.ValueType.STRING) {
			JsonString jsonString = (JsonString)jsonValue;

			return jsonString.getString();
		}

		return jsonValue.toString();
	}

	private void _translateGeoPoint(
		DocumentBuilder documentBuilder, String fieldName, JsonData jsonData) {

		JsonValue jsonValue = jsonData.toJson();

		JsonArray jsonArray = jsonValue.asJsonArray();

		documentBuilder.setGeoLocationPoint(
			fieldName, _getGeoLocationPoint(jsonArray.get(0)));
	}

	private static final String _GEOPOINT_SUFFIX = ".geopoint";

	private static final String _UID_FIELD_NAME = "uid";

}