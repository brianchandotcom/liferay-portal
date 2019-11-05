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

import com.liferay.headless.delivery.client.dto.v1_0.StructuredContentLink;
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
public class StructuredContentLinkSerDes {

	public static StructuredContentLink toDTO(String json) {
		StructuredContentLinkJSONParser structuredContentLinkJSONParser =
			new StructuredContentLinkJSONParser();

		return structuredContentLinkJSONParser.parseToDTO(json);
	}

	public static StructuredContentLink[] toDTOs(String json) {
		StructuredContentLinkJSONParser structuredContentLinkJSONParser =
			new StructuredContentLinkJSONParser();

		return structuredContentLinkJSONParser.parseToDTOs(json);
	}

	public static String toJSON(StructuredContentLink structuredContentLink) {
		if (structuredContentLink == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (structuredContentLink.getContentType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentType\": ");

			sb.append("\"");

			sb.append(_escape(structuredContentLink.getContentType()));

			sb.append("\"");
		}

		if (structuredContentLink.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(structuredContentLink.getId());
		}

		if (structuredContentLink.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(structuredContentLink.getTitle()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		StructuredContentLinkJSONParser structuredContentLinkJSONParser =
			new StructuredContentLinkJSONParser();

		return structuredContentLinkJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		StructuredContentLink structuredContentLink) {

		if (structuredContentLink == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (structuredContentLink.getContentType() == null) {
			map.put("contentType", null);
		}
		else {
			map.put(
				"contentType",
				String.valueOf(structuredContentLink.getContentType()));
		}

		if (structuredContentLink.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(structuredContentLink.getId()));
		}

		if (structuredContentLink.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(structuredContentLink.getTitle()));
		}

		return map;
	}

	public static class StructuredContentLinkJSONParser
		extends BaseJSONParser<StructuredContentLink> {

		@Override
		protected StructuredContentLink createDTO() {
			return new StructuredContentLink();
		}

		@Override
		protected StructuredContentLink[] createDTOArray(int size) {
			return new StructuredContentLink[size];
		}

		@Override
		protected void setField(
			StructuredContentLink structuredContentLink,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contentType")) {
				if (jsonParserFieldValue != null) {
					structuredContentLink.setContentType(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					structuredContentLink.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					structuredContentLink.setTitle(
						(String)jsonParserFieldValue);
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

		string = string.replace("\\", "\\\\");

		return string.replace("\"", "\\\"");
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