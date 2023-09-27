/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.ParentKnowledgeBaseFolder;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

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

			sb.append("\"folderId\": ");

			sb.append(parentKnowledgeBaseFolder.getFolderId());
		}

		if (parentKnowledgeBaseFolder.getFolderName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"folderName\": ");

			sb.append("\"");

			sb.append(_escape(parentKnowledgeBaseFolder.getFolderName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ParentKnowledgeBaseFolderJSONParser
			parentKnowledgeBaseFolderJSONParser =
				new ParentKnowledgeBaseFolderJSONParser();

		return parentKnowledgeBaseFolderJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ParentKnowledgeBaseFolder parentKnowledgeBaseFolder) {

		if (parentKnowledgeBaseFolder == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

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

	public static class ParentKnowledgeBaseFolderJSONParser
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
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}