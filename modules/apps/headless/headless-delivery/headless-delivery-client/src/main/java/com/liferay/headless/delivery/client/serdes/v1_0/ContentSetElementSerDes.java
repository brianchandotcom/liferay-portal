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

import com.liferay.headless.delivery.client.dto.v1_0.ContentSetElement;
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
public class ContentSetElementSerDes {

	public static ContentSetElement toDTO(String json) {
		ContentSetElementJSONParser contentSetElementJSONParser =
			new ContentSetElementJSONParser();

		return contentSetElementJSONParser.parseToDTO(json);
	}

	public static ContentSetElement[] toDTOs(String json) {
		ContentSetElementJSONParser contentSetElementJSONParser =
			new ContentSetElementJSONParser();

		return contentSetElementJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContentSetElement contentSetElement) {
		if (contentSetElement == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (contentSetElement.getContent() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"content\":");

			sb.append(contentSetElement.getContent());
		}

		if (contentSetElement.getContentType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentType\":");

			sb.append("\"");

			sb.append(contentSetElement.getContentType());

			sb.append("\"");
		}

		if (contentSetElement.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\":");

			sb.append(contentSetElement.getId());
		}

		if (contentSetElement.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\":");

			sb.append("\"");

			sb.append(contentSetElement.getTitle());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(
		ContentSetElement contentSetElement) {

		if (contentSetElement == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (contentSetElement.getContent() == null) {
			map.put("content", null);
		}
		else {
			map.put("content", String.valueOf(contentSetElement.getContent()));
		}

		if (contentSetElement.getContentType() == null) {
			map.put("contentType", null);
		}
		else {
			map.put(
				"contentType",
				String.valueOf(contentSetElement.getContentType()));
		}

		if (contentSetElement.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(contentSetElement.getId()));
		}

		if (contentSetElement.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(contentSetElement.getTitle()));
		}

		return map;
	}

	private static class ContentSetElementJSONParser
		extends BaseJSONParser<ContentSetElement> {

		@Override
		protected ContentSetElement createDTO() {
			return new ContentSetElement();
		}

		@Override
		protected ContentSetElement[] createDTOArray(int size) {
			return new ContentSetElement[size];
		}

		@Override
		protected void setField(
			ContentSetElement contentSetElement, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "content")) {
				if (jsonParserFieldValue != null) {
					contentSetElement.setContent((Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contentType")) {
				if (jsonParserFieldValue != null) {
					contentSetElement.setContentType(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					contentSetElement.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					contentSetElement.setTitle((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}