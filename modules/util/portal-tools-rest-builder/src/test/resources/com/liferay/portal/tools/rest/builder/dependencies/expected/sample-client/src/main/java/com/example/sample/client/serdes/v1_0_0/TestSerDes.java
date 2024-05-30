/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.client.serdes.v1_0_0;

import com.example.sample.client.dto.v1_0_0.Test;
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
public class TestSerDes {

	public static Test toDTO(String json) {
		TestJSONParser testJSONParser = new TestJSONParser();

		return testJSONParser.parseToDTO(json);
	}

	public static Test[] toDTOs(String json) {
		TestJSONParser testJSONParser = new TestJSONParser();

		return testJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Test test) {
		if (test == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (test.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(test.getId());
		}

		if (test.getJsonProperty() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"jsonProperty\": ");

			sb.append("\"");

			sb.append(_escape(test.getJsonProperty()));

			sb.append("\"");
		}

		if (test.getPropertyWithHyphens() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"property-with-hyphens\": ");

			sb.append("\"");

			sb.append(_escape(test.getPropertyWithHyphens()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		TestJSONParser testJSONParser = new TestJSONParser();

		return testJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Test test) {
		if (test == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (test.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(test.getId()));
		}

		if (test.getJsonProperty() == null) {
			map.put("jsonProperty", null);
		}
		else {
			map.put("jsonProperty", String.valueOf(test.getJsonProperty()));
		}

		if (test.getPropertyWithHyphens() == null) {
			map.put("property-with-hyphens", null);
		}
		else {
			map.put(
				"property-with-hyphens",
				String.valueOf(test.getPropertyWithHyphens()));
		}

		return map;
	}

	public static class TestJSONParser extends BaseJSONParser<Test> {

		@Override
		protected Test createDTO() {
			return new Test();
		}

		@Override
		protected Test[] createDTOArray(int size) {
			return new Test[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "jsonProperty")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "property-with-hyphens")) {

				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			Test test, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					test.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "jsonProperty")) {
				if (jsonParserFieldValue != null) {
					test.setJsonProperty((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "property-with-hyphens")) {

				if (jsonParserFieldValue != null) {
					test.setPropertyWithHyphens((String)jsonParserFieldValue);
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