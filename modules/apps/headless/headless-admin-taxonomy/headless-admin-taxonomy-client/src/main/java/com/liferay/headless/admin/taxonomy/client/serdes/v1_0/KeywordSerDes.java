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

import com.liferay.headless.admin.taxonomy.client.dto.v1_0.Keyword;
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
public class KeywordSerDes {

	public static Keyword toDTO(String json) {
		KeywordJSONParser keywordJSONParser = new KeywordJSONParser();

		return keywordJSONParser.parseToDTO(json);
	}

	public static Keyword[] toDTOs(String json) {
		KeywordJSONParser keywordJSONParser = new KeywordJSONParser();

		return keywordJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Keyword keyword) {
		if (keyword == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		sb.append("\"creator\": ");

		sb.append(CreatorSerDes.toJSON(keyword.getCreator()));
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (keyword.getDateCreated() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(keyword.getDateCreated()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (keyword.getDateModified() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(keyword.getDateModified()));

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (keyword.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(keyword.getId());
		}

		sb.append(", ");

		sb.append("\"keywordUsageCount\": ");

		if (keyword.getKeywordUsageCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(keyword.getKeywordUsageCount());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (keyword.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(keyword.getName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"siteId\": ");

		if (keyword.getSiteId() == null) {
			sb.append("null");
		}
		else {
			sb.append(keyword.getSiteId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Keyword keyword) {
		if (keyword == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (keyword.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", CreatorSerDes.toJSON(keyword.getCreator()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(keyword.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(keyword.getDateModified()));

		if (keyword.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(keyword.getId()));
		}

		if (keyword.getKeywordUsageCount() == null) {
			map.put("keywordUsageCount", null);
		}
		else {
			map.put(
				"keywordUsageCount",
				String.valueOf(keyword.getKeywordUsageCount()));
		}

		if (keyword.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(keyword.getName()));
		}

		if (keyword.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(keyword.getSiteId()));
		}

		return map;
	}

	private static class KeywordJSONParser extends BaseJSONParser<Keyword> {

		@Override
		protected Keyword createDTO() {
			return new Keyword();
		}

		@Override
		protected Keyword[] createDTOArray(int size) {
			return new Keyword[size];
		}

		@Override
		protected void setField(
			Keyword keyword, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					keyword.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					keyword.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					keyword.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					keyword.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywordUsageCount")) {
				if (jsonParserFieldValue != null) {
					keyword.setKeywordUsageCount(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					keyword.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					keyword.setSiteId(
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