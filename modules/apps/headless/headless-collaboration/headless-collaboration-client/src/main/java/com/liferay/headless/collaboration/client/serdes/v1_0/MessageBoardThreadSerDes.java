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

import com.liferay.headless.collaboration.client.dto.v1_0.MessageBoardThread;
import com.liferay.headless.collaboration.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.collaboration.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;

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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"aggregateRating\": ");

		sb.append(messageBoardThread.getAggregateRating());
		sb.append(", ");

		sb.append("\"articleBody\": ");

		sb.append("\"");
		sb.append(messageBoardThread.getArticleBody());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"contentSpaceId\": ");

		sb.append(messageBoardThread.getContentSpaceId());
		sb.append(", ");

		sb.append("\"creator\": ");

		sb.append(messageBoardThread.getCreator());
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		sb.append("\"");
		sb.append(messageBoardThread.getDateCreated());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dateModified\": ");

		sb.append("\"");
		sb.append(messageBoardThread.getDateModified());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"encodingFormat\": ");

		sb.append("\"");
		sb.append(messageBoardThread.getEncodingFormat());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"headline\": ");

		sb.append("\"");
		sb.append(messageBoardThread.getHeadline());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(messageBoardThread.getId());
		sb.append(", ");

		sb.append("\"keywords\": ");

		if (messageBoardThread.getKeywords() == null) {
			sb.append("null");
		}
		else {
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

		sb.append(", ");

		sb.append("\"numberOfMessageBoardAttachments\": ");

		sb.append(messageBoardThread.getNumberOfMessageBoardAttachments());
		sb.append(", ");

		sb.append("\"numberOfMessageBoardMessages\": ");

		sb.append(messageBoardThread.getNumberOfMessageBoardMessages());
		sb.append(", ");

		sb.append("\"showAsQuestion\": ");

		sb.append(messageBoardThread.getShowAsQuestion());
		sb.append(", ");

		sb.append("\"taxonomyCategories\": ");

		if (messageBoardThread.getTaxonomyCategories() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < messageBoardThread.getTaxonomyCategories().length; i++) {

				sb.append(messageBoardThread.getTaxonomyCategories()[i]);

				if ((i + 1) <
						messageBoardThread.getTaxonomyCategories().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"taxonomyCategoryIds\": ");

		if (messageBoardThread.getTaxonomyCategoryIds() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < messageBoardThread.getTaxonomyCategoryIds().length; i++) {

				sb.append(messageBoardThread.getTaxonomyCategoryIds()[i]);

				if ((i + 1) <
						messageBoardThread.getTaxonomyCategoryIds().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"threadType\": ");

		sb.append("\"");
		sb.append(messageBoardThread.getThreadType());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"viewableBy\": ");

		sb.append("\"");
		sb.append(messageBoardThread.getViewableBy());
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(
		Collection<MessageBoardThread> messageBoardThreads) {

		if (messageBoardThreads == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (MessageBoardThread messageBoardThread : messageBoardThreads) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(messageBoardThread));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class MessageBoardThreadJSONParser
		extends BaseJSONParser<MessageBoardThread> {

		protected MessageBoardThread createDTO() {
			return new MessageBoardThread();
		}

		protected MessageBoardThread[] createDTOArray(int size) {
			return new MessageBoardThread[size];
		}

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
			else if (Objects.equals(jsonParserFieldName, "contentSpaceId")) {
				if (jsonParserFieldValue != null) {
					messageBoardThread.setContentSpaceId(
						(Long)jsonParserFieldValue);
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
						(Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					messageBoardThread.setDateModified(
						(Date)jsonParserFieldValue);
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
					messageBoardThread.setId((Long)jsonParserFieldValue);
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
						(Integer)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfMessageBoardMessages")) {

				if (jsonParserFieldValue != null) {
					messageBoardThread.setNumberOfMessageBoardMessages(
						(Integer)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "showAsQuestion")) {
				if (jsonParserFieldValue != null) {
					messageBoardThread.setShowAsQuestion(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategories")) {

				if (jsonParserFieldValue != null) {
					messageBoardThread.setTaxonomyCategories(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> TaxonomyCategorySerDes.toDTO(
								(String)object)
						).toArray(
							size -> new TaxonomyCategory[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategoryIds")) {

				if (jsonParserFieldValue != null) {
					messageBoardThread.setTaxonomyCategoryIds(
						toLongs((Object[])jsonParserFieldValue));
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