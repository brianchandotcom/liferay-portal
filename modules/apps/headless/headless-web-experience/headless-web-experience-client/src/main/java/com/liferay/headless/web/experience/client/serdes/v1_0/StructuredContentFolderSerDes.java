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

package com.liferay.headless.web.experience.client.serdes.v1_0;

import com.liferay.headless.web.experience.client.dto.v1_0.StructuredContentFolder;
import com.liferay.headless.web.experience.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Date;
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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"contentSpaceId\": ");

		sb.append(structuredContentFolder.getContentSpaceId());
		sb.append(", ");

		sb.append("\"creator\": ");

		sb.append(structuredContentFolder.getCreator());
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		sb.append("\"");
		sb.append(structuredContentFolder.getDateCreated());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dateModified\": ");

		sb.append("\"");
		sb.append(structuredContentFolder.getDateModified());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"description\": ");

		sb.append("\"");
		sb.append(structuredContentFolder.getDescription());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(structuredContentFolder.getId());
		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(structuredContentFolder.getName());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"numberOfFolders\": ");

		sb.append(structuredContentFolder.getNumberOfFolders());
		sb.append(", ");

		sb.append("\"numberOfStructuredContents\": ");

		sb.append(structuredContentFolder.getNumberOfStructuredContents());
		sb.append(", ");

		sb.append("\"viewableBy\": ");

		sb.append("\"");
		sb.append(structuredContentFolder.getViewableBy());
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(
		Collection<StructuredContentFolder> structuredContentFolders) {

		if (structuredContentFolders == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (StructuredContentFolder structuredContentFolder :
				structuredContentFolders) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(structuredContentFolder));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class StructuredContentFolderJSONParser
		extends BaseJSONParser<StructuredContentFolder> {

		protected StructuredContentFolder createDTO() {
			return new StructuredContentFolder();
		}

		protected StructuredContentFolder[] createDTOArray(int size) {
			return new StructuredContentFolder[size];
		}

		protected void setField(
			StructuredContentFolder structuredContentFolder,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contentSpaceId")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolder.setContentSpaceId(
						(Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolder.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolder.setDateCreated(
						(Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolder.setDateModified(
						(Date)jsonParserFieldValue);
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
					structuredContentFolder.setId((Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolder.setName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfFolders")) {
				if (jsonParserFieldValue != null) {
					structuredContentFolder.setNumberOfFolders(
						(Number)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfStructuredContents")) {

				if (jsonParserFieldValue != null) {
					structuredContentFolder.setNumberOfStructuredContents(
						(Number)jsonParserFieldValue);
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