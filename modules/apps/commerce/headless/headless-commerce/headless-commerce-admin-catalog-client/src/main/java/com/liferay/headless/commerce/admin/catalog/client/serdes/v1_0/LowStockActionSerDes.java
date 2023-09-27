/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.client.serdes.v1_0;

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.LowStockAction;
import com.liferay.headless.commerce.admin.catalog.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class LowStockActionSerDes {

	public static LowStockAction toDTO(String json) {
		LowStockActionJSONParser lowStockActionJSONParser =
			new LowStockActionJSONParser();

		return lowStockActionJSONParser.parseToDTO(json);
	}

	public static LowStockAction[] toDTOs(String json) {
		LowStockActionJSONParser lowStockActionJSONParser =
			new LowStockActionJSONParser();

		return lowStockActionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(LowStockAction lowStockAction) {
		if (lowStockAction == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (lowStockAction.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(lowStockAction.getKey()));

			sb.append("\"");
		}

		if (lowStockAction.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append(_toJSON(lowStockAction.getLabel()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		LowStockActionJSONParser lowStockActionJSONParser =
			new LowStockActionJSONParser();

		return lowStockActionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(LowStockAction lowStockAction) {
		if (lowStockAction == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (lowStockAction.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(lowStockAction.getKey()));
		}

		if (lowStockAction.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put("label", String.valueOf(lowStockAction.getLabel()));
		}

		return map;
	}

	public static class LowStockActionJSONParser
		extends BaseJSONParser<LowStockAction> {

		@Override
		protected LowStockAction createDTO() {
			return new LowStockAction();
		}

		@Override
		protected LowStockAction[] createDTOArray(int size) {
			return new LowStockAction[size];
		}

		@Override
		protected void setField(
			LowStockAction lowStockAction, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					lowStockAction.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					lowStockAction.setLabel(
						(Map)LowStockActionSerDes.toMap(
							(String)jsonParserFieldValue));
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