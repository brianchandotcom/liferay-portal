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

package com.liferay.headless.batch.engine.client.serdes.v1_0;

import com.liferay.headless.batch.engine.client.dto.v1_0.FailedItem;
import com.liferay.headless.batch.engine.client.dto.v1_0.ImportTask;
import com.liferay.headless.batch.engine.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Ivica Cardic
 * @generated
 */
@Generated("")
public class ImportTaskSerDes {

	public static ImportTask toDTO(String json) {
		ImportTaskJSONParser importTaskJSONParser = new ImportTaskJSONParser();

		return importTaskJSONParser.parseToDTO(json);
	}

	public static ImportTask[] toDTOs(String json) {
		ImportTaskJSONParser importTaskJSONParser = new ImportTaskJSONParser();

		return importTaskJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ImportTask importTask) {
		if (importTask == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (importTask.getClassName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"className\": ");

			sb.append("\"");

			sb.append(_escape(importTask.getClassName()));

			sb.append("\"");
		}

		if (importTask.getContentType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentType\": ");

			sb.append("\"");

			sb.append(_escape(importTask.getContentType()));

			sb.append("\"");
		}

		if (importTask.getEndTime() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"endTime\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(importTask.getEndTime()));

			sb.append("\"");
		}

		if (importTask.getErrorMessage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"errorMessage\": ");

			sb.append("\"");

			sb.append(_escape(importTask.getErrorMessage()));

			sb.append("\"");
		}

		if (importTask.getExecuteStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"executeStatus\": ");

			sb.append("\"");

			sb.append(importTask.getExecuteStatus());

			sb.append("\"");
		}

		if (importTask.getFailedItems() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"failedItems\": ");

			sb.append("[");

			for (int i = 0; i < importTask.getFailedItems().length; i++) {
				sb.append(String.valueOf(importTask.getFailedItems()[i]));

				if ((i + 1) < importTask.getFailedItems().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (importTask.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(importTask.getId());
		}

		if (importTask.getImportStrategy() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"importStrategy\": ");

			sb.append("\"");

			sb.append(importTask.getImportStrategy());

			sb.append("\"");
		}

		if (importTask.getOperation() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"operation\": ");

			sb.append("\"");

			sb.append(importTask.getOperation());

			sb.append("\"");
		}

		if (importTask.getProcessedItemsCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"processedItemsCount\": ");

			sb.append(importTask.getProcessedItemsCount());
		}

		if (importTask.getStartTime() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"startTime\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(importTask.getStartTime()));

			sb.append("\"");
		}

		if (importTask.getTotalItemsCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"totalItemsCount\": ");

			sb.append(importTask.getTotalItemsCount());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ImportTaskJSONParser importTaskJSONParser = new ImportTaskJSONParser();

		return importTaskJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ImportTask importTask) {
		if (importTask == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (importTask.getClassName() == null) {
			map.put("className", null);
		}
		else {
			map.put("className", String.valueOf(importTask.getClassName()));
		}

		if (importTask.getContentType() == null) {
			map.put("contentType", null);
		}
		else {
			map.put("contentType", String.valueOf(importTask.getContentType()));
		}

		if (importTask.getEndTime() == null) {
			map.put("endTime", null);
		}
		else {
			map.put(
				"endTime",
				liferayToJSONDateFormat.format(importTask.getEndTime()));
		}

		if (importTask.getErrorMessage() == null) {
			map.put("errorMessage", null);
		}
		else {
			map.put(
				"errorMessage", String.valueOf(importTask.getErrorMessage()));
		}

		if (importTask.getExecuteStatus() == null) {
			map.put("executeStatus", null);
		}
		else {
			map.put(
				"executeStatus", String.valueOf(importTask.getExecuteStatus()));
		}

		if (importTask.getFailedItems() == null) {
			map.put("failedItems", null);
		}
		else {
			map.put("failedItems", String.valueOf(importTask.getFailedItems()));
		}

		if (importTask.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(importTask.getId()));
		}

		if (importTask.getImportStrategy() == null) {
			map.put("importStrategy", null);
		}
		else {
			map.put(
				"importStrategy",
				String.valueOf(importTask.getImportStrategy()));
		}

		if (importTask.getOperation() == null) {
			map.put("operation", null);
		}
		else {
			map.put("operation", String.valueOf(importTask.getOperation()));
		}

		if (importTask.getProcessedItemsCount() == null) {
			map.put("processedItemsCount", null);
		}
		else {
			map.put(
				"processedItemsCount",
				String.valueOf(importTask.getProcessedItemsCount()));
		}

		if (importTask.getStartTime() == null) {
			map.put("startTime", null);
		}
		else {
			map.put(
				"startTime",
				liferayToJSONDateFormat.format(importTask.getStartTime()));
		}

		if (importTask.getTotalItemsCount() == null) {
			map.put("totalItemsCount", null);
		}
		else {
			map.put(
				"totalItemsCount",
				String.valueOf(importTask.getTotalItemsCount()));
		}

		return map;
	}

	public static class ImportTaskJSONParser
		extends BaseJSONParser<ImportTask> {

		@Override
		protected ImportTask createDTO() {
			return new ImportTask();
		}

		@Override
		protected ImportTask[] createDTOArray(int size) {
			return new ImportTask[size];
		}

		@Override
		protected void setField(
			ImportTask importTask, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "className")) {
				if (jsonParserFieldValue != null) {
					importTask.setClassName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contentType")) {
				if (jsonParserFieldValue != null) {
					importTask.setContentType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "endTime")) {
				if (jsonParserFieldValue != null) {
					importTask.setEndTime(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "errorMessage")) {
				if (jsonParserFieldValue != null) {
					importTask.setErrorMessage((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "executeStatus")) {
				if (jsonParserFieldValue != null) {
					importTask.setExecuteStatus(
						ImportTask.ExecuteStatus.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "failedItems")) {
				if (jsonParserFieldValue != null) {
					importTask.setFailedItems(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FailedItemSerDes.toDTO((String)object)
						).toArray(
							size -> new FailedItem[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					importTask.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "importStrategy")) {
				if (jsonParserFieldValue != null) {
					importTask.setImportStrategy(
						ImportTask.ImportStrategy.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "operation")) {
				if (jsonParserFieldValue != null) {
					importTask.setOperation(
						ImportTask.Operation.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "processedItemsCount")) {

				if (jsonParserFieldValue != null) {
					importTask.setProcessedItemsCount(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "startTime")) {
				if (jsonParserFieldValue != null) {
					importTask.setStartTime(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "totalItemsCount")) {
				if (jsonParserFieldValue != null) {
					importTask.setTotalItemsCount(
						Integer.valueOf((String)jsonParserFieldValue));
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