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

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

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

		if (adaptedImage.getContentUrl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentUrl\": ");

			sb.append("\"");

			sb.append(_escape(adaptedImage.getContentUrl()));

			sb.append("\"");
		}

		if (adaptedImage.getHeight() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"height\": ");

			sb.append(adaptedImage.getHeight());
		}

		if (adaptedImage.getResolutionName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"resolutionName\": ");

			sb.append("\"");

			sb.append(_escape(adaptedImage.getResolutionName()));

			sb.append("\"");
		}

		if (adaptedImage.getSizeInBytes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sizeInBytes\": ");

			sb.append(adaptedImage.getSizeInBytes());
		}

		if (adaptedImage.getWidth() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"width\": ");

			sb.append(adaptedImage.getWidth());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AdaptedImageJSONParser adaptedImageJSONParser =
			new AdaptedImageJSONParser();

		return adaptedImageJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(AdaptedImage adaptedImage) {
		if (adaptedImage == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

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

	public static class AdaptedImageJSONParser
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

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		string = string.replace("\\", "\\\\");

		return string.replace("\"", "\\\"");
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}