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

package com.liferay.headless.commerce.machine.learning.client.serdes.v1_0;

import com.liferay.headless.commerce.machine.learning.client.dto.v1_0.Error;
import com.liferay.headless.commerce.machine.learning.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Riccardo Ferrari
 * @generated
 */
@Generated("")
public class ErrorSerDes {

	public static Error toDTO(String json) {
		ErrorJSONParser errorJSONParser = new ErrorJSONParser();

		return errorJSONParser.parseToDTO(json);
	}

	public static Error[] toDTOs(String json) {
		ErrorJSONParser errorJSONParser = new ErrorJSONParser();

		return errorJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Error error) {
		if (error == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (error.getErrorCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"errorCode\": ");

			sb.append(error.getErrorCode());
		}

		if (error.getErrorDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"errorDescription\": ");

			sb.append("\"");

			sb.append(_escape(error.getErrorDescription()));

			sb.append("\"");
		}

		if (error.getMessage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"message\": ");

			sb.append("\"");

			sb.append(_escape(error.getMessage()));

			sb.append("\"");
		}

		if (error.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append(error.getStatus());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ErrorJSONParser errorJSONParser = new ErrorJSONParser();

		return errorJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Error error) {
		if (error == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (error.getErrorCode() == null) {
			map.put("errorCode", null);
		}
		else {
			map.put("errorCode", String.valueOf(error.getErrorCode()));
		}

		if (error.getErrorDescription() == null) {
			map.put("errorDescription", null);
		}
		else {
			map.put(
				"errorDescription",
				String.valueOf(error.getErrorDescription()));
		}

		if (error.getMessage() == null) {
			map.put("message", null);
		}
		else {
			map.put("message", String.valueOf(error.getMessage()));
		}

		if (error.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(error.getStatus()));
		}

		return map;
	}

	public static class ErrorJSONParser extends BaseJSONParser<Error> {

		@Override
		protected Error createDTO() {
			return new Error();
		}

		@Override
		protected Error[] createDTOArray(int size) {
			return new Error[size];
		}

		@Override
		protected void setField(
			Error error, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "errorCode")) {
				if (jsonParserFieldValue != null) {
					error.setErrorCode(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "errorDescription")) {
				if (jsonParserFieldValue != null) {
					error.setErrorDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "message")) {
				if (jsonParserFieldValue != null) {
					error.setMessage((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					error.setStatus(
						Integer.valueOf((String)jsonParserFieldValue));
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