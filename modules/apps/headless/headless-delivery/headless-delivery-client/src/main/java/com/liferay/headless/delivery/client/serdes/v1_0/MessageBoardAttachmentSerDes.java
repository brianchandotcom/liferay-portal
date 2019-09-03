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

import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardAttachment;
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
public class MessageBoardAttachmentSerDes {

	public static MessageBoardAttachment toDTO(String json) {
		MessageBoardAttachmentJSONParser messageBoardAttachmentJSONParser =
			new MessageBoardAttachmentJSONParser();

		return messageBoardAttachmentJSONParser.parseToDTO(json);
	}

	public static MessageBoardAttachment[] toDTOs(String json) {
		MessageBoardAttachmentJSONParser messageBoardAttachmentJSONParser =
			new MessageBoardAttachmentJSONParser();

		return messageBoardAttachmentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(MessageBoardAttachment messageBoardAttachment) {
		if (messageBoardAttachment == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (messageBoardAttachment.getContentUrl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentUrl\": ");

			sb.append("\"");

			sb.append(_escape(messageBoardAttachment.getContentUrl()));

			sb.append("\"");
		}

		if (messageBoardAttachment.getEncodingFormat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"encodingFormat\": ");

			sb.append("\"");

			sb.append(_escape(messageBoardAttachment.getEncodingFormat()));

			sb.append("\"");
		}

		if (messageBoardAttachment.getFileExtension() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fileExtension\": ");

			sb.append("\"");

			sb.append(_escape(messageBoardAttachment.getFileExtension()));

			sb.append("\"");
		}

		if (messageBoardAttachment.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(messageBoardAttachment.getId());
		}

		if (messageBoardAttachment.getSizeInBytes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sizeInBytes\": ");

			sb.append(messageBoardAttachment.getSizeInBytes());
		}

		if (messageBoardAttachment.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(messageBoardAttachment.getTitle()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		MessageBoardAttachmentJSONParser messageBoardAttachmentJSONParser =
			new MessageBoardAttachmentJSONParser();

		return messageBoardAttachmentJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		MessageBoardAttachment messageBoardAttachment) {

		if (messageBoardAttachment == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (messageBoardAttachment.getContentUrl() == null) {
			map.put("contentUrl", null);
		}
		else {
			map.put(
				"contentUrl",
				String.valueOf(messageBoardAttachment.getContentUrl()));
		}

		if (messageBoardAttachment.getEncodingFormat() == null) {
			map.put("encodingFormat", null);
		}
		else {
			map.put(
				"encodingFormat",
				String.valueOf(messageBoardAttachment.getEncodingFormat()));
		}

		if (messageBoardAttachment.getFileExtension() == null) {
			map.put("fileExtension", null);
		}
		else {
			map.put(
				"fileExtension",
				String.valueOf(messageBoardAttachment.getFileExtension()));
		}

		if (messageBoardAttachment.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(messageBoardAttachment.getId()));
		}

		if (messageBoardAttachment.getSizeInBytes() == null) {
			map.put("sizeInBytes", null);
		}
		else {
			map.put(
				"sizeInBytes",
				String.valueOf(messageBoardAttachment.getSizeInBytes()));
		}

		if (messageBoardAttachment.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(messageBoardAttachment.getTitle()));
		}

		return map;
	}

	public static class MessageBoardAttachmentJSONParser
		extends BaseJSONParser<MessageBoardAttachment> {

		@Override
		protected MessageBoardAttachment createDTO() {
			return new MessageBoardAttachment();
		}

		@Override
		protected MessageBoardAttachment[] createDTOArray(int size) {
			return new MessageBoardAttachment[size];
		}

		@Override
		protected void setField(
			MessageBoardAttachment messageBoardAttachment,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contentUrl")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachment.setContentUrl(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "encodingFormat")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachment.setEncodingFormat(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fileExtension")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachment.setFileExtension(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachment.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sizeInBytes")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachment.setSizeInBytes(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachment.setTitle(
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