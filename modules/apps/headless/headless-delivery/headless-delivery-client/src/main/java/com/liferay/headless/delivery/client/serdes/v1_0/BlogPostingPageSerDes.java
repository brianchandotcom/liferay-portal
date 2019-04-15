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

import com.liferay.headless.delivery.client.dto.v1_0.BlogPosting;
import com.liferay.headless.delivery.client.dto.v1_0.BlogPostingPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class BlogPostingPageSerDes {

	public static BlogPostingPage toDTO(String json) {
		BlogPostingPageJSONParser blogPostingPageJSONParser =
			new BlogPostingPageJSONParser();

		return blogPostingPageJSONParser.parseToDTO(json);
	}

	public static BlogPostingPage[] toDTOs(String json) {
		BlogPostingPageJSONParser blogPostingPageJSONParser =
			new BlogPostingPageJSONParser();

		return blogPostingPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(BlogPostingPage blogPostingPage) {
		if (blogPostingPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (blogPostingPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < blogPostingPage.getItems().length; i++) {
				sb.append(
					BlogPostingSerDes.toJSON(blogPostingPage.getItems()[i]));

				if ((i + 1) < blogPostingPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (blogPostingPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPostingPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (blogPostingPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPostingPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (blogPostingPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPostingPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (blogPostingPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPostingPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class BlogPostingPageJSONParser
		extends BaseJSONParser<BlogPostingPage> {

		protected BlogPostingPage createDTO() {
			return new BlogPostingPage();
		}

		protected BlogPostingPage[] createDTOArray(int size) {
			return new BlogPostingPage[size];
		}

		protected void setField(
			BlogPostingPage blogPostingPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					blogPostingPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> BlogPostingSerDes.toDTO((String)object)
						).toArray(
							size -> new BlogPosting[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					blogPostingPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					blogPostingPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					blogPostingPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					blogPostingPage.setTotalCount(
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