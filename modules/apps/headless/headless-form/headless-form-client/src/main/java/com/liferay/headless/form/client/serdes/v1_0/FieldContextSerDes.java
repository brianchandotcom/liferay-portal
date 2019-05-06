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
import com.liferay.headless.form.client.dto.v1_0.Option;
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
public class FieldContextSerDes {

	public static FieldContext toDTO(String json) {
		FieldContextJSONParser fieldContextJSONParser =
			new FieldContextJSONParser();

		return fieldContextJSONParser.parseToDTO(json);
	}

	public static FieldContext[] toDTOs(String json) {
		FieldContextJSONParser fieldContextJSONParser =
			new FieldContextJSONParser();

		return fieldContextJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FieldContext fieldContext) {
		if (fieldContext == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (fieldContext.getEvaluable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"evaluable\": ");

			sb.append(fieldContext.getEvaluable());
		}

		if (fieldContext.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(fieldContext.getName()));

			sb.append("\"");
		}

		if (fieldContext.getOptions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"options\": ");

			sb.append("[");

			for (int i = 0; i < fieldContext.getOptions().length; i++) {
				sb.append(String.valueOf(fieldContext.getOptions()[i]));

				if ((i + 1) < fieldContext.getOptions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (fieldContext.getReadOnly() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"readOnly\": ");

			sb.append(fieldContext.getReadOnly());
		}

		if (fieldContext.getRequired() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"required\": ");

			sb.append(fieldContext.getRequired());
		}

		if (fieldContext.getValid() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"valid\": ");

			sb.append(fieldContext.getValid());
		}

		if (fieldContext.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\": ");

			sb.append("\"");

			sb.append(_escape(fieldContext.getValue()));

			sb.append("\"");
		}

		if (fieldContext.getValueChanged() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"valueChanged\": ");

			sb.append(fieldContext.getValueChanged());
		}

		if (fieldContext.getVisible() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"visible\": ");

			sb.append(fieldContext.getVisible());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FieldContextJSONParser fieldContextJSONParser =
			new FieldContextJSONParser();

		return fieldContextJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(FieldContext fieldContext) {
		if (fieldContext == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (fieldContext.getEvaluable() == null) {
			map.put("evaluable", null);
		}
		else {
			map.put("evaluable", String.valueOf(fieldContext.getEvaluable()));
		}

		if (fieldContext.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(fieldContext.getName()));
		}

		if (fieldContext.getOptions() == null) {
			map.put("options", null);
		}
		else {
			map.put("options", String.valueOf(fieldContext.getOptions()));
		}

		if (fieldContext.getReadOnly() == null) {
			map.put("readOnly", null);
		}
		else {
			map.put("readOnly", String.valueOf(fieldContext.getReadOnly()));
		}

		if (fieldContext.getRequired() == null) {
			map.put("required", null);
		}
		else {
			map.put("required", String.valueOf(fieldContext.getRequired()));
		}

		if (fieldContext.getValid() == null) {
			map.put("valid", null);
		}
		else {
			map.put("valid", String.valueOf(fieldContext.getValid()));
		}

		if (fieldContext.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(fieldContext.getValue()));
		}

		if (fieldContext.getValueChanged() == null) {
			map.put("valueChanged", null);
		}
		else {
			map.put(
				"valueChanged", String.valueOf(fieldContext.getValueChanged()));
		}

		if (fieldContext.getVisible() == null) {
			map.put("visible", null);
		}
		else {
			map.put("visible", String.valueOf(fieldContext.getVisible()));
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

	private static class FieldContextJSONParser
		extends BaseJSONParser<FieldContext> {

		@Override
		protected FieldContext createDTO() {
			return new FieldContext();
		}

		@Override
		protected FieldContext[] createDTOArray(int size) {
			return new FieldContext[size];
		}

		@Override
		protected void setField(
			FieldContext fieldContext, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "evaluable")) {
				if (jsonParserFieldValue != null) {
					fieldContext.setEvaluable((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					fieldContext.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "options")) {
				if (jsonParserFieldValue != null) {
					fieldContext.setOptions(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> OptionSerDes.toDTO((String)object)
						).toArray(
							size -> new Option[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "readOnly")) {
				if (jsonParserFieldValue != null) {
					fieldContext.setReadOnly((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "required")) {
				if (jsonParserFieldValue != null) {
					fieldContext.setRequired((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "valid")) {
				if (jsonParserFieldValue != null) {
					fieldContext.setValid((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					fieldContext.setValue((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "valueChanged")) {
				if (jsonParserFieldValue != null) {
					fieldContext.setValueChanged((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "visible")) {
				if (jsonParserFieldValue != null) {
					fieldContext.setVisible((Boolean)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}