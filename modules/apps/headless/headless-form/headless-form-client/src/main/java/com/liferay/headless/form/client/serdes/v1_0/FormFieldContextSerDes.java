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
import com.liferay.headless.form.client.dto.v1_0.FormFieldOption;
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
public class FormFieldContextSerDes {

	public static FormFieldContext toDTO(String json) {
		FormFieldContextJSONParser formFieldContextJSONParser =
			new FormFieldContextJSONParser();

		return formFieldContextJSONParser.parseToDTO(json);
	}

	public static FormFieldContext[] toDTOs(String json) {
		FormFieldContextJSONParser formFieldContextJSONParser =
			new FormFieldContextJSONParser();

		return formFieldContextJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FormFieldContext formFieldContext) {
		if (formFieldContext == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (formFieldContext.getEvaluable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"evaluable\": ");

			sb.append(formFieldContext.getEvaluable());
		}

		if (formFieldContext.getFormFieldOptions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formFieldOptions\": ");

			sb.append("[");

			for (int i = 0; i < formFieldContext.getFormFieldOptions().length;
				 i++) {

				sb.append(
					String.valueOf(formFieldContext.getFormFieldOptions()[i]));

				if ((i + 1) < formFieldContext.getFormFieldOptions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (formFieldContext.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(formFieldContext.getName()));

			sb.append("\"");
		}

		if (formFieldContext.getReadOnly() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"readOnly\": ");

			sb.append(formFieldContext.getReadOnly());
		}

		if (formFieldContext.getRequired() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"required\": ");

			sb.append(formFieldContext.getRequired());
		}

		if (formFieldContext.getValid() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"valid\": ");

			sb.append(formFieldContext.getValid());
		}

		if (formFieldContext.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append("\"");

			sb.append(_escape(formFieldContext.getValue()));

			sb.append("\"");
		}

		if (formFieldContext.getValueChanged() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"valueChanged\": ");

			sb.append(formFieldContext.getValueChanged());
		}

		if (formFieldContext.getVisible() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"visible\": ");

			sb.append(formFieldContext.getVisible());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FormFieldContextJSONParser formFieldContextJSONParser =
			new FormFieldContextJSONParser();

		return formFieldContextJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(FormFieldContext formFieldContext) {
		if (formFieldContext == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (formFieldContext.getEvaluable() == null) {
			map.put("evaluable", null);
		}
		else {
			map.put(
				"evaluable", String.valueOf(formFieldContext.getEvaluable()));
		}

		if (formFieldContext.getFormFieldOptions() == null) {
			map.put("formFieldOptions", null);
		}
		else {
			map.put(
				"formFieldOptions",
				String.valueOf(formFieldContext.getFormFieldOptions()));
		}

		if (formFieldContext.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(formFieldContext.getName()));
		}

		if (formFieldContext.getReadOnly() == null) {
			map.put("readOnly", null);
		}
		else {
			map.put("readOnly", String.valueOf(formFieldContext.getReadOnly()));
		}

		if (formFieldContext.getRequired() == null) {
			map.put("required", null);
		}
		else {
			map.put("required", String.valueOf(formFieldContext.getRequired()));
		}

		if (formFieldContext.getValid() == null) {
			map.put("valid", null);
		}
		else {
			map.put("valid", String.valueOf(formFieldContext.getValid()));
		}

		if (formFieldContext.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(formFieldContext.getValue()));
		}

		if (formFieldContext.getValueChanged() == null) {
			map.put("valueChanged", null);
		}
		else {
			map.put(
				"valueChanged",
				String.valueOf(formFieldContext.getValueChanged()));
		}

		if (formFieldContext.getVisible() == null) {
			map.put("visible", null);
		}
		else {
			map.put("visible", String.valueOf(formFieldContext.getVisible()));
		}

		return map;
	}

	public static class FormFieldContextJSONParser
		extends BaseJSONParser<FormFieldContext> {

		@Override
		protected FormFieldContext createDTO() {
			return new FormFieldContext();
		}

		@Override
		protected FormFieldContext[] createDTOArray(int size) {
			return new FormFieldContext[size];
		}

		@Override
		protected void setField(
			FormFieldContext formFieldContext, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "evaluable")) {
				if (jsonParserFieldValue != null) {
					formFieldContext.setEvaluable(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "formFieldOptions")) {
				if (jsonParserFieldValue != null) {
					formFieldContext.setFormFieldOptions(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FormFieldOptionSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new FormFieldOption[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					formFieldContext.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "readOnly")) {
				if (jsonParserFieldValue != null) {
					formFieldContext.setReadOnly((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "required")) {
				if (jsonParserFieldValue != null) {
					formFieldContext.setRequired((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "valid")) {
				if (jsonParserFieldValue != null) {
					formFieldContext.setValid((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					formFieldContext.setValue((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "valueChanged")) {
				if (jsonParserFieldValue != null) {
					formFieldContext.setValueChanged(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "visible")) {
				if (jsonParserFieldValue != null) {
					formFieldContext.setVisible((Boolean)jsonParserFieldValue);
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