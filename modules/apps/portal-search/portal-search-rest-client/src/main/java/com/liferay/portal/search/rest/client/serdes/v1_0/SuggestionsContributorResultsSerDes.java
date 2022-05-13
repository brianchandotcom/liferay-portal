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
import com.liferay.portal.search.rest.client.dto.v1_0.SuggestionsContributorResults;
import com.liferay.portal.search.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class SuggestionsContributorResultsSerDes {

	public static SuggestionsContributorResults toDTO(String json) {
		SuggestionsContributorResultsJSONParser
			suggestionsContributorResultsJSONParser =
				new SuggestionsContributorResultsJSONParser();

		return suggestionsContributorResultsJSONParser.parseToDTO(json);
	}

	public static SuggestionsContributorResults[] toDTOs(String json) {
		SuggestionsContributorResultsJSONParser
			suggestionsContributorResultsJSONParser =
				new SuggestionsContributorResultsJSONParser();

		return suggestionsContributorResultsJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		SuggestionsContributorResults suggestionsContributorResults) {

		if (suggestionsContributorResults == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (suggestionsContributorResults.getAttributes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"attributes\": ");

			if (suggestionsContributorResults.getAttributes() instanceof
					String) {

				sb.append("\"");
				sb.append(
					(String)suggestionsContributorResults.getAttributes());
				sb.append("\"");
			}
			else {
				sb.append(suggestionsContributorResults.getAttributes());
			}
		}

		if (suggestionsContributorResults.getDisplayGroupName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayGroupName\": ");

			sb.append("\"");

			sb.append(
				_escape(suggestionsContributorResults.getDisplayGroupName()));

			sb.append("\"");
		}

		if (suggestionsContributorResults.getSuggestions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"suggestions\": ");

			sb.append("[");

			for (int i = 0;
				 i < suggestionsContributorResults.getSuggestions().length;
				 i++) {

				sb.append(
					String.valueOf(
						suggestionsContributorResults.getSuggestions()[i]));

				if ((i + 1) <
						suggestionsContributorResults.getSuggestions().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SuggestionsContributorResultsJSONParser
			suggestionsContributorResultsJSONParser =
				new SuggestionsContributorResultsJSONParser();

		return suggestionsContributorResultsJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		SuggestionsContributorResults suggestionsContributorResults) {

		if (suggestionsContributorResults == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (suggestionsContributorResults.getAttributes() == null) {
			map.put("attributes", null);
		}
		else {
			map.put(
				"attributes",
				String.valueOf(suggestionsContributorResults.getAttributes()));
		}

		if (suggestionsContributorResults.getDisplayGroupName() == null) {
			map.put("displayGroupName", null);
		}
		else {
			map.put(
				"displayGroupName",
				String.valueOf(
					suggestionsContributorResults.getDisplayGroupName()));
		}

		if (suggestionsContributorResults.getSuggestions() == null) {
			map.put("suggestions", null);
		}
		else {
			map.put(
				"suggestions",
				String.valueOf(suggestionsContributorResults.getSuggestions()));
		}

		return map;
	}

	public static class SuggestionsContributorResultsJSONParser
		extends BaseJSONParser<SuggestionsContributorResults> {

		@Override
		protected SuggestionsContributorResults createDTO() {
			return new SuggestionsContributorResults();
		}

		@Override
		protected SuggestionsContributorResults[] createDTOArray(int size) {
			return new SuggestionsContributorResults[size];
		}

		@Override
		protected void setField(
			SuggestionsContributorResults suggestionsContributorResults,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "attributes")) {
				if (jsonParserFieldValue != null) {
					suggestionsContributorResults.setAttributes(
						(Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "displayGroupName")) {
				if (jsonParserFieldValue != null) {
					suggestionsContributorResults.setDisplayGroupName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "suggestions")) {
				if (jsonParserFieldValue != null) {
					suggestionsContributorResults.setSuggestions(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> SuggestionSerDes.toDTO((String)object)
						).toArray(
							size -> new Suggestion[size]
						));
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