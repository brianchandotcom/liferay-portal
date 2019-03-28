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

import com.liferay.headless.collaboration.client.dto.v1_0.DiscussionThread;
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
public class DiscussionThreadSerDes {

	public static DiscussionThread toDTO(String json) {
		DiscussionThreadJSONParser discussionThreadJSONParser =
			new DiscussionThreadJSONParser();

		return discussionThreadJSONParser.parseToDTO(json);
	}

	public static DiscussionThread[] toDTOs(String json) {
		DiscussionThreadJSONParser discussionThreadJSONParser =
			new DiscussionThreadJSONParser();

		return discussionThreadJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DiscussionThread discussionThread) {
		if (discussionThread == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"aggregateRating\": ");

		sb.append(discussionThread.getAggregateRating());
		sb.append(", ");

		sb.append("\"articleBody\": ");

		sb.append("\"");
		sb.append(discussionThread.getArticleBody());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"contentSpaceId\": ");

		sb.append(discussionThread.getContentSpaceId());
		sb.append(", ");

		sb.append("\"creator\": ");

		sb.append(discussionThread.getCreator());
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		sb.append("\"");
		sb.append(discussionThread.getDateCreated());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dateModified\": ");

		sb.append("\"");
		sb.append(discussionThread.getDateModified());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"headline\": ");

		sb.append("\"");
		sb.append(discussionThread.getHeadline());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(discussionThread.getId());
		sb.append(", ");

		sb.append("\"keywords\": ");

		if (discussionThread.getKeywords() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < discussionThread.getKeywords().length; i++) {
				sb.append("\"");
				sb.append(discussionThread.getKeywords()[i]);
				sb.append("\"");

				if ((i + 1) < discussionThread.getKeywords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"numberOfDiscussionAttachments\": ");

		sb.append(discussionThread.getNumberOfDiscussionAttachments());
		sb.append(", ");

		sb.append("\"numberOfDiscussionForumPostings\": ");

		sb.append(discussionThread.getNumberOfDiscussionForumPostings());
		sb.append(", ");

		sb.append("\"showAsQuestion\": ");

		sb.append(discussionThread.getShowAsQuestion());
		sb.append(", ");

		sb.append("\"taxonomyCategories\": ");

		if (discussionThread.getTaxonomyCategories() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < discussionThread.getTaxonomyCategories().length;
				 i++) {

				sb.append(discussionThread.getTaxonomyCategories()[i]);

				if ((i + 1) < discussionThread.getTaxonomyCategories().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"taxonomyCategoryIds\": ");

		if (discussionThread.getTaxonomyCategoryIds() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < discussionThread.getTaxonomyCategoryIds().length; i++) {

				sb.append(discussionThread.getTaxonomyCategoryIds()[i]);

				if ((i + 1) <
						discussionThread.getTaxonomyCategoryIds().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"threadType\": ");

		sb.append("\"");
		sb.append(discussionThread.getThreadType());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"viewableBy\": ");

		sb.append("\"");
		sb.append(discussionThread.getViewableBy());
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(
		Collection<DiscussionThread> discussionThreads) {

		if (discussionThreads == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (DiscussionThread discussionThread : discussionThreads) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(discussionThread));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class DiscussionThreadJSONParser
		extends BaseJSONParser<DiscussionThread> {

		protected DiscussionThread createDTO() {
			return new DiscussionThread();
		}

		protected DiscussionThread[] createDTOArray(int size) {
			return new DiscussionThread[size];
		}

		protected void setField(
			DiscussionThread discussionThread, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "aggregateRating")) {
				if (jsonParserFieldValue != null) {
					discussionThread.setAggregateRating(
						AggregateRatingSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "articleBody")) {
				if (jsonParserFieldValue != null) {
					discussionThread.setArticleBody(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contentSpaceId")) {
				if (jsonParserFieldValue != null) {
					discussionThread.setContentSpaceId(
						(Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					discussionThread.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					discussionThread.setDateCreated((Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					discussionThread.setDateModified(
						(Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "headline")) {
				if (jsonParserFieldValue != null) {
					discussionThread.setHeadline((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					discussionThread.setId((Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywords")) {
				if (jsonParserFieldValue != null) {
					discussionThread.setKeywords(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfDiscussionAttachments")) {

				if (jsonParserFieldValue != null) {
					discussionThread.setNumberOfDiscussionAttachments(
						(Integer)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"numberOfDiscussionForumPostings")) {

				if (jsonParserFieldValue != null) {
					discussionThread.setNumberOfDiscussionForumPostings(
						(Integer)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "showAsQuestion")) {
				if (jsonParserFieldValue != null) {
					discussionThread.setShowAsQuestion(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategories")) {

				if (jsonParserFieldValue != null) {
					discussionThread.setTaxonomyCategories(
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
					discussionThread.setTaxonomyCategoryIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "threadType")) {
				if (jsonParserFieldValue != null) {
					discussionThread.setThreadType(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					discussionThread.setViewableBy(
						DiscussionThread.ViewableBy.create(
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