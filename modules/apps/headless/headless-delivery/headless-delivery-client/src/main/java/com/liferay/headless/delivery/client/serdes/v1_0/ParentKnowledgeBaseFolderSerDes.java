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

import com.liferay.headless.delivery.client.dto.v1_0.ParentKnowledgeBaseFolder;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ParentKnowledgeBaseFolderSerDes {

	public static ParentKnowledgeBaseFolder toDTO(String json) {
		ParentKnowledgeBaseFolderJSONParser
			parentKnowledgeBaseFolderJSONParser =
				new ParentKnowledgeBaseFolderJSONParser();

		return parentKnowledgeBaseFolderJSONParser.parseToDTO(json);
	}

	public static ParentKnowledgeBaseFolder[] toDTOs(String json) {
		ParentKnowledgeBaseFolderJSONParser
			parentKnowledgeBaseFolderJSONParser =
				new ParentKnowledgeBaseFolderJSONParser();

		return parentKnowledgeBaseFolderJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ParentKnowledgeBaseFolder parentKnowledgeBaseFolder) {

		if (parentKnowledgeBaseFolder == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (parentKnowledgeBaseFolder.getFolderId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"folderId\":");

			sb.append(parentKnowledgeBaseFolder.getFolderId());
		}

		if (parentKnowledgeBaseFolder.getFolderName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"folderName\":");

			sb.append("\"");

			sb.append(_escape(parentKnowledgeBaseFolder.getFolderName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(
		ParentKnowledgeBaseFolder parentKnowledgeBaseFolder) {

		if (parentKnowledgeBaseFolder == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (parentKnowledgeBaseFolder.getFolderId() == null) {
			map.put("folderId", null);
		}
		else {
			map.put(
				"folderId",
				String.valueOf(parentKnowledgeBaseFolder.getFolderId()));
		}

		if (parentKnowledgeBaseFolder.getFolderName() == null) {
			map.put("folderName", null);
		}
		else {
			map.put(
				"folderName",
				String.valueOf(parentKnowledgeBaseFolder.getFolderName()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class ParentKnowledgeBaseFolderJSONParser
		extends BaseJSONParser<ParentKnowledgeBaseFolder> {

		@Override
		protected ParentKnowledgeBaseFolder createDTO() {
			return new ParentKnowledgeBaseFolder();
		}

		@Override
		protected ParentKnowledgeBaseFolder[] createDTOArray(int size) {
			return new ParentKnowledgeBaseFolder[size];
		}

		@Override
		protected void setField(
			ParentKnowledgeBaseFolder parentKnowledgeBaseFolder,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "folderId")) {
				if (jsonParserFieldValue != null) {
					parentKnowledgeBaseFolder.setFolderId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "folderName")) {
				if (jsonParserFieldValue != null) {
					parentKnowledgeBaseFolder.setFolderName(
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