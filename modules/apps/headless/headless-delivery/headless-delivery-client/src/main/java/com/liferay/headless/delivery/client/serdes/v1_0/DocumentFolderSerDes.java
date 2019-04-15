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

		sb.append("\"creator\": ");

		sb.append(CreatorSerDes.toJSON(documentFolder.getCreator()));
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (documentFolder.getDateCreated() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					documentFolder.getDateCreated()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (documentFolder.getDateModified() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					documentFolder.getDateModified()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"description\": ");

		if (documentFolder.getDescription() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(documentFolder.getDescription());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (documentFolder.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(documentFolder.getId());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (documentFolder.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(documentFolder.getName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"numberOfDocumentFolders\": ");

		if (documentFolder.getNumberOfDocumentFolders() == null) {
			sb.append("null");
		}
		else {
			sb.append(documentFolder.getNumberOfDocumentFolders());
		}

		sb.append(", ");

		sb.append("\"numberOfDocuments\": ");

		if (documentFolder.getNumberOfDocuments() == null) {
			sb.append("null");
		}
		else {
			sb.append(documentFolder.getNumberOfDocuments());
		}

		sb.append(", ");

		sb.append("\"siteId\": ");

		if (documentFolder.getSiteId() == null) {
			sb.append("null");
		}
		else {
			sb.append(documentFolder.getSiteId());
		}

		sb.append(", ");

		sb.append("\"viewableBy\": ");

		if (documentFolder.getViewableBy() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(documentFolder.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	private static class DocumentFolderJSONParser
		extends BaseJSONParser<DocumentFolder> {

		protected DocumentFolder createDTO() {
			return new DocumentFolder();
		}

		protected DocumentFolder[] createDTOArray(int size) {
			return new DocumentFolder[size];
		}

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
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfDocuments")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setNumberOfDocuments(
						Long.valueOf((String)jsonParserFieldValue));
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