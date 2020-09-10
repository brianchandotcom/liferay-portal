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

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceModifierCategory;
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
public class PriceModifierCategorySerDes {

	public static PriceModifierCategory toDTO(String json) {
		PriceModifierCategoryJSONParser priceModifierCategoryJSONParser =
			new PriceModifierCategoryJSONParser();

		return priceModifierCategoryJSONParser.parseToDTO(json);
	}

	public static PriceModifierCategory[] toDTOs(String json) {
		PriceModifierCategoryJSONParser priceModifierCategoryJSONParser =
			new PriceModifierCategoryJSONParser();

		return priceModifierCategoryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PriceModifierCategory priceModifierCategory) {
		if (priceModifierCategory == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (priceModifierCategory.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(priceModifierCategory.getActions()));
		}

		if (priceModifierCategory.getCategory() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"category\": ");

			sb.append(String.valueOf(priceModifierCategory.getCategory()));
		}

		if (priceModifierCategory.getCategoryExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"categoryExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(
					priceModifierCategory.getCategoryExternalReferenceCode()));

			sb.append("\"");
		}

		if (priceModifierCategory.getCategoryId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"categoryId\": ");

			sb.append(priceModifierCategory.getCategoryId());
		}

		if (priceModifierCategory.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(priceModifierCategory.getId());
		}

		if (priceModifierCategory.getPriceModifierExternalReferenceCode() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceModifierExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(
					priceModifierCategory.
						getPriceModifierExternalReferenceCode()));

			sb.append("\"");
		}

		if (priceModifierCategory.getPriceModifierId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceModifierId\": ");

			sb.append(priceModifierCategory.getPriceModifierId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PriceModifierCategoryJSONParser priceModifierCategoryJSONParser =
			new PriceModifierCategoryJSONParser();

		return priceModifierCategoryJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PriceModifierCategory priceModifierCategory) {

		if (priceModifierCategory == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (priceModifierCategory.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put(
				"actions", String.valueOf(priceModifierCategory.getActions()));
		}

		if (priceModifierCategory.getCategory() == null) {
			map.put("category", null);
		}
		else {
			map.put(
				"category",
				String.valueOf(priceModifierCategory.getCategory()));
		}

		if (priceModifierCategory.getCategoryExternalReferenceCode() == null) {
			map.put("categoryExternalReferenceCode", null);
		}
		else {
			map.put(
				"categoryExternalReferenceCode",
				String.valueOf(
					priceModifierCategory.getCategoryExternalReferenceCode()));
		}

		if (priceModifierCategory.getCategoryId() == null) {
			map.put("categoryId", null);
		}
		else {
			map.put(
				"categoryId",
				String.valueOf(priceModifierCategory.getCategoryId()));
		}

		if (priceModifierCategory.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(priceModifierCategory.getId()));
		}

		if (priceModifierCategory.getPriceModifierExternalReferenceCode() ==
				null) {

			map.put("priceModifierExternalReferenceCode", null);
		}
		else {
			map.put(
				"priceModifierExternalReferenceCode",
				String.valueOf(
					priceModifierCategory.
						getPriceModifierExternalReferenceCode()));
		}

		if (priceModifierCategory.getPriceModifierId() == null) {
			map.put("priceModifierId", null);
		}
		else {
			map.put(
				"priceModifierId",
				String.valueOf(priceModifierCategory.getPriceModifierId()));
		}

		return map;
	}

	public static class PriceModifierCategoryJSONParser
		extends BaseJSONParser<PriceModifierCategory> {

		@Override
		protected PriceModifierCategory createDTO() {
			return new PriceModifierCategory();
		}

		@Override
		protected PriceModifierCategory[] createDTOArray(int size) {
			return new PriceModifierCategory[size];
		}

		@Override
		protected void setField(
			PriceModifierCategory priceModifierCategory,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					priceModifierCategory.setActions(
						(Map)PriceModifierCategorySerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "category")) {
				if (jsonParserFieldValue != null) {
					priceModifierCategory.setCategory(
						CategorySerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "categoryExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					priceModifierCategory.setCategoryExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "categoryId")) {
				if (jsonParserFieldValue != null) {
					priceModifierCategory.setCategoryId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					priceModifierCategory.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"priceModifierExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					priceModifierCategory.setPriceModifierExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priceModifierId")) {
				if (jsonParserFieldValue != null) {
					priceModifierCategory.setPriceModifierId(
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