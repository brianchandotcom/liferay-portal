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

import com.liferay.headless.delivery.client.dto.v1_0.SEOSettings;
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
public class SEOSettingsSerDes {

	public static SEOSettings toDTO(String json) {
		SEOSettingsJSONParser seoSettingsJSONParser =
			new SEOSettingsJSONParser();

		return seoSettingsJSONParser.parseToDTO(json);
	}

	public static SEOSettings[] toDTOs(String json) {
		SEOSettingsJSONParser seoSettingsJSONParser =
			new SEOSettingsJSONParser();

		return seoSettingsJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SEOSettings seoSettings) {
		if (seoSettings == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (seoSettings.getCustomCanonicalURL() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customCanonicalURL\": ");

			sb.append("\"");

			sb.append(_escape(seoSettings.getCustomCanonicalURL()));

			sb.append("\"");
		}

		if (seoSettings.getCustomCanonicalURL_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customCanonicalURL_i18n\": ");

			sb.append(_toJSON(seoSettings.getCustomCanonicalURL_i18n()));
		}

		if (seoSettings.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(seoSettings.getDescription()));

			sb.append("\"");
		}

		if (seoSettings.getDescription_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description_i18n\": ");

			sb.append(_toJSON(seoSettings.getDescription_i18n()));
		}

		if (seoSettings.getHtmlTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"htmlTitle\": ");

			sb.append("\"");

			sb.append(_escape(seoSettings.getHtmlTitle()));

			sb.append("\"");
		}

		if (seoSettings.getHtmlTitle_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"htmlTitle_i18n\": ");

			sb.append(_toJSON(seoSettings.getHtmlTitle_i18n()));
		}

		if (seoSettings.getRobots() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"robots\": ");

			sb.append("\"");

			sb.append(_escape(seoSettings.getRobots()));

			sb.append("\"");
		}

		if (seoSettings.getRobots_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"robots_i18n\": ");

			sb.append(_toJSON(seoSettings.getRobots_i18n()));
		}

		if (seoSettings.getSeoKeywords() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"seoKeywords\": ");

			sb.append("\"");

			sb.append(_escape(seoSettings.getSeoKeywords()));

			sb.append("\"");
		}

		if (seoSettings.getSeoKeywords_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"seoKeywords_i18n\": ");

			sb.append(_toJSON(seoSettings.getSeoKeywords_i18n()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SEOSettingsJSONParser seoSettingsJSONParser =
			new SEOSettingsJSONParser();

		return seoSettingsJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SEOSettings seoSettings) {
		if (seoSettings == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (seoSettings.getCustomCanonicalURL() == null) {
			map.put("customCanonicalURL", null);
		}
		else {
			map.put(
				"customCanonicalURL",
				String.valueOf(seoSettings.getCustomCanonicalURL()));
		}

		if (seoSettings.getCustomCanonicalURL_i18n() == null) {
			map.put("customCanonicalURL_i18n", null);
		}
		else {
			map.put(
				"customCanonicalURL_i18n",
				String.valueOf(seoSettings.getCustomCanonicalURL_i18n()));
		}

		if (seoSettings.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description", String.valueOf(seoSettings.getDescription()));
		}

		if (seoSettings.getDescription_i18n() == null) {
			map.put("description_i18n", null);
		}
		else {
			map.put(
				"description_i18n",
				String.valueOf(seoSettings.getDescription_i18n()));
		}

		if (seoSettings.getHtmlTitle() == null) {
			map.put("htmlTitle", null);
		}
		else {
			map.put("htmlTitle", String.valueOf(seoSettings.getHtmlTitle()));
		}

		if (seoSettings.getHtmlTitle_i18n() == null) {
			map.put("htmlTitle_i18n", null);
		}
		else {
			map.put(
				"htmlTitle_i18n",
				String.valueOf(seoSettings.getHtmlTitle_i18n()));
		}

		if (seoSettings.getRobots() == null) {
			map.put("robots", null);
		}
		else {
			map.put("robots", String.valueOf(seoSettings.getRobots()));
		}

		if (seoSettings.getRobots_i18n() == null) {
			map.put("robots_i18n", null);
		}
		else {
			map.put(
				"robots_i18n", String.valueOf(seoSettings.getRobots_i18n()));
		}

		if (seoSettings.getSeoKeywords() == null) {
			map.put("seoKeywords", null);
		}
		else {
			map.put(
				"seoKeywords", String.valueOf(seoSettings.getSeoKeywords()));
		}

		if (seoSettings.getSeoKeywords_i18n() == null) {
			map.put("seoKeywords_i18n", null);
		}
		else {
			map.put(
				"seoKeywords_i18n",
				String.valueOf(seoSettings.getSeoKeywords_i18n()));
		}

		return map;
	}

	public static class SEOSettingsJSONParser
		extends BaseJSONParser<SEOSettings> {

		@Override
		protected SEOSettings createDTO() {
			return new SEOSettings();
		}

		@Override
		protected SEOSettings[] createDTOArray(int size) {
			return new SEOSettings[size];
		}

		@Override
		protected void setField(
			SEOSettings seoSettings, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "customCanonicalURL")) {
				if (jsonParserFieldValue != null) {
					seoSettings.setCustomCanonicalURL(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "customCanonicalURL_i18n")) {

				if (jsonParserFieldValue != null) {
					seoSettings.setCustomCanonicalURL_i18n(
						(Map)SEOSettingsSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					seoSettings.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description_i18n")) {
				if (jsonParserFieldValue != null) {
					seoSettings.setDescription_i18n(
						(Map)SEOSettingsSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "htmlTitle")) {
				if (jsonParserFieldValue != null) {
					seoSettings.setHtmlTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "htmlTitle_i18n")) {
				if (jsonParserFieldValue != null) {
					seoSettings.setHtmlTitle_i18n(
						(Map)SEOSettingsSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "robots")) {
				if (jsonParserFieldValue != null) {
					seoSettings.setRobots((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "robots_i18n")) {
				if (jsonParserFieldValue != null) {
					seoSettings.setRobots_i18n(
						(Map)SEOSettingsSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "seoKeywords")) {
				if (jsonParserFieldValue != null) {
					seoSettings.setSeoKeywords((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "seoKeywords_i18n")) {
				if (jsonParserFieldValue != null) {
					seoSettings.setSeoKeywords_i18n(
						(Map)SEOSettingsSerDes.toMap(
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