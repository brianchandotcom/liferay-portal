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

import com.liferay.headless.delivery.client.dto.v1_0.Comment;
import com.liferay.headless.delivery.client.dto.v1_0.CommentPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class CommentPageSerDes {

	public static CommentPage toDTO(String json) {
		CommentPageJSONParser commentPageJSONParser =
			new CommentPageJSONParser();

		return commentPageJSONParser.parseToDTO(json);
	}

	public static CommentPage[] toDTOs(String json) {
		CommentPageJSONParser commentPageJSONParser =
			new CommentPageJSONParser();

		return commentPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CommentPage commentPage) {
		if (commentPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (commentPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < commentPage.getItems().length; i++) {
				sb.append(CommentSerDes.toJSON(commentPage.getItems()[i]));

				if ((i + 1) < commentPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (commentPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(commentPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (commentPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(commentPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (commentPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(commentPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (commentPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(commentPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class CommentPageJSONParser
		extends BaseJSONParser<CommentPage> {

		protected CommentPage createDTO() {
			return new CommentPage();
		}

		protected CommentPage[] createDTOArray(int size) {
			return new CommentPage[size];
		}

		protected void setField(
			CommentPage commentPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					commentPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> CommentSerDes.toDTO((String)object)
						).toArray(
							size -> new Comment[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					commentPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					commentPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					commentPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					commentPage.setTotalCount(
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