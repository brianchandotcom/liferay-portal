/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.serdes.v1_0;

import com.liferay.exportimport.rest.client.dto.v1_0.PortletDataHandlerControl;
import com.liferay.exportimport.rest.client.dto.v1_0.PortletDataHandlerSetting;
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
public class PortletDataHandlerSettingSerDes {

	public static PortletDataHandlerSetting toDTO(String json) {
		PortletDataHandlerSettingJSONParser
			portletDataHandlerSettingJSONParser =
				new PortletDataHandlerSettingJSONParser();

		return portletDataHandlerSettingJSONParser.parseToDTO(json);
	}

	public static PortletDataHandlerSetting[] toDTOs(String json) {
		PortletDataHandlerSettingJSONParser
			portletDataHandlerSettingJSONParser =
				new PortletDataHandlerSettingJSONParser();

		return portletDataHandlerSettingJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		PortletDataHandlerSetting portletDataHandlerSetting) {

		if (portletDataHandlerSetting == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (portletDataHandlerSetting.getDefaultState() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultState\": ");

			sb.append(portletDataHandlerSetting.getDefaultState());
		}

		if (portletDataHandlerSetting.getPortletDataHandlerControls() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"portletDataHandlerControls\": ");

			sb.append("[");

			for (int i = 0;
				 i < portletDataHandlerSetting.
					 getPortletDataHandlerControls().length;
				 i++) {

				sb.append(
					String.valueOf(
						portletDataHandlerSetting.
							getPortletDataHandlerControls()[i]));

				if ((i + 1) < portletDataHandlerSetting.
						getPortletDataHandlerControls().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (portletDataHandlerSetting.getDisabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"disabled\": ");

			sb.append(portletDataHandlerSetting.getDisabled());
		}

		if (portletDataHandlerSetting.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append("\"");

			sb.append(_escape(portletDataHandlerSetting.getLabel()));

			sb.append("\"");
		}

		if (portletDataHandlerSetting.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(portletDataHandlerSetting.getName()));

			sb.append("\"");
		}

		if (portletDataHandlerSetting.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");
			sb.append(portletDataHandlerSetting.getType());
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PortletDataHandlerSettingJSONParser
			portletDataHandlerSettingJSONParser =
				new PortletDataHandlerSettingJSONParser();

		return portletDataHandlerSettingJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PortletDataHandlerSetting portletDataHandlerSetting) {

		if (portletDataHandlerSetting == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (portletDataHandlerSetting.getDefaultState() == null) {
			map.put("defaultState", null);
		}
		else {
			map.put(
				"defaultState",
				String.valueOf(portletDataHandlerSetting.getDefaultState()));
		}

		if (portletDataHandlerSetting.getPortletDataHandlerControls() == null) {
			map.put("portletDataHandlerControls", null);
		}
		else {
			map.put(
				"portletDataHandlerControls",
				String.valueOf(
					portletDataHandlerSetting.getPortletDataHandlerControls()));
		}

		if (portletDataHandlerSetting.getDisabled() == null) {
			map.put("disabled", null);
		}
		else {
			map.put(
				"disabled",
				String.valueOf(portletDataHandlerSetting.getDisabled()));
		}

		if (portletDataHandlerSetting.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put(
				"label", String.valueOf(portletDataHandlerSetting.getLabel()));
		}

		if (portletDataHandlerSetting.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put(
				"name", String.valueOf(portletDataHandlerSetting.getName()));
		}

		if (portletDataHandlerSetting.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put(
				"type", String.valueOf(portletDataHandlerSetting.getType()));
		}

		return map;
	}

	public static class PortletDataHandlerSettingJSONParser
		extends BaseJSONParser<PortletDataHandlerSetting> {

		@Override
		protected PortletDataHandlerSetting createDTO() {
			return new PortletDataHandlerSetting();
		}

		@Override
		protected PortletDataHandlerSetting[] createDTOArray(int size) {
			return new PortletDataHandlerSetting[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "defaultState")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "portletDataHandlerControls")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "disabled")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			PortletDataHandlerSetting portletDataHandlerSetting,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "defaultState")) {
				if (jsonParserFieldValue != null) {
					portletDataHandlerSetting.setDefaultState(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "portletDataHandlerControls")) {

				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					PortletDataHandlerControl[]
						portletDataHandlerControlsArray =
							new PortletDataHandlerControl
								[jsonParserFieldValues.length];

					for (int i = 0; i < portletDataHandlerControlsArray.length;
						 i++) {

						portletDataHandlerControlsArray[i] =
							PortletDataHandlerControlSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					portletDataHandlerSetting.setPortletDataHandlerControls(
						portletDataHandlerControlsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "disabled")) {
				if (jsonParserFieldValue != null) {
					portletDataHandlerSetting.setDisabled(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					portletDataHandlerSetting.setLabel(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					portletDataHandlerSetting.setName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					portletDataHandlerSetting.setType(
						PortletDataHandlerSetting.Type.create(
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
// LIFERAY-REST-BUILDER-HASH:1176425483