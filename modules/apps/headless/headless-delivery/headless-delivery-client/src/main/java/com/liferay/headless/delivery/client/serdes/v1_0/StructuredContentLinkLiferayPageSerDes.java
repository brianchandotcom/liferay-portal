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
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContentLinkLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class StructuredContentLinkLiferayPageSerDes {

	public static StructuredContentLinkLiferayPage toDTO(String json) {
		StructuredContentLinkLiferayPageJSONParser
			structuredContentLinkLiferayPageJSONParser =
				new StructuredContentLinkLiferayPageJSONParser();

		return structuredContentLinkLiferayPageJSONParser.parseToDTO(json);
	}

	public static StructuredContentLinkLiferayPage[] toDTOs(String json) {
		StructuredContentLinkLiferayPageJSONParser
			structuredContentLinkLiferayPageJSONParser =
				new StructuredContentLinkLiferayPageJSONParser();

		return structuredContentLinkLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		StructuredContentLinkLiferayPage structuredContentLinkLiferayPage) {

		if (structuredContentLinkLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (structuredContentLinkLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0;
				 i < structuredContentLinkLiferayPage.getItems().length; i++) {

				sb.append(
					StructuredContentLinkSerDes.toJSON(
						structuredContentLinkLiferayPage.getItems()[i]));

				if ((i + 1) <
						structuredContentLinkLiferayPage.getItems().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (structuredContentLinkLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentLinkLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (structuredContentLinkLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentLinkLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (structuredContentLinkLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentLinkLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (structuredContentLinkLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentLinkLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class StructuredContentLinkLiferayPageJSONParser
		extends BaseJSONParser<StructuredContentLinkLiferayPage> {

		protected StructuredContentLinkLiferayPage createDTO() {
			return new StructuredContentLinkLiferayPage();
		}

		protected StructuredContentLinkLiferayPage[] createDTOArray(int size) {
			return new StructuredContentLinkLiferayPage[size];
		}

		protected void setField(
			StructuredContentLinkLiferayPage structuredContentLinkLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					structuredContentLinkLiferayPage.setItems(
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
					structuredContentLinkLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					structuredContentLinkLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					structuredContentLinkLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					structuredContentLinkLiferayPage.setTotalCount(
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