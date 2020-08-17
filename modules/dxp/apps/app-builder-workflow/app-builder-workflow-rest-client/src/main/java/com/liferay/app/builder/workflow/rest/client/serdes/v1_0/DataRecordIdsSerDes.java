/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.app.builder.workflow.rest.client.serdes.v1_0;

import com.liferay.app.builder.workflow.rest.client.dto.v1_0.DataRecordIds;
import com.liferay.app.builder.workflow.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class DataRecordIdsSerDes {

	public static DataRecordIds toDTO(String json) {
		DataRecordIdsJSONParser dataRecordIdsJSONParser =
			new DataRecordIdsJSONParser();

		return dataRecordIdsJSONParser.parseToDTO(json);
	}

	public static DataRecordIds[] toDTOs(String json) {
		DataRecordIdsJSONParser dataRecordIdsJSONParser =
			new DataRecordIdsJSONParser();

		return dataRecordIdsJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataRecordIds dataRecordIds) {
		if (dataRecordIds == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataRecordIds.getDataRecordIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataRecordIds\": ");

			sb.append("[");

			for (int i = 0; i < dataRecordIds.getDataRecordIds().length; i++) {
				sb.append(dataRecordIds.getDataRecordIds()[i]);

				if ((i + 1) < dataRecordIds.getDataRecordIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DataRecordIdsJSONParser dataRecordIdsJSONParser =
			new DataRecordIdsJSONParser();

		return dataRecordIdsJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DataRecordIds dataRecordIds) {
		if (dataRecordIds == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (dataRecordIds.getDataRecordIds() == null) {
			map.put("dataRecordIds", null);
		}
		else {
			map.put(
				"dataRecordIds",
				String.valueOf(dataRecordIds.getDataRecordIds()));
		}

		return map;
	}

	public static class DataRecordIdsJSONParser
		extends BaseJSONParser<DataRecordIds> {

		@Override
		protected DataRecordIds createDTO() {
			return new DataRecordIds();
		}

		@Override
		protected DataRecordIds[] createDTOArray(int size) {
			return new DataRecordIds[size];
		}

		@Override
		protected void setField(
			DataRecordIds dataRecordIds, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dataRecordIds")) {
				if (jsonParserFieldValue != null) {
					dataRecordIds.setDataRecordIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (jsonParserFieldName.equals("status")) {
				throw new IllegalArgumentException();
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
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}