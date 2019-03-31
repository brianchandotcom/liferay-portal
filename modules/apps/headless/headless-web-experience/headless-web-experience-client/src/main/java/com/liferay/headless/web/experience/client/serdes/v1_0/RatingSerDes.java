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

package com.liferay.headless.web.experience.client.serdes.v1_0;

import com.liferay.headless.web.experience.client.dto.v1_0.Rating;
import com.liferay.headless.web.experience.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class RatingSerDes {

	public static Rating toDTO(String json) {
		RatingJSONParser ratingJSONParser = new RatingJSONParser();

		return ratingJSONParser.parseToDTO(json);
	}

	public static Rating[] toDTOs(String json) {
		RatingJSONParser ratingJSONParser = new RatingJSONParser();

		return ratingJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Rating rating) {
		if (rating == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"bestRating\": ");

		sb.append(rating.getBestRating());
		sb.append(", ");

		sb.append("\"creator\": ");

		sb.append(rating.getCreator());
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		sb.append("\"");
		sb.append(rating.getDateCreated());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dateModified\": ");

		sb.append("\"");
		sb.append(rating.getDateModified());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(rating.getId());
		sb.append(", ");

		sb.append("\"ratingValue\": ");

		sb.append(rating.getRatingValue());
		sb.append(", ");

		sb.append("\"worstRating\": ");

		sb.append(rating.getWorstRating());

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<Rating> ratings) {
		if (ratings == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (Rating rating : ratings) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(rating));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class RatingJSONParser extends BaseJSONParser<Rating> {

		protected Rating createDTO() {
			return new Rating();
		}

		protected Rating[] createDTOArray(int size) {
			return new Rating[size];
		}

		protected void setField(
			Rating rating, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "bestRating")) {
				if (jsonParserFieldValue != null) {
					rating.setBestRating((Number)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					rating.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					rating.setDateCreated((Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					rating.setDateModified((Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					rating.setId((Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "ratingValue")) {
				if (jsonParserFieldValue != null) {
					rating.setRatingValue((Number)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "worstRating")) {
				if (jsonParserFieldValue != null) {
					rating.setWorstRating((Number)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}