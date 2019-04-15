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
import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardMessagePage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class MessageBoardMessagePageSerDes {

	public static MessageBoardMessagePage toDTO(String json) {
		MessageBoardMessagePageJSONParser messageBoardMessagePageJSONParser =
			new MessageBoardMessagePageJSONParser();

		return messageBoardMessagePageJSONParser.parseToDTO(json);
	}

	public static MessageBoardMessagePage[] toDTOs(String json) {
		MessageBoardMessagePageJSONParser messageBoardMessagePageJSONParser =
			new MessageBoardMessagePageJSONParser();

		return messageBoardMessagePageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		MessageBoardMessagePage messageBoardMessagePage) {

		if (messageBoardMessagePage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (messageBoardMessagePage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < messageBoardMessagePage.getItems().length;
				 i++) {

				sb.append(
					MessageBoardMessageSerDes.toJSON(
						messageBoardMessagePage.getItems()[i]));

				if ((i + 1) < messageBoardMessagePage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (messageBoardMessagePage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardMessagePage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (messageBoardMessagePage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardMessagePage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (messageBoardMessagePage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardMessagePage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (messageBoardMessagePage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardMessagePage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class MessageBoardMessagePageJSONParser
		extends BaseJSONParser<MessageBoardMessagePage> {

		protected MessageBoardMessagePage createDTO() {
			return new MessageBoardMessagePage();
		}

		protected MessageBoardMessagePage[] createDTOArray(int size) {
			return new MessageBoardMessagePage[size];
		}

		protected void setField(
			MessageBoardMessagePage messageBoardMessagePage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessagePage.setItems(
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
					messageBoardMessagePage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessagePage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessagePage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					messageBoardMessagePage.setTotalCount(
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