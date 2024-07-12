/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0;

import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0.NestedArrayItemsTestObject;
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
public class NestedArrayItemsTestObjectSerDes {

	public static NestedArrayItemsTestObject toDTO(String json) {
		NestedArrayItemsTestObjectJSONParser
			nestedArrayItemsTestObjectJSONParser =
				new NestedArrayItemsTestObjectJSONParser();

		return nestedArrayItemsTestObjectJSONParser.parseToDTO(json);
	}

	public static NestedArrayItemsTestObject[] toDTOs(String json) {
		NestedArrayItemsTestObjectJSONParser
			nestedArrayItemsTestObjectJSONParser =
				new NestedArrayItemsTestObjectJSONParser();

		return nestedArrayItemsTestObjectJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		NestedArrayItemsTestObject nestedArrayItemsTestObject) {

		if (nestedArrayItemsTestObject == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (nestedArrayItemsTestObject.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(nestedArrayItemsTestObject.getName()));

			sb.append("\"");
		}

		if (nestedArrayItemsTestObject.getValues() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"values\": ");

			sb.append("[");

			for (int i = 0; i < nestedArrayItemsTestObject.getValues().length;
				 i++) {

				sb.append(nestedArrayItemsTestObject.getValues()[i]);

				if ((i + 1) < nestedArrayItemsTestObject.getValues().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		NestedArrayItemsTestObjectJSONParser
			nestedArrayItemsTestObjectJSONParser =
				new NestedArrayItemsTestObjectJSONParser();

		return nestedArrayItemsTestObjectJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		NestedArrayItemsTestObject nestedArrayItemsTestObject) {

		if (nestedArrayItemsTestObject == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (nestedArrayItemsTestObject.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put(
				"name", String.valueOf(nestedArrayItemsTestObject.getName()));
		}

		if (nestedArrayItemsTestObject.getValues() == null) {
			map.put("values", null);
		}
		else {
			map.put(
				"values",
				String.valueOf(nestedArrayItemsTestObject.getValues()));
		}

		return map;
	}

	public static class NestedArrayItemsTestObjectJSONParser
		extends BaseJSONParser<NestedArrayItemsTestObject> {

		@Override
		protected NestedArrayItemsTestObject createDTO() {
			return new NestedArrayItemsTestObject();
		}

		@Override
		protected NestedArrayItemsTestObject[] createDTOArray(int size) {
			return new NestedArrayItemsTestObject[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "values")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			NestedArrayItemsTestObject nestedArrayItemsTestObject,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					nestedArrayItemsTestObject.setName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "values")) {
				if (jsonParserFieldValue != null) {
					nestedArrayItemsTestObject.setValues(
						(String[][])jsonParserFieldValue);
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