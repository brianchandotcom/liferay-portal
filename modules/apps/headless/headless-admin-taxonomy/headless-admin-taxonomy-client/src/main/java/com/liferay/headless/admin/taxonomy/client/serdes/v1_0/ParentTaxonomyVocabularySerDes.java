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

import com.liferay.headless.admin.taxonomy.client.dto.v1_0.ParentTaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ParentTaxonomyVocabularySerDes {

	public static ParentTaxonomyVocabulary toDTO(String json) {
		ParentTaxonomyVocabularyJSONParser parentTaxonomyVocabularyJSONParser =
			new ParentTaxonomyVocabularyJSONParser();

		return parentTaxonomyVocabularyJSONParser.parseToDTO(json);
	}

	public static ParentTaxonomyVocabulary[] toDTOs(String json) {
		ParentTaxonomyVocabularyJSONParser parentTaxonomyVocabularyJSONParser =
			new ParentTaxonomyVocabularyJSONParser();

		return parentTaxonomyVocabularyJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ParentTaxonomyVocabulary parentTaxonomyVocabulary) {

		if (parentTaxonomyVocabulary == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"id\": ");

		if (parentTaxonomyVocabulary.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(parentTaxonomyVocabulary.getId());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (parentTaxonomyVocabulary.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append(parentTaxonomyVocabulary.getName());
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(
		Collection<ParentTaxonomyVocabulary> parentTaxonomyVocabularies) {

		if (parentTaxonomyVocabularies == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (ParentTaxonomyVocabulary parentTaxonomyVocabulary :
				parentTaxonomyVocabularies) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(parentTaxonomyVocabulary));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class ParentTaxonomyVocabularyJSONParser
		extends BaseJSONParser<ParentTaxonomyVocabulary> {

		protected ParentTaxonomyVocabulary createDTO() {
			return new ParentTaxonomyVocabulary();
		}

		protected ParentTaxonomyVocabulary[] createDTOArray(int size) {
			return new ParentTaxonomyVocabulary[size];
		}

		protected void setField(
			ParentTaxonomyVocabulary parentTaxonomyVocabulary,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					parentTaxonomyVocabulary.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					parentTaxonomyVocabulary.setName(
						(String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}