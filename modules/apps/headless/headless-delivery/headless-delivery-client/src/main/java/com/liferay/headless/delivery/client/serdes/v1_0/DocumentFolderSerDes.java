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

import java.util.Collection;
import java.util.Date;
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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"contentSpaceId\": ");

		sb.append(documentFolder.getContentSpaceId());
		sb.append(", ");

		sb.append("\"creator\": ");

		sb.append(documentFolder.getCreator());
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		sb.append("\"");
		sb.append(documentFolder.getDateCreated());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dateModified\": ");

		sb.append("\"");
		sb.append(documentFolder.getDateModified());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"description\": ");

		sb.append("\"");
		sb.append(documentFolder.getDescription());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(documentFolder.getId());
		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(documentFolder.getName());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"numberOfDocumentFolders\": ");

		sb.append(documentFolder.getNumberOfDocumentFolders());
		sb.append(", ");

		sb.append("\"numberOfDocuments\": ");

		sb.append(documentFolder.getNumberOfDocuments());
		sb.append(", ");

		sb.append("\"viewableBy\": ");

		sb.append("\"");
		sb.append(documentFolder.getViewableBy());
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<DocumentFolder> documentFolders) {
		if (documentFolders == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (DocumentFolder documentFolder : documentFolders) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(documentFolder));
		}

		sb.append("]");

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

			if (Objects.equals(jsonParserFieldName, "contentSpaceId")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setContentSpaceId(
						(Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setDateCreated((Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setDateModified((Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setId((Long)jsonParserFieldValue);
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
						(Number)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfDocuments")) {
				if (jsonParserFieldValue != null) {
					documentFolder.setNumberOfDocuments(
						(Number)jsonParserFieldValue);
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