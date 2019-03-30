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

package com.liferay.headless.collaboration.client.serdes.v1_0;

import com.liferay.headless.collaboration.client.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.collaboration.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"creator\": ");

		sb.append(knowledgeBaseFolder.getCreator());
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		sb.append("\"");
		sb.append(knowledgeBaseFolder.getDateCreated());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dateModified\": ");

		sb.append("\"");
		sb.append(knowledgeBaseFolder.getDateModified());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"description\": ");

		sb.append("\"");
		sb.append(knowledgeBaseFolder.getDescription());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(knowledgeBaseFolder.getId());
		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(knowledgeBaseFolder.getName());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"numberOfKnowledgeBaseArticles\": ");

		sb.append(knowledgeBaseFolder.getNumberOfKnowledgeBaseArticles());
		sb.append(", ");

		sb.append("\"numberOfKnowledgeBaseFolders\": ");

		sb.append(knowledgeBaseFolder.getNumberOfKnowledgeBaseFolders());
		sb.append(", ");

		sb.append("\"parentKnowledgeBaseFolder\": ");

		sb.append(knowledgeBaseFolder.getParentKnowledgeBaseFolder());
		sb.append(", ");

		sb.append("\"parentKnowledgeBaseFolderId\": ");

		sb.append(knowledgeBaseFolder.getParentKnowledgeBaseFolderId());

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(
		Collection<KnowledgeBaseFolder> knowledgeBaseFolders) {

		if (knowledgeBaseFolders == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (KnowledgeBaseFolder knowledgeBaseFolder : knowledgeBaseFolders) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(knowledgeBaseFolder));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class KnowledgeBaseFolderJSONParser
		extends BaseJSONParser<KnowledgeBaseFolder> {

		protected KnowledgeBaseFolder createDTO() {
			return new KnowledgeBaseFolder();
		}

		protected KnowledgeBaseFolder[] createDTOArray(int size) {
			return new KnowledgeBaseFolder[size];
		}

		protected void setField(
			KnowledgeBaseFolder knowledgeBaseFolder, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolder.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolder.setDateCreated(
						(Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseFolder.setDateModified(
						(Date)jsonParserFieldValue);
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
					knowledgeBaseFolder.setId((Long)jsonParserFieldValue);
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
						(Number)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfKnowledgeBaseFolders")) {

				if (jsonParserFieldValue != null) {
					knowledgeBaseFolder.setNumberOfKnowledgeBaseFolders(
						(Number)jsonParserFieldValue);
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
						(Long)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}