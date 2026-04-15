/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.serdes.v1_0;

import com.liferay.exportimport.rest.client.dto.v1_0.ValidationResponse;
import com.liferay.exportimport.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

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
public class ValidationResponseSerDes {

	public static ValidationResponse toDTO(String json) {
		ValidationResponseJSONParser validationResponseJSONParser =
			new ValidationResponseJSONParser();

		return validationResponseJSONParser.parseToDTO(json);
	}

	public static ValidationResponse[] toDTOs(String json) {
		ValidationResponseJSONParser validationResponseJSONParser =
			new ValidationResponseJSONParser();

		return validationResponseJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ValidationResponse validationResponse) {
		if (validationResponse == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (validationResponse.getErrorMessages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"errorMessages\": ");

			sb.append("[");

			for (int i = 0; i < validationResponse.getErrorMessages().length;
				 i++) {

				sb.append(_toJSON(validationResponse.getErrorMessages()[i]));

				if ((i + 1) < validationResponse.getErrorMessages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (validationResponse.getFileEntryId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fileEntryId\": ");

			sb.append(validationResponse.getFileEntryId());
		}

		if (validationResponse.getSuccess() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"success\": ");

			sb.append(validationResponse.getSuccess());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ValidationResponseJSONParser validationResponseJSONParser =
			new ValidationResponseJSONParser();

		return validationResponseJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ValidationResponse validationResponse) {

		if (validationResponse == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (validationResponse.getErrorMessages() == null) {
			map.put("errorMessages", null);
		}
		else {
			map.put(
				"errorMessages",
				String.valueOf(validationResponse.getErrorMessages()));
		}

		if (validationResponse.getFileEntryId() == null) {
			map.put("fileEntryId", null);
		}
		else {
			map.put(
				"fileEntryId",
				String.valueOf(validationResponse.getFileEntryId()));
		}

		if (validationResponse.getSuccess() == null) {
			map.put("success", null);
		}
		else {
			map.put("success", String.valueOf(validationResponse.getSuccess()));
		}

		return map;
	}

	public static class ValidationResponseJSONParser
		extends BaseJSONParser<ValidationResponse> {

		@Override
		protected ValidationResponse createDTO() {
			return new ValidationResponse();
		}

		@Override
		protected ValidationResponse[] createDTOArray(int size) {
			return new ValidationResponse[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "errorMessages")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "fileEntryId")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "success")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ValidationResponse validationResponse, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "errorMessages")) {
				if (jsonParserFieldValue != null) {
					validationResponse.setErrorMessages(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fileEntryId")) {
				if (jsonParserFieldValue != null) {
					validationResponse.setFileEntryId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "success")) {
				if (jsonParserFieldValue != null) {
					validationResponse.setSuccess(
						(Boolean)jsonParserFieldValue);
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
// LIFERAY-REST-BUILDER-HASH:1995535090