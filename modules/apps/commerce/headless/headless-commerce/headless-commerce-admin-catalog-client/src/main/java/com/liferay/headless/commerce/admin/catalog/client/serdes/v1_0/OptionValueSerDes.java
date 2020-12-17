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

package com.liferay.headless.commerce.admin.catalog.client.serdes.v1_0;

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.OptionValue;
import com.liferay.headless.commerce.admin.catalog.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class OptionValueSerDes {

	public static OptionValue toDTO(String json) {
		OptionValueJSONParser optionValueJSONParser =
			new OptionValueJSONParser();

		return optionValueJSONParser.parseToDTO(json);
	}

	public static OptionValue[] toDTOs(String json) {
		OptionValueJSONParser optionValueJSONParser =
			new OptionValueJSONParser();

		return optionValueJSONParser.parseToDTOs(json);
	}

	public static String toJSON(OptionValue optionValue) {
		if (optionValue == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (optionValue.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(optionValue.getActions()));
		}

		if (optionValue.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(optionValue.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (optionValue.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(optionValue.getId());
		}

		if (optionValue.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(optionValue.getKey()));

			sb.append("\"");
		}

		if (optionValue.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append(_toJSON(optionValue.getName()));
		}

		if (optionValue.getPriority() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priority\": ");

			sb.append(optionValue.getPriority());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		OptionValueJSONParser optionValueJSONParser =
			new OptionValueJSONParser();

		return optionValueJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(OptionValue optionValue) {
		if (optionValue == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (optionValue.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(optionValue.getActions()));
		}

		if (optionValue.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(optionValue.getExternalReferenceCode()));
		}

		if (optionValue.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(optionValue.getId()));
		}

		if (optionValue.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(optionValue.getKey()));
		}

		if (optionValue.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(optionValue.getName()));
		}

		if (optionValue.getPriority() == null) {
			map.put("priority", null);
		}
		else {
			map.put("priority", String.valueOf(optionValue.getPriority()));
		}

		return map;
	}

	public static class OptionValueJSONParser
		extends BaseJSONParser<OptionValue> {

		@Override
		protected OptionValue createDTO() {
			return new OptionValue();
		}

		@Override
		protected OptionValue[] createDTOArray(int size) {
			return new OptionValue[size];
		}

		@Override
		protected void setField(
			OptionValue optionValue, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					optionValue.setActions(
						(Map)OptionValueSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					optionValue.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					optionValue.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					optionValue.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					optionValue.setName(
						(Map)OptionValueSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priority")) {
				if (jsonParserFieldValue != null) {
					optionValue.setPriority(
						Double.valueOf((String)jsonParserFieldValue));
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