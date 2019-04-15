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
import com.liferay.headless.delivery.client.dto.v1_0.ImageLiferayPage;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ImageLiferayPageSerDes {

	public static ImageLiferayPage toDTO(String json) {
		ImageLiferayPageJSONParser imageLiferayPageJSONParser =
			new ImageLiferayPageJSONParser();

		return imageLiferayPageJSONParser.parseToDTO(json);
	}

	public static ImageLiferayPage[] toDTOs(String json) {
		ImageLiferayPageJSONParser imageLiferayPageJSONParser =
			new ImageLiferayPageJSONParser();

		return imageLiferayPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ImageLiferayPage imageLiferayPage) {
		if (imageLiferayPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"items\": ");

		if (imageLiferayPage.getItems() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < imageLiferayPage.getItems().length; i++) {
				sb.append(ImageSerDes.toJSON(imageLiferayPage.getItems()[i]));

				if ((i + 1) < imageLiferayPage.getItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"lastPage\": ");

		if (imageLiferayPage.getLastPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(imageLiferayPage.getLastPage());
		}

		sb.append(", ");

		sb.append("\"page\": ");

		if (imageLiferayPage.getPage() == null) {
			sb.append("null");
		}
		else {
			sb.append(imageLiferayPage.getPage());
		}

		sb.append(", ");

		sb.append("\"pageSize\": ");

		if (imageLiferayPage.getPageSize() == null) {
			sb.append("null");
		}
		else {
			sb.append(imageLiferayPage.getPageSize());
		}

		sb.append(", ");

		sb.append("\"totalCount\": ");

		if (imageLiferayPage.getTotalCount() == null) {
			sb.append("null");
		}
		else {
			sb.append(imageLiferayPage.getTotalCount());
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ImageLiferayPageJSONParser
		extends BaseJSONParser<ImageLiferayPage> {

		protected ImageLiferayPage createDTO() {
			return new ImageLiferayPage();
		}

		protected ImageLiferayPage[] createDTOArray(int size) {
			return new ImageLiferayPage[size];
		}

		protected void setField(
			ImageLiferayPage imageLiferayPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "items")) {
				if (jsonParserFieldValue != null) {
					imageLiferayPage.setItems(
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
					imageLiferayPage.setLastPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "page")) {
				if (jsonParserFieldValue != null) {
					imageLiferayPage.setPage(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSize")) {
				if (jsonParserFieldValue != null) {
					imageLiferayPage.setPageSize(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalCount")) {
				if (jsonParserFieldValue != null) {
					imageLiferayPage.setTotalCount(
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