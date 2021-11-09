/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.client.serdes.v1_0;

import com.liferay.search.experiences.rest.client.dto.v1_0.Field;
import com.liferay.search.experiences.rest.client.dto.v1_0.FieldMapping;
import com.liferay.search.experiences.rest.client.dto.v1_0.Option;
import com.liferay.search.experiences.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
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

		if (field.getBoost() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"boost\": ");

			sb.append(field.getBoost());
		}

		if (field.getFieldMappings() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fieldMappings\": ");

			sb.append("[");

			for (int i = 0; i < field.getFieldMappings().length; i++) {
				sb.append(String.valueOf(field.getFieldMappings()[i]));

				if ((i + 1) < field.getFieldMappings().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (field.getHelpText() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"helpText\": ");

			sb.append("\"");

			sb.append(_escape(field.getHelpText()));

			sb.append("\"");
		}

		if (field.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append("\"");

			sb.append(_escape(field.getLabel()));

			sb.append("\"");
		}

		if (field.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(field.getName()));

			sb.append("\"");
		}

		if (field.getNullable() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nullable\": ");

			sb.append(field.getNullable());
		}

		if (field.getOptions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"options\": ");

			sb.append("[");

			for (int i = 0; i < field.getOptions().length; i++) {
				sb.append(String.valueOf(field.getOptions()[i]));

				if ((i + 1) < field.getOptions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (field.getRequired() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"required\": ");

			sb.append(field.getRequired());
		}

		if (field.getUiType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"uiType\": ");

			sb.append("\"");

			sb.append(_escape(field.getUiType()));

			sb.append("\"");
		}

		if (field.getUnit() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"unit\": ");

			sb.append("\"");

			sb.append(_escape(field.getUnit()));

			sb.append("\"");
		}

		if (field.getUnitSuffix() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"unitSuffix\": ");

			sb.append("\"");

			sb.append(_escape(field.getUnitSuffix()));

			sb.append("\"");
		}

		if (field.getValueDefinition() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"valueDefinition\": ");

			sb.append(String.valueOf(field.getValueDefinition()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FieldJSONParser fieldJSONParser = new FieldJSONParser();

		return fieldJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Field field) {
		if (field == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (field.getBoost() == null) {
			map.put("boost", null);
		}
		else {
			map.put("boost", String.valueOf(field.getBoost()));
		}

		if (field.getFieldMappings() == null) {
			map.put("fieldMappings", null);
		}
		else {
			map.put("fieldMappings", String.valueOf(field.getFieldMappings()));
		}

		if (field.getHelpText() == null) {
			map.put("helpText", null);
		}
		else {
			map.put("helpText", String.valueOf(field.getHelpText()));
		}

		if (field.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put("label", String.valueOf(field.getLabel()));
		}

		if (field.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(field.getName()));
		}

		if (field.getNullable() == null) {
			map.put("nullable", null);
		}
		else {
			map.put("nullable", String.valueOf(field.getNullable()));
		}

		if (field.getOptions() == null) {
			map.put("options", null);
		}
		else {
			map.put("options", String.valueOf(field.getOptions()));
		}

		if (field.getRequired() == null) {
			map.put("required", null);
		}
		else {
			map.put("required", String.valueOf(field.getRequired()));
		}

		if (field.getUiType() == null) {
			map.put("uiType", null);
		}
		else {
			map.put("uiType", String.valueOf(field.getUiType()));
		}

		if (field.getUnit() == null) {
			map.put("unit", null);
		}
		else {
			map.put("unit", String.valueOf(field.getUnit()));
		}

		if (field.getUnitSuffix() == null) {
			map.put("unitSuffix", null);
		}
		else {
			map.put("unitSuffix", String.valueOf(field.getUnitSuffix()));
		}

		if (field.getValueDefinition() == null) {
			map.put("valueDefinition", null);
		}
		else {
			map.put(
				"valueDefinition", String.valueOf(field.getValueDefinition()));
		}

		return map;
	}

	public static class FieldJSONParser extends BaseJSONParser<Field> {

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

			if (Objects.equals(jsonParserFieldName, "boost")) {
				if (jsonParserFieldValue != null) {
					field.setBoost((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fieldMappings")) {
				if (jsonParserFieldValue != null) {
					field.setFieldMappings(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> FieldMappingSerDes.toDTO((String)object)
						).toArray(
							size -> new FieldMapping[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "helpText")) {
				if (jsonParserFieldValue != null) {
					field.setHelpText((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					field.setLabel((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					field.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "nullable")) {
				if (jsonParserFieldValue != null) {
					field.setNullable((Boolean)jsonParserFieldValue);
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
			else if (Objects.equals(jsonParserFieldName, "required")) {
				if (jsonParserFieldValue != null) {
					field.setRequired((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "uiType")) {
				if (jsonParserFieldValue != null) {
					field.setUiType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "unit")) {
				if (jsonParserFieldValue != null) {
					field.setUnit((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "unitSuffix")) {
				if (jsonParserFieldValue != null) {
					field.setUnitSuffix((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "valueDefinition")) {
				if (jsonParserFieldValue != null) {
					field.setValueDefinition(
						ValueDefinitionSerDes.toDTO(
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