/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0;

import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0.JSONMapAttributeTestObject;
import com.liferay.portal.tools.rest.builder.test.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class JSONMapAttributeTestObjectSerDes {

	public static JSONMapAttributeTestObject toDTO(String json) {
		JSONMapAttributeTestObjectJSONParser
			jsonMapAttributeTestObjectJSONParser =
				new JSONMapAttributeTestObjectJSONParser();

		return jsonMapAttributeTestObjectJSONParser.parseToDTO(json);
	}

	public static JSONMapAttributeTestObject[] toDTOs(String json) {
		JSONMapAttributeTestObjectJSONParser
			jsonMapAttributeTestObjectJSONParser =
				new JSONMapAttributeTestObjectJSONParser();

		return jsonMapAttributeTestObjectJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		JSONMapAttributeTestObject jsonMapAttributeTestObject) {

		if (jsonMapAttributeTestObject == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (jsonMapAttributeTestObject.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(jsonMapAttributeTestObject.getDescription()));

			sb.append("\"");
		}

		if (jsonMapAttributeTestObject.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(jsonMapAttributeTestObject.getName()));

			sb.append("\"");
		}

		if (jsonMapAttributeTestObject.getProperties1() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"properties1\": ");

			sb.append(_toJSON(jsonMapAttributeTestObject.getProperties1()));
		}

		if (jsonMapAttributeTestObject.getProperties2() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"properties2\": ");

			sb.append(_toJSON(jsonMapAttributeTestObject.getProperties2()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		JSONMapAttributeTestObjectJSONParser
			jsonMapAttributeTestObjectJSONParser =
				new JSONMapAttributeTestObjectJSONParser();

		return jsonMapAttributeTestObjectJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		JSONMapAttributeTestObject jsonMapAttributeTestObject) {

		if (jsonMapAttributeTestObject == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (jsonMapAttributeTestObject.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(jsonMapAttributeTestObject.getDescription()));
		}

		if (jsonMapAttributeTestObject.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put(
				"name", String.valueOf(jsonMapAttributeTestObject.getName()));
		}

		if (jsonMapAttributeTestObject.getProperties1() == null) {
			map.put("properties1", null);
		}
		else {
			map.put(
				"properties1",
				String.valueOf(jsonMapAttributeTestObject.getProperties1()));
		}

		if (jsonMapAttributeTestObject.getProperties2() == null) {
			map.put("properties2", null);
		}
		else {
			map.put(
				"properties2",
				String.valueOf(jsonMapAttributeTestObject.getProperties2()));
		}

		return map;
	}

	public static class JSONMapAttributeTestObjectJSONParser
		extends BaseJSONParser<JSONMapAttributeTestObject> {

		@Override
		protected JSONMapAttributeTestObject createDTO() {
			return new JSONMapAttributeTestObject();
		}

		@Override
		protected JSONMapAttributeTestObject[] createDTOArray(int size) {
			return new JSONMapAttributeTestObject[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "description")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "properties1")) {
				return true;
			}
			else if (Objects.equals(jsonParserFieldName, "properties2")) {
				return true;
			}

			return false;
		}

		@Override
		protected void setField(
			JSONMapAttributeTestObject jsonMapAttributeTestObject,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					jsonMapAttributeTestObject.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					jsonMapAttributeTestObject.setName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "properties1")) {
				if (jsonParserFieldValue != null) {
					jsonMapAttributeTestObject.setProperties1(
						(Map<String, Object>)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "properties2")) {
				if (jsonParserFieldValue != null) {
					jsonMapAttributeTestObject.setProperties2(
						(Map<String, Object>)jsonParserFieldValue);
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}