/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.serdes.v1_0;

import com.liferay.exportimport.rest.client.dto.v1_0.PortletDataHandlerControl;
import com.liferay.exportimport.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class PortletDataHandlerControlSerDes {

	public static PortletDataHandlerControl toDTO(String json) {
		PortletDataHandlerControlJSONParser
			portletDataHandlerControlJSONParser =
				new PortletDataHandlerControlJSONParser();

		return portletDataHandlerControlJSONParser.parseToDTO(json);
	}

	public static PortletDataHandlerControl[] toDTOs(String json) {
		PortletDataHandlerControlJSONParser
			portletDataHandlerControlJSONParser =
				new PortletDataHandlerControlJSONParser();

		return portletDataHandlerControlJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		PortletDataHandlerControl portletDataHandlerControl) {

		if (portletDataHandlerControl == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (portletDataHandlerControl.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(portletDataHandlerControl.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PortletDataHandlerControlJSONParser
			portletDataHandlerControlJSONParser =
				new PortletDataHandlerControlJSONParser();

		return portletDataHandlerControlJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PortletDataHandlerControl portletDataHandlerControl) {

		if (portletDataHandlerControl == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (portletDataHandlerControl.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put(
				"name", String.valueOf(portletDataHandlerControl.getName()));
		}

		return map;
	}

	public static class PortletDataHandlerControlJSONParser
		extends BaseJSONParser<PortletDataHandlerControl> {

		@Override
		protected PortletDataHandlerControl createDTO() {
			return new PortletDataHandlerControl();
		}

		@Override
		protected PortletDataHandlerControl[] createDTOArray(int size) {
			return new PortletDataHandlerControl[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			PortletDataHandlerControl portletDataHandlerControl,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					portletDataHandlerControl.setName(
						(String)jsonParserFieldValue);
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
// LIFERAY-REST-BUILDER-HASH:147882684