/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.client.serdes.v1_0;

import com.liferay.exportimport.rest.client.dto.v1_0.ExportRequest;
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
public class ExportRequestSerDes {

	public static ExportRequest toDTO(String json) {
		ExportRequestJSONParser exportRequestJSONParser =
			new ExportRequestJSONParser();

		return exportRequestJSONParser.parseToDTO(json);
	}

	public static ExportRequest[] toDTOs(String json) {
		ExportRequestJSONParser exportRequestJSONParser =
			new ExportRequestJSONParser();

		return exportRequestJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ExportRequest exportRequest) {
		if (exportRequest == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (exportRequest.getDeletions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"deletions\": ");

			sb.append(exportRequest.getDeletions());
		}

		if (exportRequest.getEndDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"endDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(exportRequest.getEndDate()));

			sb.append("\"");
		}

		if (exportRequest.getFileName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fileName\": ");

			sb.append("\"");

			sb.append(_escape(exportRequest.getFileName()));

			sb.append("\"");
		}

		if (exportRequest.getLast() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"last\": ");

			sb.append(exportRequest.getLast());
		}

		if (exportRequest.getPermissions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"permissions\": ");

			sb.append(exportRequest.getPermissions());
		}

		if (exportRequest.getRange() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"range\": ");

			sb.append("\"");
			sb.append(exportRequest.getRange());
			sb.append("\"");
		}

		if (exportRequest.getRequestPortletDataHandlers() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"requestPortletDataHandlers\": ");

			sb.append("[");

			for (int i = 0;
				 i < exportRequest.getRequestPortletDataHandlers().length;
				 i++) {

				sb.append(
					String.valueOf(
						exportRequest.getRequestPortletDataHandlers()[i]));

				if ((i + 1) <
						exportRequest.getRequestPortletDataHandlers().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (exportRequest.getStartDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"startDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(exportRequest.getStartDate()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ExportRequestJSONParser exportRequestJSONParser =
			new ExportRequestJSONParser();

		return exportRequestJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ExportRequest exportRequest) {
		if (exportRequest == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (exportRequest.getDeletions() == null) {
			map.put("deletions", null);
		}
		else {
			map.put("deletions", String.valueOf(exportRequest.getDeletions()));
		}

		if (exportRequest.getEndDate() == null) {
			map.put("endDate", null);
		}
		else {
			map.put(
				"endDate",
				liferayToJSONDateFormat.format(exportRequest.getEndDate()));
		}

		if (exportRequest.getFileName() == null) {
			map.put("fileName", null);
		}
		else {
			map.put("fileName", String.valueOf(exportRequest.getFileName()));
		}

		if (exportRequest.getLast() == null) {
			map.put("last", null);
		}
		else {
			map.put("last", String.valueOf(exportRequest.getLast()));
		}

		if (exportRequest.getPermissions() == null) {
			map.put("permissions", null);
		}
		else {
			map.put(
				"permissions", String.valueOf(exportRequest.getPermissions()));
		}

		if (exportRequest.getRange() == null) {
			map.put("range", null);
		}
		else {
			map.put("range", String.valueOf(exportRequest.getRange()));
		}

		if (exportRequest.getRequestPortletDataHandlers() == null) {
			map.put("requestPortletDataHandlers", null);
		}
		else {
			map.put(
				"requestPortletDataHandlers",
				String.valueOf(exportRequest.getRequestPortletDataHandlers()));
		}

		if (exportRequest.getStartDate() == null) {
			map.put("startDate", null);
		}
		else {
			map.put(
				"startDate",
				liferayToJSONDateFormat.format(exportRequest.getStartDate()));
		}

		return map;
	}

	public static class ExportRequestJSONParser
		extends BaseJSONParser<ExportRequest> {

		@Override
		protected ExportRequest createDTO() {
			return new ExportRequest();
		}

		@Override
		protected ExportRequest[] createDTOArray(int size) {
			return new ExportRequest[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "deletions")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "endDate")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "fileName")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "last")) {
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
			ExportRequest exportRequest, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "deletions")) {
				if (jsonParserFieldValue != null) {
					exportRequest.setDeletions((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "endDate")) {
				if (jsonParserFieldValue != null) {
					exportRequest.setEndDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fileName")) {
				if (jsonParserFieldValue != null) {
					exportRequest.setFileName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "last")) {
				if (jsonParserFieldValue != null) {
					exportRequest.setLast(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "permissions")) {
				if (jsonParserFieldValue != null) {
					exportRequest.setPermissions((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "range")) {
				if (jsonParserFieldValue != null) {
					exportRequest.setRange(
						ExportRequest.Range.create(
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

					exportRequest.setRequestPortletDataHandlers(
						requestPortletDataHandlersArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "startDate")) {
				if (jsonParserFieldValue != null) {
					exportRequest.setStartDate(
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
// LIFERAY-REST-BUILDER-HASH:696058832