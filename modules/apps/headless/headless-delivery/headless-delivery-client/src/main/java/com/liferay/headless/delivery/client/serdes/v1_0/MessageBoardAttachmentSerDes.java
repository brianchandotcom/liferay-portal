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
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class MessageBoardAttachmentSerDes {

	public static MessageBoardAttachment toDTO(String json) {
		MessageBoardAttachmentJSONParser messageBoardAttachmentJSONParser =
			new MessageBoardAttachmentJSONParser();

		return messageBoardAttachmentJSONParser.parseToDTO(json);
	}

	public static MessageBoardAttachment[] toDTOs(String json) {
		MessageBoardAttachmentJSONParser messageBoardAttachmentJSONParser =
			new MessageBoardAttachmentJSONParser();

		return messageBoardAttachmentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(MessageBoardAttachment messageBoardAttachment) {
		if (messageBoardAttachment == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"contentUrl\": ");

		if (messageBoardAttachment.getContentUrl() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(messageBoardAttachment.getContentUrl());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"encodingFormat\": ");

		if (messageBoardAttachment.getEncodingFormat() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(messageBoardAttachment.getEncodingFormat());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"fileExtension\": ");

		if (messageBoardAttachment.getFileExtension() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(messageBoardAttachment.getFileExtension());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (messageBoardAttachment.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardAttachment.getId());
		}

		sb.append(", ");

		sb.append("\"sizeInBytes\": ");

		if (messageBoardAttachment.getSizeInBytes() == null) {
			sb.append("null");
		}
		else {
			sb.append(messageBoardAttachment.getSizeInBytes());
		}

		sb.append(", ");

		sb.append("\"title\": ");

		if (messageBoardAttachment.getTitle() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(messageBoardAttachment.getTitle());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	private static class MessageBoardAttachmentJSONParser
		extends BaseJSONParser<MessageBoardAttachment> {

		@Override
		protected MessageBoardAttachment createDTO() {
			return new MessageBoardAttachment();
		}

		@Override
		protected MessageBoardAttachment[] createDTOArray(int size) {
			return new MessageBoardAttachment[size];
		}

		@Override
		protected void setField(
			MessageBoardAttachment messageBoardAttachment,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contentUrl")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachment.setContentUrl(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "encodingFormat")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachment.setEncodingFormat(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fileExtension")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachment.setFileExtension(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachment.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sizeInBytes")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachment.setSizeInBytes(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					messageBoardAttachment.setTitle(
						(String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}