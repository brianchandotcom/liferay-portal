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
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyCategoryProperty;
import com.liferay.headless.admin.taxonomy.client.json.BaseJSONParser;

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
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (taxonomyCategory.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(taxonomyCategory.getActions()));
		}

		if (taxonomyCategory.getAvailableLanguages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"availableLanguages\": ");

			sb.append("[");

			for (int i = 0; i < taxonomyCategory.getAvailableLanguages().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(taxonomyCategory.getAvailableLanguages()[i]));

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

			sb.append("\"creator\": ");

			sb.append(String.valueOf(taxonomyCategory.getCreator()));
		}

		if (taxonomyCategory.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

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

			sb.append("\"dateModified\": ");

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

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(taxonomyCategory.getDescription()));

			sb.append("\"");
		}

		if (taxonomyCategory.getDescription_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description_i18n\": ");

			sb.append(_toJSON(taxonomyCategory.getDescription_i18n()));
		}

		if (taxonomyCategory.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(taxonomyCategory.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (taxonomyCategory.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append("\"");

			sb.append(_escape(taxonomyCategory.getId()));

			sb.append("\"");
		}

		if (taxonomyCategory.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(taxonomyCategory.getName()));

			sb.append("\"");
		}

		if (taxonomyCategory.getName_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name_i18n\": ");

			sb.append(_toJSON(taxonomyCategory.getName_i18n()));
		}

		if (taxonomyCategory.getNumberOfTaxonomyCategories() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numberOfTaxonomyCategories\": ");

			sb.append(taxonomyCategory.getNumberOfTaxonomyCategories());
		}

		if (taxonomyCategory.getParentTaxonomyCategory() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parentTaxonomyCategory\": ");

			sb.append(
				String.valueOf(taxonomyCategory.getParentTaxonomyCategory()));
		}

		if (taxonomyCategory.getParentTaxonomyVocabulary() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parentTaxonomyVocabulary\": ");

			sb.append(
				String.valueOf(taxonomyCategory.getParentTaxonomyVocabulary()));
		}

		if (taxonomyCategory.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\": ");

			sb.append(taxonomyCategory.getSiteId());
		}

		if (taxonomyCategory.getTaxonomyCategoryProperties() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryProperties\": ");

			sb.append("[");

			for (int i = 0;
				 i < taxonomyCategory.getTaxonomyCategoryProperties().length;
				 i++) {

				sb.append(
					String.valueOf(
						taxonomyCategory.getTaxonomyCategoryProperties()[i]));

				if ((i + 1) <
						taxonomyCategory.
							getTaxonomyCategoryProperties().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (taxonomyCategory.getTaxonomyCategoryUsageCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryUsageCount\": ");

			sb.append(taxonomyCategory.getTaxonomyCategoryUsageCount());
		}

		if (taxonomyCategory.getTaxonomyVocabularyId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyVocabularyId\": ");

			sb.append(taxonomyCategory.getTaxonomyVocabularyId());
		}

		if (taxonomyCategory.getViewableBy() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"viewableBy\": ");

			sb.append("\"");

			sb.append(taxonomyCategory.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		TaxonomyCategoryJSONParser taxonomyCategoryJSONParser =
			new TaxonomyCategoryJSONParser();

		return taxonomyCategoryJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(TaxonomyCategory taxonomyCategory) {
		if (taxonomyCategory == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (taxonomyCategory.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(taxonomyCategory.getActions()));
		}

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
			map.put("creator", String.valueOf(taxonomyCategory.getCreator()));
		}

		if (taxonomyCategory.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(
					taxonomyCategory.getDateCreated()));
		}

		if (taxonomyCategory.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(
					taxonomyCategory.getDateModified()));
		}

		if (taxonomyCategory.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(taxonomyCategory.getDescription()));
		}

		if (taxonomyCategory.getDescription_i18n() == null) {
			map.put("description_i18n", null);
		}
		else {
			map.put(
				"description_i18n",
				String.valueOf(taxonomyCategory.getDescription_i18n()));
		}

		if (taxonomyCategory.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(taxonomyCategory.getExternalReferenceCode()));
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

		if (taxonomyCategory.getName_i18n() == null) {
			map.put("name_i18n", null);
		}
		else {
			map.put(
				"name_i18n", String.valueOf(taxonomyCategory.getName_i18n()));
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
				String.valueOf(taxonomyCategory.getParentTaxonomyCategory()));
		}

		if (taxonomyCategory.getParentTaxonomyVocabulary() == null) {
			map.put("parentTaxonomyVocabulary", null);
		}
		else {
			map.put(
				"parentTaxonomyVocabulary",
				String.valueOf(taxonomyCategory.getParentTaxonomyVocabulary()));
		}

		if (taxonomyCategory.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(taxonomyCategory.getSiteId()));
		}

		if (taxonomyCategory.getTaxonomyCategoryProperties() == null) {
			map.put("taxonomyCategoryProperties", null);
		}
		else {
			map.put(
				"taxonomyCategoryProperties",
				String.valueOf(
					taxonomyCategory.getTaxonomyCategoryProperties()));
		}

		if (taxonomyCategory.getTaxonomyCategoryUsageCount() == null) {
			map.put("taxonomyCategoryUsageCount", null);
		}
		else {
			map.put(
				"taxonomyCategoryUsageCount",
				String.valueOf(
					taxonomyCategory.getTaxonomyCategoryUsageCount()));
		}

		if (taxonomyCategory.getTaxonomyVocabularyId() == null) {
			map.put("taxonomyVocabularyId", null);
		}
		else {
			map.put(
				"taxonomyVocabularyId",
				String.valueOf(taxonomyCategory.getTaxonomyVocabularyId()));
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

	public static class TaxonomyCategoryJSONParser
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

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setActions(
						(Map)TaxonomyCategorySerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "availableLanguages")) {

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
			else if (Objects.equals(jsonParserFieldName, "description_i18n")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setDescription_i18n(
						(Map)TaxonomyCategorySerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					taxonomyCategory.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setId((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name_i18n")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setName_i18n(
						(Map)TaxonomyCategorySerDes.toMap(
							(String)jsonParserFieldValue));
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
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategoryProperties")) {

				if (jsonParserFieldValue != null) {
					taxonomyCategory.setTaxonomyCategoryProperties(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> TaxonomyCategoryPropertySerDes.toDTO(
								(String)object)
						).toArray(
							size -> new TaxonomyCategoryProperty[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategoryUsageCount")) {

				if (jsonParserFieldValue != null) {
					taxonomyCategory.setTaxonomyCategoryUsageCount(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyVocabularyId")) {

				if (jsonParserFieldValue != null) {
					taxonomyCategory.setTaxonomyVocabularyId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setViewableBy(
						TaxonomyCategory.ViewableBy.create(
							(String)jsonParserFieldValue));
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
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
			sb.append("\": ");

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
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}