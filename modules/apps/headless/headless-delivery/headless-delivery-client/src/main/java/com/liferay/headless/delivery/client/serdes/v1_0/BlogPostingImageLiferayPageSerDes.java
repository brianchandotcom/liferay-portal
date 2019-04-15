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

import com.liferay.headless.delivery.client.dto.v1_0.BlogPostingImage;
import com.liferay.headless.delivery.client.dto.v1_0.BlogPostingImageLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class BlogPostingImageLiferayPageSerDes {

	public static BlogPostingImageLiferayPage toDTO(String json) {
		BlogPostingImageLiferayPageJSONParser
			blogPostingImageLiferayPageJSONParser =
				new BlogPostingImageLiferayPageJSONParser();

		return blogPostingImageLiferayPageJSONParser.parseToDTO(json);
	}

	public static BlogPostingImageLiferayPage[] toDTOs(String json) {
		BlogPostingImageLiferayPageJSONParser
			blogPostingImageLiferayPageJSONParser =
				new BlogPostingImageLiferayPageJSONParser();

		return blogPostingImageLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		BlogPostingImageLiferayPage blogPostingImageLiferayPage) {

		if (blogPostingImageLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (blogPostingImageLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < blogPostingImageLiferayPage.getItems().length;
				 i++) {

				sb.append(
					BlogPostingImageSerDes.toJSON(
						blogPostingImageLiferayPage.getItems()[i]));

				if ((i + 1) < blogPostingImageLiferayPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (blogPostingImageLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPostingImageLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (blogPostingImageLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPostingImageLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (blogPostingImageLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPostingImageLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (blogPostingImageLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPostingImageLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class BlogPostingImageLiferayPageJSONParser
		extends BaseJSONParser<BlogPostingImageLiferayPage> {

		protected BlogPostingImageLiferayPage createDTO() {
			return new BlogPostingImageLiferayPage();
		}

		protected BlogPostingImageLiferayPage[] createDTOArray(int size) {
			return new BlogPostingImageLiferayPage[size];
		}

		protected void setField(
			BlogPostingImageLiferayPage blogPostingImageLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					blogPostingImageLiferayPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> BlogPostingImageSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new BlogPostingImage[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					blogPostingImageLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					blogPostingImageLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					blogPostingImageLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					blogPostingImageLiferayPage.setTotalCount(
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