/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.client.serdes.v1_0;

import com.liferay.search.experiences.rest.client.dto.v1_0.TextEmbeddingProviderValidationResult;
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
public class TextEmbeddingProviderValidationResultSerDes {

	public static TextEmbeddingProviderValidationResult toDTO(String json) {
		TextEmbeddingProviderValidationResultJSONParser
			textEmbeddingProviderValidationResultJSONParser =
				new TextEmbeddingProviderValidationResultJSONParser();

		return textEmbeddingProviderValidationResultJSONParser.parseToDTO(json);
	}

	public static TextEmbeddingProviderValidationResult[] toDTOs(String json) {
		TextEmbeddingProviderValidationResultJSONParser
			textEmbeddingProviderValidationResultJSONParser =
				new TextEmbeddingProviderValidationResultJSONParser();

		return textEmbeddingProviderValidationResultJSONParser.parseToDTOs(
			json);
	}

	public static String toJSON(
		TextEmbeddingProviderValidationResult
			textEmbeddingProviderValidationResult) {

		if (textEmbeddingProviderValidationResult == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (textEmbeddingProviderValidationResult.getErrorMessage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"errorMessage\": ");

			sb.append("\"");

			sb.append(
				_escape(
					textEmbeddingProviderValidationResult.getErrorMessage()));

			sb.append("\"");
		}

		if (textEmbeddingProviderValidationResult.getExpectedDimensions() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expectedDimensions\": ");

			sb.append(
				textEmbeddingProviderValidationResult.getExpectedDimensions());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		TextEmbeddingProviderValidationResultJSONParser
			textEmbeddingProviderValidationResultJSONParser =
				new TextEmbeddingProviderValidationResultJSONParser();

		return textEmbeddingProviderValidationResultJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		TextEmbeddingProviderValidationResult
			textEmbeddingProviderValidationResult) {

		if (textEmbeddingProviderValidationResult == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (textEmbeddingProviderValidationResult.getErrorMessage() == null) {
			map.put("errorMessage", null);
		}
		else {
			map.put(
				"errorMessage",
				String.valueOf(
					textEmbeddingProviderValidationResult.getErrorMessage()));
		}

		if (textEmbeddingProviderValidationResult.getExpectedDimensions() ==
				null) {

			map.put("expectedDimensions", null);
		}
		else {
			map.put(
				"expectedDimensions",
				String.valueOf(
					textEmbeddingProviderValidationResult.
						getExpectedDimensions()));
		}

		return map;
	}

	public static class TextEmbeddingProviderValidationResultJSONParser
		extends BaseJSONParser<TextEmbeddingProviderValidationResult> {

		@Override
		protected TextEmbeddingProviderValidationResult createDTO() {
			return new TextEmbeddingProviderValidationResult();
		}

		@Override
		protected TextEmbeddingProviderValidationResult[] createDTOArray(
			int size) {

			return new TextEmbeddingProviderValidationResult[size];
		}

		@Override
		protected void setField(
			TextEmbeddingProviderValidationResult
				textEmbeddingProviderValidationResult,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "errorMessage")) {
				if (jsonParserFieldValue != null) {
					textEmbeddingProviderValidationResult.setErrorMessage(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "expectedDimensions")) {

				if (jsonParserFieldValue != null) {
					textEmbeddingProviderValidationResult.setExpectedDimensions(
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