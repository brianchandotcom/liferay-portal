/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.client.serdes.v1_0;

import com.liferay.headless.admin.fragment.client.dto.v1_0.ApprovedFragmentVersion;
import com.liferay.headless.admin.fragment.client.dto.v1_0.DraftFragmentVersion;
import com.liferay.headless.admin.fragment.client.dto.v1_0.FragmentVersion;
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
public class FragmentVersionSerDes {

	public static FragmentVersion toDTO(String json) {
		FragmentVersionJSONParser fragmentVersionJSONParser =
			new FragmentVersionJSONParser();

		return fragmentVersionJSONParser.parseToDTO(json);
	}

	public static FragmentVersion[] toDTOs(String json) {
		FragmentVersionJSONParser fragmentVersionJSONParser =
			new FragmentVersionJSONParser();

		return fragmentVersionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FragmentVersion fragmentVersion) {
		if (fragmentVersion == null) {
			return "null";
		}

		FragmentVersion.Status status = fragmentVersion.getStatus();

		if (status != null) {
			String statusString = status.toString();

			if (statusString.equals("Approved")) {
				return ApprovedFragmentVersionSerDes.toJSON(
					(ApprovedFragmentVersion)fragmentVersion);
			}

			if (statusString.equals("Draft")) {
				return DraftFragmentVersionSerDes.toJSON(
					(DraftFragmentVersion)fragmentVersion);
			}

			throw new IllegalArgumentException(
				"Unknown status " + statusString);
		}
		else {
			throw new IllegalArgumentException("Missing status parameter");
		}
	}

	public static Map<String, Object> toMap(String json) {
		FragmentVersionJSONParser fragmentVersionJSONParser =
			new FragmentVersionJSONParser();

		return fragmentVersionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(FragmentVersion fragmentVersion) {
		if (fragmentVersion == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (fragmentVersion.getCss() == null) {
			map.put("css", null);
		}
		else {
			map.put("css", String.valueOf(fragmentVersion.getCss()));
		}

		if (fragmentVersion.getHtml() == null) {
			map.put("html", null);
		}
		else {
			map.put("html", String.valueOf(fragmentVersion.getHtml()));
		}

		if (fragmentVersion.getJs() == null) {
			map.put("js", null);
		}
		else {
			map.put("js", String.valueOf(fragmentVersion.getJs()));
		}

		if (fragmentVersion.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(fragmentVersion.getStatus()));
		}

		return map;
	}

	public static class FragmentVersionJSONParser
		extends BaseJSONParser<FragmentVersion> {

		@Override
		protected FragmentVersion createDTO() {
			return null;
		}

		@Override
		protected FragmentVersion[] createDTOArray(int size) {
			return new FragmentVersion[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "css")) {
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
		public FragmentVersion parseToDTO(String json) {
			Map<String, Object> jsonMap = parseToMap(json);

			Object status = jsonMap.get("status");

			if (status != null) {
				String statusString = status.toString();

				if (statusString.equals("Approved")) {
					return ApprovedFragmentVersion.toDTO(json);
				}

				if (statusString.equals("Draft")) {
					return DraftFragmentVersion.toDTO(json);
				}

				throw new IllegalArgumentException(
					"Unknown status " + statusString);
			}
			else {
				throw new IllegalArgumentException("Missing status parameter");
			}
		}

		@Override
		protected void setField(
			FragmentVersion fragmentVersion, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "css")) {
				if (jsonParserFieldValue != null) {
					fragmentVersion.setCss((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "html")) {
				if (jsonParserFieldValue != null) {
					fragmentVersion.setHtml((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "js")) {
				if (jsonParserFieldValue != null) {
					fragmentVersion.setJs((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					fragmentVersion.setStatus(
						FragmentVersion.Status.create(
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
// LIFERAY-REST-BUILDER-HASH:853713944