/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.client.serdes.v1_0;

import com.liferay.headless.object.client.dto.v1_0.BatchEngineJobResponse;
import com.liferay.headless.object.client.dto.v1_0.ObjectEntryCMSBulkActionResponse;
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
public class ObjectEntryCMSBulkActionResponseSerDes {

	public static ObjectEntryCMSBulkActionResponse toDTO(String json) {
		ObjectEntryCMSBulkActionResponseJSONParser
			objectEntryCMSBulkActionResponseJSONParser =
				new ObjectEntryCMSBulkActionResponseJSONParser();

		return objectEntryCMSBulkActionResponseJSONParser.parseToDTO(json);
	}

	public static ObjectEntryCMSBulkActionResponse[] toDTOs(String json) {
		ObjectEntryCMSBulkActionResponseJSONParser
			objectEntryCMSBulkActionResponseJSONParser =
				new ObjectEntryCMSBulkActionResponseJSONParser();

		return objectEntryCMSBulkActionResponseJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ObjectEntryCMSBulkActionResponse objectEntryCMSBulkActionResponse) {

		if (objectEntryCMSBulkActionResponse == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (objectEntryCMSBulkActionResponse.getBatchEngineJobResponses() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"batchEngineJobResponses\": ");

			sb.append("[");

			for (int i = 0;
				 i < objectEntryCMSBulkActionResponse.
					 getBatchEngineJobResponses().length;
				 i++) {

				sb.append(
					String.valueOf(
						objectEntryCMSBulkActionResponse.
							getBatchEngineJobResponses()[i]));

				if ((i + 1) < objectEntryCMSBulkActionResponse.
						getBatchEngineJobResponses().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ObjectEntryCMSBulkActionResponseJSONParser
			objectEntryCMSBulkActionResponseJSONParser =
				new ObjectEntryCMSBulkActionResponseJSONParser();

		return objectEntryCMSBulkActionResponseJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ObjectEntryCMSBulkActionResponse objectEntryCMSBulkActionResponse) {

		if (objectEntryCMSBulkActionResponse == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (objectEntryCMSBulkActionResponse.getBatchEngineJobResponses() ==
				null) {

			map.put("batchEngineJobResponses", null);
		}
		else {
			map.put(
				"batchEngineJobResponses",
				String.valueOf(
					objectEntryCMSBulkActionResponse.
						getBatchEngineJobResponses()));
		}

		return map;
	}

	public static class ObjectEntryCMSBulkActionResponseJSONParser
		extends BaseJSONParser<ObjectEntryCMSBulkActionResponse> {

		@Override
		protected ObjectEntryCMSBulkActionResponse createDTO() {
			return new ObjectEntryCMSBulkActionResponse();
		}

		@Override
		protected ObjectEntryCMSBulkActionResponse[] createDTOArray(int size) {
			return new ObjectEntryCMSBulkActionResponse[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(
					jsonParserFieldName, "batchEngineJobResponses")) {

				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ObjectEntryCMSBulkActionResponse objectEntryCMSBulkActionResponse,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "batchEngineJobResponses")) {

				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					BatchEngineJobResponse[] batchEngineJobResponsesArray =
						new BatchEngineJobResponse
							[jsonParserFieldValues.length];

					for (int i = 0; i < batchEngineJobResponsesArray.length;
						 i++) {

						batchEngineJobResponsesArray[i] =
							BatchEngineJobResponseSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					objectEntryCMSBulkActionResponse.setBatchEngineJobResponses(
						batchEngineJobResponsesArray);
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