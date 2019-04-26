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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.RelatedContent;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class RelatedContentSerDes {

	public static RelatedContent toDTO(String json) {
		RelatedContentJSONParser relatedContentJSONParser =
			new RelatedContentJSONParser();

		return relatedContentJSONParser.parseToDTO(json);
	}

	public static RelatedContent[] toDTOs(String json) {
		RelatedContentJSONParser relatedContentJSONParser =
			new RelatedContentJSONParser();

		return relatedContentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(RelatedContent relatedContent) {
		if (relatedContent == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (relatedContent.getContentType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentType\": ");

			sb.append("\"");

			sb.append(_escape(relatedContent.getContentType()));

			sb.append("\"");
		}

		if (relatedContent.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(relatedContent.getId());
		}

		if (relatedContent.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(relatedContent.getTitle()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(RelatedContent relatedContent) {
		if (relatedContent == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (relatedContent.getContentType() == null) {
			map.put("contentType", null);
		}
		else {
			map.put(
				"contentType", String.valueOf(relatedContent.getContentType()));
		}

		if (relatedContent.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(relatedContent.getId()));
		}

		if (relatedContent.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(relatedContent.getTitle()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class RelatedContentJSONParser
		extends BaseJSONParser<RelatedContent> {

		@Override
		protected RelatedContent createDTO() {
			return new RelatedContent();
		}

		@Override
		protected RelatedContent[] createDTOArray(int size) {
			return new RelatedContent[size];
		}

		@Override
		protected void setField(
			RelatedContent relatedContent, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contentType")) {
				if (jsonParserFieldValue != null) {
					relatedContent.setContentType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					relatedContent.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					relatedContent.setTitle((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}