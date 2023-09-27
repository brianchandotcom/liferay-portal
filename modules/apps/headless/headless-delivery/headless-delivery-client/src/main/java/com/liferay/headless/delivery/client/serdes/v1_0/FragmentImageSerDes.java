/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.FragmentImage;
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
public class FragmentImageSerDes {

	public static FragmentImage toDTO(String json) {
		FragmentImageJSONParser fragmentImageJSONParser =
			new FragmentImageJSONParser();

		return fragmentImageJSONParser.parseToDTO(json);
	}

	public static FragmentImage[] toDTOs(String json) {
		FragmentImageJSONParser fragmentImageJSONParser =
			new FragmentImageJSONParser();

		return fragmentImageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FragmentImage fragmentImage) {
		if (fragmentImage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (fragmentImage.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			if (fragmentImage.getDescription() instanceof String) {
				sb.append("\"");
				sb.append((String)fragmentImage.getDescription());
				sb.append("\"");
			}
			else {
				sb.append(fragmentImage.getDescription());
			}
		}

		if (fragmentImage.getFragmentImageClassPKReference() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fragmentImageClassPKReference\": ");

			sb.append(
				String.valueOf(
					fragmentImage.getFragmentImageClassPKReference()));
		}

		if (fragmentImage.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			if (fragmentImage.getTitle() instanceof String) {
				sb.append("\"");
				sb.append((String)fragmentImage.getTitle());
				sb.append("\"");
			}
			else {
				sb.append(fragmentImage.getTitle());
			}
		}

		if (fragmentImage.getUrl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"url\": ");

			if (fragmentImage.getUrl() instanceof String) {
				sb.append("\"");
				sb.append((String)fragmentImage.getUrl());
				sb.append("\"");
			}
			else {
				sb.append(fragmentImage.getUrl());
			}
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FragmentImageJSONParser fragmentImageJSONParser =
			new FragmentImageJSONParser();

		return fragmentImageJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(FragmentImage fragmentImage) {
		if (fragmentImage == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (fragmentImage.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description", String.valueOf(fragmentImage.getDescription()));
		}

		if (fragmentImage.getFragmentImageClassPKReference() == null) {
			map.put("fragmentImageClassPKReference", null);
		}
		else {
			map.put(
				"fragmentImageClassPKReference",
				String.valueOf(
					fragmentImage.getFragmentImageClassPKReference()));
		}

		if (fragmentImage.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(fragmentImage.getTitle()));
		}

		if (fragmentImage.getUrl() == null) {
			map.put("url", null);
		}
		else {
			map.put("url", String.valueOf(fragmentImage.getUrl()));
		}

		return map;
	}

	public static class FragmentImageJSONParser
		extends BaseJSONParser<FragmentImage> {

		@Override
		protected FragmentImage createDTO() {
			return new FragmentImage();
		}

		@Override
		protected FragmentImage[] createDTOArray(int size) {
			return new FragmentImage[size];
		}

		@Override
		protected void setField(
			FragmentImage fragmentImage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					fragmentImage.setDescription((Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "fragmentImageClassPKReference")) {

				if (jsonParserFieldValue != null) {
					fragmentImage.setFragmentImageClassPKReference(
						FragmentImageClassPKReferenceSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					fragmentImage.setTitle((Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "url")) {
				if (jsonParserFieldValue != null) {
					fragmentImage.setUrl((Object)jsonParserFieldValue);
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