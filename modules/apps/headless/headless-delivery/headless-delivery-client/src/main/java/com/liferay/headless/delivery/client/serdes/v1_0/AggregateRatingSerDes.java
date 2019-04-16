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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.AggregateRating;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

		sb.append("\"bestRating\": ");

		if (aggregateRating.getBestRating() == null) {
			sb.append("null");
		}
		else {
			sb.append(aggregateRating.getBestRating());
		}

		sb.append(", ");

		sb.append("\"ratingCount\": ");

		if (aggregateRating.getRatingCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(aggregateRating.getRatingCount());
		}

		sb.append(", ");

		sb.append("\"ratingValue\": ");

		if (aggregateRating.getRatingValue() == null) {
			sb.append("null");
		}
		else {
			sb.append(aggregateRating.getRatingValue());
		}

		sb.append(", ");

		sb.append("\"worstRating\": ");

		if (aggregateRating.getWorstRating() == null) {
			sb.append("null");
		}
		else {
			sb.append(aggregateRating.getWorstRating());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(AggregateRating aggregateRating) {
		if (aggregateRating == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		map.put("bestRating", String.valueOf(aggregateRating.getBestRating()));

		map.put(
			"ratingCount", String.valueOf(aggregateRating.getRatingCount()));

		map.put(
			"ratingValue", String.valueOf(aggregateRating.getRatingValue()));

		map.put(
			"worstRating", String.valueOf(aggregateRating.getWorstRating()));

		return map;
	}

	private static class AggregateRatingJSONParser
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
						Integer.valueOf((String)jsonParserFieldValue));
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
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "worstRating")) {
				if (jsonParserFieldValue != null) {
					aggregateRating.setWorstRating(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}