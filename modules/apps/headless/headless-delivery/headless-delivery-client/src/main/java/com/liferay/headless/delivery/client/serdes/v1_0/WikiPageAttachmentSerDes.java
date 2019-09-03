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

import com.liferay.headless.delivery.client.dto.v1_0.WikiPageAttachment;
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
public class WikiPageAttachmentSerDes {

	public static WikiPageAttachment toDTO(String json) {
		WikiPageAttachmentJSONParser wikiPageAttachmentJSONParser =
			new WikiPageAttachmentJSONParser();

		return wikiPageAttachmentJSONParser.parseToDTO(json);
	}

	public static WikiPageAttachment[] toDTOs(String json) {
		WikiPageAttachmentJSONParser wikiPageAttachmentJSONParser =
			new WikiPageAttachmentJSONParser();

		return wikiPageAttachmentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(WikiPageAttachment wikiPageAttachment) {
		if (wikiPageAttachment == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (wikiPageAttachment.getContentUrl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentUrl\": ");

			sb.append("\"");

			sb.append(_escape(wikiPageAttachment.getContentUrl()));

			sb.append("\"");
		}

		if (wikiPageAttachment.getEncodingFormat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"encodingFormat\": ");

			sb.append("\"");

			sb.append(_escape(wikiPageAttachment.getEncodingFormat()));

			sb.append("\"");
		}

		if (wikiPageAttachment.getFileExtension() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fileExtension\": ");

			sb.append("\"");

			sb.append(_escape(wikiPageAttachment.getFileExtension()));

			sb.append("\"");
		}

		if (wikiPageAttachment.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(wikiPageAttachment.getId());
		}

		if (wikiPageAttachment.getSizeInBytes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sizeInBytes\": ");

			sb.append(wikiPageAttachment.getSizeInBytes());
		}

		if (wikiPageAttachment.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(wikiPageAttachment.getTitle()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		WikiPageAttachmentJSONParser wikiPageAttachmentJSONParser =
			new WikiPageAttachmentJSONParser();

		return wikiPageAttachmentJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		WikiPageAttachment wikiPageAttachment) {

		if (wikiPageAttachment == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (wikiPageAttachment.getContentUrl() == null) {
			map.put("contentUrl", null);
		}
		else {
			map.put(
				"contentUrl",
				String.valueOf(wikiPageAttachment.getContentUrl()));
		}

		if (wikiPageAttachment.getEncodingFormat() == null) {
			map.put("encodingFormat", null);
		}
		else {
			map.put(
				"encodingFormat",
				String.valueOf(wikiPageAttachment.getEncodingFormat()));
		}

		if (wikiPageAttachment.getFileExtension() == null) {
			map.put("fileExtension", null);
		}
		else {
			map.put(
				"fileExtension",
				String.valueOf(wikiPageAttachment.getFileExtension()));
		}

		if (wikiPageAttachment.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(wikiPageAttachment.getId()));
		}

		if (wikiPageAttachment.getSizeInBytes() == null) {
			map.put("sizeInBytes", null);
		}
		else {
			map.put(
				"sizeInBytes",
				String.valueOf(wikiPageAttachment.getSizeInBytes()));
		}

		if (wikiPageAttachment.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(wikiPageAttachment.getTitle()));
		}

		return map;
	}

	public static class WikiPageAttachmentJSONParser
		extends BaseJSONParser<WikiPageAttachment> {

		@Override
		protected WikiPageAttachment createDTO() {
			return new WikiPageAttachment();
		}

		@Override
		protected WikiPageAttachment[] createDTOArray(int size) {
			return new WikiPageAttachment[size];
		}

		@Override
		protected void setField(
			WikiPageAttachment wikiPageAttachment, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contentUrl")) {
				if (jsonParserFieldValue != null) {
					wikiPageAttachment.setContentUrl(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "encodingFormat")) {
				if (jsonParserFieldValue != null) {
					wikiPageAttachment.setEncodingFormat(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fileExtension")) {
				if (jsonParserFieldValue != null) {
					wikiPageAttachment.setFileExtension(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					wikiPageAttachment.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sizeInBytes")) {
				if (jsonParserFieldValue != null) {
					wikiPageAttachment.setSizeInBytes(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					wikiPageAttachment.setTitle((String)jsonParserFieldValue);
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