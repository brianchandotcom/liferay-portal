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

package com.liferay.portal.search.rest.client.serdes.v1_0;

import com.liferay.portal.search.rest.client.dto.v1_0.Suggestion;
import com.liferay.portal.search.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class SuggestionSerDes {

	public static Suggestion toDTO(String json) {
		SuggestionJSONParser suggestionJSONParser = new SuggestionJSONParser();

		return suggestionJSONParser.parseToDTO(json);
	}

	public static Suggestion[] toDTOs(String json) {
		SuggestionJSONParser suggestionJSONParser = new SuggestionJSONParser();

		return suggestionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Suggestion suggestion) {
		if (suggestion == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (suggestion.getAttributes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"attributes\": ");

			if (suggestion.getAttributes() instanceof String) {
				sb.append("\"");
				sb.append((String)suggestion.getAttributes());
				sb.append("\"");
			}
			else {
				sb.append(suggestion.getAttributes());
			}
		}

		if (suggestion.getScore() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"score\": ");

			sb.append(suggestion.getScore());
		}

		if (suggestion.getText() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"text\": ");

			sb.append("\"");

			sb.append(_escape(suggestion.getText()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SuggestionJSONParser suggestionJSONParser = new SuggestionJSONParser();

		return suggestionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Suggestion suggestion) {
		if (suggestion == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (suggestion.getAttributes() == null) {
			map.put("attributes", null);
		}
		else {
			map.put("attributes", String.valueOf(suggestion.getAttributes()));
		}

		if (suggestion.getScore() == null) {
			map.put("score", null);
		}
		else {
			map.put("score", String.valueOf(suggestion.getScore()));
		}

		if (suggestion.getText() == null) {
			map.put("text", null);
		}
		else {
			map.put("text", String.valueOf(suggestion.getText()));
		}

		return map;
	}

	public static class SuggestionJSONParser
		extends BaseJSONParser<Suggestion> {

		@Override
		protected Suggestion createDTO() {
			return new Suggestion();
		}

		@Override
		protected Suggestion[] createDTOArray(int size) {
			return new Suggestion[size];
		}

		@Override
		protected void setField(
			Suggestion suggestion, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "attributes")) {
				if (jsonParserFieldValue != null) {
					suggestion.setAttributes((Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "score")) {
				if (jsonParserFieldValue != null) {
					suggestion.setScore(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "text")) {
				if (jsonParserFieldValue != null) {
					suggestion.setText((String)jsonParserFieldValue);
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