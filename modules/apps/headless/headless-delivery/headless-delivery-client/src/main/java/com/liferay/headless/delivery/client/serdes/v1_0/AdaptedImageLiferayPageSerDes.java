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

import com.liferay.headless.delivery.client.dto.v1_0.AdaptedImage;
import com.liferay.headless.delivery.client.dto.v1_0.AdaptedImageLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class AdaptedImageLiferayPageSerDes {

	public static AdaptedImageLiferayPage toDTO(String json) {
		AdaptedImageLiferayPageJSONParser adaptedImageLiferayPageJSONParser =
			new AdaptedImageLiferayPageJSONParser();

		return adaptedImageLiferayPageJSONParser.parseToDTO(json);
	}

	public static AdaptedImageLiferayPage[] toDTOs(String json) {
		AdaptedImageLiferayPageJSONParser adaptedImageLiferayPageJSONParser =
			new AdaptedImageLiferayPageJSONParser();

		return adaptedImageLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		AdaptedImageLiferayPage adaptedImageLiferayPage) {

		if (adaptedImageLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (adaptedImageLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < adaptedImageLiferayPage.getItems().length;
				 i++) {

				sb.append(
					AdaptedImageSerDes.toJSON(
						adaptedImageLiferayPage.getItems()[i]));

				if ((i + 1) < adaptedImageLiferayPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (adaptedImageLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(adaptedImageLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (adaptedImageLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(adaptedImageLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (adaptedImageLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(adaptedImageLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (adaptedImageLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(adaptedImageLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class AdaptedImageLiferayPageJSONParser
		extends BaseJSONParser<AdaptedImageLiferayPage> {

		protected AdaptedImageLiferayPage createDTO() {
			return new AdaptedImageLiferayPage();
		}

		protected AdaptedImageLiferayPage[] createDTOArray(int size) {
			return new AdaptedImageLiferayPage[size];
		}

		protected void setField(
			AdaptedImageLiferayPage adaptedImageLiferayPage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					adaptedImageLiferayPage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> AdaptedImageSerDes.toDTO((String)object)
						).toArray(
							size -> new AdaptedImage[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					adaptedImageLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					adaptedImageLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					adaptedImageLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					adaptedImageLiferayPage.setTotalCount(
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