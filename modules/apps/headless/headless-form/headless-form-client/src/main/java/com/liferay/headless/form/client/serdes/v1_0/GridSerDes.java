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

import com.liferay.headless.form.client.dto.v1_0.Column;
import com.liferay.headless.form.client.dto.v1_0.Grid;
import com.liferay.headless.form.client.dto.v1_0.Row;
import com.liferay.headless.form.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

			sb.append("\"columns\":");

			sb.append("[");

			for (int i = 0; i < grid.getColumns().length; i++) {
				sb.append(ColumnSerDes.toJSON(grid.getColumns()[i]));

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

			sb.append("\"id\":");

			sb.append(grid.getId());
		}

		if (grid.getRows() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"rows\":");

			sb.append("[");

			for (int i = 0; i < grid.getRows().length; i++) {
				sb.append(RowSerDes.toJSON(grid.getRows()[i]));

				if ((i + 1) < grid.getRows().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Grid grid) {
		if (grid == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

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

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class GridJSONParser extends BaseJSONParser<Grid> {

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
							object -> ColumnSerDes.toDTO((String)object)
						).toArray(
							size -> new Column[size]
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
							object -> RowSerDes.toDTO((String)object)
						).toArray(
							size -> new Row[size]
						));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}