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
public class AdaptedImageSerDes {

	public static AdaptedImage toDTO(String json) {
		AdaptedImageJSONParser adaptedImageJSONParser =
			new AdaptedImageJSONParser();

		return adaptedImageJSONParser.parseToDTO(json);
	}

	public static AdaptedImage[] toDTOs(String json) {
		AdaptedImageJSONParser adaptedImageJSONParser =
			new AdaptedImageJSONParser();

		return adaptedImageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AdaptedImage adaptedImage) {
		if (adaptedImage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"contentUrl\": ");

		if (adaptedImage.getContentUrl() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(adaptedImage.getContentUrl());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"height\": ");

		if (adaptedImage.getHeight() == null) {
			sb.append("null");
		}
		else {
			sb.append(adaptedImage.getHeight());
		}

		sb.append(", ");

		sb.append("\"resolutionName\": ");

		if (adaptedImage.getResolutionName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(adaptedImage.getResolutionName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"sizeInBytes\": ");

		if (adaptedImage.getSizeInBytes() == null) {
			sb.append("null");
		}
		else {
			sb.append(adaptedImage.getSizeInBytes());
		}

		sb.append(", ");

		sb.append("\"width\": ");

		if (adaptedImage.getWidth() == null) {
			sb.append("null");
		}
		else {
			sb.append(adaptedImage.getWidth());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(AdaptedImage adaptedImage) {
		if (adaptedImage == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (adaptedImage.getContentUrl() == null) {
			map.put("contentUrl", null);
		}
		else {
			map.put("contentUrl", String.valueOf(adaptedImage.getContentUrl()));
		}

		if (adaptedImage.getHeight() == null) {
			map.put("height", null);
		}
		else {
			map.put("height", String.valueOf(adaptedImage.getHeight()));
		}

		if (adaptedImage.getResolutionName() == null) {
			map.put("resolutionName", null);
		}
		else {
			map.put(
				"resolutionName",
				String.valueOf(adaptedImage.getResolutionName()));
		}

		if (adaptedImage.getSizeInBytes() == null) {
			map.put("sizeInBytes", null);
		}
		else {
			map.put(
				"sizeInBytes", String.valueOf(adaptedImage.getSizeInBytes()));
		}

		if (adaptedImage.getWidth() == null) {
			map.put("width", null);
		}
		else {
			map.put("width", String.valueOf(adaptedImage.getWidth()));
		}

		return map;
	}

	private static class AdaptedImageJSONParser
		extends BaseJSONParser<AdaptedImage> {

		@Override
		protected AdaptedImage createDTO() {
			return new AdaptedImage();
		}

		@Override
		protected AdaptedImage[] createDTOArray(int size) {
			return new AdaptedImage[size];
		}

		@Override
		protected void setField(
			AdaptedImage adaptedImage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contentUrl")) {
				if (jsonParserFieldValue != null) {
					adaptedImage.setContentUrl((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "height")) {
				if (jsonParserFieldValue != null) {
					adaptedImage.setHeight(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "resolutionName")) {
				if (jsonParserFieldValue != null) {
					adaptedImage.setResolutionName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sizeInBytes")) {
				if (jsonParserFieldValue != null) {
					adaptedImage.setSizeInBytes(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "width")) {
				if (jsonParserFieldValue != null) {
					adaptedImage.setWidth(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}