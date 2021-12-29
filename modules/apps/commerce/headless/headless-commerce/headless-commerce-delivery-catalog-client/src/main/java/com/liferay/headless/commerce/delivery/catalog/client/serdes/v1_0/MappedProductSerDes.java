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

import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.MappedProduct;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.ProductOption;
import com.liferay.headless.commerce.delivery.catalog.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class MappedProductSerDes {

	public static MappedProduct toDTO(String json) {
		MappedProductJSONParser mappedProductJSONParser =
			new MappedProductJSONParser();

		return mappedProductJSONParser.parseToDTO(json);
	}

	public static MappedProduct[] toDTOs(String json) {
		MappedProductJSONParser mappedProductJSONParser =
			new MappedProductJSONParser();

		return mappedProductJSONParser.parseToDTOs(json);
	}

	public static String toJSON(MappedProduct mappedProduct) {
		if (mappedProduct == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (mappedProduct.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(mappedProduct.getActions()));
		}

		if (mappedProduct.getAvailability() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"availability\": ");

			sb.append(String.valueOf(mappedProduct.getAvailability()));
		}

		if (mappedProduct.getFirstAvailableReplacementMappedProduct() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"firstAvailableReplacementMappedProduct\": ");

			sb.append(
				String.valueOf(
					mappedProduct.getFirstAvailableReplacementMappedProduct()));
		}

		if (mappedProduct.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(mappedProduct.getId());
		}

		if (mappedProduct.getOptions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"options\": ");

			sb.append(_toJSON(mappedProduct.getOptions()));
		}

		if (mappedProduct.getPrice() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"price\": ");

			sb.append(String.valueOf(mappedProduct.getPrice()));
		}

		if (mappedProduct.getProductConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productConfiguration\": ");

			sb.append(String.valueOf(mappedProduct.getProductConfiguration()));
		}

		if (mappedProduct.getProductExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(mappedProduct.getProductExternalReferenceCode()));

			sb.append("\"");
		}

		if (mappedProduct.getProductId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productId\": ");

			sb.append(mappedProduct.getProductId());
		}

		if (mappedProduct.getProductName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productName\": ");

			sb.append(_toJSON(mappedProduct.getProductName()));
		}

		if (mappedProduct.getProductOptions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productOptions\": ");

			sb.append("[");

			for (int i = 0; i < mappedProduct.getProductOptions().length; i++) {
				sb.append(String.valueOf(mappedProduct.getProductOptions()[i]));

				if ((i + 1) < mappedProduct.getProductOptions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (mappedProduct.getPurchasable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"purchasable\": ");

			sb.append(mappedProduct.getPurchasable());
		}

		if (mappedProduct.getQuantity() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"quantity\": ");

			sb.append(mappedProduct.getQuantity());
		}

		if (mappedProduct.getReplacementMappedProduct() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"replacementMappedProduct\": ");

			sb.append(
				String.valueOf(mappedProduct.getReplacementMappedProduct()));
		}

		if (mappedProduct.getReplacementMessage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"replacementMessage\": ");

			sb.append("\"");

			sb.append(_escape(mappedProduct.getReplacementMessage()));

			sb.append("\"");
		}

		if (mappedProduct.getSequence() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sequence\": ");

			sb.append("\"");

			sb.append(_escape(mappedProduct.getSequence()));

			sb.append("\"");
		}

		if (mappedProduct.getSku() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sku\": ");

			sb.append("\"");

			sb.append(_escape(mappedProduct.getSku()));

			sb.append("\"");
		}

		if (mappedProduct.getSkuExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(mappedProduct.getSkuExternalReferenceCode()));

			sb.append("\"");
		}

		if (mappedProduct.getSkuId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuId\": ");

			sb.append(mappedProduct.getSkuId());
		}

		if (mappedProduct.getThumbnail() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"thumbnail\": ");

			sb.append("\"");

			sb.append(_escape(mappedProduct.getThumbnail()));

			sb.append("\"");
		}

		if (mappedProduct.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(mappedProduct.getType());

			sb.append("\"");
		}

		if (mappedProduct.getUrls() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"urls\": ");

			sb.append(_toJSON(mappedProduct.getUrls()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		MappedProductJSONParser mappedProductJSONParser =
			new MappedProductJSONParser();

		return mappedProductJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(MappedProduct mappedProduct) {
		if (mappedProduct == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (mappedProduct.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(mappedProduct.getActions()));
		}

		if (mappedProduct.getAvailability() == null) {
			map.put("availability", null);
		}
		else {
			map.put(
				"availability",
				String.valueOf(mappedProduct.getAvailability()));
		}

		if (mappedProduct.getFirstAvailableReplacementMappedProduct() == null) {
			map.put("firstAvailableReplacementMappedProduct", null);
		}
		else {
			map.put(
				"firstAvailableReplacementMappedProduct",
				String.valueOf(
					mappedProduct.getFirstAvailableReplacementMappedProduct()));
		}

		if (mappedProduct.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(mappedProduct.getId()));
		}

		if (mappedProduct.getOptions() == null) {
			map.put("options", null);
		}
		else {
			map.put("options", String.valueOf(mappedProduct.getOptions()));
		}

		if (mappedProduct.getPrice() == null) {
			map.put("price", null);
		}
		else {
			map.put("price", String.valueOf(mappedProduct.getPrice()));
		}

		if (mappedProduct.getProductConfiguration() == null) {
			map.put("productConfiguration", null);
		}
		else {
			map.put(
				"productConfiguration",
				String.valueOf(mappedProduct.getProductConfiguration()));
		}

		if (mappedProduct.getProductExternalReferenceCode() == null) {
			map.put("productExternalReferenceCode", null);
		}
		else {
			map.put(
				"productExternalReferenceCode",
				String.valueOf(
					mappedProduct.getProductExternalReferenceCode()));
		}

		if (mappedProduct.getProductId() == null) {
			map.put("productId", null);
		}
		else {
			map.put("productId", String.valueOf(mappedProduct.getProductId()));
		}

		if (mappedProduct.getProductName() == null) {
			map.put("productName", null);
		}
		else {
			map.put(
				"productName", String.valueOf(mappedProduct.getProductName()));
		}

		if (mappedProduct.getProductOptions() == null) {
			map.put("productOptions", null);
		}
		else {
			map.put(
				"productOptions",
				String.valueOf(mappedProduct.getProductOptions()));
		}

		if (mappedProduct.getPurchasable() == null) {
			map.put("purchasable", null);
		}
		else {
			map.put(
				"purchasable", String.valueOf(mappedProduct.getPurchasable()));
		}

		if (mappedProduct.getQuantity() == null) {
			map.put("quantity", null);
		}
		else {
			map.put("quantity", String.valueOf(mappedProduct.getQuantity()));
		}

		if (mappedProduct.getReplacementMappedProduct() == null) {
			map.put("replacementMappedProduct", null);
		}
		else {
			map.put(
				"replacementMappedProduct",
				String.valueOf(mappedProduct.getReplacementMappedProduct()));
		}

		if (mappedProduct.getReplacementMessage() == null) {
			map.put("replacementMessage", null);
		}
		else {
			map.put(
				"replacementMessage",
				String.valueOf(mappedProduct.getReplacementMessage()));
		}

		if (mappedProduct.getSequence() == null) {
			map.put("sequence", null);
		}
		else {
			map.put("sequence", String.valueOf(mappedProduct.getSequence()));
		}

		if (mappedProduct.getSku() == null) {
			map.put("sku", null);
		}
		else {
			map.put("sku", String.valueOf(mappedProduct.getSku()));
		}

		if (mappedProduct.getSkuExternalReferenceCode() == null) {
			map.put("skuExternalReferenceCode", null);
		}
		else {
			map.put(
				"skuExternalReferenceCode",
				String.valueOf(mappedProduct.getSkuExternalReferenceCode()));
		}

		if (mappedProduct.getSkuId() == null) {
			map.put("skuId", null);
		}
		else {
			map.put("skuId", String.valueOf(mappedProduct.getSkuId()));
		}

		if (mappedProduct.getThumbnail() == null) {
			map.put("thumbnail", null);
		}
		else {
			map.put("thumbnail", String.valueOf(mappedProduct.getThumbnail()));
		}

		if (mappedProduct.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(mappedProduct.getType()));
		}

		if (mappedProduct.getUrls() == null) {
			map.put("urls", null);
		}
		else {
			map.put("urls", String.valueOf(mappedProduct.getUrls()));
		}

		return map;
	}

	public static class MappedProductJSONParser
		extends BaseJSONParser<MappedProduct> {

		@Override
		protected MappedProduct createDTO() {
			return new MappedProduct();
		}

		@Override
		protected MappedProduct[] createDTOArray(int size) {
			return new MappedProduct[size];
		}

		@Override
		protected void setField(
			MappedProduct mappedProduct, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					mappedProduct.setActions(
						(Map)MappedProductSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "availability")) {
				if (jsonParserFieldValue != null) {
					mappedProduct.setAvailability(
						AvailabilitySerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"firstAvailableReplacementMappedProduct")) {

				if (jsonParserFieldValue != null) {
					mappedProduct.setFirstAvailableReplacementMappedProduct(
						MappedProductSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					mappedProduct.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "options")) {
				if (jsonParserFieldValue != null) {
					mappedProduct.setOptions(
						(Map)MappedProductSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "price")) {
				if (jsonParserFieldValue != null) {
					mappedProduct.setPrice(
						PriceSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "productConfiguration")) {

				if (jsonParserFieldValue != null) {
					mappedProduct.setProductConfiguration(
						ProductConfigurationSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "productExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					mappedProduct.setProductExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productId")) {
				if (jsonParserFieldValue != null) {
					mappedProduct.setProductId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productName")) {
				if (jsonParserFieldValue != null) {
					mappedProduct.setProductName(
						(Map)MappedProductSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productOptions")) {
				if (jsonParserFieldValue != null) {
					mappedProduct.setProductOptions(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ProductOptionSerDes.toDTO((String)object)
						).toArray(
							size -> new ProductOption[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "purchasable")) {
				if (jsonParserFieldValue != null) {
					mappedProduct.setPurchasable((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "quantity")) {
				if (jsonParserFieldValue != null) {
					mappedProduct.setQuantity(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "replacementMappedProduct")) {

				if (jsonParserFieldValue != null) {
					mappedProduct.setReplacementMappedProduct(
						MappedProductSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "replacementMessage")) {

				if (jsonParserFieldValue != null) {
					mappedProduct.setReplacementMessage(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sequence")) {
				if (jsonParserFieldValue != null) {
					mappedProduct.setSequence((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sku")) {
				if (jsonParserFieldValue != null) {
					mappedProduct.setSku((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "skuExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					mappedProduct.setSkuExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "skuId")) {
				if (jsonParserFieldValue != null) {
					mappedProduct.setSkuId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "thumbnail")) {
				if (jsonParserFieldValue != null) {
					mappedProduct.setThumbnail((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					mappedProduct.setType(
						MappedProduct.Type.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "urls")) {
				if (jsonParserFieldValue != null) {
					mappedProduct.setUrls(
						(Map)MappedProductSerDes.toMap(
							(String)jsonParserFieldValue));
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