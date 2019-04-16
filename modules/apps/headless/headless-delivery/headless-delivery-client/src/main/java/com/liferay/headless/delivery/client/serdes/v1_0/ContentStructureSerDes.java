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

import com.liferay.headless.delivery.client.dto.v1_0.ContentStructure;
import com.liferay.headless.delivery.client.dto.v1_0.ContentStructureField;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentStructureSerDes {

	public static ContentStructure toDTO(String json) {
		ContentStructureJSONParser contentStructureJSONParser =
			new ContentStructureJSONParser();

		return contentStructureJSONParser.parseToDTO(json);
	}

	public static ContentStructure[] toDTOs(String json) {
		ContentStructureJSONParser contentStructureJSONParser =
			new ContentStructureJSONParser();

		return contentStructureJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContentStructure contentStructure) {
		if (contentStructure == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		sb.append("\"availableLanguages\": ");

		if (contentStructure.getAvailableLanguages() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < contentStructure.getAvailableLanguages().length;
				 i++) {

				sb.append("\"");

				sb.append(contentStructure.getAvailableLanguages()[i]);

				sb.append("\"");

				if ((i + 1) < contentStructure.getAvailableLanguages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"contentStructureFields\": ");

		if (contentStructure.getContentStructureFields() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < contentStructure.getContentStructureFields().length; i++) {

				sb.append(
					ContentStructureFieldSerDes.toJSON(
						contentStructure.getContentStructureFields()[i]));

				if ((i + 1) <
						contentStructure.getContentStructureFields().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"creator\": ");

		sb.append(CreatorSerDes.toJSON(contentStructure.getCreator()));
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (contentStructure.getDateCreated() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					contentStructure.getDateCreated()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (contentStructure.getDateModified() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					contentStructure.getDateModified()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"description\": ");

		if (contentStructure.getDescription() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contentStructure.getDescription());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (contentStructure.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructure.getId());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (contentStructure.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contentStructure.getName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"siteId\": ");

		if (contentStructure.getSiteId() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentStructure.getSiteId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(ContentStructure contentStructure) {
		if (contentStructure == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		map.put(
			"availableLanguages",
			String.valueOf(contentStructure.getAvailableLanguages()));

		map.put(
			"contentStructureFields",
			String.valueOf(contentStructure.getContentStructureFields()));

		map.put("creator", CreatorSerDes.toJSON(contentStructure.getCreator()));

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(contentStructure.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(contentStructure.getDateModified()));

		map.put(
			"description", String.valueOf(contentStructure.getDescription()));

		map.put("id", String.valueOf(contentStructure.getId()));

		map.put("name", String.valueOf(contentStructure.getName()));

		map.put("siteId", String.valueOf(contentStructure.getSiteId()));

		return map;
	}

	private static class ContentStructureJSONParser
		extends BaseJSONParser<ContentStructure> {

		@Override
		protected ContentStructure createDTO() {
			return new ContentStructure();
		}

		@Override
		protected ContentStructure[] createDTOArray(int size) {
			return new ContentStructure[size];
		}

		@Override
		protected void setField(
			ContentStructure contentStructure, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "availableLanguages")) {
				if (jsonParserFieldValue != null) {
					contentStructure.setAvailableLanguages(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "contentStructureFields")) {

				if (jsonParserFieldValue != null) {
					contentStructure.setContentStructureFields(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ContentStructureFieldSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ContentStructureField[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					contentStructure.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					contentStructure.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					contentStructure.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					contentStructure.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					contentStructure.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					contentStructure.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					contentStructure.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}