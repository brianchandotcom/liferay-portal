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

import com.liferay.headless.delivery.client.dto.v1_0.DocumentFolder;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (documentFolder.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"creator\":");

			sb.append(CreatorSerDes.toJSON(documentFolder.getCreator()));
		}

		if (documentFolder.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"dateCreated\":");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					documentFolder.getDateCreated()));

			sb.append("\"");
		}

		if (documentFolder.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"dateModified\":");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					documentFolder.getDateModified()));

			sb.append("\"");
		}

		if (documentFolder.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"description\":");

			sb.append("\"");

			sb.append(documentFolder.getDescription());

			sb.append("\"");
		}

		if (documentFolder.getId() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"id\":");

			sb.append(documentFolder.getId());
		}

		if (documentFolder.getName() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"name\":");

			sb.append("\"");

			sb.append(documentFolder.getName());

			sb.append("\"");
		}

		if (documentFolder.getNumberOfDocumentFolders() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"numberOfDocumentFolders\":");

			sb.append(documentFolder.getNumberOfDocumentFolders());
		}

		if (documentFolder.getNumberOfDocuments() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"numberOfDocuments\":");

			sb.append(documentFolder.getNumberOfDocuments());
		}

		if (documentFolder.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"siteId\":");

			sb.append(documentFolder.getSiteId());
		}

		if (documentFolder.getViewableBy() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"viewableBy\":");

			sb.append("\"");

			sb.append(documentFolder.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(DocumentFolder documentFolder) {
		if (documentFolder == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (documentFolder.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put(
				"creator", CreatorSerDes.toJSON(documentFolder.getCreator()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(documentFolder.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(documentFolder.getDateModified()));

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

		if (documentFolder.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(documentFolder.getSiteId()));
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

	private static class DocumentFolderJSONParser
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

			if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
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
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setViewableBy(
						DocumentFolder.ViewableBy.create(
							(String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}