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

import com.liferay.search.experiences.rest.client.dto.v1_0.SearchIndex;
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
public class SearchIndexSerDes {

	public static SearchIndex toDTO(String json) {
		SearchIndexJSONParser searchIndexJSONParser =
			new SearchIndexJSONParser();

		return searchIndexJSONParser.parseToDTO(json);
	}

	public static SearchIndex[] toDTOs(String json) {
		SearchIndexJSONParser searchIndexJSONParser =
			new SearchIndexJSONParser();

		return searchIndexJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SearchIndex searchIndex) {
		if (searchIndex == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (searchIndex.getExternal() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"external\": ");

			sb.append(searchIndex.getExternal());
		}

		if (searchIndex.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(searchIndex.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SearchIndexJSONParser searchIndexJSONParser =
			new SearchIndexJSONParser();

		return searchIndexJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SearchIndex searchIndex) {
		if (searchIndex == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (searchIndex.getExternal() == null) {
			map.put("external", null);
		}
		else {
			map.put("external", String.valueOf(searchIndex.getExternal()));
		}

		if (searchIndex.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(searchIndex.getName()));
		}

		return map;
	}

	public static class SearchIndexJSONParser
		extends BaseJSONParser<SearchIndex> {

		@Override
		protected SearchIndex createDTO() {
			return new SearchIndex();
		}

		@Override
		protected SearchIndex[] createDTOArray(int size) {
			return new SearchIndex[size];
		}

		@Override
		protected void setField(
			SearchIndex searchIndex, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "external")) {
				if (jsonParserFieldValue != null) {
					searchIndex.setExternal((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					searchIndex.setName((String)jsonParserFieldValue);
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