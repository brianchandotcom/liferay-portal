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

import java.util.Collection;
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
			return "{}";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"autocomplete\": ");

		sb.append(field.getAutocomplete());
		sb.append(", ");

		sb.append("\"dataSourceType\": ");

		sb.append("\"");
		sb.append(field.getDataSourceType());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dataType\": ");

		sb.append("\"");
		sb.append(field.getDataType());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"displayStyle\": ");

		sb.append("\"");
		sb.append(field.getDisplayStyle());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"grid\": ");

		sb.append(field.getGrid());
		sb.append(", ");

		sb.append("\"hasFormRules\": ");

		sb.append(field.getHasFormRules());
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(field.getId());
		sb.append(", ");

		sb.append("\"immutable\": ");

		sb.append(field.getImmutable());
		sb.append(", ");

		sb.append("\"inline\": ");

		sb.append(field.getInline());
		sb.append(", ");

		sb.append("\"inputControl\": ");

		sb.append("\"");
		sb.append(field.getInputControl());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"label\": ");

		sb.append("\"");
		sb.append(field.getLabel());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"localizable\": ");

		sb.append(field.getLocalizable());
		sb.append(", ");

		sb.append("\"multiple\": ");

		sb.append(field.getMultiple());
		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(field.getName());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"options\": ");

		if (field.getOptions() == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < field.getOptions().length; i++) {
				sb.append(field.getOptions()[i]);

				if ((i + 1) < field.getOptions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"placeholder\": ");

		sb.append("\"");
		sb.append(field.getPlaceholder());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"predefinedValue\": ");

		sb.append("\"");
		sb.append(field.getPredefinedValue());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"readOnly\": ");

		sb.append(field.getReadOnly());
		sb.append(", ");

		sb.append("\"repeatable\": ");

		sb.append(field.getRepeatable());
		sb.append(", ");

		sb.append("\"required\": ");

		sb.append(field.getRequired());
		sb.append(", ");

		sb.append("\"showAsSwitcher\": ");

		sb.append(field.getShowAsSwitcher());
		sb.append(", ");

		sb.append("\"showLabel\": ");

		sb.append(field.getShowLabel());
		sb.append(", ");

		sb.append("\"style\": ");

		sb.append("\"");
		sb.append(field.getStyle());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"text\": ");

		sb.append("\"");
		sb.append(field.getText());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"tooltip\": ");

		sb.append("\"");
		sb.append(field.getTooltip());
		sb.append("\"");
		sb.append(", ");

		sb.append("\"validation\": ");

		sb.append(field.getValidation());

		sb.append("}");

		return sb.toString();
	}

	public static String toJSON(Collection<Field> fields) {
		if (fields == null) {
			return "[]";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (Field field : fields) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(toJSON(field));
		}

		sb.append("]");

		return sb.toString();
	}

	private static class FieldJSONParser extends BaseJSONParser<Field> {

		protected Field createDTO() {
			return new Field();
		}

		protected Field[] createDTOArray(int size) {
			return new Field[size];
		}

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
					field.setId((Long)jsonParserFieldValue);
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