/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.client.serdes.v1_0;

import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.ReplacementSku;
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
public class ReplacementSkuSerDes {

	public static ReplacementSku toDTO(String json) {
		ReplacementSkuJSONParser replacementSkuJSONParser =
			new ReplacementSkuJSONParser();

		return replacementSkuJSONParser.parseToDTO(json);
	}

	public static ReplacementSku[] toDTOs(String json) {
		ReplacementSkuJSONParser replacementSkuJSONParser =
			new ReplacementSkuJSONParser();

		return replacementSkuJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ReplacementSku replacementSku) {
		if (replacementSku == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (replacementSku.getPrice() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"price\": ");

			sb.append(String.valueOf(replacementSku.getPrice()));
		}

		if (replacementSku.getProductConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"productConfiguration\": ");

			sb.append(String.valueOf(replacementSku.getProductConfiguration()));
		}

		if (replacementSku.getSku() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sku\": ");

			sb.append("\"");

			sb.append(_escape(replacementSku.getSku()));

			sb.append("\"");
		}

		if (replacementSku.getSkuId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuId\": ");

			sb.append(replacementSku.getSkuId());
		}

		if (replacementSku.getSkuOptions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"skuOptions\": ");

			sb.append("[");

			for (int i = 0; i < replacementSku.getSkuOptions().length; i++) {
				sb.append(String.valueOf(replacementSku.getSkuOptions()[i]));

				if ((i + 1) < replacementSku.getSkuOptions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (replacementSku.getUrls() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"urls\": ");

			sb.append(_toJSON(replacementSku.getUrls()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ReplacementSkuJSONParser replacementSkuJSONParser =
			new ReplacementSkuJSONParser();

		return replacementSkuJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ReplacementSku replacementSku) {
		if (replacementSku == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (replacementSku.getPrice() == null) {
			map.put("price", null);
		}
		else {
			map.put("price", String.valueOf(replacementSku.getPrice()));
		}

		if (replacementSku.getProductConfiguration() == null) {
			map.put("productConfiguration", null);
		}
		else {
			map.put(
				"productConfiguration",
				String.valueOf(replacementSku.getProductConfiguration()));
		}

		if (replacementSku.getSku() == null) {
			map.put("sku", null);
		}
		else {
			map.put("sku", String.valueOf(replacementSku.getSku()));
		}

		if (replacementSku.getSkuId() == null) {
			map.put("skuId", null);
		}
		else {
			map.put("skuId", String.valueOf(replacementSku.getSkuId()));
		}

		if (replacementSku.getSkuOptions() == null) {
			map.put("skuOptions", null);
		}
		else {
			map.put(
				"skuOptions", String.valueOf(replacementSku.getSkuOptions()));
		}

		if (replacementSku.getUrls() == null) {
			map.put("urls", null);
		}
		else {
			map.put("urls", String.valueOf(replacementSku.getUrls()));
		}

		return map;
	}

	public static class ReplacementSkuJSONParser
		extends BaseJSONParser<ReplacementSku> {

		@Override
		protected ReplacementSku createDTO() {
			return new ReplacementSku();
		}

		@Override
		protected ReplacementSku[] createDTOArray(int size) {
			return new ReplacementSku[size];
		}

		@Override
		protected void setField(
			ReplacementSku replacementSku, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "price")) {
				if (jsonParserFieldValue != null) {
					replacementSku.setPrice(
						PriceSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "productConfiguration")) {

				if (jsonParserFieldValue != null) {
					replacementSku.setProductConfiguration(
						ProductConfigurationSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sku")) {
				if (jsonParserFieldValue != null) {
					replacementSku.setSku((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "skuId")) {
				if (jsonParserFieldValue != null) {
					replacementSku.setSkuId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "skuOptions")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					SkuOption[] skuOptionsArray =
						new SkuOption[jsonParserFieldValues.length];

					for (int i = 0; i < skuOptionsArray.length; i++) {
						skuOptionsArray[i] = SkuOptionSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					replacementSku.setSkuOptions(skuOptionsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "urls")) {
				if (jsonParserFieldValue != null) {
					replacementSku.setUrls(
						(Map)ReplacementSkuSerDes.toMap(
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