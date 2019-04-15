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
import com.liferay.headless.delivery.client.dto.v1_0.BlogPostingImagePage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class BlogPostingImagePageSerDes {

	public static BlogPostingImagePage toDTO(String json) {
		BlogPostingImagePageJSONParser blogPostingImagePageJSONParser =
			new BlogPostingImagePageJSONParser();

		return blogPostingImagePageJSONParser.parseToDTO(json);
	}

	public static BlogPostingImagePage[] toDTOs(String json) {
		BlogPostingImagePageJSONParser blogPostingImagePageJSONParser =
			new BlogPostingImagePageJSONParser();

		return blogPostingImagePageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(BlogPostingImagePage blogPostingImagePage) {
		if (blogPostingImagePage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (blogPostingImagePage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < blogPostingImagePage.getItems().length; i++) {
				sb.append(
					BlogPostingImageSerDes.toJSON(
						blogPostingImagePage.getItems()[i]));

				if ((i + 1) < blogPostingImagePage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (blogPostingImagePage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPostingImagePage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (blogPostingImagePage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPostingImagePage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (blogPostingImagePage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPostingImagePage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (blogPostingImagePage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPostingImagePage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class BlogPostingImagePageJSONParser
		extends BaseJSONParser<BlogPostingImagePage> {

		protected BlogPostingImagePage createDTO() {
			return new BlogPostingImagePage();
		}

		protected BlogPostingImagePage[] createDTOArray(int size) {
			return new BlogPostingImagePage[size];
		}

		protected void setField(
			BlogPostingImagePage blogPostingImagePage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					blogPostingImagePage.setItems(
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
					blogPostingImagePage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					blogPostingImagePage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					blogPostingImagePage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					blogPostingImagePage.setTotalCount(
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