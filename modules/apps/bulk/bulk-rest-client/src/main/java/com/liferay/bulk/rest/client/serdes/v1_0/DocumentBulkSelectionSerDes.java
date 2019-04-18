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

package com.liferay.bulk.rest.client.serdes.v1_0;

import com.liferay.bulk.rest.client.dto.v1_0.DocumentBulkSelection;
import com.liferay.bulk.rest.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class DocumentBulkSelectionSerDes {

	public static DocumentBulkSelection toDTO(String json) {
		DocumentBulkSelectionJSONParser documentBulkSelectionJSONParser =
			new DocumentBulkSelectionJSONParser();

		return documentBulkSelectionJSONParser.parseToDTO(json);
	}

	public static DocumentBulkSelection[] toDTOs(String json) {
		DocumentBulkSelectionJSONParser documentBulkSelectionJSONParser =
			new DocumentBulkSelectionJSONParser();

		return documentBulkSelectionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DocumentBulkSelection documentBulkSelection) {
		if (documentBulkSelection == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (documentBulkSelection.getDocumentIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"documentIds\":");

			sb.append("[");

			for (int i = 0; i < documentBulkSelection.getDocumentIds().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(documentBulkSelection.getDocumentIds()[i]));

				sb.append("\"");

				if ((i + 1) < documentBulkSelection.getDocumentIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (documentBulkSelection.getSelectionScope() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selectionScope\":");

			sb.append(
				SelectionScopeSerDes.toJSON(
					documentBulkSelection.getSelectionScope()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(
		DocumentBulkSelection documentBulkSelection) {

		if (documentBulkSelection == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (documentBulkSelection.getDocumentIds() == null) {
			map.put("documentIds", null);
		}
		else {
			map.put(
				"documentIds",
				String.valueOf(documentBulkSelection.getDocumentIds()));
		}

		if (documentBulkSelection.getSelectionScope() == null) {
			map.put("selectionScope", null);
		}
		else {
			map.put(
				"selectionScope",
				SelectionScopeSerDes.toJSON(
					documentBulkSelection.getSelectionScope()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class DocumentBulkSelectionJSONParser
		extends BaseJSONParser<DocumentBulkSelection> {

		@Override
		protected DocumentBulkSelection createDTO() {
			return new DocumentBulkSelection();
		}

		@Override
		protected DocumentBulkSelection[] createDTOArray(int size) {
			return new DocumentBulkSelection[size];
		}

		@Override
		protected void setField(
			DocumentBulkSelection documentBulkSelection,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "documentIds")) {
				if (jsonParserFieldValue != null) {
					documentBulkSelection.setDocumentIds(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "selectionScope")) {
				if (jsonParserFieldValue != null) {
					documentBulkSelection.setSelectionScope(
						SelectionScopeSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}