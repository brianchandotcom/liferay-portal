/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.client.serdes.v1_0_0;

import com.example.sample.client.dto.v1_0_0.ChildSchema2;
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
public class ChildSchema2SerDes {

	public static ChildSchema2 toDTO(String json) {
		ChildSchema2JSONParser childSchema2JSONParser =
			new ChildSchema2JSONParser();

		return childSchema2JSONParser.parseToDTO(json);
	}

	public static ChildSchema2[] toDTOs(String json) {
		ChildSchema2JSONParser childSchema2JSONParser =
			new ChildSchema2JSONParser();

		return childSchema2JSONParser.parseToDTOs(json);
	}

	public static String toJSON(ChildSchema2 childSchema2) {
		if (childSchema2 == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (childSchema2.getText() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"text\": ");

			sb.append("\"");

			sb.append(_escape(childSchema2.getText()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ChildSchema2JSONParser childSchema2JSONParser =
			new ChildSchema2JSONParser();

		return childSchema2JSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ChildSchema2 childSchema2) {
		if (childSchema2 == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (childSchema2.getText() == null) {
			map.put("text", null);
		}
		else {
			map.put("text", String.valueOf(childSchema2.getText()));
		}

		return map;
	}

	public static class ChildSchema2JSONParser
		extends BaseJSONParser<ChildSchema2> {

		@Override
		protected ChildSchema2 createDTO() {
			return new ChildSchema2();
		}

		@Override
		protected ChildSchema2[] createDTOArray(int size) {
			return new ChildSchema2[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "text")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ChildSchema2 childSchema2, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "text")) {
				if (jsonParserFieldValue != null) {
					childSchema2.setText((String)jsonParserFieldValue);
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