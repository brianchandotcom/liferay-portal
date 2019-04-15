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

import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardMessage;
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
public class MessageBoardMessageSerDes {

	public static MessageBoardMessage toDTO(String json) {
		MessageBoardMessageJSONParser messageBoardMessageJSONParser =
			new MessageBoardMessageJSONParser();

		return messageBoardMessageJSONParser.parseToDTO(json);
	}

	public static MessageBoardMessage[] toDTOs(String json) {
		MessageBoardMessageJSONParser messageBoardMessageJSONParser =
			new MessageBoardMessageJSONParser();

		return messageBoardMessageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(MessageBoardMessage messageBoardMessage) {
		if (messageBoardMessage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		sb.append("\"aggregateRating\": ");

		sb.append(
			AggregateRatingSerDes.toJSON(
				messageBoardMessage.getAggregateRating()));
		sb.append(", ");

		sb.append("\"anonymous\": ");

		if (messageBoardMessage.getAnonymous() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardMessage.getAnonymous());
		}

		sb.append(", ");

		sb.append("\"articleBody\": ");

		if (messageBoardMessage.getArticleBody() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(messageBoardMessage.getArticleBody());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"creator\": ");

		sb.append(CreatorSerDes.toJSON(messageBoardMessage.getCreator()));
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (messageBoardMessage.getDateCreated() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					messageBoardMessage.getDateCreated()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (messageBoardMessage.getDateModified() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					messageBoardMessage.getDateModified()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"encodingFormat\": ");

		if (messageBoardMessage.getEncodingFormat() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(messageBoardMessage.getEncodingFormat());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"headline\": ");

		if (messageBoardMessage.getHeadline() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(messageBoardMessage.getHeadline());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (messageBoardMessage.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardMessage.getId());
		}

		sb.append(", ");

		sb.append("\"keywords\": ");

		if (messageBoardMessage.getKeywords() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < messageBoardMessage.getKeywords().length; i++) {
				sb.append("\"");

				sb.append(messageBoardMessage.getKeywords()[i]);

				sb.append("\"");

				if ((i + 1) < messageBoardMessage.getKeywords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"numberOfMessageBoardAttachments\": ");

		if (messageBoardMessage.getNumberOfMessageBoardAttachments() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardMessage.getNumberOfMessageBoardAttachments());
		}

		sb.append(", ");

		sb.append("\"numberOfMessageBoardMessages\": ");

		if (messageBoardMessage.getNumberOfMessageBoardMessages() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardMessage.getNumberOfMessageBoardMessages());
		}

		sb.append(", ");

		sb.append("\"showAsAnswer\": ");

		if (messageBoardMessage.getShowAsAnswer() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardMessage.getShowAsAnswer());
		}

		sb.append(", ");

		sb.append("\"siteId\": ");

		if (messageBoardMessage.getSiteId() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardMessage.getSiteId());
		}

		sb.append(", ");

		sb.append("\"viewableBy\": ");

		if (messageBoardMessage.getViewableBy() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(messageBoardMessage.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	private static class MessageBoardMessageJSONParser
		extends BaseJSONParser<MessageBoardMessage> {

		protected MessageBoardMessage createDTO() {
			return new MessageBoardMessage();
		}

		protected MessageBoardMessage[] createDTOArray(int size) {
			return new MessageBoardMessage[size];
		}

		protected void setField(
			MessageBoardMessage messageBoardMessage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "aggregateRating")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessage.setAggregateRating(
						AggregateRatingSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "anonymous")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessage.setAnonymous(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "articleBody")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessage.setArticleBody(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessage.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessage.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessage.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "encodingFormat")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessage.setEncodingFormat(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "headline")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessage.setHeadline(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessage.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywords")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessage.setKeywords(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"numberOfMessageBoardAttachments")) {

				if (jsonParserFieldValue != null) {
					messageBoardMessage.setNumberOfMessageBoardAttachments(
						(Integer)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfMessageBoardMessages")) {

				if (jsonParserFieldValue != null) {
					messageBoardMessage.setNumberOfMessageBoardMessages(
						(Integer)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "showAsAnswer")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessage.setShowAsAnswer(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessage.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessage.setViewableBy(
						MessageBoardMessage.ViewableBy.create(
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