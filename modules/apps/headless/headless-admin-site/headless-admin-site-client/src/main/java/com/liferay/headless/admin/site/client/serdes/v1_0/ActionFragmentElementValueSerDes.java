/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.serdes.v1_0;

import com.liferay.headless.admin.site.client.dto.v1_0.ActionFragmentElementValue;
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
public class ActionFragmentElementValueSerDes {

	public static ActionFragmentElementValue toDTO(String json) {
		ActionFragmentElementValueJSONParser
			actionFragmentElementValueJSONParser =
				new ActionFragmentElementValueJSONParser();

		return actionFragmentElementValueJSONParser.parseToDTO(json);
	}

	public static ActionFragmentElementValue[] toDTOs(String json) {
		ActionFragmentElementValueJSONParser
			actionFragmentElementValueJSONParser =
				new ActionFragmentElementValueJSONParser();

		return actionFragmentElementValueJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ActionFragmentElementValue actionFragmentElementValue) {

		if (actionFragmentElementValue == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (actionFragmentElementValue.getAction() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"action\": ");

			if (actionFragmentElementValue.getAction() instanceof String) {
				sb.append("\"");
				sb.append((String)actionFragmentElementValue.getAction());
				sb.append("\"");
			}
			else {
				sb.append(actionFragmentElementValue.getAction());
			}
		}

		if (actionFragmentElementValue.getOnError() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"onError\": ");

			sb.append(String.valueOf(actionFragmentElementValue.getOnError()));
		}

		if (actionFragmentElementValue.getOnSuccess() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"onSuccess\": ");

			sb.append(
				String.valueOf(actionFragmentElementValue.getOnSuccess()));
		}

		if (actionFragmentElementValue.getText() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"text\": ");

			if (actionFragmentElementValue.getText() instanceof String) {
				sb.append("\"");
				sb.append((String)actionFragmentElementValue.getText());
				sb.append("\"");
			}
			else {
				sb.append(actionFragmentElementValue.getText());
			}
		}

		if (actionFragmentElementValue.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");
			sb.append(actionFragmentElementValue.getType());
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ActionFragmentElementValueJSONParser
			actionFragmentElementValueJSONParser =
				new ActionFragmentElementValueJSONParser();

		return actionFragmentElementValueJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ActionFragmentElementValue actionFragmentElementValue) {

		if (actionFragmentElementValue == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (actionFragmentElementValue.getAction() == null) {
			map.put("action", null);
		}
		else {
			map.put(
				"action",
				String.valueOf(actionFragmentElementValue.getAction()));
		}

		if (actionFragmentElementValue.getOnError() == null) {
			map.put("onError", null);
		}
		else {
			map.put(
				"onError",
				String.valueOf(actionFragmentElementValue.getOnError()));
		}

		if (actionFragmentElementValue.getOnSuccess() == null) {
			map.put("onSuccess", null);
		}
		else {
			map.put(
				"onSuccess",
				String.valueOf(actionFragmentElementValue.getOnSuccess()));
		}

		if (actionFragmentElementValue.getText() == null) {
			map.put("text", null);
		}
		else {
			map.put(
				"text", String.valueOf(actionFragmentElementValue.getText()));
		}

		if (actionFragmentElementValue.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put(
				"type", String.valueOf(actionFragmentElementValue.getType()));
		}

		return map;
	}

	public static class ActionFragmentElementValueJSONParser
		extends BaseJSONParser<ActionFragmentElementValue> {

		@Override
		protected ActionFragmentElementValue createDTO() {
			return new ActionFragmentElementValue();
		}

		@Override
		protected ActionFragmentElementValue[] createDTOArray(int size) {
			return new ActionFragmentElementValue[size];
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
			ActionFragmentElementValue actionFragmentElementValue,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "action")) {
				if (jsonParserFieldValue != null) {
					actionFragmentElementValue.setAction(
						(Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "onError")) {
				if (jsonParserFieldValue != null) {
					actionFragmentElementValue.setOnError(
						ActionExecutionResultSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "onSuccess")) {
				if (jsonParserFieldValue != null) {
					actionFragmentElementValue.setOnSuccess(
						ActionExecutionResultSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "text")) {
				if (jsonParserFieldValue != null) {
					actionFragmentElementValue.setText(
						(Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					actionFragmentElementValue.setType(
						ActionFragmentElementValue.Type.create(
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