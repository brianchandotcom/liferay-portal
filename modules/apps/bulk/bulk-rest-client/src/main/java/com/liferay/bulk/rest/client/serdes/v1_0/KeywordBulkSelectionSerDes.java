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

import com.liferay.bulk.rest.client.dto.v1_0.KeywordBulkSelection;
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
public class KeywordBulkSelectionSerDes {

	public static KeywordBulkSelection toDTO(String json) {
		KeywordBulkSelectionJSONParser keywordBulkSelectionJSONParser =
			new KeywordBulkSelectionJSONParser();

		return keywordBulkSelectionJSONParser.parseToDTO(json);
	}

	public static KeywordBulkSelection[] toDTOs(String json) {
		KeywordBulkSelectionJSONParser keywordBulkSelectionJSONParser =
			new KeywordBulkSelectionJSONParser();

		return keywordBulkSelectionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(KeywordBulkSelection keywordBulkSelection) {
		if (keywordBulkSelection == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (keywordBulkSelection.getDocumentBulkSelection() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"documentBulkSelection\":");

			sb.append(
				DocumentBulkSelectionSerDes.toJSON(
					keywordBulkSelection.getDocumentBulkSelection()));
		}

		if (keywordBulkSelection.getKeywordsToAdd() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"keywordsToAdd\":");

			sb.append("[");

			for (int i = 0; i < keywordBulkSelection.getKeywordsToAdd().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(keywordBulkSelection.getKeywordsToAdd()[i]));

				sb.append("\"");

				if ((i + 1) < keywordBulkSelection.getKeywordsToAdd().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (keywordBulkSelection.getKeywordsToRemove() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"keywordsToRemove\":");

			sb.append("[");

			for (int i = 0;
				 i < keywordBulkSelection.getKeywordsToRemove().length; i++) {

				sb.append("\"");

				sb.append(
					_escape(keywordBulkSelection.getKeywordsToRemove()[i]));

				sb.append("\"");

				if ((i + 1) <
						keywordBulkSelection.getKeywordsToRemove().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(
		KeywordBulkSelection keywordBulkSelection) {

		if (keywordBulkSelection == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (keywordBulkSelection.getDocumentBulkSelection() == null) {
			map.put("documentBulkSelection", null);
		}
		else {
			map.put(
				"documentBulkSelection",
				DocumentBulkSelectionSerDes.toJSON(
					keywordBulkSelection.getDocumentBulkSelection()));
		}

		if (keywordBulkSelection.getKeywordsToAdd() == null) {
			map.put("keywordsToAdd", null);
		}
		else {
			map.put(
				"keywordsToAdd",
				String.valueOf(keywordBulkSelection.getKeywordsToAdd()));
		}

		if (keywordBulkSelection.getKeywordsToRemove() == null) {
			map.put("keywordsToRemove", null);
		}
		else {
			map.put(
				"keywordsToRemove",
				String.valueOf(keywordBulkSelection.getKeywordsToRemove()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class KeywordBulkSelectionJSONParser
		extends BaseJSONParser<KeywordBulkSelection> {

		@Override
		protected KeywordBulkSelection createDTO() {
			return new KeywordBulkSelection();
		}

		@Override
		protected KeywordBulkSelection[] createDTOArray(int size) {
			return new KeywordBulkSelection[size];
		}

		@Override
		protected void setField(
			KeywordBulkSelection keywordBulkSelection,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "documentBulkSelection")) {
				if (jsonParserFieldValue != null) {
					keywordBulkSelection.setDocumentBulkSelection(
						DocumentBulkSelectionSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywordsToAdd")) {
				if (jsonParserFieldValue != null) {
					keywordBulkSelection.setKeywordsToAdd(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywordsToRemove")) {
				if (jsonParserFieldValue != null) {
					keywordBulkSelection.setKeywordsToRemove(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}