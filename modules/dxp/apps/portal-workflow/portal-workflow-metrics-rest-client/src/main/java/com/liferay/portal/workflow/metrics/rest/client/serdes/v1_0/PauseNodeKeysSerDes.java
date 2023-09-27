/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.NodeKey;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.PauseNodeKeys;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public class PauseNodeKeysSerDes {

	public static PauseNodeKeys toDTO(String json) {
		PauseNodeKeysJSONParser pauseNodeKeysJSONParser =
			new PauseNodeKeysJSONParser();

		return pauseNodeKeysJSONParser.parseToDTO(json);
	}

	public static PauseNodeKeys[] toDTOs(String json) {
		PauseNodeKeysJSONParser pauseNodeKeysJSONParser =
			new PauseNodeKeysJSONParser();

		return pauseNodeKeysJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PauseNodeKeys pauseNodeKeys) {
		if (pauseNodeKeys == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (pauseNodeKeys.getNodeKeys() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nodeKeys\": ");

			sb.append("[");

			for (int i = 0; i < pauseNodeKeys.getNodeKeys().length; i++) {
				sb.append(String.valueOf(pauseNodeKeys.getNodeKeys()[i]));

				if ((i + 1) < pauseNodeKeys.getNodeKeys().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (pauseNodeKeys.getStatus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append(pauseNodeKeys.getStatus());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PauseNodeKeysJSONParser pauseNodeKeysJSONParser =
			new PauseNodeKeysJSONParser();

		return pauseNodeKeysJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(PauseNodeKeys pauseNodeKeys) {
		if (pauseNodeKeys == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (pauseNodeKeys.getNodeKeys() == null) {
			map.put("nodeKeys", null);
		}
		else {
			map.put("nodeKeys", String.valueOf(pauseNodeKeys.getNodeKeys()));
		}

		if (pauseNodeKeys.getStatus() == null) {
			map.put("status", null);
		}
		else {
			map.put("status", String.valueOf(pauseNodeKeys.getStatus()));
		}

		return map;
	}

	public static class PauseNodeKeysJSONParser
		extends BaseJSONParser<PauseNodeKeys> {

		@Override
		protected PauseNodeKeys createDTO() {
			return new PauseNodeKeys();
		}

		@Override
		protected PauseNodeKeys[] createDTOArray(int size) {
			return new PauseNodeKeys[size];
		}

		@Override
		protected void setField(
			PauseNodeKeys pauseNodeKeys, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "nodeKeys")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					NodeKey[] nodeKeysArray =
						new NodeKey[jsonParserFieldValues.length];

					for (int i = 0; i < nodeKeysArray.length; i++) {
						nodeKeysArray[i] = NodeKeySerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					pauseNodeKeys.setNodeKeys(nodeKeysArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					pauseNodeKeys.setStatus(
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