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

import java.util.Collection;
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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"content\": ");

		sb.append(contentSetElement.getContent());
		sb.append(", ");

		sb.append("\"contentType\": ");

		sb.append("\"");
		sb.append(contentSetElement.getContentType());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"order\": ");

		sb.append(contentSetElement.getOrder());
		sb.append(", ");

		sb.append("\"title\": ");

		sb.append("\"");
		sb.append(contentSetElement.getTitle());
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(
		Collection<ContentSetElement> contentSetElements) {

		if (contentSetElements == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (ContentSetElement contentSetElement : contentSetElements) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(contentSetElement));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class ContentSetElementJSONParser
		extends BaseJSONParser<ContentSetElement> {

		protected ContentSetElement createDTO() {
			return new ContentSetElement();
		}

		protected ContentSetElement[] createDTOArray(int size) {
			return new ContentSetElement[size];
		}

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
			else if (Objects.equals(jsonParserFieldName, "order")) {
				if (jsonParserFieldValue != null) {
					contentSetElement.setOrder((Number)jsonParserFieldValue);
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