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
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Date;
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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"creator\": ");

		if (keyword.getCreator() == null) {
			sb.append("null");
		}
		else {
			sb.append(keyword.getCreator());
		}

		sb.append(", ");

		sb.append("\"dateCreated\": ");

		if (keyword.getDateCreated() == null) {
			sb.append("null");
		}
		else {
			sb.append(keyword.getDateCreated());
		}

		sb.append(", ");

		sb.append("\"dateModified\": ");

		if (keyword.getDateModified() == null) {
			sb.append("null");
		}
		else {
			sb.append(keyword.getDateModified());
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
			sb.append(keyword.getName());
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

	public static String toJSON(Collection<Keyword> keywords) {
		if (keywords == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (Keyword keyword : keywords) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(keyword));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class KeywordJSONParser extends BaseJSONParser<Keyword> {

		protected Keyword createDTO() {
			return new Keyword();
		}

		protected Keyword[] createDTOArray(int size) {
			return new Keyword[size];
		}

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
						_toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					keyword.setDateModified(
						_toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					keyword.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywordUsageCount")) {
				if (jsonParserFieldValue != null) {
					keyword.setKeywordUsageCount((Number)jsonParserFieldValue);
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

		private Date _toDate(String string) {
			try {
				DateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss'Z'");

				return dateFormat.parse(string);
			}
			catch (ParseException pe) {
				throw new IllegalArgumentException("Unable to parse " + string);
			}
		}

	}

}