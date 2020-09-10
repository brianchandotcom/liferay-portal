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

package com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0;

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.DiscountCategory;
import com.liferay.headless.commerce.admin.pricing.client.json.BaseJSONParser;

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
public class DiscountCategorySerDes {

	public static DiscountCategory toDTO(String json) {
		DiscountCategoryJSONParser discountCategoryJSONParser =
			new DiscountCategoryJSONParser();

		return discountCategoryJSONParser.parseToDTO(json);
	}

	public static DiscountCategory[] toDTOs(String json) {
		DiscountCategoryJSONParser discountCategoryJSONParser =
			new DiscountCategoryJSONParser();

		return discountCategoryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DiscountCategory discountCategory) {
		if (discountCategory == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (discountCategory.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(discountCategory.getActions()));
		}

		if (discountCategory.getCategory() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"category\": ");

			sb.append(String.valueOf(discountCategory.getCategory()));
		}

		if (discountCategory.getCategoryExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"categoryExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(discountCategory.getCategoryExternalReferenceCode()));

			sb.append("\"");
		}

		if (discountCategory.getCategoryId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"categoryId\": ");

			sb.append(discountCategory.getCategoryId());
		}

		if (discountCategory.getDiscountExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(discountCategory.getDiscountExternalReferenceCode()));

			sb.append("\"");
		}

		if (discountCategory.getDiscountId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountId\": ");

			sb.append(discountCategory.getDiscountId());
		}

		if (discountCategory.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(discountCategory.getId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DiscountCategoryJSONParser discountCategoryJSONParser =
			new DiscountCategoryJSONParser();

		return discountCategoryJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DiscountCategory discountCategory) {
		if (discountCategory == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (discountCategory.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(discountCategory.getActions()));
		}

		if (discountCategory.getCategory() == null) {
			map.put("category", null);
		}
		else {
			map.put("category", String.valueOf(discountCategory.getCategory()));
		}

		if (discountCategory.getCategoryExternalReferenceCode() == null) {
			map.put("categoryExternalReferenceCode", null);
		}
		else {
			map.put(
				"categoryExternalReferenceCode",
				String.valueOf(
					discountCategory.getCategoryExternalReferenceCode()));
		}

		if (discountCategory.getCategoryId() == null) {
			map.put("categoryId", null);
		}
		else {
			map.put(
				"categoryId", String.valueOf(discountCategory.getCategoryId()));
		}

		if (discountCategory.getDiscountExternalReferenceCode() == null) {
			map.put("discountExternalReferenceCode", null);
		}
		else {
			map.put(
				"discountExternalReferenceCode",
				String.valueOf(
					discountCategory.getDiscountExternalReferenceCode()));
		}

		if (discountCategory.getDiscountId() == null) {
			map.put("discountId", null);
		}
		else {
			map.put(
				"discountId", String.valueOf(discountCategory.getDiscountId()));
		}

		if (discountCategory.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(discountCategory.getId()));
		}

		return map;
	}

	public static class DiscountCategoryJSONParser
		extends BaseJSONParser<DiscountCategory> {

		@Override
		protected DiscountCategory createDTO() {
			return new DiscountCategory();
		}

		@Override
		protected DiscountCategory[] createDTOArray(int size) {
			return new DiscountCategory[size];
		}

		@Override
		protected void setField(
			DiscountCategory discountCategory, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					discountCategory.setActions(
						(Map)DiscountCategorySerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "category")) {
				if (jsonParserFieldValue != null) {
					discountCategory.setCategory(
						CategorySerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "categoryExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					discountCategory.setCategoryExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "categoryId")) {
				if (jsonParserFieldValue != null) {
					discountCategory.setCategoryId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "discountExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					discountCategory.setDiscountExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "discountId")) {
				if (jsonParserFieldValue != null) {
					discountCategory.setDiscountId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					discountCategory.setId(
						Long.valueOf((String)jsonParserFieldValue));
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