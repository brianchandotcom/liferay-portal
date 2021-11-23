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

import com.liferay.headless.delivery.client.dto.v1_0.ContentElement;
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
public class ContentElementSerDes {

	public static ContentElement toDTO(String json) {
		ContentElementJSONParser contentElementJSONParser =
			new ContentElementJSONParser();

		return contentElementJSONParser.parseToDTO(json);
	}

	public static ContentElement[] toDTOs(String json) {
		ContentElementJSONParser contentElementJSONParser =
			new ContentElementJSONParser();

		return contentElementJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContentElement contentElement) {
		if (contentElement == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (contentElement.getContent() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"content\": ");

			if (contentElement.getContent() instanceof String) {
				sb.append("\"");
				sb.append((String)contentElement.getContent());
				sb.append("\"");
			}
			else {
				sb.append(contentElement.getContent());
			}
		}

		if (contentElement.getContentType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentType\": ");

			sb.append("\"");

			sb.append(_escape(contentElement.getContentType()));

			sb.append("\"");
		}

		if (contentElement.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(contentElement.getId());
		}

		if (contentElement.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(contentElement.getTitle()));

			sb.append("\"");
		}

		if (contentElement.getTitle_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title_i18n\": ");

			sb.append(_toJSON(contentElement.getTitle_i18n()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ContentElementJSONParser contentElementJSONParser =
			new ContentElementJSONParser();

		return contentElementJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ContentElement contentElement) {
		if (contentElement == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (contentElement.getContent() == null) {
			map.put("content", null);
		}
		else {
			map.put("content", String.valueOf(contentElement.getContent()));
		}

		if (contentElement.getContentType() == null) {
			map.put("contentType", null);
		}
		else {
			map.put(
				"contentType", String.valueOf(contentElement.getContentType()));
		}

		if (contentElement.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(contentElement.getId()));
		}

		if (contentElement.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(contentElement.getTitle()));
		}

		if (contentElement.getTitle_i18n() == null) {
			map.put("title_i18n", null);
		}
		else {
			map.put(
				"title_i18n", String.valueOf(contentElement.getTitle_i18n()));
		}

		return map;
	}

	public static class ContentElementJSONParser
		extends BaseJSONParser<ContentElement> {

		@Override
		protected ContentElement createDTO() {
			return new ContentElement();
		}

		@Override
		protected ContentElement[] createDTOArray(int size) {
			return new ContentElement[size];
		}

		@Override
		protected void setField(
			ContentElement contentElement, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "content")) {
				if (jsonParserFieldValue != null) {
					contentElement.setContent((Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contentType")) {
				if (jsonParserFieldValue != null) {
					contentElement.setContentType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					contentElement.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					contentElement.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title_i18n")) {
				if (jsonParserFieldValue != null) {
					contentElement.setTitle_i18n(
						(Map)ContentElementSerDes.toMap(
							(String)jsonParserFieldValue));
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