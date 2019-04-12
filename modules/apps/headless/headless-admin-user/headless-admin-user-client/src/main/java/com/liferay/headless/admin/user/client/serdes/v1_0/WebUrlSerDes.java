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

package com.liferay.headless.admin.user.client.serdes.v1_0;

import com.liferay.headless.admin.user.client.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class WebUrlSerDes {

	public static WebUrl toDTO(String json) {
		WebUrlJSONParser webUrlJSONParser = new WebUrlJSONParser();

		return webUrlJSONParser.parseToDTO(json);
	}

	public static WebUrl[] toDTOs(String json) {
		WebUrlJSONParser webUrlJSONParser = new WebUrlJSONParser();

		return webUrlJSONParser.parseToDTOs(json);
	}

	public static String toJSON(WebUrl webUrl) {
		if (webUrl == null) {
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"id\": ");

		if (webUrl.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(webUrl.getId());
		}

		sb.append(", ");

		sb.append("\"url\": ");

		if (webUrl.getUrl() == null) {
			sb.append("null");
		}
		else {
			sb.append(webUrl.getUrl());
		}

		sb.append(", ");

		sb.append("\"urlType\": ");

		if (webUrl.getUrlType() == null) {
			sb.append("null");
		}
		else {
			sb.append(webUrl.getUrlType());
		}

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<WebUrl> webUrls) {
		if (webUrls == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (WebUrl webUrl : webUrls) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(webUrl));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class WebUrlJSONParser extends BaseJSONParser<WebUrl> {

		protected WebUrl createDTO() {
			return new WebUrl();
		}

		protected WebUrl[] createDTOArray(int size) {
			return new WebUrl[size];
		}

		protected void setField(
			WebUrl webUrl, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					webUrl.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "url")) {
				if (jsonParserFieldValue != null) {
					webUrl.setUrl((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "urlType")) {
				if (jsonParserFieldValue != null) {
					webUrl.setUrlType((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}