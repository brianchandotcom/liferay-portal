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
import com.liferay.headless.delivery.client.dto.v1_0.ContentSetElementLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentSetElementLiferayPageSerDes {

	public static ContentSetElementLiferayPage toDTO(String json) {
		ContentSetElementLiferayPageJSONParser
			contentSetElementLiferayPageJSONParser =
				new ContentSetElementLiferayPageJSONParser();

		return contentSetElementLiferayPageJSONParser.parseToDTO(json);
	}

	public static ContentSetElementLiferayPage[] toDTOs(String json) {
		ContentSetElementLiferayPageJSONParser
			contentSetElementLiferayPageJSONParser =
				new ContentSetElementLiferayPageJSONParser();

		return contentSetElementLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ContentSetElementLiferayPage contentSetElementLiferayPage) {

		if (contentSetElementLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (contentSetElementLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < contentSetElementLiferayPage.getItems().length;
				 i++) {

				sb.append(
					ContentSetElementSerDes.toJSON(
						contentSetElementLiferayPage.getItems()[i]));

				if ((i + 1) < contentSetElementLiferayPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (contentSetElementLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentSetElementLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (contentSetElementLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentSetElementLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (contentSetElementLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentSetElementLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (contentSetElementLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentSetElementLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ContentSetElementLiferayPageJSONParser
		extends BaseJSONParser<ContentSetElementLiferayPage> {

		protected ContentSetElementLiferayPage createDTO() {
			return new ContentSetElementLiferayPage();
		}

		protected ContentSetElementLiferayPage[] createDTOArray(int size) {
			return new ContentSetElementLiferayPage[size];
		}

		protected void setField(
			ContentSetElementLiferayPage contentSetElementLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					contentSetElementLiferayPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ContentSetElementSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new ContentSetElement[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					contentSetElementLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					contentSetElementLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					contentSetElementLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					contentSetElementLiferayPage.setTotalCount(
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