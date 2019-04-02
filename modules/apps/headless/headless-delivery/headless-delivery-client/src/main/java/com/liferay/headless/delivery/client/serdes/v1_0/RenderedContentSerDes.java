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

import java.util.Collection;
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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"renderedContentURL\": ");

		sb.append("\"");
		sb.append(renderedContent.getRenderedContentURL());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"templateName\": ");

		sb.append("\"");
		sb.append(renderedContent.getTemplateName());
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<RenderedContent> renderedContents) {
		if (renderedContents == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (RenderedContent renderedContent : renderedContents) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(renderedContent));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class RenderedContentJSONParser
		extends BaseJSONParser<RenderedContent> {

		protected RenderedContent createDTO() {
			return new RenderedContent();
		}

		protected RenderedContent[] createDTOArray(int size) {
			return new RenderedContent[size];
		}

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