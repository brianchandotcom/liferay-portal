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

import com.liferay.headless.delivery.client.dto.v1_0.FragmentFieldImage;
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
public class FragmentFieldImageSerDes {

	public static FragmentFieldImage toDTO(String json) {
		FragmentFieldImageJSONParser fragmentFieldImageJSONParser =
			new FragmentFieldImageJSONParser();

		return fragmentFieldImageJSONParser.parseToDTO(json);
	}

	public static FragmentFieldImage[] toDTOs(String json) {
		FragmentFieldImageJSONParser fragmentFieldImageJSONParser =
			new FragmentFieldImageJSONParser();

		return fragmentFieldImageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FragmentFieldImage fragmentFieldImage) {
		if (fragmentFieldImage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (fragmentFieldImage.getFragmentImage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentImage\": ");

			sb.append(String.valueOf(fragmentFieldImage.getFragmentImage()));
		}

		if (fragmentFieldImage.getFragmentLink() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentLink\": ");

			sb.append(String.valueOf(fragmentFieldImage.getFragmentLink()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FragmentFieldImageJSONParser fragmentFieldImageJSONParser =
			new FragmentFieldImageJSONParser();

		return fragmentFieldImageJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		FragmentFieldImage fragmentFieldImage) {

		if (fragmentFieldImage == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (fragmentFieldImage.getFragmentImage() == null) {
			map.put("fragmentImage", null);
		}
		else {
			map.put(
				"fragmentImage",
				String.valueOf(fragmentFieldImage.getFragmentImage()));
		}

		if (fragmentFieldImage.getFragmentLink() == null) {
			map.put("fragmentLink", null);
		}
		else {
			map.put(
				"fragmentLink",
				String.valueOf(fragmentFieldImage.getFragmentLink()));
		}

		return map;
	}

	public static class FragmentFieldImageJSONParser
		extends BaseJSONParser<FragmentFieldImage> {

		@Override
		protected FragmentFieldImage createDTO() {
			return new FragmentFieldImage();
		}

		@Override
		protected FragmentFieldImage[] createDTOArray(int size) {
			return new FragmentFieldImage[size];
		}

		@Override
		protected void setField(
			FragmentFieldImage fragmentFieldImage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "fragmentImage")) {
				if (jsonParserFieldValue != null) {
					fragmentFieldImage.setFragmentImage(
						FragmentImageSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fragmentLink")) {
				if (jsonParserFieldValue != null) {
					fragmentFieldImage.setFragmentLink(
						FragmentLinkSerDes.toDTO((String)jsonParserFieldValue));
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