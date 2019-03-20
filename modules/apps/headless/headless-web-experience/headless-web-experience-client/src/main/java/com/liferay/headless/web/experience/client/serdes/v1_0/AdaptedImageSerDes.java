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

package com.liferay.headless.web.experience.client.serdes.v1_0;

import com.liferay.headless.web.experience.client.dto.v1_0.AdaptedImage;
import com.liferay.headless.web.experience.client.json.BaseJSONParser;

import java.util.Collection;
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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"contentUrl\": ");

		sb.append("\"");
		sb.append(adaptedImage.getContentUrl());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"height\": ");

		sb.append(adaptedImage.getHeight());
		sb.append(", ");

		sb.append("\"resolutionName\": ");

		sb.append("\"");
		sb.append(adaptedImage.getResolutionName());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"sizeInBytes\": ");

		sb.append(adaptedImage.getSizeInBytes());
		sb.append(", ");

		sb.append("\"width\": ");

		sb.append(adaptedImage.getWidth());

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<AdaptedImage> adaptedImages) {
		if (adaptedImages == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (AdaptedImage adaptedImage : adaptedImages) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(adaptedImage));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class AdaptedImageJSONParser
		extends BaseJSONParser<AdaptedImage> {

		protected AdaptedImage createDTO() {
			return new AdaptedImage();
		}

		protected AdaptedImage[] createDTOArray(int size) {
			return new AdaptedImage[size];
		}

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
					adaptedImage.setHeight((Number)jsonParserFieldValue);
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
					adaptedImage.setSizeInBytes((Number)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "width")) {
				if (jsonParserFieldValue != null) {
					adaptedImage.setWidth((Number)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}