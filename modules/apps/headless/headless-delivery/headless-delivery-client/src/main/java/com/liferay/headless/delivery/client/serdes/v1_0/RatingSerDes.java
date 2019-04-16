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

import com.liferay.headless.delivery.client.dto.v1_0.Rating;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;
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
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		sb.append("\"bestRating\": ");

		if (rating.getBestRating() == null) {
			sb.append("null");
		}
		else {
			sb.append(rating.getBestRating());
		}

		sb.append(", ");

		sb.append("\"creator\": ");

		sb.append(CreatorSerDes.toJSON(rating.getCreator()));
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (rating.getDateCreated() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(rating.getDateCreated()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (rating.getDateModified() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(rating.getDateModified()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (rating.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(rating.getId());
		}

		sb.append(", ");

		sb.append("\"ratingValue\": ");

		if (rating.getRatingValue() == null) {
			sb.append("null");
		}
		else {
			sb.append(rating.getRatingValue());
		}

		sb.append(", ");

		sb.append("\"worstRating\": ");

		if (rating.getWorstRating() == null) {
			sb.append("null");
		}
		else {
			sb.append(rating.getWorstRating());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Rating rating) {
		if (rating == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		map.put("bestRating", String.valueOf(rating.getBestRating()));

		map.put("creator", CreatorSerDes.toJSON(rating.getCreator()));

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(rating.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(rating.getDateModified()));

		map.put("id", String.valueOf(rating.getId()));

		map.put("ratingValue", String.valueOf(rating.getRatingValue()));

		map.put("worstRating", String.valueOf(rating.getWorstRating()));

		return map;
	}

	private static class RatingJSONParser extends BaseJSONParser<Rating> {

		@Override
		protected Rating createDTO() {
			return new Rating();
		}

		@Override
		protected Rating[] createDTOArray(int size) {
			return new Rating[size];
		}

		@Override
		protected void setField(
			Rating rating, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "bestRating")) {
				if (jsonParserFieldValue != null) {
					rating.setBestRating(
						Integer.valueOf((String)jsonParserFieldValue));
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
					rating.setDateCreated(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					rating.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					rating.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "ratingValue")) {
				if (jsonParserFieldValue != null) {
					rating.setRatingValue(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "worstRating")) {
				if (jsonParserFieldValue != null) {
					rating.setWorstRating(
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