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

import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseFolder;
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

		sb.append("\"creator\": ");

		sb.append(CreatorSerDes.toJSON(knowledgeBaseFolder.getCreator()));
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (knowledgeBaseFolder.getDateCreated() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					knowledgeBaseFolder.getDateCreated()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (knowledgeBaseFolder.getDateModified() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					knowledgeBaseFolder.getDateModified()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"description\": ");

		if (knowledgeBaseFolder.getDescription() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(knowledgeBaseFolder.getDescription());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (knowledgeBaseFolder.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseFolder.getId());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (knowledgeBaseFolder.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(knowledgeBaseFolder.getName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"numberOfKnowledgeBaseArticles\": ");

		if (knowledgeBaseFolder.getNumberOfKnowledgeBaseArticles() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseFolder.getNumberOfKnowledgeBaseArticles());
		}

		sb.append(", ");

		sb.append("\"numberOfKnowledgeBaseFolders\": ");

		if (knowledgeBaseFolder.getNumberOfKnowledgeBaseFolders() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseFolder.getNumberOfKnowledgeBaseFolders());
		}

		sb.append(", ");

		sb.append("\"parentKnowledgeBaseFolder\": ");

		sb.append(
			ParentKnowledgeBaseFolderSerDes.toJSON(
				knowledgeBaseFolder.getParentKnowledgeBaseFolder()));
		sb.append(", ");

		sb.append("\"parentKnowledgeBaseFolderId\": ");

		if (knowledgeBaseFolder.getParentKnowledgeBaseFolderId() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseFolder.getParentKnowledgeBaseFolderId());
		}

		sb.append(", ");

		sb.append("\"siteId\": ");

		if (knowledgeBaseFolder.getSiteId() == null) {
			sb.append("null");
		}
		else {
			sb.append(knowledgeBaseFolder.getSiteId());
		}

		sb.append(", ");

		sb.append("\"viewableBy\": ");

		if (knowledgeBaseFolder.getViewableBy() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(knowledgeBaseFolder.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(
		KnowledgeBaseFolder knowledgeBaseFolder) {

		if (knowledgeBaseFolder == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (knowledgeBaseFolder.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put(
				"creator",
				CreatorSerDes.toJSON(knowledgeBaseFolder.getCreator()));
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
				ParentKnowledgeBaseFolderSerDes.toJSON(
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

	private static class KnowledgeBaseFolderJSONParser
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

}