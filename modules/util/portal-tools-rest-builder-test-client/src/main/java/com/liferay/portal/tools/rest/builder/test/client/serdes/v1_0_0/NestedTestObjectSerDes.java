/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0;

import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0.NestedTestObject;
import com.liferay.portal.tools.rest.builder.test.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
public class NestedTestObjectSerDes {

	public static NestedTestObject toDTO(String json) {
		NestedTestObjectJSONParser nestedTestObjectJSONParser =
			new NestedTestObjectJSONParser();

		return nestedTestObjectJSONParser.parseToDTO(json);
	}

	public static NestedTestObject[] toDTOs(String json) {
		NestedTestObjectJSONParser nestedTestObjectJSONParser =
			new NestedTestObjectJSONParser();

		return nestedTestObjectJSONParser.parseToDTOs(json);
	}

	public static String toJSON(NestedTestObject nestedTestObject) {
		if (nestedTestObject == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (nestedTestObject.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					nestedTestObject.getDateCreated()));

			sb.append("\"");
		}

		if (nestedTestObject.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					nestedTestObject.getDateModified()));

			sb.append("\"");
		}

		if (nestedTestObject.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(nestedTestObject.getDescription()));

			sb.append("\"");
		}

		if (nestedTestObject.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(nestedTestObject.getId());
		}

		if (nestedTestObject.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(nestedTestObject.getName()));

			sb.append("\"");
		}

		if (nestedTestObject.getTestObject() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testObject\": ");

			sb.append(String.valueOf(nestedTestObject.getTestObject()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		NestedTestObjectJSONParser nestedTestObjectJSONParser =
			new NestedTestObjectJSONParser();

		return nestedTestObjectJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(NestedTestObject nestedTestObject) {
		if (nestedTestObject == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (nestedTestObject.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(
					nestedTestObject.getDateCreated()));
		}

		if (nestedTestObject.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(
					nestedTestObject.getDateModified()));
		}

		if (nestedTestObject.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(nestedTestObject.getDescription()));
		}

		if (nestedTestObject.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(nestedTestObject.getId()));
		}

		if (nestedTestObject.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(nestedTestObject.getName()));
		}

		if (nestedTestObject.getTestObject() == null) {
			map.put("testObject", null);
		}
		else {
			map.put(
				"testObject", String.valueOf(nestedTestObject.getTestObject()));
		}

		return map;
	}

	public static class NestedTestObjectJSONParser
		extends BaseJSONParser<NestedTestObject> {

		@Override
		protected NestedTestObject createDTO() {
			return new NestedTestObject();
		}

		@Override
		protected NestedTestObject[] createDTOArray(int size) {
			return new NestedTestObject[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "testObject")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			NestedTestObject nestedTestObject, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					nestedTestObject.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					nestedTestObject.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					nestedTestObject.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					nestedTestObject.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					nestedTestObject.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "testObject")) {
				if (jsonParserFieldValue != null) {
					nestedTestObject.setTestObject(
						TestObjectSerDes.toDTO((String)jsonParserFieldValue));
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