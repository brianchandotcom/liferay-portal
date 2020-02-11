/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.FragmentFieldCustom;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FragmentFieldCustomSerDes {

	public static FragmentFieldCustom toDTO(String json) {
		FragmentFieldCustomJSONParser fragmentFieldCustomJSONParser =
			new FragmentFieldCustomJSONParser();

		return fragmentFieldCustomJSONParser.parseToDTO(json);
	}

	public static FragmentFieldCustom[] toDTOs(String json) {
		FragmentFieldCustomJSONParser fragmentFieldCustomJSONParser =
			new FragmentFieldCustomJSONParser();

		return fragmentFieldCustomJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FragmentFieldCustom fragmentFieldCustom) {
		if (fragmentFieldCustom == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (fragmentFieldCustom.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(fragmentFieldCustom.getType()));

			sb.append("\"");
		}

		if (fragmentFieldCustom.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append(String.valueOf(fragmentFieldCustom.getValue()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FragmentFieldCustomJSONParser fragmentFieldCustomJSONParser =
			new FragmentFieldCustomJSONParser();

		return fragmentFieldCustomJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		FragmentFieldCustom fragmentFieldCustom) {

		if (fragmentFieldCustom == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (fragmentFieldCustom.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(fragmentFieldCustom.getType()));
		}

		if (fragmentFieldCustom.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(fragmentFieldCustom.getValue()));
		}

		return map;
	}

	public static class FragmentFieldCustomJSONParser
		extends BaseJSONParser<FragmentFieldCustom> {

		@Override
		protected FragmentFieldCustom createDTO() {
			return new FragmentFieldCustom();
		}

		@Override
		protected FragmentFieldCustom[] createDTOArray(int size) {
			return new FragmentFieldCustom[size];
		}

		@Override
		protected void setField(
			FragmentFieldCustom fragmentFieldCustom, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					fragmentFieldCustom.setType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					fragmentFieldCustom.setValue(
						ValueSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
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
			sb.append("\":");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}