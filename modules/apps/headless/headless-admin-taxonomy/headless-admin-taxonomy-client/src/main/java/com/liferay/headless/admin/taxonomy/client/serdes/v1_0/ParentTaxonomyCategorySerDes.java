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

import com.liferay.headless.admin.taxonomy.client.dto.v1_0.ParentTaxonomyCategory;
import com.liferay.headless.admin.taxonomy.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ParentTaxonomyCategorySerDes {

	public static ParentTaxonomyCategory toDTO(String json) {
		ParentTaxonomyCategoryJSONParser parentTaxonomyCategoryJSONParser =
			new ParentTaxonomyCategoryJSONParser();

		return parentTaxonomyCategoryJSONParser.parseToDTO(json);
	}

	public static ParentTaxonomyCategory[] toDTOs(String json) {
		ParentTaxonomyCategoryJSONParser parentTaxonomyCategoryJSONParser =
			new ParentTaxonomyCategoryJSONParser();

		return parentTaxonomyCategoryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ParentTaxonomyCategory parentTaxonomyCategory) {
		if (parentTaxonomyCategory == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"id\": ");

		if (parentTaxonomyCategory.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(parentTaxonomyCategory.getId());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (parentTaxonomyCategory.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(parentTaxonomyCategory.getName());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(
		ParentTaxonomyCategory parentTaxonomyCategory) {

		if (parentTaxonomyCategory == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		map.put("id", String.valueOf(parentTaxonomyCategory.getId()));

		map.put("name", String.valueOf(parentTaxonomyCategory.getName()));

		return map;
	}

	private static class ParentTaxonomyCategoryJSONParser
		extends BaseJSONParser<ParentTaxonomyCategory> {

		@Override
		protected ParentTaxonomyCategory createDTO() {
			return new ParentTaxonomyCategory();
		}

		@Override
		protected ParentTaxonomyCategory[] createDTOArray(int size) {
			return new ParentTaxonomyCategory[size];
		}

		@Override
		protected void setField(
			ParentTaxonomyCategory parentTaxonomyCategory,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					parentTaxonomyCategory.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					parentTaxonomyCategory.setName(
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