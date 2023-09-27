/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.client.serdes.v1_0;

import com.liferay.headless.admin.user.client.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

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
public class WebUrlSerDes {

	public static WebUrl toDTO(String json) {
		WebUrlJSONParser webUrlJSONParser = new WebUrlJSONParser();

		return webUrlJSONParser.parseToDTO(json);
	}

	public static WebUrl[] toDTOs(String json) {
		WebUrlJSONParser webUrlJSONParser = new WebUrlJSONParser();

		return webUrlJSONParser.parseToDTOs(json);
	}

	public static String toJSON(WebUrl webUrl) {
		if (webUrl == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (webUrl.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(webUrl.getId());
		}

		if (webUrl.getPrimary() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"primary\": ");

			sb.append(webUrl.getPrimary());
		}

		if (webUrl.getUrl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"url\": ");

			sb.append("\"");

			sb.append(_escape(webUrl.getUrl()));

			sb.append("\"");
		}

		if (webUrl.getUrlType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"urlType\": ");

			sb.append("\"");

			sb.append(_escape(webUrl.getUrlType()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		WebUrlJSONParser webUrlJSONParser = new WebUrlJSONParser();

		return webUrlJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(WebUrl webUrl) {
		if (webUrl == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (webUrl.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(webUrl.getId()));
		}

		if (webUrl.getPrimary() == null) {
			map.put("primary", null);
		}
		else {
			map.put("primary", String.valueOf(webUrl.getPrimary()));
		}

		if (webUrl.getUrl() == null) {
			map.put("url", null);
		}
		else {
			map.put("url", String.valueOf(webUrl.getUrl()));
		}

		if (webUrl.getUrlType() == null) {
			map.put("urlType", null);
		}
		else {
			map.put("urlType", String.valueOf(webUrl.getUrlType()));
		}

		return map;
	}

	public static class WebUrlJSONParser extends BaseJSONParser<WebUrl> {

		@Override
		protected WebUrl createDTO() {
			return new WebUrl();
		}

		@Override
		protected WebUrl[] createDTOArray(int size) {
			return new WebUrl[size];
		}

		@Override
		protected void setField(
			WebUrl webUrl, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					webUrl.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "primary")) {
				if (jsonParserFieldValue != null) {
					webUrl.setPrimary((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "url")) {
				if (jsonParserFieldValue != null) {
					webUrl.setUrl((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "urlType")) {
				if (jsonParserFieldValue != null) {
					webUrl.setUrlType((String)jsonParserFieldValue);
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