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

import com.liferay.headless.delivery.client.dto.v1_0.StructuredContentLink;
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
public class StructuredContentLinkSerDes {

	public static StructuredContentLink toDTO(String json) {
		StructuredContentLinkJSONParser structuredContentLinkJSONParser =
			new StructuredContentLinkJSONParser();

		return structuredContentLinkJSONParser.parseToDTO(json);
	}

	public static StructuredContentLink[] toDTOs(String json) {
		StructuredContentLinkJSONParser structuredContentLinkJSONParser =
			new StructuredContentLinkJSONParser();

		return structuredContentLinkJSONParser.parseToDTOs(json);
	}

	public static String toJSON(StructuredContentLink structuredContentLink) {
		if (structuredContentLink == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (structuredContentLink.getId() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"id\":");

			sb.append(structuredContentLink.getId());
		}

		if (structuredContentLink.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(",");
			}

			sb.append("\"title\":");

			sb.append("\"");

			sb.append(structuredContentLink.getTitle());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(
		StructuredContentLink structuredContentLink) {

		if (structuredContentLink == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (structuredContentLink.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(structuredContentLink.getId()));
		}

		if (structuredContentLink.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(structuredContentLink.getTitle()));
		}

		return map;
	}

	private static class StructuredContentLinkJSONParser
		extends BaseJSONParser<StructuredContentLink> {

		@Override
		protected StructuredContentLink createDTO() {
			return new StructuredContentLink();
		}

		@Override
		protected StructuredContentLink[] createDTOArray(int size) {
			return new StructuredContentLink[size];
		}

		@Override
		protected void setField(
			StructuredContentLink structuredContentLink,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					structuredContentLink.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					structuredContentLink.setTitle(
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