/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.client.serdes.v1_0;

import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.TierPrice;
import com.liferay.headless.commerce.delivery.catalog.client.json.BaseJSONParser;

import java.math.BigDecimal;

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
public class TierPriceSerDes {

	public static TierPrice toDTO(String json) {
		TierPriceJSONParser tierPriceJSONParser = new TierPriceJSONParser();

		return tierPriceJSONParser.parseToDTO(json);
	}

	public static TierPrice[] toDTOs(String json) {
		TierPriceJSONParser tierPriceJSONParser = new TierPriceJSONParser();

		return tierPriceJSONParser.parseToDTOs(json);
	}

	public static String toJSON(TierPrice tierPrice) {
		if (tierPrice == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (tierPrice.getCurrency() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"currency\": ");

			sb.append("\"");

			sb.append(_escape(tierPrice.getCurrency()));

			sb.append("\"");
		}

		if (tierPrice.getPrice() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"price\": ");

			sb.append(tierPrice.getPrice());
		}

		if (tierPrice.getPriceFormatted() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceFormatted\": ");

			sb.append("\"");

			sb.append(_escape(tierPrice.getPriceFormatted()));

			sb.append("\"");
		}

		if (tierPrice.getQuantity() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"quantity\": ");

			sb.append(tierPrice.getQuantity());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		TierPriceJSONParser tierPriceJSONParser = new TierPriceJSONParser();

		return tierPriceJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(TierPrice tierPrice) {
		if (tierPrice == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (tierPrice.getCurrency() == null) {
			map.put("currency", null);
		}
		else {
			map.put("currency", String.valueOf(tierPrice.getCurrency()));
		}

		if (tierPrice.getPrice() == null) {
			map.put("price", null);
		}
		else {
			map.put("price", String.valueOf(tierPrice.getPrice()));
		}

		if (tierPrice.getPriceFormatted() == null) {
			map.put("priceFormatted", null);
		}
		else {
			map.put(
				"priceFormatted",
				String.valueOf(tierPrice.getPriceFormatted()));
		}

		if (tierPrice.getQuantity() == null) {
			map.put("quantity", null);
		}
		else {
			map.put("quantity", String.valueOf(tierPrice.getQuantity()));
		}

		return map;
	}

	public static class TierPriceJSONParser extends BaseJSONParser<TierPrice> {

		@Override
		protected TierPrice createDTO() {
			return new TierPrice();
		}

		@Override
		protected TierPrice[] createDTOArray(int size) {
			return new TierPrice[size];
		}

		@Override
		protected void setField(
			TierPrice tierPrice, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "currency")) {
				if (jsonParserFieldValue != null) {
					tierPrice.setCurrency((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "price")) {
				if (jsonParserFieldValue != null) {
					tierPrice.setPrice(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priceFormatted")) {
				if (jsonParserFieldValue != null) {
					tierPrice.setPriceFormatted((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "quantity")) {
				if (jsonParserFieldValue != null) {
					tierPrice.setQuantity(
						new BigDecimal((String)jsonParserFieldValue));
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