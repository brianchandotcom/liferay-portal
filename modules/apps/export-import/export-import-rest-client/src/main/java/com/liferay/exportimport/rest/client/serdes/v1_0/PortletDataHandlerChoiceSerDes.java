/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.serdes.v1_0;

import com.liferay.exportimport.rest.client.dto.v1_0.Choice;
import com.liferay.exportimport.rest.client.dto.v1_0.PortletDataHandlerChoice;
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
public class PortletDataHandlerChoiceSerDes {

	public static PortletDataHandlerChoice toDTO(String json) {
		PortletDataHandlerChoiceJSONParser portletDataHandlerChoiceJSONParser =
			new PortletDataHandlerChoiceJSONParser();

		return portletDataHandlerChoiceJSONParser.parseToDTO(json);
	}

	public static PortletDataHandlerChoice[] toDTOs(String json) {
		PortletDataHandlerChoiceJSONParser portletDataHandlerChoiceJSONParser =
			new PortletDataHandlerChoiceJSONParser();

		return portletDataHandlerChoiceJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		PortletDataHandlerChoice portletDataHandlerChoice) {

		if (portletDataHandlerChoice == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (portletDataHandlerChoice.getChoices() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"choices\": ");

			sb.append("[");

			for (int i = 0; i < portletDataHandlerChoice.getChoices().length;
				 i++) {

				sb.append(
					String.valueOf(portletDataHandlerChoice.getChoices()[i]));

				if ((i + 1) < portletDataHandlerChoice.getChoices().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (portletDataHandlerChoice.getDefaultChoice() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultChoice\": ");

			sb.append("\"");

			sb.append(_escape(portletDataHandlerChoice.getDefaultChoice()));

			sb.append("\"");
		}

		if (portletDataHandlerChoice.getDisabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"disabled\": ");

			sb.append(portletDataHandlerChoice.getDisabled());
		}

		if (portletDataHandlerChoice.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append("\"");

			sb.append(_escape(portletDataHandlerChoice.getLabel()));

			sb.append("\"");
		}

		if (portletDataHandlerChoice.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(portletDataHandlerChoice.getName()));

			sb.append("\"");
		}

		if (portletDataHandlerChoice.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");
			sb.append(portletDataHandlerChoice.getType());
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PortletDataHandlerChoiceJSONParser portletDataHandlerChoiceJSONParser =
			new PortletDataHandlerChoiceJSONParser();

		return portletDataHandlerChoiceJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		PortletDataHandlerChoice portletDataHandlerChoice) {

		if (portletDataHandlerChoice == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (portletDataHandlerChoice.getChoices() == null) {
			map.put("choices", null);
		}
		else {
			map.put(
				"choices",
				String.valueOf(portletDataHandlerChoice.getChoices()));
		}

		if (portletDataHandlerChoice.getDefaultChoice() == null) {
			map.put("defaultChoice", null);
		}
		else {
			map.put(
				"defaultChoice",
				String.valueOf(portletDataHandlerChoice.getDefaultChoice()));
		}

		if (portletDataHandlerChoice.getDisabled() == null) {
			map.put("disabled", null);
		}
		else {
			map.put(
				"disabled",
				String.valueOf(portletDataHandlerChoice.getDisabled()));
		}

		if (portletDataHandlerChoice.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put(
				"label", String.valueOf(portletDataHandlerChoice.getLabel()));
		}

		if (portletDataHandlerChoice.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(portletDataHandlerChoice.getName()));
		}

		if (portletDataHandlerChoice.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(portletDataHandlerChoice.getType()));
		}

		return map;
	}

	public static class PortletDataHandlerChoiceJSONParser
		extends BaseJSONParser<PortletDataHandlerChoice> {

		@Override
		protected PortletDataHandlerChoice createDTO() {
			return new PortletDataHandlerChoice();
		}

		@Override
		protected PortletDataHandlerChoice[] createDTOArray(int size) {
			return new PortletDataHandlerChoice[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "choices")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "defaultChoice")) {
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
			PortletDataHandlerChoice portletDataHandlerChoice,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "choices")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					Choice[] choicesArray =
						new Choice[jsonParserFieldValues.length];

					for (int i = 0; i < choicesArray.length; i++) {
						choicesArray[i] = ChoiceSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					portletDataHandlerChoice.setChoices(choicesArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "defaultChoice")) {
				if (jsonParserFieldValue != null) {
					portletDataHandlerChoice.setDefaultChoice(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "disabled")) {
				if (jsonParserFieldValue != null) {
					portletDataHandlerChoice.setDisabled(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					portletDataHandlerChoice.setLabel(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					portletDataHandlerChoice.setName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					portletDataHandlerChoice.setType(
						PortletDataHandlerChoice.Type.create(
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
// LIFERAY-REST-BUILDER-HASH:847267857