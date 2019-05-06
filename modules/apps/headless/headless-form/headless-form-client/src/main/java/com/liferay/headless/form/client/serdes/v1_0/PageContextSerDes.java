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

package com.liferay.headless.form.client.serdes.v1_0;

import com.liferay.headless.form.client.dto.v1_0.FieldContext;
import com.liferay.headless.form.client.dto.v1_0.PageContext;
import com.liferay.headless.form.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class PageContextSerDes {

	public static PageContext toDTO(String json) {
		PageContextJSONParser pageContextJSONParser =
			new PageContextJSONParser();

		return pageContextJSONParser.parseToDTO(json);
	}

	public static PageContext[] toDTOs(String json) {
		PageContextJSONParser pageContextJSONParser =
			new PageContextJSONParser();

		return pageContextJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PageContext pageContext) {
		if (pageContext == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (pageContext.getEnabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enabled\": ");

			sb.append(pageContext.getEnabled());
		}

		if (pageContext.getFieldContexts() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fieldContexts\": ");

			sb.append("[");

			for (int i = 0; i < pageContext.getFieldContexts().length; i++) {
				sb.append(String.valueOf(pageContext.getFieldContexts()[i]));

				if ((i + 1) < pageContext.getFieldContexts().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (pageContext.getShowRequiredFieldsWarning() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"showRequiredFieldsWarning\": ");

			sb.append(pageContext.getShowRequiredFieldsWarning());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PageContextJSONParser pageContextJSONParser =
			new PageContextJSONParser();

		return pageContextJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(PageContext pageContext) {
		if (pageContext == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (pageContext.getEnabled() == null) {
			map.put("enabled", null);
		}
		else {
			map.put("enabled", String.valueOf(pageContext.getEnabled()));
		}

		if (pageContext.getFieldContexts() == null) {
			map.put("fieldContexts", null);
		}
		else {
			map.put(
				"fieldContexts",
				String.valueOf(pageContext.getFieldContexts()));
		}

		if (pageContext.getShowRequiredFieldsWarning() == null) {
			map.put("showRequiredFieldsWarning", null);
		}
		else {
			map.put(
				"showRequiredFieldsWarning",
				String.valueOf(pageContext.getShowRequiredFieldsWarning()));
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

	private static class PageContextJSONParser
		extends BaseJSONParser<PageContext> {

		@Override
		protected PageContext createDTO() {
			return new PageContext();
		}

		@Override
		protected PageContext[] createDTOArray(int size) {
			return new PageContext[size];
		}

		@Override
		protected void setField(
			PageContext pageContext, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "enabled")) {
				if (jsonParserFieldValue != null) {
					pageContext.setEnabled((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fieldContexts")) {
				if (jsonParserFieldValue != null) {
					pageContext.setFieldContexts(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FieldContextSerDes.toDTO((String)object)
						).toArray(
							size -> new FieldContext[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "showRequiredFieldsWarning")) {

				if (jsonParserFieldValue != null) {
					pageContext.setShowRequiredFieldsWarning(
						(Boolean)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}