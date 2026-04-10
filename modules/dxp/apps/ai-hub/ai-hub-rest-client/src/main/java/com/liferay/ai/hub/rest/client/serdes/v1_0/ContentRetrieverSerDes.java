/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.client.serdes.v1_0;

import com.liferay.ai.hub.rest.client.dto.v1_0.ContentRetriever;
import com.liferay.ai.hub.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Feliphe Marinho
 * @generated
 */
@Generated("")
public class ContentRetrieverSerDes {

	public static ContentRetriever toDTO(String json) {
		ContentRetrieverJSONParser contentRetrieverJSONParser =
			new ContentRetrieverJSONParser();

		return contentRetrieverJSONParser.parseToDTO(json);
	}

	public static ContentRetriever[] toDTOs(String json) {
		ContentRetrieverJSONParser contentRetrieverJSONParser =
			new ContentRetrieverJSONParser();

		return contentRetrieverJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContentRetriever contentRetriever) {
		if (contentRetriever == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (contentRetriever.getCrawlDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"crawlDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					contentRetriever.getCrawlDate()));

			sb.append("\"");
		}

		if (contentRetriever.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(contentRetriever.getDescription()));

			sb.append("\"");
		}

		if (contentRetriever.getDescription_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description_i18n\": ");

			sb.append(_toJSON(contentRetriever.getDescription_i18n()));
		}

		if (contentRetriever.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(contentRetriever.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (contentRetriever.getIndexName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"indexName\": ");

			sb.append("\"");

			sb.append(_escape(contentRetriever.getIndexName()));

			sb.append("\"");
		}

		if (contentRetriever.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(contentRetriever.getTitle()));

			sb.append("\"");
		}

		if (contentRetriever.getTitle_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title_i18n\": ");

			sb.append(_toJSON(contentRetriever.getTitle_i18n()));
		}

		if (contentRetriever.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(contentRetriever.getType()));

			sb.append("\"");
		}

		if (contentRetriever.getUrl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"url\": ");

			sb.append("\"");

			sb.append(_escape(contentRetriever.getUrl()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ContentRetrieverJSONParser contentRetrieverJSONParser =
			new ContentRetrieverJSONParser();

		return contentRetrieverJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ContentRetriever contentRetriever) {
		if (contentRetriever == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (contentRetriever.getCrawlDate() == null) {
			map.put("crawlDate", null);
		}
		else {
			map.put(
				"crawlDate",
				liferayToJSONDateFormat.format(
					contentRetriever.getCrawlDate()));
		}

		if (contentRetriever.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(contentRetriever.getDescription()));
		}

		if (contentRetriever.getDescription_i18n() == null) {
			map.put("description_i18n", null);
		}
		else {
			map.put(
				"description_i18n",
				String.valueOf(contentRetriever.getDescription_i18n()));
		}

		if (contentRetriever.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(contentRetriever.getExternalReferenceCode()));
		}

		if (contentRetriever.getIndexName() == null) {
			map.put("indexName", null);
		}
		else {
			map.put(
				"indexName", String.valueOf(contentRetriever.getIndexName()));
		}

		if (contentRetriever.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(contentRetriever.getTitle()));
		}

		if (contentRetriever.getTitle_i18n() == null) {
			map.put("title_i18n", null);
		}
		else {
			map.put(
				"title_i18n", String.valueOf(contentRetriever.getTitle_i18n()));
		}

		if (contentRetriever.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(contentRetriever.getType()));
		}

		if (contentRetriever.getUrl() == null) {
			map.put("url", null);
		}
		else {
			map.put("url", String.valueOf(contentRetriever.getUrl()));
		}

		return map;
	}

	public static class ContentRetrieverJSONParser
		extends BaseJSONParser<ContentRetriever> {

		@Override
		protected ContentRetriever createDTO() {
			return new ContentRetriever();
		}

		@Override
		protected ContentRetriever[] createDTOArray(int size) {
			return new ContentRetriever[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "crawlDate")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "description_i18n")) {
				return true;
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "indexName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "title_i18n")) {
				return true;
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "url")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ContentRetriever contentRetriever, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "crawlDate")) {
				if (jsonParserFieldValue != null) {
					contentRetriever.setCrawlDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					contentRetriever.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description_i18n")) {
				if (jsonParserFieldValue != null) {
					contentRetriever.setDescription_i18n(
						(Map<String, String>)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					contentRetriever.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "indexName")) {
				if (jsonParserFieldValue != null) {
					contentRetriever.setIndexName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					contentRetriever.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title_i18n")) {
				if (jsonParserFieldValue != null) {
					contentRetriever.setTitle_i18n(
						(Map<String, String>)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					contentRetriever.setType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "url")) {
				if (jsonParserFieldValue != null) {
					contentRetriever.setUrl((String)jsonParserFieldValue);
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

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value == null) {
			return "null";
		}

		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}
// LIFERAY-REST-BUILDER-HASH:1282608392