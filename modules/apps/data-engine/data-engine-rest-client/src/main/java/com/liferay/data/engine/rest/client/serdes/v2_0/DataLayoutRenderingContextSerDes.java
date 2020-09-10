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

package com.liferay.data.engine.rest.client.serdes.v2_0;

import com.liferay.data.engine.rest.client.dto.v2_0.DataLayoutRenderingContext;
import com.liferay.data.engine.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataLayoutRenderingContextSerDes {

	public static DataLayoutRenderingContext toDTO(String json) {
		DataLayoutRenderingContextJSONParser
			dataLayoutRenderingContextJSONParser =
				new DataLayoutRenderingContextJSONParser();

		return dataLayoutRenderingContextJSONParser.parseToDTO(json);
	}

	public static DataLayoutRenderingContext[] toDTOs(String json) {
		DataLayoutRenderingContextJSONParser
			dataLayoutRenderingContextJSONParser =
				new DataLayoutRenderingContextJSONParser();

		return dataLayoutRenderingContextJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		DataLayoutRenderingContext dataLayoutRenderingContext) {

		if (dataLayoutRenderingContext == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataLayoutRenderingContext.getContainerId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"containerId\": ");

			sb.append("\"");

			sb.append(_escape(dataLayoutRenderingContext.getContainerId()));

			sb.append("\"");
		}

		if (dataLayoutRenderingContext.getDataRecordValues() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataRecordValues\": ");

			sb.append(
				_toJSON(dataLayoutRenderingContext.getDataRecordValues()));
		}

		if (dataLayoutRenderingContext.getNamespace() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"namespace\": ");

			sb.append("\"");

			sb.append(_escape(dataLayoutRenderingContext.getNamespace()));

			sb.append("\"");
		}

		if (dataLayoutRenderingContext.getPathThemeImages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pathThemeImages\": ");

			sb.append("\"");

			sb.append(_escape(dataLayoutRenderingContext.getPathThemeImages()));

			sb.append("\"");
		}

		if (dataLayoutRenderingContext.getReadOnly() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"readOnly\": ");

			sb.append(dataLayoutRenderingContext.getReadOnly());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DataLayoutRenderingContextJSONParser
			dataLayoutRenderingContextJSONParser =
				new DataLayoutRenderingContextJSONParser();

		return dataLayoutRenderingContextJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DataLayoutRenderingContext dataLayoutRenderingContext) {

		if (dataLayoutRenderingContext == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (dataLayoutRenderingContext.getContainerId() == null) {
			map.put("containerId", null);
		}
		else {
			map.put(
				"containerId",
				String.valueOf(dataLayoutRenderingContext.getContainerId()));
		}

		if (dataLayoutRenderingContext.getDataRecordValues() == null) {
			map.put("dataRecordValues", null);
		}
		else {
			map.put(
				"dataRecordValues",
				String.valueOf(
					dataLayoutRenderingContext.getDataRecordValues()));
		}

		if (dataLayoutRenderingContext.getNamespace() == null) {
			map.put("namespace", null);
		}
		else {
			map.put(
				"namespace",
				String.valueOf(dataLayoutRenderingContext.getNamespace()));
		}

		if (dataLayoutRenderingContext.getPathThemeImages() == null) {
			map.put("pathThemeImages", null);
		}
		else {
			map.put(
				"pathThemeImages",
				String.valueOf(
					dataLayoutRenderingContext.getPathThemeImages()));
		}

		if (dataLayoutRenderingContext.getReadOnly() == null) {
			map.put("readOnly", null);
		}
		else {
			map.put(
				"readOnly",
				String.valueOf(dataLayoutRenderingContext.getReadOnly()));
		}

		return map;
	}

	public static class DataLayoutRenderingContextJSONParser
		extends BaseJSONParser<DataLayoutRenderingContext> {

		@Override
		protected DataLayoutRenderingContext createDTO() {
			return new DataLayoutRenderingContext();
		}

		@Override
		protected DataLayoutRenderingContext[] createDTOArray(int size) {
			return new DataLayoutRenderingContext[size];
		}

		@Override
		protected void setField(
			DataLayoutRenderingContext dataLayoutRenderingContext,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "containerId")) {
				if (jsonParserFieldValue != null) {
					dataLayoutRenderingContext.setContainerId(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataRecordValues")) {
				if (jsonParserFieldValue != null) {
					dataLayoutRenderingContext.setDataRecordValues(
						(Map)DataLayoutRenderingContextSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "namespace")) {
				if (jsonParserFieldValue != null) {
					dataLayoutRenderingContext.setNamespace(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pathThemeImages")) {
				if (jsonParserFieldValue != null) {
					dataLayoutRenderingContext.setPathThemeImages(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "readOnly")) {
				if (jsonParserFieldValue != null) {
					dataLayoutRenderingContext.setReadOnly(
						(Boolean)jsonParserFieldValue);
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