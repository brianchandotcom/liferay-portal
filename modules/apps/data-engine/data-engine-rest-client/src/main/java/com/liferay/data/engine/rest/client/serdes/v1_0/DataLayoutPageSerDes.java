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

import com.liferay.data.engine.rest.client.dto.v1_0.DataLayoutPage;
import com.liferay.data.engine.rest.client.dto.v1_0.DataLayoutRow;
import com.liferay.data.engine.rest.client.dto.v1_0.LocalizedValue;
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
public class DataLayoutPageSerDes {

	public static DataLayoutPage toDTO(String json) {
		DataLayoutPageJSONParser dataLayoutPageJSONParser =
			new DataLayoutPageJSONParser();

		return dataLayoutPageJSONParser.parseToDTO(json);
	}

	public static DataLayoutPage[] toDTOs(String json) {
		DataLayoutPageJSONParser dataLayoutPageJSONParser =
			new DataLayoutPageJSONParser();

		return dataLayoutPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataLayoutPage dataLayoutPage) {
		if (dataLayoutPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataLayoutPage.getDataLayoutRows() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataLayoutRows\":");

			sb.append("[");

			for (int i = 0; i < dataLayoutPage.getDataLayoutRows().length;
				 i++) {

				sb.append(
					DataLayoutRowSerDes.toJSON(
						dataLayoutPage.getDataLayoutRows()[i]));

				if ((i + 1) < dataLayoutPage.getDataLayoutRows().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (dataLayoutPage.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\":");

			sb.append("[");

			for (int i = 0; i < dataLayoutPage.getDescription().length; i++) {
				sb.append(
					LocalizedValueSerDes.toJSON(
						dataLayoutPage.getDescription()[i]));

				if ((i + 1) < dataLayoutPage.getDescription().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (dataLayoutPage.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\":");

			sb.append("[");

			for (int i = 0; i < dataLayoutPage.getTitle().length; i++) {
				sb.append(
					LocalizedValueSerDes.toJSON(dataLayoutPage.getTitle()[i]));

				if ((i + 1) < dataLayoutPage.getTitle().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(DataLayoutPage dataLayoutPage) {
		if (dataLayoutPage == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (dataLayoutPage.getDataLayoutRows() == null) {
			map.put("dataLayoutRows", null);
		}
		else {
			map.put(
				"dataLayoutRows",
				String.valueOf(dataLayoutPage.getDataLayoutRows()));
		}

		if (dataLayoutPage.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description", String.valueOf(dataLayoutPage.getDescription()));
		}

		if (dataLayoutPage.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(dataLayoutPage.getTitle()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class DataLayoutPageJSONParser
		extends BaseJSONParser<DataLayoutPage> {

		@Override
		protected DataLayoutPage createDTO() {
			return new DataLayoutPage();
		}

		@Override
		protected DataLayoutPage[] createDTOArray(int size) {
			return new DataLayoutPage[size];
		}

		@Override
		protected void setField(
			DataLayoutPage dataLayoutPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dataLayoutRows")) {
				if (jsonParserFieldValue != null) {
					dataLayoutPage.setDataLayoutRows(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> DataLayoutRowSerDes.toDTO((String)object)
						).toArray(
							size -> new DataLayoutRow[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					dataLayoutPage.setDescription(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> LocalizedValueSerDes.toDTO((String)object)
						).toArray(
							size -> new LocalizedValue[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					dataLayoutPage.setTitle(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> LocalizedValueSerDes.toDTO((String)object)
						).toArray(
							size -> new LocalizedValue[size]
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