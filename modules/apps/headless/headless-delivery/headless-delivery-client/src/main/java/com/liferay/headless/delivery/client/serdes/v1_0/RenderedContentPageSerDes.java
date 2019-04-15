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
import com.liferay.headless.delivery.client.dto.v1_0.RenderedContentPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class RenderedContentPageSerDes {

	public static RenderedContentPage toDTO(String json) {
		RenderedContentPageJSONParser renderedContentPageJSONParser =
			new RenderedContentPageJSONParser();

		return renderedContentPageJSONParser.parseToDTO(json);
	}

	public static RenderedContentPage[] toDTOs(String json) {
		RenderedContentPageJSONParser renderedContentPageJSONParser =
			new RenderedContentPageJSONParser();

		return renderedContentPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(RenderedContentPage renderedContentPage) {
		if (renderedContentPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (renderedContentPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < renderedContentPage.getItems().length; i++) {
				sb.append(
					RenderedContentSerDes.toJSON(
						renderedContentPage.getItems()[i]));

				if ((i + 1) < renderedContentPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (renderedContentPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(renderedContentPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (renderedContentPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(renderedContentPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (renderedContentPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(renderedContentPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (renderedContentPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(renderedContentPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class RenderedContentPageJSONParser
		extends BaseJSONParser<RenderedContentPage> {

		protected RenderedContentPage createDTO() {
			return new RenderedContentPage();
		}

		protected RenderedContentPage[] createDTOArray(int size) {
			return new RenderedContentPage[size];
		}

		protected void setField(
			RenderedContentPage renderedContentPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					renderedContentPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> RenderedContentSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new RenderedContent[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					renderedContentPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					renderedContentPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					renderedContentPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					renderedContentPage.setTotalCount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}