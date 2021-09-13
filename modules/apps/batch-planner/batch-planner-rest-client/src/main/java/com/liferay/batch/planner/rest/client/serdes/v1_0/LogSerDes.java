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

package com.liferay.batch.planner.rest.client.serdes.v1_0;

import com.liferay.batch.planner.rest.client.dto.v1_0.Log;
import com.liferay.batch.planner.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Matija Petanjek
 * @generated
 */
@Generated("")
public class LogSerDes {

	public static Log toDTO(String json) {
		LogJSONParser logJSONParser = new LogJSONParser();

		return logJSONParser.parseToDTO(json);
	}

	public static Log[] toDTOs(String json) {
		LogJSONParser logJSONParser = new LogJSONParser();

		return logJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Log log) {
		if (log == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (log.getDispatchTriggerExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dispatchTriggerExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(log.getDispatchTriggerExternalReferenceCode()));

			sb.append("\"");
		}

		if (log.getExportTaskExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"exportTaskExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(log.getExportTaskExternalReferenceCode()));

			sb.append("\"");
		}

		if (log.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(log.getId());
		}

		if (log.getImportTaskExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"importTaskExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(log.getImportTaskExternalReferenceCode()));

			sb.append("\"");
		}

		if (log.getPlanId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"planId\": ");

			sb.append(log.getPlanId());
		}

		if (log.getSize() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"size\": ");

			sb.append(log.getSize());
		}

		if (log.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append(log.getStatus());
		}

		if (log.getTotal() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"total\": ");

			sb.append(log.getTotal());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		LogJSONParser logJSONParser = new LogJSONParser();

		return logJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Log log) {
		if (log == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (log.getDispatchTriggerExternalReferenceCode() == null) {
			map.put("dispatchTriggerExternalReferenceCode", null);
		}
		else {
			map.put(
				"dispatchTriggerExternalReferenceCode",
				String.valueOf(log.getDispatchTriggerExternalReferenceCode()));
		}

		if (log.getExportTaskExternalReferenceCode() == null) {
			map.put("exportTaskExternalReferenceCode", null);
		}
		else {
			map.put(
				"exportTaskExternalReferenceCode",
				String.valueOf(log.getExportTaskExternalReferenceCode()));
		}

		if (log.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(log.getId()));
		}

		if (log.getImportTaskExternalReferenceCode() == null) {
			map.put("importTaskExternalReferenceCode", null);
		}
		else {
			map.put(
				"importTaskExternalReferenceCode",
				String.valueOf(log.getImportTaskExternalReferenceCode()));
		}

		if (log.getPlanId() == null) {
			map.put("planId", null);
		}
		else {
			map.put("planId", String.valueOf(log.getPlanId()));
		}

		if (log.getSize() == null) {
			map.put("size", null);
		}
		else {
			map.put("size", String.valueOf(log.getSize()));
		}

		if (log.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(log.getStatus()));
		}

		if (log.getTotal() == null) {
			map.put("total", null);
		}
		else {
			map.put("total", String.valueOf(log.getTotal()));
		}

		return map;
	}

	public static class LogJSONParser extends BaseJSONParser<Log> {

		@Override
		protected Log createDTO() {
			return new Log();
		}

		@Override
		protected Log[] createDTOArray(int size) {
			return new Log[size];
		}

		@Override
		protected void setField(
			Log log, String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName,
					"dispatchTriggerExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					log.setDispatchTriggerExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"exportTaskExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					log.setExportTaskExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					log.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName,
						"importTaskExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					log.setImportTaskExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "planId")) {
				if (jsonParserFieldValue != null) {
					log.setPlanId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "size")) {
				if (jsonParserFieldValue != null) {
					log.setSize(Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					log.setStatus(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "total")) {
				if (jsonParserFieldValue != null) {
					log.setTotal(Integer.valueOf((String)jsonParserFieldValue));
				}
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
			sb.append("\": ");

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
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}