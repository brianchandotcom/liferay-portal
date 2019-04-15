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
import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardAttachmentPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class MessageBoardAttachmentPageSerDes {

	public static MessageBoardAttachmentPage toDTO(String json) {
		MessageBoardAttachmentPageJSONParser
			messageBoardAttachmentPageJSONParser =
				new MessageBoardAttachmentPageJSONParser();

		return messageBoardAttachmentPageJSONParser.parseToDTO(json);
	}

	public static MessageBoardAttachmentPage[] toDTOs(String json) {
		MessageBoardAttachmentPageJSONParser
			messageBoardAttachmentPageJSONParser =
				new MessageBoardAttachmentPageJSONParser();

		return messageBoardAttachmentPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		MessageBoardAttachmentPage messageBoardAttachmentPage) {

		if (messageBoardAttachmentPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (messageBoardAttachmentPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < messageBoardAttachmentPage.getItems().length;
				 i++) {

				sb.append(
					MessageBoardAttachmentSerDes.toJSON(
						messageBoardAttachmentPage.getItems()[i]));

				if ((i + 1) < messageBoardAttachmentPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (messageBoardAttachmentPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardAttachmentPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (messageBoardAttachmentPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardAttachmentPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (messageBoardAttachmentPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardAttachmentPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (messageBoardAttachmentPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardAttachmentPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class MessageBoardAttachmentPageJSONParser
		extends BaseJSONParser<MessageBoardAttachmentPage> {

		protected MessageBoardAttachmentPage createDTO() {
			return new MessageBoardAttachmentPage();
		}

		protected MessageBoardAttachmentPage[] createDTOArray(int size) {
			return new MessageBoardAttachmentPage[size];
		}

		protected void setField(
			MessageBoardAttachmentPage messageBoardAttachmentPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachmentPage.setItems(
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
					messageBoardAttachmentPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachmentPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachmentPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachmentPage.setTotalCount(
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