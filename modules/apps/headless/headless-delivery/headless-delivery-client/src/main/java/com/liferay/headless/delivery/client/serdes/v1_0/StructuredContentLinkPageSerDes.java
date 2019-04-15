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
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContentLinkPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class StructuredContentLinkPageSerDes {

	public static StructuredContentLinkPage toDTO(String json) {
		StructuredContentLinkPageJSONParser
			structuredContentLinkPageJSONParser =
				new StructuredContentLinkPageJSONParser();

		return structuredContentLinkPageJSONParser.parseToDTO(json);
	}

	public static StructuredContentLinkPage[] toDTOs(String json) {
		StructuredContentLinkPageJSONParser
			structuredContentLinkPageJSONParser =
				new StructuredContentLinkPageJSONParser();

		return structuredContentLinkPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		StructuredContentLinkPage structuredContentLinkPage) {

		if (structuredContentLinkPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (structuredContentLinkPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < structuredContentLinkPage.getItems().length;
				 i++) {

				sb.append(
					StructuredContentLinkSerDes.toJSON(
						structuredContentLinkPage.getItems()[i]));

				if ((i + 1) < structuredContentLinkPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (structuredContentLinkPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentLinkPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (structuredContentLinkPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentLinkPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (structuredContentLinkPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentLinkPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (structuredContentLinkPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentLinkPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class StructuredContentLinkPageJSONParser
		extends BaseJSONParser<StructuredContentLinkPage> {

		protected StructuredContentLinkPage createDTO() {
			return new StructuredContentLinkPage();
		}

		protected StructuredContentLinkPage[] createDTOArray(int size) {
			return new StructuredContentLinkPage[size];
		}

		protected void setField(
			StructuredContentLinkPage structuredContentLinkPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					structuredContentLinkPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> StructuredContentLinkSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new StructuredContentLink[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					structuredContentLinkPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					structuredContentLinkPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					structuredContentLinkPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					structuredContentLinkPage.setTotalCount(
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