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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.Settings;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class SettingsSerDes {

	public static Settings toDTO(String json) {
		SettingsJSONParser settingsJSONParser = new SettingsJSONParser();

		return settingsJSONParser.parseToDTO(json);
	}

	public static Settings[] toDTOs(String json) {
		SettingsJSONParser settingsJSONParser = new SettingsJSONParser();

		return settingsJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Settings settings) {
		if (settings == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (settings.getColorSchemeName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"colorSchemeName\": ");

			sb.append("\"");

			sb.append(_escape(settings.getColorSchemeName()));

			sb.append("\"");
		}

		if (settings.getCss() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"css\": ");

			sb.append("\"");

			sb.append(_escape(settings.getCss()));

			sb.append("\"");
		}

		if (settings.getJavascript() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"javascript\": ");

			sb.append("\"");

			sb.append(_escape(settings.getJavascript()));

			sb.append("\"");
		}

		if (settings.getMasterPage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"masterPage\": ");

			sb.append(String.valueOf(settings.getMasterPage()));
		}

		if (settings.getThemeName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"themeName\": ");

			sb.append("\"");

			sb.append(_escape(settings.getThemeName()));

			sb.append("\"");
		}

		if (settings.getThemeSettings() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"themeSettings\": ");

			sb.append("\"");

			sb.append(_escape(settings.getThemeSettings()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SettingsJSONParser settingsJSONParser = new SettingsJSONParser();

		return settingsJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Settings settings) {
		if (settings == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (settings.getColorSchemeName() == null) {
			map.put("colorSchemeName", null);
		}
		else {
			map.put(
				"colorSchemeName",
				String.valueOf(settings.getColorSchemeName()));
		}

		if (settings.getCss() == null) {
			map.put("css", null);
		}
		else {
			map.put("css", String.valueOf(settings.getCss()));
		}

		if (settings.getJavascript() == null) {
			map.put("javascript", null);
		}
		else {
			map.put("javascript", String.valueOf(settings.getJavascript()));
		}

		if (settings.getMasterPage() == null) {
			map.put("masterPage", null);
		}
		else {
			map.put("masterPage", String.valueOf(settings.getMasterPage()));
		}

		if (settings.getThemeName() == null) {
			map.put("themeName", null);
		}
		else {
			map.put("themeName", String.valueOf(settings.getThemeName()));
		}

		if (settings.getThemeSettings() == null) {
			map.put("themeSettings", null);
		}
		else {
			map.put(
				"themeSettings", String.valueOf(settings.getThemeSettings()));
		}

		return map;
	}

	public static class SettingsJSONParser extends BaseJSONParser<Settings> {

		@Override
		protected Settings createDTO() {
			return new Settings();
		}

		@Override
		protected Settings[] createDTOArray(int size) {
			return new Settings[size];
		}

		@Override
		protected void setField(
			Settings settings, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "colorSchemeName")) {
				if (jsonParserFieldValue != null) {
					settings.setColorSchemeName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "css")) {
				if (jsonParserFieldValue != null) {
					settings.setCss((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "javascript")) {
				if (jsonParserFieldValue != null) {
					settings.setJavascript((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "masterPage")) {
				if (jsonParserFieldValue != null) {
					settings.setMasterPage(
						MasterPageSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "themeName")) {
				if (jsonParserFieldValue != null) {
					settings.setThemeName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "themeSettings")) {
				if (jsonParserFieldValue != null) {
					settings.setThemeSettings((Object)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
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
			else {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}