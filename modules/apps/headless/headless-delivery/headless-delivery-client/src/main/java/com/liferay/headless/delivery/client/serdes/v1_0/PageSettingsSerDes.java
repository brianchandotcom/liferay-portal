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

import com.liferay.headless.delivery.client.dto.v1_0.CustomMetaTag;
import com.liferay.headless.delivery.client.dto.v1_0.PageSettings;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class PageSettingsSerDes {

	public static PageSettings toDTO(String json) {
		PageSettingsJSONParser pageSettingsJSONParser =
			new PageSettingsJSONParser();

		return pageSettingsJSONParser.parseToDTO(json);
	}

	public static PageSettings[] toDTOs(String json) {
		PageSettingsJSONParser pageSettingsJSONParser =
			new PageSettingsJSONParser();

		return pageSettingsJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PageSettings pageSettings) {
		if (pageSettings == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (pageSettings.getCustomMetaTags() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customMetaTags\": ");

			sb.append("[");

			for (int i = 0; i < pageSettings.getCustomMetaTags().length; i++) {
				sb.append(String.valueOf(pageSettings.getCustomMetaTags()[i]));

				if ((i + 1) < pageSettings.getCustomMetaTags().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (pageSettings.getHiddenFromNavigation() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"hiddenFromNavigation\": ");

			sb.append(pageSettings.getHiddenFromNavigation());
		}

		if (pageSettings.getOpenGraphSettings() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"openGraphSettings\": ");

			sb.append(String.valueOf(pageSettings.getOpenGraphSettings()));
		}

		if (pageSettings.getSeoSettings() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"seoSettings\": ");

			sb.append(String.valueOf(pageSettings.getSeoSettings()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PageSettingsJSONParser pageSettingsJSONParser =
			new PageSettingsJSONParser();

		return pageSettingsJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(PageSettings pageSettings) {
		if (pageSettings == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (pageSettings.getCustomMetaTags() == null) {
			map.put("customMetaTags", null);
		}
		else {
			map.put(
				"customMetaTags",
				String.valueOf(pageSettings.getCustomMetaTags()));
		}

		if (pageSettings.getHiddenFromNavigation() == null) {
			map.put("hiddenFromNavigation", null);
		}
		else {
			map.put(
				"hiddenFromNavigation",
				String.valueOf(pageSettings.getHiddenFromNavigation()));
		}

		if (pageSettings.getOpenGraphSettings() == null) {
			map.put("openGraphSettings", null);
		}
		else {
			map.put(
				"openGraphSettings",
				String.valueOf(pageSettings.getOpenGraphSettings()));
		}

		if (pageSettings.getSeoSettings() == null) {
			map.put("seoSettings", null);
		}
		else {
			map.put(
				"seoSettings", String.valueOf(pageSettings.getSeoSettings()));
		}

		return map;
	}

	public static class PageSettingsJSONParser
		extends BaseJSONParser<PageSettings> {

		@Override
		protected PageSettings createDTO() {
			return new PageSettings();
		}

		@Override
		protected PageSettings[] createDTOArray(int size) {
			return new PageSettings[size];
		}

		@Override
		protected void setField(
			PageSettings pageSettings, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "customMetaTags")) {
				if (jsonParserFieldValue != null) {
					pageSettings.setCustomMetaTags(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> CustomMetaTagSerDes.toDTO((String)object)
						).toArray(
							size -> new CustomMetaTag[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "hiddenFromNavigation")) {

				if (jsonParserFieldValue != null) {
					pageSettings.setHiddenFromNavigation(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "openGraphSettings")) {
				if (jsonParserFieldValue != null) {
					pageSettings.setOpenGraphSettings(
						OpenGraphSettingsSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "seoSettings")) {
				if (jsonParserFieldValue != null) {
					pageSettings.setSeoSettings(
						SEOSettingsSerDes.toDTO((String)jsonParserFieldValue));
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