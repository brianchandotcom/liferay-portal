/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.serdes.v1_0_0;

import com.liferay.portal.tools.rest.builder.test.client.dto.v1_0_0.UnreferencedTestObject;
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
public class UnreferencedTestObjectSerDes {

	public static UnreferencedTestObject toDTO(String json) {
		UnreferencedTestObjectJSONParser unreferencedTestObjectJSONParser =
			new UnreferencedTestObjectJSONParser();

		return unreferencedTestObjectJSONParser.parseToDTO(json);
	}

	public static UnreferencedTestObject[] toDTOs(String json) {
		UnreferencedTestObjectJSONParser unreferencedTestObjectJSONParser =
			new UnreferencedTestObjectJSONParser();

		return unreferencedTestObjectJSONParser.parseToDTOs(json);
	}

	public static String toJSON(UnreferencedTestObject unreferencedTestObject) {
		if (unreferencedTestObject == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (unreferencedTestObject.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(unreferencedTestObject.getDescription()));

			sb.append("\"");
		}

		if (unreferencedTestObject.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(unreferencedTestObject.getId());
		}

		if (unreferencedTestObject.getPropertyWithHyphens() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"property-with-hyphens\": ");

			sb.append("\"");

			sb.append(_escape(unreferencedTestObject.getPropertyWithHyphens()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		UnreferencedTestObjectJSONParser unreferencedTestObjectJSONParser =
			new UnreferencedTestObjectJSONParser();

		return unreferencedTestObjectJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		UnreferencedTestObject unreferencedTestObject) {

		if (unreferencedTestObject == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (unreferencedTestObject.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(unreferencedTestObject.getDescription()));
		}

		if (unreferencedTestObject.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(unreferencedTestObject.getId()));
		}

		if (unreferencedTestObject.getPropertyWithHyphens() == null) {
			map.put("property-with-hyphens", null);
		}
		else {
			map.put(
				"property-with-hyphens",
				String.valueOf(
					unreferencedTestObject.getPropertyWithHyphens()));
		}

		return map;
	}

	public static class UnreferencedTestObjectJSONParser
		extends BaseJSONParser<UnreferencedTestObject> {

		@Override
		protected UnreferencedTestObject createDTO() {
			return new UnreferencedTestObject();
		}

		@Override
		protected UnreferencedTestObject[] createDTOArray(int size) {
			return new UnreferencedTestObject[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "description")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
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
			UnreferencedTestObject unreferencedTestObject,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					unreferencedTestObject.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					unreferencedTestObject.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "property-with-hyphens")) {

				if (jsonParserFieldValue != null) {
					unreferencedTestObject.setPropertyWithHyphens(
						(String)jsonParserFieldValue);
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