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

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductGroup;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductGroupProduct;
import com.liferay.headless.commerce.admin.catalog.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class ProductGroupSerDes {

	public static ProductGroup toDTO(String json) {
		ProductGroupJSONParser productGroupJSONParser =
			new ProductGroupJSONParser();

		return productGroupJSONParser.parseToDTO(json);
	}

	public static ProductGroup[] toDTOs(String json) {
		ProductGroupJSONParser productGroupJSONParser =
			new ProductGroupJSONParser();

		return productGroupJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ProductGroup productGroup) {
		if (productGroup == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (productGroup.getCustomFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append(_toJSON(productGroup.getCustomFields()));
		}

		if (productGroup.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append(_toJSON(productGroup.getDescription()));
		}

		if (productGroup.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(productGroup.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (productGroup.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(productGroup.getId());
		}

		if (productGroup.getProducts() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"products\": ");

			sb.append("[");

			for (int i = 0; i < productGroup.getProducts().length; i++) {
				sb.append(String.valueOf(productGroup.getProducts()[i]));

				if ((i + 1) < productGroup.getProducts().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (productGroup.getProductsCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productsCount\": ");

			sb.append(productGroup.getProductsCount());
		}

		if (productGroup.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append(_toJSON(productGroup.getTitle()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ProductGroupJSONParser productGroupJSONParser =
			new ProductGroupJSONParser();

		return productGroupJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ProductGroup productGroup) {
		if (productGroup == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (productGroup.getCustomFields() == null) {
			map.put("customFields", null);
		}
		else {
			map.put(
				"customFields", String.valueOf(productGroup.getCustomFields()));
		}

		if (productGroup.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description", String.valueOf(productGroup.getDescription()));
		}

		if (productGroup.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(productGroup.getExternalReferenceCode()));
		}

		if (productGroup.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(productGroup.getId()));
		}

		if (productGroup.getProducts() == null) {
			map.put("products", null);
		}
		else {
			map.put("products", String.valueOf(productGroup.getProducts()));
		}

		if (productGroup.getProductsCount() == null) {
			map.put("productsCount", null);
		}
		else {
			map.put(
				"productsCount",
				String.valueOf(productGroup.getProductsCount()));
		}

		if (productGroup.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(productGroup.getTitle()));
		}

		return map;
	}

	public static class ProductGroupJSONParser
		extends BaseJSONParser<ProductGroup> {

		@Override
		protected ProductGroup createDTO() {
			return new ProductGroup();
		}

		@Override
		protected ProductGroup[] createDTOArray(int size) {
			return new ProductGroup[size];
		}

		@Override
		protected void setField(
			ProductGroup productGroup, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "customFields")) {
				if (jsonParserFieldValue != null) {
					productGroup.setCustomFields(
						(Map)ProductGroupSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					productGroup.setDescription(
						(Map)ProductGroupSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					productGroup.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					productGroup.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "products")) {
				if (jsonParserFieldValue != null) {
					productGroup.setProducts(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ProductGroupProductSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ProductGroupProduct[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "productsCount")) {
				if (jsonParserFieldValue != null) {
					productGroup.setProductsCount(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					productGroup.setTitle(
						(Map)ProductGroupSerDes.toMap(
							(String)jsonParserFieldValue));
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