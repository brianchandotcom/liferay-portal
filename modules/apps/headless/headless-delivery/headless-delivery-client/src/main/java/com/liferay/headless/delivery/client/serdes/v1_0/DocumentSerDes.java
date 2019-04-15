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

import com.liferay.headless.delivery.client.dto.v1_0.AdaptedImage;
import com.liferay.headless.delivery.client.dto.v1_0.Document;
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
public class DocumentSerDes {

	public static Document toDTO(String json) {
		DocumentJSONParser documentJSONParser = new DocumentJSONParser();

		return documentJSONParser.parseToDTO(json);
	}

	public static Document[] toDTOs(String json) {
		DocumentJSONParser documentJSONParser = new DocumentJSONParser();

		return documentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Document document) {
		if (document == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		sb.append("\"adaptedImages\": ");

		if (document.getAdaptedImages() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < document.getAdaptedImages().length; i++) {
				sb.append(
					AdaptedImageSerDes.toJSON(document.getAdaptedImages()[i]));

				if ((i + 1) < document.getAdaptedImages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"aggregateRating\": ");

		sb.append(AggregateRatingSerDes.toJSON(document.getAggregateRating()));
		sb.append(", ");

		sb.append("\"contentUrl\": ");

		if (document.getContentUrl() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(document.getContentUrl());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"creator\": ");

		sb.append(CreatorSerDes.toJSON(document.getCreator()));
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (document.getDateCreated() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(document.getDateCreated()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (document.getDateModified() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(document.getDateModified()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"description\": ");

		if (document.getDescription() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(document.getDescription());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"documentFolderId\": ");

		if (document.getDocumentFolderId() == null) {
			sb.append("null");
		}
		else {
			sb.append(document.getDocumentFolderId());
		}

		sb.append(", ");

		sb.append("\"encodingFormat\": ");

		if (document.getEncodingFormat() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(document.getEncodingFormat());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"fileExtension\": ");

		if (document.getFileExtension() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(document.getFileExtension());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (document.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(document.getId());
		}

		sb.append(", ");

		sb.append("\"keywords\": ");

		if (document.getKeywords() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < document.getKeywords().length; i++) {
				sb.append("\"");

				sb.append(document.getKeywords()[i]);

				sb.append("\"");

				if ((i + 1) < document.getKeywords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"numberOfComments\": ");

		if (document.getNumberOfComments() == null) {
			sb.append("null");
		}
		else {
			sb.append(document.getNumberOfComments());
		}

		sb.append(", ");

		sb.append("\"sizeInBytes\": ");

		if (document.getSizeInBytes() == null) {
			sb.append("null");
		}
		else {
			sb.append(document.getSizeInBytes());
		}

		sb.append(", ");

		sb.append("\"taxonomyCategories\": ");

		if (document.getTaxonomyCategories() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < document.getTaxonomyCategories().length; i++) {
				sb.append(
					TaxonomyCategorySerDes.toJSON(
						document.getTaxonomyCategories()[i]));

				if ((i + 1) < document.getTaxonomyCategories().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"taxonomyCategoryIds\": ");

		if (document.getTaxonomyCategoryIds() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < document.getTaxonomyCategoryIds().length; i++) {
				sb.append(document.getTaxonomyCategoryIds()[i]);

				if ((i + 1) < document.getTaxonomyCategoryIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"title\": ");

		if (document.getTitle() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(document.getTitle());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"viewableBy\": ");

		if (document.getViewableBy() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(document.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	private static class DocumentJSONParser extends BaseJSONParser<Document> {

		protected Document createDTO() {
			return new Document();
		}

		protected Document[] createDTOArray(int size) {
			return new Document[size];
		}

		protected void setField(
			Document document, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "adaptedImages")) {
				if (jsonParserFieldValue != null) {
					document.setAdaptedImages(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> AdaptedImageSerDes.toDTO((String)object)
						).toArray(
							size -> new AdaptedImage[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "aggregateRating")) {
				if (jsonParserFieldValue != null) {
					document.setAggregateRating(
						AggregateRatingSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contentUrl")) {
				if (jsonParserFieldValue != null) {
					document.setContentUrl((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					document.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					document.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					document.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					document.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "documentFolderId")) {
				if (jsonParserFieldValue != null) {
					document.setDocumentFolderId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "encodingFormat")) {
				if (jsonParserFieldValue != null) {
					document.setEncodingFormat((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fileExtension")) {
				if (jsonParserFieldValue != null) {
					document.setFileExtension((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					document.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywords")) {
				if (jsonParserFieldValue != null) {
					document.setKeywords(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numberOfComments")) {
				if (jsonParserFieldValue != null) {
					document.setNumberOfComments(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sizeInBytes")) {
				if (jsonParserFieldValue != null) {
					document.setSizeInBytes(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategories")) {

				if (jsonParserFieldValue != null) {
					document.setTaxonomyCategories(
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
					document.setTaxonomyCategoryIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					document.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					document.setViewableBy(
						Document.ViewableBy.create(
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