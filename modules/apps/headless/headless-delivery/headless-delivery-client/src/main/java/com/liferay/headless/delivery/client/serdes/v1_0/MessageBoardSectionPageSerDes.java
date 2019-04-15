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
import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardSectionPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class MessageBoardSectionPageSerDes {

	public static MessageBoardSectionPage toDTO(String json) {
		MessageBoardSectionPageJSONParser messageBoardSectionPageJSONParser =
			new MessageBoardSectionPageJSONParser();

		return messageBoardSectionPageJSONParser.parseToDTO(json);
	}

	public static MessageBoardSectionPage[] toDTOs(String json) {
		MessageBoardSectionPageJSONParser messageBoardSectionPageJSONParser =
			new MessageBoardSectionPageJSONParser();

		return messageBoardSectionPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		MessageBoardSectionPage messageBoardSectionPage) {

		if (messageBoardSectionPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (messageBoardSectionPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < messageBoardSectionPage.getItems().length;
				 i++) {

				sb.append(
					MessageBoardSectionSerDes.toJSON(
						messageBoardSectionPage.getItems()[i]));

				if ((i + 1) < messageBoardSectionPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (messageBoardSectionPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardSectionPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (messageBoardSectionPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardSectionPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (messageBoardSectionPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardSectionPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (messageBoardSectionPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardSectionPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class MessageBoardSectionPageJSONParser
		extends BaseJSONParser<MessageBoardSectionPage> {

		protected MessageBoardSectionPage createDTO() {
			return new MessageBoardSectionPage();
		}

		protected MessageBoardSectionPage[] createDTOArray(int size) {
			return new MessageBoardSectionPage[size];
		}

		protected void setField(
			MessageBoardSectionPage messageBoardSectionPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					messageBoardSectionPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> MessageBoardSectionSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new MessageBoardSection[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					messageBoardSectionPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					messageBoardSectionPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					messageBoardSectionPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					messageBoardSectionPage.setTotalCount(
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