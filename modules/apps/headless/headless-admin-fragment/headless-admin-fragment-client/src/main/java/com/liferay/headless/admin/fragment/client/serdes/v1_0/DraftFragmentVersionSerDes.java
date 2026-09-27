/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.client.serdes.v1_0;

import com.liferay.headless.admin.fragment.client.dto.v1_0.DraftFragmentVersion;
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
public class DraftFragmentVersionSerDes {

	public static DraftFragmentVersion toDTO(String json) {
		DraftFragmentVersionJSONParser draftFragmentVersionJSONParser =
			new DraftFragmentVersionJSONParser();

		return draftFragmentVersionJSONParser.parseToDTO(json);
	}

	public static DraftFragmentVersion[] toDTOs(String json) {
		DraftFragmentVersionJSONParser draftFragmentVersionJSONParser =
			new DraftFragmentVersionJSONParser();

		return draftFragmentVersionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DraftFragmentVersion draftFragmentVersion) {
		if (draftFragmentVersion == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (draftFragmentVersion.getConfiguration() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"configuration\": ");

			sb.append("\"");

			sb.append(_escape(draftFragmentVersion.getConfiguration()));

			sb.append("\"");
		}

		if (draftFragmentVersion.getCss() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"css\": ");

			sb.append("\"");

			sb.append(_escape(draftFragmentVersion.getCss()));

			sb.append("\"");
		}

		if (draftFragmentVersion.getHtml() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"html\": ");

			sb.append("\"");

			sb.append(_escape(draftFragmentVersion.getHtml()));

			sb.append("\"");
		}

		if (draftFragmentVersion.getJs() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"js\": ");

			sb.append("\"");

			sb.append(_escape(draftFragmentVersion.getJs()));

			sb.append("\"");
		}

		if (draftFragmentVersion.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");
			sb.append(draftFragmentVersion.getStatus());
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DraftFragmentVersionJSONParser draftFragmentVersionJSONParser =
			new DraftFragmentVersionJSONParser();

		return draftFragmentVersionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DraftFragmentVersion draftFragmentVersion) {

		if (draftFragmentVersion == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (draftFragmentVersion.getConfiguration() == null) {
			map.put("configuration", null);
		}
		else {
			map.put(
				"configuration",
				String.valueOf(draftFragmentVersion.getConfiguration()));
		}

		if (draftFragmentVersion.getCss() == null) {
			map.put("css", null);
		}
		else {
			map.put("css", String.valueOf(draftFragmentVersion.getCss()));
		}

		if (draftFragmentVersion.getHtml() == null) {
			map.put("html", null);
		}
		else {
			map.put("html", String.valueOf(draftFragmentVersion.getHtml()));
		}

		if (draftFragmentVersion.getJs() == null) {
			map.put("js", null);
		}
		else {
			map.put("js", String.valueOf(draftFragmentVersion.getJs()));
		}

		if (draftFragmentVersion.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(draftFragmentVersion.getStatus()));
		}

		return map;
	}

	public static class DraftFragmentVersionJSONParser
		extends BaseJSONParser<DraftFragmentVersion> {

		@Override
		protected DraftFragmentVersion createDTO() {
			return new DraftFragmentVersion();
		}

		@Override
		protected DraftFragmentVersion[] createDTOArray(int size) {
			return new DraftFragmentVersion[size];
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
			DraftFragmentVersion draftFragmentVersion,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "configuration")) {
				if (jsonParserFieldValue != null) {
					draftFragmentVersion.setConfiguration(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "css")) {
				if (jsonParserFieldValue != null) {
					draftFragmentVersion.setCss((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "html")) {
				if (jsonParserFieldValue != null) {
					draftFragmentVersion.setHtml((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "js")) {
				if (jsonParserFieldValue != null) {
					draftFragmentVersion.setJs((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					draftFragmentVersion.setStatus(
						DraftFragmentVersion.Status.create(
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
// LIFERAY-REST-BUILDER-HASH:1290913943