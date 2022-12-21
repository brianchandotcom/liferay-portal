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

package com.liferay.digital.signature.rest.client.serdes.v1_0;

import com.liferay.digital.signature.rest.client.dto.v1_0.DSEnvelopeSignUrl;
import com.liferay.digital.signature.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author José Abelenda
 * @generated
 */
@Generated("")
public class DSEnvelopeSignUrlSerDes {

	public static DSEnvelopeSignUrl toDTO(String json) {
		DSEnvelopeSignUrlJSONParser dsEnvelopeSignUrlJSONParser =
			new DSEnvelopeSignUrlJSONParser();

		return dsEnvelopeSignUrlJSONParser.parseToDTO(json);
	}

	public static DSEnvelopeSignUrl[] toDTOs(String json) {
		DSEnvelopeSignUrlJSONParser dsEnvelopeSignUrlJSONParser =
			new DSEnvelopeSignUrlJSONParser();

		return dsEnvelopeSignUrlJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DSEnvelopeSignUrl dsEnvelopeSignUrl) {
		if (dsEnvelopeSignUrl == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dsEnvelopeSignUrl.getUrl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"url\": ");

			sb.append("\"");

			sb.append(_escape(dsEnvelopeSignUrl.getUrl()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DSEnvelopeSignUrlJSONParser dsEnvelopeSignUrlJSONParser =
			new DSEnvelopeSignUrlJSONParser();

		return dsEnvelopeSignUrlJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DSEnvelopeSignUrl dsEnvelopeSignUrl) {

		if (dsEnvelopeSignUrl == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (dsEnvelopeSignUrl.getUrl() == null) {
			map.put("url", null);
		}
		else {
			map.put("url", String.valueOf(dsEnvelopeSignUrl.getUrl()));
		}

		return map;
	}

	public static class DSEnvelopeSignUrlJSONParser
		extends BaseJSONParser<DSEnvelopeSignUrl> {

		@Override
		protected DSEnvelopeSignUrl createDTO() {
			return new DSEnvelopeSignUrl();
		}

		@Override
		protected DSEnvelopeSignUrl[] createDTOArray(int size) {
			return new DSEnvelopeSignUrl[size];
		}

		@Override
		protected void setField(
			DSEnvelopeSignUrl dsEnvelopeSignUrl, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "url")) {
				if (jsonParserFieldValue != null) {
					dsEnvelopeSignUrl.setUrl((String)jsonParserFieldValue);
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