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

import com.liferay.search.experiences.rest.client.dto.v1_0.General;
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
public class GeneralSerDes {

	public static General toDTO(String json) {
		GeneralJSONParser generalJSONParser = new GeneralJSONParser();

		return generalJSONParser.parseToDTO(json);
	}

	public static General[] toDTOs(String json) {
		GeneralJSONParser generalJSONParser = new GeneralJSONParser();

		return generalJSONParser.parseToDTOs(json);
	}

	public static String toJSON(General general) {
		if (general == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (general.getClauseContributorsExcludes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clauseContributorsExcludes\": ");

			sb.append("[");

			for (int i = 0; i < general.getClauseContributorsExcludes().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(general.getClauseContributorsExcludes()[i]));

				sb.append("\"");

				if ((i + 1) < general.getClauseContributorsExcludes().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (general.getClauseContributorsIncludes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clauseContributorsIncludes\": ");

			sb.append("[");

			for (int i = 0; i < general.getClauseContributorsIncludes().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(general.getClauseContributorsIncludes()[i]));

				sb.append("\"");

				if ((i + 1) < general.getClauseContributorsIncludes().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (general.getEmptySearchEnabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"emptySearchEnabled\": ");

			sb.append(general.getEmptySearchEnabled());
		}

		if (general.getExplain() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"explain\": ");

			sb.append(general.getExplain());
		}

		if (general.getIncludeResponseString() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"includeResponseString\": ");

			sb.append(general.getIncludeResponseString());
		}

		if (general.getSearchableAssetTypes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"searchableAssetTypes\": ");

			sb.append("[");

			for (int i = 0; i < general.getSearchableAssetTypes().length; i++) {
				sb.append("\"");

				sb.append(_escape(general.getSearchableAssetTypes()[i]));

				sb.append("\"");

				if ((i + 1) < general.getSearchableAssetTypes().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		GeneralJSONParser generalJSONParser = new GeneralJSONParser();

		return generalJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(General general) {
		if (general == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (general.getClauseContributorsExcludes() == null) {
			map.put("clauseContributorsExcludes", null);
		}
		else {
			map.put(
				"clauseContributorsExcludes",
				String.valueOf(general.getClauseContributorsExcludes()));
		}

		if (general.getClauseContributorsIncludes() == null) {
			map.put("clauseContributorsIncludes", null);
		}
		else {
			map.put(
				"clauseContributorsIncludes",
				String.valueOf(general.getClauseContributorsIncludes()));
		}

		if (general.getEmptySearchEnabled() == null) {
			map.put("emptySearchEnabled", null);
		}
		else {
			map.put(
				"emptySearchEnabled",
				String.valueOf(general.getEmptySearchEnabled()));
		}

		if (general.getExplain() == null) {
			map.put("explain", null);
		}
		else {
			map.put("explain", String.valueOf(general.getExplain()));
		}

		if (general.getIncludeResponseString() == null) {
			map.put("includeResponseString", null);
		}
		else {
			map.put(
				"includeResponseString",
				String.valueOf(general.getIncludeResponseString()));
		}

		if (general.getSearchableAssetTypes() == null) {
			map.put("searchableAssetTypes", null);
		}
		else {
			map.put(
				"searchableAssetTypes",
				String.valueOf(general.getSearchableAssetTypes()));
		}

		return map;
	}

	public static class GeneralJSONParser extends BaseJSONParser<General> {

		@Override
		protected General createDTO() {
			return new General();
		}

		@Override
		protected General[] createDTOArray(int size) {
			return new General[size];
		}

		@Override
		protected void setField(
			General general, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "clauseContributorsExcludes")) {

				if (jsonParserFieldValue != null) {
					general.setClauseContributorsExcludes(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "clauseContributorsIncludes")) {

				if (jsonParserFieldValue != null) {
					general.setClauseContributorsIncludes(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "emptySearchEnabled")) {

				if (jsonParserFieldValue != null) {
					general.setEmptySearchEnabled(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "explain")) {
				if (jsonParserFieldValue != null) {
					general.setExplain((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "includeResponseString")) {

				if (jsonParserFieldValue != null) {
					general.setIncludeResponseString(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "searchableAssetTypes")) {

				if (jsonParserFieldValue != null) {
					general.setSearchableAssetTypes(
						toStrings((Object[])jsonParserFieldValue));
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