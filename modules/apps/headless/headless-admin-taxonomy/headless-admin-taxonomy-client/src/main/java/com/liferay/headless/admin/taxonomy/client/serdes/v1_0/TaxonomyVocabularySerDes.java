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

package com.liferay.headless.admin.taxonomy.client.serdes.v1_0;

import com.liferay.headless.admin.taxonomy.client.dto.v1_0.AssetType;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.client.json.BaseJSONParser;

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
public class TaxonomyVocabularySerDes {

	public static TaxonomyVocabulary toDTO(String json) {
		TaxonomyVocabularyJSONParser taxonomyVocabularyJSONParser =
			new TaxonomyVocabularyJSONParser();

		return taxonomyVocabularyJSONParser.parseToDTO(json);
	}

	public static TaxonomyVocabulary[] toDTOs(String json) {
		TaxonomyVocabularyJSONParser taxonomyVocabularyJSONParser =
			new TaxonomyVocabularyJSONParser();

		return taxonomyVocabularyJSONParser.parseToDTOs(json);
	}

	public static String toJSON(TaxonomyVocabulary taxonomyVocabulary) {
		if (taxonomyVocabulary == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (taxonomyVocabulary.getAssetTypes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"assetTypes\":");

			sb.append("[");

			for (int i = 0; i < taxonomyVocabulary.getAssetTypes().length;
				 i++) {

				sb.append(
					AssetTypeSerDes.toJSON(
						taxonomyVocabulary.getAssetTypes()[i]));

				if ((i + 1) < taxonomyVocabulary.getAssetTypes().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (taxonomyVocabulary.getAvailableLanguages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"availableLanguages\":");

			sb.append("[");

			for (int i = 0;
				 i < taxonomyVocabulary.getAvailableLanguages().length; i++) {

				sb.append("\"");

				sb.append(
					_escape(taxonomyVocabulary.getAvailableLanguages()[i]));

				sb.append("\"");

				if ((i + 1) <
						taxonomyVocabulary.getAvailableLanguages().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (taxonomyVocabulary.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\":");

			sb.append(CreatorSerDes.toJSON(taxonomyVocabulary.getCreator()));
		}

		if (taxonomyVocabulary.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\":");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					taxonomyVocabulary.getDateCreated()));

			sb.append("\"");
		}

		if (taxonomyVocabulary.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\":");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					taxonomyVocabulary.getDateModified()));

			sb.append("\"");
		}

		if (taxonomyVocabulary.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\":");

			sb.append("\"");

			sb.append(_escape(taxonomyVocabulary.getDescription()));

			sb.append("\"");
		}

		if (taxonomyVocabulary.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\":");

			sb.append(taxonomyVocabulary.getId());
		}

		if (taxonomyVocabulary.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\":");

			sb.append("\"");

			sb.append(_escape(taxonomyVocabulary.getName()));

			sb.append("\"");
		}

		if (taxonomyVocabulary.getNumberOfTaxonomyCategories() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfTaxonomyCategories\":");

			sb.append(taxonomyVocabulary.getNumberOfTaxonomyCategories());
		}

		if (taxonomyVocabulary.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\":");

			sb.append(taxonomyVocabulary.getSiteId());
		}

		if (taxonomyVocabulary.getViewableBy() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"viewableBy\":");

			sb.append("\"");

			sb.append(taxonomyVocabulary.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(
		TaxonomyVocabulary taxonomyVocabulary) {

		if (taxonomyVocabulary == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (taxonomyVocabulary.getAssetTypes() == null) {
			map.put("assetTypes", null);
		}
		else {
			map.put(
				"assetTypes",
				String.valueOf(taxonomyVocabulary.getAssetTypes()));
		}

		if (taxonomyVocabulary.getAvailableLanguages() == null) {
			map.put("availableLanguages", null);
		}
		else {
			map.put(
				"availableLanguages",
				String.valueOf(taxonomyVocabulary.getAvailableLanguages()));
		}

		if (taxonomyVocabulary.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put(
				"creator",
				CreatorSerDes.toJSON(taxonomyVocabulary.getCreator()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(
				taxonomyVocabulary.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(
				taxonomyVocabulary.getDateModified()));

		if (taxonomyVocabulary.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(taxonomyVocabulary.getDescription()));
		}

		if (taxonomyVocabulary.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(taxonomyVocabulary.getId()));
		}

		if (taxonomyVocabulary.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(taxonomyVocabulary.getName()));
		}

		if (taxonomyVocabulary.getNumberOfTaxonomyCategories() == null) {
			map.put("numberOfTaxonomyCategories", null);
		}
		else {
			map.put(
				"numberOfTaxonomyCategories",
				String.valueOf(
					taxonomyVocabulary.getNumberOfTaxonomyCategories()));
		}

		if (taxonomyVocabulary.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(taxonomyVocabulary.getSiteId()));
		}

		if (taxonomyVocabulary.getViewableBy() == null) {
			map.put("viewableBy", null);
		}
		else {
			map.put(
				"viewableBy",
				String.valueOf(taxonomyVocabulary.getViewableBy()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class TaxonomyVocabularyJSONParser
		extends BaseJSONParser<TaxonomyVocabulary> {

		@Override
		protected TaxonomyVocabulary createDTO() {
			return new TaxonomyVocabulary();
		}

		@Override
		protected TaxonomyVocabulary[] createDTOArray(int size) {
			return new TaxonomyVocabulary[size];
		}

		@Override
		protected void setField(
			TaxonomyVocabulary taxonomyVocabulary, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "assetTypes")) {
				if (jsonParserFieldValue != null) {
					taxonomyVocabulary.setAssetTypes(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> AssetTypeSerDes.toDTO((String)object)
						).toArray(
							size -> new AssetType[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "availableLanguages")) {

				if (jsonParserFieldValue != null) {
					taxonomyVocabulary.setAvailableLanguages(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					taxonomyVocabulary.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					taxonomyVocabulary.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					taxonomyVocabulary.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					taxonomyVocabulary.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					taxonomyVocabulary.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					taxonomyVocabulary.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfTaxonomyCategories")) {

				if (jsonParserFieldValue != null) {
					taxonomyVocabulary.setNumberOfTaxonomyCategories(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					taxonomyVocabulary.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					taxonomyVocabulary.setViewableBy(
						TaxonomyVocabulary.ViewableBy.create(
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