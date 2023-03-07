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

package com.liferay.headless.commerce.delivery.catalog.v2.client.serdes.v2_0;

import com.liferay.headless.commerce.delivery.catalog.v2.client.dto.v2_0.SkuOption;
import com.liferay.headless.commerce.delivery.catalog.v2.client.dto.v2_0.SkuOptionValue;
import com.liferay.headless.commerce.delivery.catalog.v2.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Crescenzo Rega
 * @generated
 */
@Generated("")
public class SkuOptionSerDes {

	public static SkuOption toDTO(String json) {
		SkuOptionJSONParser skuOptionJSONParser = new SkuOptionJSONParser();

		return skuOptionJSONParser.parseToDTO(json);
	}

	public static SkuOption[] toDTOs(String json) {
		SkuOptionJSONParser skuOptionJSONParser = new SkuOptionJSONParser();

		return skuOptionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SkuOption skuOption) {
		if (skuOption == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (skuOption.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(skuOption.getId());
		}

		if (skuOption.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(skuOption.getKey()));

			sb.append("\"");
		}

		if (skuOption.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(skuOption.getName()));

			sb.append("\"");
		}

		if (skuOption.getPriceContributor() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceContributor\": ");

			sb.append(skuOption.getPriceContributor());
		}

		if (skuOption.getRequired() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"required\": ");

			sb.append(skuOption.getRequired());
		}

		if (skuOption.getSkuContributor() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuContributor\": ");

			sb.append(skuOption.getSkuContributor());
		}

		if (skuOption.getSkuOptionValues() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuOptionValues\": ");

			sb.append("[");

			for (int i = 0; i < skuOption.getSkuOptionValues().length; i++) {
				sb.append(String.valueOf(skuOption.getSkuOptionValues()[i]));

				if ((i + 1) < skuOption.getSkuOptionValues().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SkuOptionJSONParser skuOptionJSONParser = new SkuOptionJSONParser();

		return skuOptionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SkuOption skuOption) {
		if (skuOption == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (skuOption.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(skuOption.getId()));
		}

		if (skuOption.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(skuOption.getKey()));
		}

		if (skuOption.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(skuOption.getName()));
		}

		if (skuOption.getPriceContributor() == null) {
			map.put("priceContributor", null);
		}
		else {
			map.put(
				"priceContributor",
				String.valueOf(skuOption.getPriceContributor()));
		}

		if (skuOption.getRequired() == null) {
			map.put("required", null);
		}
		else {
			map.put("required", String.valueOf(skuOption.getRequired()));
		}

		if (skuOption.getSkuContributor() == null) {
			map.put("skuContributor", null);
		}
		else {
			map.put(
				"skuContributor",
				String.valueOf(skuOption.getSkuContributor()));
		}

		if (skuOption.getSkuOptionValues() == null) {
			map.put("skuOptionValues", null);
		}
		else {
			map.put(
				"skuOptionValues",
				String.valueOf(skuOption.getSkuOptionValues()));
		}

		return map;
	}

	public static class SkuOptionJSONParser extends BaseJSONParser<SkuOption> {

		@Override
		protected SkuOption createDTO() {
			return new SkuOption();
		}

		@Override
		protected SkuOption[] createDTOArray(int size) {
			return new SkuOption[size];
		}

		@Override
		protected void setField(
			SkuOption skuOption, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					skuOption.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					skuOption.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					skuOption.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priceContributor")) {
				if (jsonParserFieldValue != null) {
					skuOption.setPriceContributor(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "required")) {
				if (jsonParserFieldValue != null) {
					skuOption.setRequired((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "skuContributor")) {
				if (jsonParserFieldValue != null) {
					skuOption.setSkuContributor((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "skuOptionValues")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					SkuOptionValue[] skuOptionValuesArray =
						new SkuOptionValue[jsonParserFieldValues.length];

					for (int i = 0; i < skuOptionValuesArray.length; i++) {
						skuOptionValuesArray[i] = SkuOptionValueSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					skuOption.setSkuOptionValues(skuOptionValuesArray);
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