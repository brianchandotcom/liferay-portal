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

import java.util.Collection;
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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"bestRating\": ");

		sb.append(aggregateRating.getBestRating());
		sb.append(", ");

		sb.append("\"ratingCount\": ");

		sb.append(aggregateRating.getRatingCount());
		sb.append(", ");

		sb.append("\"ratingValue\": ");

		sb.append(aggregateRating.getRatingValue());
		sb.append(", ");

		sb.append("\"worstRating\": ");

		sb.append(aggregateRating.getWorstRating());

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<AggregateRating> aggregateRatings) {
		if (aggregateRatings == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (AggregateRating aggregateRating : aggregateRatings) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(aggregateRating));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class AggregateRatingJSONParser
		extends BaseJSONParser<AggregateRating> {

		protected AggregateRating createDTO() {
			return new AggregateRating();
		}

		protected AggregateRating[] createDTOArray(int size) {
			return new AggregateRating[size];
		}

		protected void setField(
			AggregateRating aggregateRating, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "bestRating")) {
				if (jsonParserFieldValue != null) {
					aggregateRating.setBestRating((Number)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "ratingCount")) {
				if (jsonParserFieldValue != null) {
					aggregateRating.setRatingCount(
						(Number)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "ratingValue")) {
				if (jsonParserFieldValue != null) {
					aggregateRating.setRatingValue(
						(Number)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "worstRating")) {
				if (jsonParserFieldValue != null) {
					aggregateRating.setWorstRating(
						(Number)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}