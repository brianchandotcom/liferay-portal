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

package com.liferay.change.tracking.rest.client.serdes.v1_0;

import com.liferay.change.tracking.rest.client.dto.v1_0.Entry;
import com.liferay.change.tracking.rest.client.dto.v1_0.Process;
import com.liferay.change.tracking.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

/**
 * @author Máté Thurzó
 * @generated
 */
@Generated("")
public class ProcessSerDes {

	public static Process toDTO(String json) {
		ProcessJSONParser processJSONParser = new ProcessJSONParser();

		return processJSONParser.parseToDTO(json);
	}

	public static Process[] toDTOs(String json) {
		ProcessJSONParser processJSONParser = new ProcessJSONParser();

		return processJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Process process) {
		if (process == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (process.getCollection() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"collection\": ");

			sb.append(String.valueOf(process.getCollection()));
		}

		if (process.getCompanyId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"companyId\": ");

			sb.append(process.getCompanyId());
		}

		if (process.getDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"date\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(process.getDate()));

			sb.append("\"");
		}

		if (process.getPercentage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"percentage\": ");

			sb.append(process.getPercentage());
		}

		if (process.getProcessId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"processId\": ");

			sb.append(process.getProcessId());
		}

		if (process.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");

			sb.append(_escape(process.getStatus()));

			sb.append("\"");
		}

		if (process.getUserInitials() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userInitials\": ");

			sb.append("\"");

			sb.append(_escape(process.getUserInitials()));

			sb.append("\"");
		}

		if (process.getUserName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userName\": ");

			sb.append("\"");

			sb.append(_escape(process.getUserName()));

			sb.append("\"");
		}

		if (process.getUserPortraitURL() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userPortraitURL\": ");

			sb.append("\"");

			sb.append(_escape(process.getUserPortraitURL()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ProcessJSONParser processJSONParser = new ProcessJSONParser();

		return processJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Process process) {
		if (process == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (process.getCollection() == null) {
			map.put("collection", null);
		}
		else {
			map.put("collection", String.valueOf(process.getCollection()));
		}

		if (process.getCompanyId() == null) {
			map.put("companyId", null);
		}
		else {
			map.put("companyId", String.valueOf(process.getCompanyId()));
		}

		map.put("date", liferayToJSONDateFormat.format(process.getDate()));

		if (process.getPercentage() == null) {
			map.put("percentage", null);
		}
		else {
			map.put("percentage", String.valueOf(process.getPercentage()));
		}

		if (process.getProcessId() == null) {
			map.put("processId", null);
		}
		else {
			map.put("processId", String.valueOf(process.getProcessId()));
		}

		if (process.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(process.getStatus()));
		}

		if (process.getUserInitials() == null) {
			map.put("userInitials", null);
		}
		else {
			map.put("userInitials", String.valueOf(process.getUserInitials()));
		}

		if (process.getUserName() == null) {
			map.put("userName", null);
		}
		else {
			map.put("userName", String.valueOf(process.getUserName()));
		}

		if (process.getUserPortraitURL() == null) {
			map.put("userPortraitURL", null);
		}
		else {
			map.put(
				"userPortraitURL",
				String.valueOf(process.getUserPortraitURL()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		string = string.replace("\\", "\\\\");

		return string.replace("\"", "\\\"");
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
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static class ProcessJSONParser extends BaseJSONParser<Process> {

		@Override
		protected Process createDTO() {
			return new Process();
		}

		@Override
		protected Process[] createDTOArray(int size) {
			return new Process[size];
		}

		@Override
		protected void setField(
			Process process, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "collection")) {
				if (jsonParserFieldValue != null) {
					process.setCollection(
						CollectionSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "companyId")) {
				if (jsonParserFieldValue != null) {
					process.setCompanyId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "date")) {
				if (jsonParserFieldValue != null) {
					process.setDate(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "percentage")) {
				if (jsonParserFieldValue != null) {
					process.setPercentage(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "processId")) {
				if (jsonParserFieldValue != null) {
					process.setProcessId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					process.setStatus((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userInitials")) {
				if (jsonParserFieldValue != null) {
					process.setUserInitials((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userName")) {
				if (jsonParserFieldValue != null) {
					process.setUserName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userPortraitURL")) {
				if (jsonParserFieldValue != null) {
					process.setUserPortraitURL((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}