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

import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardSection;
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
public class MessageBoardSectionSerDes {

	public static MessageBoardSection toDTO(String json) {
		MessageBoardSectionJSONParser messageBoardSectionJSONParser =
			new MessageBoardSectionJSONParser();

		return messageBoardSectionJSONParser.parseToDTO(json);
	}

	public static MessageBoardSection[] toDTOs(String json) {
		MessageBoardSectionJSONParser messageBoardSectionJSONParser =
			new MessageBoardSectionJSONParser();

		return messageBoardSectionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(MessageBoardSection messageBoardSection) {
		if (messageBoardSection == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (messageBoardSection.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"creator\":");

			sb.append(CreatorSerDes.toJSON(messageBoardSection.getCreator()));
		}

		if (messageBoardSection.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"dateCreated\":");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					messageBoardSection.getDateCreated()));

			sb.append("\"");
		}

		if (messageBoardSection.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"dateModified\":");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					messageBoardSection.getDateModified()));

			sb.append("\"");
		}

		if (messageBoardSection.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"description\":");

			sb.append("\"");

			sb.append(messageBoardSection.getDescription());

			sb.append("\"");
		}

		if (messageBoardSection.getId() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"id\":");

			sb.append(messageBoardSection.getId());
		}

		if (messageBoardSection.getNumberOfMessageBoardSections() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"numberOfMessageBoardSections\":");

			sb.append(messageBoardSection.getNumberOfMessageBoardSections());
		}

		if (messageBoardSection.getNumberOfMessageBoardThreads() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"numberOfMessageBoardThreads\":");

			sb.append(messageBoardSection.getNumberOfMessageBoardThreads());
		}

		if (messageBoardSection.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"siteId\":");

			sb.append(messageBoardSection.getSiteId());
		}

		if (messageBoardSection.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"title\":");

			sb.append("\"");

			sb.append(messageBoardSection.getTitle());

			sb.append("\"");
		}

		if (messageBoardSection.getViewableBy() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"viewableBy\":");

			sb.append("\"");

			sb.append(messageBoardSection.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(
		MessageBoardSection messageBoardSection) {

		if (messageBoardSection == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (messageBoardSection.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put(
				"creator",
				CreatorSerDes.toJSON(messageBoardSection.getCreator()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(
				messageBoardSection.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(
				messageBoardSection.getDateModified()));

		if (messageBoardSection.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(messageBoardSection.getDescription()));
		}

		if (messageBoardSection.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(messageBoardSection.getId()));
		}

		if (messageBoardSection.getNumberOfMessageBoardSections() == null) {
			map.put("numberOfMessageBoardSections", null);
		}
		else {
			map.put(
				"numberOfMessageBoardSections",
				String.valueOf(
					messageBoardSection.getNumberOfMessageBoardSections()));
		}

		if (messageBoardSection.getNumberOfMessageBoardThreads() == null) {
			map.put("numberOfMessageBoardThreads", null);
		}
		else {
			map.put(
				"numberOfMessageBoardThreads",
				String.valueOf(
					messageBoardSection.getNumberOfMessageBoardThreads()));
		}

		if (messageBoardSection.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(messageBoardSection.getSiteId()));
		}

		if (messageBoardSection.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(messageBoardSection.getTitle()));
		}

		if (messageBoardSection.getViewableBy() == null) {
			map.put("viewableBy", null);
		}
		else {
			map.put(
				"viewableBy",
				String.valueOf(messageBoardSection.getViewableBy()));
		}

		return map;
	}

	private static class MessageBoardSectionJSONParser
		extends BaseJSONParser<MessageBoardSection> {

		@Override
		protected MessageBoardSection createDTO() {
			return new MessageBoardSection();
		}

		@Override
		protected MessageBoardSection[] createDTOArray(int size) {
			return new MessageBoardSection[size];
		}

		@Override
		protected void setField(
			MessageBoardSection messageBoardSection, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					messageBoardSection.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					messageBoardSection.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					messageBoardSection.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					messageBoardSection.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					messageBoardSection.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfMessageBoardSections")) {

				if (jsonParserFieldValue != null) {
					messageBoardSection.setNumberOfMessageBoardSections(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfMessageBoardThreads")) {

				if (jsonParserFieldValue != null) {
					messageBoardSection.setNumberOfMessageBoardThreads(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					messageBoardSection.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					messageBoardSection.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					messageBoardSection.setViewableBy(
						MessageBoardSection.ViewableBy.create(
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