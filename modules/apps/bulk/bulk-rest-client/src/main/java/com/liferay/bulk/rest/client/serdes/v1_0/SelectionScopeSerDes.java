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

package com.liferay.bulk.rest.client.serdes.v1_0;

import com.liferay.bulk.rest.client.dto.v1_0.SelectionScope;
import com.liferay.bulk.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class SelectionScopeSerDes {

	public static SelectionScope toDTO(String json) {
		SelectionScopeJSONParser selectionScopeJSONParser =
			new SelectionScopeJSONParser();

		return selectionScopeJSONParser.parseToDTO(json);
	}

	public static SelectionScope[] toDTOs(String json) {
		SelectionScopeJSONParser selectionScopeJSONParser =
			new SelectionScopeJSONParser();

		return selectionScopeJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SelectionScope selectionScope) {
		if (selectionScope == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (selectionScope.getFolderId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"folderId\": ");

			sb.append(selectionScope.getFolderId());
		}

		if (selectionScope.getRepositoryId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"repositoryId\": ");

			sb.append(selectionScope.getRepositoryId());
		}

		if (selectionScope.getSelectAll() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"selectAll\": ");

			sb.append(selectionScope.getSelectAll());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SelectionScopeJSONParser selectionScopeJSONParser =
			new SelectionScopeJSONParser();

		return selectionScopeJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SelectionScope selectionScope) {
		if (selectionScope == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (selectionScope.getFolderId() == null) {
			map.put("folderId", null);
		}
		else {
			map.put("folderId", String.valueOf(selectionScope.getFolderId()));
		}

		if (selectionScope.getRepositoryId() == null) {
			map.put("repositoryId", null);
		}
		else {
			map.put(
				"repositoryId",
				String.valueOf(selectionScope.getRepositoryId()));
		}

		if (selectionScope.getSelectAll() == null) {
			map.put("selectAll", null);
		}
		else {
			map.put("selectAll", String.valueOf(selectionScope.getSelectAll()));
		}

		return map;
	}

	public static class SelectionScopeJSONParser
		extends BaseJSONParser<SelectionScope> {

		@Override
		protected SelectionScope createDTO() {
			return new SelectionScope();
		}

		@Override
		protected SelectionScope[] createDTOArray(int size) {
			return new SelectionScope[size];
		}

		@Override
		protected void setField(
			SelectionScope selectionScope, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "folderId")) {
				if (jsonParserFieldValue != null) {
					selectionScope.setFolderId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "repositoryId")) {
				if (jsonParserFieldValue != null) {
					selectionScope.setRepositoryId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "selectAll")) {
				if (jsonParserFieldValue != null) {
					selectionScope.setSelectAll((Boolean)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		string = string.replace("\\", "\\\\");

		return string.replace("\"", "\\\"");
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
			sb.append("\":");

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
			else {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}