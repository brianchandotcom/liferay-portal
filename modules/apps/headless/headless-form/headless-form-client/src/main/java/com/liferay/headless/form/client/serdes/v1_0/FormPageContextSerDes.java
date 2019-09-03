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

import com.liferay.headless.form.client.dto.v1_0.FormFieldContext;
import com.liferay.headless.form.client.dto.v1_0.FormPageContext;
import com.liferay.headless.form.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FormPageContextSerDes {

	public static FormPageContext toDTO(String json) {
		FormPageContextJSONParser formPageContextJSONParser =
			new FormPageContextJSONParser();

		return formPageContextJSONParser.parseToDTO(json);
	}

	public static FormPageContext[] toDTOs(String json) {
		FormPageContextJSONParser formPageContextJSONParser =
			new FormPageContextJSONParser();

		return formPageContextJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FormPageContext formPageContext) {
		if (formPageContext == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (formPageContext.getEnabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enabled\": ");

			sb.append(formPageContext.getEnabled());
		}

		if (formPageContext.getFormFieldContexts() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formFieldContexts\": ");

			sb.append("[");

			for (int i = 0; i < formPageContext.getFormFieldContexts().length;
				 i++) {

				sb.append(
					String.valueOf(formPageContext.getFormFieldContexts()[i]));

				if ((i + 1) < formPageContext.getFormFieldContexts().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (formPageContext.getShowRequiredFieldsWarning() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"showRequiredFieldsWarning\": ");

			sb.append(formPageContext.getShowRequiredFieldsWarning());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FormPageContextJSONParser formPageContextJSONParser =
			new FormPageContextJSONParser();

		return formPageContextJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(FormPageContext formPageContext) {
		if (formPageContext == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (formPageContext.getEnabled() == null) {
			map.put("enabled", null);
		}
		else {
			map.put("enabled", String.valueOf(formPageContext.getEnabled()));
		}

		if (formPageContext.getFormFieldContexts() == null) {
			map.put("formFieldContexts", null);
		}
		else {
			map.put(
				"formFieldContexts",
				String.valueOf(formPageContext.getFormFieldContexts()));
		}

		if (formPageContext.getShowRequiredFieldsWarning() == null) {
			map.put("showRequiredFieldsWarning", null);
		}
		else {
			map.put(
				"showRequiredFieldsWarning",
				String.valueOf(formPageContext.getShowRequiredFieldsWarning()));
		}

		return map;
	}

	public static class FormPageContextJSONParser
		extends BaseJSONParser<FormPageContext> {

		@Override
		protected FormPageContext createDTO() {
			return new FormPageContext();
		}

		@Override
		protected FormPageContext[] createDTOArray(int size) {
			return new FormPageContext[size];
		}

		@Override
		protected void setField(
			FormPageContext formPageContext, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "enabled")) {
				if (jsonParserFieldValue != null) {
					formPageContext.setEnabled((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "formFieldContexts")) {
				if (jsonParserFieldValue != null) {
					formPageContext.setFormFieldContexts(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FormFieldContextSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new FormFieldContext[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "showRequiredFieldsWarning")) {

				if (jsonParserFieldValue != null) {
					formPageContext.setShowRequiredFieldsWarning(
						(Boolean)jsonParserFieldValue);
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
				sb.append(_escape(entry.getValue()));
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