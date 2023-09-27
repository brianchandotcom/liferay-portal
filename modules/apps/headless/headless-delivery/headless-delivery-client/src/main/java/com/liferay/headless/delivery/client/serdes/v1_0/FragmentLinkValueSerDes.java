/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.FragmentLinkValue;
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
public class FragmentLinkValueSerDes {

	public static FragmentLinkValue toDTO(String json) {
		FragmentLinkValueJSONParser fragmentLinkValueJSONParser =
			new FragmentLinkValueJSONParser();

		return fragmentLinkValueJSONParser.parseToDTO(json);
	}

	public static FragmentLinkValue[] toDTOs(String json) {
		FragmentLinkValueJSONParser fragmentLinkValueJSONParser =
			new FragmentLinkValueJSONParser();

		return fragmentLinkValueJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FragmentLinkValue fragmentLinkValue) {
		if (fragmentLinkValue == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (fragmentLinkValue.getHref() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"href\": ");

			if (fragmentLinkValue.getHref() instanceof String) {
				sb.append("\"");
				sb.append((String)fragmentLinkValue.getHref());
				sb.append("\"");
			}
			else {
				sb.append(fragmentLinkValue.getHref());
			}
		}

		if (fragmentLinkValue.getTarget() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"target\": ");

			sb.append("\"");

			sb.append(fragmentLinkValue.getTarget());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FragmentLinkValueJSONParser fragmentLinkValueJSONParser =
			new FragmentLinkValueJSONParser();

		return fragmentLinkValueJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		FragmentLinkValue fragmentLinkValue) {

		if (fragmentLinkValue == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (fragmentLinkValue.getHref() == null) {
			map.put("href", null);
		}
		else {
			map.put("href", String.valueOf(fragmentLinkValue.getHref()));
		}

		if (fragmentLinkValue.getTarget() == null) {
			map.put("target", null);
		}
		else {
			map.put("target", String.valueOf(fragmentLinkValue.getTarget()));
		}

		return map;
	}

	public static class FragmentLinkValueJSONParser
		extends BaseJSONParser<FragmentLinkValue> {

		@Override
		protected FragmentLinkValue createDTO() {
			return new FragmentLinkValue();
		}

		@Override
		protected FragmentLinkValue[] createDTOArray(int size) {
			return new FragmentLinkValue[size];
		}

		@Override
		protected void setField(
			FragmentLinkValue fragmentLinkValue, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "href")) {
				if (jsonParserFieldValue != null) {
					fragmentLinkValue.setHref((Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "target")) {
				if (jsonParserFieldValue != null) {
					fragmentLinkValue.setTarget(
						FragmentLinkValue.Target.create(
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