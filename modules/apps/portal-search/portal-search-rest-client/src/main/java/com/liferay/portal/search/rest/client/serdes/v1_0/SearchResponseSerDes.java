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

import com.liferay.portal.search.rest.client.dto.v1_0.SearchResponse;
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
public class SearchResponseSerDes {

	public static SearchResponse toDTO(String json) {
		SearchResponseJSONParser searchResponseJSONParser =
			new SearchResponseJSONParser();

		return searchResponseJSONParser.parseToDTO(json);
	}

	public static SearchResponse[] toDTOs(String json) {
		SearchResponseJSONParser searchResponseJSONParser =
			new SearchResponseJSONParser();

		return searchResponseJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SearchResponse searchResponse) {
		if (searchResponse == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (searchResponse.getAggregationResults() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"aggregationResults\": ");

			sb.append(_toJSON(searchResponse.getAggregationResults()));
		}

		if (searchResponse.getDocuments() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"documents\": ");

			sb.append("[");

			for (int i = 0; i < searchResponse.getDocuments().length; i++) {
				sb.append("\"");

				sb.append(_escape(searchResponse.getDocuments()[i]));

				sb.append("\"");

				if ((i + 1) < searchResponse.getDocuments().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (searchResponse.getFacets() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"facets\": ");

			sb.append(_toJSON(searchResponse.getFacets()));
		}

		if (searchResponse.getMaxScore() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxScore\": ");

			sb.append(searchResponse.getMaxScore());
		}

		if (searchResponse.getPage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"page\": ");

			sb.append(searchResponse.getPage());
		}

		if (searchResponse.getPageSize() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pageSize\": ");

			sb.append(searchResponse.getPageSize());
		}

		if (searchResponse.getRequest() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"request\": ");

			if (searchResponse.getRequest() instanceof String) {
				sb.append("\"");
				sb.append((String)searchResponse.getRequest());
				sb.append("\"");
			}
			else {
				sb.append(searchResponse.getRequest());
			}
		}

		if (searchResponse.getResponse() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"response\": ");

			if (searchResponse.getResponse() instanceof String) {
				sb.append("\"");
				sb.append((String)searchResponse.getResponse());
				sb.append("\"");
			}
			else {
				sb.append(searchResponse.getResponse());
			}
		}

		if (searchResponse.getTotalHits() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalHits\": ");

			sb.append(searchResponse.getTotalHits());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SearchResponseJSONParser searchResponseJSONParser =
			new SearchResponseJSONParser();

		return searchResponseJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SearchResponse searchResponse) {
		if (searchResponse == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (searchResponse.getAggregationResults() == null) {
			map.put("aggregationResults", null);
		}
		else {
			map.put(
				"aggregationResults",
				String.valueOf(searchResponse.getAggregationResults()));
		}

		if (searchResponse.getDocuments() == null) {
			map.put("documents", null);
		}
		else {
			map.put("documents", String.valueOf(searchResponse.getDocuments()));
		}

		if (searchResponse.getFacets() == null) {
			map.put("facets", null);
		}
		else {
			map.put("facets", String.valueOf(searchResponse.getFacets()));
		}

		if (searchResponse.getMaxScore() == null) {
			map.put("maxScore", null);
		}
		else {
			map.put("maxScore", String.valueOf(searchResponse.getMaxScore()));
		}

		if (searchResponse.getPage() == null) {
			map.put("page", null);
		}
		else {
			map.put("page", String.valueOf(searchResponse.getPage()));
		}

		if (searchResponse.getPageSize() == null) {
			map.put("pageSize", null);
		}
		else {
			map.put("pageSize", String.valueOf(searchResponse.getPageSize()));
		}

		if (searchResponse.getRequest() == null) {
			map.put("request", null);
		}
		else {
			map.put("request", String.valueOf(searchResponse.getRequest()));
		}

		if (searchResponse.getResponse() == null) {
			map.put("response", null);
		}
		else {
			map.put("response", String.valueOf(searchResponse.getResponse()));
		}

		if (searchResponse.getTotalHits() == null) {
			map.put("totalHits", null);
		}
		else {
			map.put("totalHits", String.valueOf(searchResponse.getTotalHits()));
		}

		return map;
	}

	public static class SearchResponseJSONParser
		extends BaseJSONParser<SearchResponse> {

		@Override
		protected SearchResponse createDTO() {
			return new SearchResponse();
		}

		@Override
		protected SearchResponse[] createDTOArray(int size) {
			return new SearchResponse[size];
		}

		@Override
		protected void setField(
			SearchResponse searchResponse, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "aggregationResults")) {
				if (jsonParserFieldValue != null) {
					searchResponse.setAggregationResults(
						(Map)SearchResponseSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "documents")) {
				if (jsonParserFieldValue != null) {
					searchResponse.setDocuments((Object[])jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "facets")) {
				if (jsonParserFieldValue != null) {
					searchResponse.setFacets(
						(Map)SearchResponseSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "maxScore")) {
				if (jsonParserFieldValue != null) {
					searchResponse.setMaxScore(
						Float.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					searchResponse.setPage(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					searchResponse.setPageSize(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "request")) {
				if (jsonParserFieldValue != null) {
					searchResponse.setRequest((Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "response")) {
				if (jsonParserFieldValue != null) {
					searchResponse.setResponse((Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalHits")) {
				if (jsonParserFieldValue != null) {
					searchResponse.setTotalHits(
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