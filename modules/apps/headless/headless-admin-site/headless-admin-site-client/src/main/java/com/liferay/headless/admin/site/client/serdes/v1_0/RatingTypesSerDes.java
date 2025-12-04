/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.serdes.v1_0;

import com.liferay.headless.admin.site.client.dto.v1_0.RatingTypes;
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
public class RatingTypesSerDes {

	public static RatingTypes toDTO(String json) {
		RatingTypesJSONParser ratingTypesJSONParser =
			new RatingTypesJSONParser();

		return ratingTypesJSONParser.parseToDTO(json);
	}

	public static RatingTypes[] toDTOs(String json) {
		RatingTypesJSONParser ratingTypesJSONParser =
			new RatingTypesJSONParser();

		return ratingTypesJSONParser.parseToDTOs(json);
	}

	public static String toJSON(RatingTypes ratingTypes) {
		if (ratingTypes == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (ratingTypes.getBlogs() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"blogs\": ");

			sb.append("\"");
			sb.append(ratingTypes.getBlogs());
			sb.append("\"");
		}

		if (ratingTypes.getBookmarks() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"bookmarks\": ");

			sb.append("\"");
			sb.append(ratingTypes.getBookmarks());
			sb.append("\"");
		}

		if (ratingTypes.getComments() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"comments\": ");

			sb.append("\"");
			sb.append(ratingTypes.getComments());
			sb.append("\"");
		}

		if (ratingTypes.getDocumentsAndMedia() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"documentsAndMedia\": ");

			sb.append("\"");
			sb.append(ratingTypes.getDocumentsAndMedia());
			sb.append("\"");
		}

		if (ratingTypes.getKnowledgeBase() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"knowledgeBase\": ");

			sb.append("\"");
			sb.append(ratingTypes.getKnowledgeBase());
			sb.append("\"");
		}

		if (ratingTypes.getMessageBoards() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"messageBoards\": ");

			sb.append("\"");
			sb.append(ratingTypes.getMessageBoards());
			sb.append("\"");
		}

		if (ratingTypes.getPageRatings() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pageRatings\": ");

			sb.append("\"");
			sb.append(ratingTypes.getPageRatings());
			sb.append("\"");
		}

		if (ratingTypes.getWebContent() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"webContent\": ");

			sb.append("\"");
			sb.append(ratingTypes.getWebContent());
			sb.append("\"");
		}

		if (ratingTypes.getWiki() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"wiki\": ");

			sb.append("\"");
			sb.append(ratingTypes.getWiki());
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		RatingTypesJSONParser ratingTypesJSONParser =
			new RatingTypesJSONParser();

		return ratingTypesJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(RatingTypes ratingTypes) {
		if (ratingTypes == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (ratingTypes.getBlogs() == null) {
			map.put("blogs", null);
		}
		else {
			map.put("blogs", String.valueOf(ratingTypes.getBlogs()));
		}

		if (ratingTypes.getBookmarks() == null) {
			map.put("bookmarks", null);
		}
		else {
			map.put("bookmarks", String.valueOf(ratingTypes.getBookmarks()));
		}

		if (ratingTypes.getComments() == null) {
			map.put("comments", null);
		}
		else {
			map.put("comments", String.valueOf(ratingTypes.getComments()));
		}

		if (ratingTypes.getDocumentsAndMedia() == null) {
			map.put("documentsAndMedia", null);
		}
		else {
			map.put(
				"documentsAndMedia",
				String.valueOf(ratingTypes.getDocumentsAndMedia()));
		}

		if (ratingTypes.getKnowledgeBase() == null) {
			map.put("knowledgeBase", null);
		}
		else {
			map.put(
				"knowledgeBase",
				String.valueOf(ratingTypes.getKnowledgeBase()));
		}

		if (ratingTypes.getMessageBoards() == null) {
			map.put("messageBoards", null);
		}
		else {
			map.put(
				"messageBoards",
				String.valueOf(ratingTypes.getMessageBoards()));
		}

		if (ratingTypes.getPageRatings() == null) {
			map.put("pageRatings", null);
		}
		else {
			map.put(
				"pageRatings", String.valueOf(ratingTypes.getPageRatings()));
		}

		if (ratingTypes.getWebContent() == null) {
			map.put("webContent", null);
		}
		else {
			map.put("webContent", String.valueOf(ratingTypes.getWebContent()));
		}

		if (ratingTypes.getWiki() == null) {
			map.put("wiki", null);
		}
		else {
			map.put("wiki", String.valueOf(ratingTypes.getWiki()));
		}

		return map;
	}

	public static class RatingTypesJSONParser
		extends BaseJSONParser<RatingTypes> {

		@Override
		protected RatingTypes createDTO() {
			return new RatingTypes();
		}

		@Override
		protected RatingTypes[] createDTOArray(int size) {
			return new RatingTypes[size];
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
			RatingTypes ratingTypes, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "blogs")) {
				if (jsonParserFieldValue != null) {
					ratingTypes.setBlogs(
						RatingTypes.Blogs.create((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "bookmarks")) {
				if (jsonParserFieldValue != null) {
					ratingTypes.setBookmarks(
						RatingTypes.Bookmarks.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "comments")) {
				if (jsonParserFieldValue != null) {
					ratingTypes.setComments(
						RatingTypes.Comments.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "documentsAndMedia")) {
				if (jsonParserFieldValue != null) {
					ratingTypes.setDocumentsAndMedia(
						RatingTypes.DocumentsAndMedia.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "knowledgeBase")) {
				if (jsonParserFieldValue != null) {
					ratingTypes.setKnowledgeBase(
						RatingTypes.KnowledgeBase.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "messageBoards")) {
				if (jsonParserFieldValue != null) {
					ratingTypes.setMessageBoards(
						RatingTypes.MessageBoards.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageRatings")) {
				if (jsonParserFieldValue != null) {
					ratingTypes.setPageRatings(
						RatingTypes.PageRatings.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "webContent")) {
				if (jsonParserFieldValue != null) {
					ratingTypes.setWebContent(
						RatingTypes.WebContent.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "wiki")) {
				if (jsonParserFieldValue != null) {
					ratingTypes.setWiki(
						RatingTypes.Wiki.create((String)jsonParserFieldValue));
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