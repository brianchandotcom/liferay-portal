/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.client.serdes.v1_0;

import com.liferay.headless.object.client.dto.v1_0.BatchEngineJobResponse;
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
public class BatchEngineJobResponseSerDes {

	public static BatchEngineJobResponse toDTO(String json) {
		BatchEngineJobResponseJSONParser batchEngineJobResponseJSONParser =
			new BatchEngineJobResponseJSONParser();

		return batchEngineJobResponseJSONParser.parseToDTO(json);
	}

	public static BatchEngineJobResponse[] toDTOs(String json) {
		BatchEngineJobResponseJSONParser batchEngineJobResponseJSONParser =
			new BatchEngineJobResponseJSONParser();

		return batchEngineJobResponseJSONParser.parseToDTOs(json);
	}

	public static String toJSON(BatchEngineJobResponse batchEngineJobResponse) {
		if (batchEngineJobResponse == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (batchEngineJobResponse.getBatchId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"batchId\": ");

			sb.append(batchEngineJobResponse.getBatchId());
		}

		if (batchEngineJobResponse.getObjectType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"objectType\": ");

			sb.append("\"");

			sb.append(_escape(batchEngineJobResponse.getObjectType()));

			sb.append("\"");
		}

		if (batchEngineJobResponse.getProcessedIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"processedIds\": ");

			sb.append("[");

			for (int i = 0; i < batchEngineJobResponse.getProcessedIds().length;
				 i++) {

				sb.append(batchEngineJobResponse.getProcessedIds()[i]);

				if ((i + 1) < batchEngineJobResponse.getProcessedIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		BatchEngineJobResponseJSONParser batchEngineJobResponseJSONParser =
			new BatchEngineJobResponseJSONParser();

		return batchEngineJobResponseJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		BatchEngineJobResponse batchEngineJobResponse) {

		if (batchEngineJobResponse == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (batchEngineJobResponse.getBatchId() == null) {
			map.put("batchId", null);
		}
		else {
			map.put(
				"batchId", String.valueOf(batchEngineJobResponse.getBatchId()));
		}

		if (batchEngineJobResponse.getObjectType() == null) {
			map.put("objectType", null);
		}
		else {
			map.put(
				"objectType",
				String.valueOf(batchEngineJobResponse.getObjectType()));
		}

		if (batchEngineJobResponse.getProcessedIds() == null) {
			map.put("processedIds", null);
		}
		else {
			map.put(
				"processedIds",
				String.valueOf(batchEngineJobResponse.getProcessedIds()));
		}

		return map;
	}

	public static class BatchEngineJobResponseJSONParser
		extends BaseJSONParser<BatchEngineJobResponse> {

		@Override
		protected BatchEngineJobResponse createDTO() {
			return new BatchEngineJobResponse();
		}

		@Override
		protected BatchEngineJobResponse[] createDTOArray(int size) {
			return new BatchEngineJobResponse[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "batchId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "objectType")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "processedIds")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			BatchEngineJobResponse batchEngineJobResponse,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "batchId")) {
				if (jsonParserFieldValue != null) {
					batchEngineJobResponse.setBatchId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "objectType")) {
				if (jsonParserFieldValue != null) {
					batchEngineJobResponse.setObjectType(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "processedIds")) {
				if (jsonParserFieldValue != null) {
					batchEngineJobResponse.setProcessedIds(
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