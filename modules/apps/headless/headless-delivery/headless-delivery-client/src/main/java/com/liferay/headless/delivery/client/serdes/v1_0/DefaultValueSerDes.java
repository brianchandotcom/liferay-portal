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

import com.liferay.headless.delivery.client.dto.v1_0.DefaultValue;
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
public class DefaultValueSerDes {

	public static DefaultValue toDTO(String json) {
		DefaultValueJSONParser defaultValueJSONParser =
			new DefaultValueJSONParser();

		return defaultValueJSONParser.parseToDTO(json);
	}

	public static DefaultValue[] toDTOs(String json) {
		DefaultValueJSONParser defaultValueJSONParser =
			new DefaultValueJSONParser();

		return defaultValueJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DefaultValue defaultValue) {
		if (defaultValue == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (defaultValue.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append("\"");

			sb.append(_escape(defaultValue.getValue()));

			sb.append("\"");
		}

		if (defaultValue.getValue_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value_i18n\": ");

			sb.append(_toJSON(defaultValue.getValue_i18n()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DefaultValueJSONParser defaultValueJSONParser =
			new DefaultValueJSONParser();

		return defaultValueJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DefaultValue defaultValue) {
		if (defaultValue == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (defaultValue.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(defaultValue.getValue()));
		}

		if (defaultValue.getValue_i18n() == null) {
			map.put("value_i18n", null);
		}
		else {
			map.put("value_i18n", String.valueOf(defaultValue.getValue_i18n()));
		}

		return map;
	}

	public static class DefaultValueJSONParser
		extends BaseJSONParser<DefaultValue> {

		@Override
		protected DefaultValue createDTO() {
			return new DefaultValue();
		}

		@Override
		protected DefaultValue[] createDTOArray(int size) {
			return new DefaultValue[size];
		}

		@Override
		protected void setField(
			DefaultValue defaultValue, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					defaultValue.setValue((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value_i18n")) {
				if (jsonParserFieldValue != null) {
					defaultValue.setValue_i18n(
						(Map)DefaultValueSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (jsonParserFieldName.equals("status")) {
				throw new IllegalArgumentException();
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
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}