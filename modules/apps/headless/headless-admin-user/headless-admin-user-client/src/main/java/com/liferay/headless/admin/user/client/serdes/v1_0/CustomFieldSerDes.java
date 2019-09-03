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

package com.liferay.headless.admin.user.client.serdes.v1_0;

import com.liferay.headless.admin.user.client.dto.v1_0.CustomField;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

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
public class CustomFieldSerDes {

	public static CustomField toDTO(String json) {
		CustomFieldJSONParser customFieldJSONParser =
			new CustomFieldJSONParser();

		return customFieldJSONParser.parseToDTO(json);
	}

	public static CustomField[] toDTOs(String json) {
		CustomFieldJSONParser customFieldJSONParser =
			new CustomFieldJSONParser();

		return customFieldJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CustomField customField) {
		if (customField == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (customField.getCustomValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customValue\": ");

			sb.append(String.valueOf(customField.getCustomValue()));
		}

		if (customField.getDataType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataType\": ");

			sb.append("\"");

			sb.append(_escape(customField.getDataType()));

			sb.append("\"");
		}

		if (customField.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(customField.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CustomFieldJSONParser customFieldJSONParser =
			new CustomFieldJSONParser();

		return customFieldJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(CustomField customField) {
		if (customField == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (customField.getCustomValue() == null) {
			map.put("customValue", null);
		}
		else {
			map.put(
				"customValue", String.valueOf(customField.getCustomValue()));
		}

		if (customField.getDataType() == null) {
			map.put("dataType", null);
		}
		else {
			map.put("dataType", String.valueOf(customField.getDataType()));
		}

		if (customField.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(customField.getName()));
		}

		return map;
	}

	public static class CustomFieldJSONParser
		extends BaseJSONParser<CustomField> {

		@Override
		protected CustomField createDTO() {
			return new CustomField();
		}

		@Override
		protected CustomField[] createDTOArray(int size) {
			return new CustomField[size];
		}

		@Override
		protected void setField(
			CustomField customField, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "customValue")) {
				if (jsonParserFieldValue != null) {
					customField.setCustomValue(
						CustomValueSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataType")) {
				if (jsonParserFieldValue != null) {
					customField.setDataType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					customField.setName((String)jsonParserFieldValue);
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