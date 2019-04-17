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

import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardThread;
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
public class MessageBoardThreadSerDes {

	public static MessageBoardThread toDTO(String json) {
		MessageBoardThreadJSONParser messageBoardThreadJSONParser =
			new MessageBoardThreadJSONParser();

		return messageBoardThreadJSONParser.parseToDTO(json);
	}

	public static MessageBoardThread[] toDTOs(String json) {
		MessageBoardThreadJSONParser messageBoardThreadJSONParser =
			new MessageBoardThreadJSONParser();

		return messageBoardThreadJSONParser.parseToDTOs(json);
	}

	public static String toJSON(MessageBoardThread messageBoardThread) {
		if (messageBoardThread == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (messageBoardThread.getAggregateRating() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"aggregateRating\":");

			sb.append(
				AggregateRatingSerDes.toJSON(
					messageBoardThread.getAggregateRating()));
		}

		if (messageBoardThread.getArticleBody() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"articleBody\":");

			sb.append("\"");

			sb.append(messageBoardThread.getArticleBody());

			sb.append("\"");
		}

		if (messageBoardThread.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\":");

			sb.append(CreatorSerDes.toJSON(messageBoardThread.getCreator()));
		}

		if (messageBoardThread.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\":");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					messageBoardThread.getDateCreated()));

			sb.append("\"");
		}

		if (messageBoardThread.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\":");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					messageBoardThread.getDateModified()));

			sb.append("\"");
		}

		if (messageBoardThread.getEncodingFormat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"encodingFormat\":");

			sb.append("\"");

			sb.append(messageBoardThread.getEncodingFormat());

			sb.append("\"");
		}

		if (messageBoardThread.getHeadline() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"headline\":");

			sb.append("\"");

			sb.append(messageBoardThread.getHeadline());

			sb.append("\"");
		}

		if (messageBoardThread.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\":");

			sb.append(messageBoardThread.getId());
		}

		if (messageBoardThread.getKeywords() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"keywords\":");

			sb.append("[");

			for (int i = 0; i < messageBoardThread.getKeywords().length; i++) {
				sb.append("\"");

				sb.append(messageBoardThread.getKeywords()[i]);

				sb.append("\"");

				if ((i + 1) < messageBoardThread.getKeywords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (messageBoardThread.getNumberOfMessageBoardAttachments() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfMessageBoardAttachments\":");

			sb.append(messageBoardThread.getNumberOfMessageBoardAttachments());
		}

		if (messageBoardThread.getNumberOfMessageBoardMessages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfMessageBoardMessages\":");

			sb.append(messageBoardThread.getNumberOfMessageBoardMessages());
		}

		if (messageBoardThread.getShowAsQuestion() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"showAsQuestion\":");

			sb.append(messageBoardThread.getShowAsQuestion());
		}

		if (messageBoardThread.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\":");

			sb.append(messageBoardThread.getSiteId());
		}

		if (messageBoardThread.getThreadType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"threadType\":");

			sb.append("\"");

			sb.append(messageBoardThread.getThreadType());

			sb.append("\"");
		}

		if (messageBoardThread.getViewableBy() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"viewableBy\":");

			sb.append("\"");

			sb.append(messageBoardThread.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(
		MessageBoardThread messageBoardThread) {

		if (messageBoardThread == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (messageBoardThread.getAggregateRating() == null) {
			map.put("aggregateRating", null);
		}
		else {
			map.put(
				"aggregateRating",
				AggregateRatingSerDes.toJSON(
					messageBoardThread.getAggregateRating()));
		}

		if (messageBoardThread.getArticleBody() == null) {
			map.put("articleBody", null);
		}
		else {
			map.put(
				"articleBody",
				String.valueOf(messageBoardThread.getArticleBody()));
		}

		if (messageBoardThread.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put(
				"creator",
				CreatorSerDes.toJSON(messageBoardThread.getCreator()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(
				messageBoardThread.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(
				messageBoardThread.getDateModified()));

		if (messageBoardThread.getEncodingFormat() == null) {
			map.put("encodingFormat", null);
		}
		else {
			map.put(
				"encodingFormat",
				String.valueOf(messageBoardThread.getEncodingFormat()));
		}

		if (messageBoardThread.getHeadline() == null) {
			map.put("headline", null);
		}
		else {
			map.put(
				"headline", String.valueOf(messageBoardThread.getHeadline()));
		}

		if (messageBoardThread.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(messageBoardThread.getId()));
		}

		if (messageBoardThread.getKeywords() == null) {
			map.put("keywords", null);
		}
		else {
			map.put(
				"keywords", String.valueOf(messageBoardThread.getKeywords()));
		}

		if (messageBoardThread.getNumberOfMessageBoardAttachments() == null) {
			map.put("numberOfMessageBoardAttachments", null);
		}
		else {
			map.put(
				"numberOfMessageBoardAttachments",
				String.valueOf(
					messageBoardThread.getNumberOfMessageBoardAttachments()));
		}

		if (messageBoardThread.getNumberOfMessageBoardMessages() == null) {
			map.put("numberOfMessageBoardMessages", null);
		}
		else {
			map.put(
				"numberOfMessageBoardMessages",
				String.valueOf(
					messageBoardThread.getNumberOfMessageBoardMessages()));
		}

		if (messageBoardThread.getShowAsQuestion() == null) {
			map.put("showAsQuestion", null);
		}
		else {
			map.put(
				"showAsQuestion",
				String.valueOf(messageBoardThread.getShowAsQuestion()));
		}

		if (messageBoardThread.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(messageBoardThread.getSiteId()));
		}

		if (messageBoardThread.getThreadType() == null) {
			map.put("threadType", null);
		}
		else {
			map.put(
				"threadType",
				String.valueOf(messageBoardThread.getThreadType()));
		}

		if (messageBoardThread.getViewableBy() == null) {
			map.put("viewableBy", null);
		}
		else {
			map.put(
				"viewableBy",
				String.valueOf(messageBoardThread.getViewableBy()));
		}

		return map;
	}

	private static class MessageBoardThreadJSONParser
		extends BaseJSONParser<MessageBoardThread> {

		@Override
		protected MessageBoardThread createDTO() {
			return new MessageBoardThread();
		}

		@Override
		protected MessageBoardThread[] createDTOArray(int size) {
			return new MessageBoardThread[size];
		}

		@Override
		protected void setField(
			MessageBoardThread messageBoardThread, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "aggregateRating")) {
				if (jsonParserFieldValue != null) {
					messageBoardThread.setAggregateRating(
						AggregateRatingSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "articleBody")) {
				if (jsonParserFieldValue != null) {
					messageBoardThread.setArticleBody(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					messageBoardThread.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					messageBoardThread.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					messageBoardThread.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "encodingFormat")) {
				if (jsonParserFieldValue != null) {
					messageBoardThread.setEncodingFormat(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "headline")) {
				if (jsonParserFieldValue != null) {
					messageBoardThread.setHeadline(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					messageBoardThread.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywords")) {
				if (jsonParserFieldValue != null) {
					messageBoardThread.setKeywords(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"numberOfMessageBoardAttachments")) {

				if (jsonParserFieldValue != null) {
					messageBoardThread.setNumberOfMessageBoardAttachments(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfMessageBoardMessages")) {

				if (jsonParserFieldValue != null) {
					messageBoardThread.setNumberOfMessageBoardMessages(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "showAsQuestion")) {
				if (jsonParserFieldValue != null) {
					messageBoardThread.setShowAsQuestion(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					messageBoardThread.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "threadType")) {
				if (jsonParserFieldValue != null) {
					messageBoardThread.setThreadType(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					messageBoardThread.setViewableBy(
						MessageBoardThread.ViewableBy.create(
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