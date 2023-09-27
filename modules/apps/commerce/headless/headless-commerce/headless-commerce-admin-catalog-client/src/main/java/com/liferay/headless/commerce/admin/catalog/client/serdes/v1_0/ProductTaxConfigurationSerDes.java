/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.client.serdes.v1_0;

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductTaxConfiguration;
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
public class ProductTaxConfigurationSerDes {

	public static ProductTaxConfiguration toDTO(String json) {
		ProductTaxConfigurationJSONParser productTaxConfigurationJSONParser =
			new ProductTaxConfigurationJSONParser();

		return productTaxConfigurationJSONParser.parseToDTO(json);
	}

	public static ProductTaxConfiguration[] toDTOs(String json) {
		ProductTaxConfigurationJSONParser productTaxConfigurationJSONParser =
			new ProductTaxConfigurationJSONParser();

		return productTaxConfigurationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ProductTaxConfiguration productTaxConfiguration) {

		if (productTaxConfiguration == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (productTaxConfiguration.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(productTaxConfiguration.getId());
		}

		if (productTaxConfiguration.getTaxCategory() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxCategory\": ");

			sb.append("\"");

			sb.append(_escape(productTaxConfiguration.getTaxCategory()));

			sb.append("\"");
		}

		if (productTaxConfiguration.getTaxable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxable\": ");

			sb.append(productTaxConfiguration.getTaxable());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ProductTaxConfigurationJSONParser productTaxConfigurationJSONParser =
			new ProductTaxConfigurationJSONParser();

		return productTaxConfigurationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ProductTaxConfiguration productTaxConfiguration) {

		if (productTaxConfiguration == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (productTaxConfiguration.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(productTaxConfiguration.getId()));
		}

		if (productTaxConfiguration.getTaxCategory() == null) {
			map.put("taxCategory", null);
		}
		else {
			map.put(
				"taxCategory",
				String.valueOf(productTaxConfiguration.getTaxCategory()));
		}

		if (productTaxConfiguration.getTaxable() == null) {
			map.put("taxable", null);
		}
		else {
			map.put(
				"taxable",
				String.valueOf(productTaxConfiguration.getTaxable()));
		}

		return map;
	}

	public static class ProductTaxConfigurationJSONParser
		extends BaseJSONParser<ProductTaxConfiguration> {

		@Override
		protected ProductTaxConfiguration createDTO() {
			return new ProductTaxConfiguration();
		}

		@Override
		protected ProductTaxConfiguration[] createDTOArray(int size) {
			return new ProductTaxConfiguration[size];
		}

		@Override
		protected void setField(
			ProductTaxConfiguration productTaxConfiguration,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					productTaxConfiguration.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "taxCategory")) {
				if (jsonParserFieldValue != null) {
					productTaxConfiguration.setTaxCategory(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "taxable")) {
				if (jsonParserFieldValue != null) {
					productTaxConfiguration.setTaxable(
						(Boolean)jsonParserFieldValue);
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