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

import com.liferay.headless.delivery.client.dto.v1_0.ContentDocument;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentDocumentSerDes {

	public static ContentDocument toDTO(String json) {
		ContentDocumentJSONParser contentDocumentJSONParser =
			new ContentDocumentJSONParser();

		return contentDocumentJSONParser.parseToDTO(json);
	}

	public static ContentDocument[] toDTOs(String json) {
		ContentDocumentJSONParser contentDocumentJSONParser =
			new ContentDocumentJSONParser();

		return contentDocumentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContentDocument contentDocument) {
		if (contentDocument == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"contentUrl\": ");

		if (contentDocument.getContentUrl() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contentDocument.getContentUrl());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"description\": ");

		if (contentDocument.getDescription() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contentDocument.getDescription());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"encodingFormat\": ");

		if (contentDocument.getEncodingFormat() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contentDocument.getEncodingFormat());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"fileExtension\": ");

		if (contentDocument.getFileExtension() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contentDocument.getFileExtension());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (contentDocument.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentDocument.getId());
		}

		sb.append(", ");

		sb.append("\"sizeInBytes\": ");

		if (contentDocument.getSizeInBytes() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentDocument.getSizeInBytes());
		}

		sb.append(", ");

		sb.append("\"title\": ");

		if (contentDocument.getTitle() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(contentDocument.getTitle());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ContentDocumentJSONParser
		extends BaseJSONParser<ContentDocument> {

		@Override
		protected ContentDocument createDTO() {
			return new ContentDocument();
		}

		@Override
		protected ContentDocument[] createDTOArray(int size) {
			return new ContentDocument[size];
		}

		@Override
		protected void setField(
			ContentDocument contentDocument, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contentUrl")) {
				if (jsonParserFieldValue != null) {
					contentDocument.setContentUrl((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					contentDocument.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "encodingFormat")) {
				if (jsonParserFieldValue != null) {
					contentDocument.setEncodingFormat(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fileExtension")) {
				if (jsonParserFieldValue != null) {
					contentDocument.setFileExtension(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					contentDocument.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sizeInBytes")) {
				if (jsonParserFieldValue != null) {
					contentDocument.setSizeInBytes(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					contentDocument.setTitle((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}