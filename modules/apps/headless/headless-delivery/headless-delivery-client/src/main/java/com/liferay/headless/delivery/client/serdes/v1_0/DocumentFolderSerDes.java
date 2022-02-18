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
import com.liferay.headless.delivery.client.dto.v1_0.DocumentFolder;
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
public class DocumentFolderSerDes {

	public static DocumentFolder toDTO(String json) {
		DocumentFolderJSONParser documentFolderJSONParser =
			new DocumentFolderJSONParser();

		return documentFolderJSONParser.parseToDTO(json);
	}

	public static DocumentFolder[] toDTOs(String json) {
		DocumentFolderJSONParser documentFolderJSONParser =
			new DocumentFolderJSONParser();

		return documentFolderJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DocumentFolder documentFolder) {
		if (documentFolder == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (documentFolder.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(documentFolder.getActions()));
		}

		if (documentFolder.getAssetLibraryKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"assetLibraryKey\": ");

			sb.append("\"");

			sb.append(_escape(documentFolder.getAssetLibraryKey()));

			sb.append("\"");
		}

		if (documentFolder.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\": ");

			sb.append(String.valueOf(documentFolder.getCreator()));
		}

		if (documentFolder.getCustomFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append("[");

			for (int i = 0; i < documentFolder.getCustomFields().length; i++) {
				sb.append(String.valueOf(documentFolder.getCustomFields()[i]));

				if ((i + 1) < documentFolder.getCustomFields().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (documentFolder.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					documentFolder.getDateCreated()));

			sb.append("\"");
		}

		if (documentFolder.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					documentFolder.getDateModified()));

			sb.append("\"");
		}

		if (documentFolder.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(documentFolder.getDescription()));

			sb.append("\"");
		}

		if (documentFolder.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(documentFolder.getId());
		}

		if (documentFolder.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(documentFolder.getName()));

			sb.append("\"");
		}

		if (documentFolder.getNumberOfDocumentFolders() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfDocumentFolders\": ");

			sb.append(documentFolder.getNumberOfDocumentFolders());
		}

		if (documentFolder.getNumberOfDocuments() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfDocuments\": ");

			sb.append(documentFolder.getNumberOfDocuments());
		}

		if (documentFolder.getParentDocumentFolderId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parentDocumentFolderId\": ");

			sb.append(documentFolder.getParentDocumentFolderId());
		}

		if (documentFolder.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\": ");

			sb.append(documentFolder.getSiteId());
		}

		if (documentFolder.getSubscribed() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subscribed\": ");

			sb.append(documentFolder.getSubscribed());
		}

		if (documentFolder.getViewableBy() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"viewableBy\": ");

			sb.append("\"");

			sb.append(documentFolder.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DocumentFolderJSONParser documentFolderJSONParser =
			new DocumentFolderJSONParser();

		return documentFolderJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DocumentFolder documentFolder) {
		if (documentFolder == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (documentFolder.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(documentFolder.getActions()));
		}

		if (documentFolder.getAssetLibraryKey() == null) {
			map.put("assetLibraryKey", null);
		}
		else {
			map.put(
				"assetLibraryKey",
				String.valueOf(documentFolder.getAssetLibraryKey()));
		}

		if (documentFolder.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", String.valueOf(documentFolder.getCreator()));
		}

		if (documentFolder.getCustomFields() == null) {
			map.put("customFields", null);
		}
		else {
			map.put(
				"customFields",
				String.valueOf(documentFolder.getCustomFields()));
		}

		if (documentFolder.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(
					documentFolder.getDateCreated()));
		}

		if (documentFolder.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(
					documentFolder.getDateModified()));
		}

		if (documentFolder.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description", String.valueOf(documentFolder.getDescription()));
		}

		if (documentFolder.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(documentFolder.getId()));
		}

		if (documentFolder.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(documentFolder.getName()));
		}

		if (documentFolder.getNumberOfDocumentFolders() == null) {
			map.put("numberOfDocumentFolders", null);
		}
		else {
			map.put(
				"numberOfDocumentFolders",
				String.valueOf(documentFolder.getNumberOfDocumentFolders()));
		}

		if (documentFolder.getNumberOfDocuments() == null) {
			map.put("numberOfDocuments", null);
		}
		else {
			map.put(
				"numberOfDocuments",
				String.valueOf(documentFolder.getNumberOfDocuments()));
		}

		if (documentFolder.getParentDocumentFolderId() == null) {
			map.put("parentDocumentFolderId", null);
		}
		else {
			map.put(
				"parentDocumentFolderId",
				String.valueOf(documentFolder.getParentDocumentFolderId()));
		}

		if (documentFolder.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(documentFolder.getSiteId()));
		}

		if (documentFolder.getSubscribed() == null) {
			map.put("subscribed", null);
		}
		else {
			map.put(
				"subscribed", String.valueOf(documentFolder.getSubscribed()));
		}

		if (documentFolder.getViewableBy() == null) {
			map.put("viewableBy", null);
		}
		else {
			map.put(
				"viewableBy", String.valueOf(documentFolder.getViewableBy()));
		}

		return map;
	}

	public static class DocumentFolderJSONParser
		extends BaseJSONParser<DocumentFolder> {

		@Override
		protected DocumentFolder createDTO() {
			return new DocumentFolder();
		}

		@Override
		protected DocumentFolder[] createDTOArray(int size) {
			return new DocumentFolder[size];
		}

		@Override
		protected void setField(
			DocumentFolder documentFolder, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setActions(
						(Map)DocumentFolderSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "assetLibraryKey")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setAssetLibraryKey(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "customFields")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setCustomFields(
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
					documentFolder.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfDocumentFolders")) {

				if (jsonParserFieldValue != null) {
					documentFolder.setNumberOfDocumentFolders(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfDocuments")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setNumberOfDocuments(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "parentDocumentFolderId")) {

				if (jsonParserFieldValue != null) {
					documentFolder.setParentDocumentFolderId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "subscribed")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setSubscribed((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setViewableBy(
						DocumentFolder.ViewableBy.create(
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