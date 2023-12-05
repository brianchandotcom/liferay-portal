/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.rest.client.serdes.v1_0;

import com.liferay.captcha.rest.client.dto.v1_0.SimpleCaptchaForm;
import com.liferay.captcha.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Loc Pham
 * @generated
 */
@Generated("")
public class SimpleCaptchaFormSerDes {

	public static SimpleCaptchaForm toDTO(String json) {
		SimpleCaptchaFormJSONParser simpleCaptchaFormJSONParser =
			new SimpleCaptchaFormJSONParser();

		return simpleCaptchaFormJSONParser.parseToDTO(json);
	}

	public static SimpleCaptchaForm[] toDTOs(String json) {
		SimpleCaptchaFormJSONParser simpleCaptchaFormJSONParser =
			new SimpleCaptchaFormJSONParser();

		return simpleCaptchaFormJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SimpleCaptchaForm simpleCaptchaForm) {
		if (simpleCaptchaForm == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (simpleCaptchaForm.getAnswer() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"answer\": ");

			sb.append("\"");

			sb.append(_escape(simpleCaptchaForm.getAnswer()));

			sb.append("\"");
		}

		if (simpleCaptchaForm.getToken() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"token\": ");

			sb.append("\"");

			sb.append(_escape(simpleCaptchaForm.getToken()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SimpleCaptchaFormJSONParser simpleCaptchaFormJSONParser =
			new SimpleCaptchaFormJSONParser();

		return simpleCaptchaFormJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		SimpleCaptchaForm simpleCaptchaForm) {

		if (simpleCaptchaForm == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (simpleCaptchaForm.getAnswer() == null) {
			map.put("answer", null);
		}
		else {
			map.put("answer", String.valueOf(simpleCaptchaForm.getAnswer()));
		}

		if (simpleCaptchaForm.getToken() == null) {
			map.put("token", null);
		}
		else {
			map.put("token", String.valueOf(simpleCaptchaForm.getToken()));
		}

		return map;
	}

	public static class SimpleCaptchaFormJSONParser
		extends BaseJSONParser<SimpleCaptchaForm> {

		@Override
		protected SimpleCaptchaForm createDTO() {
			return new SimpleCaptchaForm();
		}

		@Override
		protected SimpleCaptchaForm[] createDTOArray(int size) {
			return new SimpleCaptchaForm[size];
		}

		@Override
		protected void setField(
			SimpleCaptchaForm simpleCaptchaForm, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "answer")) {
				if (jsonParserFieldValue != null) {
					simpleCaptchaForm.setAnswer((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "token")) {
				if (jsonParserFieldValue != null) {
					simpleCaptchaForm.setToken((String)jsonParserFieldValue);
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