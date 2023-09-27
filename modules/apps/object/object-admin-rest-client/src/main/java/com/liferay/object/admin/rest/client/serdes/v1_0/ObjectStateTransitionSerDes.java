/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.client.serdes.v1_0;

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectStateTransition;
import com.liferay.object.admin.rest.client.json.BaseJSONParser;

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
public class ObjectStateTransitionSerDes {

	public static ObjectStateTransition toDTO(String json) {
		ObjectStateTransitionJSONParser objectStateTransitionJSONParser =
			new ObjectStateTransitionJSONParser();

		return objectStateTransitionJSONParser.parseToDTO(json);
	}

	public static ObjectStateTransition[] toDTOs(String json) {
		ObjectStateTransitionJSONParser objectStateTransitionJSONParser =
			new ObjectStateTransitionJSONParser();

		return objectStateTransitionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ObjectStateTransition objectStateTransition) {
		if (objectStateTransition == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (objectStateTransition.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(objectStateTransition.getKey()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ObjectStateTransitionJSONParser objectStateTransitionJSONParser =
			new ObjectStateTransitionJSONParser();

		return objectStateTransitionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ObjectStateTransition objectStateTransition) {

		if (objectStateTransition == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (objectStateTransition.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(objectStateTransition.getKey()));
		}

		return map;
	}

	public static class ObjectStateTransitionJSONParser
		extends BaseJSONParser<ObjectStateTransition> {

		@Override
		protected ObjectStateTransition createDTO() {
			return new ObjectStateTransition();
		}

		@Override
		protected ObjectStateTransition[] createDTOArray(int size) {
			return new ObjectStateTransition[size];
		}

		@Override
		protected void setField(
			ObjectStateTransition objectStateTransition,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					objectStateTransition.setKey((String)jsonParserFieldValue);
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