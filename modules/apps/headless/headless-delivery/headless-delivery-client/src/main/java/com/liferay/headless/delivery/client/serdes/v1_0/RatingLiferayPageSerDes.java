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
import com.liferay.headless.delivery.client.dto.v1_0.RatingLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class RatingLiferayPageSerDes {

	public static RatingLiferayPage toDTO(String json) {
		RatingLiferayPageJSONParser ratingLiferayPageJSONParser =
			new RatingLiferayPageJSONParser();

		return ratingLiferayPageJSONParser.parseToDTO(json);
	}

	public static RatingLiferayPage[] toDTOs(String json) {
		RatingLiferayPageJSONParser ratingLiferayPageJSONParser =
			new RatingLiferayPageJSONParser();

		return ratingLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(RatingLiferayPage ratingLiferayPage) {
		if (ratingLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (ratingLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < ratingLiferayPage.getItems().length; i++) {
				sb.append(RatingSerDes.toJSON(ratingLiferayPage.getItems()[i]));

				if ((i + 1) < ratingLiferayPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (ratingLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(ratingLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (ratingLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(ratingLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (ratingLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(ratingLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (ratingLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(ratingLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class RatingLiferayPageJSONParser
		extends BaseJSONParser<RatingLiferayPage> {

		protected RatingLiferayPage createDTO() {
			return new RatingLiferayPage();
		}

		protected RatingLiferayPage[] createDTOArray(int size) {
			return new RatingLiferayPage[size];
		}

		protected void setField(
			RatingLiferayPage ratingLiferayPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					ratingLiferayPage.setItems(
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
					ratingLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					ratingLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					ratingLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					ratingLiferayPage.setTotalCount(
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