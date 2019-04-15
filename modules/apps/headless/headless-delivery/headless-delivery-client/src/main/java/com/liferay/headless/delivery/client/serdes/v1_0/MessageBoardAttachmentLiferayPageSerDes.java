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

import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardAttachment;
import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardAttachmentLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class MessageBoardAttachmentLiferayPageSerDes {

	public static MessageBoardAttachmentLiferayPage toDTO(String json) {
		MessageBoardAttachmentLiferayPageJSONParser
			messageBoardAttachmentLiferayPageJSONParser =
				new MessageBoardAttachmentLiferayPageJSONParser();

		return messageBoardAttachmentLiferayPageJSONParser.parseToDTO(json);
	}

	public static MessageBoardAttachmentLiferayPage[] toDTOs(String json) {
		MessageBoardAttachmentLiferayPageJSONParser
			messageBoardAttachmentLiferayPageJSONParser =
				new MessageBoardAttachmentLiferayPageJSONParser();

		return messageBoardAttachmentLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		MessageBoardAttachmentLiferayPage messageBoardAttachmentLiferayPage) {

		if (messageBoardAttachmentLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (messageBoardAttachmentLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < messageBoardAttachmentLiferayPage.getItems().length; i++) {

				sb.append(
					MessageBoardAttachmentSerDes.toJSON(
						messageBoardAttachmentLiferayPage.getItems()[i]));

				if ((i + 1) <
						messageBoardAttachmentLiferayPage.getItems().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (messageBoardAttachmentLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardAttachmentLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (messageBoardAttachmentLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardAttachmentLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (messageBoardAttachmentLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardAttachmentLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (messageBoardAttachmentLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardAttachmentLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class MessageBoardAttachmentLiferayPageJSONParser
		extends BaseJSONParser<MessageBoardAttachmentLiferayPage> {

		protected MessageBoardAttachmentLiferayPage createDTO() {
			return new MessageBoardAttachmentLiferayPage();
		}

		protected MessageBoardAttachmentLiferayPage[] createDTOArray(int size) {
			return new MessageBoardAttachmentLiferayPage[size];
		}

		protected void setField(
			MessageBoardAttachmentLiferayPage messageBoardAttachmentLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachmentLiferayPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> MessageBoardAttachmentSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new MessageBoardAttachment[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachmentLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachmentLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachmentLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachmentLiferayPage.setTotalCount(
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