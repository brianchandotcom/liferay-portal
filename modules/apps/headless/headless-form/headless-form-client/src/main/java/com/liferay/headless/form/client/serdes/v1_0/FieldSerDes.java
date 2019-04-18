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

import com.liferay.headless.form.client.dto.v1_0.Field;
import com.liferay.headless.form.client.dto.v1_0.Option;
import com.liferay.headless.form.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FieldSerDes {

	public static Field toDTO(String json) {
		FieldJSONParser fieldJSONParser = new FieldJSONParser();

		return fieldJSONParser.parseToDTO(json);
	}

	public static Field[] toDTOs(String json) {
		FieldJSONParser fieldJSONParser = new FieldJSONParser();

		return fieldJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Field field) {
		if (field == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (field.getAutocomplete() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"autocomplete\":");

			sb.append(field.getAutocomplete());
		}

		if (field.getDataSourceType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataSourceType\":");

			sb.append("\"");

			sb.append(_escape(field.getDataSourceType()));

			sb.append("\"");
		}

		if (field.getDataType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataType\":");

			sb.append("\"");

			sb.append(_escape(field.getDataType()));

			sb.append("\"");
		}

		if (field.getDisplayStyle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayStyle\":");

			sb.append("\"");

			sb.append(_escape(field.getDisplayStyle()));

			sb.append("\"");
		}

		if (field.getGrid() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"grid\":");

			sb.append(GridSerDes.toJSON(field.getGrid()));
		}

		if (field.getHasFormRules() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"hasFormRules\":");

			sb.append(field.getHasFormRules());
		}

		if (field.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\":");

			sb.append(field.getId());
		}

		if (field.getImmutable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"immutable\":");

			sb.append(field.getImmutable());
		}

		if (field.getInline() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inline\":");

			sb.append(field.getInline());
		}

		if (field.getInputControl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inputControl\":");

			sb.append("\"");

			sb.append(_escape(field.getInputControl()));

			sb.append("\"");
		}

		if (field.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\":");

			sb.append("\"");

			sb.append(_escape(field.getLabel()));

			sb.append("\"");
		}

		if (field.getLocalizable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"localizable\":");

			sb.append(field.getLocalizable());
		}

		if (field.getMultiple() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"multiple\":");

			sb.append(field.getMultiple());
		}

		if (field.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\":");

			sb.append("\"");

			sb.append(_escape(field.getName()));

			sb.append("\"");
		}

		if (field.getOptions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"options\":");

			sb.append("[");

			for (int i = 0; i < field.getOptions().length; i++) {
				sb.append(OptionSerDes.toJSON(field.getOptions()[i]));

				if ((i + 1) < field.getOptions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (field.getPlaceholder() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"placeholder\":");

			sb.append("\"");

			sb.append(_escape(field.getPlaceholder()));

			sb.append("\"");
		}

		if (field.getPredefinedValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"predefinedValue\":");

			sb.append("\"");

			sb.append(_escape(field.getPredefinedValue()));

			sb.append("\"");
		}

		if (field.getReadOnly() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"readOnly\":");

			sb.append(field.getReadOnly());
		}

		if (field.getRepeatable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"repeatable\":");

			sb.append(field.getRepeatable());
		}

		if (field.getRequired() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"required\":");

			sb.append(field.getRequired());
		}

		if (field.getShowAsSwitcher() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"showAsSwitcher\":");

			sb.append(field.getShowAsSwitcher());
		}

		if (field.getShowLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"showLabel\":");

			sb.append(field.getShowLabel());
		}

		if (field.getStyle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"style\":");

			sb.append("\"");

			sb.append(_escape(field.getStyle()));

			sb.append("\"");
		}

		if (field.getText() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"text\":");

			sb.append("\"");

			sb.append(_escape(field.getText()));

			sb.append("\"");
		}

		if (field.getTooltip() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"tooltip\":");

			sb.append("\"");

			sb.append(_escape(field.getTooltip()));

			sb.append("\"");
		}

		if (field.getValidation() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"validation\":");

			sb.append(ValidationSerDes.toJSON(field.getValidation()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Field field) {
		if (field == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (field.getAutocomplete() == null) {
			map.put("autocomplete", null);
		}
		else {
			map.put("autocomplete", String.valueOf(field.getAutocomplete()));
		}

		if (field.getDataSourceType() == null) {
			map.put("dataSourceType", null);
		}
		else {
			map.put(
				"dataSourceType", String.valueOf(field.getDataSourceType()));
		}

		if (field.getDataType() == null) {
			map.put("dataType", null);
		}
		else {
			map.put("dataType", String.valueOf(field.getDataType()));
		}

		if (field.getDisplayStyle() == null) {
			map.put("displayStyle", null);
		}
		else {
			map.put("displayStyle", String.valueOf(field.getDisplayStyle()));
		}

		if (field.getGrid() == null) {
			map.put("grid", null);
		}
		else {
			map.put("grid", GridSerDes.toJSON(field.getGrid()));
		}

		if (field.getHasFormRules() == null) {
			map.put("hasFormRules", null);
		}
		else {
			map.put("hasFormRules", String.valueOf(field.getHasFormRules()));
		}

		if (field.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(field.getId()));
		}

		if (field.getImmutable() == null) {
			map.put("immutable", null);
		}
		else {
			map.put("immutable", String.valueOf(field.getImmutable()));
		}

		if (field.getInline() == null) {
			map.put("inline", null);
		}
		else {
			map.put("inline", String.valueOf(field.getInline()));
		}

		if (field.getInputControl() == null) {
			map.put("inputControl", null);
		}
		else {
			map.put("inputControl", String.valueOf(field.getInputControl()));
		}

		if (field.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put("label", String.valueOf(field.getLabel()));
		}

		if (field.getLocalizable() == null) {
			map.put("localizable", null);
		}
		else {
			map.put("localizable", String.valueOf(field.getLocalizable()));
		}

		if (field.getMultiple() == null) {
			map.put("multiple", null);
		}
		else {
			map.put("multiple", String.valueOf(field.getMultiple()));
		}

		if (field.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(field.getName()));
		}

		if (field.getOptions() == null) {
			map.put("options", null);
		}
		else {
			map.put("options", String.valueOf(field.getOptions()));
		}

		if (field.getPlaceholder() == null) {
			map.put("placeholder", null);
		}
		else {
			map.put("placeholder", String.valueOf(field.getPlaceholder()));
		}

		if (field.getPredefinedValue() == null) {
			map.put("predefinedValue", null);
		}
		else {
			map.put(
				"predefinedValue", String.valueOf(field.getPredefinedValue()));
		}

		if (field.getReadOnly() == null) {
			map.put("readOnly", null);
		}
		else {
			map.put("readOnly", String.valueOf(field.getReadOnly()));
		}

		if (field.getRepeatable() == null) {
			map.put("repeatable", null);
		}
		else {
			map.put("repeatable", String.valueOf(field.getRepeatable()));
		}

		if (field.getRequired() == null) {
			map.put("required", null);
		}
		else {
			map.put("required", String.valueOf(field.getRequired()));
		}

		if (field.getShowAsSwitcher() == null) {
			map.put("showAsSwitcher", null);
		}
		else {
			map.put(
				"showAsSwitcher", String.valueOf(field.getShowAsSwitcher()));
		}

		if (field.getShowLabel() == null) {
			map.put("showLabel", null);
		}
		else {
			map.put("showLabel", String.valueOf(field.getShowLabel()));
		}

		if (field.getStyle() == null) {
			map.put("style", null);
		}
		else {
			map.put("style", String.valueOf(field.getStyle()));
		}

		if (field.getText() == null) {
			map.put("text", null);
		}
		else {
			map.put("text", String.valueOf(field.getText()));
		}

		if (field.getTooltip() == null) {
			map.put("tooltip", null);
		}
		else {
			map.put("tooltip", String.valueOf(field.getTooltip()));
		}

		if (field.getValidation() == null) {
			map.put("validation", null);
		}
		else {
			map.put(
				"validation", ValidationSerDes.toJSON(field.getValidation()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class FieldJSONParser extends BaseJSONParser<Field> {

		@Override
		protected Field createDTO() {
			return new Field();
		}

		@Override
		protected Field[] createDTOArray(int size) {
			return new Field[size];
		}

		@Override
		protected void setField(
			Field field, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "autocomplete")) {
				if (jsonParserFieldValue != null) {
					field.setAutocomplete((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataSourceType")) {
				if (jsonParserFieldValue != null) {
					field.setDataSourceType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataType")) {
				if (jsonParserFieldValue != null) {
					field.setDataType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "displayStyle")) {
				if (jsonParserFieldValue != null) {
					field.setDisplayStyle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "grid")) {
				if (jsonParserFieldValue != null) {
					field.setGrid(
						GridSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "hasFormRules")) {
				if (jsonParserFieldValue != null) {
					field.setHasFormRules((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					field.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "immutable")) {
				if (jsonParserFieldValue != null) {
					field.setImmutable((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "inline")) {
				if (jsonParserFieldValue != null) {
					field.setInline((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "inputControl")) {
				if (jsonParserFieldValue != null) {
					field.setInputControl((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					field.setLabel((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "localizable")) {
				if (jsonParserFieldValue != null) {
					field.setLocalizable((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "multiple")) {
				if (jsonParserFieldValue != null) {
					field.setMultiple((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					field.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "options")) {
				if (jsonParserFieldValue != null) {
					field.setOptions(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> OptionSerDes.toDTO((String)object)
						).toArray(
							size -> new Option[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "placeholder")) {
				if (jsonParserFieldValue != null) {
					field.setPlaceholder((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "predefinedValue")) {
				if (jsonParserFieldValue != null) {
					field.setPredefinedValue((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "readOnly")) {
				if (jsonParserFieldValue != null) {
					field.setReadOnly((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "repeatable")) {
				if (jsonParserFieldValue != null) {
					field.setRepeatable((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "required")) {
				if (jsonParserFieldValue != null) {
					field.setRequired((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "showAsSwitcher")) {
				if (jsonParserFieldValue != null) {
					field.setShowAsSwitcher((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "showLabel")) {
				if (jsonParserFieldValue != null) {
					field.setShowLabel((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "style")) {
				if (jsonParserFieldValue != null) {
					field.setStyle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "text")) {
				if (jsonParserFieldValue != null) {
					field.setText((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "tooltip")) {
				if (jsonParserFieldValue != null) {
					field.setTooltip((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "validation")) {
				if (jsonParserFieldValue != null) {
					field.setValidation(
						ValidationSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}