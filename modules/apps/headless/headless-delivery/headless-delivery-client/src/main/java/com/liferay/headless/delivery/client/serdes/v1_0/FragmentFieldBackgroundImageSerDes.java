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

import com.liferay.headless.delivery.client.dto.v1_0.FragmentFieldBackgroundImage;
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
public class FragmentFieldBackgroundImageSerDes {

	public static FragmentFieldBackgroundImage toDTO(String json) {
		FragmentFieldBackgroundImageJSONParser
			fragmentFieldBackgroundImageJSONParser =
				new FragmentFieldBackgroundImageJSONParser();

		return fragmentFieldBackgroundImageJSONParser.parseToDTO(json);
	}

	public static FragmentFieldBackgroundImage[] toDTOs(String json) {
		FragmentFieldBackgroundImageJSONParser
			fragmentFieldBackgroundImageJSONParser =
				new FragmentFieldBackgroundImageJSONParser();

		return fragmentFieldBackgroundImageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		FragmentFieldBackgroundImage fragmentFieldBackgroundImage) {

		if (fragmentFieldBackgroundImage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (fragmentFieldBackgroundImage.getBackgroundImage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"backgroundImage\": ");

			sb.append(
				String.valueOf(
					fragmentFieldBackgroundImage.getBackgroundImage()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FragmentFieldBackgroundImageJSONParser
			fragmentFieldBackgroundImageJSONParser =
				new FragmentFieldBackgroundImageJSONParser();

		return fragmentFieldBackgroundImageJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		FragmentFieldBackgroundImage fragmentFieldBackgroundImage) {

		if (fragmentFieldBackgroundImage == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (fragmentFieldBackgroundImage.getBackgroundImage() == null) {
			map.put("backgroundImage", null);
		}
		else {
			map.put(
				"backgroundImage",
				String.valueOf(
					fragmentFieldBackgroundImage.getBackgroundImage()));
		}

		return map;
	}

	public static class FragmentFieldBackgroundImageJSONParser
		extends BaseJSONParser<FragmentFieldBackgroundImage> {

		@Override
		protected FragmentFieldBackgroundImage createDTO() {
			return new FragmentFieldBackgroundImage();
		}

		@Override
		protected FragmentFieldBackgroundImage[] createDTOArray(int size) {
			return new FragmentFieldBackgroundImage[size];
		}

		@Override
		protected void setField(
			FragmentFieldBackgroundImage fragmentFieldBackgroundImage,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "backgroundImage")) {
				if (jsonParserFieldValue != null) {
					fragmentFieldBackgroundImage.setBackgroundImage(
						FragmentImageSerDes.toDTO(
							(String)jsonParserFieldValue));
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

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
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