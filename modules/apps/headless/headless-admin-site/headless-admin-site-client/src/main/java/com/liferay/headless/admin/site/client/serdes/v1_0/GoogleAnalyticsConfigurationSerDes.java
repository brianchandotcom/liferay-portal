/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.serdes.v1_0;

import com.liferay.headless.admin.site.client.dto.v1_0.GoogleAnalyticsConfiguration;
import com.liferay.headless.admin.site.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class GoogleAnalyticsConfigurationSerDes {

	public static GoogleAnalyticsConfiguration toDTO(String json) {
		GoogleAnalyticsConfigurationJSONParser
			googleAnalyticsConfigurationJSONParser =
				new GoogleAnalyticsConfigurationJSONParser();

		return googleAnalyticsConfigurationJSONParser.parseToDTO(json);
	}

	public static GoogleAnalyticsConfiguration[] toDTOs(String json) {
		GoogleAnalyticsConfigurationJSONParser
			googleAnalyticsConfigurationJSONParser =
				new GoogleAnalyticsConfigurationJSONParser();

		return googleAnalyticsConfigurationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		GoogleAnalyticsConfiguration googleAnalyticsConfiguration) {

		if (googleAnalyticsConfiguration == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (googleAnalyticsConfiguration.getGoogleAnalytics4Config() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"googleAnalytics4Config\": ");

			sb.append("\"");

			sb.append(
				_escape(
					googleAnalyticsConfiguration.getGoogleAnalytics4Config()));

			sb.append("\"");
		}

		if (googleAnalyticsConfiguration.getGoogleAnalytics4Id() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"googleAnalytics4Id\": ");

			sb.append("\"");

			sb.append(
				_escape(googleAnalyticsConfiguration.getGoogleAnalytics4Id()));

			sb.append("\"");
		}

		if (googleAnalyticsConfiguration.getGoogleAnalyticsConfig() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"googleAnalyticsConfig\": ");

			sb.append("\"");

			sb.append(
				_escape(
					googleAnalyticsConfiguration.getGoogleAnalyticsConfig()));

			sb.append("\"");
		}

		if (googleAnalyticsConfiguration.
				getGoogleAnalyticsCreateMethodConfig() != null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"googleAnalyticsCreateMethodConfig\": ");

			sb.append("\"");

			sb.append(
				_escape(
					googleAnalyticsConfiguration.
						getGoogleAnalyticsCreateMethodConfig()));

			sb.append("\"");
		}

		if (googleAnalyticsConfiguration.getGoogleAnalyticsId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"googleAnalyticsId\": ");

			sb.append("\"");

			sb.append(
				_escape(googleAnalyticsConfiguration.getGoogleAnalyticsId()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		GoogleAnalyticsConfigurationJSONParser
			googleAnalyticsConfigurationJSONParser =
				new GoogleAnalyticsConfigurationJSONParser();

		return googleAnalyticsConfigurationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		GoogleAnalyticsConfiguration googleAnalyticsConfiguration) {

		if (googleAnalyticsConfiguration == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (googleAnalyticsConfiguration.getGoogleAnalytics4Config() == null) {
			map.put("googleAnalytics4Config", null);
		}
		else {
			map.put(
				"googleAnalytics4Config",
				String.valueOf(
					googleAnalyticsConfiguration.getGoogleAnalytics4Config()));
		}

		if (googleAnalyticsConfiguration.getGoogleAnalytics4Id() == null) {
			map.put("googleAnalytics4Id", null);
		}
		else {
			map.put(
				"googleAnalytics4Id",
				String.valueOf(
					googleAnalyticsConfiguration.getGoogleAnalytics4Id()));
		}

		if (googleAnalyticsConfiguration.getGoogleAnalyticsConfig() == null) {
			map.put("googleAnalyticsConfig", null);
		}
		else {
			map.put(
				"googleAnalyticsConfig",
				String.valueOf(
					googleAnalyticsConfiguration.getGoogleAnalyticsConfig()));
		}

		if (googleAnalyticsConfiguration.
				getGoogleAnalyticsCreateMethodConfig() == null) {

			map.put("googleAnalyticsCreateMethodConfig", null);
		}
		else {
			map.put(
				"googleAnalyticsCreateMethodConfig",
				String.valueOf(
					googleAnalyticsConfiguration.
						getGoogleAnalyticsCreateMethodConfig()));
		}

		if (googleAnalyticsConfiguration.getGoogleAnalyticsId() == null) {
			map.put("googleAnalyticsId", null);
		}
		else {
			map.put(
				"googleAnalyticsId",
				String.valueOf(
					googleAnalyticsConfiguration.getGoogleAnalyticsId()));
		}

		return map;
	}

	public static class GoogleAnalyticsConfigurationJSONParser
		extends BaseJSONParser<GoogleAnalyticsConfiguration> {

		@Override
		protected GoogleAnalyticsConfiguration createDTO() {
			return new GoogleAnalyticsConfiguration();
		}

		@Override
		protected GoogleAnalyticsConfiguration[] createDTOArray(int size) {
			return new GoogleAnalyticsConfiguration[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "googleAnalytics4Config")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "googleAnalytics4Id")) {

				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "googleAnalyticsConfig")) {

				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"googleAnalyticsCreateMethodConfig")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "googleAnalyticsId")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			GoogleAnalyticsConfiguration googleAnalyticsConfiguration,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "googleAnalytics4Config")) {
				if (jsonParserFieldValue != null) {
					googleAnalyticsConfiguration.setGoogleAnalytics4Config(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "googleAnalytics4Id")) {

				if (jsonParserFieldValue != null) {
					googleAnalyticsConfiguration.setGoogleAnalytics4Id(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "googleAnalyticsConfig")) {

				if (jsonParserFieldValue != null) {
					googleAnalyticsConfiguration.setGoogleAnalyticsConfig(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"googleAnalyticsCreateMethodConfig")) {

				if (jsonParserFieldValue != null) {
					googleAnalyticsConfiguration.
						setGoogleAnalyticsCreateMethodConfig(
							(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "googleAnalyticsId")) {
				if (jsonParserFieldValue != null) {
					googleAnalyticsConfiguration.setGoogleAnalyticsId(
						(String)jsonParserFieldValue);
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

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value == null) {
			return "null";
		}

		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}