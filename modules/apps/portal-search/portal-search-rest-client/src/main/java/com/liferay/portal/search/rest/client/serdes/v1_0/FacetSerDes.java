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
public class FacetSerDes {

	public static Facet toDTO(String json) {
		FacetJSONParser facetJSONParser = new FacetJSONParser();

		return facetJSONParser.parseToDTO(json);
	}

	public static Facet[] toDTOs(String json) {
		FacetJSONParser facetJSONParser = new FacetJSONParser();

		return facetJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Facet facet) {
		if (facet == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (facet.getAggregationName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"aggregationName\": ");

			sb.append("\"");

			sb.append(_escape(facet.getAggregationName()));

			sb.append("\"");
		}

		if (facet.getAttributes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"attributes\": ");

			sb.append(_toJSON(facet.getAttributes()));
		}

		if (facet.getFrequencyThreshold() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"frequencyThreshold\": ");

			sb.append(facet.getFrequencyThreshold());
		}

		if (facet.getMaxTerms() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"maxTerms\": ");

			sb.append(facet.getMaxTerms());
		}

		if (facet.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(facet.getName()));

			sb.append("\"");
		}

		if (facet.getValues() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"values\": ");

			sb.append("[");

			for (int i = 0; i < facet.getValues().length; i++) {
				sb.append("\"");

				sb.append(_escape(facet.getValues()[i]));

				sb.append("\"");

				if ((i + 1) < facet.getValues().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FacetJSONParser facetJSONParser = new FacetJSONParser();

		return facetJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Facet facet) {
		if (facet == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (facet.getAggregationName() == null) {
			map.put("aggregationName", null);
		}
		else {
			map.put(
				"aggregationName", String.valueOf(facet.getAggregationName()));
		}

		if (facet.getAttributes() == null) {
			map.put("attributes", null);
		}
		else {
			map.put("attributes", String.valueOf(facet.getAttributes()));
		}

		if (facet.getFrequencyThreshold() == null) {
			map.put("frequencyThreshold", null);
		}
		else {
			map.put(
				"frequencyThreshold",
				String.valueOf(facet.getFrequencyThreshold()));
		}

		if (facet.getMaxTerms() == null) {
			map.put("maxTerms", null);
		}
		else {
			map.put("maxTerms", String.valueOf(facet.getMaxTerms()));
		}

		if (facet.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(facet.getName()));
		}

		if (facet.getValues() == null) {
			map.put("values", null);
		}
		else {
			map.put("values", String.valueOf(facet.getValues()));
		}

		return map;
	}

	public static class FacetJSONParser extends BaseJSONParser<Facet> {

		@Override
		protected Facet createDTO() {
			return new Facet();
		}

		@Override
		protected Facet[] createDTOArray(int size) {
			return new Facet[size];
		}

		@Override
		protected void setField(
			Facet facet, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "aggregationName")) {
				if (jsonParserFieldValue != null) {
					facet.setAggregationName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "attributes")) {
				if (jsonParserFieldValue != null) {
					facet.setAttributes(
						(Map)FacetSerDes.toMap((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "frequencyThreshold")) {

				if (jsonParserFieldValue != null) {
					facet.setFrequencyThreshold(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "maxTerms")) {
				if (jsonParserFieldValue != null) {
					facet.setMaxTerms(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					facet.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "values")) {
				if (jsonParserFieldValue != null) {
					facet.setValues((Object[])jsonParserFieldValue);
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