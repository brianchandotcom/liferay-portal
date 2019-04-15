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
import com.liferay.headless.delivery.client.dto.v1_0.AdaptedImagePage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class AdaptedImagePageSerDes {

	public static AdaptedImagePage toDTO(String json) {
		AdaptedImagePageJSONParser adaptedImagePageJSONParser =
			new AdaptedImagePageJSONParser();

		return adaptedImagePageJSONParser.parseToDTO(json);
	}

	public static AdaptedImagePage[] toDTOs(String json) {
		AdaptedImagePageJSONParser adaptedImagePageJSONParser =
			new AdaptedImagePageJSONParser();

		return adaptedImagePageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AdaptedImagePage adaptedImagePage) {
		if (adaptedImagePage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (adaptedImagePage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < adaptedImagePage.getItems().length; i++) {
				sb.append(
					AdaptedImageSerDes.toJSON(adaptedImagePage.getItems()[i]));

				if ((i + 1) < adaptedImagePage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (adaptedImagePage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(adaptedImagePage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (adaptedImagePage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(adaptedImagePage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (adaptedImagePage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(adaptedImagePage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (adaptedImagePage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(adaptedImagePage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class AdaptedImagePageJSONParser
		extends BaseJSONParser<AdaptedImagePage> {

		protected AdaptedImagePage createDTO() {
			return new AdaptedImagePage();
		}

		protected AdaptedImagePage[] createDTOArray(int size) {
			return new AdaptedImagePage[size];
		}

		protected void setField(
			AdaptedImagePage adaptedImagePage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					adaptedImagePage.setItems(
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
					adaptedImagePage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					adaptedImagePage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					adaptedImagePage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					adaptedImagePage.setTotalCount(
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