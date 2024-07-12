/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.client.serdes.v1_0_0;

import com.example.sample.client.dto.v1_0_0.ChildSchema1;
import com.example.sample.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author John Doe
 * @generated
 */
@Generated("")
public class ChildSchema1SerDes {

	public static ChildSchema1 toDTO(String json) {
		ChildSchema1JSONParser childSchema1JSONParser =
			new ChildSchema1JSONParser();

		return childSchema1JSONParser.parseToDTO(json);
	}

	public static ChildSchema1[] toDTOs(String json) {
		ChildSchema1JSONParser childSchema1JSONParser =
			new ChildSchema1JSONParser();

		return childSchema1JSONParser.parseToDTOs(json);
	}

	public static String toJSON(ChildSchema1 childSchema1) {
		if (childSchema1 == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ChildSchema1JSONParser childSchema1JSONParser =
			new ChildSchema1JSONParser();

		return childSchema1JSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ChildSchema1 childSchema1) {
		if (childSchema1 == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		return map;
	}

	public static class ChildSchema1JSONParser
		extends BaseJSONParser<ChildSchema1> {

		@Override
		protected ChildSchema1 createDTO() {
			return new ChildSchema1();
		}

		@Override
		protected ChildSchema1[] createDTOArray(int size) {
			return new ChildSchema1[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			return false;
		}

		@Override
		protected void setField(
			ChildSchema1 childSchema1, String jsonParserFieldName,
			Object jsonParserFieldValue) {
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