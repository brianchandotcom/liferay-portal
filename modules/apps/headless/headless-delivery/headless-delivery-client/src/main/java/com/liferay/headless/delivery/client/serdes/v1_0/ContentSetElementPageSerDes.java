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
import com.liferay.headless.delivery.client.dto.v1_0.ContentSetElementPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentSetElementPageSerDes {

	public static ContentSetElementPage toDTO(String json) {
		ContentSetElementPageJSONParser contentSetElementPageJSONParser =
			new ContentSetElementPageJSONParser();

		return contentSetElementPageJSONParser.parseToDTO(json);
	}

	public static ContentSetElementPage[] toDTOs(String json) {
		ContentSetElementPageJSONParser contentSetElementPageJSONParser =
			new ContentSetElementPageJSONParser();

		return contentSetElementPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContentSetElementPage contentSetElementPage) {
		if (contentSetElementPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (contentSetElementPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < contentSetElementPage.getItems().length; i++) {
				sb.append(
					ContentSetElementSerDes.toJSON(
						contentSetElementPage.getItems()[i]));

				if ((i + 1) < contentSetElementPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (contentSetElementPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentSetElementPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (contentSetElementPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentSetElementPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (contentSetElementPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentSetElementPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (contentSetElementPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(contentSetElementPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ContentSetElementPageJSONParser
		extends BaseJSONParser<ContentSetElementPage> {

		protected ContentSetElementPage createDTO() {
			return new ContentSetElementPage();
		}

		protected ContentSetElementPage[] createDTOArray(int size) {
			return new ContentSetElementPage[size];
		}

		protected void setField(
			ContentSetElementPage contentSetElementPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					contentSetElementPage.setItems(
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
					contentSetElementPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					contentSetElementPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					contentSetElementPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					contentSetElementPage.setTotalCount(
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