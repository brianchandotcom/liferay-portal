/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.serdes.v1_0;

import com.liferay.headless.admin.site.client.dto.v1_0.ActionFragmentEditableElementValue;
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
public class ActionFragmentEditableElementValueSerDes {

	public static ActionFragmentEditableElementValue toDTO(String json) {
		ActionFragmentEditableElementValueJSONParser
			actionFragmentEditableElementValueJSONParser =
				new ActionFragmentEditableElementValueJSONParser();

		return actionFragmentEditableElementValueJSONParser.parseToDTO(json);
	}

	public static ActionFragmentEditableElementValue[] toDTOs(String json) {
		ActionFragmentEditableElementValueJSONParser
			actionFragmentEditableElementValueJSONParser =
				new ActionFragmentEditableElementValueJSONParser();

		return actionFragmentEditableElementValueJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ActionFragmentEditableElementValue actionFragmentEditableElementValue) {

		if (actionFragmentEditableElementValue == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (actionFragmentEditableElementValue.getAction() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"action\": ");

			if (actionFragmentEditableElementValue.getAction() instanceof
					String) {

				sb.append("\"");
				sb.append(
					(String)actionFragmentEditableElementValue.getAction());
				sb.append("\"");
			}
			else {
				sb.append(actionFragmentEditableElementValue.getAction());
			}
		}

		if (actionFragmentEditableElementValue.getOnError() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"onError\": ");

			sb.append(
				String.valueOf(
					actionFragmentEditableElementValue.getOnError()));
		}

		if (actionFragmentEditableElementValue.getOnSuccess() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"onSuccess\": ");

			sb.append(
				String.valueOf(
					actionFragmentEditableElementValue.getOnSuccess()));
		}

		if (actionFragmentEditableElementValue.getText() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"text\": ");

			if (actionFragmentEditableElementValue.getText() instanceof
					String) {

				sb.append("\"");
				sb.append((String)actionFragmentEditableElementValue.getText());
				sb.append("\"");
			}
			else {
				sb.append(actionFragmentEditableElementValue.getText());
			}
		}

		if (actionFragmentEditableElementValue.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");
			sb.append(actionFragmentEditableElementValue.getType());
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ActionFragmentEditableElementValueJSONParser
			actionFragmentEditableElementValueJSONParser =
				new ActionFragmentEditableElementValueJSONParser();

		return actionFragmentEditableElementValueJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ActionFragmentEditableElementValue actionFragmentEditableElementValue) {

		if (actionFragmentEditableElementValue == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (actionFragmentEditableElementValue.getAction() == null) {
			map.put("action", null);
		}
		else {
			map.put(
				"action",
				String.valueOf(actionFragmentEditableElementValue.getAction()));
		}

		if (actionFragmentEditableElementValue.getOnError() == null) {
			map.put("onError", null);
		}
		else {
			map.put(
				"onError",
				String.valueOf(
					actionFragmentEditableElementValue.getOnError()));
		}

		if (actionFragmentEditableElementValue.getOnSuccess() == null) {
			map.put("onSuccess", null);
		}
		else {
			map.put(
				"onSuccess",
				String.valueOf(
					actionFragmentEditableElementValue.getOnSuccess()));
		}

		if (actionFragmentEditableElementValue.getText() == null) {
			map.put("text", null);
		}
		else {
			map.put(
				"text",
				String.valueOf(actionFragmentEditableElementValue.getText()));
		}

		if (actionFragmentEditableElementValue.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put(
				"type",
				String.valueOf(actionFragmentEditableElementValue.getType()));
		}

		return map;
	}

	public static class ActionFragmentEditableElementValueJSONParser
		extends BaseJSONParser<ActionFragmentEditableElementValue> {

		@Override
		protected ActionFragmentEditableElementValue createDTO() {
			return new ActionFragmentEditableElementValue();
		}

		@Override
		protected ActionFragmentEditableElementValue[] createDTOArray(
			int size) {

			return new ActionFragmentEditableElementValue[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "action")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "onError")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "onSuccess")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "text")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ActionFragmentEditableElementValue
				actionFragmentEditableElementValue,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "action")) {
				if (jsonParserFieldValue != null) {
					actionFragmentEditableElementValue.setAction(
						(Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "onError")) {
				if (jsonParserFieldValue != null) {
					actionFragmentEditableElementValue.setOnError(
						ActionExecutionResultSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "onSuccess")) {
				if (jsonParserFieldValue != null) {
					actionFragmentEditableElementValue.setOnSuccess(
						ActionExecutionResultSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "text")) {
				if (jsonParserFieldValue != null) {
					actionFragmentEditableElementValue.setText(
						(Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					actionFragmentEditableElementValue.setType(
						ActionFragmentEditableElementValue.Type.create(
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