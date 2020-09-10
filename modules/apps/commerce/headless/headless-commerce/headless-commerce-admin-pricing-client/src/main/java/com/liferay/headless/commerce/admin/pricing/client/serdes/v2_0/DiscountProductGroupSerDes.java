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

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.DiscountProductGroup;
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
public class DiscountProductGroupSerDes {

	public static DiscountProductGroup toDTO(String json) {
		DiscountProductGroupJSONParser discountProductGroupJSONParser =
			new DiscountProductGroupJSONParser();

		return discountProductGroupJSONParser.parseToDTO(json);
	}

	public static DiscountProductGroup[] toDTOs(String json) {
		DiscountProductGroupJSONParser discountProductGroupJSONParser =
			new DiscountProductGroupJSONParser();

		return discountProductGroupJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DiscountProductGroup discountProductGroup) {
		if (discountProductGroup == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (discountProductGroup.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(discountProductGroup.getActions()));
		}

		if (discountProductGroup.getDiscountExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(
					discountProductGroup.getDiscountExternalReferenceCode()));

			sb.append("\"");
		}

		if (discountProductGroup.getDiscountId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountId\": ");

			sb.append(discountProductGroup.getDiscountId());
		}

		if (discountProductGroup.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(discountProductGroup.getId());
		}

		if (discountProductGroup.getProductGroup() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productGroup\": ");

			sb.append(String.valueOf(discountProductGroup.getProductGroup()));
		}

		if (discountProductGroup.getProductGroupExternalReferenceCode() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productGroupExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(
					discountProductGroup.
						getProductGroupExternalReferenceCode()));

			sb.append("\"");
		}

		if (discountProductGroup.getProductGroupId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productGroupId\": ");

			sb.append(discountProductGroup.getProductGroupId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DiscountProductGroupJSONParser discountProductGroupJSONParser =
			new DiscountProductGroupJSONParser();

		return discountProductGroupJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DiscountProductGroup discountProductGroup) {

		if (discountProductGroup == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (discountProductGroup.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put(
				"actions", String.valueOf(discountProductGroup.getActions()));
		}

		if (discountProductGroup.getDiscountExternalReferenceCode() == null) {
			map.put("discountExternalReferenceCode", null);
		}
		else {
			map.put(
				"discountExternalReferenceCode",
				String.valueOf(
					discountProductGroup.getDiscountExternalReferenceCode()));
		}

		if (discountProductGroup.getDiscountId() == null) {
			map.put("discountId", null);
		}
		else {
			map.put(
				"discountId",
				String.valueOf(discountProductGroup.getDiscountId()));
		}

		if (discountProductGroup.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(discountProductGroup.getId()));
		}

		if (discountProductGroup.getProductGroup() == null) {
			map.put("productGroup", null);
		}
		else {
			map.put(
				"productGroup",
				String.valueOf(discountProductGroup.getProductGroup()));
		}

		if (discountProductGroup.getProductGroupExternalReferenceCode() ==
				null) {

			map.put("productGroupExternalReferenceCode", null);
		}
		else {
			map.put(
				"productGroupExternalReferenceCode",
				String.valueOf(
					discountProductGroup.
						getProductGroupExternalReferenceCode()));
		}

		if (discountProductGroup.getProductGroupId() == null) {
			map.put("productGroupId", null);
		}
		else {
			map.put(
				"productGroupId",
				String.valueOf(discountProductGroup.getProductGroupId()));
		}

		return map;
	}

	public static class DiscountProductGroupJSONParser
		extends BaseJSONParser<DiscountProductGroup> {

		@Override
		protected DiscountProductGroup createDTO() {
			return new DiscountProductGroup();
		}

		@Override
		protected DiscountProductGroup[] createDTOArray(int size) {
			return new DiscountProductGroup[size];
		}

		@Override
		protected void setField(
			DiscountProductGroup discountProductGroup,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					discountProductGroup.setActions(
						(Map)DiscountProductGroupSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "discountExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					discountProductGroup.setDiscountExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "discountId")) {
				if (jsonParserFieldValue != null) {
					discountProductGroup.setDiscountId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					discountProductGroup.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productGroup")) {
				if (jsonParserFieldValue != null) {
					discountProductGroup.setProductGroup(
						ProductGroupSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"productGroupExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					discountProductGroup.setProductGroupExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productGroupId")) {
				if (jsonParserFieldValue != null) {
					discountProductGroup.setProductGroupId(
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