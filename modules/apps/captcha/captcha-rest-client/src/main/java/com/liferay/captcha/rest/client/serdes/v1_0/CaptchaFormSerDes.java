/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.rest.client.serdes.v1_0;

import com.liferay.captcha.rest.client.dto.v1_0.CaptchaForm;
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
public class CaptchaFormSerDes {

	public static CaptchaForm toDTO(String json) {
		CaptchaFormJSONParser captchaFormJSONParser =
			new CaptchaFormJSONParser();

		return captchaFormJSONParser.parseToDTO(json);
	}

	public static CaptchaForm[] toDTOs(String json) {
		CaptchaFormJSONParser captchaFormJSONParser =
			new CaptchaFormJSONParser();

		return captchaFormJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CaptchaForm captchaForm) {
		if (captchaForm == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (captchaForm.getAnswer() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"answer\": ");

			sb.append("\"");

			sb.append(_escape(captchaForm.getAnswer()));

			sb.append("\"");
		}

		if (captchaForm.getCaptchaToken() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"captchaToken\": ");

			sb.append("\"");

			sb.append(_escape(captchaForm.getCaptchaToken()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CaptchaFormJSONParser captchaFormJSONParser =
			new CaptchaFormJSONParser();

		return captchaFormJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(CaptchaForm captchaForm) {
		if (captchaForm == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (captchaForm.getAnswer() == null) {
			map.put("answer", null);
		}
		else {
			map.put("answer", String.valueOf(captchaForm.getAnswer()));
		}

		if (captchaForm.getCaptchaToken() == null) {
			map.put("captchaToken", null);
		}
		else {
			map.put(
				"captchaToken", String.valueOf(captchaForm.getCaptchaToken()));
		}

		return map;
	}

	public static class CaptchaFormJSONParser
		extends BaseJSONParser<CaptchaForm> {

		@Override
		protected CaptchaForm createDTO() {
			return new CaptchaForm();
		}

		@Override
		protected CaptchaForm[] createDTOArray(int size) {
			return new CaptchaForm[size];
		}

		@Override
		protected void setField(
			CaptchaForm captchaForm, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "answer")) {
				if (jsonParserFieldValue != null) {
					captchaForm.setAnswer((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "captchaToken")) {
				if (jsonParserFieldValue != null) {
					captchaForm.setCaptchaToken((String)jsonParserFieldValue);
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