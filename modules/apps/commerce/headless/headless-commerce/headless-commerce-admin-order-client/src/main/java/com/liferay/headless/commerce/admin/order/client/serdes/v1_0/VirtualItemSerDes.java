/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.client.serdes.v1_0;

import com.liferay.headless.commerce.admin.order.client.dto.v1_0.VirtualItem;
import com.liferay.headless.commerce.admin.order.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public class VirtualItemSerDes {

	public static VirtualItem toDTO(String json) {
		VirtualItemJSONParser virtualItemJSONParser =
			new VirtualItemJSONParser();

		return virtualItemJSONParser.parseToDTO(json);
	}

	public static VirtualItem[] toDTOs(String json) {
		VirtualItemJSONParser virtualItemJSONParser =
			new VirtualItemJSONParser();

		return virtualItemJSONParser.parseToDTOs(json);
	}

	public static String toJSON(VirtualItem virtualItem) {
		if (virtualItem == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (virtualItem.getUrl() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"url\": ");

			sb.append("\"");

			sb.append(_escape(virtualItem.getUrl()));

			sb.append("\"");
		}

		if (virtualItem.getUsages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"usages\": ");

			sb.append(virtualItem.getUsages());
		}

		if (virtualItem.getVersion() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"version\": ");

			sb.append("\"");

			sb.append(_escape(virtualItem.getVersion()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		VirtualItemJSONParser virtualItemJSONParser =
			new VirtualItemJSONParser();

		return virtualItemJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(VirtualItem virtualItem) {
		if (virtualItem == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (virtualItem.getUrl() == null) {
			map.put("url", null);
		}
		else {
			map.put("url", String.valueOf(virtualItem.getUrl()));
		}

		if (virtualItem.getUsages() == null) {
			map.put("usages", null);
		}
		else {
			map.put("usages", String.valueOf(virtualItem.getUsages()));
		}

		if (virtualItem.getVersion() == null) {
			map.put("version", null);
		}
		else {
			map.put("version", String.valueOf(virtualItem.getVersion()));
		}

		return map;
	}

	public static class VirtualItemJSONParser
		extends BaseJSONParser<VirtualItem> {

		@Override
		protected VirtualItem createDTO() {
			return new VirtualItem();
		}

		@Override
		protected VirtualItem[] createDTOArray(int size) {
			return new VirtualItem[size];
		}

		@Override
		protected void setField(
			VirtualItem virtualItem, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "url")) {
				if (jsonParserFieldValue != null) {
					virtualItem.setUrl((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "usages")) {
				if (jsonParserFieldValue != null) {
					virtualItem.setUsages(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "version")) {
				if (jsonParserFieldValue != null) {
					virtualItem.setVersion((String)jsonParserFieldValue);
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