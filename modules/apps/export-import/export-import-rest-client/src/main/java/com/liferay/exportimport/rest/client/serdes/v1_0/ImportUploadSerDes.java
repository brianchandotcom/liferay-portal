/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.serdes.v1_0;

import com.liferay.exportimport.rest.client.dto.v1_0.ImportUpload;
import com.liferay.exportimport.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class ImportUploadSerDes {

	public static ImportUpload toDTO(String json) {
		ImportUploadJSONParser importUploadJSONParser =
			new ImportUploadJSONParser();

		return importUploadJSONParser.parseToDTO(json);
	}

	public static ImportUpload[] toDTOs(String json) {
		ImportUploadJSONParser importUploadJSONParser =
			new ImportUploadJSONParser();

		return importUploadJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ImportUpload importUpload) {
		if (importUpload == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (importUpload.getChunkSize() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"chunkSize\": ");

			sb.append(importUpload.getChunkSize());
		}

		if (importUpload.getErrorMessage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"errorMessage\": ");

			sb.append("\"");

			sb.append(_escape(importUpload.getErrorMessage()));

			sb.append("\"");
		}

		if (importUpload.getFileName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fileName\": ");

			sb.append("\"");

			sb.append(_escape(importUpload.getFileName()));

			sb.append("\"");
		}

		if (importUpload.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append("\"");

			sb.append(_escape(importUpload.getId()));

			sb.append("\"");
		}

		if (importUpload.getImportPreview() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"importPreview\": ");

			sb.append(String.valueOf(importUpload.getImportPreview()));
		}

		if (importUpload.getPercentage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"percentage\": ");

			sb.append(importUpload.getPercentage());
		}

		if (importUpload.getSize() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"size\": ");

			sb.append(importUpload.getSize());
		}

		if (importUpload.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");
			sb.append(importUpload.getStatus());
			sb.append("\"");
		}

		if (importUpload.getTotalChunksCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalChunksCount\": ");

			sb.append(importUpload.getTotalChunksCount());
		}

		if (importUpload.getUploadedChunksCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"uploadedChunksCount\": ");

			sb.append(importUpload.getUploadedChunksCount());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ImportUploadJSONParser importUploadJSONParser =
			new ImportUploadJSONParser();

		return importUploadJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ImportUpload importUpload) {
		if (importUpload == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (importUpload.getChunkSize() == null) {
			map.put("chunkSize", null);
		}
		else {
			map.put("chunkSize", String.valueOf(importUpload.getChunkSize()));
		}

		if (importUpload.getErrorMessage() == null) {
			map.put("errorMessage", null);
		}
		else {
			map.put(
				"errorMessage", String.valueOf(importUpload.getErrorMessage()));
		}

		if (importUpload.getFileName() == null) {
			map.put("fileName", null);
		}
		else {
			map.put("fileName", String.valueOf(importUpload.getFileName()));
		}

		if (importUpload.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(importUpload.getId()));
		}

		if (importUpload.getImportPreview() == null) {
			map.put("importPreview", null);
		}
		else {
			map.put(
				"importPreview",
				String.valueOf(importUpload.getImportPreview()));
		}

		if (importUpload.getPercentage() == null) {
			map.put("percentage", null);
		}
		else {
			map.put("percentage", String.valueOf(importUpload.getPercentage()));
		}

		if (importUpload.getSize() == null) {
			map.put("size", null);
		}
		else {
			map.put("size", String.valueOf(importUpload.getSize()));
		}

		if (importUpload.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(importUpload.getStatus()));
		}

		if (importUpload.getTotalChunksCount() == null) {
			map.put("totalChunksCount", null);
		}
		else {
			map.put(
				"totalChunksCount",
				String.valueOf(importUpload.getTotalChunksCount()));
		}

		if (importUpload.getUploadedChunksCount() == null) {
			map.put("uploadedChunksCount", null);
		}
		else {
			map.put(
				"uploadedChunksCount",
				String.valueOf(importUpload.getUploadedChunksCount()));
		}

		return map;
	}

	public static class ImportUploadJSONParser
		extends BaseJSONParser<ImportUpload> {

		@Override
		protected ImportUpload createDTO() {
			return new ImportUpload();
		}

		@Override
		protected ImportUpload[] createDTOArray(int size) {
			return new ImportUpload[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "chunkSize")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "errorMessage")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "fileName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "importPreview")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "percentage")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "size")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "totalChunksCount")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "uploadedChunksCount")) {

				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ImportUpload importUpload, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "chunkSize")) {
				if (jsonParserFieldValue != null) {
					importUpload.setChunkSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "errorMessage")) {
				if (jsonParserFieldValue != null) {
					importUpload.setErrorMessage((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fileName")) {
				if (jsonParserFieldValue != null) {
					importUpload.setFileName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					importUpload.setId((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "importPreview")) {
				if (jsonParserFieldValue != null) {
					importUpload.setImportPreview(
						ImportPreviewSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "percentage")) {
				if (jsonParserFieldValue != null) {
					importUpload.setPercentage(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "size")) {
				if (jsonParserFieldValue != null) {
					importUpload.setSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					importUpload.setStatus(
						ImportUpload.Status.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalChunksCount")) {
				if (jsonParserFieldValue != null) {
					importUpload.setTotalChunksCount(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "uploadedChunksCount")) {

				if (jsonParserFieldValue != null) {
					importUpload.setUploadedChunksCount(
						Integer.valueOf((String)jsonParserFieldValue));
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

		if (value instanceof Collection) {
			Collection<?> collection = (Collection<?>)value;

			return _toJSON(collection.toArray());
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
// LIFERAY-REST-BUILDER-HASH:677360004