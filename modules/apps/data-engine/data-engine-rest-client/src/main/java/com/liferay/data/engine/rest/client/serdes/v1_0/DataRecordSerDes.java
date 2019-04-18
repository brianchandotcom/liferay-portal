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

import com.liferay.data.engine.rest.client.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.client.dto.v1_0.DataRecordValue;
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
public class DataRecordSerDes {

	public static DataRecord toDTO(String json) {
		DataRecordJSONParser dataRecordJSONParser = new DataRecordJSONParser();

		return dataRecordJSONParser.parseToDTO(json);
	}

	public static DataRecord[] toDTOs(String json) {
		DataRecordJSONParser dataRecordJSONParser = new DataRecordJSONParser();

		return dataRecordJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataRecord dataRecord) {
		if (dataRecord == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataRecord.getDataRecordCollectionId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataRecordCollectionId\":");

			sb.append(dataRecord.getDataRecordCollectionId());
		}

		if (dataRecord.getDataRecordValues() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataRecordValues\":");

			sb.append("[");

			for (int i = 0; i < dataRecord.getDataRecordValues().length; i++) {
				sb.append(
					DataRecordValueSerDes.toJSON(
						dataRecord.getDataRecordValues()[i]));

				if ((i + 1) < dataRecord.getDataRecordValues().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (dataRecord.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\":");

			sb.append(dataRecord.getId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(DataRecord dataRecord) {
		if (dataRecord == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (dataRecord.getDataRecordCollectionId() == null) {
			map.put("dataRecordCollectionId", null);
		}
		else {
			map.put(
				"dataRecordCollectionId",
				String.valueOf(dataRecord.getDataRecordCollectionId()));
		}

		if (dataRecord.getDataRecordValues() == null) {
			map.put("dataRecordValues", null);
		}
		else {
			map.put(
				"dataRecordValues",
				String.valueOf(dataRecord.getDataRecordValues()));
		}

		if (dataRecord.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(dataRecord.getId()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class DataRecordJSONParser
		extends BaseJSONParser<DataRecord> {

		@Override
		protected DataRecord createDTO() {
			return new DataRecord();
		}

		@Override
		protected DataRecord[] createDTOArray(int size) {
			return new DataRecord[size];
		}

		@Override
		protected void setField(
			DataRecord dataRecord, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dataRecordCollectionId")) {
				if (jsonParserFieldValue != null) {
					dataRecord.setDataRecordCollectionId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataRecordValues")) {
				if (jsonParserFieldValue != null) {
					dataRecord.setDataRecordValues(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> DataRecordValueSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new DataRecordValue[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					dataRecord.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}