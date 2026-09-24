/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.client.serdes.v1_0;

import com.liferay.headless.admin.fragment.client.dto.v1_0.ApprovedFragmentVersion;
import com.liferay.headless.admin.fragment.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class ApprovedFragmentVersionSerDes {

	public static ApprovedFragmentVersion toDTO(String json) {
		ApprovedFragmentVersionJSONParser approvedFragmentVersionJSONParser =
			new ApprovedFragmentVersionJSONParser();

		return approvedFragmentVersionJSONParser.parseToDTO(json);
	}

	public static ApprovedFragmentVersion[] toDTOs(String json) {
		ApprovedFragmentVersionJSONParser approvedFragmentVersionJSONParser =
			new ApprovedFragmentVersionJSONParser();

		return approvedFragmentVersionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		ApprovedFragmentVersion approvedFragmentVersion) {

		if (approvedFragmentVersion == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (approvedFragmentVersion.getConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"configuration\": ");

			sb.append(
				String.valueOf(approvedFragmentVersion.getConfiguration()));
		}

		if (approvedFragmentVersion.getCss() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"css\": ");

			sb.append("\"");

			sb.append(_escape(approvedFragmentVersion.getCss()));

			sb.append("\"");
		}

		if (approvedFragmentVersion.getHtml() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"html\": ");

			sb.append("\"");

			sb.append(_escape(approvedFragmentVersion.getHtml()));

			sb.append("\"");
		}

		if (approvedFragmentVersion.getJs() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"js\": ");

			sb.append("\"");

			sb.append(_escape(approvedFragmentVersion.getJs()));

			sb.append("\"");
		}

		if (approvedFragmentVersion.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");
			sb.append(approvedFragmentVersion.getStatus());
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ApprovedFragmentVersionJSONParser approvedFragmentVersionJSONParser =
			new ApprovedFragmentVersionJSONParser();

		return approvedFragmentVersionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ApprovedFragmentVersion approvedFragmentVersion) {

		if (approvedFragmentVersion == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (approvedFragmentVersion.getConfiguration() == null) {
			map.put("configuration", null);
		}
		else {
			map.put(
				"configuration",
				String.valueOf(approvedFragmentVersion.getConfiguration()));
		}

		if (approvedFragmentVersion.getCss() == null) {
			map.put("css", null);
		}
		else {
			map.put("css", String.valueOf(approvedFragmentVersion.getCss()));
		}

		if (approvedFragmentVersion.getHtml() == null) {
			map.put("html", null);
		}
		else {
			map.put("html", String.valueOf(approvedFragmentVersion.getHtml()));
		}

		if (approvedFragmentVersion.getJs() == null) {
			map.put("js", null);
		}
		else {
			map.put("js", String.valueOf(approvedFragmentVersion.getJs()));
		}

		if (approvedFragmentVersion.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put(
				"status", String.valueOf(approvedFragmentVersion.getStatus()));
		}

		return map;
	}

	public static class ApprovedFragmentVersionJSONParser
		extends BaseJSONParser<ApprovedFragmentVersion> {

		@Override
		protected ApprovedFragmentVersion createDTO() {
			return new ApprovedFragmentVersion();
		}

		@Override
		protected ApprovedFragmentVersion[] createDTOArray(int size) {
			return new ApprovedFragmentVersion[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "configuration")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "css")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "html")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "js")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ApprovedFragmentVersion approvedFragmentVersion,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "configuration")) {
				if (jsonParserFieldValue != null) {
					approvedFragmentVersion.setConfiguration(
						ConfigurationSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "css")) {
				if (jsonParserFieldValue != null) {
					approvedFragmentVersion.setCss(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "html")) {
				if (jsonParserFieldValue != null) {
					approvedFragmentVersion.setHtml(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "js")) {
				if (jsonParserFieldValue != null) {
					approvedFragmentVersion.setJs((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					approvedFragmentVersion.setStatus(
						ApprovedFragmentVersion.Status.create(
							(String)jsonParserFieldValue));
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

		if (value instanceof Collection) {
			Collection<?> collection = (Collection<?>)value;

			return _toJSON(collection.toArray());
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
// LIFERAY-REST-BUILDER-HASH:-616643646