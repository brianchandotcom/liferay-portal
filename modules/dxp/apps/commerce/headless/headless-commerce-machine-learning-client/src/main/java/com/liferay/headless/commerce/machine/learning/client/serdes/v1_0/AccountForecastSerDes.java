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

import com.liferay.headless.commerce.machine.learning.client.dto.v1_0.AccountForecast;
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
public class AccountForecastSerDes {

	public static AccountForecast toDTO(String json) {
		AccountForecastJSONParser accountForecastJSONParser =
			new AccountForecastJSONParser();

		return accountForecastJSONParser.parseToDTO(json);
	}

	public static AccountForecast[] toDTOs(String json) {
		AccountForecastJSONParser accountForecastJSONParser =
			new AccountForecastJSONParser();

		return accountForecastJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AccountForecast accountForecast) {
		if (accountForecast == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (accountForecast.getAccount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"account\": ");

			sb.append(accountForecast.getAccount());
		}

		if (accountForecast.getActual() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actual\": ");

			sb.append(accountForecast.getActual());
		}

		if (accountForecast.getForecast() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"forecast\": ");

			sb.append(accountForecast.getForecast());
		}

		if (accountForecast.getForecastLowerBound() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"forecastLowerBound\": ");

			sb.append(accountForecast.getForecastLowerBound());
		}

		if (accountForecast.getForecastUpperBound() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"forecastUpperBound\": ");

			sb.append(accountForecast.getForecastUpperBound());
		}

		if (accountForecast.getTimestamp() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"timestamp\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(accountForecast.getTimestamp()));

			sb.append("\"");
		}

		if (accountForecast.getUnit() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"unit\": ");

			sb.append("\"");

			sb.append(_escape(accountForecast.getUnit()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AccountForecastJSONParser accountForecastJSONParser =
			new AccountForecastJSONParser();

		return accountForecastJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(AccountForecast accountForecast) {
		if (accountForecast == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (accountForecast.getAccount() == null) {
			map.put("account", null);
		}
		else {
			map.put("account", String.valueOf(accountForecast.getAccount()));
		}

		if (accountForecast.getActual() == null) {
			map.put("actual", null);
		}
		else {
			map.put("actual", String.valueOf(accountForecast.getActual()));
		}

		if (accountForecast.getForecast() == null) {
			map.put("forecast", null);
		}
		else {
			map.put("forecast", String.valueOf(accountForecast.getForecast()));
		}

		if (accountForecast.getForecastLowerBound() == null) {
			map.put("forecastLowerBound", null);
		}
		else {
			map.put(
				"forecastLowerBound",
				String.valueOf(accountForecast.getForecastLowerBound()));
		}

		if (accountForecast.getForecastUpperBound() == null) {
			map.put("forecastUpperBound", null);
		}
		else {
			map.put(
				"forecastUpperBound",
				String.valueOf(accountForecast.getForecastUpperBound()));
		}

		if (accountForecast.getTimestamp() == null) {
			map.put("timestamp", null);
		}
		else {
			map.put(
				"timestamp",
				liferayToJSONDateFormat.format(accountForecast.getTimestamp()));
		}

		if (accountForecast.getUnit() == null) {
			map.put("unit", null);
		}
		else {
			map.put("unit", String.valueOf(accountForecast.getUnit()));
		}

		return map;
	}

	public static class AccountForecastJSONParser
		extends BaseJSONParser<AccountForecast> {

		@Override
		protected AccountForecast createDTO() {
			return new AccountForecast();
		}

		@Override
		protected AccountForecast[] createDTOArray(int size) {
			return new AccountForecast[size];
		}

		@Override
		protected void setField(
			AccountForecast accountForecast, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "account")) {
				if (jsonParserFieldValue != null) {
					accountForecast.setAccount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "actual")) {
				if (jsonParserFieldValue != null) {
					accountForecast.setActual(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "forecast")) {
				if (jsonParserFieldValue != null) {
					accountForecast.setForecast(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "forecastLowerBound")) {

				if (jsonParserFieldValue != null) {
					accountForecast.setForecastLowerBound(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "forecastUpperBound")) {

				if (jsonParserFieldValue != null) {
					accountForecast.setForecastUpperBound(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "timestamp")) {
				if (jsonParserFieldValue != null) {
					accountForecast.setTimestamp(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "unit")) {
				if (jsonParserFieldValue != null) {
					accountForecast.setUnit((String)jsonParserFieldValue);
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