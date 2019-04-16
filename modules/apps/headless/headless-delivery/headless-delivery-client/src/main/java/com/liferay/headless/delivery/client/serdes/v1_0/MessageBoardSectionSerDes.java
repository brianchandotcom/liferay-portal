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

		sb.append("\"creator\": ");

		sb.append(CreatorSerDes.toJSON(messageBoardSection.getCreator()));
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (messageBoardSection.getDateCreated() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					messageBoardSection.getDateCreated()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (messageBoardSection.getDateModified() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					messageBoardSection.getDateModified()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"description\": ");

		if (messageBoardSection.getDescription() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(messageBoardSection.getDescription());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (messageBoardSection.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardSection.getId());
		}

		sb.append(", ");

		sb.append("\"numberOfMessageBoardSections\": ");

		if (messageBoardSection.getNumberOfMessageBoardSections() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardSection.getNumberOfMessageBoardSections());
		}

		sb.append(", ");

		sb.append("\"numberOfMessageBoardThreads\": ");

		if (messageBoardSection.getNumberOfMessageBoardThreads() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardSection.getNumberOfMessageBoardThreads());
		}

		sb.append(", ");

		sb.append("\"siteId\": ");

		if (messageBoardSection.getSiteId() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardSection.getSiteId());
		}

		sb.append(", ");

		sb.append("\"title\": ");

		if (messageBoardSection.getTitle() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(messageBoardSection.getTitle());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"viewableBy\": ");

		if (messageBoardSection.getViewableBy() == null) {
			sb.append("null");
		}
		else {
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

		map.put(
			"creator", CreatorSerDes.toJSON(messageBoardSection.getCreator()));

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(
				messageBoardSection.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(
				messageBoardSection.getDateModified()));

		map.put(
			"description",
			String.valueOf(messageBoardSection.getDescription()));

		map.put("id", String.valueOf(messageBoardSection.getId()));

		map.put(
			"numberOfMessageBoardSections",
			String.valueOf(
				messageBoardSection.getNumberOfMessageBoardSections()));

		map.put(
			"numberOfMessageBoardThreads",
			String.valueOf(
				messageBoardSection.getNumberOfMessageBoardThreads()));

		map.put("siteId", String.valueOf(messageBoardSection.getSiteId()));

		map.put("title", String.valueOf(messageBoardSection.getTitle()));

		map.put(
			"viewableBy", String.valueOf(messageBoardSection.getViewableBy()));

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