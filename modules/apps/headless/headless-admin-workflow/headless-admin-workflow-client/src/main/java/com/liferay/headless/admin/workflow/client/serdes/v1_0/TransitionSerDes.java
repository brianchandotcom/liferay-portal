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

package com.liferay.headless.admin.workflow.client.serdes.v1_0;

import com.liferay.headless.admin.workflow.client.dto.v1_0.Transition;
import com.liferay.headless.admin.workflow.client.json.BaseJSONParser;

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
public class TransitionSerDes {

	public static Transition toDTO(String json) {
		TransitionJSONParser transitionJSONParser = new TransitionJSONParser();

		return transitionJSONParser.parseToDTO(json);
	}

	public static Transition[] toDTOs(String json) {
		TransitionJSONParser transitionJSONParser = new TransitionJSONParser();

		return transitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Transition transition) {
		if (transition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (transition.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append("\"");

			sb.append(_escape(transition.getLabel()));

			sb.append("\"");
		}

		if (transition.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(transition.getName()));

			sb.append("\"");
		}

		if (transition.getSourceNodeName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sourceNodeName\": ");

			sb.append("\"");

			sb.append(_escape(transition.getSourceNodeName()));

			sb.append("\"");
		}

		if (transition.getTargetNodeName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"targetNodeName\": ");

			sb.append("\"");

			sb.append(_escape(transition.getTargetNodeName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		TransitionJSONParser transitionJSONParser = new TransitionJSONParser();

		return transitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Transition transition) {
		if (transition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (transition.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put("label", String.valueOf(transition.getLabel()));
		}

		if (transition.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(transition.getName()));
		}

		if (transition.getSourceNodeName() == null) {
			map.put("sourceNodeName", null);
		}
		else {
			map.put(
				"sourceNodeName",
				String.valueOf(transition.getSourceNodeName()));
		}

		if (transition.getTargetNodeName() == null) {
			map.put("targetNodeName", null);
		}
		else {
			map.put(
				"targetNodeName",
				String.valueOf(transition.getTargetNodeName()));
		}

		return map;
	}

	public static class TransitionJSONParser
		extends BaseJSONParser<Transition> {

		@Override
		protected Transition createDTO() {
			return new Transition();
		}

		@Override
		protected Transition[] createDTOArray(int size) {
			return new Transition[size];
		}

		@Override
		protected void setField(
			Transition transition, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					transition.setLabel((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					transition.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "sourceNodeName")) {
				if (jsonParserFieldValue != null) {
					transition.setSourceNodeName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "targetNodeName")) {
				if (jsonParserFieldValue != null) {
					transition.setTargetNodeName((String)jsonParserFieldValue);
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