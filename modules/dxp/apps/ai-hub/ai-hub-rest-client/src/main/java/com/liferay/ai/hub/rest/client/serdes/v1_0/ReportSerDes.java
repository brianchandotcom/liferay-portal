/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.client.serdes.v1_0;

import com.liferay.ai.hub.rest.client.dto.v1_0.Report;
import com.liferay.ai.hub.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Feliphe Marinho
 * @generated
 */
@Generated("")
public class ReportSerDes {

	public static Report toDTO(String json) {
		ReportJSONParser reportJSONParser = new ReportJSONParser();

		return reportJSONParser.parseToDTO(json);
	}

	public static Report[] toDTOs(String json) {
		ReportJSONParser reportJSONParser = new ReportJSONParser();

		return reportJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Report report) {
		if (report == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (report.getAgentDefinitionExternalReferenceCodes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"agentDefinitionExternalReferenceCodes\": ");

			sb.append("[");

			for (int i = 0;
				 i < report.getAgentDefinitionExternalReferenceCodes().length;
				 i++) {

				sb.append(
					_toJSON(
						report.getAgentDefinitionExternalReferenceCodes()[i]));

				if ((i + 1) <
						report.
							getAgentDefinitionExternalReferenceCodes().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (report.getChatbotExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"chatbotExternalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(report.getChatbotExternalReferenceCode()));

			sb.append("\"");
		}

		if (report.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(report.getDateCreated()));

			sb.append("\"");
		}

		if (report.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(report.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (report.getFeedback() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"feedback\": ");

			sb.append("\"");

			sb.append(_escape(report.getFeedback()));

			sb.append("\"");
		}

		if (report.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(report.getId());
		}

		if (report.getLevel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"level\": ");

			sb.append("\"");

			sb.append(_escape(report.getLevel()));

			sb.append("\"");
		}

		if (report.getReason() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"reason\": ");

			sb.append("\"");

			sb.append(_escape(report.getReason()));

			sb.append("\"");
		}

		if (report.getSurface() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"surface\": ");

			sb.append("\"");

			sb.append(_escape(report.getSurface()));

			sb.append("\"");
		}

		if (report.getUserMessage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userMessage\": ");

			sb.append("\"");

			sb.append(_escape(report.getUserMessage()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ReportJSONParser reportJSONParser = new ReportJSONParser();

		return reportJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Report report) {
		if (report == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (report.getAgentDefinitionExternalReferenceCodes() == null) {
			map.put("agentDefinitionExternalReferenceCodes", null);
		}
		else {
			map.put(
				"agentDefinitionExternalReferenceCodes",
				String.valueOf(
					report.getAgentDefinitionExternalReferenceCodes()));
		}

		if (report.getChatbotExternalReferenceCode() == null) {
			map.put("chatbotExternalReferenceCode", null);
		}
		else {
			map.put(
				"chatbotExternalReferenceCode",
				String.valueOf(report.getChatbotExternalReferenceCode()));
		}

		if (report.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(report.getDateCreated()));
		}

		if (report.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(report.getExternalReferenceCode()));
		}

		if (report.getFeedback() == null) {
			map.put("feedback", null);
		}
		else {
			map.put("feedback", String.valueOf(report.getFeedback()));
		}

		if (report.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(report.getId()));
		}

		if (report.getLevel() == null) {
			map.put("level", null);
		}
		else {
			map.put("level", String.valueOf(report.getLevel()));
		}

		if (report.getReason() == null) {
			map.put("reason", null);
		}
		else {
			map.put("reason", String.valueOf(report.getReason()));
		}

		if (report.getSurface() == null) {
			map.put("surface", null);
		}
		else {
			map.put("surface", String.valueOf(report.getSurface()));
		}

		if (report.getUserMessage() == null) {
			map.put("userMessage", null);
		}
		else {
			map.put("userMessage", String.valueOf(report.getUserMessage()));
		}

		return map;
	}

	public static class ReportJSONParser extends BaseJSONParser<Report> {

		@Override
		protected Report createDTO() {
			return new Report();
		}

		@Override
		protected Report[] createDTOArray(int size) {
			return new Report[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(
					jsonParserFieldName,
					"agentDefinitionExternalReferenceCodes")) {

				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "chatbotExternalReferenceCode")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "feedback")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "level")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "reason")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "surface")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "userMessage")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			Report report, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName,
					"agentDefinitionExternalReferenceCodes")) {

				if (jsonParserFieldValue != null) {
					report.setAgentDefinitionExternalReferenceCodes(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "chatbotExternalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					report.setChatbotExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					report.setDateCreated(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					report.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "feedback")) {
				if (jsonParserFieldValue != null) {
					report.setFeedback((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					report.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "level")) {
				if (jsonParserFieldValue != null) {
					report.setLevel((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "reason")) {
				if (jsonParserFieldValue != null) {
					report.setReason((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "surface")) {
				if (jsonParserFieldValue != null) {
					report.setSurface((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userMessage")) {
				if (jsonParserFieldValue != null) {
					report.setUserMessage((String)jsonParserFieldValue);
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
// LIFERAY-REST-BUILDER-HASH:-310956924