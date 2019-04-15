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
import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardMessageLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class MessageBoardMessageLiferayPageSerDes {

	public static MessageBoardMessageLiferayPage toDTO(String json) {
		MessageBoardMessageLiferayPageJSONParser
			messageBoardMessageLiferayPageJSONParser =
				new MessageBoardMessageLiferayPageJSONParser();

		return messageBoardMessageLiferayPageJSONParser.parseToDTO(json);
	}

	public static MessageBoardMessageLiferayPage[] toDTOs(String json) {
		MessageBoardMessageLiferayPageJSONParser
			messageBoardMessageLiferayPageJSONParser =
				new MessageBoardMessageLiferayPageJSONParser();

		return messageBoardMessageLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		MessageBoardMessageLiferayPage messageBoardMessageLiferayPage) {

		if (messageBoardMessageLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (messageBoardMessageLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < messageBoardMessageLiferayPage.getItems().length; i++) {

				sb.append(
					MessageBoardMessageSerDes.toJSON(
						messageBoardMessageLiferayPage.getItems()[i]));

				if ((i + 1) <
						messageBoardMessageLiferayPage.getItems().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (messageBoardMessageLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardMessageLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (messageBoardMessageLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardMessageLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (messageBoardMessageLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardMessageLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (messageBoardMessageLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardMessageLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class MessageBoardMessageLiferayPageJSONParser
		extends BaseJSONParser<MessageBoardMessageLiferayPage> {

		protected MessageBoardMessageLiferayPage createDTO() {
			return new MessageBoardMessageLiferayPage();
		}

		protected MessageBoardMessageLiferayPage[] createDTOArray(int size) {
			return new MessageBoardMessageLiferayPage[size];
		}

		protected void setField(
			MessageBoardMessageLiferayPage messageBoardMessageLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessageLiferayPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> MessageBoardMessageSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new MessageBoardMessage[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessageLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessageLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessageLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessageLiferayPage.setTotalCount(
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