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

package com.liferay.headless.commerce.delivery.catalog.v2.client.serdes.v2_0;

import com.liferay.headless.commerce.delivery.catalog.v2.client.dto.v2_0.SkuOptionValue;
import com.liferay.headless.commerce.delivery.catalog.v2.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class SkuOptionValueSerDes {

	public static SkuOptionValue toDTO(String json) {
		SkuOptionValueJSONParser skuOptionValueJSONParser =
			new SkuOptionValueJSONParser();

		return skuOptionValueJSONParser.parseToDTO(json);
	}

	public static SkuOptionValue[] toDTOs(String json) {
		SkuOptionValueJSONParser skuOptionValueJSONParser =
			new SkuOptionValueJSONParser();

		return skuOptionValueJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SkuOptionValue skuOptionValue) {
		if (skuOptionValue == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (skuOptionValue.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(skuOptionValue.getId());
		}

		if (skuOptionValue.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(skuOptionValue.getKey()));

			sb.append("\"");
		}

		if (skuOptionValue.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(skuOptionValue.getName()));

			sb.append("\"");
		}

		if (skuOptionValue.getPreselected() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"preselected\": ");

			sb.append(skuOptionValue.getPreselected());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SkuOptionValueJSONParser skuOptionValueJSONParser =
			new SkuOptionValueJSONParser();

		return skuOptionValueJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SkuOptionValue skuOptionValue) {
		if (skuOptionValue == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (skuOptionValue.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(skuOptionValue.getId()));
		}

		if (skuOptionValue.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(skuOptionValue.getKey()));
		}

		if (skuOptionValue.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(skuOptionValue.getName()));
		}

		if (skuOptionValue.getPreselected() == null) {
			map.put("preselected", null);
		}
		else {
			map.put(
				"preselected", String.valueOf(skuOptionValue.getPreselected()));
		}

		return map;
	}

	public static class SkuOptionValueJSONParser
		extends BaseJSONParser<SkuOptionValue> {

		@Override
		protected SkuOptionValue createDTO() {
			return new SkuOptionValue();
		}

		@Override
		protected SkuOptionValue[] createDTOArray(int size) {
			return new SkuOptionValue[size];
		}

		@Override
		protected void setField(
			SkuOptionValue skuOptionValue, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					skuOptionValue.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					skuOptionValue.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					skuOptionValue.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "preselected")) {
				if (jsonParserFieldValue != null) {
					skuOptionValue.setPreselected(
						(Boolean)jsonParserFieldValue);
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