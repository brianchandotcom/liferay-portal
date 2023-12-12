/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.rest.client.serdes.v1_0;

import com.liferay.captcha.rest.client.dto.v1_0.SimpleCaptcha;
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
public class SimpleCaptchaSerDes {

	public static SimpleCaptcha toDTO(String json) {
		SimpleCaptchaJSONParser simpleCaptchaJSONParser =
			new SimpleCaptchaJSONParser();

		return simpleCaptchaJSONParser.parseToDTO(json);
	}

	public static SimpleCaptcha[] toDTOs(String json) {
		SimpleCaptchaJSONParser simpleCaptchaJSONParser =
			new SimpleCaptchaJSONParser();

		return simpleCaptchaJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SimpleCaptcha simpleCaptcha) {
		if (simpleCaptcha == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (simpleCaptcha.getAnswer() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"answer\": ");

			sb.append("\"");

			sb.append(_escape(simpleCaptcha.getAnswer()));

			sb.append("\"");
		}

		if (simpleCaptcha.getImage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"image\": ");

			sb.append("\"");

			sb.append(_escape(simpleCaptcha.getImage()));

			sb.append("\"");
		}

		if (simpleCaptcha.getToken() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"token\": ");

			sb.append("\"");

			sb.append(_escape(simpleCaptcha.getToken()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SimpleCaptchaJSONParser simpleCaptchaJSONParser =
			new SimpleCaptchaJSONParser();

		return simpleCaptchaJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SimpleCaptcha simpleCaptcha) {
		if (simpleCaptcha == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (simpleCaptcha.getAnswer() == null) {
			map.put("answer", null);
		}
		else {
			map.put("answer", String.valueOf(simpleCaptcha.getAnswer()));
		}

		if (simpleCaptcha.getImage() == null) {
			map.put("image", null);
		}
		else {
			map.put("image", String.valueOf(simpleCaptcha.getImage()));
		}

		if (simpleCaptcha.getToken() == null) {
			map.put("token", null);
		}
		else {
			map.put("token", String.valueOf(simpleCaptcha.getToken()));
		}

		return map;
	}

	public static class SimpleCaptchaJSONParser
		extends BaseJSONParser<SimpleCaptcha> {

		@Override
		protected SimpleCaptcha createDTO() {
			return new SimpleCaptcha();
		}

		@Override
		protected SimpleCaptcha[] createDTOArray(int size) {
			return new SimpleCaptcha[size];
		}

		@Override
		protected void setField(
			SimpleCaptcha simpleCaptcha, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "answer")) {
				if (jsonParserFieldValue != null) {
					simpleCaptcha.setAnswer((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "image")) {
				if (jsonParserFieldValue != null) {
					simpleCaptcha.setImage((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "token")) {
				if (jsonParserFieldValue != null) {
					simpleCaptcha.setToken((String)jsonParserFieldValue);
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