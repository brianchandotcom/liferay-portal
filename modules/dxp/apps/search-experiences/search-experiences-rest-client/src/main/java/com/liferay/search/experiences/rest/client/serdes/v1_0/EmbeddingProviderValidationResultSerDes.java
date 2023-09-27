/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.client.serdes.v1_0;

import com.liferay.search.experiences.rest.client.dto.v1_0.EmbeddingProviderValidationResult;
import com.liferay.search.experiences.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class EmbeddingProviderValidationResultSerDes {

	public static EmbeddingProviderValidationResult toDTO(String json) {
		EmbeddingProviderValidationResultJSONParser
			embeddingProviderValidationResultJSONParser =
				new EmbeddingProviderValidationResultJSONParser();

		return embeddingProviderValidationResultJSONParser.parseToDTO(json);
	}

	public static EmbeddingProviderValidationResult[] toDTOs(String json) {
		EmbeddingProviderValidationResultJSONParser
			embeddingProviderValidationResultJSONParser =
				new EmbeddingProviderValidationResultJSONParser();

		return embeddingProviderValidationResultJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		EmbeddingProviderValidationResult embeddingProviderValidationResult) {

		if (embeddingProviderValidationResult == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (embeddingProviderValidationResult.getErrorMessage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"errorMessage\": ");

			sb.append("\"");

			sb.append(
				_escape(embeddingProviderValidationResult.getErrorMessage()));

			sb.append("\"");
		}

		if (embeddingProviderValidationResult.getExpectedDimensions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expectedDimensions\": ");

			sb.append(
				embeddingProviderValidationResult.getExpectedDimensions());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		EmbeddingProviderValidationResultJSONParser
			embeddingProviderValidationResultJSONParser =
				new EmbeddingProviderValidationResultJSONParser();

		return embeddingProviderValidationResultJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		EmbeddingProviderValidationResult embeddingProviderValidationResult) {

		if (embeddingProviderValidationResult == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (embeddingProviderValidationResult.getErrorMessage() == null) {
			map.put("errorMessage", null);
		}
		else {
			map.put(
				"errorMessage",
				String.valueOf(
					embeddingProviderValidationResult.getErrorMessage()));
		}

		if (embeddingProviderValidationResult.getExpectedDimensions() == null) {
			map.put("expectedDimensions", null);
		}
		else {
			map.put(
				"expectedDimensions",
				String.valueOf(
					embeddingProviderValidationResult.getExpectedDimensions()));
		}

		return map;
	}

	public static class EmbeddingProviderValidationResultJSONParser
		extends BaseJSONParser<EmbeddingProviderValidationResult> {

		@Override
		protected EmbeddingProviderValidationResult createDTO() {
			return new EmbeddingProviderValidationResult();
		}

		@Override
		protected EmbeddingProviderValidationResult[] createDTOArray(int size) {
			return new EmbeddingProviderValidationResult[size];
		}

		@Override
		protected void setField(
			EmbeddingProviderValidationResult embeddingProviderValidationResult,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "errorMessage")) {
				if (jsonParserFieldValue != null) {
					embeddingProviderValidationResult.setErrorMessage(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "expectedDimensions")) {

				if (jsonParserFieldValue != null) {
					embeddingProviderValidationResult.setExpectedDimensions(
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