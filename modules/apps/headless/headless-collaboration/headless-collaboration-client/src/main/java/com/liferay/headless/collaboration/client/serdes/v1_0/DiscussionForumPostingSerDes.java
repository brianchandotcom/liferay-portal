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

package com.liferay.headless.collaboration.client.serdes.v1_0;

import com.liferay.headless.collaboration.client.dto.v1_0.DiscussionForumPosting;
import com.liferay.headless.collaboration.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.collaboration.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class DiscussionForumPostingSerDes {

	public static DiscussionForumPosting toDTO(String json) {
		DiscussionForumPostingJSONParser discussionForumPostingJSONParser =
			new DiscussionForumPostingJSONParser();

		return discussionForumPostingJSONParser.parseToDTO(json);
	}

	public static DiscussionForumPosting[] toDTOs(String json) {
		DiscussionForumPostingJSONParser discussionForumPostingJSONParser =
			new DiscussionForumPostingJSONParser();

		return discussionForumPostingJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DiscussionForumPosting discussionForumPosting) {
		if (discussionForumPosting == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"aggregateRating\": ");

		sb.append(discussionForumPosting.getAggregateRating());
		sb.append(", ");

		sb.append("\"anonymous\": ");

		sb.append(discussionForumPosting.getAnonymous());
		sb.append(", ");

		sb.append("\"articleBody\": ");

		sb.append("\"");
		sb.append(discussionForumPosting.getArticleBody());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"contentSpaceId\": ");

		sb.append(discussionForumPosting.getContentSpaceId());
		sb.append(", ");

		sb.append("\"creator\": ");

		sb.append(discussionForumPosting.getCreator());
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		sb.append("\"");
		sb.append(discussionForumPosting.getDateCreated());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dateModified\": ");

		sb.append("\"");
		sb.append(discussionForumPosting.getDateModified());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"headline\": ");

		sb.append("\"");
		sb.append(discussionForumPosting.getHeadline());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(discussionForumPosting.getId());
		sb.append(", ");

		sb.append("\"keywords\": ");

		if (discussionForumPosting.getKeywords() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < discussionForumPosting.getKeywords().length;
				 i++) {

				sb.append("\"");
				sb.append(discussionForumPosting.getKeywords()[i]);
				sb.append("\"");

				if ((i + 1) < discussionForumPosting.getKeywords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"numberOfDiscussionAttachments\": ");

		sb.append(discussionForumPosting.getNumberOfDiscussionAttachments());
		sb.append(", ");

		sb.append("\"numberOfDiscussionForumPostings\": ");

		sb.append(discussionForumPosting.getNumberOfDiscussionForumPostings());
		sb.append(", ");

		sb.append("\"showAsAnswer\": ");

		sb.append(discussionForumPosting.getShowAsAnswer());
		sb.append(", ");

		sb.append("\"taxonomyCategories\": ");

		if (discussionForumPosting.getTaxonomyCategories() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < discussionForumPosting.getTaxonomyCategories().length;
				 i++) {

				sb.append(discussionForumPosting.getTaxonomyCategories()[i]);

				if ((i + 1) <
						discussionForumPosting.getTaxonomyCategories().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"taxonomyCategoryIds\": ");

		if (discussionForumPosting.getTaxonomyCategoryIds() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < discussionForumPosting.getTaxonomyCategoryIds().length;
				 i++) {

				sb.append(discussionForumPosting.getTaxonomyCategoryIds()[i]);

				if ((i + 1) <
						discussionForumPosting.
							getTaxonomyCategoryIds().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"viewableBy\": ");

		sb.append("\"");
		sb.append(discussionForumPosting.getViewableBy());
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(
		Collection<DiscussionForumPosting> discussionForumPostings) {

		if (discussionForumPostings == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (DiscussionForumPosting discussionForumPosting :
				discussionForumPostings) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(discussionForumPosting));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class DiscussionForumPostingJSONParser
		extends BaseJSONParser<DiscussionForumPosting> {

		protected DiscussionForumPosting createDTO() {
			return new DiscussionForumPosting();
		}

		protected DiscussionForumPosting[] createDTOArray(int size) {
			return new DiscussionForumPosting[size];
		}

		protected void setField(
			DiscussionForumPosting discussionForumPosting,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "aggregateRating")) {
				if (jsonParserFieldValue != null) {
					discussionForumPosting.setAggregateRating(
						AggregateRatingSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "anonymous")) {
				if (jsonParserFieldValue != null) {
					discussionForumPosting.setAnonymous(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "articleBody")) {
				if (jsonParserFieldValue != null) {
					discussionForumPosting.setArticleBody(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contentSpaceId")) {
				if (jsonParserFieldValue != null) {
					discussionForumPosting.setContentSpaceId(
						(Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					discussionForumPosting.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					discussionForumPosting.setDateCreated(
						(Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					discussionForumPosting.setDateModified(
						(Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "headline")) {
				if (jsonParserFieldValue != null) {
					discussionForumPosting.setHeadline(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					discussionForumPosting.setId((Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywords")) {
				if (jsonParserFieldValue != null) {
					discussionForumPosting.setKeywords(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfDiscussionAttachments")) {

				if (jsonParserFieldValue != null) {
					discussionForumPosting.setNumberOfDiscussionAttachments(
						(Integer)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"numberOfDiscussionForumPostings")) {

				if (jsonParserFieldValue != null) {
					discussionForumPosting.setNumberOfDiscussionForumPostings(
						(Integer)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "showAsAnswer")) {
				if (jsonParserFieldValue != null) {
					discussionForumPosting.setShowAsAnswer(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategories")) {

				if (jsonParserFieldValue != null) {
					discussionForumPosting.setTaxonomyCategories(
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
					discussionForumPosting.setTaxonomyCategoryIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					discussionForumPosting.setViewableBy(
						DiscussionForumPosting.ViewableBy.create(
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