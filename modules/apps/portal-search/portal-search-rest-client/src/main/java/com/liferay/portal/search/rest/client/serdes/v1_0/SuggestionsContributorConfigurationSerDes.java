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

import com.liferay.portal.search.rest.client.dto.v1_0.SuggestionsContributorConfiguration;
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
public class SuggestionsContributorConfigurationSerDes {

	public static SuggestionsContributorConfiguration toDTO(String json) {
		SuggestionsContributorConfigurationJSONParser
			suggestionsContributorConfigurationJSONParser =
				new SuggestionsContributorConfigurationJSONParser();

		return suggestionsContributorConfigurationJSONParser.parseToDTO(json);
	}

	public static SuggestionsContributorConfiguration[] toDTOs(String json) {
		SuggestionsContributorConfigurationJSONParser
			suggestionsContributorConfigurationJSONParser =
				new SuggestionsContributorConfigurationJSONParser();

		return suggestionsContributorConfigurationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		SuggestionsContributorConfiguration
			suggestionsContributorConfiguration) {

		if (suggestionsContributorConfiguration == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (suggestionsContributorConfiguration.getAttributes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"attributes\": ");

			if (suggestionsContributorConfiguration.getAttributes() instanceof
					String) {

				sb.append("\"");
				sb.append(
					(String)
						suggestionsContributorConfiguration.getAttributes());
				sb.append("\"");
			}
			else {
				sb.append(suggestionsContributorConfiguration.getAttributes());
			}
		}

		if (suggestionsContributorConfiguration.getContributorName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contributorName\": ");

			sb.append("\"");

			sb.append(
				_escape(
					suggestionsContributorConfiguration.getContributorName()));

			sb.append("\"");
		}

		if (suggestionsContributorConfiguration.getDisplayGroupName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayGroupName\": ");

			sb.append("\"");

			sb.append(
				_escape(
					suggestionsContributorConfiguration.getDisplayGroupName()));

			sb.append("\"");
		}

		if (suggestionsContributorConfiguration.getSize() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"size\": ");

			sb.append(suggestionsContributorConfiguration.getSize());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SuggestionsContributorConfigurationJSONParser
			suggestionsContributorConfigurationJSONParser =
				new SuggestionsContributorConfigurationJSONParser();

		return suggestionsContributorConfigurationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		SuggestionsContributorConfiguration
			suggestionsContributorConfiguration) {

		if (suggestionsContributorConfiguration == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (suggestionsContributorConfiguration.getAttributes() == null) {
			map.put("attributes", null);
		}
		else {
			map.put(
				"attributes",
				String.valueOf(
					suggestionsContributorConfiguration.getAttributes()));
		}

		if (suggestionsContributorConfiguration.getContributorName() == null) {
			map.put("contributorName", null);
		}
		else {
			map.put(
				"contributorName",
				String.valueOf(
					suggestionsContributorConfiguration.getContributorName()));
		}

		if (suggestionsContributorConfiguration.getDisplayGroupName() == null) {
			map.put("displayGroupName", null);
		}
		else {
			map.put(
				"displayGroupName",
				String.valueOf(
					suggestionsContributorConfiguration.getDisplayGroupName()));
		}

		if (suggestionsContributorConfiguration.getSize() == null) {
			map.put("size", null);
		}
		else {
			map.put(
				"size",
				String.valueOf(suggestionsContributorConfiguration.getSize()));
		}

		return map;
	}

	public static class SuggestionsContributorConfigurationJSONParser
		extends BaseJSONParser<SuggestionsContributorConfiguration> {

		@Override
		protected SuggestionsContributorConfiguration createDTO() {
			return new SuggestionsContributorConfiguration();
		}

		@Override
		protected SuggestionsContributorConfiguration[] createDTOArray(
			int size) {

			return new SuggestionsContributorConfiguration[size];
		}

		@Override
		protected void setField(
			SuggestionsContributorConfiguration
				suggestionsContributorConfiguration,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "attributes")) {
				if (jsonParserFieldValue != null) {
					suggestionsContributorConfiguration.setAttributes(
						(Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contributorName")) {
				if (jsonParserFieldValue != null) {
					suggestionsContributorConfiguration.setContributorName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "displayGroupName")) {
				if (jsonParserFieldValue != null) {
					suggestionsContributorConfiguration.setDisplayGroupName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "size")) {
				if (jsonParserFieldValue != null) {
					suggestionsContributorConfiguration.setSize(
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