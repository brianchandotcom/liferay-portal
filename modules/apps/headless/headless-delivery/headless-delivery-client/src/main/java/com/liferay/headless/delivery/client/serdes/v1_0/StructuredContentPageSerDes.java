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

import com.liferay.headless.delivery.client.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContentPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class StructuredContentPageSerDes {

	public static StructuredContentPage toDTO(String json) {
		StructuredContentPageJSONParser structuredContentPageJSONParser =
			new StructuredContentPageJSONParser();

		return structuredContentPageJSONParser.parseToDTO(json);
	}

	public static StructuredContentPage[] toDTOs(String json) {
		StructuredContentPageJSONParser structuredContentPageJSONParser =
			new StructuredContentPageJSONParser();

		return structuredContentPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(StructuredContentPage structuredContentPage) {
		if (structuredContentPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (structuredContentPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < structuredContentPage.getItems().length; i++) {
				sb.append(
					StructuredContentSerDes.toJSON(
						structuredContentPage.getItems()[i]));

				if ((i + 1) < structuredContentPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (structuredContentPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (structuredContentPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (structuredContentPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (structuredContentPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(structuredContentPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class StructuredContentPageJSONParser
		extends BaseJSONParser<StructuredContentPage> {

		protected StructuredContentPage createDTO() {
			return new StructuredContentPage();
		}

		protected StructuredContentPage[] createDTOArray(int size) {
			return new StructuredContentPage[size];
		}

		protected void setField(
			StructuredContentPage structuredContentPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					structuredContentPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> StructuredContentSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new StructuredContent[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					structuredContentPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					structuredContentPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					structuredContentPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					structuredContentPage.setTotalCount(
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