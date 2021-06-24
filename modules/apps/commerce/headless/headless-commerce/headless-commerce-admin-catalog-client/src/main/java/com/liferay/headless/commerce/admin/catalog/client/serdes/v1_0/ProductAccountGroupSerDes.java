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

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductAccountGroup;
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
public class ProductAccountGroupSerDes {

	public static ProductAccountGroup toDTO(String json) {
		ProductAccountGroupJSONParser productAccountGroupJSONParser =
			new ProductAccountGroupJSONParser();

		return productAccountGroupJSONParser.parseToDTO(json);
	}

	public static ProductAccountGroup[] toDTOs(String json) {
		ProductAccountGroupJSONParser productAccountGroupJSONParser =
			new ProductAccountGroupJSONParser();

		return productAccountGroupJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ProductAccountGroup productAccountGroup) {
		if (productAccountGroup == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (productAccountGroup.getAccountGroupId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountGroupId\": ");

			sb.append(productAccountGroup.getAccountGroupId());
		}

		if (productAccountGroup.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(productAccountGroup.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (productAccountGroup.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(productAccountGroup.getId());
		}

		if (productAccountGroup.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(productAccountGroup.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ProductAccountGroupJSONParser productAccountGroupJSONParser =
			new ProductAccountGroupJSONParser();

		return productAccountGroupJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ProductAccountGroup productAccountGroup) {

		if (productAccountGroup == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (productAccountGroup.getAccountGroupId() == null) {
			map.put("accountGroupId", null);
		}
		else {
			map.put(
				"accountGroupId",
				String.valueOf(productAccountGroup.getAccountGroupId()));
		}

		if (productAccountGroup.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(productAccountGroup.getExternalReferenceCode()));
		}

		if (productAccountGroup.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(productAccountGroup.getId()));
		}

		if (productAccountGroup.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(productAccountGroup.getName()));
		}

		return map;
	}

	public static class ProductAccountGroupJSONParser
		extends BaseJSONParser<ProductAccountGroup> {

		@Override
		protected ProductAccountGroup createDTO() {
			return new ProductAccountGroup();
		}

		@Override
		protected ProductAccountGroup[] createDTOArray(int size) {
			return new ProductAccountGroup[size];
		}

		@Override
		protected void setField(
			ProductAccountGroup productAccountGroup, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "accountGroupId")) {
				if (jsonParserFieldValue != null) {
					productAccountGroup.setAccountGroupId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					productAccountGroup.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					productAccountGroup.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					productAccountGroup.setName((String)jsonParserFieldValue);
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