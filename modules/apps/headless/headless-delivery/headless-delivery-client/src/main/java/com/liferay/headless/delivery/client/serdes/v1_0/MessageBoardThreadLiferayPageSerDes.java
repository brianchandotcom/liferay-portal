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
import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardThreadLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class MessageBoardThreadLiferayPageSerDes {

	public static MessageBoardThreadLiferayPage toDTO(String json) {
		MessageBoardThreadLiferayPageJSONParser
			messageBoardThreadLiferayPageJSONParser =
				new MessageBoardThreadLiferayPageJSONParser();

		return messageBoardThreadLiferayPageJSONParser.parseToDTO(json);
	}

	public static MessageBoardThreadLiferayPage[] toDTOs(String json) {
		MessageBoardThreadLiferayPageJSONParser
			messageBoardThreadLiferayPageJSONParser =
				new MessageBoardThreadLiferayPageJSONParser();

		return messageBoardThreadLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		MessageBoardThreadLiferayPage messageBoardThreadLiferayPage) {

		if (messageBoardThreadLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (messageBoardThreadLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < messageBoardThreadLiferayPage.getItems().length;
				 i++) {

				sb.append(
					MessageBoardThreadSerDes.toJSON(
						messageBoardThreadLiferayPage.getItems()[i]));

				if ((i + 1) < messageBoardThreadLiferayPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (messageBoardThreadLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardThreadLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (messageBoardThreadLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardThreadLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (messageBoardThreadLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardThreadLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (messageBoardThreadLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardThreadLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class MessageBoardThreadLiferayPageJSONParser
		extends BaseJSONParser<MessageBoardThreadLiferayPage> {

		protected MessageBoardThreadLiferayPage createDTO() {
			return new MessageBoardThreadLiferayPage();
		}

		protected MessageBoardThreadLiferayPage[] createDTOArray(int size) {
			return new MessageBoardThreadLiferayPage[size];
		}

		protected void setField(
			MessageBoardThreadLiferayPage messageBoardThreadLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					messageBoardThreadLiferayPage.setItems(
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
					messageBoardThreadLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					messageBoardThreadLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					messageBoardThreadLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					messageBoardThreadLiferayPage.setTotalCount(
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