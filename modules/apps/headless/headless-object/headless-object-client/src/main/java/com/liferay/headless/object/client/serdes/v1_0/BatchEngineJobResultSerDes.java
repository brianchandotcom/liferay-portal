/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.client.serdes.v1_0;

import com.liferay.headless.object.client.dto.v1_0.BatchEngineJobResult;
import com.liferay.headless.object.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Alicia García
 * @generated
 */
@Generated("")
public class BatchEngineJobResultSerDes {

	public static BatchEngineJobResult toDTO(String json) {
		BatchEngineJobResultJSONParser batchEngineJobResultJSONParser =
			new BatchEngineJobResultJSONParser();

		return batchEngineJobResultJSONParser.parseToDTO(json);
	}

	public static BatchEngineJobResult[] toDTOs(String json) {
		BatchEngineJobResultJSONParser batchEngineJobResultJSONParser =
			new BatchEngineJobResultJSONParser();

		return batchEngineJobResultJSONParser.parseToDTOs(json);
	}

	public static String toJSON(BatchEngineJobResult batchEngineJobResult) {
		if (batchEngineJobResult == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (batchEngineJobResult.getBatchId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"batchId\": ");

			sb.append(batchEngineJobResult.getBatchId());
		}

		if (batchEngineJobResult.getObjectDefinitionName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectDefinitionName\": ");

			sb.append("\"");

			sb.append(_escape(batchEngineJobResult.getObjectDefinitionName()));

			sb.append("\"");
		}

		if (batchEngineJobResult.getProcessedIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"processedIds\": ");

			sb.append("[");

			for (int i = 0; i < batchEngineJobResult.getProcessedIds().length;
				 i++) {

				sb.append(batchEngineJobResult.getProcessedIds()[i]);

				if ((i + 1) < batchEngineJobResult.getProcessedIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		BatchEngineJobResultJSONParser batchEngineJobResultJSONParser =
			new BatchEngineJobResultJSONParser();

		return batchEngineJobResultJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		BatchEngineJobResult batchEngineJobResult) {

		if (batchEngineJobResult == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (batchEngineJobResult.getBatchId() == null) {
			map.put("batchId", null);
		}
		else {
			map.put(
				"batchId", String.valueOf(batchEngineJobResult.getBatchId()));
		}

		if (batchEngineJobResult.getObjectDefinitionName() == null) {
			map.put("objectDefinitionName", null);
		}
		else {
			map.put(
				"objectDefinitionName",
				String.valueOf(batchEngineJobResult.getObjectDefinitionName()));
		}

		if (batchEngineJobResult.getProcessedIds() == null) {
			map.put("processedIds", null);
		}
		else {
			map.put(
				"processedIds",
				String.valueOf(batchEngineJobResult.getProcessedIds()));
		}

		return map;
	}

	public static class BatchEngineJobResultJSONParser
		extends BaseJSONParser<BatchEngineJobResult> {

		@Override
		protected BatchEngineJobResult createDTO() {
			return new BatchEngineJobResult();
		}

		@Override
		protected BatchEngineJobResult[] createDTOArray(int size) {
			return new BatchEngineJobResult[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "batchId")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "objectDefinitionName")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "processedIds")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			BatchEngineJobResult batchEngineJobResult,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "batchId")) {
				if (jsonParserFieldValue != null) {
					batchEngineJobResult.setBatchId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "objectDefinitionName")) {

				if (jsonParserFieldValue != null) {
					batchEngineJobResult.setObjectDefinitionName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "processedIds")) {
				if (jsonParserFieldValue != null) {
					batchEngineJobResult.setProcessedIds(
						toLongs((Object[])jsonParserFieldValue));
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