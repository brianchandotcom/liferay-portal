/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.Fragment;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

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
public class FragmentSerDes {

	public static Fragment toDTO(String json) {
		FragmentJSONParser fragmentJSONParser = new FragmentJSONParser();

		return fragmentJSONParser.parseToDTO(json);
	}

	public static Fragment[] toDTOs(String json) {
		FragmentJSONParser fragmentJSONParser = new FragmentJSONParser();

		return fragmentJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Fragment fragment) {
		if (fragment == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (fragment.getCollectionName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"collectionName\": ");

			sb.append("\"");

			sb.append(_escape(fragment.getCollectionName()));

			sb.append("\"");
		}

		if (fragment.getKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"key\": ");

			sb.append("\"");

			sb.append(_escape(fragment.getKey()));

			sb.append("\"");
		}

		if (fragment.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(fragment.getName()));

			sb.append("\"");
		}

		if (fragment.getSiteKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteKey\": ");

			sb.append("\"");

			sb.append(_escape(fragment.getSiteKey()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FragmentJSONParser fragmentJSONParser = new FragmentJSONParser();

		return fragmentJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Fragment fragment) {
		if (fragment == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (fragment.getCollectionName() == null) {
			map.put("collectionName", null);
		}
		else {
			map.put(
				"collectionName", String.valueOf(fragment.getCollectionName()));
		}

		if (fragment.getKey() == null) {
			map.put("key", null);
		}
		else {
			map.put("key", String.valueOf(fragment.getKey()));
		}

		if (fragment.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(fragment.getName()));
		}

		if (fragment.getSiteKey() == null) {
			map.put("siteKey", null);
		}
		else {
			map.put("siteKey", String.valueOf(fragment.getSiteKey()));
		}

		return map;
	}

	public static class FragmentJSONParser extends BaseJSONParser<Fragment> {

		@Override
		protected Fragment createDTO() {
			return new Fragment();
		}

		@Override
		protected Fragment[] createDTOArray(int size) {
			return new Fragment[size];
		}

		@Override
		protected void setField(
			Fragment fragment, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "collectionName")) {
				if (jsonParserFieldValue != null) {
					fragment.setCollectionName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "key")) {
				if (jsonParserFieldValue != null) {
					fragment.setKey((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					fragment.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteKey")) {
				if (jsonParserFieldValue != null) {
					fragment.setSiteKey((String)jsonParserFieldValue);
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