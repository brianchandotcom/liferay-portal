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
import com.liferay.headless.delivery.client.dto.v1_0.RatingPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class RatingPageSerDes {

	public static RatingPage toDTO(String json) {
		RatingPageJSONParser ratingPageJSONParser = new RatingPageJSONParser();

		return ratingPageJSONParser.parseToDTO(json);
	}

	public static RatingPage[] toDTOs(String json) {
		RatingPageJSONParser ratingPageJSONParser = new RatingPageJSONParser();

		return ratingPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(RatingPage ratingPage) {
		if (ratingPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (ratingPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < ratingPage.getItems().length; i++) {
				sb.append(RatingSerDes.toJSON(ratingPage.getItems()[i]));

				if ((i + 1) < ratingPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (ratingPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(ratingPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (ratingPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(ratingPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (ratingPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(ratingPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (ratingPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(ratingPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class RatingPageJSONParser
		extends BaseJSONParser<RatingPage> {

		protected RatingPage createDTO() {
			return new RatingPage();
		}

		protected RatingPage[] createDTOArray(int size) {
			return new RatingPage[size];
		}

		protected void setField(
			RatingPage ratingPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					ratingPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> RatingSerDes.toDTO((String)object)
						).toArray(
							size -> new Rating[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					ratingPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					ratingPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					ratingPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					ratingPage.setTotalCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}