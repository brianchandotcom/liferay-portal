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

import com.liferay.headless.delivery.client.dto.v1_0.Value;
import com.liferay.headless.delivery.client.dto.v1_0.ValueLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ValueLiferayPageSerDes {

	public static ValueLiferayPage toDTO(String json) {
		ValueLiferayPageJSONParser valueLiferayPageJSONParser =
			new ValueLiferayPageJSONParser();

		return valueLiferayPageJSONParser.parseToDTO(json);
	}

	public static ValueLiferayPage[] toDTOs(String json) {
		ValueLiferayPageJSONParser valueLiferayPageJSONParser =
			new ValueLiferayPageJSONParser();

		return valueLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ValueLiferayPage valueLiferayPage) {
		if (valueLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (valueLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < valueLiferayPage.getItems().length; i++) {
				sb.append(ValueSerDes.toJSON(valueLiferayPage.getItems()[i]));

				if ((i + 1) < valueLiferayPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (valueLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(valueLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (valueLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(valueLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (valueLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(valueLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (valueLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(valueLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ValueLiferayPageJSONParser
		extends BaseJSONParser<ValueLiferayPage> {

		protected ValueLiferayPage createDTO() {
			return new ValueLiferayPage();
		}

		protected ValueLiferayPage[] createDTOArray(int size) {
			return new ValueLiferayPage[size];
		}

		protected void setField(
			ValueLiferayPage valueLiferayPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					valueLiferayPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ValueSerDes.toDTO((String)object)
						).toArray(
							size -> new Value[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					valueLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					valueLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					valueLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					valueLiferayPage.setTotalCount(
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