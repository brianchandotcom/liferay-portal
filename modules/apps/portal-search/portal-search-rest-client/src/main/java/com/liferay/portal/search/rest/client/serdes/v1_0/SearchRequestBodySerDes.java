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

import com.liferay.portal.search.rest.client.dto.v1_0.Facet;
import com.liferay.portal.search.rest.client.dto.v1_0.SearchRequestBody;
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
public class SearchRequestBodySerDes {

	public static SearchRequestBody toDTO(String json) {
		SearchRequestBodyJSONParser searchRequestBodyJSONParser =
			new SearchRequestBodyJSONParser();

		return searchRequestBodyJSONParser.parseToDTO(json);
	}

	public static SearchRequestBody[] toDTOs(String json) {
		SearchRequestBodyJSONParser searchRequestBodyJSONParser =
			new SearchRequestBodyJSONParser();

		return searchRequestBodyJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SearchRequestBody searchRequestBody) {
		if (searchRequestBody == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (searchRequestBody.getFacets() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"facets\": ");

			sb.append("[");

			for (int i = 0; i < searchRequestBody.getFacets().length; i++) {
				sb.append(String.valueOf(searchRequestBody.getFacets()[i]));

				if ((i + 1) < searchRequestBody.getFacets().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (searchRequestBody.getSearchContextAttributes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"searchContextAttributes\": ");

			sb.append(_toJSON(searchRequestBody.getSearchContextAttributes()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SearchRequestBodyJSONParser searchRequestBodyJSONParser =
			new SearchRequestBodyJSONParser();

		return searchRequestBodyJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		SearchRequestBody searchRequestBody) {

		if (searchRequestBody == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (searchRequestBody.getFacets() == null) {
			map.put("facets", null);
		}
		else {
			map.put("facets", String.valueOf(searchRequestBody.getFacets()));
		}

		if (searchRequestBody.getSearchContextAttributes() == null) {
			map.put("searchContextAttributes", null);
		}
		else {
			map.put(
				"searchContextAttributes",
				String.valueOf(searchRequestBody.getSearchContextAttributes()));
		}

		return map;
	}

	public static class SearchRequestBodyJSONParser
		extends BaseJSONParser<SearchRequestBody> {

		@Override
		protected SearchRequestBody createDTO() {
			return new SearchRequestBody();
		}

		@Override
		protected SearchRequestBody[] createDTOArray(int size) {
			return new SearchRequestBody[size];
		}

		@Override
		protected void setField(
			SearchRequestBody searchRequestBody, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "facets")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					Facet[] facetsArray =
						new Facet[jsonParserFieldValues.length];

					for (int i = 0; i < facetsArray.length; i++) {
						facetsArray[i] = FacetSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					searchRequestBody.setFacets(facetsArray);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "searchContextAttributes")) {

				if (jsonParserFieldValue != null) {
					searchRequestBody.setSearchContextAttributes(
						(Map)SearchRequestBodySerDes.toMap(
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