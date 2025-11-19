/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.client.serdes.v1_0;

import com.liferay.headless.digital.sales.room.client.dto.v1_0.FileEntry;
import com.liferay.headless.digital.sales.room.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Stefano Motta
 * @generated
 */
@Generated("")
public class FileEntrySerDes {

	public static FileEntry toDTO(String json) {
		FileEntryJSONParser fileEntryJSONParser = new FileEntryJSONParser();

		return fileEntryJSONParser.parseToDTO(json);
	}

	public static FileEntry[] toDTOs(String json) {
		FileEntryJSONParser fileEntryJSONParser = new FileEntryJSONParser();

		return fileEntryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FileEntry fileEntry) {
		if (fileEntry == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (fileEntry.getFileBase64() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fileBase64\": ");

			sb.append("\"");

			sb.append(_escape(fileEntry.getFileBase64()));

			sb.append("\"");
		}

		if (fileEntry.getFileName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fileName\": ");

			sb.append("\"");

			sb.append(_escape(fileEntry.getFileName()));

			sb.append("\"");
		}

		if (fileEntry.getFileURL() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fileURL\": ");

			sb.append("\"");

			sb.append(_escape(fileEntry.getFileURL()));

			sb.append("\"");
		}

		if (fileEntry.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(fileEntry.getId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FileEntryJSONParser fileEntryJSONParser = new FileEntryJSONParser();

		return fileEntryJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(FileEntry fileEntry) {
		if (fileEntry == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (fileEntry.getFileBase64() == null) {
			map.put("fileBase64", null);
		}
		else {
			map.put("fileBase64", String.valueOf(fileEntry.getFileBase64()));
		}

		if (fileEntry.getFileName() == null) {
			map.put("fileName", null);
		}
		else {
			map.put("fileName", String.valueOf(fileEntry.getFileName()));
		}

		if (fileEntry.getFileURL() == null) {
			map.put("fileURL", null);
		}
		else {
			map.put("fileURL", String.valueOf(fileEntry.getFileURL()));
		}

		if (fileEntry.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(fileEntry.getId()));
		}

		return map;
	}

	public static class FileEntryJSONParser extends BaseJSONParser<FileEntry> {

		@Override
		protected FileEntry createDTO() {
			return new FileEntry();
		}

		@Override
		protected FileEntry[] createDTOArray(int size) {
			return new FileEntry[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "fileBase64")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "fileName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "fileURL")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			FileEntry fileEntry, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "fileBase64")) {
				if (jsonParserFieldValue != null) {
					fileEntry.setFileBase64((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fileName")) {
				if (jsonParserFieldValue != null) {
					fileEntry.setFileName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fileURL")) {
				if (jsonParserFieldValue != null) {
					fileEntry.setFileURL((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					fileEntry.setId(Long.valueOf((String)jsonParserFieldValue));
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

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value == null) {
			return "null";
		}

		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}