/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.client.serdes.v1_0;

import com.liferay.ai.hub.rest.client.dto.v1_0.Model;
import com.liferay.ai.hub.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Feliphe Marinho
 * @generated
 */
@Generated("")
public class ModelSerDes {

	public static Model toDTO(String json) {
		ModelJSONParser modelJSONParser = new ModelJSONParser();

		return modelJSONParser.parseToDTO(json);
	}

	public static Model[] toDTOs(String json) {
		ModelJSONParser modelJSONParser = new ModelJSONParser();

		return modelJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Model model) {
		if (model == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (model.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append("\"");

			sb.append(_escape(model.getLabel()));

			sb.append("\"");
		}

		if (model.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(model.getName()));

			sb.append("\"");
		}

		if (model.getProviderLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"providerLabel\": ");

			sb.append("\"");

			sb.append(_escape(model.getProviderLabel()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ModelJSONParser modelJSONParser = new ModelJSONParser();

		return modelJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Model model) {
		if (model == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (model.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put("label", String.valueOf(model.getLabel()));
		}

		if (model.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(model.getName()));
		}

		if (model.getProviderLabel() == null) {
			map.put("providerLabel", null);
		}
		else {
			map.put("providerLabel", String.valueOf(model.getProviderLabel()));
		}

		return map;
	}

	public static class ModelJSONParser extends BaseJSONParser<Model> {

		@Override
		protected Model createDTO() {
			return new Model();
		}

		@Override
		protected Model[] createDTOArray(int size) {
			return new Model[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "label")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "providerLabel")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			Model model, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					model.setLabel((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					model.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "providerLabel")) {
				if (jsonParserFieldValue != null) {
					model.setProviderLabel((String)jsonParserFieldValue);
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
// LIFERAY-REST-BUILDER-HASH:1816925180