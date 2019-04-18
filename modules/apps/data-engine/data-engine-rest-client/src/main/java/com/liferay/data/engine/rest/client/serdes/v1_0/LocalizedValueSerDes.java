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

package com.liferay.data.engine.rest.client.serdes.v1_0;

import com.liferay.data.engine.rest.client.dto.v1_0.LocalizedValue;
import com.liferay.data.engine.rest.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class LocalizedValueSerDes {

	public static LocalizedValue toDTO(String json) {
		LocalizedValueJSONParser localizedValueJSONParser =
			new LocalizedValueJSONParser();

		return localizedValueJSONParser.parseToDTO(json);
	}

	public static LocalizedValue[] toDTOs(String json) {
		LocalizedValueJSONParser localizedValueJSONParser =
			new LocalizedValueJSONParser();

		return localizedValueJSONParser.parseToDTOs(json);
	}

	public static String toJSON(LocalizedValue localizedValue) {
		if (localizedValue == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (localizedValue.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\":");

			sb.append("\"");

			sb.append(_escape(localizedValue.getKey()));

			sb.append("\"");
		}

		if (localizedValue.getValue() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"value\":");

			sb.append("\"");

			sb.append(_escape(localizedValue.getValue()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(LocalizedValue localizedValue) {
		if (localizedValue == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (localizedValue.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(localizedValue.getKey()));
		}

		if (localizedValue.getValue() == null) {
			map.put("value", null);
		}
		else {
			map.put("value", String.valueOf(localizedValue.getValue()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class LocalizedValueJSONParser
		extends BaseJSONParser<LocalizedValue> {

		@Override
		protected LocalizedValue createDTO() {
			return new LocalizedValue();
		}

		@Override
		protected LocalizedValue[] createDTOArray(int size) {
			return new LocalizedValue[size];
		}

		@Override
		protected void setField(
			LocalizedValue localizedValue, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					localizedValue.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "value")) {
				if (jsonParserFieldValue != null) {
					localizedValue.setValue((Object)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}