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

package com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.NodeKey;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.PauseNodeKeys;
import com.liferay.portal.workflow.metrics.rest.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

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

		Map<String, String> map = new HashMap<>();

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

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
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
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static class PauseNodeKeysJSONParser
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
					pauseNodeKeys.setNodeKeys(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> NodeKeySerDes.toDTO((String)object)
						).toArray(
							size -> new NodeKey[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "status")) {
				if (jsonParserFieldValue != null) {
					pauseNodeKeys.setStatus(
						Integer.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}