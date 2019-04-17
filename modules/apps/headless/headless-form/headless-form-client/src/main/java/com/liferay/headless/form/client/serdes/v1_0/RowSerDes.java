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

import com.liferay.headless.form.client.dto.v1_0.Row;
import com.liferay.headless.form.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class RowSerDes {

	public static Row toDTO(String json) {
		RowJSONParser rowJSONParser = new RowJSONParser();

		return rowJSONParser.parseToDTO(json);
	}

	public static Row[] toDTOs(String json) {
		RowJSONParser rowJSONParser = new RowJSONParser();

		return rowJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Row row) {
		if (row == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (row.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\":");

			sb.append(row.getId());
		}

		if (row.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\":");

			sb.append("\"");

			sb.append(row.getLabel());

			sb.append("\"");
		}

		if (row.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\":");

			sb.append("\"");

			sb.append(row.getValue());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Row row) {
		if (row == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (row.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(row.getId()));
		}

		if (row.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put("label", String.valueOf(row.getLabel()));
		}

		if (row.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(row.getValue()));
		}

		return map;
	}

	private static class RowJSONParser extends BaseJSONParser<Row> {

		@Override
		protected Row createDTO() {
			return new Row();
		}

		@Override
		protected Row[] createDTOArray(int size) {
			return new Row[size];
		}

		@Override
		protected void setField(
			Row row, String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					row.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					row.setLabel((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					row.setValue((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}