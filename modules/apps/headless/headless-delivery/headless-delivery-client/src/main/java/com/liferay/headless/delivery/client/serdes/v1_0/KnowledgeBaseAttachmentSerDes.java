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

import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseAttachment;
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
public class KnowledgeBaseAttachmentSerDes {

	public static KnowledgeBaseAttachment toDTO(String json) {
		KnowledgeBaseAttachmentJSONParser knowledgeBaseAttachmentJSONParser =
			new KnowledgeBaseAttachmentJSONParser();

		return knowledgeBaseAttachmentJSONParser.parseToDTO(json);
	}

	public static KnowledgeBaseAttachment[] toDTOs(String json) {
		KnowledgeBaseAttachmentJSONParser knowledgeBaseAttachmentJSONParser =
			new KnowledgeBaseAttachmentJSONParser();

		return knowledgeBaseAttachmentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		KnowledgeBaseAttachment knowledgeBaseAttachment) {

		if (knowledgeBaseAttachment == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (knowledgeBaseAttachment.getContentUrl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentUrl\": ");

			sb.append("\"");

			sb.append(_escape(knowledgeBaseAttachment.getContentUrl()));

			sb.append("\"");
		}

		if (knowledgeBaseAttachment.getEncodingFormat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"encodingFormat\": ");

			sb.append("\"");

			sb.append(_escape(knowledgeBaseAttachment.getEncodingFormat()));

			sb.append("\"");
		}

		if (knowledgeBaseAttachment.getFileExtension() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fileExtension\": ");

			sb.append("\"");

			sb.append(_escape(knowledgeBaseAttachment.getFileExtension()));

			sb.append("\"");
		}

		if (knowledgeBaseAttachment.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(knowledgeBaseAttachment.getId());
		}

		if (knowledgeBaseAttachment.getSizeInBytes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sizeInBytes\": ");

			sb.append(knowledgeBaseAttachment.getSizeInBytes());
		}

		if (knowledgeBaseAttachment.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(knowledgeBaseAttachment.getTitle()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		KnowledgeBaseAttachmentJSONParser knowledgeBaseAttachmentJSONParser =
			new KnowledgeBaseAttachmentJSONParser();

		return knowledgeBaseAttachmentJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		KnowledgeBaseAttachment knowledgeBaseAttachment) {

		if (knowledgeBaseAttachment == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (knowledgeBaseAttachment.getContentUrl() == null) {
			map.put("contentUrl", null);
		}
		else {
			map.put(
				"contentUrl",
				String.valueOf(knowledgeBaseAttachment.getContentUrl()));
		}

		if (knowledgeBaseAttachment.getEncodingFormat() == null) {
			map.put("encodingFormat", null);
		}
		else {
			map.put(
				"encodingFormat",
				String.valueOf(knowledgeBaseAttachment.getEncodingFormat()));
		}

		if (knowledgeBaseAttachment.getFileExtension() == null) {
			map.put("fileExtension", null);
		}
		else {
			map.put(
				"fileExtension",
				String.valueOf(knowledgeBaseAttachment.getFileExtension()));
		}

		if (knowledgeBaseAttachment.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(knowledgeBaseAttachment.getId()));
		}

		if (knowledgeBaseAttachment.getSizeInBytes() == null) {
			map.put("sizeInBytes", null);
		}
		else {
			map.put(
				"sizeInBytes",
				String.valueOf(knowledgeBaseAttachment.getSizeInBytes()));
		}

		if (knowledgeBaseAttachment.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put(
				"title", String.valueOf(knowledgeBaseAttachment.getTitle()));
		}

		return map;
	}

	public static class KnowledgeBaseAttachmentJSONParser
		extends BaseJSONParser<KnowledgeBaseAttachment> {

		@Override
		protected KnowledgeBaseAttachment createDTO() {
			return new KnowledgeBaseAttachment();
		}

		@Override
		protected KnowledgeBaseAttachment[] createDTOArray(int size) {
			return new KnowledgeBaseAttachment[size];
		}

		@Override
		protected void setField(
			KnowledgeBaseAttachment knowledgeBaseAttachment,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contentUrl")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseAttachment.setContentUrl(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "encodingFormat")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseAttachment.setEncodingFormat(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fileExtension")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseAttachment.setFileExtension(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseAttachment.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sizeInBytes")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseAttachment.setSizeInBytes(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseAttachment.setTitle(
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