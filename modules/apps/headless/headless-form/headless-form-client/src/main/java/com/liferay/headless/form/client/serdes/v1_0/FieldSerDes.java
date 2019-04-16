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

		sb.append("\"autocomplete\": ");

		if (field.getAutocomplete() == null) {
			sb.append("null");
		}
		else {
			sb.append(field.getAutocomplete());
		}

		sb.append(", ");

		sb.append("\"dataSourceType\": ");

		if (field.getDataSourceType() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(field.getDataSourceType());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"dataType\": ");

		if (field.getDataType() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(field.getDataType());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"displayStyle\": ");

		if (field.getDisplayStyle() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(field.getDisplayStyle());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"grid\": ");

		sb.append(GridSerDes.toJSON(field.getGrid()));
		sb.append(", ");

		sb.append("\"hasFormRules\": ");

		if (field.getHasFormRules() == null) {
			sb.append("null");
		}
		else {
			sb.append(field.getHasFormRules());
		}

		sb.append(", ");

		sb.append("\"id\": ");

		if (field.getId() == null) {
			sb.append("null");
		}
		else {
			sb.append(field.getId());
		}

		sb.append(", ");

		sb.append("\"immutable\": ");

		if (field.getImmutable() == null) {
			sb.append("null");
		}
		else {
			sb.append(field.getImmutable());
		}

		sb.append(", ");

		sb.append("\"inline\": ");

		if (field.getInline() == null) {
			sb.append("null");
		}
		else {
			sb.append(field.getInline());
		}

		sb.append(", ");

		sb.append("\"inputControl\": ");

		if (field.getInputControl() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(field.getInputControl());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"label\": ");

		if (field.getLabel() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(field.getLabel());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"localizable\": ");

		if (field.getLocalizable() == null) {
			sb.append("null");
		}
		else {
			sb.append(field.getLocalizable());
		}

		sb.append(", ");

		sb.append("\"multiple\": ");

		if (field.getMultiple() == null) {
			sb.append("null");
		}
		else {
			sb.append(field.getMultiple());
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (field.getName() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(field.getName());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"options\": ");

		if (field.getOptions() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < field.getOptions().length; i++) {
				sb.append(OptionSerDes.toJSON(field.getOptions()[i]));

				if ((i + 1) < field.getOptions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"placeholder\": ");

		if (field.getPlaceholder() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(field.getPlaceholder());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"predefinedValue\": ");

		if (field.getPredefinedValue() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(field.getPredefinedValue());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"readOnly\": ");

		if (field.getReadOnly() == null) {
			sb.append("null");
		}
		else {
			sb.append(field.getReadOnly());
		}

		sb.append(", ");

		sb.append("\"repeatable\": ");

		if (field.getRepeatable() == null) {
			sb.append("null");
		}
		else {
			sb.append(field.getRepeatable());
		}

		sb.append(", ");

		sb.append("\"required\": ");

		if (field.getRequired() == null) {
			sb.append("null");
		}
		else {
			sb.append(field.getRequired());
		}

		sb.append(", ");

		sb.append("\"showAsSwitcher\": ");

		if (field.getShowAsSwitcher() == null) {
			sb.append("null");
		}
		else {
			sb.append(field.getShowAsSwitcher());
		}

		sb.append(", ");

		sb.append("\"showLabel\": ");

		if (field.getShowLabel() == null) {
			sb.append("null");
		}
		else {
			sb.append(field.getShowLabel());
		}

		sb.append(", ");

		sb.append("\"style\": ");

		if (field.getStyle() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(field.getStyle());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"text\": ");

		if (field.getText() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(field.getText());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"tooltip\": ");

		if (field.getTooltip() == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");

			sb.append(field.getTooltip());

			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"validation\": ");

		sb.append(ValidationSerDes.toJSON(field.getValidation()));

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(Field field) {
		if (field == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		map.put("autocomplete", String.valueOf(field.getAutocomplete()));

		map.put("dataSourceType", String.valueOf(field.getDataSourceType()));

		map.put("dataType", String.valueOf(field.getDataType()));

		map.put("displayStyle", String.valueOf(field.getDisplayStyle()));

		map.put("grid", GridSerDes.toJSON(field.getGrid()));

		map.put("hasFormRules", String.valueOf(field.getHasFormRules()));

		map.put("id", String.valueOf(field.getId()));

		map.put("immutable", String.valueOf(field.getImmutable()));

		map.put("inline", String.valueOf(field.getInline()));

		map.put("inputControl", String.valueOf(field.getInputControl()));

		map.put("label", String.valueOf(field.getLabel()));

		map.put("localizable", String.valueOf(field.getLocalizable()));

		map.put("multiple", String.valueOf(field.getMultiple()));

		map.put("name", String.valueOf(field.getName()));

		map.put("options", String.valueOf(field.getOptions()));

		map.put("placeholder", String.valueOf(field.getPlaceholder()));

		map.put("predefinedValue", String.valueOf(field.getPredefinedValue()));

		map.put("readOnly", String.valueOf(field.getReadOnly()));

		map.put("repeatable", String.valueOf(field.getRepeatable()));

		map.put("required", String.valueOf(field.getRequired()));

		map.put("showAsSwitcher", String.valueOf(field.getShowAsSwitcher()));

		map.put("showLabel", String.valueOf(field.getShowLabel()));

		map.put("style", String.valueOf(field.getStyle()));

		map.put("text", String.valueOf(field.getText()));

		map.put("tooltip", String.valueOf(field.getTooltip()));

		map.put("validation", ValidationSerDes.toJSON(field.getValidation()));

		return map;
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