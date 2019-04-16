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

import java.util.HashMap;
import java.util.Map;
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
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"contentUrl\": ");

		if (blogPostingImage.getContentUrl() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(blogPostingImage.getContentUrl());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"encodingFormat\": ");

		if (blogPostingImage.getEncodingFormat() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(blogPostingImage.getEncodingFormat());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"fileExtension\": ");

		if (blogPostingImage.getFileExtension() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(blogPostingImage.getFileExtension());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (blogPostingImage.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPostingImage.getId());
		}

		sb.append(", ");

		sb.append("\"sizeInBytes\": ");

		if (blogPostingImage.getSizeInBytes() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPostingImage.getSizeInBytes());
		}

		sb.append(", ");

		sb.append("\"title\": ");

		if (blogPostingImage.getTitle() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(blogPostingImage.getTitle());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"viewableBy\": ");

		if (blogPostingImage.getViewableBy() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(blogPostingImage.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(BlogPostingImage blogPostingImage) {
		if (blogPostingImage == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (blogPostingImage.getContentUrl() == null) {
			map.put("contentUrl", null);
		}
		else {
			map.put(
				"contentUrl", String.valueOf(blogPostingImage.getContentUrl()));
		}

		if (blogPostingImage.getEncodingFormat() == null) {
			map.put("encodingFormat", null);
		}
		else {
			map.put(
				"encodingFormat",
				String.valueOf(blogPostingImage.getEncodingFormat()));
		}

		if (blogPostingImage.getFileExtension() == null) {
			map.put("fileExtension", null);
		}
		else {
			map.put(
				"fileExtension",
				String.valueOf(blogPostingImage.getFileExtension()));
		}

		if (blogPostingImage.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(blogPostingImage.getId()));
		}

		if (blogPostingImage.getSizeInBytes() == null) {
			map.put("sizeInBytes", null);
		}
		else {
			map.put(
				"sizeInBytes",
				String.valueOf(blogPostingImage.getSizeInBytes()));
		}

		if (blogPostingImage.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(blogPostingImage.getTitle()));
		}

		if (blogPostingImage.getViewableBy() == null) {
			map.put("viewableBy", null);
		}
		else {
			map.put(
				"viewableBy", String.valueOf(blogPostingImage.getViewableBy()));
		}

		return map;
	}

	private static class BlogPostingImageJSONParser
		extends BaseJSONParser<BlogPostingImage> {

		@Override
		protected BlogPostingImage createDTO() {
			return new BlogPostingImage();
		}

		@Override
		protected BlogPostingImage[] createDTOArray(int size) {
			return new BlogPostingImage[size];
		}

		@Override
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
					blogPostingImage.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sizeInBytes")) {
				if (jsonParserFieldValue != null) {
					blogPostingImage.setSizeInBytes(
						Integer.valueOf((String)jsonParserFieldValue));
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