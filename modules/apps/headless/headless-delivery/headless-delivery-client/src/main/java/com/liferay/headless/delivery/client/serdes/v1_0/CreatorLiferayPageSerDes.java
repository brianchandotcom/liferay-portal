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

import com.liferay.headless.delivery.client.dto.v1_0.Creator;
import com.liferay.headless.delivery.client.dto.v1_0.CreatorLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class CreatorLiferayPageSerDes {

	public static CreatorLiferayPage toDTO(String json) {
		CreatorLiferayPageJSONParser creatorLiferayPageJSONParser =
			new CreatorLiferayPageJSONParser();

		return creatorLiferayPageJSONParser.parseToDTO(json);
	}

	public static CreatorLiferayPage[] toDTOs(String json) {
		CreatorLiferayPageJSONParser creatorLiferayPageJSONParser =
			new CreatorLiferayPageJSONParser();

		return creatorLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CreatorLiferayPage creatorLiferayPage) {
		if (creatorLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (creatorLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < creatorLiferayPage.getItems().length; i++) {
				sb.append(
					CreatorSerDes.toJSON(creatorLiferayPage.getItems()[i]));

				if ((i + 1) < creatorLiferayPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (creatorLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(creatorLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (creatorLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(creatorLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (creatorLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(creatorLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (creatorLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(creatorLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class CreatorLiferayPageJSONParser
		extends BaseJSONParser<CreatorLiferayPage> {

		protected CreatorLiferayPage createDTO() {
			return new CreatorLiferayPage();
		}

		protected CreatorLiferayPage[] createDTOArray(int size) {
			return new CreatorLiferayPage[size];
		}

		protected void setField(
			CreatorLiferayPage creatorLiferayPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					creatorLiferayPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> CreatorSerDes.toDTO((String)object)
						).toArray(
							size -> new Creator[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					creatorLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					creatorLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					creatorLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					creatorLiferayPage.setTotalCount(
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