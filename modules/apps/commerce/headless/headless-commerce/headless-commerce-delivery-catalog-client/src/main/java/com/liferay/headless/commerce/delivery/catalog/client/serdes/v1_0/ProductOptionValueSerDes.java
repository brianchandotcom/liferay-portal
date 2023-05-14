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

package com.liferay.headless.commerce.delivery.catalog.client.serdes.v1_0;

import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.ProductOptionValue;
import com.liferay.headless.commerce.delivery.catalog.client.json.BaseJSONParser;

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
public class ProductOptionValueSerDes {

	public static ProductOptionValue toDTO(String json) {
		ProductOptionValueJSONParser productOptionValueJSONParser =
			new ProductOptionValueJSONParser();

		return productOptionValueJSONParser.parseToDTO(json);
	}

	public static ProductOptionValue[] toDTOs(String json) {
		ProductOptionValueJSONParser productOptionValueJSONParser =
			new ProductOptionValueJSONParser();

		return productOptionValueJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ProductOptionValue productOptionValue) {
		if (productOptionValue == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (productOptionValue.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(productOptionValue.getId());
		}

		if (productOptionValue.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(productOptionValue.getKey()));

			sb.append("\"");
		}

		if (productOptionValue.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(productOptionValue.getName()));

			sb.append("\"");
		}

		if (productOptionValue.getPreselected() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"preselected\": ");

			sb.append(productOptionValue.getPreselected());
		}

		if (productOptionValue.getPriority() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priority\": ");

			sb.append(productOptionValue.getPriority());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ProductOptionValueJSONParser productOptionValueJSONParser =
			new ProductOptionValueJSONParser();

		return productOptionValueJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ProductOptionValue productOptionValue) {

		if (productOptionValue == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (productOptionValue.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(productOptionValue.getId()));
		}

		if (productOptionValue.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(productOptionValue.getKey()));
		}

		if (productOptionValue.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(productOptionValue.getName()));
		}

		if (productOptionValue.getPreselected() == null) {
			map.put("preselected", null);
		}
		else {
			map.put(
				"preselected",
				String.valueOf(productOptionValue.getPreselected()));
		}

		if (productOptionValue.getPriority() == null) {
			map.put("priority", null);
		}
		else {
			map.put(
				"priority", String.valueOf(productOptionValue.getPriority()));
		}

		return map;
	}

	public static class ProductOptionValueJSONParser
		extends BaseJSONParser<ProductOptionValue> {

		@Override
		protected ProductOptionValue createDTO() {
			return new ProductOptionValue();
		}

		@Override
		protected ProductOptionValue[] createDTOArray(int size) {
			return new ProductOptionValue[size];
		}

		@Override
		protected void setField(
			ProductOptionValue productOptionValue, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					productOptionValue.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					productOptionValue.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					productOptionValue.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "preselected")) {
				if (jsonParserFieldValue != null) {
					productOptionValue.setPreselected(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priority")) {
				if (jsonParserFieldValue != null) {
					productOptionValue.setPriority(
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