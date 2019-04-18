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

import com.liferay.headless.delivery.client.dto.v1_0.RenderedContent;
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
public class RenderedContentSerDes {

	public static RenderedContent toDTO(String json) {
		RenderedContentJSONParser renderedContentJSONParser =
			new RenderedContentJSONParser();

		return renderedContentJSONParser.parseToDTO(json);
	}

	public static RenderedContent[] toDTOs(String json) {
		RenderedContentJSONParser renderedContentJSONParser =
			new RenderedContentJSONParser();

		return renderedContentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(RenderedContent renderedContent) {
		if (renderedContent == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (renderedContent.getRenderedContentURL() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"renderedContentURL\":");

			sb.append("\"");

			sb.append(_escape(renderedContent.getRenderedContentURL()));

			sb.append("\"");
		}

		if (renderedContent.getTemplateName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"templateName\":");

			sb.append("\"");

			sb.append(_escape(renderedContent.getTemplateName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(RenderedContent renderedContent) {
		if (renderedContent == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (renderedContent.getRenderedContentURL() == null) {
			map.put("renderedContentURL", null);
		}
		else {
			map.put(
				"renderedContentURL",
				String.valueOf(renderedContent.getRenderedContentURL()));
		}

		if (renderedContent.getTemplateName() == null) {
			map.put("templateName", null);
		}
		else {
			map.put(
				"templateName",
				String.valueOf(renderedContent.getTemplateName()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class RenderedContentJSONParser
		extends BaseJSONParser<RenderedContent> {

		@Override
		protected RenderedContent createDTO() {
			return new RenderedContent();
		}

		@Override
		protected RenderedContent[] createDTOArray(int size) {
			return new RenderedContent[size];
		}

		@Override
		protected void setField(
			RenderedContent renderedContent, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "renderedContentURL")) {
				if (jsonParserFieldValue != null) {
					renderedContent.setRenderedContentURL(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "templateName")) {
				if (jsonParserFieldValue != null) {
					renderedContent.setTemplateName(
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