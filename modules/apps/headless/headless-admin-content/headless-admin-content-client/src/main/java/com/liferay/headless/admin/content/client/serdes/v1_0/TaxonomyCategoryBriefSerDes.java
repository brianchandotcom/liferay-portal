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

package com.liferay.headless.admin.content.client.serdes.v1_0;

import com.liferay.headless.admin.content.client.dto.v1_0.TaxonomyCategoryBrief;
import com.liferay.headless.admin.content.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class TaxonomyCategoryBriefSerDes {

	public static TaxonomyCategoryBrief toDTO(String json) {
		TaxonomyCategoryBriefJSONParser taxonomyCategoryBriefJSONParser =
			new TaxonomyCategoryBriefJSONParser();

		return taxonomyCategoryBriefJSONParser.parseToDTO(json);
	}

	public static TaxonomyCategoryBrief[] toDTOs(String json) {
		TaxonomyCategoryBriefJSONParser taxonomyCategoryBriefJSONParser =
			new TaxonomyCategoryBriefJSONParser();

		return taxonomyCategoryBriefJSONParser.parseToDTOs(json);
	}

	public static String toJSON(TaxonomyCategoryBrief taxonomyCategoryBrief) {
		if (taxonomyCategoryBrief == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (taxonomyCategoryBrief.getEmbeddedTaxonomyCategory() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"embeddedTaxonomyCategory\": ");

			if (taxonomyCategoryBrief.getEmbeddedTaxonomyCategory() instanceof
					String) {

				sb.append("\"");
				sb.append(
					(String)
						taxonomyCategoryBrief.getEmbeddedTaxonomyCategory());
				sb.append("\"");
			}
			else {
				sb.append(taxonomyCategoryBrief.getEmbeddedTaxonomyCategory());
			}
		}

		if (taxonomyCategoryBrief.getTaxonomyCategoryId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryId\": ");

			sb.append(taxonomyCategoryBrief.getTaxonomyCategoryId());
		}

		if (taxonomyCategoryBrief.getTaxonomyCategoryName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryName\": ");

			sb.append("\"");

			sb.append(_escape(taxonomyCategoryBrief.getTaxonomyCategoryName()));

			sb.append("\"");
		}

		if (taxonomyCategoryBrief.getTaxonomyCategoryName_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryName_i18n\": ");

			sb.append(
				_toJSON(taxonomyCategoryBrief.getTaxonomyCategoryName_i18n()));
		}

		if (taxonomyCategoryBrief.getTaxonomyCategoryReference() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryReference\": ");

			sb.append(taxonomyCategoryBrief.getTaxonomyCategoryReference());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		TaxonomyCategoryBriefJSONParser taxonomyCategoryBriefJSONParser =
			new TaxonomyCategoryBriefJSONParser();

		return taxonomyCategoryBriefJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		TaxonomyCategoryBrief taxonomyCategoryBrief) {

		if (taxonomyCategoryBrief == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (taxonomyCategoryBrief.getEmbeddedTaxonomyCategory() == null) {
			map.put("embeddedTaxonomyCategory", null);
		}
		else {
			map.put(
				"embeddedTaxonomyCategory",
				String.valueOf(
					taxonomyCategoryBrief.getEmbeddedTaxonomyCategory()));
		}

		if (taxonomyCategoryBrief.getTaxonomyCategoryId() == null) {
			map.put("taxonomyCategoryId", null);
		}
		else {
			map.put(
				"taxonomyCategoryId",
				String.valueOf(taxonomyCategoryBrief.getTaxonomyCategoryId()));
		}

		if (taxonomyCategoryBrief.getTaxonomyCategoryName() == null) {
			map.put("taxonomyCategoryName", null);
		}
		else {
			map.put(
				"taxonomyCategoryName",
				String.valueOf(
					taxonomyCategoryBrief.getTaxonomyCategoryName()));
		}

		if (taxonomyCategoryBrief.getTaxonomyCategoryName_i18n() == null) {
			map.put("taxonomyCategoryName_i18n", null);
		}
		else {
			map.put(
				"taxonomyCategoryName_i18n",
				String.valueOf(
					taxonomyCategoryBrief.getTaxonomyCategoryName_i18n()));
		}

		if (taxonomyCategoryBrief.getTaxonomyCategoryReference() == null) {
			map.put("taxonomyCategoryReference", null);
		}
		else {
			map.put(
				"taxonomyCategoryReference",
				String.valueOf(
					taxonomyCategoryBrief.getTaxonomyCategoryReference()));
		}

		return map;
	}

	public static class TaxonomyCategoryBriefJSONParser
		extends BaseJSONParser<TaxonomyCategoryBrief> {

		@Override
		protected TaxonomyCategoryBrief createDTO() {
			return new TaxonomyCategoryBrief();
		}

		@Override
		protected TaxonomyCategoryBrief[] createDTOArray(int size) {
			return new TaxonomyCategoryBrief[size];
		}

		@Override
		protected void setField(
			TaxonomyCategoryBrief taxonomyCategoryBrief,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "embeddedTaxonomyCategory")) {

				if (jsonParserFieldValue != null) {
					taxonomyCategoryBrief.setEmbeddedTaxonomyCategory(
						(Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategoryId")) {

				if (jsonParserFieldValue != null) {
					taxonomyCategoryBrief.setTaxonomyCategoryId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategoryName")) {

				if (jsonParserFieldValue != null) {
					taxonomyCategoryBrief.setTaxonomyCategoryName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategoryName_i18n")) {

				if (jsonParserFieldValue != null) {
					taxonomyCategoryBrief.setTaxonomyCategoryName_i18n(
						(Map)TaxonomyCategoryBriefSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategoryReference")) {

				if (jsonParserFieldValue != null) {
					taxonomyCategoryBrief.setTaxonomyCategoryReference(
						TaxonomyCategoryReferenceSerDes.toDTO(
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