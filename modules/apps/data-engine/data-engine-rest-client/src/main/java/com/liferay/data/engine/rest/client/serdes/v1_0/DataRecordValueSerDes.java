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

import com.liferay.data.engine.rest.client.dto.v1_0.DataRecordValue;
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
public class DataRecordValueSerDes {

	public static DataRecordValue toDTO(String json) {
		DataRecordValueJSONParser dataRecordValueJSONParser =
			new DataRecordValueJSONParser();

		return dataRecordValueJSONParser.parseToDTO(json);
	}

	public static DataRecordValue[] toDTOs(String json) {
		DataRecordValueJSONParser dataRecordValueJSONParser =
			new DataRecordValueJSONParser();

		return dataRecordValueJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataRecordValue dataRecordValue) {
		if (dataRecordValue == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataRecordValue.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\":");

			sb.append("\"");

			sb.append(_escape(dataRecordValue.getKey()));

			sb.append("\"");
		}

		if (dataRecordValue.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\":");

			sb.append("\"");

			sb.append(_escape(dataRecordValue.getValue()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(DataRecordValue dataRecordValue) {
		if (dataRecordValue == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (dataRecordValue.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(dataRecordValue.getKey()));
		}

		if (dataRecordValue.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(dataRecordValue.getValue()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class DataRecordValueJSONParser
		extends BaseJSONParser<DataRecordValue> {

		@Override
		protected DataRecordValue createDTO() {
			return new DataRecordValue();
		}

		@Override
		protected DataRecordValue[] createDTOArray(int size) {
			return new DataRecordValue[size];
		}

		@Override
		protected void setField(
			DataRecordValue dataRecordValue, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					dataRecordValue.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					dataRecordValue.setValue((Object)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}