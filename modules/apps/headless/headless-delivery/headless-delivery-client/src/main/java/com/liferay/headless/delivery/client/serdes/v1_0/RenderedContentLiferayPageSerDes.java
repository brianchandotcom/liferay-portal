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
import com.liferay.headless.delivery.client.dto.v1_0.RenderedContentLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class RenderedContentLiferayPageSerDes {

	public static RenderedContentLiferayPage toDTO(String json) {
		RenderedContentLiferayPageJSONParser
			renderedContentLiferayPageJSONParser =
				new RenderedContentLiferayPageJSONParser();

		return renderedContentLiferayPageJSONParser.parseToDTO(json);
	}

	public static RenderedContentLiferayPage[] toDTOs(String json) {
		RenderedContentLiferayPageJSONParser
			renderedContentLiferayPageJSONParser =
				new RenderedContentLiferayPageJSONParser();

		return renderedContentLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		RenderedContentLiferayPage renderedContentLiferayPage) {

		if (renderedContentLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (renderedContentLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < renderedContentLiferayPage.getItems().length;
				 i++) {

				sb.append(
					RenderedContentSerDes.toJSON(
						renderedContentLiferayPage.getItems()[i]));

				if ((i + 1) < renderedContentLiferayPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (renderedContentLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(renderedContentLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (renderedContentLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(renderedContentLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (renderedContentLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(renderedContentLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (renderedContentLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(renderedContentLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class RenderedContentLiferayPageJSONParser
		extends BaseJSONParser<RenderedContentLiferayPage> {

		protected RenderedContentLiferayPage createDTO() {
			return new RenderedContentLiferayPage();
		}

		protected RenderedContentLiferayPage[] createDTOArray(int size) {
			return new RenderedContentLiferayPage[size];
		}

		protected void setField(
			RenderedContentLiferayPage renderedContentLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					renderedContentLiferayPage.setItems(
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
					renderedContentLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					renderedContentLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					renderedContentLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					renderedContentLiferayPage.setTotalCount(
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