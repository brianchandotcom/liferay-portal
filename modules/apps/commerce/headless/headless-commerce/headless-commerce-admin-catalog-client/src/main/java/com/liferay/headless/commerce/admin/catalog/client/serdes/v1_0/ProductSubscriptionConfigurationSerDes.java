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

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductSubscriptionConfiguration;
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
public class ProductSubscriptionConfigurationSerDes {

	public static ProductSubscriptionConfiguration toDTO(String json) {
		ProductSubscriptionConfigurationJSONParser
			productSubscriptionConfigurationJSONParser =
				new ProductSubscriptionConfigurationJSONParser();

		return productSubscriptionConfigurationJSONParser.parseToDTO(json);
	}

	public static ProductSubscriptionConfiguration[] toDTOs(String json) {
		ProductSubscriptionConfigurationJSONParser
			productSubscriptionConfigurationJSONParser =
				new ProductSubscriptionConfigurationJSONParser();

		return productSubscriptionConfigurationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ProductSubscriptionConfiguration productSubscriptionConfiguration) {

		if (productSubscriptionConfiguration == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (productSubscriptionConfiguration.getEnable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enable\": ");

			sb.append(productSubscriptionConfiguration.getEnable());
		}

		if (productSubscriptionConfiguration.getLength() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"length\": ");

			sb.append(productSubscriptionConfiguration.getLength());
		}

		if (productSubscriptionConfiguration.getNumberOfLength() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfLength\": ");

			sb.append(productSubscriptionConfiguration.getNumberOfLength());
		}

		if (productSubscriptionConfiguration.getSubscriptionType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subscriptionType\": ");

			sb.append("\"");

			sb.append(productSubscriptionConfiguration.getSubscriptionType());

			sb.append("\"");
		}

		if (productSubscriptionConfiguration.getSubscriptionTypeSettings() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subscriptionTypeSettings\": ");

			sb.append(
				_toJSON(
					productSubscriptionConfiguration.
						getSubscriptionTypeSettings()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ProductSubscriptionConfigurationJSONParser
			productSubscriptionConfigurationJSONParser =
				new ProductSubscriptionConfigurationJSONParser();

		return productSubscriptionConfigurationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ProductSubscriptionConfiguration productSubscriptionConfiguration) {

		if (productSubscriptionConfiguration == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (productSubscriptionConfiguration.getEnable() == null) {
			map.put("enable", null);
		}
		else {
			map.put(
				"enable",
				String.valueOf(productSubscriptionConfiguration.getEnable()));
		}

		if (productSubscriptionConfiguration.getLength() == null) {
			map.put("length", null);
		}
		else {
			map.put(
				"length",
				String.valueOf(productSubscriptionConfiguration.getLength()));
		}

		if (productSubscriptionConfiguration.getNumberOfLength() == null) {
			map.put("numberOfLength", null);
		}
		else {
			map.put(
				"numberOfLength",
				String.valueOf(
					productSubscriptionConfiguration.getNumberOfLength()));
		}

		if (productSubscriptionConfiguration.getSubscriptionType() == null) {
			map.put("subscriptionType", null);
		}
		else {
			map.put(
				"subscriptionType",
				String.valueOf(
					productSubscriptionConfiguration.getSubscriptionType()));
		}

		if (productSubscriptionConfiguration.getSubscriptionTypeSettings() ==
				null) {

			map.put("subscriptionTypeSettings", null);
		}
		else {
			map.put(
				"subscriptionTypeSettings",
				String.valueOf(
					productSubscriptionConfiguration.
						getSubscriptionTypeSettings()));
		}

		return map;
	}

	public static class ProductSubscriptionConfigurationJSONParser
		extends BaseJSONParser<ProductSubscriptionConfiguration> {

		@Override
		protected ProductSubscriptionConfiguration createDTO() {
			return new ProductSubscriptionConfiguration();
		}

		@Override
		protected ProductSubscriptionConfiguration[] createDTOArray(int size) {
			return new ProductSubscriptionConfiguration[size];
		}

		@Override
		protected void setField(
			ProductSubscriptionConfiguration productSubscriptionConfiguration,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "enable")) {
				if (jsonParserFieldValue != null) {
					productSubscriptionConfiguration.setEnable(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "length")) {
				if (jsonParserFieldValue != null) {
					productSubscriptionConfiguration.setLength(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfLength")) {
				if (jsonParserFieldValue != null) {
					productSubscriptionConfiguration.setNumberOfLength(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "subscriptionType")) {
				if (jsonParserFieldValue != null) {
					productSubscriptionConfiguration.setSubscriptionType(
						ProductSubscriptionConfiguration.SubscriptionType.
							create((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "subscriptionTypeSettings")) {

				if (jsonParserFieldValue != null) {
					productSubscriptionConfiguration.
						setSubscriptionTypeSettings(
							(Map)ProductSubscriptionConfigurationSerDes.toMap(
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