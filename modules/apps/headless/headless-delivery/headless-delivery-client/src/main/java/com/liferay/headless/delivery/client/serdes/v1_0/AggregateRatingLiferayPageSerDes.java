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
import com.liferay.headless.delivery.client.dto.v1_0.AggregateRatingLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class AggregateRatingLiferayPageSerDes {

	public static AggregateRatingLiferayPage toDTO(String json) {
		AggregateRatingLiferayPageJSONParser
			aggregateRatingLiferayPageJSONParser =
				new AggregateRatingLiferayPageJSONParser();

		return aggregateRatingLiferayPageJSONParser.parseToDTO(json);
	}

	public static AggregateRatingLiferayPage[] toDTOs(String json) {
		AggregateRatingLiferayPageJSONParser
			aggregateRatingLiferayPageJSONParser =
				new AggregateRatingLiferayPageJSONParser();

		return aggregateRatingLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		AggregateRatingLiferayPage aggregateRatingLiferayPage) {

		if (aggregateRatingLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (aggregateRatingLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < aggregateRatingLiferayPage.getItems().length;
				 i++) {

				sb.append(
					AggregateRatingSerDes.toJSON(
						aggregateRatingLiferayPage.getItems()[i]));

				if ((i + 1) < aggregateRatingLiferayPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (aggregateRatingLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(aggregateRatingLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (aggregateRatingLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(aggregateRatingLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (aggregateRatingLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(aggregateRatingLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (aggregateRatingLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(aggregateRatingLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class AggregateRatingLiferayPageJSONParser
		extends BaseJSONParser<AggregateRatingLiferayPage> {

		protected AggregateRatingLiferayPage createDTO() {
			return new AggregateRatingLiferayPage();
		}

		protected AggregateRatingLiferayPage[] createDTOArray(int size) {
			return new AggregateRatingLiferayPage[size];
		}

		protected void setField(
			AggregateRatingLiferayPage aggregateRatingLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					aggregateRatingLiferayPage.setItems(
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
					aggregateRatingLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					aggregateRatingLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					aggregateRatingLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					aggregateRatingLiferayPage.setTotalCount(
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