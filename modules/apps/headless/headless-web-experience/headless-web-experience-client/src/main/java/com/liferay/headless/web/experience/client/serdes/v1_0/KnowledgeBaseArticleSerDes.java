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

package com.liferay.headless.web.experience.client.serdes.v1_0;

import com.liferay.headless.web.experience.client.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.web.experience.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.web.experience.client.json.BaseJSONParser;

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
public class KnowledgeBaseArticleSerDes {

	public static KnowledgeBaseArticle toDTO(String json) {
		KnowledgeBaseArticleJSONParser knowledgeBaseArticleJSONParser =
			new KnowledgeBaseArticleJSONParser();

		return knowledgeBaseArticleJSONParser.parseToDTO(json);
	}

	public static KnowledgeBaseArticle[] toDTOs(String json) {
		KnowledgeBaseArticleJSONParser knowledgeBaseArticleJSONParser =
			new KnowledgeBaseArticleJSONParser();

		return knowledgeBaseArticleJSONParser.parseToDTOs(json);
	}

	public static String toJSON(KnowledgeBaseArticle knowledgeBaseArticle) {
		if (knowledgeBaseArticle == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"aggregateRating\": ");

		sb.append(knowledgeBaseArticle.getAggregateRating());
		sb.append(", ");

		sb.append("\"articleBody\": ");

		sb.append("\"");
		sb.append(knowledgeBaseArticle.getArticleBody());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"contentSpaceId\": ");

		sb.append(knowledgeBaseArticle.getContentSpaceId());
		sb.append(", ");

		sb.append("\"creator\": ");

		sb.append(knowledgeBaseArticle.getCreator());
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		sb.append("\"");
		sb.append(knowledgeBaseArticle.getDateCreated());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dateModified\": ");

		sb.append("\"");
		sb.append(knowledgeBaseArticle.getDateModified());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"description\": ");

		sb.append("\"");
		sb.append(knowledgeBaseArticle.getDescription());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"friendlyUrlPath\": ");

		sb.append("\"");
		sb.append(knowledgeBaseArticle.getFriendlyUrlPath());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(knowledgeBaseArticle.getId());
		sb.append(", ");

		sb.append("\"keywords\": ");

		if (knowledgeBaseArticle.getKeywords() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < knowledgeBaseArticle.getKeywords().length;
				 i++) {

				sb.append("\"");
				sb.append(knowledgeBaseArticle.getKeywords()[i]);
				sb.append("\"");

				if ((i + 1) < knowledgeBaseArticle.getKeywords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"numberOfAttachments\": ");

		sb.append(knowledgeBaseArticle.getNumberOfAttachments());
		sb.append(", ");

		sb.append("\"numberOfKnowledgeBaseArticles\": ");

		sb.append(knowledgeBaseArticle.getNumberOfKnowledgeBaseArticles());
		sb.append(", ");

		sb.append("\"parentKnowledgeBaseFolder\": ");

		sb.append(knowledgeBaseArticle.getParentKnowledgeBaseFolder());
		sb.append(", ");

		sb.append("\"parentKnowledgeBaseFolderId\": ");

		sb.append(knowledgeBaseArticle.getParentKnowledgeBaseFolderId());
		sb.append(", ");

		sb.append("\"taxonomyCategories\": ");

		if (knowledgeBaseArticle.getTaxonomyCategories() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < knowledgeBaseArticle.getTaxonomyCategories().length; i++) {

				sb.append(knowledgeBaseArticle.getTaxonomyCategories()[i]);

				if ((i + 1) <
						knowledgeBaseArticle.getTaxonomyCategories().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"taxonomyCategoryIds\": ");

		if (knowledgeBaseArticle.getTaxonomyCategoryIds() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < knowledgeBaseArticle.getTaxonomyCategoryIds().length;
				 i++) {

				sb.append(knowledgeBaseArticle.getTaxonomyCategoryIds()[i]);

				if ((i + 1) <
						knowledgeBaseArticle.getTaxonomyCategoryIds().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"title\": ");

		sb.append("\"");
		sb.append(knowledgeBaseArticle.getTitle());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"viewableBy\": ");

		sb.append("\"");
		sb.append(knowledgeBaseArticle.getViewableBy());
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(
		Collection<KnowledgeBaseArticle> knowledgeBaseArticles) {

		if (knowledgeBaseArticles == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (KnowledgeBaseArticle knowledgeBaseArticle :
				knowledgeBaseArticles) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(knowledgeBaseArticle));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class KnowledgeBaseArticleJSONParser
		extends BaseJSONParser<KnowledgeBaseArticle> {

		protected KnowledgeBaseArticle createDTO() {
			return new KnowledgeBaseArticle();
		}

		protected KnowledgeBaseArticle[] createDTOArray(int size) {
			return new KnowledgeBaseArticle[size];
		}

		protected void setField(
			KnowledgeBaseArticle knowledgeBaseArticle,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "aggregateRating")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setAggregateRating(
						AggregateRatingSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "articleBody")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setArticleBody(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contentSpaceId")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setContentSpaceId(
						(Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setDateCreated(
						(Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setDateModified(
						(Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "friendlyUrlPath")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setFriendlyUrlPath(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setId((Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywords")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setKeywords(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfAttachments")) {

				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setNumberOfAttachments(
						(Number)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfKnowledgeBaseArticles")) {

				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setNumberOfKnowledgeBaseArticles(
						(Number)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "parentKnowledgeBaseFolder")) {

				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setParentKnowledgeBaseFolder(
						ParentKnowledgeBaseFolderSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "parentKnowledgeBaseFolderId")) {

				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setParentKnowledgeBaseFolderId(
						(Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategories")) {

				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setTaxonomyCategories(
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
					knowledgeBaseArticle.setTaxonomyCategoryIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setViewableBy(
						KnowledgeBaseArticle.ViewableBy.create(
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