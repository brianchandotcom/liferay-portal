/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.serdes.v1_0;

import com.liferay.headless.admin.site.client.dto.v1_0.RatingsTypes;
import com.liferay.headless.admin.site.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class RatingsTypesSerDes {

	public static RatingsTypes toDTO(String json) {
		RatingsTypesJSONParser ratingsTypesJSONParser =
			new RatingsTypesJSONParser();

		return ratingsTypesJSONParser.parseToDTO(json);
	}

	public static RatingsTypes[] toDTOs(String json) {
		RatingsTypesJSONParser ratingsTypesJSONParser =
			new RatingsTypesJSONParser();

		return ratingsTypesJSONParser.parseToDTOs(json);
	}

	public static String toJSON(RatingsTypes ratingsTypes) {
		if (ratingsTypes == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (ratingsTypes.getBlogs() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"blogs\": ");

			sb.append("\"");
			sb.append(ratingsTypes.getBlogs());
			sb.append("\"");
		}

		if (ratingsTypes.getBookmarks() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"bookmarks\": ");

			sb.append("\"");
			sb.append(ratingsTypes.getBookmarks());
			sb.append("\"");
		}

		if (ratingsTypes.getComments() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"comments\": ");

			sb.append("\"");
			sb.append(ratingsTypes.getComments());
			sb.append("\"");
		}

		if (ratingsTypes.getDocumentsAndMedia() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"documentsAndMedia\": ");

			sb.append("\"");
			sb.append(ratingsTypes.getDocumentsAndMedia());
			sb.append("\"");
		}

		if (ratingsTypes.getKnowledgeBase() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"knowledgeBase\": ");

			sb.append("\"");
			sb.append(ratingsTypes.getKnowledgeBase());
			sb.append("\"");
		}

		if (ratingsTypes.getMessageBoards() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"messageBoards\": ");

			sb.append("\"");
			sb.append(ratingsTypes.getMessageBoards());
			sb.append("\"");
		}

		if (ratingsTypes.getPageRatings() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pageRatings\": ");

			sb.append("\"");
			sb.append(ratingsTypes.getPageRatings());
			sb.append("\"");
		}

		if (ratingsTypes.getWebContent() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"webContent\": ");

			sb.append("\"");
			sb.append(ratingsTypes.getWebContent());
			sb.append("\"");
		}

		if (ratingsTypes.getWiki() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"wiki\": ");

			sb.append("\"");
			sb.append(ratingsTypes.getWiki());
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		RatingsTypesJSONParser ratingsTypesJSONParser =
			new RatingsTypesJSONParser();

		return ratingsTypesJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(RatingsTypes ratingsTypes) {
		if (ratingsTypes == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (ratingsTypes.getBlogs() == null) {
			map.put("blogs", null);
		}
		else {
			map.put("blogs", String.valueOf(ratingsTypes.getBlogs()));
		}

		if (ratingsTypes.getBookmarks() == null) {
			map.put("bookmarks", null);
		}
		else {
			map.put("bookmarks", String.valueOf(ratingsTypes.getBookmarks()));
		}

		if (ratingsTypes.getComments() == null) {
			map.put("comments", null);
		}
		else {
			map.put("comments", String.valueOf(ratingsTypes.getComments()));
		}

		if (ratingsTypes.getDocumentsAndMedia() == null) {
			map.put("documentsAndMedia", null);
		}
		else {
			map.put(
				"documentsAndMedia",
				String.valueOf(ratingsTypes.getDocumentsAndMedia()));
		}

		if (ratingsTypes.getKnowledgeBase() == null) {
			map.put("knowledgeBase", null);
		}
		else {
			map.put(
				"knowledgeBase",
				String.valueOf(ratingsTypes.getKnowledgeBase()));
		}

		if (ratingsTypes.getMessageBoards() == null) {
			map.put("messageBoards", null);
		}
		else {
			map.put(
				"messageBoards",
				String.valueOf(ratingsTypes.getMessageBoards()));
		}

		if (ratingsTypes.getPageRatings() == null) {
			map.put("pageRatings", null);
		}
		else {
			map.put(
				"pageRatings", String.valueOf(ratingsTypes.getPageRatings()));
		}

		if (ratingsTypes.getWebContent() == null) {
			map.put("webContent", null);
		}
		else {
			map.put("webContent", String.valueOf(ratingsTypes.getWebContent()));
		}

		if (ratingsTypes.getWiki() == null) {
			map.put("wiki", null);
		}
		else {
			map.put("wiki", String.valueOf(ratingsTypes.getWiki()));
		}

		return map;
	}

	public static class RatingsTypesJSONParser
		extends BaseJSONParser<RatingsTypes> {

		@Override
		protected RatingsTypes createDTO() {
			return new RatingsTypes();
		}

		@Override
		protected RatingsTypes[] createDTOArray(int size) {
			return new RatingsTypes[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "blogs")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "bookmarks")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "comments")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "documentsAndMedia")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "knowledgeBase")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "messageBoards")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "pageRatings")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "webContent")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "wiki")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			RatingsTypes ratingsTypes, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "blogs")) {
				if (jsonParserFieldValue != null) {
					ratingsTypes.setBlogs(
						RatingsTypes.Blogs.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "bookmarks")) {
				if (jsonParserFieldValue != null) {
					ratingsTypes.setBookmarks(
						RatingsTypes.Bookmarks.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "comments")) {
				if (jsonParserFieldValue != null) {
					ratingsTypes.setComments(
						RatingsTypes.Comments.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "documentsAndMedia")) {
				if (jsonParserFieldValue != null) {
					ratingsTypes.setDocumentsAndMedia(
						RatingsTypes.DocumentsAndMedia.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "knowledgeBase")) {
				if (jsonParserFieldValue != null) {
					ratingsTypes.setKnowledgeBase(
						RatingsTypes.KnowledgeBase.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "messageBoards")) {
				if (jsonParserFieldValue != null) {
					ratingsTypes.setMessageBoards(
						RatingsTypes.MessageBoards.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageRatings")) {
				if (jsonParserFieldValue != null) {
					ratingsTypes.setPageRatings(
						RatingsTypes.PageRatings.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "webContent")) {
				if (jsonParserFieldValue != null) {
					ratingsTypes.setWebContent(
						RatingsTypes.WebContent.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "wiki")) {
				if (jsonParserFieldValue != null) {
					ratingsTypes.setWiki(
						RatingsTypes.Wiki.create((String)jsonParserFieldValue));
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