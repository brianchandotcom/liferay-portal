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
import com.liferay.headless.delivery.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class BlogPostingSerDes {

	public static BlogPosting toDTO(String json) {
		BlogPostingJSONParser blogPostingJSONParser =
			new BlogPostingJSONParser();

		return blogPostingJSONParser.parseToDTO(json);
	}

	public static BlogPosting[] toDTOs(String json) {
		BlogPostingJSONParser blogPostingJSONParser =
			new BlogPostingJSONParser();

		return blogPostingJSONParser.parseToDTOs(json);
	}

	public static String toJSON(BlogPosting blogPosting) {
		if (blogPosting == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		sb.append("\"aggregateRating\": ");

		sb.append(
			AggregateRatingSerDes.toJSON(blogPosting.getAggregateRating()));
		sb.append(", ");

		sb.append("\"alternativeHeadline\": ");

		if (blogPosting.getAlternativeHeadline() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(blogPosting.getAlternativeHeadline());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"articleBody\": ");

		if (blogPosting.getArticleBody() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(blogPosting.getArticleBody());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"creator\": ");

		sb.append(CreatorSerDes.toJSON(blogPosting.getCreator()));
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (blogPosting.getDateCreated() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(blogPosting.getDateCreated()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (blogPosting.getDateModified() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(blogPosting.getDateModified()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"datePublished\": ");

		if (blogPosting.getDatePublished() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(blogPosting.getDatePublished()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"description\": ");

		if (blogPosting.getDescription() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(blogPosting.getDescription());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"encodingFormat\": ");

		if (blogPosting.getEncodingFormat() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(blogPosting.getEncodingFormat());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"friendlyUrlPath\": ");

		if (blogPosting.getFriendlyUrlPath() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(blogPosting.getFriendlyUrlPath());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"headline\": ");

		if (blogPosting.getHeadline() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(blogPosting.getHeadline());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (blogPosting.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPosting.getId());
		}

		sb.append(", ");

		sb.append("\"image\": ");

		sb.append(ImageSerDes.toJSON(blogPosting.getImage()));
		sb.append(", ");

		sb.append("\"keywords\": ");

		if (blogPosting.getKeywords() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < blogPosting.getKeywords().length; i++) {
				sb.append("\"");

				sb.append(blogPosting.getKeywords()[i]);

				sb.append("\"");

				if ((i + 1) < blogPosting.getKeywords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"numberOfComments\": ");

		if (blogPosting.getNumberOfComments() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPosting.getNumberOfComments());
		}

		sb.append(", ");

		sb.append("\"siteId\": ");

		if (blogPosting.getSiteId() == null) {
			sb.append("null");
		}
		else {
			sb.append(blogPosting.getSiteId());
		}

		sb.append(", ");

		sb.append("\"taxonomyCategories\": ");

		if (blogPosting.getTaxonomyCategories() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < blogPosting.getTaxonomyCategories().length;
				 i++) {

				sb.append(
					TaxonomyCategorySerDes.toJSON(
						blogPosting.getTaxonomyCategories()[i]));

				if ((i + 1) < blogPosting.getTaxonomyCategories().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"taxonomyCategoryIds\": ");

		if (blogPosting.getTaxonomyCategoryIds() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < blogPosting.getTaxonomyCategoryIds().length;
				 i++) {

				sb.append(blogPosting.getTaxonomyCategoryIds()[i]);

				if ((i + 1) < blogPosting.getTaxonomyCategoryIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"viewableBy\": ");

		if (blogPosting.getViewableBy() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(blogPosting.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(BlogPosting blogPosting) {
		if (blogPosting == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (blogPosting.getAggregateRating() == null) {
			map.put("aggregateRating", null);
		}
		else {
			map.put(
				"aggregateRating",
				AggregateRatingSerDes.toJSON(blogPosting.getAggregateRating()));
		}

		if (blogPosting.getAlternativeHeadline() == null) {
			map.put("alternativeHeadline", null);
		}
		else {
			map.put(
				"alternativeHeadline",
				String.valueOf(blogPosting.getAlternativeHeadline()));
		}

		if (blogPosting.getArticleBody() == null) {
			map.put("articleBody", null);
		}
		else {
			map.put(
				"articleBody", String.valueOf(blogPosting.getArticleBody()));
		}

		if (blogPosting.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", CreatorSerDes.toJSON(blogPosting.getCreator()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(blogPosting.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(blogPosting.getDateModified()));

		map.put(
			"datePublished",
			liferayToJSONDateFormat.format(blogPosting.getDatePublished()));

		if (blogPosting.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description", String.valueOf(blogPosting.getDescription()));
		}

		if (blogPosting.getEncodingFormat() == null) {
			map.put("encodingFormat", null);
		}
		else {
			map.put(
				"encodingFormat",
				String.valueOf(blogPosting.getEncodingFormat()));
		}

		if (blogPosting.getFriendlyUrlPath() == null) {
			map.put("friendlyUrlPath", null);
		}
		else {
			map.put(
				"friendlyUrlPath",
				String.valueOf(blogPosting.getFriendlyUrlPath()));
		}

		if (blogPosting.getHeadline() == null) {
			map.put("headline", null);
		}
		else {
			map.put("headline", String.valueOf(blogPosting.getHeadline()));
		}

		if (blogPosting.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(blogPosting.getId()));
		}

		if (blogPosting.getImage() == null) {
			map.put("image", null);
		}
		else {
			map.put("image", ImageSerDes.toJSON(blogPosting.getImage()));
		}

		if (blogPosting.getKeywords() == null) {
			map.put("keywords", null);
		}
		else {
			map.put("keywords", String.valueOf(blogPosting.getKeywords()));
		}

		if (blogPosting.getNumberOfComments() == null) {
			map.put("numberOfComments", null);
		}
		else {
			map.put(
				"numberOfComments",
				String.valueOf(blogPosting.getNumberOfComments()));
		}

		if (blogPosting.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(blogPosting.getSiteId()));
		}

		if (blogPosting.getTaxonomyCategories() == null) {
			map.put("taxonomyCategories", null);
		}
		else {
			map.put(
				"taxonomyCategories",
				String.valueOf(blogPosting.getTaxonomyCategories()));
		}

		if (blogPosting.getTaxonomyCategoryIds() == null) {
			map.put("taxonomyCategoryIds", null);
		}
		else {
			map.put(
				"taxonomyCategoryIds",
				String.valueOf(blogPosting.getTaxonomyCategoryIds()));
		}

		if (blogPosting.getViewableBy() == null) {
			map.put("viewableBy", null);
		}
		else {
			map.put("viewableBy", String.valueOf(blogPosting.getViewableBy()));
		}

		return map;
	}

	private static class BlogPostingJSONParser
		extends BaseJSONParser<BlogPosting> {

		@Override
		protected BlogPosting createDTO() {
			return new BlogPosting();
		}

		@Override
		protected BlogPosting[] createDTOArray(int size) {
			return new BlogPosting[size];
		}

		@Override
		protected void setField(
			BlogPosting blogPosting, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "aggregateRating")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setAggregateRating(
						AggregateRatingSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "alternativeHeadline")) {

				if (jsonParserFieldValue != null) {
					blogPosting.setAlternativeHeadline(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "articleBody")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setArticleBody((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "datePublished")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setDatePublished(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "encodingFormat")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setEncodingFormat((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "friendlyUrlPath")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setFriendlyUrlPath(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "headline")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setHeadline((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "image")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setImage(
						ImageSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywords")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setKeywords(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfComments")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setNumberOfComments(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategories")) {

				if (jsonParserFieldValue != null) {
					blogPosting.setTaxonomyCategories(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> TaxonomyCategorySerDes.toDTO(
								(String)object)
						).toArray(
							size -> new TaxonomyCategory[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategoryIds")) {

				if (jsonParserFieldValue != null) {
					blogPosting.setTaxonomyCategoryIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					blogPosting.setViewableBy(
						BlogPosting.ViewableBy.create(
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