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

import com.liferay.search.experiences.rest.client.dto.v1_0.Hit;
import com.liferay.search.experiences.rest.client.dto.v1_0.SearchHits;
import com.liferay.search.experiences.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class SearchHitsSerDes {

	public static SearchHits toDTO(String json) {
		SearchHitsJSONParser searchHitsJSONParser = new SearchHitsJSONParser();

		return searchHitsJSONParser.parseToDTO(json);
	}

	public static SearchHits[] toDTOs(String json) {
		SearchHitsJSONParser searchHitsJSONParser = new SearchHitsJSONParser();

		return searchHitsJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SearchHits searchHits) {
		if (searchHits == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (searchHits.getHits() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"hits\": ");

			sb.append("[");

			for (int i = 0; i < searchHits.getHits().length; i++) {
				sb.append(String.valueOf(searchHits.getHits()[i]));

				if ((i + 1) < searchHits.getHits().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (searchHits.getMaxScore() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxScore\": ");

			sb.append(searchHits.getMaxScore());
		}

		if (searchHits.getTotalHits() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalHits\": ");

			sb.append(searchHits.getTotalHits());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SearchHitsJSONParser searchHitsJSONParser = new SearchHitsJSONParser();

		return searchHitsJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SearchHits searchHits) {
		if (searchHits == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (searchHits.getHits() == null) {
			map.put("hits", null);
		}
		else {
			map.put("hits", String.valueOf(searchHits.getHits()));
		}

		if (searchHits.getMaxScore() == null) {
			map.put("maxScore", null);
		}
		else {
			map.put("maxScore", String.valueOf(searchHits.getMaxScore()));
		}

		if (searchHits.getTotalHits() == null) {
			map.put("totalHits", null);
		}
		else {
			map.put("totalHits", String.valueOf(searchHits.getTotalHits()));
		}

		return map;
	}

	public static class SearchHitsJSONParser
		extends BaseJSONParser<SearchHits> {

		@Override
		protected SearchHits createDTO() {
			return new SearchHits();
		}

		@Override
		protected SearchHits[] createDTOArray(int size) {
			return new SearchHits[size];
		}

		@Override
		protected void setField(
			SearchHits searchHits, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "hits")) {
				if (jsonParserFieldValue != null) {
					searchHits.setHits(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> HitSerDes.toDTO((String)object)
						).toArray(
							size -> new Hit[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "maxScore")) {
				if (jsonParserFieldValue != null) {
					searchHits.setMaxScore(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalHits")) {
				if (jsonParserFieldValue != null) {
					searchHits.setTotalHits(
						Long.valueOf((String)jsonParserFieldValue));
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