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
import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardSectionLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class MessageBoardSectionLiferayPageSerDes {

	public static MessageBoardSectionLiferayPage toDTO(String json) {
		MessageBoardSectionLiferayPageJSONParser
			messageBoardSectionLiferayPageJSONParser =
				new MessageBoardSectionLiferayPageJSONParser();

		return messageBoardSectionLiferayPageJSONParser.parseToDTO(json);
	}

	public static MessageBoardSectionLiferayPage[] toDTOs(String json) {
		MessageBoardSectionLiferayPageJSONParser
			messageBoardSectionLiferayPageJSONParser =
				new MessageBoardSectionLiferayPageJSONParser();

		return messageBoardSectionLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		MessageBoardSectionLiferayPage messageBoardSectionLiferayPage) {

		if (messageBoardSectionLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (messageBoardSectionLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < messageBoardSectionLiferayPage.getItems().length; i++) {

				sb.append(
					MessageBoardSectionSerDes.toJSON(
						messageBoardSectionLiferayPage.getItems()[i]));

				if ((i + 1) <
						messageBoardSectionLiferayPage.getItems().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (messageBoardSectionLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardSectionLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (messageBoardSectionLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardSectionLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (messageBoardSectionLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardSectionLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (messageBoardSectionLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardSectionLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class MessageBoardSectionLiferayPageJSONParser
		extends BaseJSONParser<MessageBoardSectionLiferayPage> {

		protected MessageBoardSectionLiferayPage createDTO() {
			return new MessageBoardSectionLiferayPage();
		}

		protected MessageBoardSectionLiferayPage[] createDTOArray(int size) {
			return new MessageBoardSectionLiferayPage[size];
		}

		protected void setField(
			MessageBoardSectionLiferayPage messageBoardSectionLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					messageBoardSectionLiferayPage.setItems(
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
					messageBoardSectionLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					messageBoardSectionLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					messageBoardSectionLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					messageBoardSectionLiferayPage.setTotalCount(
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