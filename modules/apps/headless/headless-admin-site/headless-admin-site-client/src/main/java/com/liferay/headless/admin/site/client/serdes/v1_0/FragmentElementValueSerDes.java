/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.serdes.v1_0;

import com.liferay.headless.admin.site.client.dto.v1_0.ActionFragmentElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.BackgroundImageFragmentElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.HTMLFragmentElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.ImageFragmentElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.TextFragmentElementValue;
import com.liferay.headless.admin.site.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class FragmentElementValueSerDes {

	public static FragmentElementValue toDTO(String json) {
		FragmentElementValueJSONParser fragmentElementValueJSONParser =
			new FragmentElementValueJSONParser();

		return fragmentElementValueJSONParser.parseToDTO(json);
	}

	public static FragmentElementValue[] toDTOs(String json) {
		FragmentElementValueJSONParser fragmentElementValueJSONParser =
			new FragmentElementValueJSONParser();

		return fragmentElementValueJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FragmentElementValue fragmentElementValue) {
		if (fragmentElementValue == null) {
			return "null";
		}

		FragmentElementValue.Type type = fragmentElementValue.getType();

		if (type != null) {
			String typeString = type.toString();

			if (typeString.equals("Action")) {
				return ActionFragmentElementValueSerDes.toJSON(
					(ActionFragmentElementValue)fragmentElementValue);
			}

			if (typeString.equals("BackgroundImage")) {
				return BackgroundImageFragmentElementValueSerDes.toJSON(
					(BackgroundImageFragmentElementValue)fragmentElementValue);
			}

			if (typeString.equals("HTML")) {
				return HTMLFragmentElementValueSerDes.toJSON(
					(HTMLFragmentElementValue)fragmentElementValue);
			}

			if (typeString.equals("Image")) {
				return ImageFragmentElementValueSerDes.toJSON(
					(ImageFragmentElementValue)fragmentElementValue);
			}

			if (typeString.equals("Text")) {
				return TextFragmentElementValueSerDes.toJSON(
					(TextFragmentElementValue)fragmentElementValue);
			}

			throw new IllegalArgumentException("Unknown type " + typeString);
		}
		else {
			throw new IllegalArgumentException("Missing type parameter");
		}
	}

	public static Map<String, Object> toMap(String json) {
		FragmentElementValueJSONParser fragmentElementValueJSONParser =
			new FragmentElementValueJSONParser();

		return fragmentElementValueJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		FragmentElementValue fragmentElementValue) {

		if (fragmentElementValue == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (fragmentElementValue.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(fragmentElementValue.getType()));
		}

		return map;
	}

	public static class FragmentElementValueJSONParser
		extends BaseJSONParser<FragmentElementValue> {

		@Override
		protected FragmentElementValue createDTO() {
			return null;
		}

		@Override
		protected FragmentElementValue[] createDTOArray(int size) {
			return new FragmentElementValue[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}

			return false;
		}

		@Override
		public FragmentElementValue parseToDTO(String json) {
			Map<String, Object> jsonMap = parseToMap(json);

			Object type = jsonMap.get("type");

			if (type != null) {
				String typeString = type.toString();

				if (typeString.equals("Action")) {
					return ActionFragmentElementValue.toDTO(json);
				}

				if (typeString.equals("BackgroundImage")) {
					return BackgroundImageFragmentElementValue.toDTO(json);
				}

				if (typeString.equals("HTML")) {
					return HTMLFragmentElementValue.toDTO(json);
				}

				if (typeString.equals("Image")) {
					return ImageFragmentElementValue.toDTO(json);
				}

				if (typeString.equals("Text")) {
					return TextFragmentElementValue.toDTO(json);
				}

				throw new IllegalArgumentException(
					"Unknown type " + typeString);
			}
			else {
				throw new IllegalArgumentException("Missing type parameter");
			}
		}

		@Override
		protected void setField(
			FragmentElementValue fragmentElementValue,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					fragmentElementValue.setType(
						FragmentElementValue.Type.create(
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

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value == null) {
			return "null";
		}

		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}