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

package com.liferay.app.builder.rest.client.serdes.v1_0;

import com.liferay.app.builder.rest.client.dto.v1_0.AppDeployment;
import com.liferay.app.builder.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
public class AppDeploymentSerDes {

	public static AppDeployment toDTO(String json) {
		AppDeploymentJSONParser appDeploymentJSONParser =
			new AppDeploymentJSONParser();

		return appDeploymentJSONParser.parseToDTO(json);
	}

	public static AppDeployment[] toDTOs(String json) {
		AppDeploymentJSONParser appDeploymentJSONParser =
			new AppDeploymentJSONParser();

		return appDeploymentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AppDeployment appDeployment) {
		if (appDeployment == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (appDeployment.getSettings() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"settings\": ");

			sb.append(_toJSON(appDeployment.getSettings()));
		}

		if (appDeployment.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(appDeployment.getType()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AppDeploymentJSONParser appDeploymentJSONParser =
			new AppDeploymentJSONParser();

		return appDeploymentJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(AppDeployment appDeployment) {
		if (appDeployment == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (appDeployment.getSettings() == null) {
			map.put("settings", null);
		}
		else {
			map.put("settings", String.valueOf(appDeployment.getSettings()));
		}

		if (appDeployment.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(appDeployment.getType()));
		}

		return map;
	}

	public static class AppDeploymentJSONParser
		extends BaseJSONParser<AppDeployment> {

		@Override
		protected AppDeployment createDTO() {
			return new AppDeployment();
		}

		@Override
		protected AppDeployment[] createDTOArray(int size) {
			return new AppDeployment[size];
		}

		@Override
		protected void setField(
			AppDeployment appDeployment, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "settings")) {
				if (jsonParserFieldValue != null) {
					appDeployment.setSettings(
						(Map)AppDeploymentSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					appDeployment.setType((String)jsonParserFieldValue);
				}
			}
			else if (jsonParserFieldName.equals("status")) {
				throw new IllegalArgumentException();
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
			sb.append("\":");

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
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}