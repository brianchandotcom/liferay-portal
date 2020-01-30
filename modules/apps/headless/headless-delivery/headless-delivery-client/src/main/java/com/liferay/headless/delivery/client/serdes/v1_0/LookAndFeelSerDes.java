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

import com.liferay.headless.delivery.client.dto.v1_0.LookAndFeel;
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
public class LookAndFeelSerDes {

	public static LookAndFeel toDTO(String json) {
		LookAndFeelJSONParser lookAndFeelJSONParser =
			new LookAndFeelJSONParser();

		return lookAndFeelJSONParser.parseToDTO(json);
	}

	public static LookAndFeel[] toDTOs(String json) {
		LookAndFeelJSONParser lookAndFeelJSONParser =
			new LookAndFeelJSONParser();

		return lookAndFeelJSONParser.parseToDTOs(json);
	}

	public static String toJSON(LookAndFeel lookAndFeel) {
		if (lookAndFeel == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (lookAndFeel.getColorSchemeName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"colorSchemeName\": ");

			sb.append("\"");

			sb.append(_escape(lookAndFeel.getColorSchemeName()));

			sb.append("\"");
		}

		if (lookAndFeel.getCss() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"css\": ");

			sb.append("\"");

			sb.append(_escape(lookAndFeel.getCss()));

			sb.append("\"");
		}

		if (lookAndFeel.getJavascript() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"javascript\": ");

			sb.append("\"");

			sb.append(_escape(lookAndFeel.getJavascript()));

			sb.append("\"");
		}

		if (lookAndFeel.getThemeName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"themeName\": ");

			sb.append("\"");

			sb.append(_escape(lookAndFeel.getThemeName()));

			sb.append("\"");
		}

		if (lookAndFeel.getThemeSettings() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"themeSettings\": ");

			sb.append("\"");

			sb.append(_escape(lookAndFeel.getThemeSettings()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		LookAndFeelJSONParser lookAndFeelJSONParser =
			new LookAndFeelJSONParser();

		return lookAndFeelJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(LookAndFeel lookAndFeel) {
		if (lookAndFeel == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (lookAndFeel.getColorSchemeName() == null) {
			map.put("colorSchemeName", null);
		}
		else {
			map.put(
				"colorSchemeName",
				String.valueOf(lookAndFeel.getColorSchemeName()));
		}

		if (lookAndFeel.getCss() == null) {
			map.put("css", null);
		}
		else {
			map.put("css", String.valueOf(lookAndFeel.getCss()));
		}

		if (lookAndFeel.getJavascript() == null) {
			map.put("javascript", null);
		}
		else {
			map.put("javascript", String.valueOf(lookAndFeel.getJavascript()));
		}

		if (lookAndFeel.getThemeName() == null) {
			map.put("themeName", null);
		}
		else {
			map.put("themeName", String.valueOf(lookAndFeel.getThemeName()));
		}

		if (lookAndFeel.getThemeSettings() == null) {
			map.put("themeSettings", null);
		}
		else {
			map.put(
				"themeSettings",
				String.valueOf(lookAndFeel.getThemeSettings()));
		}

		return map;
	}

	public static class LookAndFeelJSONParser
		extends BaseJSONParser<LookAndFeel> {

		@Override
		protected LookAndFeel createDTO() {
			return new LookAndFeel();
		}

		@Override
		protected LookAndFeel[] createDTOArray(int size) {
			return new LookAndFeel[size];
		}

		@Override
		protected void setField(
			LookAndFeel lookAndFeel, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "colorSchemeName")) {
				if (jsonParserFieldValue != null) {
					lookAndFeel.setColorSchemeName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "css")) {
				if (jsonParserFieldValue != null) {
					lookAndFeel.setCss((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "javascript")) {
				if (jsonParserFieldValue != null) {
					lookAndFeel.setJavascript((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "themeName")) {
				if (jsonParserFieldValue != null) {
					lookAndFeel.setThemeName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "themeSettings")) {
				if (jsonParserFieldValue != null) {
					lookAndFeel.setThemeSettings((Object)jsonParserFieldValue);
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