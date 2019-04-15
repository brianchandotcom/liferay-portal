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
import com.liferay.headless.delivery.client.dto.v1_0.CommentLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class CommentLiferayPageSerDes {

	public static CommentLiferayPage toDTO(String json) {
		CommentLiferayPageJSONParser commentLiferayPageJSONParser =
			new CommentLiferayPageJSONParser();

		return commentLiferayPageJSONParser.parseToDTO(json);
	}

	public static CommentLiferayPage[] toDTOs(String json) {
		CommentLiferayPageJSONParser commentLiferayPageJSONParser =
			new CommentLiferayPageJSONParser();

		return commentLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CommentLiferayPage commentLiferayPage) {
		if (commentLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (commentLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < commentLiferayPage.getItems().length; i++) {
				sb.append(
					CommentSerDes.toJSON(commentLiferayPage.getItems()[i]));

				if ((i + 1) < commentLiferayPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (commentLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(commentLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (commentLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(commentLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (commentLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(commentLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (commentLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(commentLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class CommentLiferayPageJSONParser
		extends BaseJSONParser<CommentLiferayPage> {

		protected CommentLiferayPage createDTO() {
			return new CommentLiferayPage();
		}

		protected CommentLiferayPage[] createDTOArray(int size) {
			return new CommentLiferayPage[size];
		}

		protected void setField(
			CommentLiferayPage commentLiferayPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					commentLiferayPage.setItems(
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
					commentLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					commentLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					commentLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					commentLiferayPage.setTotalCount(
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