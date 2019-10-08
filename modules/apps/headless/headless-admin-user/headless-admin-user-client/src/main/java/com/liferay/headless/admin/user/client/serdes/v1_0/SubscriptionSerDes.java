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

package com.liferay.headless.admin.user.client.serdes.v1_0;

import com.liferay.headless.admin.user.client.dto.v1_0.Subscription;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
public class SubscriptionSerDes {

	public static Subscription toDTO(String json) {
		SubscriptionJSONParser subscriptionJSONParser =
			new SubscriptionJSONParser();

		return subscriptionJSONParser.parseToDTO(json);
	}

	public static Subscription[] toDTOs(String json) {
		SubscriptionJSONParser subscriptionJSONParser =
			new SubscriptionJSONParser();

		return subscriptionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Subscription subscription) {
		if (subscription == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (subscription.getContentId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentId\": ");

			sb.append("\"");

			sb.append(_escape(subscription.getContentId()));

			sb.append("\"");
		}

		if (subscription.getContentType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contentType\": ");

			sb.append("\"");

			sb.append(_escape(subscription.getContentType()));

			sb.append("\"");
		}

		if (subscription.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(subscription.getDateCreated()));

			sb.append("\"");
		}

		if (subscription.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(subscription.getDateModified()));

			sb.append("\"");
		}

		if (subscription.getFrequency() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"frequency\": ");

			sb.append("\"");

			sb.append(_escape(subscription.getFrequency()));

			sb.append("\"");
		}

		if (subscription.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(subscription.getId());
		}

		if (subscription.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\": ");

			sb.append(subscription.getSiteId());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SubscriptionJSONParser subscriptionJSONParser =
			new SubscriptionJSONParser();

		return subscriptionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Subscription subscription) {
		if (subscription == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (subscription.getContentId() == null) {
			map.put("contentId", null);
		}
		else {
			map.put("contentId", String.valueOf(subscription.getContentId()));
		}

		if (subscription.getContentType() == null) {
			map.put("contentType", null);
		}
		else {
			map.put(
				"contentType", String.valueOf(subscription.getContentType()));
		}

		map.put(
			"dateCreated",
			liferayToJSONDateFormat.format(subscription.getDateCreated()));

		map.put(
			"dateModified",
			liferayToJSONDateFormat.format(subscription.getDateModified()));

		if (subscription.getFrequency() == null) {
			map.put("frequency", null);
		}
		else {
			map.put("frequency", String.valueOf(subscription.getFrequency()));
		}

		if (subscription.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(subscription.getId()));
		}

		if (subscription.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(subscription.getSiteId()));
		}

		return map;
	}

	public static class SubscriptionJSONParser
		extends BaseJSONParser<Subscription> {

		@Override
		protected Subscription createDTO() {
			return new Subscription();
		}

		@Override
		protected Subscription[] createDTOArray(int size) {
			return new Subscription[size];
		}

		@Override
		protected void setField(
			Subscription subscription, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contentId")) {
				if (jsonParserFieldValue != null) {
					subscription.setContentId((Object)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contentType")) {
				if (jsonParserFieldValue != null) {
					subscription.setContentType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					subscription.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					subscription.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "frequency")) {
				if (jsonParserFieldValue != null) {
					subscription.setFrequency((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					subscription.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					subscription.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		string = string.replace("\\", "\\\\");

		return string.replace("\"", "\\\"");
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
			else {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}