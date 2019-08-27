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

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.SLAResult;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class InstanceSerDes {

	public static Instance toDTO(String json) {
		InstanceJSONParser instanceJSONParser = new InstanceJSONParser();

		return instanceJSONParser.parseToDTO(json);
	}

	public static Instance[] toDTOs(String json) {
		InstanceJSONParser instanceJSONParser = new InstanceJSONParser();

		return instanceJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Instance instance) {
		if (instance == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (instance.getAssetTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"assetTitle\": ");

			sb.append("\"");

			sb.append(_escape(instance.getAssetTitle()));

			sb.append("\"");
		}

		if (instance.getAssetType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"assetType\": ");

			sb.append("\"");

			sb.append(_escape(instance.getAssetType()));

			sb.append("\"");
		}

		if (instance.getDateCompletion() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCompletion\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(instance.getDateCompletion()));

			sb.append("\"");
		}

		if (instance.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(instance.getDateCreated()));

			sb.append("\"");
		}

		if (instance.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(instance.getId());
		}

		if (instance.getProcessId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"processId\": ");

			sb.append(instance.getProcessId());
		}

		if (instance.getSlaResults() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"slaResults\": ");

			sb.append("[");

			for (int i = 0; i < instance.getSlaResults().length; i++) {
				sb.append(String.valueOf(instance.getSlaResults()[i]));

				if ((i + 1) < instance.getSlaResults().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (instance.getSLAStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"slaStatus\": ");

			sb.append("\"");

			sb.append(instance.getSLAStatus());

			sb.append("\"");
		}

		if (instance.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");

			sb.append(instance.getStatus());

			sb.append("\"");
		}

		if (instance.getTaskNames() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taskNames\": ");

			sb.append("[");

			for (int i = 0; i < instance.getTaskNames().length; i++) {
				sb.append("\"");

				sb.append(_escape(instance.getTaskNames()[i]));

				sb.append("\"");

				if ((i + 1) < instance.getTaskNames().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (instance.getUserName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userName\": ");

			sb.append("\"");

			sb.append(_escape(instance.getUserName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		InstanceJSONParser instanceJSONParser = new InstanceJSONParser();

		return instanceJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Instance instance) {
		if (instance == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (instance.getAssetTitle() == null) {
			map.put("assetTitle", null);
		}
		else {
			map.put("assetTitle", String.valueOf(instance.getAssetTitle()));
		}

		if (instance.getAssetType() == null) {
			map.put("assetType", null);
		}
		else {
			map.put("assetType", String.valueOf(instance.getAssetType()));
		}

		map.put(
			"dateCompletion",
			liferayToJSONDateFormat.format(instance.getDateCompletion()));

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(instance.getDateCreated()));

		if (instance.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(instance.getId()));
		}

		if (instance.getProcessId() == null) {
			map.put("processId", null);
		}
		else {
			map.put("processId", String.valueOf(instance.getProcessId()));
		}

		if (instance.getSlaResults() == null) {
			map.put("slaResults", null);
		}
		else {
			map.put("slaResults", String.valueOf(instance.getSlaResults()));
		}

		if (instance.getSLAStatus() == null) {
			map.put("slaStatus", null);
		}
		else {
			map.put("slaStatus", String.valueOf(instance.getSLAStatus()));
		}

		if (instance.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(instance.getStatus()));
		}

		if (instance.getTaskNames() == null) {
			map.put("taskNames", null);
		}
		else {
			map.put("taskNames", String.valueOf(instance.getTaskNames()));
		}

		if (instance.getUserName() == null) {
			map.put("userName", null);
		}
		else {
			map.put("userName", String.valueOf(instance.getUserName()));
		}

		return map;
	}

	public static class InstanceJSONParser extends BaseJSONParser<Instance> {

		@Override
		protected Instance createDTO() {
			return new Instance();
		}

		@Override
		protected Instance[] createDTOArray(int size) {
			return new Instance[size];
		}

		@Override
		protected void setField(
			Instance instance, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "assetTitle")) {
				if (jsonParserFieldValue != null) {
					instance.setAssetTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "assetType")) {
				if (jsonParserFieldValue != null) {
					instance.setAssetType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCompletion")) {
				if (jsonParserFieldValue != null) {
					instance.setDateCompletion(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					instance.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					instance.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "processId")) {
				if (jsonParserFieldValue != null) {
					instance.setProcessId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "slaResults")) {
				if (jsonParserFieldValue != null) {
					instance.setSlaResults(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> SLAResultSerDes.toDTO((String)object)
						).toArray(
							size -> new SLAResult[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "slaStatus")) {
				if (jsonParserFieldValue != null) {
					instance.setSLAStatus(
						Instance.SLAStatus.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					instance.setStatus(
						Instance.Status.create((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "taskNames")) {
				if (jsonParserFieldValue != null) {
					instance.setTaskNames(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userName")) {
				if (jsonParserFieldValue != null) {
					instance.setUserName((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

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

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
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
			else {
				sb.append("\"");
				sb.append(entry.getValue());
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}