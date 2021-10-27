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

import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.ProductConfiguration;
import com.liferay.headless.commerce.delivery.catalog.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class ProductConfigurationSerDes {

	public static ProductConfiguration toDTO(String json) {
		ProductConfigurationJSONParser productConfigurationJSONParser =
			new ProductConfigurationJSONParser();

		return productConfigurationJSONParser.parseToDTO(json);
	}

	public static ProductConfiguration[] toDTOs(String json) {
		ProductConfigurationJSONParser productConfigurationJSONParser =
			new ProductConfigurationJSONParser();

		return productConfigurationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ProductConfiguration productConfiguration) {
		if (productConfiguration == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (productConfiguration.getAllowBackOrder() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"allowBackOrder\": ");

			sb.append(productConfiguration.getAllowBackOrder());
		}

		if (productConfiguration.getAllowedOrderQuantities() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"allowedOrderQuantities\": ");

			sb.append("[");

			for (int i = 0;
				 i < productConfiguration.getAllowedOrderQuantities().length;
				 i++) {

				sb.append(productConfiguration.getAllowedOrderQuantities()[i]);

				if ((i + 1) <
						productConfiguration.
							getAllowedOrderQuantities().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (productConfiguration.getDisplayAvailability() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayAvailability\": ");

			sb.append(productConfiguration.getDisplayAvailability());
		}

		if (productConfiguration.getDisplayStockQuantity() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayStockQuantity\": ");

			sb.append(productConfiguration.getDisplayStockQuantity());
		}

		if (productConfiguration.getInventoryEngine() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inventoryEngine\": ");

			sb.append("\"");

			sb.append(_escape(productConfiguration.getInventoryEngine()));

			sb.append("\"");
		}

		if (productConfiguration.getLowStockAction() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"lowStockAction\": ");

			sb.append("\"");

			sb.append(_escape(productConfiguration.getLowStockAction()));

			sb.append("\"");
		}

		if (productConfiguration.getMaxOrderQuantity() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxOrderQuantity\": ");

			sb.append(productConfiguration.getMaxOrderQuantity());
		}

		if (productConfiguration.getMinOrderQuantity() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minOrderQuantity\": ");

			sb.append(productConfiguration.getMinOrderQuantity());
		}

		if (productConfiguration.getMinStockQuantity() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"minStockQuantity\": ");

			sb.append(productConfiguration.getMinStockQuantity());
		}

		if (productConfiguration.getMultipleOrderQuantity() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"multipleOrderQuantity\": ");

			sb.append(productConfiguration.getMultipleOrderQuantity());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ProductConfigurationJSONParser productConfigurationJSONParser =
			new ProductConfigurationJSONParser();

		return productConfigurationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ProductConfiguration productConfiguration) {

		if (productConfiguration == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (productConfiguration.getAllowBackOrder() == null) {
			map.put("allowBackOrder", null);
		}
		else {
			map.put(
				"allowBackOrder",
				String.valueOf(productConfiguration.getAllowBackOrder()));
		}

		if (productConfiguration.getAllowedOrderQuantities() == null) {
			map.put("allowedOrderQuantities", null);
		}
		else {
			map.put(
				"allowedOrderQuantities",
				String.valueOf(
					productConfiguration.getAllowedOrderQuantities()));
		}

		if (productConfiguration.getDisplayAvailability() == null) {
			map.put("displayAvailability", null);
		}
		else {
			map.put(
				"displayAvailability",
				String.valueOf(productConfiguration.getDisplayAvailability()));
		}

		if (productConfiguration.getDisplayStockQuantity() == null) {
			map.put("displayStockQuantity", null);
		}
		else {
			map.put(
				"displayStockQuantity",
				String.valueOf(productConfiguration.getDisplayStockQuantity()));
		}

		if (productConfiguration.getInventoryEngine() == null) {
			map.put("inventoryEngine", null);
		}
		else {
			map.put(
				"inventoryEngine",
				String.valueOf(productConfiguration.getInventoryEngine()));
		}

		if (productConfiguration.getLowStockAction() == null) {
			map.put("lowStockAction", null);
		}
		else {
			map.put(
				"lowStockAction",
				String.valueOf(productConfiguration.getLowStockAction()));
		}

		if (productConfiguration.getMaxOrderQuantity() == null) {
			map.put("maxOrderQuantity", null);
		}
		else {
			map.put(
				"maxOrderQuantity",
				String.valueOf(productConfiguration.getMaxOrderQuantity()));
		}

		if (productConfiguration.getMinOrderQuantity() == null) {
			map.put("minOrderQuantity", null);
		}
		else {
			map.put(
				"minOrderQuantity",
				String.valueOf(productConfiguration.getMinOrderQuantity()));
		}

		if (productConfiguration.getMinStockQuantity() == null) {
			map.put("minStockQuantity", null);
		}
		else {
			map.put(
				"minStockQuantity",
				String.valueOf(productConfiguration.getMinStockQuantity()));
		}

		if (productConfiguration.getMultipleOrderQuantity() == null) {
			map.put("multipleOrderQuantity", null);
		}
		else {
			map.put(
				"multipleOrderQuantity",
				String.valueOf(
					productConfiguration.getMultipleOrderQuantity()));
		}

		return map;
	}

	public static class ProductConfigurationJSONParser
		extends BaseJSONParser<ProductConfiguration> {

		@Override
		protected ProductConfiguration createDTO() {
			return new ProductConfiguration();
		}

		@Override
		protected ProductConfiguration[] createDTOArray(int size) {
			return new ProductConfiguration[size];
		}

		@Override
		protected void setField(
			ProductConfiguration productConfiguration,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "allowBackOrder")) {
				if (jsonParserFieldValue != null) {
					productConfiguration.setAllowBackOrder(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "allowedOrderQuantities")) {

				if (jsonParserFieldValue != null) {
					productConfiguration.setAllowedOrderQuantities(
						toIntegers((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "displayAvailability")) {

				if (jsonParserFieldValue != null) {
					productConfiguration.setDisplayAvailability(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "displayStockQuantity")) {

				if (jsonParserFieldValue != null) {
					productConfiguration.setDisplayStockQuantity(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "inventoryEngine")) {
				if (jsonParserFieldValue != null) {
					productConfiguration.setInventoryEngine(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lowStockAction")) {
				if (jsonParserFieldValue != null) {
					productConfiguration.setLowStockAction(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "maxOrderQuantity")) {
				if (jsonParserFieldValue != null) {
					productConfiguration.setMaxOrderQuantity(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "minOrderQuantity")) {
				if (jsonParserFieldValue != null) {
					productConfiguration.setMinOrderQuantity(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "minStockQuantity")) {
				if (jsonParserFieldValue != null) {
					productConfiguration.setMinStockQuantity(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "multipleOrderQuantity")) {

				if (jsonParserFieldValue != null) {
					productConfiguration.setMultipleOrderQuantity(
						Integer.valueOf((String)jsonParserFieldValue));
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