/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.SitePageActionExecutionResult;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class SitePageActionExecutionResultSerDes {

	public static SitePageActionExecutionResult toDTO(String json) {
		SitePageActionExecutionResultJSONParser
			sitePageActionExecutionResultJSONParser =
				new SitePageActionExecutionResultJSONParser();

		return sitePageActionExecutionResultJSONParser.parseToDTO(json);
	}

	public static SitePageActionExecutionResult[] toDTOs(String json) {
		SitePageActionExecutionResultJSONParser
			sitePageActionExecutionResultJSONParser =
				new SitePageActionExecutionResultJSONParser();

		return sitePageActionExecutionResultJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		SitePageActionExecutionResult sitePageActionExecutionResult) {

		if (sitePageActionExecutionResult == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (sitePageActionExecutionResult.getItemReference() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"itemReference\": ");

			sb.append(
				String.valueOf(
					sitePageActionExecutionResult.getItemReference()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SitePageActionExecutionResultJSONParser
			sitePageActionExecutionResultJSONParser =
				new SitePageActionExecutionResultJSONParser();

		return sitePageActionExecutionResultJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		SitePageActionExecutionResult sitePageActionExecutionResult) {

		if (sitePageActionExecutionResult == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (sitePageActionExecutionResult.getItemReference() == null) {
			map.put("itemReference", null);
		}
		else {
			map.put(
				"itemReference",
				String.valueOf(
					sitePageActionExecutionResult.getItemReference()));
		}

		return map;
	}

	public static class SitePageActionExecutionResultJSONParser
		extends BaseJSONParser<SitePageActionExecutionResult> {

		@Override
		protected SitePageActionExecutionResult createDTO() {
			return new SitePageActionExecutionResult();
		}

		@Override
		protected SitePageActionExecutionResult[] createDTOArray(int size) {
			return new SitePageActionExecutionResult[size];
		}

		@Override
		protected void setField(
			SitePageActionExecutionResult sitePageActionExecutionResult,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "itemReference")) {
				if (jsonParserFieldValue != null) {
					sitePageActionExecutionResult.setItemReference(
						ClassFieldsReferenceSerDes.toDTO(
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