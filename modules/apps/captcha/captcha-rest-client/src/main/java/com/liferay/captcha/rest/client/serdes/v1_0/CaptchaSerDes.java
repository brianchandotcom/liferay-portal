/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.rest.client.serdes.v1_0;

import com.liferay.captcha.rest.client.dto.v1_0.Captcha;
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
public class CaptchaSerDes {

	public static Captcha toDTO(String json) {
		CaptchaJSONParser captchaJSONParser = new CaptchaJSONParser();

		return captchaJSONParser.parseToDTO(json);
	}

	public static Captcha[] toDTOs(String json) {
		CaptchaJSONParser captchaJSONParser = new CaptchaJSONParser();

		return captchaJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Captcha captcha) {
		if (captcha == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (captcha.getCaptchaToken() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"captchaToken\": ");

			sb.append("\"");

			sb.append(_escape(captcha.getCaptchaToken()));

			sb.append("\"");
		}

		if (captcha.getImage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"image\": ");

			sb.append("\"");

			sb.append(_escape(captcha.getImage()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CaptchaJSONParser captchaJSONParser = new CaptchaJSONParser();

		return captchaJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Captcha captcha) {
		if (captcha == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (captcha.getCaptchaToken() == null) {
			map.put("captchaToken", null);
		}
		else {
			map.put("captchaToken", String.valueOf(captcha.getCaptchaToken()));
		}

		if (captcha.getImage() == null) {
			map.put("image", null);
		}
		else {
			map.put("image", String.valueOf(captcha.getImage()));
		}

		return map;
	}

	public static class CaptchaJSONParser extends BaseJSONParser<Captcha> {

		@Override
		protected Captcha createDTO() {
			return new Captcha();
		}

		@Override
		protected Captcha[] createDTOArray(int size) {
			return new Captcha[size];
		}

		@Override
		protected void setField(
			Captcha captcha, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "captchaToken")) {
				if (jsonParserFieldValue != null) {
					captcha.setCaptchaToken((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "image")) {
				if (jsonParserFieldValue != null) {
					captcha.setImage((String)jsonParserFieldValue);
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