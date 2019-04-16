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

package com.liferay.bulk.rest.client.serdes.v1_0;

import com.liferay.bulk.rest.client.dto.v1_0.TaxonomyCategory;
import com.liferay.bulk.rest.client.dto.v1_0.TaxonomyVocabulary;
import com.liferay.bulk.rest.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
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

		sb.append("\"multiValued\": ");

		if (taxonomyVocabulary.getMultiValued() == null) {
			sb.append("null");
		}
		else {
			sb.append(taxonomyVocabulary.getMultiValued());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (taxonomyVocabulary.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(taxonomyVocabulary.getName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"required\": ");

		if (taxonomyVocabulary.getRequired() == null) {
			sb.append("null");
		}
		else {
			sb.append(taxonomyVocabulary.getRequired());
		}

		sb.append(", ");

		sb.append("\"taxonomyCategories\": ");

		if (taxonomyVocabulary.getTaxonomyCategories() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < taxonomyVocabulary.getTaxonomyCategories().length; i++) {

				sb.append(
					TaxonomyCategorySerDes.toJSON(
						taxonomyVocabulary.getTaxonomyCategories()[i]));

				if ((i + 1) <
						taxonomyVocabulary.getTaxonomyCategories().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"taxonomyVocabularyId\": ");

		if (taxonomyVocabulary.getTaxonomyVocabularyId() == null) {
			sb.append("null");
		}
		else {
			sb.append(taxonomyVocabulary.getTaxonomyVocabularyId());
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

		map.put(
			"multiValued", String.valueOf(taxonomyVocabulary.getMultiValued()));

		map.put("name", String.valueOf(taxonomyVocabulary.getName()));

		map.put("required", String.valueOf(taxonomyVocabulary.getRequired()));

		map.put(
			"taxonomyCategories",
			String.valueOf(taxonomyVocabulary.getTaxonomyCategories()));

		map.put(
			"taxonomyVocabularyId",
			String.valueOf(taxonomyVocabulary.getTaxonomyVocabularyId()));

		return map;
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

			if (Objects.equals(jsonParserFieldName, "multiValued")) {
				if (jsonParserFieldValue != null) {
					taxonomyVocabulary.setMultiValued(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					taxonomyVocabulary.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "required")) {
				if (jsonParserFieldValue != null) {
					taxonomyVocabulary.setRequired(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategories")) {

				if (jsonParserFieldValue != null) {
					taxonomyVocabulary.setTaxonomyCategories(
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
						jsonParserFieldName, "taxonomyVocabularyId")) {

				if (jsonParserFieldValue != null) {
					taxonomyVocabulary.setTaxonomyVocabularyId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}