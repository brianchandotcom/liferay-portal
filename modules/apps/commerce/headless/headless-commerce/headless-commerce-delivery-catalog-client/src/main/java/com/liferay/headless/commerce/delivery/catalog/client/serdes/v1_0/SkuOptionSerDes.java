/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.client.serdes.v1_0;

import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.SkuOption;
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

		if (skuOption.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append(skuOption.getKey());
		}

		if (skuOption.getPrice() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"price\": ");

			sb.append("\"");

			sb.append(_escape(skuOption.getPrice()));

			sb.append("\"");
		}

		if (skuOption.getPriceType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceType\": ");

			sb.append("\"");

			sb.append(_escape(skuOption.getPriceType()));

			sb.append("\"");
		}

		if (skuOption.getQuantity() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"quantity\": ");

			sb.append("\"");

			sb.append(_escape(skuOption.getQuantity()));

			sb.append("\"");
		}

		if (skuOption.getSkuId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuId\": ");

			sb.append(skuOption.getSkuId());
		}

		if (skuOption.getSkuOptionId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuOptionId\": ");

			sb.append(skuOption.getSkuOptionId());
		}

		if (skuOption.getSkuOptionKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuOptionKey\": ");

			sb.append("\"");

			sb.append(_escape(skuOption.getSkuOptionKey()));

			sb.append("\"");
		}

		if (skuOption.getSkuOptionValueId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuOptionValueId\": ");

			sb.append(skuOption.getSkuOptionValueId());
		}

		if (skuOption.getSkuOptionValueKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuOptionValueKey\": ");

			sb.append("\"");

			sb.append(_escape(skuOption.getSkuOptionValueKey()));

			sb.append("\"");
		}

		if (skuOption.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append(skuOption.getValue());
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

		if (skuOption.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(skuOption.getKey()));
		}

		if (skuOption.getPrice() == null) {
			map.put("price", null);
		}
		else {
			map.put("price", String.valueOf(skuOption.getPrice()));
		}

		if (skuOption.getPriceType() == null) {
			map.put("priceType", null);
		}
		else {
			map.put("priceType", String.valueOf(skuOption.getPriceType()));
		}

		if (skuOption.getQuantity() == null) {
			map.put("quantity", null);
		}
		else {
			map.put("quantity", String.valueOf(skuOption.getQuantity()));
		}

		if (skuOption.getSkuId() == null) {
			map.put("skuId", null);
		}
		else {
			map.put("skuId", String.valueOf(skuOption.getSkuId()));
		}

		if (skuOption.getSkuOptionId() == null) {
			map.put("skuOptionId", null);
		}
		else {
			map.put("skuOptionId", String.valueOf(skuOption.getSkuOptionId()));
		}

		if (skuOption.getSkuOptionKey() == null) {
			map.put("skuOptionKey", null);
		}
		else {
			map.put(
				"skuOptionKey", String.valueOf(skuOption.getSkuOptionKey()));
		}

		if (skuOption.getSkuOptionValueId() == null) {
			map.put("skuOptionValueId", null);
		}
		else {
			map.put(
				"skuOptionValueId",
				String.valueOf(skuOption.getSkuOptionValueId()));
		}

		if (skuOption.getSkuOptionValueKey() == null) {
			map.put("skuOptionValueKey", null);
		}
		else {
			map.put(
				"skuOptionValueKey",
				String.valueOf(skuOption.getSkuOptionValueKey()));
		}

		if (skuOption.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(skuOption.getValue()));
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

			if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					skuOption.setKey(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "price")) {
				if (jsonParserFieldValue != null) {
					skuOption.setPrice((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priceType")) {
				if (jsonParserFieldValue != null) {
					skuOption.setPriceType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "quantity")) {
				if (jsonParserFieldValue != null) {
					skuOption.setQuantity((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "skuId")) {
				if (jsonParserFieldValue != null) {
					skuOption.setSkuId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "skuOptionId")) {
				if (jsonParserFieldValue != null) {
					skuOption.setSkuOptionId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "skuOptionKey")) {
				if (jsonParserFieldValue != null) {
					skuOption.setSkuOptionKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "skuOptionValueId")) {
				if (jsonParserFieldValue != null) {
					skuOption.setSkuOptionValueId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "skuOptionValueKey")) {
				if (jsonParserFieldValue != null) {
					skuOption.setSkuOptionValueKey(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					skuOption.setValue(
						Long.valueOf((String)jsonParserFieldValue));
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