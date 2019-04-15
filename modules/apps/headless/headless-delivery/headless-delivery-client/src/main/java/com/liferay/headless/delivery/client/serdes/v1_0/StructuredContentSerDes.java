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

import com.liferay.headless.delivery.client.dto.v1_0.ContentField;
import com.liferay.headless.delivery.client.dto.v1_0.RenderedContent;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class StructuredContentSerDes {

	public static StructuredContent toDTO(String json) {
		StructuredContentJSONParser structuredContentJSONParser =
			new StructuredContentJSONParser();

		return structuredContentJSONParser.parseToDTO(json);
	}

	public static StructuredContent[] toDTOs(String json) {
		StructuredContentJSONParser structuredContentJSONParser =
			new StructuredContentJSONParser();

		return structuredContentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(StructuredContent structuredContent) {
		if (structuredContent == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		sb.append("\"aggregateRating\": ");

		sb.append(
			AggregateRatingSerDes.toJSON(
				structuredContent.getAggregateRating()));
		sb.append(", ");

		sb.append("\"availableLanguages\": ");

		if (structuredContent.getAvailableLanguages() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < structuredContent.getAvailableLanguages().length; i++) {

				sb.append("\"");

				sb.append(structuredContent.getAvailableLanguages()[i]);

				sb.append("\"");

				if ((i + 1) <
						structuredContent.getAvailableLanguages().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"contentFields\": ");

		if (structuredContent.getContentFields() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < structuredContent.getContentFields().length;
				 i++) {

				sb.append(
					ContentFieldSerDes.toJSON(
						structuredContent.getContentFields()[i]));

				if ((i + 1) < structuredContent.getContentFields().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"contentStructureId\": ");

		if (structuredContent.getContentStructureId() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContent.getContentStructureId());
		}

		sb.append(", ");

		sb.append("\"creator\": ");

		sb.append(CreatorSerDes.toJSON(structuredContent.getCreator()));
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (structuredContent.getDateCreated() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					structuredContent.getDateCreated()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (structuredContent.getDateModified() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					structuredContent.getDateModified()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"datePublished\": ");

		if (structuredContent.getDatePublished() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					structuredContent.getDatePublished()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"description\": ");

		if (structuredContent.getDescription() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(structuredContent.getDescription());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"friendlyUrlPath\": ");

		if (structuredContent.getFriendlyUrlPath() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(structuredContent.getFriendlyUrlPath());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (structuredContent.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContent.getId());
		}

		sb.append(", ");

		sb.append("\"key\": ");

		if (structuredContent.getKey() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(structuredContent.getKey());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"keywords\": ");

		if (structuredContent.getKeywords() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < structuredContent.getKeywords().length; i++) {
				sb.append("\"");

				sb.append(structuredContent.getKeywords()[i]);

				sb.append("\"");

				if ((i + 1) < structuredContent.getKeywords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"numberOfComments\": ");

		if (structuredContent.getNumberOfComments() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContent.getNumberOfComments());
		}

		sb.append(", ");

		sb.append("\"renderedContents\": ");

		if (structuredContent.getRenderedContents() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < structuredContent.getRenderedContents().length;
				 i++) {

				sb.append(
					RenderedContentSerDes.toJSON(
						structuredContent.getRenderedContents()[i]));

				if ((i + 1) < structuredContent.getRenderedContents().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"siteId\": ");

		if (structuredContent.getSiteId() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContent.getSiteId());
		}

		sb.append(", ");

		sb.append("\"taxonomyCategories\": ");

		if (structuredContent.getTaxonomyCategories() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < structuredContent.getTaxonomyCategories().length; i++) {

				sb.append(
					TaxonomyCategorySerDes.toJSON(
						structuredContent.getTaxonomyCategories()[i]));

				if ((i + 1) <
						structuredContent.getTaxonomyCategories().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"taxonomyCategoryIds\": ");

		if (structuredContent.getTaxonomyCategoryIds() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < structuredContent.getTaxonomyCategoryIds().length; i++) {

				sb.append(structuredContent.getTaxonomyCategoryIds()[i]);

				if ((i + 1) <
						structuredContent.getTaxonomyCategoryIds().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"title\": ");

		if (structuredContent.getTitle() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(structuredContent.getTitle());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"uuid\": ");

		if (structuredContent.getUuid() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(structuredContent.getUuid());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"viewableBy\": ");

		if (structuredContent.getViewableBy() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(structuredContent.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	private static class StructuredContentJSONParser
		extends BaseJSONParser<StructuredContent> {

		protected StructuredContent createDTO() {
			return new StructuredContent();
		}

		protected StructuredContent[] createDTOArray(int size) {
			return new StructuredContent[size];
		}

		protected void setField(
			StructuredContent structuredContent, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "aggregateRating")) {
				if (jsonParserFieldValue != null) {
					structuredContent.setAggregateRating(
						AggregateRatingSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "availableLanguages")) {

				if (jsonParserFieldValue != null) {
					structuredContent.setAvailableLanguages(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contentFields")) {
				if (jsonParserFieldValue != null) {
					structuredContent.setContentFields(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ContentFieldSerDes.toDTO((String)object)
						).toArray(
							size -> new ContentField[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "contentStructureId")) {

				if (jsonParserFieldValue != null) {
					structuredContent.setContentStructureId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					structuredContent.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					structuredContent.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					structuredContent.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "datePublished")) {
				if (jsonParserFieldValue != null) {
					structuredContent.setDatePublished(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					structuredContent.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "friendlyUrlPath")) {
				if (jsonParserFieldValue != null) {
					structuredContent.setFriendlyUrlPath(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					structuredContent.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					structuredContent.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywords")) {
				if (jsonParserFieldValue != null) {
					structuredContent.setKeywords(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfComments")) {
				if (jsonParserFieldValue != null) {
					structuredContent.setNumberOfComments(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "renderedContents")) {
				if (jsonParserFieldValue != null) {
					structuredContent.setRenderedContents(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> RenderedContentSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new RenderedContent[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					structuredContent.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategories")) {

				if (jsonParserFieldValue != null) {
					structuredContent.setTaxonomyCategories(
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
					structuredContent.setTaxonomyCategoryIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					structuredContent.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "uuid")) {
				if (jsonParserFieldValue != null) {
					structuredContent.setUuid((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					structuredContent.setViewableBy(
						StructuredContent.ViewableBy.create(
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