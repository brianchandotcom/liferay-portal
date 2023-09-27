/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.rest.client.serdes.v1_0;

import com.liferay.batch.planner.rest.client.dto.v1_0.SiteScope;
import com.liferay.batch.planner.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Matija Petanjek
 * @generated
 */
@Generated("")
public class SiteScopeSerDes {

	public static SiteScope toDTO(String json) {
		SiteScopeJSONParser siteScopeJSONParser = new SiteScopeJSONParser();

		return siteScopeJSONParser.parseToDTO(json);
	}

	public static SiteScope[] toDTOs(String json) {
		SiteScopeJSONParser siteScopeJSONParser = new SiteScopeJSONParser();

		return siteScopeJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SiteScope siteScope) {
		if (siteScope == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (siteScope.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append("\"");

			sb.append(_escape(siteScope.getLabel()));

			sb.append("\"");
		}

		if (siteScope.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append(siteScope.getValue());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SiteScopeJSONParser siteScopeJSONParser = new SiteScopeJSONParser();

		return siteScopeJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SiteScope siteScope) {
		if (siteScope == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (siteScope.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put("label", String.valueOf(siteScope.getLabel()));
		}

		if (siteScope.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(siteScope.getValue()));
		}

		return map;
	}

	public static class SiteScopeJSONParser extends BaseJSONParser<SiteScope> {

		@Override
		protected SiteScope createDTO() {
			return new SiteScope();
		}

		@Override
		protected SiteScope[] createDTOArray(int size) {
			return new SiteScope[size];
		}

		@Override
		protected void setField(
			SiteScope siteScope, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					siteScope.setLabel((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					siteScope.setValue(
						Long.valueOf((String)jsonParserFieldValue));
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
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}