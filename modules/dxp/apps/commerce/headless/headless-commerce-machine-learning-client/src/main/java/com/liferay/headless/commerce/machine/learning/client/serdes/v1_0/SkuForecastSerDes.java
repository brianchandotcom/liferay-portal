/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.headless.commerce.machine.learning.client.serdes.v1_0;

import com.liferay.headless.commerce.machine.learning.client.dto.v1_0.SkuForecast;
import com.liferay.headless.commerce.machine.learning.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Riccardo Ferrari
 * @generated
 */
@Generated("")
public class SkuForecastSerDes {

	public static SkuForecast toDTO(String json) {
		SkuForecastJSONParser skuForecastJSONParser =
			new SkuForecastJSONParser();

		return skuForecastJSONParser.parseToDTO(json);
	}

	public static SkuForecast[] toDTOs(String json) {
		SkuForecastJSONParser skuForecastJSONParser =
			new SkuForecastJSONParser();

		return skuForecastJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SkuForecast skuForecast) {
		if (skuForecast == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (skuForecast.getActual() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actual\": ");

			sb.append(skuForecast.getActual());
		}

		if (skuForecast.getForecast() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"forecast\": ");

			sb.append(skuForecast.getForecast());
		}

		if (skuForecast.getForecastLowerBound() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"forecastLowerBound\": ");

			sb.append(skuForecast.getForecastLowerBound());
		}

		if (skuForecast.getForecastUpperBound() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"forecastUpperBound\": ");

			sb.append(skuForecast.getForecastUpperBound());
		}

		if (skuForecast.getSku() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sku\": ");

			sb.append("\"");

			sb.append(_escape(skuForecast.getSku()));

			sb.append("\"");
		}

		if (skuForecast.getTimestamp() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"timestamp\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(skuForecast.getTimestamp()));

			sb.append("\"");
		}

		if (skuForecast.getUnit() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"unit\": ");

			sb.append("\"");

			sb.append(_escape(skuForecast.getUnit()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SkuForecastJSONParser skuForecastJSONParser =
			new SkuForecastJSONParser();

		return skuForecastJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SkuForecast skuForecast) {
		if (skuForecast == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (skuForecast.getActual() == null) {
			map.put("actual", null);
		}
		else {
			map.put("actual", String.valueOf(skuForecast.getActual()));
		}

		if (skuForecast.getForecast() == null) {
			map.put("forecast", null);
		}
		else {
			map.put("forecast", String.valueOf(skuForecast.getForecast()));
		}

		if (skuForecast.getForecastLowerBound() == null) {
			map.put("forecastLowerBound", null);
		}
		else {
			map.put(
				"forecastLowerBound",
				String.valueOf(skuForecast.getForecastLowerBound()));
		}

		if (skuForecast.getForecastUpperBound() == null) {
			map.put("forecastUpperBound", null);
		}
		else {
			map.put(
				"forecastUpperBound",
				String.valueOf(skuForecast.getForecastUpperBound()));
		}

		if (skuForecast.getSku() == null) {
			map.put("sku", null);
		}
		else {
			map.put("sku", String.valueOf(skuForecast.getSku()));
		}

		if (skuForecast.getTimestamp() == null) {
			map.put("timestamp", null);
		}
		else {
			map.put(
				"timestamp",
				liferayToJSONDateFormat.format(skuForecast.getTimestamp()));
		}

		if (skuForecast.getUnit() == null) {
			map.put("unit", null);
		}
		else {
			map.put("unit", String.valueOf(skuForecast.getUnit()));
		}

		return map;
	}

	public static class SkuForecastJSONParser
		extends BaseJSONParser<SkuForecast> {

		@Override
		protected SkuForecast createDTO() {
			return new SkuForecast();
		}

		@Override
		protected SkuForecast[] createDTOArray(int size) {
			return new SkuForecast[size];
		}

		@Override
		protected void setField(
			SkuForecast skuForecast, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actual")) {
				if (jsonParserFieldValue != null) {
					skuForecast.setActual(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "forecast")) {
				if (jsonParserFieldValue != null) {
					skuForecast.setForecast(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "forecastLowerBound")) {

				if (jsonParserFieldValue != null) {
					skuForecast.setForecastLowerBound(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "forecastUpperBound")) {

				if (jsonParserFieldValue != null) {
					skuForecast.setForecastUpperBound(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sku")) {
				if (jsonParserFieldValue != null) {
					skuForecast.setSku((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "timestamp")) {
				if (jsonParserFieldValue != null) {
					skuForecast.setTimestamp(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "unit")) {
				if (jsonParserFieldValue != null) {
					skuForecast.setUnit((String)jsonParserFieldValue);
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