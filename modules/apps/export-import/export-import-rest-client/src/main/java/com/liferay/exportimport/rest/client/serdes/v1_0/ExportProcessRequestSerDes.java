/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.serdes.v1_0;

import com.liferay.exportimport.rest.client.dto.v1_0.ExportProcessRequest;
import com.liferay.exportimport.rest.client.dto.v1_0.RequestPortletDataHandler;
import com.liferay.exportimport.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class ExportProcessRequestSerDes {

	public static ExportProcessRequest toDTO(String json) {
		ExportProcessRequestJSONParser exportProcessRequestJSONParser =
			new ExportProcessRequestJSONParser();

		return exportProcessRequestJSONParser.parseToDTO(json);
	}

	public static ExportProcessRequest[] toDTOs(String json) {
		ExportProcessRequestJSONParser exportProcessRequestJSONParser =
			new ExportProcessRequestJSONParser();

		return exportProcessRequestJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ExportProcessRequest exportProcessRequest) {
		if (exportProcessRequest == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (exportProcessRequest.getDeletions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"deletions\": ");

			sb.append(exportProcessRequest.getDeletions());
		}

		if (exportProcessRequest.getEndDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"endDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					exportProcessRequest.getEndDate()));

			sb.append("\"");
		}

		if (exportProcessRequest.getLast() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"last\": ");

			sb.append(exportProcessRequest.getLast());
		}

		if (exportProcessRequest.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(exportProcessRequest.getName()));

			sb.append("\"");
		}

		if (exportProcessRequest.getPermissions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"permissions\": ");

			sb.append(exportProcessRequest.getPermissions());
		}

		if (exportProcessRequest.getRange() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"range\": ");

			sb.append("\"");
			sb.append(exportProcessRequest.getRange());
			sb.append("\"");
		}

		if (exportProcessRequest.getRequestPortletDataHandlers() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"requestPortletDataHandlers\": ");

			sb.append("[");

			for (int i = 0;
				 i <
					 exportProcessRequest.
						 getRequestPortletDataHandlers().length;
				 i++) {

				sb.append(
					String.valueOf(
						exportProcessRequest.getRequestPortletDataHandlers()
							[i]));

				if ((i + 1) < exportProcessRequest.
						getRequestPortletDataHandlers().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (exportProcessRequest.getStartDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"startDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(
					exportProcessRequest.getStartDate()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ExportProcessRequestJSONParser exportProcessRequestJSONParser =
			new ExportProcessRequestJSONParser();

		return exportProcessRequestJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ExportProcessRequest exportProcessRequest) {

		if (exportProcessRequest == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (exportProcessRequest.getDeletions() == null) {
			map.put("deletions", null);
		}
		else {
			map.put(
				"deletions",
				String.valueOf(exportProcessRequest.getDeletions()));
		}

		if (exportProcessRequest.getEndDate() == null) {
			map.put("endDate", null);
		}
		else {
			map.put(
				"endDate",
				liferayToJSONDateFormat.format(
					exportProcessRequest.getEndDate()));
		}

		if (exportProcessRequest.getLast() == null) {
			map.put("last", null);
		}
		else {
			map.put("last", String.valueOf(exportProcessRequest.getLast()));
		}

		if (exportProcessRequest.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(exportProcessRequest.getName()));
		}

		if (exportProcessRequest.getPermissions() == null) {
			map.put("permissions", null);
		}
		else {
			map.put(
				"permissions",
				String.valueOf(exportProcessRequest.getPermissions()));
		}

		if (exportProcessRequest.getRange() == null) {
			map.put("range", null);
		}
		else {
			map.put("range", String.valueOf(exportProcessRequest.getRange()));
		}

		if (exportProcessRequest.getRequestPortletDataHandlers() == null) {
			map.put("requestPortletDataHandlers", null);
		}
		else {
			map.put(
				"requestPortletDataHandlers",
				String.valueOf(
					exportProcessRequest.getRequestPortletDataHandlers()));
		}

		if (exportProcessRequest.getStartDate() == null) {
			map.put("startDate", null);
		}
		else {
			map.put(
				"startDate",
				liferayToJSONDateFormat.format(
					exportProcessRequest.getStartDate()));
		}

		return map;
	}

	public static class ExportProcessRequestJSONParser
		extends BaseJSONParser<ExportProcessRequest> {

		@Override
		protected ExportProcessRequest createDTO() {
			return new ExportProcessRequest();
		}

		@Override
		protected ExportProcessRequest[] createDTOArray(int size) {
			return new ExportProcessRequest[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "deletions")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "endDate")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "last")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "permissions")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "range")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "requestPortletDataHandlers")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "startDate")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ExportProcessRequest exportProcessRequest,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "deletions")) {
				if (jsonParserFieldValue != null) {
					exportProcessRequest.setDeletions(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "endDate")) {
				if (jsonParserFieldValue != null) {
					exportProcessRequest.setEndDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "last")) {
				if (jsonParserFieldValue != null) {
					exportProcessRequest.setLast(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					exportProcessRequest.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "permissions")) {
				if (jsonParserFieldValue != null) {
					exportProcessRequest.setPermissions(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "range")) {
				if (jsonParserFieldValue != null) {
					exportProcessRequest.setRange(
						ExportProcessRequest.Range.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "requestPortletDataHandlers")) {

				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					RequestPortletDataHandler[]
						requestPortletDataHandlersArray =
							new RequestPortletDataHandler
								[jsonParserFieldValues.length];

					for (int i = 0; i < requestPortletDataHandlersArray.length;
						 i++) {

						requestPortletDataHandlersArray[i] =
							RequestPortletDataHandlerSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					exportProcessRequest.setRequestPortletDataHandlers(
						requestPortletDataHandlersArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "startDate")) {
				if (jsonParserFieldValue != null) {
					exportProcessRequest.setStartDate(
						toDate((String)jsonParserFieldValue));
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

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value == null) {
			return "null";
		}

		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}
// LIFERAY-REST-BUILDER-HASH:1211673737