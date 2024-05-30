/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.client.serdes.v1_0_0;

import com.example.sample.client.dto.v1_0_0.TestJSONMapAttribute;
import com.example.sample.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author John Doe
 * @generated
 */
@Generated("")
public class TestJSONMapAttributeSerDes {

	public static TestJSONMapAttribute toDTO(String json) {
		TestJSONMapAttributeJSONParser testJSONMapAttributeJSONParser =
			new TestJSONMapAttributeJSONParser();

		return testJSONMapAttributeJSONParser.parseToDTO(json);
	}

	public static TestJSONMapAttribute[] toDTOs(String json) {
		TestJSONMapAttributeJSONParser testJSONMapAttributeJSONParser =
			new TestJSONMapAttributeJSONParser();

		return testJSONMapAttributeJSONParser.parseToDTOs(json);
	}

	public static String toJSON(TestJSONMapAttribute testJSONMapAttribute) {
		if (testJSONMapAttribute == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (testJSONMapAttribute.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(testJSONMapAttribute.getDescription()));

			sb.append("\"");
		}

		if (testJSONMapAttribute.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(testJSONMapAttribute.getName()));

			sb.append("\"");
		}

		if (testJSONMapAttribute.getProperties1() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"properties1\": ");

			sb.append(_toJSON(testJSONMapAttribute.getProperties1()));
		}

		if (testJSONMapAttribute.getProperties2() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"properties2\": ");

			sb.append(_toJSON(testJSONMapAttribute.getProperties2()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		TestJSONMapAttributeJSONParser testJSONMapAttributeJSONParser =
			new TestJSONMapAttributeJSONParser();

		return testJSONMapAttributeJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		TestJSONMapAttribute testJSONMapAttribute) {

		if (testJSONMapAttribute == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (testJSONMapAttribute.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(testJSONMapAttribute.getDescription()));
		}

		if (testJSONMapAttribute.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(testJSONMapAttribute.getName()));
		}

		if (testJSONMapAttribute.getProperties1() == null) {
			map.put("properties1", null);
		}
		else {
			map.put(
				"properties1",
				String.valueOf(testJSONMapAttribute.getProperties1()));
		}

		if (testJSONMapAttribute.getProperties2() == null) {
			map.put("properties2", null);
		}
		else {
			map.put(
				"properties2",
				String.valueOf(testJSONMapAttribute.getProperties2()));
		}

		return map;
	}

	public static class TestJSONMapAttributeJSONParser
		extends BaseJSONParser<TestJSONMapAttribute> {

		@Override
		protected TestJSONMapAttribute createDTO() {
			return new TestJSONMapAttribute();
		}

		@Override
		protected TestJSONMapAttribute[] createDTOArray(int size) {
			return new TestJSONMapAttribute[size];
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
			TestJSONMapAttribute testJSONMapAttribute,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					testJSONMapAttribute.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					testJSONMapAttribute.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "properties1")) {
				if (jsonParserFieldValue != null) {
					testJSONMapAttribute.setProperties1(
						(Map<String, Object>)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "properties2")) {
				if (jsonParserFieldValue != null) {
					testJSONMapAttribute.setProperties2(
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