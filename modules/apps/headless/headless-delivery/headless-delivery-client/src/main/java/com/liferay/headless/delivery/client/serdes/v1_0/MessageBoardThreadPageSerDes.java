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
import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardThreadPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class MessageBoardThreadPageSerDes {

	public static MessageBoardThreadPage toDTO(String json) {
		MessageBoardThreadPageJSONParser messageBoardThreadPageJSONParser =
			new MessageBoardThreadPageJSONParser();

		return messageBoardThreadPageJSONParser.parseToDTO(json);
	}

	public static MessageBoardThreadPage[] toDTOs(String json) {
		MessageBoardThreadPageJSONParser messageBoardThreadPageJSONParser =
			new MessageBoardThreadPageJSONParser();

		return messageBoardThreadPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(MessageBoardThreadPage messageBoardThreadPage) {
		if (messageBoardThreadPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (messageBoardThreadPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < messageBoardThreadPage.getItems().length; i++) {
				sb.append(
					MessageBoardThreadSerDes.toJSON(
						messageBoardThreadPage.getItems()[i]));

				if ((i + 1) < messageBoardThreadPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (messageBoardThreadPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardThreadPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (messageBoardThreadPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardThreadPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (messageBoardThreadPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardThreadPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (messageBoardThreadPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardThreadPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class MessageBoardThreadPageJSONParser
		extends BaseJSONParser<MessageBoardThreadPage> {

		protected MessageBoardThreadPage createDTO() {
			return new MessageBoardThreadPage();
		}

		protected MessageBoardThreadPage[] createDTOArray(int size) {
			return new MessageBoardThreadPage[size];
		}

		protected void setField(
			MessageBoardThreadPage messageBoardThreadPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					messageBoardThreadPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> MessageBoardThreadSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new MessageBoardThread[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					messageBoardThreadPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					messageBoardThreadPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					messageBoardThreadPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					messageBoardThreadPage.setTotalCount(
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