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

import com.liferay.data.engine.rest.client.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataRecordCollectionSerDes {

	public static DataRecordCollection toDTO(String json) {
		DataRecordCollectionJSONParser dataRecordCollectionJSONParser =
			new DataRecordCollectionJSONParser();

		return dataRecordCollectionJSONParser.parseToDTO(json);
	}

	public static DataRecordCollection[] toDTOs(String json) {
		DataRecordCollectionJSONParser dataRecordCollectionJSONParser =
			new DataRecordCollectionJSONParser();

		return dataRecordCollectionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataRecordCollection dataRecordCollection) {
		if (dataRecordCollection == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataRecordCollection.getDataDefinitionId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataDefinitionId\": ");

			sb.append(dataRecordCollection.getDataDefinitionId());
		}

		if (dataRecordCollection.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append(dataRecordCollection.getDescription());
		}

		if (dataRecordCollection.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(dataRecordCollection.getId());
		}

		if (dataRecordCollection.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append(dataRecordCollection.getName());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(
		DataRecordCollection dataRecordCollection) {

		if (dataRecordCollection == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (dataRecordCollection.getDataDefinitionId() == null) {
			map.put("dataDefinitionId", null);
		}
		else {
			map.put(
				"dataDefinitionId",
				String.valueOf(dataRecordCollection.getDataDefinitionId()));
		}

		if (dataRecordCollection.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put(
				"description",
				String.valueOf(dataRecordCollection.getDescription()));
		}

		if (dataRecordCollection.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(dataRecordCollection.getId()));
		}

		if (dataRecordCollection.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(dataRecordCollection.getName()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class DataRecordCollectionJSONParser
		extends BaseJSONParser<DataRecordCollection> {

		@Override
		protected DataRecordCollection createDTO() {
			return new DataRecordCollection();
		}

		@Override
		protected DataRecordCollection[] createDTOArray(int size) {
			return new DataRecordCollection[size];
		}

		@Override
		protected void setField(
			DataRecordCollection dataRecordCollection,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dataDefinitionId")) {
				if (jsonParserFieldValue != null) {
					dataRecordCollection.setDataDefinitionId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					dataRecordCollection.setDescription(
						(Map<String, String>)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					dataRecordCollection.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					dataRecordCollection.setName(
						(Map<String, String>)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}