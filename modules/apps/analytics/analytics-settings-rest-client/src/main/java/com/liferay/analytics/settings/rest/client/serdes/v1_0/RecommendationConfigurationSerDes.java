/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.rest.client.serdes.v1_0;

import com.liferay.analytics.settings.rest.client.dto.v1_0.RecommendationConfiguration;
import com.liferay.analytics.settings.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Riccardo Ferrari
 * @generated
 */
@Generated("")
public class RecommendationConfigurationSerDes {

	public static RecommendationConfiguration toDTO(String json) {
		RecommendationConfigurationJSONParser
			recommendationConfigurationJSONParser =
				new RecommendationConfigurationJSONParser();

		return recommendationConfigurationJSONParser.parseToDTO(json);
	}

	public static RecommendationConfiguration[] toDTOs(String json) {
		RecommendationConfigurationJSONParser
			recommendationConfigurationJSONParser =
				new RecommendationConfigurationJSONParser();

		return recommendationConfigurationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		RecommendationConfiguration recommendationConfiguration) {

		if (recommendationConfiguration == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (recommendationConfiguration.
				getContentRecommenderMostPopularItemsEnabled() != null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentRecommenderMostPopularItemsEnabled\": ");

			sb.append(
				recommendationConfiguration.
					getContentRecommenderMostPopularItemsEnabled());
		}

		if (recommendationConfiguration.
				getContentRecommenderUserPersonalizationEnabled() != null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentRecommenderUserPersonalizationEnabled\": ");

			sb.append(
				recommendationConfiguration.
					getContentRecommenderUserPersonalizationEnabled());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		RecommendationConfigurationJSONParser
			recommendationConfigurationJSONParser =
				new RecommendationConfigurationJSONParser();

		return recommendationConfigurationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		RecommendationConfiguration recommendationConfiguration) {

		if (recommendationConfiguration == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (recommendationConfiguration.
				getContentRecommenderMostPopularItemsEnabled() == null) {

			map.put("contentRecommenderMostPopularItemsEnabled", null);
		}
		else {
			map.put(
				"contentRecommenderMostPopularItemsEnabled",
				String.valueOf(
					recommendationConfiguration.
						getContentRecommenderMostPopularItemsEnabled()));
		}

		if (recommendationConfiguration.
				getContentRecommenderUserPersonalizationEnabled() == null) {

			map.put("contentRecommenderUserPersonalizationEnabled", null);
		}
		else {
			map.put(
				"contentRecommenderUserPersonalizationEnabled",
				String.valueOf(
					recommendationConfiguration.
						getContentRecommenderUserPersonalizationEnabled()));
		}

		return map;
	}

	public static class RecommendationConfigurationJSONParser
		extends BaseJSONParser<RecommendationConfiguration> {

		@Override
		protected RecommendationConfiguration createDTO() {
			return new RecommendationConfiguration();
		}

		@Override
		protected RecommendationConfiguration[] createDTOArray(int size) {
			return new RecommendationConfiguration[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(
					jsonParserFieldName,
					"contentRecommenderMostPopularItemsEnabled")) {

				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"contentRecommenderUserPersonalizationEnabled")) {

				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			RecommendationConfiguration recommendationConfiguration,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName,
					"contentRecommenderMostPopularItemsEnabled")) {

				if (jsonParserFieldValue != null) {
					recommendationConfiguration.
						setContentRecommenderMostPopularItemsEnabled(
							(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"contentRecommenderUserPersonalizationEnabled")) {

				if (jsonParserFieldValue != null) {
					recommendationConfiguration.
						setContentRecommenderUserPersonalizationEnabled(
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