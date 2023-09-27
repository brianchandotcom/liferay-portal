/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0;

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.ProductGroup;
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

		if (productGroup.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(productGroup.getId());
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

		if (productGroup.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(productGroup.getId()));
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

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					productGroup.setId(
						Long.valueOf((String)jsonParserFieldValue));
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