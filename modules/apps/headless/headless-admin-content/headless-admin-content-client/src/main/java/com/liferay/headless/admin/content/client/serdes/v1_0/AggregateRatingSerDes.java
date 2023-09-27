/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.content.client.serdes.v1_0;

import com.liferay.headless.admin.content.client.dto.v1_0.AggregateRating;
import com.liferay.headless.admin.content.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class AggregateRatingSerDes {

	public static AggregateRating toDTO(String json) {
		AggregateRatingJSONParser aggregateRatingJSONParser =
			new AggregateRatingJSONParser();

		return aggregateRatingJSONParser.parseToDTO(json);
	}

	public static AggregateRating[] toDTOs(String json) {
		AggregateRatingJSONParser aggregateRatingJSONParser =
			new AggregateRatingJSONParser();

		return aggregateRatingJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AggregateRating aggregateRating) {
		if (aggregateRating == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (aggregateRating.getBestRating() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"bestRating\": ");

			sb.append(aggregateRating.getBestRating());
		}

		if (aggregateRating.getRatingAverage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"ratingAverage\": ");

			sb.append(aggregateRating.getRatingAverage());
		}

		if (aggregateRating.getRatingCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"ratingCount\": ");

			sb.append(aggregateRating.getRatingCount());
		}

		if (aggregateRating.getRatingValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"ratingValue\": ");

			sb.append(aggregateRating.getRatingValue());
		}

		if (aggregateRating.getWorstRating() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"worstRating\": ");

			sb.append(aggregateRating.getWorstRating());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AggregateRatingJSONParser aggregateRatingJSONParser =
			new AggregateRatingJSONParser();

		return aggregateRatingJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(AggregateRating aggregateRating) {
		if (aggregateRating == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (aggregateRating.getBestRating() == null) {
			map.put("bestRating", null);
		}
		else {
			map.put(
				"bestRating", String.valueOf(aggregateRating.getBestRating()));
		}

		if (aggregateRating.getRatingAverage() == null) {
			map.put("ratingAverage", null);
		}
		else {
			map.put(
				"ratingAverage",
				String.valueOf(aggregateRating.getRatingAverage()));
		}

		if (aggregateRating.getRatingCount() == null) {
			map.put("ratingCount", null);
		}
		else {
			map.put(
				"ratingCount",
				String.valueOf(aggregateRating.getRatingCount()));
		}

		if (aggregateRating.getRatingValue() == null) {
			map.put("ratingValue", null);
		}
		else {
			map.put(
				"ratingValue",
				String.valueOf(aggregateRating.getRatingValue()));
		}

		if (aggregateRating.getWorstRating() == null) {
			map.put("worstRating", null);
		}
		else {
			map.put(
				"worstRating",
				String.valueOf(aggregateRating.getWorstRating()));
		}

		return map;
	}

	public static class AggregateRatingJSONParser
		extends BaseJSONParser<AggregateRating> {

		@Override
		protected AggregateRating createDTO() {
			return new AggregateRating();
		}

		@Override
		protected AggregateRating[] createDTOArray(int size) {
			return new AggregateRating[size];
		}

		@Override
		protected void setField(
			AggregateRating aggregateRating, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "bestRating")) {
				if (jsonParserFieldValue != null) {
					aggregateRating.setBestRating(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "ratingAverage")) {
				if (jsonParserFieldValue != null) {
					aggregateRating.setRatingAverage(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "ratingCount")) {
				if (jsonParserFieldValue != null) {
					aggregateRating.setRatingCount(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "ratingValue")) {
				if (jsonParserFieldValue != null) {
					aggregateRating.setRatingValue(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "worstRating")) {
				if (jsonParserFieldValue != null) {
					aggregateRating.setWorstRating(
						Double.valueOf((String)jsonParserFieldValue));
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