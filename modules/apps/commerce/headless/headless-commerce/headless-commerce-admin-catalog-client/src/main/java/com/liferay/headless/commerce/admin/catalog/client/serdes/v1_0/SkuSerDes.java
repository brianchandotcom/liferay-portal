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

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Sku;
import com.liferay.headless.commerce.admin.catalog.client.json.BaseJSONParser;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
public class SkuSerDes {

	public static Sku toDTO(String json) {
		SkuJSONParser skuJSONParser = new SkuJSONParser();

		return skuJSONParser.parseToDTO(json);
	}

	public static Sku[] toDTOs(String json) {
		SkuJSONParser skuJSONParser = new SkuJSONParser();

		return skuJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Sku sku) {
		if (sku == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (sku.getCost() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"cost\": ");

			sb.append(sku.getCost());
		}

		if (sku.getDepth() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"depth\": ");

			sb.append(sku.getDepth());
		}

		if (sku.getDiscontinued() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discontinued\": ");

			sb.append(sku.getDiscontinued());
		}

		if (sku.getDiscontinuedDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"discontinuedDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(sku.getDiscontinuedDate()));

			sb.append("\"");
		}

		if (sku.getDisplayDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(sku.getDisplayDate()));

			sb.append("\"");
		}

		if (sku.getExpirationDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expirationDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(sku.getExpirationDate()));

			sb.append("\"");
		}

		if (sku.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(sku.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (sku.getGtin() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"gtin\": ");

			sb.append("\"");

			sb.append(_escape(sku.getGtin()));

			sb.append("\"");
		}

		if (sku.getHeight() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"height\": ");

			sb.append(sku.getHeight());
		}

		if (sku.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(sku.getId());
		}

		if (sku.getInventoryLevel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inventoryLevel\": ");

			sb.append(sku.getInventoryLevel());
		}

		if (sku.getManufacturerPartNumber() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"manufacturerPartNumber\": ");

			sb.append("\"");

			sb.append(_escape(sku.getManufacturerPartNumber()));

			sb.append("\"");
		}

		if (sku.getNeverExpire() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"neverExpire\": ");

			sb.append(sku.getNeverExpire());
		}

		if (sku.getOptions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"options\": ");

			sb.append(_toJSON(sku.getOptions()));
		}

		if (sku.getPrice() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"price\": ");

			sb.append(sku.getPrice());
		}

		if (sku.getProductId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productId\": ");

			sb.append(sku.getProductId());
		}

		if (sku.getProductName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productName\": ");

			sb.append(_toJSON(sku.getProductName()));
		}

		if (sku.getPromoPrice() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"promoPrice\": ");

			sb.append(sku.getPromoPrice());
		}

		if (sku.getPublished() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"published\": ");

			sb.append(sku.getPublished());
		}

		if (sku.getPurchasable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"purchasable\": ");

			sb.append(sku.getPurchasable());
		}

		if (sku.getReplacementSkuExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"replacementSkuExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(sku.getReplacementSkuExternalReferenceCode()));

			sb.append("\"");
		}

		if (sku.getReplacementSkuId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"replacementSkuId\": ");

			sb.append(sku.getReplacementSkuId());
		}

		if (sku.getSku() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sku\": ");

			sb.append("\"");

			sb.append(_escape(sku.getSku()));

			sb.append("\"");
		}

		if (sku.getUnspsc() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"unspsc\": ");

			sb.append("\"");

			sb.append(_escape(sku.getUnspsc()));

			sb.append("\"");
		}

		if (sku.getWeight() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"weight\": ");

			sb.append(sku.getWeight());
		}

		if (sku.getWidth() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"width\": ");

			sb.append(sku.getWidth());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SkuJSONParser skuJSONParser = new SkuJSONParser();

		return skuJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Sku sku) {
		if (sku == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (sku.getCost() == null) {
			map.put("cost", null);
		}
		else {
			map.put("cost", String.valueOf(sku.getCost()));
		}

		if (sku.getDepth() == null) {
			map.put("depth", null);
		}
		else {
			map.put("depth", String.valueOf(sku.getDepth()));
		}

		if (sku.getDiscontinued() == null) {
			map.put("discontinued", null);
		}
		else {
			map.put("discontinued", String.valueOf(sku.getDiscontinued()));
		}

		if (sku.getDiscontinuedDate() == null) {
			map.put("discontinuedDate", null);
		}
		else {
			map.put(
				"discontinuedDate",
				liferayToJSONDateFormat.format(sku.getDiscontinuedDate()));
		}

		if (sku.getDisplayDate() == null) {
			map.put("displayDate", null);
		}
		else {
			map.put(
				"displayDate",
				liferayToJSONDateFormat.format(sku.getDisplayDate()));
		}

		if (sku.getExpirationDate() == null) {
			map.put("expirationDate", null);
		}
		else {
			map.put(
				"expirationDate",
				liferayToJSONDateFormat.format(sku.getExpirationDate()));
		}

		if (sku.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(sku.getExternalReferenceCode()));
		}

		if (sku.getGtin() == null) {
			map.put("gtin", null);
		}
		else {
			map.put("gtin", String.valueOf(sku.getGtin()));
		}

		if (sku.getHeight() == null) {
			map.put("height", null);
		}
		else {
			map.put("height", String.valueOf(sku.getHeight()));
		}

		if (sku.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(sku.getId()));
		}

		if (sku.getInventoryLevel() == null) {
			map.put("inventoryLevel", null);
		}
		else {
			map.put("inventoryLevel", String.valueOf(sku.getInventoryLevel()));
		}

		if (sku.getManufacturerPartNumber() == null) {
			map.put("manufacturerPartNumber", null);
		}
		else {
			map.put(
				"manufacturerPartNumber",
				String.valueOf(sku.getManufacturerPartNumber()));
		}

		if (sku.getNeverExpire() == null) {
			map.put("neverExpire", null);
		}
		else {
			map.put("neverExpire", String.valueOf(sku.getNeverExpire()));
		}

		if (sku.getOptions() == null) {
			map.put("options", null);
		}
		else {
			map.put("options", String.valueOf(sku.getOptions()));
		}

		if (sku.getPrice() == null) {
			map.put("price", null);
		}
		else {
			map.put("price", String.valueOf(sku.getPrice()));
		}

		if (sku.getProductId() == null) {
			map.put("productId", null);
		}
		else {
			map.put("productId", String.valueOf(sku.getProductId()));
		}

		if (sku.getProductName() == null) {
			map.put("productName", null);
		}
		else {
			map.put("productName", String.valueOf(sku.getProductName()));
		}

		if (sku.getPromoPrice() == null) {
			map.put("promoPrice", null);
		}
		else {
			map.put("promoPrice", String.valueOf(sku.getPromoPrice()));
		}

		if (sku.getPublished() == null) {
			map.put("published", null);
		}
		else {
			map.put("published", String.valueOf(sku.getPublished()));
		}

		if (sku.getPurchasable() == null) {
			map.put("purchasable", null);
		}
		else {
			map.put("purchasable", String.valueOf(sku.getPurchasable()));
		}

		if (sku.getReplacementSkuExternalReferenceCode() == null) {
			map.put("replacementSkuExternalReferenceCode", null);
		}
		else {
			map.put(
				"replacementSkuExternalReferenceCode",
				String.valueOf(sku.getReplacementSkuExternalReferenceCode()));
		}

		if (sku.getReplacementSkuId() == null) {
			map.put("replacementSkuId", null);
		}
		else {
			map.put(
				"replacementSkuId", String.valueOf(sku.getReplacementSkuId()));
		}

		if (sku.getSku() == null) {
			map.put("sku", null);
		}
		else {
			map.put("sku", String.valueOf(sku.getSku()));
		}

		if (sku.getUnspsc() == null) {
			map.put("unspsc", null);
		}
		else {
			map.put("unspsc", String.valueOf(sku.getUnspsc()));
		}

		if (sku.getWeight() == null) {
			map.put("weight", null);
		}
		else {
			map.put("weight", String.valueOf(sku.getWeight()));
		}

		if (sku.getWidth() == null) {
			map.put("width", null);
		}
		else {
			map.put("width", String.valueOf(sku.getWidth()));
		}

		return map;
	}

	public static class SkuJSONParser extends BaseJSONParser<Sku> {

		@Override
		protected Sku createDTO() {
			return new Sku();
		}

		@Override
		protected Sku[] createDTOArray(int size) {
			return new Sku[size];
		}

		@Override
		protected void setField(
			Sku sku, String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "cost")) {
				if (jsonParserFieldValue != null) {
					sku.setCost(new BigDecimal((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "depth")) {
				if (jsonParserFieldValue != null) {
					sku.setDepth(Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "discontinued")) {
				if (jsonParserFieldValue != null) {
					sku.setDiscontinued((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "discontinuedDate")) {
				if (jsonParserFieldValue != null) {
					sku.setDiscontinuedDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "displayDate")) {
				if (jsonParserFieldValue != null) {
					sku.setDisplayDate(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "expirationDate")) {
				if (jsonParserFieldValue != null) {
					sku.setExpirationDate(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					sku.setExternalReferenceCode((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "gtin")) {
				if (jsonParserFieldValue != null) {
					sku.setGtin((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "height")) {
				if (jsonParserFieldValue != null) {
					sku.setHeight(Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					sku.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "inventoryLevel")) {
				if (jsonParserFieldValue != null) {
					sku.setInventoryLevel(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "manufacturerPartNumber")) {

				if (jsonParserFieldValue != null) {
					sku.setManufacturerPartNumber((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "neverExpire")) {
				if (jsonParserFieldValue != null) {
					sku.setNeverExpire((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "options")) {
				if (jsonParserFieldValue != null) {
					sku.setOptions(
						(Map)SkuSerDes.toMap((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "price")) {
				if (jsonParserFieldValue != null) {
					sku.setPrice(new BigDecimal((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productId")) {
				if (jsonParserFieldValue != null) {
					sku.setProductId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productName")) {
				if (jsonParserFieldValue != null) {
					sku.setProductName(
						(Map)SkuSerDes.toMap((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "promoPrice")) {
				if (jsonParserFieldValue != null) {
					sku.setPromoPrice(
						new BigDecimal((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "published")) {
				if (jsonParserFieldValue != null) {
					sku.setPublished((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "purchasable")) {
				if (jsonParserFieldValue != null) {
					sku.setPurchasable((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"replacementSkuExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					sku.setReplacementSkuExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "replacementSkuId")) {
				if (jsonParserFieldValue != null) {
					sku.setReplacementSkuId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sku")) {
				if (jsonParserFieldValue != null) {
					sku.setSku((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "unspsc")) {
				if (jsonParserFieldValue != null) {
					sku.setUnspsc((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "weight")) {
				if (jsonParserFieldValue != null) {
					sku.setWeight(Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "width")) {
				if (jsonParserFieldValue != null) {
					sku.setWidth(Double.valueOf((String)jsonParserFieldValue));
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