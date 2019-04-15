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
import com.liferay.headless.delivery.client.dto.v1_0.AggregateRatingPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class AggregateRatingPageSerDes {

	public static AggregateRatingPage toDTO(String json) {
		AggregateRatingPageJSONParser aggregateRatingPageJSONParser =
			new AggregateRatingPageJSONParser();

		return aggregateRatingPageJSONParser.parseToDTO(json);
	}

	public static AggregateRatingPage[] toDTOs(String json) {
		AggregateRatingPageJSONParser aggregateRatingPageJSONParser =
			new AggregateRatingPageJSONParser();

		return aggregateRatingPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AggregateRatingPage aggregateRatingPage) {
		if (aggregateRatingPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (aggregateRatingPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < aggregateRatingPage.getItems().length; i++) {
				sb.append(
					AggregateRatingSerDes.toJSON(
						aggregateRatingPage.getItems()[i]));

				if ((i + 1) < aggregateRatingPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (aggregateRatingPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(aggregateRatingPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (aggregateRatingPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(aggregateRatingPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (aggregateRatingPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(aggregateRatingPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (aggregateRatingPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(aggregateRatingPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class AggregateRatingPageJSONParser
		extends BaseJSONParser<AggregateRatingPage> {

		protected AggregateRatingPage createDTO() {
			return new AggregateRatingPage();
		}

		protected AggregateRatingPage[] createDTOArray(int size) {
			return new AggregateRatingPage[size];
		}

		protected void setField(
			AggregateRatingPage aggregateRatingPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					aggregateRatingPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> AggregateRatingSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new AggregateRating[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					aggregateRatingPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					aggregateRatingPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					aggregateRatingPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					aggregateRatingPage.setTotalCount(
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