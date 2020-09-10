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

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceListDiscount;
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
public class PriceListDiscountSerDes {

	public static PriceListDiscount toDTO(String json) {
		PriceListDiscountJSONParser priceListDiscountJSONParser =
			new PriceListDiscountJSONParser();

		return priceListDiscountJSONParser.parseToDTO(json);
	}

	public static PriceListDiscount[] toDTOs(String json) {
		PriceListDiscountJSONParser priceListDiscountJSONParser =
			new PriceListDiscountJSONParser();

		return priceListDiscountJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PriceListDiscount priceListDiscount) {
		if (priceListDiscount == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (priceListDiscount.getDiscountExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(priceListDiscount.getDiscountExternalReferenceCode()));

			sb.append("\"");
		}

		if (priceListDiscount.getDiscountId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountId\": ");

			sb.append(priceListDiscount.getDiscountId());
		}

		if (priceListDiscount.getDiscountName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discountName\": ");

			sb.append("\"");

			sb.append(_escape(priceListDiscount.getDiscountName()));

			sb.append("\"");
		}

		if (priceListDiscount.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(priceListDiscount.getId());
		}

		if (priceListDiscount.getOrder() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"order\": ");

			sb.append(priceListDiscount.getOrder());
		}

		if (priceListDiscount.getPriceListExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(
				_escape(priceListDiscount.getPriceListExternalReferenceCode()));

			sb.append("\"");
		}

		if (priceListDiscount.getPriceListId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListId\": ");

			sb.append(priceListDiscount.getPriceListId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PriceListDiscountJSONParser priceListDiscountJSONParser =
			new PriceListDiscountJSONParser();

		return priceListDiscountJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PriceListDiscount priceListDiscount) {

		if (priceListDiscount == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (priceListDiscount.getDiscountExternalReferenceCode() == null) {
			map.put("discountExternalReferenceCode", null);
		}
		else {
			map.put(
				"discountExternalReferenceCode",
				String.valueOf(
					priceListDiscount.getDiscountExternalReferenceCode()));
		}

		if (priceListDiscount.getDiscountId() == null) {
			map.put("discountId", null);
		}
		else {
			map.put(
				"discountId",
				String.valueOf(priceListDiscount.getDiscountId()));
		}

		if (priceListDiscount.getDiscountName() == null) {
			map.put("discountName", null);
		}
		else {
			map.put(
				"discountName",
				String.valueOf(priceListDiscount.getDiscountName()));
		}

		if (priceListDiscount.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(priceListDiscount.getId()));
		}

		if (priceListDiscount.getOrder() == null) {
			map.put("order", null);
		}
		else {
			map.put("order", String.valueOf(priceListDiscount.getOrder()));
		}

		if (priceListDiscount.getPriceListExternalReferenceCode() == null) {
			map.put("priceListExternalReferenceCode", null);
		}
		else {
			map.put(
				"priceListExternalReferenceCode",
				String.valueOf(
					priceListDiscount.getPriceListExternalReferenceCode()));
		}

		if (priceListDiscount.getPriceListId() == null) {
			map.put("priceListId", null);
		}
		else {
			map.put(
				"priceListId",
				String.valueOf(priceListDiscount.getPriceListId()));
		}

		return map;
	}

	public static class PriceListDiscountJSONParser
		extends BaseJSONParser<PriceListDiscount> {

		@Override
		protected PriceListDiscount createDTO() {
			return new PriceListDiscount();
		}

		@Override
		protected PriceListDiscount[] createDTOArray(int size) {
			return new PriceListDiscount[size];
		}

		@Override
		protected void setField(
			PriceListDiscount priceListDiscount, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "discountExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					priceListDiscount.setDiscountExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "discountId")) {
				if (jsonParserFieldValue != null) {
					priceListDiscount.setDiscountId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "discountName")) {
				if (jsonParserFieldValue != null) {
					priceListDiscount.setDiscountName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					priceListDiscount.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "order")) {
				if (jsonParserFieldValue != null) {
					priceListDiscount.setOrder(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"priceListExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					priceListDiscount.setPriceListExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priceListId")) {
				if (jsonParserFieldValue != null) {
					priceListDiscount.setPriceListId(
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