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

import com.liferay.headless.delivery.client.dto.v1_0.FragmentImageClassPKReference;
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
public class FragmentImageClassPKReferenceSerDes {

	public static FragmentImageClassPKReference toDTO(String json) {
		FragmentImageClassPKReferenceJSONParser
			fragmentImageClassPKReferenceJSONParser =
				new FragmentImageClassPKReferenceJSONParser();

		return fragmentImageClassPKReferenceJSONParser.parseToDTO(json);
	}

	public static FragmentImageClassPKReference[] toDTOs(String json) {
		FragmentImageClassPKReferenceJSONParser
			fragmentImageClassPKReferenceJSONParser =
				new FragmentImageClassPKReferenceJSONParser();

		return fragmentImageClassPKReferenceJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		FragmentImageClassPKReference fragmentImageClassPKReference) {

		if (fragmentImageClassPKReference == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (fragmentImageClassPKReference.getImageConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"imageConfiguration\": ");

			sb.append(
				String.valueOf(
					fragmentImageClassPKReference.getImageConfiguration()));
		}

		if (fragmentImageClassPKReference.getImageReference() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"imageReference\": ");

			sb.append(
				_toJSON(fragmentImageClassPKReference.getImageReference()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FragmentImageClassPKReferenceJSONParser
			fragmentImageClassPKReferenceJSONParser =
				new FragmentImageClassPKReferenceJSONParser();

		return fragmentImageClassPKReferenceJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		FragmentImageClassPKReference fragmentImageClassPKReference) {

		if (fragmentImageClassPKReference == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (fragmentImageClassPKReference.getImageConfiguration() == null) {
			map.put("imageConfiguration", null);
		}
		else {
			map.put(
				"imageConfiguration",
				String.valueOf(
					fragmentImageClassPKReference.getImageConfiguration()));
		}

		if (fragmentImageClassPKReference.getImageReference() == null) {
			map.put("imageReference", null);
		}
		else {
			map.put(
				"imageReference",
				String.valueOf(
					fragmentImageClassPKReference.getImageReference()));
		}

		return map;
	}

	public static class FragmentImageClassPKReferenceJSONParser
		extends BaseJSONParser<FragmentImageClassPKReference> {

		@Override
		protected FragmentImageClassPKReference createDTO() {
			return new FragmentImageClassPKReference();
		}

		@Override
		protected FragmentImageClassPKReference[] createDTOArray(int size) {
			return new FragmentImageClassPKReference[size];
		}

		@Override
		protected void setField(
			FragmentImageClassPKReference fragmentImageClassPKReference,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "imageConfiguration")) {
				if (jsonParserFieldValue != null) {
					fragmentImageClassPKReference.setImageConfiguration(
						FragmentImageConfigurationSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "imageReference")) {
				if (jsonParserFieldValue != null) {
					fragmentImageClassPKReference.setImageReference(
						(Map)FragmentImageClassPKReferenceSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (jsonParserFieldName.equals("status")) {
				throw new IllegalArgumentException();
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
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}