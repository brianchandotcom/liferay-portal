/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.client.serdes.v1_0_0;

import com.example.sample.client.dto.v1_0_0.ExternalReferenceElement2;
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
public class ExternalReferenceElement2SerDes {

	public static ExternalReferenceElement2 toDTO(String json) {
		ExternalReferenceElement2JSONParser
			externalReferenceElement2JSONParser =
				new ExternalReferenceElement2JSONParser();

		return externalReferenceElement2JSONParser.parseToDTO(json);
	}

	public static ExternalReferenceElement2[] toDTOs(String json) {
		ExternalReferenceElement2JSONParser
			externalReferenceElement2JSONParser =
				new ExternalReferenceElement2JSONParser();

		return externalReferenceElement2JSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ExternalReferenceElement2 externalReferenceElement2) {

		if (externalReferenceElement2 == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (externalReferenceElement2.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(externalReferenceElement2.getName()));

			sb.append("\"");
		}

		if (externalReferenceElement2.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append("\"");

			sb.append(_escape(externalReferenceElement2.getValue()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ExternalReferenceElement2JSONParser
			externalReferenceElement2JSONParser =
				new ExternalReferenceElement2JSONParser();

		return externalReferenceElement2JSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ExternalReferenceElement2 externalReferenceElement2) {

		if (externalReferenceElement2 == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (externalReferenceElement2.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put(
				"name", String.valueOf(externalReferenceElement2.getName()));
		}

		if (externalReferenceElement2.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put(
				"value", String.valueOf(externalReferenceElement2.getValue()));
		}

		return map;
	}

	public static class ExternalReferenceElement2JSONParser
		extends BaseJSONParser<ExternalReferenceElement2> {

		@Override
		protected ExternalReferenceElement2 createDTO() {
			return new ExternalReferenceElement2();
		}

		@Override
		protected ExternalReferenceElement2[] createDTOArray(int size) {
			return new ExternalReferenceElement2[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ExternalReferenceElement2 externalReferenceElement2,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					externalReferenceElement2.setName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					externalReferenceElement2.setValue(
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