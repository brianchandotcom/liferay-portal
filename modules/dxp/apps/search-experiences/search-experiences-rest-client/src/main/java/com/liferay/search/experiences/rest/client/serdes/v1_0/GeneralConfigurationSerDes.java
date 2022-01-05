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

import com.liferay.search.experiences.rest.client.dto.v1_0.GeneralConfiguration;
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
public class GeneralConfigurationSerDes {

	public static GeneralConfiguration toDTO(String json) {
		GeneralConfigurationJSONParser generalConfigurationJSONParser =
			new GeneralConfigurationJSONParser();

		return generalConfigurationJSONParser.parseToDTO(json);
	}

	public static GeneralConfiguration[] toDTOs(String json) {
		GeneralConfigurationJSONParser generalConfigurationJSONParser =
			new GeneralConfigurationJSONParser();

		return generalConfigurationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(GeneralConfiguration generalConfiguration) {
		if (generalConfiguration == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (generalConfiguration.getClauseContributorsExcludes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clauseContributorsExcludes\": ");

			sb.append("[");

			for (int i = 0;
				 i <
					 generalConfiguration.
						 getClauseContributorsExcludes().length;
				 i++) {

				sb.append("\"");

				sb.append(
					_escape(
						generalConfiguration.getClauseContributorsExcludes()
							[i]));

				sb.append("\"");

				if ((i + 1) < generalConfiguration.
						getClauseContributorsExcludes().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (generalConfiguration.getClauseContributorsIncludes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clauseContributorsIncludes\": ");

			sb.append("[");

			for (int i = 0;
				 i <
					 generalConfiguration.
						 getClauseContributorsIncludes().length;
				 i++) {

				sb.append("\"");

				sb.append(
					_escape(
						generalConfiguration.getClauseContributorsIncludes()
							[i]));

				sb.append("\"");

				if ((i + 1) < generalConfiguration.
						getClauseContributorsIncludes().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (generalConfiguration.getEmptySearchEnabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"emptySearchEnabled\": ");

			sb.append(generalConfiguration.getEmptySearchEnabled());
		}

		if (generalConfiguration.getExplain() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"explain\": ");

			sb.append(generalConfiguration.getExplain());
		}

		if (generalConfiguration.getIncludeResponseString() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"includeResponseString\": ");

			sb.append(generalConfiguration.getIncludeResponseString());
		}

		if (generalConfiguration.getLocaleId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"localeId\": ");

			sb.append("\"");

			sb.append(_escape(generalConfiguration.getLocaleId()));

			sb.append("\"");
		}

		if (generalConfiguration.getQueryString() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"queryString\": ");

			sb.append("\"");

			sb.append(_escape(generalConfiguration.getQueryString()));

			sb.append("\"");
		}

		if (generalConfiguration.getSearchableAssetTypes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"searchableAssetTypes\": ");

			sb.append("[");

			for (int i = 0;
				 i < generalConfiguration.getSearchableAssetTypes().length;
				 i++) {

				sb.append("\"");

				sb.append(
					_escape(generalConfiguration.getSearchableAssetTypes()[i]));

				sb.append("\"");

				if ((i + 1) <
						generalConfiguration.getSearchableAssetTypes().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (generalConfiguration.getTimeZoneId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"timeZoneId\": ");

			sb.append("\"");

			sb.append(_escape(generalConfiguration.getTimeZoneId()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		GeneralConfigurationJSONParser generalConfigurationJSONParser =
			new GeneralConfigurationJSONParser();

		return generalConfigurationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		GeneralConfiguration generalConfiguration) {

		if (generalConfiguration == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (generalConfiguration.getClauseContributorsExcludes() == null) {
			map.put("clauseContributorsExcludes", null);
		}
		else {
			map.put(
				"clauseContributorsExcludes",
				String.valueOf(
					generalConfiguration.getClauseContributorsExcludes()));
		}

		if (generalConfiguration.getClauseContributorsIncludes() == null) {
			map.put("clauseContributorsIncludes", null);
		}
		else {
			map.put(
				"clauseContributorsIncludes",
				String.valueOf(
					generalConfiguration.getClauseContributorsIncludes()));
		}

		if (generalConfiguration.getEmptySearchEnabled() == null) {
			map.put("emptySearchEnabled", null);
		}
		else {
			map.put(
				"emptySearchEnabled",
				String.valueOf(generalConfiguration.getEmptySearchEnabled()));
		}

		if (generalConfiguration.getExplain() == null) {
			map.put("explain", null);
		}
		else {
			map.put(
				"explain", String.valueOf(generalConfiguration.getExplain()));
		}

		if (generalConfiguration.getIncludeResponseString() == null) {
			map.put("includeResponseString", null);
		}
		else {
			map.put(
				"includeResponseString",
				String.valueOf(
					generalConfiguration.getIncludeResponseString()));
		}

		if (generalConfiguration.getLocaleId() == null) {
			map.put("localeId", null);
		}
		else {
			map.put(
				"localeId", String.valueOf(generalConfiguration.getLocaleId()));
		}

		if (generalConfiguration.getQueryString() == null) {
			map.put("queryString", null);
		}
		else {
			map.put(
				"queryString",
				String.valueOf(generalConfiguration.getQueryString()));
		}

		if (generalConfiguration.getSearchableAssetTypes() == null) {
			map.put("searchableAssetTypes", null);
		}
		else {
			map.put(
				"searchableAssetTypes",
				String.valueOf(generalConfiguration.getSearchableAssetTypes()));
		}

		if (generalConfiguration.getTimeZoneId() == null) {
			map.put("timeZoneId", null);
		}
		else {
			map.put(
				"timeZoneId",
				String.valueOf(generalConfiguration.getTimeZoneId()));
		}

		return map;
	}

	public static class GeneralConfigurationJSONParser
		extends BaseJSONParser<GeneralConfiguration> {

		@Override
		protected GeneralConfiguration createDTO() {
			return new GeneralConfiguration();
		}

		@Override
		protected GeneralConfiguration[] createDTOArray(int size) {
			return new GeneralConfiguration[size];
		}

		@Override
		protected void setField(
			GeneralConfiguration generalConfiguration,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "clauseContributorsExcludes")) {

				if (jsonParserFieldValue != null) {
					generalConfiguration.setClauseContributorsExcludes(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "clauseContributorsIncludes")) {

				if (jsonParserFieldValue != null) {
					generalConfiguration.setClauseContributorsIncludes(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "emptySearchEnabled")) {

				if (jsonParserFieldValue != null) {
					generalConfiguration.setEmptySearchEnabled(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "explain")) {
				if (jsonParserFieldValue != null) {
					generalConfiguration.setExplain(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "includeResponseString")) {

				if (jsonParserFieldValue != null) {
					generalConfiguration.setIncludeResponseString(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "localeId")) {
				if (jsonParserFieldValue != null) {
					generalConfiguration.setLocaleId(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "queryString")) {
				if (jsonParserFieldValue != null) {
					generalConfiguration.setQueryString(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "searchableAssetTypes")) {

				if (jsonParserFieldValue != null) {
					generalConfiguration.setSearchableAssetTypes(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "timeZoneId")) {
				if (jsonParserFieldValue != null) {
					generalConfiguration.setTimeZoneId(
						(String)jsonParserFieldValue);
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