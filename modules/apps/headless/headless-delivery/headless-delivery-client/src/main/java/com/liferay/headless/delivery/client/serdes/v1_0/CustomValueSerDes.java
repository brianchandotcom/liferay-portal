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

import com.liferay.headless.delivery.client.dto.v1_0.CustomValue;
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
public class CustomValueSerDes {

	public static CustomValue toDTO(String json) {
		CustomValueJSONParser customValueJSONParser =
			new CustomValueJSONParser();

		return customValueJSONParser.parseToDTO(json);
	}

	public static CustomValue[] toDTOs(String json) {
		CustomValueJSONParser customValueJSONParser =
			new CustomValueJSONParser();

		return customValueJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CustomValue customValue) {
		if (customValue == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (customValue.getData() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"data\": ");

			sb.append("\"");

			sb.append(_escape(customValue.getData()));

			sb.append("\"");
		}

		if (customValue.getGeo() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"geo\": ");

			sb.append(String.valueOf(customValue.getGeo()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CustomValueJSONParser customValueJSONParser =
			new CustomValueJSONParser();

		return customValueJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(CustomValue customValue) {
		if (customValue == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (customValue.getData() == null) {
			map.put("data", null);
		}
		else {
			map.put("data", String.valueOf(customValue.getData()));
		}

		if (customValue.getGeo() == null) {
			map.put("geo", null);
		}
		else {
			map.put("geo", String.valueOf(customValue.getGeo()));
		}

		return map;
	}

	public static class CustomValueJSONParser
		extends BaseJSONParser<CustomValue> {

		@Override
		protected CustomValue createDTO() {
			return new CustomValue();
		}

		@Override
		protected CustomValue[] createDTOArray(int size) {
			return new CustomValue[size];
		}

		@Override
		protected void setField(
			CustomValue customValue, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "data")) {
				if (jsonParserFieldValue != null) {
					customValue.setData((Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "geo")) {
				if (jsonParserFieldValue != null) {
					customValue.setGeo(
						GeoSerDes.toDTO((String)jsonParserFieldValue));
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

		string = string.replace("\\", "\\\\");

		return string.replace("\"", "\\\"");
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