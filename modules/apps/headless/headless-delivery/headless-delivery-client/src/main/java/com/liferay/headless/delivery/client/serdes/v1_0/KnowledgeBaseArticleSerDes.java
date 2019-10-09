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

import com.liferay.headless.delivery.client.dto.v1_0.CustomField;
import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.delivery.client.dto.v1_0.RelatedContent;
import com.liferay.headless.delivery.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
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
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (knowledgeBaseArticle.getAggregateRating() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"aggregateRating\": ");

			sb.append(
				String.valueOf(knowledgeBaseArticle.getAggregateRating()));
		}

		if (knowledgeBaseArticle.getArticleBody() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"articleBody\": ");

			sb.append("\"");

			sb.append(_escape(knowledgeBaseArticle.getArticleBody()));

			sb.append("\"");
		}

		if (knowledgeBaseArticle.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\": ");

			sb.append(String.valueOf(knowledgeBaseArticle.getCreator()));
		}

		if (knowledgeBaseArticle.getCustomFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append("[");

			for (int i = 0; i < knowledgeBaseArticle.getCustomFields().length;
				 i++) {

				sb.append(
					String.valueOf(knowledgeBaseArticle.getCustomFields()[i]));

				if ((i + 1) < knowledgeBaseArticle.getCustomFields().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (knowledgeBaseArticle.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					knowledgeBaseArticle.getDateCreated()));

			sb.append("\"");
		}

		if (knowledgeBaseArticle.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					knowledgeBaseArticle.getDateModified()));

			sb.append("\"");
		}

		if (knowledgeBaseArticle.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(knowledgeBaseArticle.getDescription()));

			sb.append("\"");
		}

		if (knowledgeBaseArticle.getEncodingFormat() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"encodingFormat\": ");

			sb.append("\"");

			sb.append(_escape(knowledgeBaseArticle.getEncodingFormat()));

			sb.append("\"");
		}

		if (knowledgeBaseArticle.getFriendlyUrlPath() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"friendlyUrlPath\": ");

			sb.append("\"");

			sb.append(_escape(knowledgeBaseArticle.getFriendlyUrlPath()));

			sb.append("\"");
		}

		if (knowledgeBaseArticle.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(knowledgeBaseArticle.getId());
		}

		if (knowledgeBaseArticle.getKeywords() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"keywords\": ");

			sb.append("[");

			for (int i = 0; i < knowledgeBaseArticle.getKeywords().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(knowledgeBaseArticle.getKeywords()[i]));

				sb.append("\"");

				if ((i + 1) < knowledgeBaseArticle.getKeywords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (knowledgeBaseArticle.getNumberOfAttachments() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfAttachments\": ");

			sb.append(knowledgeBaseArticle.getNumberOfAttachments());
		}

		if (knowledgeBaseArticle.getNumberOfKnowledgeBaseArticles() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfKnowledgeBaseArticles\": ");

			sb.append(knowledgeBaseArticle.getNumberOfKnowledgeBaseArticles());
		}

		if (knowledgeBaseArticle.getParentKnowledgeBaseFolder() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parentKnowledgeBaseFolder\": ");

			sb.append(
				String.valueOf(
					knowledgeBaseArticle.getParentKnowledgeBaseFolder()));
		}

		if (knowledgeBaseArticle.getParentKnowledgeBaseFolderId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parentKnowledgeBaseFolderId\": ");

			sb.append(knowledgeBaseArticle.getParentKnowledgeBaseFolderId());
		}

		if (knowledgeBaseArticle.getRelatedContents() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"relatedContents\": ");

			sb.append("[");

			for (int i = 0;
				 i < knowledgeBaseArticle.getRelatedContents().length; i++) {

				sb.append(
					String.valueOf(
						knowledgeBaseArticle.getRelatedContents()[i]));

				if ((i + 1) <
						knowledgeBaseArticle.getRelatedContents().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (knowledgeBaseArticle.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\": ");

			sb.append(knowledgeBaseArticle.getSiteId());
		}

		if (knowledgeBaseArticle.getSubscribed() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subscribed\": ");

			sb.append(knowledgeBaseArticle.getSubscribed());
		}

		if (knowledgeBaseArticle.getTaxonomyCategories() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategories\": ");

			sb.append("[");

			for (int i = 0;
				 i < knowledgeBaseArticle.getTaxonomyCategories().length; i++) {

				sb.append(
					String.valueOf(
						knowledgeBaseArticle.getTaxonomyCategories()[i]));

				if ((i + 1) <
						knowledgeBaseArticle.getTaxonomyCategories().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (knowledgeBaseArticle.getTaxonomyCategoryIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryIds\": ");

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

		if (knowledgeBaseArticle.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(knowledgeBaseArticle.getTitle()));

			sb.append("\"");
		}

		if (knowledgeBaseArticle.getViewableBy() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"viewableBy\": ");

			sb.append("\"");

			sb.append(knowledgeBaseArticle.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		KnowledgeBaseArticleJSONParser knowledgeBaseArticleJSONParser =
			new KnowledgeBaseArticleJSONParser();

		return knowledgeBaseArticleJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		KnowledgeBaseArticle knowledgeBaseArticle) {

		if (knowledgeBaseArticle == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (knowledgeBaseArticle.getAggregateRating() == null) {
			map.put("aggregateRating", null);
		}
		else {
			map.put(
				"aggregateRating",
				String.valueOf(knowledgeBaseArticle.getAggregateRating()));
		}

		if (knowledgeBaseArticle.getArticleBody() == null) {
			map.put("articleBody", null);
		}
		else {
			map.put(
				"articleBody",
				String.valueOf(knowledgeBaseArticle.getArticleBody()));
		}

		if (knowledgeBaseArticle.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put(
				"creator", String.valueOf(knowledgeBaseArticle.getCreator()));
		}

		if (knowledgeBaseArticle.getCustomFields() == null) {
			map.put("customFields", null);
		}
		else {
			map.put(
				"customFields",
				String.valueOf(knowledgeBaseArticle.getCustomFields()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(
				knowledgeBaseArticle.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(
				knowledgeBaseArticle.getDateModified()));

		if (knowledgeBaseArticle.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(knowledgeBaseArticle.getDescription()));
		}

		if (knowledgeBaseArticle.getEncodingFormat() == null) {
			map.put("encodingFormat", null);
		}
		else {
			map.put(
				"encodingFormat",
				String.valueOf(knowledgeBaseArticle.getEncodingFormat()));
		}

		if (knowledgeBaseArticle.getFriendlyUrlPath() == null) {
			map.put("friendlyUrlPath", null);
		}
		else {
			map.put(
				"friendlyUrlPath",
				String.valueOf(knowledgeBaseArticle.getFriendlyUrlPath()));
		}

		if (knowledgeBaseArticle.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(knowledgeBaseArticle.getId()));
		}

		if (knowledgeBaseArticle.getKeywords() == null) {
			map.put("keywords", null);
		}
		else {
			map.put(
				"keywords", String.valueOf(knowledgeBaseArticle.getKeywords()));
		}

		if (knowledgeBaseArticle.getNumberOfAttachments() == null) {
			map.put("numberOfAttachments", null);
		}
		else {
			map.put(
				"numberOfAttachments",
				String.valueOf(knowledgeBaseArticle.getNumberOfAttachments()));
		}

		if (knowledgeBaseArticle.getNumberOfKnowledgeBaseArticles() == null) {
			map.put("numberOfKnowledgeBaseArticles", null);
		}
		else {
			map.put(
				"numberOfKnowledgeBaseArticles",
				String.valueOf(
					knowledgeBaseArticle.getNumberOfKnowledgeBaseArticles()));
		}

		if (knowledgeBaseArticle.getParentKnowledgeBaseFolder() == null) {
			map.put("parentKnowledgeBaseFolder", null);
		}
		else {
			map.put(
				"parentKnowledgeBaseFolder",
				String.valueOf(
					knowledgeBaseArticle.getParentKnowledgeBaseFolder()));
		}

		if (knowledgeBaseArticle.getParentKnowledgeBaseFolderId() == null) {
			map.put("parentKnowledgeBaseFolderId", null);
		}
		else {
			map.put(
				"parentKnowledgeBaseFolderId",
				String.valueOf(
					knowledgeBaseArticle.getParentKnowledgeBaseFolderId()));
		}

		if (knowledgeBaseArticle.getRelatedContents() == null) {
			map.put("relatedContents", null);
		}
		else {
			map.put(
				"relatedContents",
				String.valueOf(knowledgeBaseArticle.getRelatedContents()));
		}

		if (knowledgeBaseArticle.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(knowledgeBaseArticle.getSiteId()));
		}

		if (knowledgeBaseArticle.getSubscribed() == null) {
			map.put("subscribed", null);
		}
		else {
			map.put(
				"subscribed",
				String.valueOf(knowledgeBaseArticle.getSubscribed()));
		}

		if (knowledgeBaseArticle.getTaxonomyCategories() == null) {
			map.put("taxonomyCategories", null);
		}
		else {
			map.put(
				"taxonomyCategories",
				String.valueOf(knowledgeBaseArticle.getTaxonomyCategories()));
		}

		if (knowledgeBaseArticle.getTaxonomyCategoryIds() == null) {
			map.put("taxonomyCategoryIds", null);
		}
		else {
			map.put(
				"taxonomyCategoryIds",
				String.valueOf(knowledgeBaseArticle.getTaxonomyCategoryIds()));
		}

		if (knowledgeBaseArticle.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(knowledgeBaseArticle.getTitle()));
		}

		if (knowledgeBaseArticle.getViewableBy() == null) {
			map.put("viewableBy", null);
		}
		else {
			map.put(
				"viewableBy",
				String.valueOf(knowledgeBaseArticle.getViewableBy()));
		}

		return map;
	}

	public static class KnowledgeBaseArticleJSONParser
		extends BaseJSONParser<KnowledgeBaseArticle> {

		@Override
		protected KnowledgeBaseArticle createDTO() {
			return new KnowledgeBaseArticle();
		}

		@Override
		protected KnowledgeBaseArticle[] createDTOArray(int size) {
			return new KnowledgeBaseArticle[size];
		}

		@Override
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
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "customFields")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setCustomFields(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> CustomFieldSerDes.toDTO((String)object)
						).toArray(
							size -> new CustomField[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "encodingFormat")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setEncodingFormat(
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
					knowledgeBaseArticle.setId(
						Long.valueOf((String)jsonParserFieldValue));
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
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfKnowledgeBaseArticles")) {

				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setNumberOfKnowledgeBaseArticles(
						Integer.valueOf((String)jsonParserFieldValue));
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
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "relatedContents")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setRelatedContents(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> RelatedContentSerDes.toDTO((String)object)
						).toArray(
							size -> new RelatedContent[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "subscribed")) {
				if (jsonParserFieldValue != null) {
					knowledgeBaseArticle.setSubscribed(
						(Boolean)jsonParserFieldValue);
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

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		string = string.replace("\\", "\\\\");

		return string.replace("\"", "\\\"");
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}