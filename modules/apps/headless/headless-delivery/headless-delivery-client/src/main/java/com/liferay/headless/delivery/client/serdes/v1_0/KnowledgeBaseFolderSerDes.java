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

import com.liferay.headless.delivery.client.dto.v1_0.CustomField;
import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
public class KnowledgeBaseFolderSerDes {

	public static KnowledgeBaseFolder toDTO(String json) {
		KnowledgeBaseFolderJSONParser knowledgeBaseFolderJSONParser =
			new KnowledgeBaseFolderJSONParser();

		return knowledgeBaseFolderJSONParser.parseToDTO(json);
	}

	public static KnowledgeBaseFolder[] toDTOs(String json) {
		KnowledgeBaseFolderJSONParser knowledgeBaseFolderJSONParser =
			new KnowledgeBaseFolderJSONParser();

		return knowledgeBaseFolderJSONParser.parseToDTOs(json);
	}

	public static String toJSON(KnowledgeBaseFolder knowledgeBaseFolder) {
		if (knowledgeBaseFolder == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (knowledgeBaseFolder.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\": ");

			sb.append(String.valueOf(knowledgeBaseFolder.getCreator()));
		}

		if (knowledgeBaseFolder.getCustomFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append("[");

			for (int i = 0; i < knowledgeBaseFolder.getCustomFields().length;
				 i++) {

				sb.append(
					String.valueOf(knowledgeBaseFolder.getCustomFields()[i]));

				if ((i + 1) < knowledgeBaseFolder.getCustomFields().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (knowledgeBaseFolder.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					knowledgeBaseFolder.getDateCreated()));

			sb.append("\"");
		}

		if (knowledgeBaseFolder.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					knowledgeBaseFolder.getDateModified()));

			sb.append("\"");
		}

		if (knowledgeBaseFolder.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(knowledgeBaseFolder.getDescription()));

			sb.append("\"");
		}

		if (knowledgeBaseFolder.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(knowledgeBaseFolder.getId());
		}

		if (knowledgeBaseFolder.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(knowledgeBaseFolder.getName()));

			sb.append("\"");
		}

		if (knowledgeBaseFolder.getNumberOfKnowledgeBaseArticles() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfKnowledgeBaseArticles\": ");

			sb.append(knowledgeBaseFolder.getNumberOfKnowledgeBaseArticles());
		}

		if (knowledgeBaseFolder.getNumberOfKnowledgeBaseFolders() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfKnowledgeBaseFolders\": ");

			sb.append(knowledgeBaseFolder.getNumberOfKnowledgeBaseFolders());
		}

		if (knowledgeBaseFolder.getParentKnowledgeBaseFolder() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parentKnowledgeBaseFolder\": ");

			sb.append(
				String.valueOf(
					knowledgeBaseFolder.getParentKnowledgeBaseFolder()));
		}

		if (knowledgeBaseFolder.getParentKnowledgeBaseFolderId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parentKnowledgeBaseFolderId\": ");

			sb.append(knowledgeBaseFolder.getParentKnowledgeBaseFolderId());
		}

		if (knowledgeBaseFolder.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\": ");

			sb.append(knowledgeBaseFolder.getSiteId());
		}

		if (knowledgeBaseFolder.getViewableBy() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"viewableBy\": ");

			sb.append("\"");

			sb.append(knowledgeBaseFolder.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		KnowledgeBaseFolderJSONParser knowledgeBaseFolderJSONParser =
			new KnowledgeBaseFolderJSONParser();

		return knowledgeBaseFolderJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		KnowledgeBaseFolder knowledgeBaseFolder) {

		if (knowledgeBaseFolder == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (knowledgeBaseFolder.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put(
				"creator", String.valueOf(knowledgeBaseFolder.getCreator()));
		}

		if (knowledgeBaseFolder.getCustomFields() == null) {
			map.put("customFields", null);
		}
		else {
			map.put(
				"customFields",
				String.valueOf(knowledgeBaseFolder.getCustomFields()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(
				knowledgeBaseFolder.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(
				knowledgeBaseFolder.getDateModified()));

		if (knowledgeBaseFolder.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(knowledgeBaseFolder.getDescription()));
		}

		if (knowledgeBaseFolder.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(knowledgeBaseFolder.getId()));
		}

		if (knowledgeBaseFolder.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(knowledgeBaseFolder.getName()));
		}

		if (knowledgeBaseFolder.getNumberOfKnowledgeBaseArticles() == null) {
			map.put("numberOfKnowledgeBaseArticles", null);
		}
		else {
			map.put(
				"numberOfKnowledgeBaseArticles",
				String.valueOf(
					knowledgeBaseFolder.getNumberOfKnowledgeBaseArticles()));
		}

		if (knowledgeBaseFolder.getNumberOfKnowledgeBaseFolders() == null) {
			map.put("numberOfKnowledgeBaseFolders", null);
		}
		else {
			map.put(
				"numberOfKnowledgeBaseFolders",
				String.valueOf(
					knowledgeBaseFolder.getNumberOfKnowledgeBaseFolders()));
		}

		if (knowledgeBaseFolder.getParentKnowledgeBaseFolder() == null) {
			map.put("parentKnowledgeBaseFolder", null);
		}
		else {
			map.put(
				"parentKnowledgeBaseFolder",
				String.valueOf(
					knowledgeBaseFolder.getParentKnowledgeBaseFolder()));
		}

		if (knowledgeBaseFolder.getParentKnowledgeBaseFolderId() == null) {
			map.put("parentKnowledgeBaseFolderId", null);
		}
		else {
			map.put(
				"parentKnowledgeBaseFolderId",
				String.valueOf(
					knowledgeBaseFolder.getParentKnowledgeBaseFolderId()));
		}

		if (knowledgeBaseFolder.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(knowledgeBaseFolder.getSiteId()));
		}

		if (knowledgeBaseFolder.getViewableBy() == null) {
			map.put("viewableBy", null);
		}
		else {
			map.put(
				"viewableBy",
				String.valueOf(knowledgeBaseFolder.getViewableBy()));
		}

		return map;
	}

	public static class KnowledgeBaseFolderJSONParser
		extends BaseJSONParser<KnowledgeBaseFolder> {

		@Override
		protected KnowledgeBaseFolder createDTO() {
			return new KnowledgeBaseFolder();
		}

		@Override
		protected KnowledgeBaseFolder[] createDTOArray(int size) {
			return new KnowledgeBaseFolder[size];
		}

		@Override
		protected void setField(
			KnowledgeBaseFolder knowledgeBaseFolder, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolder.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "customFields")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolder.setCustomFields(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> CustomFieldSerDes.toDTO((String)object)
						).toArray(
							size -> new CustomField[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolder.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolder.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolder.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolder.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolder.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfKnowledgeBaseArticles")) {

				if (jsonParserFieldValue != null) {
					knowledgeBaseFolder.setNumberOfKnowledgeBaseArticles(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfKnowledgeBaseFolders")) {

				if (jsonParserFieldValue != null) {
					knowledgeBaseFolder.setNumberOfKnowledgeBaseFolders(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "parentKnowledgeBaseFolder")) {

				if (jsonParserFieldValue != null) {
					knowledgeBaseFolder.setParentKnowledgeBaseFolder(
						ParentKnowledgeBaseFolderSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "parentKnowledgeBaseFolderId")) {

				if (jsonParserFieldValue != null) {
					knowledgeBaseFolder.setParentKnowledgeBaseFolderId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolder.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolder.setViewableBy(
						KnowledgeBaseFolder.ViewableBy.create(
							(String)jsonParserFieldValue));
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