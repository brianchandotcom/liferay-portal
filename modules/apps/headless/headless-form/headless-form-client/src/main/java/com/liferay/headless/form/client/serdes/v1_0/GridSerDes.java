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

package com.liferay.headless.form.client.serdes.v1_0;

import com.liferay.headless.form.client.dto.v1_0.FormFieldOption;
import com.liferay.headless.form.client.dto.v1_0.Grid;
import com.liferay.headless.form.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class GridSerDes {

	public static Grid toDTO(String json) {
		GridJSONParser gridJSONParser = new GridJSONParser();

		return gridJSONParser.parseToDTO(json);
	}

	public static Grid[] toDTOs(String json) {
		GridJSONParser gridJSONParser = new GridJSONParser();

		return gridJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Grid grid) {
		if (grid == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (grid.getColumns() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"columns\": ");

			sb.append("[");

			for (int i = 0; i < grid.getColumns().length; i++) {
				sb.append(String.valueOf(grid.getColumns()[i]));

				if ((i + 1) < grid.getColumns().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (grid.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(grid.getId());
		}

		if (grid.getRows() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"rows\": ");

			sb.append("[");

			for (int i = 0; i < grid.getRows().length; i++) {
				sb.append(String.valueOf(grid.getRows()[i]));

				if ((i + 1) < grid.getRows().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		GridJSONParser gridJSONParser = new GridJSONParser();

		return gridJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Grid grid) {
		if (grid == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (grid.getColumns() == null) {
			map.put("columns", null);
		}
		else {
			map.put("columns", String.valueOf(grid.getColumns()));
		}

		if (grid.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(grid.getId()));
		}

		if (grid.getRows() == null) {
			map.put("rows", null);
		}
		else {
			map.put("rows", String.valueOf(grid.getRows()));
		}

		return map;
	}

	public static class GridJSONParser extends BaseJSONParser<Grid> {

		@Override
		protected Grid createDTO() {
			return new Grid();
		}

		@Override
		protected Grid[] createDTOArray(int size) {
			return new Grid[size];
		}

		@Override
		protected void setField(
			Grid grid, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "columns")) {
				if (jsonParserFieldValue != null) {
					grid.setColumns(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FormFieldOptionSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new FormFieldOption[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					grid.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "rows")) {
				if (jsonParserFieldValue != null) {
					grid.setRows(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FormFieldOptionSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new FormFieldOption[size]
						));
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