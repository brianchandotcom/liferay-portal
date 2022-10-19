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

import com.liferay.search.experiences.rest.client.dto.v1_0.Configuration;
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
public class ConfigurationSerDes {

	public static Configuration toDTO(String json) {
		ConfigurationJSONParser configurationJSONParser =
			new ConfigurationJSONParser();

		return configurationJSONParser.parseToDTO(json);
	}

	public static Configuration[] toDTOs(String json) {
		ConfigurationJSONParser configurationJSONParser =
			new ConfigurationJSONParser();

		return configurationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Configuration configuration) {
		if (configuration == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (configuration.getAdvancedConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"advancedConfiguration\": ");

			sb.append(String.valueOf(configuration.getAdvancedConfiguration()));
		}

		if (configuration.getAggregationConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"aggregationConfiguration\": ");

			sb.append(
				String.valueOf(configuration.getAggregationConfiguration()));
		}

		if (configuration.getGeneralConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"generalConfiguration\": ");

			sb.append(String.valueOf(configuration.getGeneralConfiguration()));
		}

		if (configuration.getHighlightConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"highlightConfiguration\": ");

			sb.append(
				String.valueOf(configuration.getHighlightConfiguration()));
		}

		if (configuration.getIndexConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"indexConfiguration\": ");

			sb.append(String.valueOf(configuration.getIndexConfiguration()));
		}

		if (configuration.getParameterConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parameterConfiguration\": ");

			sb.append(
				String.valueOf(configuration.getParameterConfiguration()));
		}

		if (configuration.getQueryConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"queryConfiguration\": ");

			sb.append(String.valueOf(configuration.getQueryConfiguration()));
		}

		if (configuration.getSearchContextAttributes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"searchContextAttributes\": ");

			sb.append(_toJSON(configuration.getSearchContextAttributes()));
		}

		if (configuration.getSortConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sortConfiguration\": ");

			sb.append(String.valueOf(configuration.getSortConfiguration()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ConfigurationJSONParser configurationJSONParser =
			new ConfigurationJSONParser();

		return configurationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Configuration configuration) {
		if (configuration == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (configuration.getAdvancedConfiguration() == null) {
			map.put("advancedConfiguration", null);
		}
		else {
			map.put(
				"advancedConfiguration",
				String.valueOf(configuration.getAdvancedConfiguration()));
		}

		if (configuration.getAggregationConfiguration() == null) {
			map.put("aggregationConfiguration", null);
		}
		else {
			map.put(
				"aggregationConfiguration",
				String.valueOf(configuration.getAggregationConfiguration()));
		}

		if (configuration.getGeneralConfiguration() == null) {
			map.put("generalConfiguration", null);
		}
		else {
			map.put(
				"generalConfiguration",
				String.valueOf(configuration.getGeneralConfiguration()));
		}

		if (configuration.getHighlightConfiguration() == null) {
			map.put("highlightConfiguration", null);
		}
		else {
			map.put(
				"highlightConfiguration",
				String.valueOf(configuration.getHighlightConfiguration()));
		}

		if (configuration.getIndexConfiguration() == null) {
			map.put("indexConfiguration", null);
		}
		else {
			map.put(
				"indexConfiguration",
				String.valueOf(configuration.getIndexConfiguration()));
		}

		if (configuration.getParameterConfiguration() == null) {
			map.put("parameterConfiguration", null);
		}
		else {
			map.put(
				"parameterConfiguration",
				String.valueOf(configuration.getParameterConfiguration()));
		}

		if (configuration.getQueryConfiguration() == null) {
			map.put("queryConfiguration", null);
		}
		else {
			map.put(
				"queryConfiguration",
				String.valueOf(configuration.getQueryConfiguration()));
		}

		if (configuration.getSearchContextAttributes() == null) {
			map.put("searchContextAttributes", null);
		}
		else {
			map.put(
				"searchContextAttributes",
				String.valueOf(configuration.getSearchContextAttributes()));
		}

		if (configuration.getSortConfiguration() == null) {
			map.put("sortConfiguration", null);
		}
		else {
			map.put(
				"sortConfiguration",
				String.valueOf(configuration.getSortConfiguration()));
		}

		return map;
	}

	public static class ConfigurationJSONParser
		extends BaseJSONParser<Configuration> {

		@Override
		protected Configuration createDTO() {
			return new Configuration();
		}

		@Override
		protected Configuration[] createDTOArray(int size) {
			return new Configuration[size];
		}

		@Override
		protected void setField(
			Configuration configuration, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "advancedConfiguration")) {
				if (jsonParserFieldValue != null) {
					configuration.setAdvancedConfiguration(
						AdvancedConfigurationSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "aggregationConfiguration")) {

				if (jsonParserFieldValue != null) {
					configuration.setAggregationConfiguration(
						AggregationConfigurationSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "generalConfiguration")) {

				if (jsonParserFieldValue != null) {
					configuration.setGeneralConfiguration(
						GeneralConfigurationSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "highlightConfiguration")) {

				if (jsonParserFieldValue != null) {
					configuration.setHighlightConfiguration(
						HighlightConfigurationSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "indexConfiguration")) {

				if (jsonParserFieldValue != null) {
					configuration.setIndexConfiguration(
						IndexConfigurationSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "parameterConfiguration")) {

				if (jsonParserFieldValue != null) {
					configuration.setParameterConfiguration(
						ParameterConfigurationSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "queryConfiguration")) {

				if (jsonParserFieldValue != null) {
					configuration.setQueryConfiguration(
						QueryConfigurationSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "searchContextAttributes")) {

				if (jsonParserFieldValue != null) {
					configuration.setSearchContextAttributes(
						(Map)ConfigurationSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sortConfiguration")) {
				if (jsonParserFieldValue != null) {
					configuration.setSortConfiguration(
						SortConfigurationSerDes.toDTO(
							(String)jsonParserFieldValue));
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