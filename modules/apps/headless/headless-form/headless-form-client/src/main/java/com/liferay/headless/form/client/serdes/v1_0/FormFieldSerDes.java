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

import com.liferay.headless.form.client.dto.v1_0.FormField;
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
public class FormFieldSerDes {

	public static FormField toDTO(String json) {
		FormFieldJSONParser formFieldJSONParser = new FormFieldJSONParser();

		return formFieldJSONParser.parseToDTO(json);
	}

	public static FormField[] toDTOs(String json) {
		FormFieldJSONParser formFieldJSONParser = new FormFieldJSONParser();

		return formFieldJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FormField formField) {
		if (formField == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (formField.getAutocomplete() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"autocomplete\": ");

			sb.append(formField.getAutocomplete());
		}

		if (formField.getDataSourceType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataSourceType\": ");

			sb.append("\"");

			sb.append(_escape(formField.getDataSourceType()));

			sb.append("\"");
		}

		if (formField.getDataType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataType\": ");

			sb.append("\"");

			sb.append(_escape(formField.getDataType()));

			sb.append("\"");
		}

		if (formField.getDisplayStyle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayStyle\": ");

			sb.append("\"");

			sb.append(_escape(formField.getDisplayStyle()));

			sb.append("\"");
		}

		if (formField.getFormFieldOptions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formFieldOptions\": ");

			sb.append("[");

			for (int i = 0; i < formField.getFormFieldOptions().length; i++) {
				sb.append(String.valueOf(formField.getFormFieldOptions()[i]));

				if ((i + 1) < formField.getFormFieldOptions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (formField.getGrid() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"grid\": ");

			sb.append(String.valueOf(formField.getGrid()));
		}

		if (formField.getHasFormRules() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"hasFormRules\": ");

			sb.append(formField.getHasFormRules());
		}

		if (formField.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(formField.getId());
		}

		if (formField.getImmutable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"immutable\": ");

			sb.append(formField.getImmutable());
		}

		if (formField.getInline() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inline\": ");

			sb.append(formField.getInline());
		}

		if (formField.getInputControl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inputControl\": ");

			sb.append("\"");

			sb.append(_escape(formField.getInputControl()));

			sb.append("\"");
		}

		if (formField.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append("\"");

			sb.append(_escape(formField.getLabel()));

			sb.append("\"");
		}

		if (formField.getLocalizable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"localizable\": ");

			sb.append(formField.getLocalizable());
		}

		if (formField.getMultiple() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"multiple\": ");

			sb.append(formField.getMultiple());
		}

		if (formField.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(formField.getName()));

			sb.append("\"");
		}

		if (formField.getPlaceholder() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"placeholder\": ");

			sb.append("\"");

			sb.append(_escape(formField.getPlaceholder()));

			sb.append("\"");
		}

		if (formField.getPredefinedValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"predefinedValue\": ");

			sb.append("\"");

			sb.append(_escape(formField.getPredefinedValue()));

			sb.append("\"");
		}

		if (formField.getReadOnly() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"readOnly\": ");

			sb.append(formField.getReadOnly());
		}

		if (formField.getRepeatable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"repeatable\": ");

			sb.append(formField.getRepeatable());
		}

		if (formField.getRequired() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"required\": ");

			sb.append(formField.getRequired());
		}

		if (formField.getShowAsSwitcher() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"showAsSwitcher\": ");

			sb.append(formField.getShowAsSwitcher());
		}

		if (formField.getShowLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"showLabel\": ");

			sb.append(formField.getShowLabel());
		}

		if (formField.getStyle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"style\": ");

			sb.append("\"");

			sb.append(_escape(formField.getStyle()));

			sb.append("\"");
		}

		if (formField.getText() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"text\": ");

			sb.append("\"");

			sb.append(_escape(formField.getText()));

			sb.append("\"");
		}

		if (formField.getTooltip() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"tooltip\": ");

			sb.append("\"");

			sb.append(_escape(formField.getTooltip()));

			sb.append("\"");
		}

		if (formField.getValidation() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"validation\": ");

			sb.append(String.valueOf(formField.getValidation()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FormFieldJSONParser formFieldJSONParser = new FormFieldJSONParser();

		return formFieldJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(FormField formField) {
		if (formField == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (formField.getAutocomplete() == null) {
			map.put("autocomplete", null);
		}
		else {
			map.put(
				"autocomplete", String.valueOf(formField.getAutocomplete()));
		}

		if (formField.getDataSourceType() == null) {
			map.put("dataSourceType", null);
		}
		else {
			map.put(
				"dataSourceType",
				String.valueOf(formField.getDataSourceType()));
		}

		if (formField.getDataType() == null) {
			map.put("dataType", null);
		}
		else {
			map.put("dataType", String.valueOf(formField.getDataType()));
		}

		if (formField.getDisplayStyle() == null) {
			map.put("displayStyle", null);
		}
		else {
			map.put(
				"displayStyle", String.valueOf(formField.getDisplayStyle()));
		}

		if (formField.getFormFieldOptions() == null) {
			map.put("formFieldOptions", null);
		}
		else {
			map.put(
				"formFieldOptions",
				String.valueOf(formField.getFormFieldOptions()));
		}

		if (formField.getGrid() == null) {
			map.put("grid", null);
		}
		else {
			map.put("grid", String.valueOf(formField.getGrid()));
		}

		if (formField.getHasFormRules() == null) {
			map.put("hasFormRules", null);
		}
		else {
			map.put(
				"hasFormRules", String.valueOf(formField.getHasFormRules()));
		}

		if (formField.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(formField.getId()));
		}

		if (formField.getImmutable() == null) {
			map.put("immutable", null);
		}
		else {
			map.put("immutable", String.valueOf(formField.getImmutable()));
		}

		if (formField.getInline() == null) {
			map.put("inline", null);
		}
		else {
			map.put("inline", String.valueOf(formField.getInline()));
		}

		if (formField.getInputControl() == null) {
			map.put("inputControl", null);
		}
		else {
			map.put(
				"inputControl", String.valueOf(formField.getInputControl()));
		}

		if (formField.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put("label", String.valueOf(formField.getLabel()));
		}

		if (formField.getLocalizable() == null) {
			map.put("localizable", null);
		}
		else {
			map.put("localizable", String.valueOf(formField.getLocalizable()));
		}

		if (formField.getMultiple() == null) {
			map.put("multiple", null);
		}
		else {
			map.put("multiple", String.valueOf(formField.getMultiple()));
		}

		if (formField.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(formField.getName()));
		}

		if (formField.getPlaceholder() == null) {
			map.put("placeholder", null);
		}
		else {
			map.put("placeholder", String.valueOf(formField.getPlaceholder()));
		}

		if (formField.getPredefinedValue() == null) {
			map.put("predefinedValue", null);
		}
		else {
			map.put(
				"predefinedValue",
				String.valueOf(formField.getPredefinedValue()));
		}

		if (formField.getReadOnly() == null) {
			map.put("readOnly", null);
		}
		else {
			map.put("readOnly", String.valueOf(formField.getReadOnly()));
		}

		if (formField.getRepeatable() == null) {
			map.put("repeatable", null);
		}
		else {
			map.put("repeatable", String.valueOf(formField.getRepeatable()));
		}

		if (formField.getRequired() == null) {
			map.put("required", null);
		}
		else {
			map.put("required", String.valueOf(formField.getRequired()));
		}

		if (formField.getShowAsSwitcher() == null) {
			map.put("showAsSwitcher", null);
		}
		else {
			map.put(
				"showAsSwitcher",
				String.valueOf(formField.getShowAsSwitcher()));
		}

		if (formField.getShowLabel() == null) {
			map.put("showLabel", null);
		}
		else {
			map.put("showLabel", String.valueOf(formField.getShowLabel()));
		}

		if (formField.getStyle() == null) {
			map.put("style", null);
		}
		else {
			map.put("style", String.valueOf(formField.getStyle()));
		}

		if (formField.getText() == null) {
			map.put("text", null);
		}
		else {
			map.put("text", String.valueOf(formField.getText()));
		}

		if (formField.getTooltip() == null) {
			map.put("tooltip", null);
		}
		else {
			map.put("tooltip", String.valueOf(formField.getTooltip()));
		}

		if (formField.getValidation() == null) {
			map.put("validation", null);
		}
		else {
			map.put("validation", String.valueOf(formField.getValidation()));
		}

		return map;
	}

	public static class FormFieldJSONParser extends BaseJSONParser<FormField> {

		@Override
		protected FormField createDTO() {
			return new FormField();
		}

		@Override
		protected FormField[] createDTOArray(int size) {
			return new FormField[size];
		}

		@Override
		protected void setField(
			FormField formField, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "autocomplete")) {
				if (jsonParserFieldValue != null) {
					formField.setAutocomplete((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataSourceType")) {
				if (jsonParserFieldValue != null) {
					formField.setDataSourceType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataType")) {
				if (jsonParserFieldValue != null) {
					formField.setDataType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "displayStyle")) {
				if (jsonParserFieldValue != null) {
					formField.setDisplayStyle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "formFieldOptions")) {
				if (jsonParserFieldValue != null) {
					formField.setFormFieldOptions(
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
			else if (Objects.equals(jsonParserFieldName, "grid")) {
				if (jsonParserFieldValue != null) {
					formField.setGrid(
						GridSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "hasFormRules")) {
				if (jsonParserFieldValue != null) {
					formField.setHasFormRules((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					formField.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "immutable")) {
				if (jsonParserFieldValue != null) {
					formField.setImmutable((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "inline")) {
				if (jsonParserFieldValue != null) {
					formField.setInline((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "inputControl")) {
				if (jsonParserFieldValue != null) {
					formField.setInputControl((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					formField.setLabel((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "localizable")) {
				if (jsonParserFieldValue != null) {
					formField.setLocalizable((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "multiple")) {
				if (jsonParserFieldValue != null) {
					formField.setMultiple((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					formField.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "placeholder")) {
				if (jsonParserFieldValue != null) {
					formField.setPlaceholder((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "predefinedValue")) {
				if (jsonParserFieldValue != null) {
					formField.setPredefinedValue((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "readOnly")) {
				if (jsonParserFieldValue != null) {
					formField.setReadOnly((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "repeatable")) {
				if (jsonParserFieldValue != null) {
					formField.setRepeatable((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "required")) {
				if (jsonParserFieldValue != null) {
					formField.setRequired((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "showAsSwitcher")) {
				if (jsonParserFieldValue != null) {
					formField.setShowAsSwitcher((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "showLabel")) {
				if (jsonParserFieldValue != null) {
					formField.setShowLabel((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "style")) {
				if (jsonParserFieldValue != null) {
					formField.setStyle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "text")) {
				if (jsonParserFieldValue != null) {
					formField.setText((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "tooltip")) {
				if (jsonParserFieldValue != null) {
					formField.setTooltip((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "validation")) {
				if (jsonParserFieldValue != null) {
					formField.setValidation(
						ValidationSerDes.toDTO((String)jsonParserFieldValue));
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