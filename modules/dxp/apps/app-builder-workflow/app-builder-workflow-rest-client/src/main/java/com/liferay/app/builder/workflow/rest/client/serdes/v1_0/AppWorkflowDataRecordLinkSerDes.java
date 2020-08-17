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

import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowDataRecordLink;
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
public class AppWorkflowDataRecordLinkSerDes {

	public static AppWorkflowDataRecordLink toDTO(String json) {
		AppWorkflowDataRecordLinkJSONParser
			appWorkflowDataRecordLinkJSONParser =
				new AppWorkflowDataRecordLinkJSONParser();

		return appWorkflowDataRecordLinkJSONParser.parseToDTO(json);
	}

	public static AppWorkflowDataRecordLink[] toDTOs(String json) {
		AppWorkflowDataRecordLinkJSONParser
			appWorkflowDataRecordLinkJSONParser =
				new AppWorkflowDataRecordLinkJSONParser();

		return appWorkflowDataRecordLinkJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		AppWorkflowDataRecordLink appWorkflowDataRecordLink) {

		if (appWorkflowDataRecordLink == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (appWorkflowDataRecordLink.getAppWorkflow() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appWorkflow\": ");

			sb.append(
				String.valueOf(appWorkflowDataRecordLink.getAppWorkflow()));
		}

		if (appWorkflowDataRecordLink.getDataRecordId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataRecordId\": ");

			sb.append(appWorkflowDataRecordLink.getDataRecordId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AppWorkflowDataRecordLinkJSONParser
			appWorkflowDataRecordLinkJSONParser =
				new AppWorkflowDataRecordLinkJSONParser();

		return appWorkflowDataRecordLinkJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		AppWorkflowDataRecordLink appWorkflowDataRecordLink) {

		if (appWorkflowDataRecordLink == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (appWorkflowDataRecordLink.getAppWorkflow() == null) {
			map.put("appWorkflow", null);
		}
		else {
			map.put(
				"appWorkflow",
				String.valueOf(appWorkflowDataRecordLink.getAppWorkflow()));
		}

		if (appWorkflowDataRecordLink.getDataRecordId() == null) {
			map.put("dataRecordId", null);
		}
		else {
			map.put(
				"dataRecordId",
				String.valueOf(appWorkflowDataRecordLink.getDataRecordId()));
		}

		return map;
	}

	public static class AppWorkflowDataRecordLinkJSONParser
		extends BaseJSONParser<AppWorkflowDataRecordLink> {

		@Override
		protected AppWorkflowDataRecordLink createDTO() {
			return new AppWorkflowDataRecordLink();
		}

		@Override
		protected AppWorkflowDataRecordLink[] createDTOArray(int size) {
			return new AppWorkflowDataRecordLink[size];
		}

		@Override
		protected void setField(
			AppWorkflowDataRecordLink appWorkflowDataRecordLink,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "appWorkflow")) {
				if (jsonParserFieldValue != null) {
					appWorkflowDataRecordLink.setAppWorkflow(
						AppWorkflowSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataRecordId")) {
				if (jsonParserFieldValue != null) {
					appWorkflowDataRecordLink.setDataRecordId(
						Long.valueOf((String)jsonParserFieldValue));
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