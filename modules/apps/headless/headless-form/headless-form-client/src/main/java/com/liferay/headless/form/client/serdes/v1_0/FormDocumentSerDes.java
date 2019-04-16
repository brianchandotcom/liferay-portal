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

package com.liferay.headless.form.client.serdes.v1_0;

import com.liferay.headless.form.client.dto.v1_0.FormDocument;
import com.liferay.headless.form.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FormDocumentSerDes {

	public static FormDocument toDTO(String json) {
		FormDocumentJSONParser formDocumentJSONParser =
			new FormDocumentJSONParser();

		return formDocumentJSONParser.parseToDTO(json);
	}

	public static FormDocument[] toDTOs(String json) {
		FormDocumentJSONParser formDocumentJSONParser =
			new FormDocumentJSONParser();

		return formDocumentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FormDocument formDocument) {
		if (formDocument == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"contentUrl\": ");

		if (formDocument.getContentUrl() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(formDocument.getContentUrl());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"encodingFormat\": ");

		if (formDocument.getEncodingFormat() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(formDocument.getEncodingFormat());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"fileExtension\": ");

		if (formDocument.getFileExtension() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(formDocument.getFileExtension());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (formDocument.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(formDocument.getId());
		}

		sb.append(", ");

		sb.append("\"sizeInBytes\": ");

		if (formDocument.getSizeInBytes() == null) {
			sb.append("null");
		}
		else {
			sb.append(formDocument.getSizeInBytes());
		}

		sb.append(", ");

		sb.append("\"title\": ");

		if (formDocument.getTitle() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(formDocument.getTitle());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(FormDocument formDocument) {
		if (formDocument == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (formDocument.getContentUrl() == null) {
			map.put("contentUrl", null);
		}
		else {
			map.put("contentUrl", String.valueOf(formDocument.getContentUrl()));
		}

		if (formDocument.getEncodingFormat() == null) {
			map.put("encodingFormat", null);
		}
		else {
			map.put(
				"encodingFormat",
				String.valueOf(formDocument.getEncodingFormat()));
		}

		if (formDocument.getFileExtension() == null) {
			map.put("fileExtension", null);
		}
		else {
			map.put(
				"fileExtension",
				String.valueOf(formDocument.getFileExtension()));
		}

		if (formDocument.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(formDocument.getId()));
		}

		if (formDocument.getSizeInBytes() == null) {
			map.put("sizeInBytes", null);
		}
		else {
			map.put(
				"sizeInBytes", String.valueOf(formDocument.getSizeInBytes()));
		}

		if (formDocument.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(formDocument.getTitle()));
		}

		return map;
	}

	private static class FormDocumentJSONParser
		extends BaseJSONParser<FormDocument> {

		@Override
		protected FormDocument createDTO() {
			return new FormDocument();
		}

		@Override
		protected FormDocument[] createDTOArray(int size) {
			return new FormDocument[size];
		}

		@Override
		protected void setField(
			FormDocument formDocument, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contentUrl")) {
				if (jsonParserFieldValue != null) {
					formDocument.setContentUrl((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "encodingFormat")) {
				if (jsonParserFieldValue != null) {
					formDocument.setEncodingFormat(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fileExtension")) {
				if (jsonParserFieldValue != null) {
					formDocument.setFileExtension((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					formDocument.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sizeInBytes")) {
				if (jsonParserFieldValue != null) {
					formDocument.setSizeInBytes(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					formDocument.setTitle((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}