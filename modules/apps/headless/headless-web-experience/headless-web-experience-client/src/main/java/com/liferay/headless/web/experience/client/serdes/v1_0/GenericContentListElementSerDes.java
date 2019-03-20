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

import com.liferay.headless.web.experience.client.dto.v1_0.GenericContentListElement;
import com.liferay.headless.web.experience.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class GenericContentListElementSerDes {

	public static GenericContentListElement toDTO(String json) {
		GenericContentListElementJSONParser
			genericContentListElementJSONParser =
				new GenericContentListElementJSONParser();

		return genericContentListElementJSONParser.parseToDTO(json);
	}

	public static GenericContentListElement[] toDTOs(String json) {
		GenericContentListElementJSONParser
			genericContentListElementJSONParser =
				new GenericContentListElementJSONParser();

		return genericContentListElementJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		GenericContentListElement genericContentListElement) {

		if (genericContentListElement == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"contentSpaceId\": ");

		sb.append(genericContentListElement.getContentSpaceId());
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		sb.append("\"");
		sb.append(genericContentListElement.getDateCreated());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dateModified\": ");

		sb.append("\"");
		sb.append(genericContentListElement.getDateModified());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"description\": ");

		sb.append("\"");
		sb.append(genericContentListElement.getDescription());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(genericContentListElement.getId());
		sb.append(", ");

		sb.append("\"title\": ");

		sb.append("\"");
		sb.append(genericContentListElement.getTitle());
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(
		Collection<GenericContentListElement> genericContentListElements) {

		if (genericContentListElements == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (GenericContentListElement genericContentListElement :
				genericContentListElements) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(genericContentListElement));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class GenericContentListElementJSONParser
		extends BaseJSONParser<GenericContentListElement> {

		protected GenericContentListElement createDTO() {
			return new GenericContentListElement();
		}

		protected GenericContentListElement[] createDTOArray(int size) {
			return new GenericContentListElement[size];
		}

		protected void setField(
			GenericContentListElement genericContentListElement,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contentSpaceId")) {
				if (jsonParserFieldValue != null) {
					genericContentListElement.setContentSpaceId(
						(Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					genericContentListElement.setDateCreated(
						(Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					genericContentListElement.setDateModified(
						(Date)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					genericContentListElement.setDescription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					genericContentListElement.setId((Long)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					genericContentListElement.setTitle(
						(String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}