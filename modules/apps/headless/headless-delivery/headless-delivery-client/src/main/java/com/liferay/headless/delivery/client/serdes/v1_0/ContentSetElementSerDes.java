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

import com.liferay.headless.delivery.client.dto.v1_0.ContentSetElement;
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
public class ContentSetElementSerDes {

	public static ContentSetElement toDTO(String json) {
		ContentSetElementJSONParser contentSetElementJSONParser =
			new ContentSetElementJSONParser();

		return contentSetElementJSONParser.parseToDTO(json);
	}

	public static ContentSetElement[] toDTOs(String json) {
		ContentSetElementJSONParser contentSetElementJSONParser =
			new ContentSetElementJSONParser();

		return contentSetElementJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContentSetElement contentSetElement) {
		if (contentSetElement == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (contentSetElement.getContent() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"content\": ");

			sb.append("\"");

			sb.append(_escape(contentSetElement.getContent()));

			sb.append("\"");
		}

		if (contentSetElement.getContentType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentType\": ");

			sb.append("\"");

			sb.append(_escape(contentSetElement.getContentType()));

			sb.append("\"");
		}

		if (contentSetElement.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(contentSetElement.getId());
		}

		if (contentSetElement.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(contentSetElement.getTitle()));

			sb.append("\"");
		}

		if (contentSetElement.getTitle_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title_i18n\": ");

			sb.append("\"");

			sb.append(_escape(contentSetElement.getTitle_i18n()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ContentSetElementJSONParser contentSetElementJSONParser =
			new ContentSetElementJSONParser();

		return contentSetElementJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ContentSetElement contentSetElement) {

		if (contentSetElement == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (contentSetElement.getContent() == null) {
			map.put("content", null);
		}
		else {
			map.put("content", String.valueOf(contentSetElement.getContent()));
		}

		if (contentSetElement.getContentType() == null) {
			map.put("contentType", null);
		}
		else {
			map.put(
				"contentType",
				String.valueOf(contentSetElement.getContentType()));
		}

		if (contentSetElement.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(contentSetElement.getId()));
		}

		if (contentSetElement.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(contentSetElement.getTitle()));
		}

		if (contentSetElement.getTitle_i18n() == null) {
			map.put("title_i18n", null);
		}
		else {
			map.put(
				"title_i18n",
				String.valueOf(contentSetElement.getTitle_i18n()));
		}

		return map;
	}

	public static class ContentSetElementJSONParser
		extends BaseJSONParser<ContentSetElement> {

		@Override
		protected ContentSetElement createDTO() {
			return new ContentSetElement();
		}

		@Override
		protected ContentSetElement[] createDTOArray(int size) {
			return new ContentSetElement[size];
		}

		@Override
		protected void setField(
			ContentSetElement contentSetElement, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "content")) {
				if (jsonParserFieldValue != null) {
					contentSetElement.setContent((Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contentType")) {
				if (jsonParserFieldValue != null) {
					contentSetElement.setContentType(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					contentSetElement.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					contentSetElement.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title_i18n")) {
				if (jsonParserFieldValue != null) {
					contentSetElement.setTitle_i18n(
						(Object)jsonParserFieldValue);
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