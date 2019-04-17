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

import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class TaxonomyCategorySerDes {

	public static TaxonomyCategory toDTO(String json) {
		TaxonomyCategoryJSONParser taxonomyCategoryJSONParser =
			new TaxonomyCategoryJSONParser();

		return taxonomyCategoryJSONParser.parseToDTO(json);
	}

	public static TaxonomyCategory[] toDTOs(String json) {
		TaxonomyCategoryJSONParser taxonomyCategoryJSONParser =
			new TaxonomyCategoryJSONParser();

		return taxonomyCategoryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(TaxonomyCategory taxonomyCategory) {
		if (taxonomyCategory == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (taxonomyCategory.getAvailableLanguages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"availableLanguages\":");

			sb.append("[");

			for (int i = 0; i < taxonomyCategory.getAvailableLanguages().length;
				 i++) {

				sb.append("\"");

				sb.append(taxonomyCategory.getAvailableLanguages()[i]);

				sb.append("\"");

				if ((i + 1) < taxonomyCategory.getAvailableLanguages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (taxonomyCategory.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\":");

			sb.append(CreatorSerDes.toJSON(taxonomyCategory.getCreator()));
		}

		if (taxonomyCategory.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\":");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					taxonomyCategory.getDateCreated()));

			sb.append("\"");
		}

		if (taxonomyCategory.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\":");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					taxonomyCategory.getDateModified()));

			sb.append("\"");
		}

		if (taxonomyCategory.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\":");

			sb.append("\"");

			sb.append(taxonomyCategory.getDescription());

			sb.append("\"");
		}

		if (taxonomyCategory.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\":");

			sb.append(taxonomyCategory.getId());
		}

		if (taxonomyCategory.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\":");

			sb.append("\"");

			sb.append(taxonomyCategory.getName());

			sb.append("\"");
		}

		if (taxonomyCategory.getNumberOfTaxonomyCategories() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfTaxonomyCategories\":");

			sb.append(taxonomyCategory.getNumberOfTaxonomyCategories());
		}

		if (taxonomyCategory.getParentTaxonomyCategory() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parentTaxonomyCategory\":");

			sb.append(
				ParentTaxonomyCategorySerDes.toJSON(
					taxonomyCategory.getParentTaxonomyCategory()));
		}

		if (taxonomyCategory.getParentTaxonomyVocabulary() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parentTaxonomyVocabulary\":");

			sb.append(
				ParentTaxonomyVocabularySerDes.toJSON(
					taxonomyCategory.getParentTaxonomyVocabulary()));
		}

		if (taxonomyCategory.getViewableBy() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"viewableBy\":");

			sb.append("\"");

			sb.append(taxonomyCategory.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(TaxonomyCategory taxonomyCategory) {
		if (taxonomyCategory == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (taxonomyCategory.getAvailableLanguages() == null) {
			map.put("availableLanguages", null);
		}
		else {
			map.put(
				"availableLanguages",
				String.valueOf(taxonomyCategory.getAvailableLanguages()));
		}

		if (taxonomyCategory.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put(
				"creator", CreatorSerDes.toJSON(taxonomyCategory.getCreator()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(taxonomyCategory.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(taxonomyCategory.getDateModified()));

		if (taxonomyCategory.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(taxonomyCategory.getDescription()));
		}

		if (taxonomyCategory.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(taxonomyCategory.getId()));
		}

		if (taxonomyCategory.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(taxonomyCategory.getName()));
		}

		if (taxonomyCategory.getNumberOfTaxonomyCategories() == null) {
			map.put("numberOfTaxonomyCategories", null);
		}
		else {
			map.put(
				"numberOfTaxonomyCategories",
				String.valueOf(
					taxonomyCategory.getNumberOfTaxonomyCategories()));
		}

		if (taxonomyCategory.getParentTaxonomyCategory() == null) {
			map.put("parentTaxonomyCategory", null);
		}
		else {
			map.put(
				"parentTaxonomyCategory",
				ParentTaxonomyCategorySerDes.toJSON(
					taxonomyCategory.getParentTaxonomyCategory()));
		}

		if (taxonomyCategory.getParentTaxonomyVocabulary() == null) {
			map.put("parentTaxonomyVocabulary", null);
		}
		else {
			map.put(
				"parentTaxonomyVocabulary",
				ParentTaxonomyVocabularySerDes.toJSON(
					taxonomyCategory.getParentTaxonomyVocabulary()));
		}

		if (taxonomyCategory.getViewableBy() == null) {
			map.put("viewableBy", null);
		}
		else {
			map.put(
				"viewableBy", String.valueOf(taxonomyCategory.getViewableBy()));
		}

		return map;
	}

	private static class TaxonomyCategoryJSONParser
		extends BaseJSONParser<TaxonomyCategory> {

		@Override
		protected TaxonomyCategory createDTO() {
			return new TaxonomyCategory();
		}

		@Override
		protected TaxonomyCategory[] createDTOArray(int size) {
			return new TaxonomyCategory[size];
		}

		@Override
		protected void setField(
			TaxonomyCategory taxonomyCategory, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "availableLanguages")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setAvailableLanguages(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "numberOfTaxonomyCategories")) {

				if (jsonParserFieldValue != null) {
					taxonomyCategory.setNumberOfTaxonomyCategories(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "parentTaxonomyCategory")) {

				if (jsonParserFieldValue != null) {
					taxonomyCategory.setParentTaxonomyCategory(
						ParentTaxonomyCategorySerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "parentTaxonomyVocabulary")) {

				if (jsonParserFieldValue != null) {
					taxonomyCategory.setParentTaxonomyVocabulary(
						ParentTaxonomyVocabularySerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setViewableBy(
						TaxonomyCategory.ViewableBy.create(
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