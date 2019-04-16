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

import com.liferay.headless.delivery.client.dto.v1_0.StructuredContentFolder;
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
public class StructuredContentFolderSerDes {

	public static StructuredContentFolder toDTO(String json) {
		StructuredContentFolderJSONParser structuredContentFolderJSONParser =
			new StructuredContentFolderJSONParser();

		return structuredContentFolderJSONParser.parseToDTO(json);
	}

	public static StructuredContentFolder[] toDTOs(String json) {
		StructuredContentFolderJSONParser structuredContentFolderJSONParser =
			new StructuredContentFolderJSONParser();

		return structuredContentFolderJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		StructuredContentFolder structuredContentFolder) {

		if (structuredContentFolder == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		sb.append("\"creator\": ");

		sb.append(CreatorSerDes.toJSON(structuredContentFolder.getCreator()));
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (structuredContentFolder.getDateCreated() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					structuredContentFolder.getDateCreated()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (structuredContentFolder.getDateModified() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					structuredContentFolder.getDateModified()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"description\": ");

		if (structuredContentFolder.getDescription() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(structuredContentFolder.getDescription());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (structuredContentFolder.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentFolder.getId());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (structuredContentFolder.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(structuredContentFolder.getName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"numberOfStructuredContentFolders\": ");

		if (structuredContentFolder.getNumberOfStructuredContentFolders() ==
				null) {

			sb.append("null");
		}
		else {
			sb.append(
				structuredContentFolder.getNumberOfStructuredContentFolders());
		}

		sb.append(", ");

		sb.append("\"numberOfStructuredContents\": ");

		if (structuredContentFolder.getNumberOfStructuredContents() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentFolder.getNumberOfStructuredContents());
		}

		sb.append(", ");

		sb.append("\"siteId\": ");

		if (structuredContentFolder.getSiteId() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentFolder.getSiteId());
		}

		sb.append(", ");

		sb.append("\"viewableBy\": ");

		if (structuredContentFolder.getViewableBy() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(structuredContentFolder.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(
		StructuredContentFolder structuredContentFolder) {

		if (structuredContentFolder == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		map.put(
			"creator",
			CreatorSerDes.toJSON(structuredContentFolder.getCreator()));

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(
				structuredContentFolder.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(
				structuredContentFolder.getDateModified()));

		map.put(
			"description",
			String.valueOf(structuredContentFolder.getDescription()));

		map.put("id", String.valueOf(structuredContentFolder.getId()));

		map.put("name", String.valueOf(structuredContentFolder.getName()));

		map.put(
			"numberOfStructuredContentFolders",
			String.valueOf(
				structuredContentFolder.getNumberOfStructuredContentFolders()));

		map.put(
			"numberOfStructuredContents",
			String.valueOf(
				structuredContentFolder.getNumberOfStructuredContents()));

		map.put("siteId", String.valueOf(structuredContentFolder.getSiteId()));

		map.put(
			"viewableBy",
			String.valueOf(structuredContentFolder.getViewableBy()));

		return map;
	}

	private static class StructuredContentFolderJSONParser
		extends BaseJSONParser<StructuredContentFolder> {

		@Override
		protected StructuredContentFolder createDTO() {
			return new StructuredContentFolder();
		}

		@Override
		protected StructuredContentFolder[] createDTOArray(int size) {
			return new StructuredContentFolder[size];
		}

		@Override
		protected void setField(
			StructuredContentFolder structuredContentFolder,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolder.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolder.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolder.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolder.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolder.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolder.setName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"numberOfStructuredContentFolders")) {

				if (jsonParserFieldValue != null) {
					structuredContentFolder.setNumberOfStructuredContentFolders(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfStructuredContents")) {

				if (jsonParserFieldValue != null) {
					structuredContentFolder.setNumberOfStructuredContents(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolder.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolder.setViewableBy(
						StructuredContentFolder.ViewableBy.create(
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