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
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class BlogPostingImageSerDes {

	public static BlogPostingImage toDTO(String json) {
		BlogPostingImageJSONParser blogPostingImageJSONParser =
			new BlogPostingImageJSONParser();

		return blogPostingImageJSONParser.parseToDTO(json);
	}

	public static BlogPostingImage[] toDTOs(String json) {
		BlogPostingImageJSONParser blogPostingImageJSONParser =
			new BlogPostingImageJSONParser();

		return blogPostingImageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(BlogPostingImage blogPostingImage) {
		if (blogPostingImage == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"contentUrl\": ");

		sb.append("\"");
		sb.append(blogPostingImage.getContentUrl());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"encodingFormat\": ");

		sb.append("\"");
		sb.append(blogPostingImage.getEncodingFormat());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"fileExtension\": ");

		sb.append("\"");
		sb.append(blogPostingImage.getFileExtension());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(blogPostingImage.getId());
		sb.append(", ");

		sb.append("\"sizeInBytes\": ");

		sb.append(blogPostingImage.getSizeInBytes());
		sb.append(", ");

		sb.append("\"title\": ");

		sb.append("\"");
		sb.append(blogPostingImage.getTitle());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"viewableBy\": ");

		sb.append("\"");
		sb.append(blogPostingImage.getViewableBy());
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(
		Collection<BlogPostingImage> blogPostingImages) {

		if (blogPostingImages == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (BlogPostingImage blogPostingImage : blogPostingImages) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(blogPostingImage));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class BlogPostingImageJSONParser
		extends BaseJSONParser<BlogPostingImage> {

		protected BlogPostingImage createDTO() {
			return new BlogPostingImage();
		}

		protected BlogPostingImage[] createDTOArray(int size) {
			return new BlogPostingImage[size];
		}

		protected void setField(
			BlogPostingImage blogPostingImage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contentUrl")) {
				if (jsonParserFieldValue != null) {
					blogPostingImage.setContentUrl(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "encodingFormat")) {
				if (jsonParserFieldValue != null) {
					blogPostingImage.setEncodingFormat(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fileExtension")) {
				if (jsonParserFieldValue != null) {
					blogPostingImage.setFileExtension(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					blogPostingImage.setId((Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sizeInBytes")) {
				if (jsonParserFieldValue != null) {
					blogPostingImage.setSizeInBytes(
						(Number)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					blogPostingImage.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					blogPostingImage.setViewableBy(
						BlogPostingImage.ViewableBy.create(
							(String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}