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

package com.liferay.headless.commerce.admin.channel.client.serdes.v1_0;

import com.liferay.headless.commerce.admin.channel.client.dto.v1_0.ShippingOption;
import com.liferay.headless.commerce.admin.channel.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class ShippingOptionSerDes {

	public static ShippingOption toDTO(String json) {
		ShippingOptionJSONParser shippingOptionJSONParser =
			new ShippingOptionJSONParser();

		return shippingOptionJSONParser.parseToDTO(json);
	}

	public static ShippingOption[] toDTOs(String json) {
		ShippingOptionJSONParser shippingOptionJSONParser =
			new ShippingOptionJSONParser();

		return shippingOptionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ShippingOption shippingOption) {
		if (shippingOption == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (shippingOption.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(shippingOption.getActive());
		}

		if (shippingOption.getAmount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"amount\": ");

			sb.append(shippingOption.getAmount());
		}

		if (shippingOption.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append(_toJSON(shippingOption.getDescription()));
		}

		if (shippingOption.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(shippingOption.getId());
		}

		if (shippingOption.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append(_toJSON(shippingOption.getName()));
		}

		if (shippingOption.getPriority() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priority\": ");

			sb.append(shippingOption.getPriority());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ShippingOptionJSONParser shippingOptionJSONParser =
			new ShippingOptionJSONParser();

		return shippingOptionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ShippingOption shippingOption) {
		if (shippingOption == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (shippingOption.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(shippingOption.getActive()));
		}

		if (shippingOption.getAmount() == null) {
			map.put("amount", null);
		}
		else {
			map.put("amount", String.valueOf(shippingOption.getAmount()));
		}

		if (shippingOption.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description", String.valueOf(shippingOption.getDescription()));
		}

		if (shippingOption.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(shippingOption.getId()));
		}

		if (shippingOption.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(shippingOption.getName()));
		}

		if (shippingOption.getPriority() == null) {
			map.put("priority", null);
		}
		else {
			map.put("priority", String.valueOf(shippingOption.getPriority()));
		}

		return map;
	}

	public static class ShippingOptionJSONParser
		extends BaseJSONParser<ShippingOption> {

		@Override
		protected ShippingOption createDTO() {
			return new ShippingOption();
		}

		@Override
		protected ShippingOption[] createDTOArray(int size) {
			return new ShippingOption[size];
		}

		@Override
		protected void setField(
			ShippingOption shippingOption, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					shippingOption.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "amount")) {
				if (jsonParserFieldValue != null) {
					shippingOption.setAmount(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					shippingOption.setDescription(
						(Map)ShippingOptionSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					shippingOption.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					shippingOption.setName(
						(Map)ShippingOptionSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priority")) {
				if (jsonParserFieldValue != null) {
					shippingOption.setPriority(
						Double.valueOf((String)jsonParserFieldValue));
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
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}