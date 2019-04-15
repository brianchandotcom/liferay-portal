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
import com.liferay.headless.delivery.client.dto.v1_0.BlogPostingLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class BlogPostingLiferayPageSerDes {

	public static BlogPostingLiferayPage toDTO(String json) {
		BlogPostingLiferayPageJSONParser blogPostingLiferayPageJSONParser =
			new BlogPostingLiferayPageJSONParser();

		return blogPostingLiferayPageJSONParser.parseToDTO(json);
	}

	public static BlogPostingLiferayPage[] toDTOs(String json) {
		BlogPostingLiferayPageJSONParser blogPostingLiferayPageJSONParser =
			new BlogPostingLiferayPageJSONParser();

		return blogPostingLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(BlogPostingLiferayPage blogPostingLiferayPage) {
		if (blogPostingLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (blogPostingLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < blogPostingLiferayPage.getItems().length; i++) {
				sb.append(
					BlogPostingSerDes.toJSON(
						blogPostingLiferayPage.getItems()[i]));

				if ((i + 1) < blogPostingLiferayPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (blogPostingLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPostingLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (blogPostingLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPostingLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (blogPostingLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPostingLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (blogPostingLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPostingLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class BlogPostingLiferayPageJSONParser
		extends BaseJSONParser<BlogPostingLiferayPage> {

		protected BlogPostingLiferayPage createDTO() {
			return new BlogPostingLiferayPage();
		}

		protected BlogPostingLiferayPage[] createDTOArray(int size) {
			return new BlogPostingLiferayPage[size];
		}

		protected void setField(
			BlogPostingLiferayPage blogPostingLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					blogPostingLiferayPage.setItems(
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
					blogPostingLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					blogPostingLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					blogPostingLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					blogPostingLiferayPage.setTotalCount(
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