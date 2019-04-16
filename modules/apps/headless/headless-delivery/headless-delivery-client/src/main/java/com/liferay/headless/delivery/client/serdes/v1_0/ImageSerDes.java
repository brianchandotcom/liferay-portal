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
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ImageSerDes {

	public static Image toDTO(String json) {
		ImageJSONParser imageJSONParser = new ImageJSONParser();

		return imageJSONParser.parseToDTO(json);
	}

	public static Image[] toDTOs(String json) {
		ImageJSONParser imageJSONParser = new ImageJSONParser();

		return imageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Image image) {
		if (image == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"caption\": ");

		if (image.getCaption() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(image.getCaption());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"contentUrl\": ");

		if (image.getContentUrl() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(image.getContentUrl());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"imageId\": ");

		if (image.getImageId() == null) {
			sb.append("null");
		}
		else {
			sb.append(image.getImageId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Image image) {
		if (image == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (image.getCaption() == null) {
			map.put("caption", null);
		}
		else {
			map.put("caption", String.valueOf(image.getCaption()));
		}

		if (image.getContentUrl() == null) {
			map.put("contentUrl", null);
		}
		else {
			map.put("contentUrl", String.valueOf(image.getContentUrl()));
		}

		if (image.getImageId() == null) {
			map.put("imageId", null);
		}
		else {
			map.put("imageId", String.valueOf(image.getImageId()));
		}

		return map;
	}

	private static class ImageJSONParser extends BaseJSONParser<Image> {

		@Override
		protected Image createDTO() {
			return new Image();
		}

		@Override
		protected Image[] createDTOArray(int size) {
			return new Image[size];
		}

		@Override
		protected void setField(
			Image image, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "caption")) {
				if (jsonParserFieldValue != null) {
					image.setCaption((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contentUrl")) {
				if (jsonParserFieldValue != null) {
					image.setContentUrl((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "imageId")) {
				if (jsonParserFieldValue != null) {
					image.setImageId(
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