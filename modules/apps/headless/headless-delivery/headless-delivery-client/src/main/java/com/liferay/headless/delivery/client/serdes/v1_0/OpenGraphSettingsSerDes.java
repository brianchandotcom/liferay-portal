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

import com.liferay.headless.delivery.client.dto.v1_0.OpenGraphSettings;
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
public class OpenGraphSettingsSerDes {

	public static OpenGraphSettings toDTO(String json) {
		OpenGraphSettingsJSONParser openGraphSettingsJSONParser =
			new OpenGraphSettingsJSONParser();

		return openGraphSettingsJSONParser.parseToDTO(json);
	}

	public static OpenGraphSettings[] toDTOs(String json) {
		OpenGraphSettingsJSONParser openGraphSettingsJSONParser =
			new OpenGraphSettingsJSONParser();

		return openGraphSettingsJSONParser.parseToDTOs(json);
	}

	public static String toJSON(OpenGraphSettings openGraphSettings) {
		if (openGraphSettings == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (openGraphSettings.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(openGraphSettings.getDescription()));

			sb.append("\"");
		}

		if (openGraphSettings.getDescription_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description_i18n\": ");

			sb.append(_toJSON(openGraphSettings.getDescription_i18n()));
		}

		if (openGraphSettings.getImage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"image\": ");

			sb.append(String.valueOf(openGraphSettings.getImage()));
		}

		if (openGraphSettings.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(openGraphSettings.getTitle()));

			sb.append("\"");
		}

		if (openGraphSettings.getTitle_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title_i18n\": ");

			sb.append(_toJSON(openGraphSettings.getTitle_i18n()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		OpenGraphSettingsJSONParser openGraphSettingsJSONParser =
			new OpenGraphSettingsJSONParser();

		return openGraphSettingsJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		OpenGraphSettings openGraphSettings) {

		if (openGraphSettings == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (openGraphSettings.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(openGraphSettings.getDescription()));
		}

		if (openGraphSettings.getDescription_i18n() == null) {
			map.put("description_i18n", null);
		}
		else {
			map.put(
				"description_i18n",
				String.valueOf(openGraphSettings.getDescription_i18n()));
		}

		if (openGraphSettings.getImage() == null) {
			map.put("image", null);
		}
		else {
			map.put("image", String.valueOf(openGraphSettings.getImage()));
		}

		if (openGraphSettings.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(openGraphSettings.getTitle()));
		}

		if (openGraphSettings.getTitle_i18n() == null) {
			map.put("title_i18n", null);
		}
		else {
			map.put(
				"title_i18n",
				String.valueOf(openGraphSettings.getTitle_i18n()));
		}

		return map;
	}

	public static class OpenGraphSettingsJSONParser
		extends BaseJSONParser<OpenGraphSettings> {

		@Override
		protected OpenGraphSettings createDTO() {
			return new OpenGraphSettings();
		}

		@Override
		protected OpenGraphSettings[] createDTOArray(int size) {
			return new OpenGraphSettings[size];
		}

		@Override
		protected void setField(
			OpenGraphSettings openGraphSettings, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					openGraphSettings.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description_i18n")) {
				if (jsonParserFieldValue != null) {
					openGraphSettings.setDescription_i18n(
						(Map)OpenGraphSettingsSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "image")) {
				if (jsonParserFieldValue != null) {
					openGraphSettings.setImage(
						ContentDocumentSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					openGraphSettings.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title_i18n")) {
				if (jsonParserFieldValue != null) {
					openGraphSettings.setTitle_i18n(
						(Map)OpenGraphSettingsSerDes.toMap(
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