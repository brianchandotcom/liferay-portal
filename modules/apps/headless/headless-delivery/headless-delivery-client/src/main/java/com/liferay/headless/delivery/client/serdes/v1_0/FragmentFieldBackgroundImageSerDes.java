/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		if (fragmentFieldBackgroundImage.getBackgroundFragmentImage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"backgroundFragmentImage\": ");

			sb.append(
				String.valueOf(
					fragmentFieldBackgroundImage.getBackgroundFragmentImage()));
		}

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

		if (fragmentFieldBackgroundImage.getBackgroundFragmentImage() == null) {
			map.put("backgroundFragmentImage", null);
		}
		else {
			map.put(
				"backgroundFragmentImage",
				String.valueOf(
					fragmentFieldBackgroundImage.getBackgroundFragmentImage()));
		}

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

			if (Objects.equals(
					jsonParserFieldName, "backgroundFragmentImage")) {

				if (jsonParserFieldValue != null) {
					fragmentFieldBackgroundImage.setBackgroundFragmentImage(
						FragmentImageSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "backgroundImage")) {
				if (jsonParserFieldValue != null) {
					fragmentFieldBackgroundImage.setBackgroundImage(
						BackgroundImageSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
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
			sb.append("\": ");

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
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}