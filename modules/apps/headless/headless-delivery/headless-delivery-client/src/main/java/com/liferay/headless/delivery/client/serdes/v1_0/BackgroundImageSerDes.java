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

import com.liferay.headless.delivery.client.dto.v1_0.BackgroundImage;
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
public class BackgroundImageSerDes {

	public static BackgroundImage toDTO(String json) {
		BackgroundImageJSONParser backgroundImageJSONParser =
			new BackgroundImageJSONParser();

		return backgroundImageJSONParser.parseToDTO(json);
	}

	public static BackgroundImage[] toDTOs(String json) {
		BackgroundImageJSONParser backgroundImageJSONParser =
			new BackgroundImageJSONParser();

		return backgroundImageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(BackgroundImage backgroundImage) {
		if (backgroundImage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (backgroundImage.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			if (backgroundImage.getDescription() instanceof String) {
				sb.append("\"");
				sb.append((String)backgroundImage.getDescription());
				sb.append("\"");
			}
			else {
				sb.append(backgroundImage.getDescription());
			}
		}

		if (backgroundImage.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			if (backgroundImage.getTitle() instanceof String) {
				sb.append("\"");
				sb.append((String)backgroundImage.getTitle());
				sb.append("\"");
			}
			else {
				sb.append(backgroundImage.getTitle());
			}
		}

		if (backgroundImage.getUrl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"url\": ");

			if (backgroundImage.getUrl() instanceof String) {
				sb.append("\"");
				sb.append((String)backgroundImage.getUrl());
				sb.append("\"");
			}
			else {
				sb.append(backgroundImage.getUrl());
			}
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		BackgroundImageJSONParser backgroundImageJSONParser =
			new BackgroundImageJSONParser();

		return backgroundImageJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(BackgroundImage backgroundImage) {
		if (backgroundImage == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (backgroundImage.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(backgroundImage.getDescription()));
		}

		if (backgroundImage.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(backgroundImage.getTitle()));
		}

		if (backgroundImage.getUrl() == null) {
			map.put("url", null);
		}
		else {
			map.put("url", String.valueOf(backgroundImage.getUrl()));
		}

		return map;
	}

	public static class BackgroundImageJSONParser
		extends BaseJSONParser<BackgroundImage> {

		@Override
		protected BackgroundImage createDTO() {
			return new BackgroundImage();
		}

		@Override
		protected BackgroundImage[] createDTOArray(int size) {
			return new BackgroundImage[size];
		}

		@Override
		protected void setField(
			BackgroundImage backgroundImage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					backgroundImage.setDescription(
						(Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					backgroundImage.setTitle((Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "url")) {
				if (jsonParserFieldValue != null) {
					backgroundImage.setUrl((Object)jsonParserFieldValue);
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