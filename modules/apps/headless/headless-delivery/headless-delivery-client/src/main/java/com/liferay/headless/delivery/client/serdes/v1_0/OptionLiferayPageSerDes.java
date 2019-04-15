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

import com.liferay.headless.delivery.client.dto.v1_0.Option;
import com.liferay.headless.delivery.client.dto.v1_0.OptionLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class OptionLiferayPageSerDes {

	public static OptionLiferayPage toDTO(String json) {
		OptionLiferayPageJSONParser optionLiferayPageJSONParser =
			new OptionLiferayPageJSONParser();

		return optionLiferayPageJSONParser.parseToDTO(json);
	}

	public static OptionLiferayPage[] toDTOs(String json) {
		OptionLiferayPageJSONParser optionLiferayPageJSONParser =
			new OptionLiferayPageJSONParser();

		return optionLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(OptionLiferayPage optionLiferayPage) {
		if (optionLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (optionLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < optionLiferayPage.getItems().length; i++) {
				sb.append(OptionSerDes.toJSON(optionLiferayPage.getItems()[i]));

				if ((i + 1) < optionLiferayPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (optionLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(optionLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (optionLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(optionLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (optionLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(optionLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (optionLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(optionLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class OptionLiferayPageJSONParser
		extends BaseJSONParser<OptionLiferayPage> {

		protected OptionLiferayPage createDTO() {
			return new OptionLiferayPage();
		}

		protected OptionLiferayPage[] createDTOArray(int size) {
			return new OptionLiferayPage[size];
		}

		protected void setField(
			OptionLiferayPage optionLiferayPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					optionLiferayPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> OptionSerDes.toDTO((String)object)
						).toArray(
							size -> new Option[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					optionLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					optionLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					optionLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					optionLiferayPage.setTotalCount(
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