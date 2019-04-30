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

package com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class SLASerDes {

	public static SLA toDTO(String json) {
		SLAJSONParser slaJSONParser = new SLAJSONParser();

		return slaJSONParser.parseToDTO(json);
	}

	public static SLA[] toDTOs(String json) {
		SLAJSONParser slaJSONParser = new SLAJSONParser();

		return slaJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SLA sla) {
		if (sla == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (sla.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(sla.getDateModified()));

			sb.append("\"");
		}

		if (sla.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(sla.getDescription()));

			sb.append("\"");
		}

		if (sla.getDuration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"duration\": ");

			sb.append(sla.getDuration());
		}

		if (sla.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(sla.getId());
		}

		if (sla.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(sla.getName()));

			sb.append("\"");
		}

		if (sla.getPauseNodeKeys() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pauseNodeKeys\": ");

			sb.append("[");

			for (int i = 0; i < sla.getPauseNodeKeys().length; i++) {
				sb.append("\"");

				sb.append(_escape(sla.getPauseNodeKeys()[i]));

				sb.append("\"");

				if ((i + 1) < sla.getPauseNodeKeys().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (sla.getProcessId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"processId\": ");

			sb.append(sla.getProcessId());
		}

		if (sla.getStartNodeKeys() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"startNodeKeys\": ");

			sb.append("[");

			for (int i = 0; i < sla.getStartNodeKeys().length; i++) {
				sb.append("\"");

				sb.append(_escape(sla.getStartNodeKeys()[i]));

				sb.append("\"");

				if ((i + 1) < sla.getStartNodeKeys().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (sla.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append(sla.getStatus());
		}

		if (sla.getStopNodeKeys() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"stopNodeKeys\": ");

			sb.append("[");

			for (int i = 0; i < sla.getStopNodeKeys().length; i++) {
				sb.append("\"");

				sb.append(_escape(sla.getStopNodeKeys()[i]));

				sb.append("\"");

				if ((i + 1) < sla.getStopNodeKeys().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SLAJSONParser slaJSONParser = new SLAJSONParser();

		return slaJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SLA sla) {
		if (sla == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(sla.getDateModified()));

		if (sla.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(sla.getDescription()));
		}

		if (sla.getDuration() == null) {
			map.put("duration", null);
		}
		else {
			map.put("duration", String.valueOf(sla.getDuration()));
		}

		if (sla.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(sla.getId()));
		}

		if (sla.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(sla.getName()));
		}

		if (sla.getPauseNodeKeys() == null) {
			map.put("pauseNodeKeys", null);
		}
		else {
			map.put("pauseNodeKeys", String.valueOf(sla.getPauseNodeKeys()));
		}

		if (sla.getProcessId() == null) {
			map.put("processId", null);
		}
		else {
			map.put("processId", String.valueOf(sla.getProcessId()));
		}

		if (sla.getStartNodeKeys() == null) {
			map.put("startNodeKeys", null);
		}
		else {
			map.put("startNodeKeys", String.valueOf(sla.getStartNodeKeys()));
		}

		if (sla.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(sla.getStatus()));
		}

		if (sla.getStopNodeKeys() == null) {
			map.put("stopNodeKeys", null);
		}
		else {
			map.put("stopNodeKeys", String.valueOf(sla.getStopNodeKeys()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
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

	private static class SLAJSONParser extends BaseJSONParser<SLA> {

		@Override
		protected SLA createDTO() {
			return new SLA();
		}

		@Override
		protected SLA[] createDTOArray(int size) {
			return new SLA[size];
		}

		@Override
		protected void setField(
			SLA sla, String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					sla.setDateModified(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					sla.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "duration")) {
				if (jsonParserFieldValue != null) {
					sla.setDuration(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					sla.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					sla.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pauseNodeKeys")) {
				if (jsonParserFieldValue != null) {
					sla.setPauseNodeKeys(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "processId")) {
				if (jsonParserFieldValue != null) {
					sla.setProcessId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "startNodeKeys")) {
				if (jsonParserFieldValue != null) {
					sla.setStartNodeKeys(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					sla.setStatus(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "stopNodeKeys")) {
				if (jsonParserFieldValue != null) {
					sla.setStopNodeKeys(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}