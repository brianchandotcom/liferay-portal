/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.FormConfig;
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
public class FormConfigSerDes {

	public static FormConfig toDTO(String json) {
		FormConfigJSONParser formConfigJSONParser = new FormConfigJSONParser();

		return formConfigJSONParser.parseToDTO(json);
	}

	public static FormConfig[] toDTOs(String json) {
		FormConfigJSONParser formConfigJSONParser = new FormConfigJSONParser();

		return formConfigJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FormConfig formConfig) {
		if (formConfig == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (formConfig.getFormReference() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formReference\": ");

			if (formConfig.getFormReference() instanceof String) {
				sb.append("\"");
				sb.append((String)formConfig.getFormReference());
				sb.append("\"");
			}
			else {
				sb.append(formConfig.getFormReference());
			}
		}

		if (formConfig.getFormSuccessSubmissionResult() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"formSuccessSubmissionResult\": ");

			if (formConfig.getFormSuccessSubmissionResult() instanceof String) {
				sb.append("\"");
				sb.append((String)formConfig.getFormSuccessSubmissionResult());
				sb.append("\"");
			}
			else {
				sb.append(formConfig.getFormSuccessSubmissionResult());
			}
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FormConfigJSONParser formConfigJSONParser = new FormConfigJSONParser();

		return formConfigJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(FormConfig formConfig) {
		if (formConfig == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (formConfig.getFormReference() == null) {
			map.put("formReference", null);
		}
		else {
			map.put(
				"formReference", String.valueOf(formConfig.getFormReference()));
		}

		if (formConfig.getFormSuccessSubmissionResult() == null) {
			map.put("formSuccessSubmissionResult", null);
		}
		else {
			map.put(
				"formSuccessSubmissionResult",
				String.valueOf(formConfig.getFormSuccessSubmissionResult()));
		}

		return map;
	}

	public static class FormConfigJSONParser
		extends BaseJSONParser<FormConfig> {

		@Override
		protected FormConfig createDTO() {
			return new FormConfig();
		}

		@Override
		protected FormConfig[] createDTOArray(int size) {
			return new FormConfig[size];
		}

		@Override
		protected void setField(
			FormConfig formConfig, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "formReference")) {
				if (jsonParserFieldValue != null) {
					formConfig.setFormReference((Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "formSuccessSubmissionResult")) {

				if (jsonParserFieldValue != null) {
					formConfig.setFormSuccessSubmissionResult(
						(Object)jsonParserFieldValue);
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