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

package com.liferay.data.engine.rest.client.serdes.v1_0;

import com.liferay.data.engine.rest.client.dto.v1_0.DataLayoutColumn;
import com.liferay.data.engine.rest.client.dto.v1_0.DataLayoutRow;
import com.liferay.data.engine.rest.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataLayoutRowSerDes {

	public static DataLayoutRow toDTO(String json) {
		DataLayoutRowJSONParser dataLayoutRowJSONParser =
			new DataLayoutRowJSONParser();

		return dataLayoutRowJSONParser.parseToDTO(json);
	}

	public static DataLayoutRow[] toDTOs(String json) {
		DataLayoutRowJSONParser dataLayoutRowJSONParser =
			new DataLayoutRowJSONParser();

		return dataLayoutRowJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataLayoutRow dataLayoutRow) {
		if (dataLayoutRow == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataLayoutRow.getDataLayoutColums() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataLayoutColums\":");

			sb.append("[");

			for (int i = 0; i < dataLayoutRow.getDataLayoutColums().length;
				 i++) {

				sb.append(
					DataLayoutColumnSerDes.toJSON(
						dataLayoutRow.getDataLayoutColums()[i]));

				if ((i + 1) < dataLayoutRow.getDataLayoutColums().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(DataLayoutRow dataLayoutRow) {
		if (dataLayoutRow == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (dataLayoutRow.getDataLayoutColums() == null) {
			map.put("dataLayoutColums", null);
		}
		else {
			map.put(
				"dataLayoutColums",
				String.valueOf(dataLayoutRow.getDataLayoutColums()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class DataLayoutRowJSONParser
		extends BaseJSONParser<DataLayoutRow> {

		@Override
		protected DataLayoutRow createDTO() {
			return new DataLayoutRow();
		}

		@Override
		protected DataLayoutRow[] createDTOArray(int size) {
			return new DataLayoutRow[size];
		}

		@Override
		protected void setField(
			DataLayoutRow dataLayoutRow, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dataLayoutColums")) {
				if (jsonParserFieldValue != null) {
					dataLayoutRow.setDataLayoutColums(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> DataLayoutColumnSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new DataLayoutColumn[size]
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