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

import com.liferay.headless.delivery.client.dto.v1_0.Image;
import com.liferay.headless.delivery.client.dto.v1_0.ImagePage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ImagePageSerDes {

	public static ImagePage toDTO(String json) {
		ImagePageJSONParser imagePageJSONParser = new ImagePageJSONParser();

		return imagePageJSONParser.parseToDTO(json);
	}

	public static ImagePage[] toDTOs(String json) {
		ImagePageJSONParser imagePageJSONParser = new ImagePageJSONParser();

		return imagePageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ImagePage imagePage) {
		if (imagePage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (imagePage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < imagePage.getItems().length; i++) {
				sb.append(ImageSerDes.toJSON(imagePage.getItems()[i]));

				if ((i + 1) < imagePage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (imagePage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(imagePage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (imagePage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(imagePage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (imagePage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(imagePage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (imagePage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(imagePage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ImagePageJSONParser extends BaseJSONParser<ImagePage> {

		protected ImagePage createDTO() {
			return new ImagePage();
		}

		protected ImagePage[] createDTOArray(int size) {
			return new ImagePage[size];
		}

		protected void setField(
			ImagePage imagePage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					imagePage.setItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ImageSerDes.toDTO((String)object)
						).toArray(
							size -> new Image[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "lastPage")) {
				if (jsonParserFieldValue != null) {
					imagePage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					imagePage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					imagePage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					imagePage.setTotalCount(
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