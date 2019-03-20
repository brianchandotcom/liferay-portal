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

package com.liferay.headless.web.experience.client.serdes.v1_0;

import com.liferay.headless.web.experience.client.dto.v1_0.ContentListElement;
import com.liferay.headless.web.experience.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentListElementSerDes {

	public static ContentListElement toDTO(String json) {
		ContentListElementJSONParser contentListElementJSONParser =
			new ContentListElementJSONParser();

		return contentListElementJSONParser.parseToDTO(json);
	}

	public static ContentListElement[] toDTOs(String json) {
		ContentListElementJSONParser contentListElementJSONParser =
			new ContentListElementJSONParser();

		return contentListElementJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContentListElement contentListElement) {
		if (contentListElement == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"contentType\": ");

		sb.append("\"");
		sb.append(contentListElement.getContentType());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"order\": ");

		sb.append(contentListElement.getOrder());

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(
		Collection<ContentListElement> contentListElements) {

		if (contentListElements == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (ContentListElement contentListElement : contentListElements) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(contentListElement));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class ContentListElementJSONParser
		extends BaseJSONParser<ContentListElement> {

		protected ContentListElement createDTO() {
			return new ContentListElement();
		}

		protected ContentListElement[] createDTOArray(int size) {
			return new ContentListElement[size];
		}

		protected void setField(
			ContentListElement contentListElement, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contentType")) {
				if (jsonParserFieldValue != null) {
					contentListElement.setContentType(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "order")) {
				if (jsonParserFieldValue != null) {
					contentListElement.setOrder((Number)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}