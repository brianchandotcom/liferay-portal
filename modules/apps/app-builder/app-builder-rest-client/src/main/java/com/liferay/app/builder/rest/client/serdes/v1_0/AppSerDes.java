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

import com.liferay.app.builder.rest.client.dto.v1_0.App;
import com.liferay.app.builder.rest.client.dto.v1_0.AppDeployment;
import com.liferay.app.builder.rest.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
public class AppSerDes {

	public static App toDTO(String json) {
		AppJSONParser appJSONParser = new AppJSONParser();

		return appJSONParser.parseToDTO(json);
	}

	public static App[] toDTOs(String json) {
		AppJSONParser appJSONParser = new AppJSONParser();

		return appJSONParser.parseToDTOs(json);
	}

	public static String toJSON(App app) {
		if (app == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (app.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(app.getActive());
		}

		if (app.getAppDeployments() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"appDeployments\": ");

			sb.append("[");

			for (int i = 0; i < app.getAppDeployments().length; i++) {
				sb.append(String.valueOf(app.getAppDeployments()[i]));

				if ((i + 1) < app.getAppDeployments().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (app.getDataDefinitionId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataDefinitionId\": ");

			sb.append(app.getDataDefinitionId());
		}

		if (app.getDataDefinitionName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataDefinitionName\": ");

			sb.append("\"");

			sb.append(_escape(app.getDataDefinitionName()));

			sb.append("\"");
		}

		if (app.getDataLayoutId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataLayoutId\": ");

			sb.append(app.getDataLayoutId());
		}

		if (app.getDataListViewId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataListViewId\": ");

			sb.append(app.getDataListViewId());
		}

		if (app.getDataRecordCollectionId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dataRecordCollectionId\": ");

			sb.append(app.getDataRecordCollectionId());
		}

		if (app.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(app.getDateCreated()));

			sb.append("\"");
		}

		if (app.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(app.getDateModified()));

			sb.append("\"");
		}

		if (app.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(app.getId());
		}

		if (app.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append(_toJSON(app.getName()));
		}

		if (app.getScope() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"scope\": ");

			sb.append("\"");

			sb.append(_escape(app.getScope()));

			sb.append("\"");
		}

		if (app.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\": ");

			sb.append(app.getSiteId());
		}

		if (app.getUserId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userId\": ");

			sb.append(app.getUserId());
		}

		if (app.getVersion() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"version\": ");

			sb.append("\"");

			sb.append(_escape(app.getVersion()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AppJSONParser appJSONParser = new AppJSONParser();

		return appJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(App app) {
		if (app == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (app.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(app.getActive()));
		}

		if (app.getAppDeployments() == null) {
			map.put("appDeployments", null);
		}
		else {
			map.put("appDeployments", String.valueOf(app.getAppDeployments()));
		}

		if (app.getDataDefinitionId() == null) {
			map.put("dataDefinitionId", null);
		}
		else {
			map.put(
				"dataDefinitionId", String.valueOf(app.getDataDefinitionId()));
		}

		if (app.getDataDefinitionName() == null) {
			map.put("dataDefinitionName", null);
		}
		else {
			map.put(
				"dataDefinitionName",
				String.valueOf(app.getDataDefinitionName()));
		}

		if (app.getDataLayoutId() == null) {
			map.put("dataLayoutId", null);
		}
		else {
			map.put("dataLayoutId", String.valueOf(app.getDataLayoutId()));
		}

		if (app.getDataListViewId() == null) {
			map.put("dataListViewId", null);
		}
		else {
			map.put("dataListViewId", String.valueOf(app.getDataListViewId()));
		}

		if (app.getDataRecordCollectionId() == null) {
			map.put("dataRecordCollectionId", null);
		}
		else {
			map.put(
				"dataRecordCollectionId",
				String.valueOf(app.getDataRecordCollectionId()));
		}

		if (app.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(app.getDateCreated()));
		}

		if (app.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(app.getDateModified()));
		}

		if (app.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(app.getId()));
		}

		if (app.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(app.getName()));
		}

		if (app.getScope() == null) {
			map.put("scope", null);
		}
		else {
			map.put("scope", String.valueOf(app.getScope()));
		}

		if (app.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(app.getSiteId()));
		}

		if (app.getUserId() == null) {
			map.put("userId", null);
		}
		else {
			map.put("userId", String.valueOf(app.getUserId()));
		}

		if (app.getVersion() == null) {
			map.put("version", null);
		}
		else {
			map.put("version", String.valueOf(app.getVersion()));
		}

		return map;
	}

	public static class AppJSONParser extends BaseJSONParser<App> {

		@Override
		protected App createDTO() {
			return new App();
		}

		@Override
		protected App[] createDTOArray(int size) {
			return new App[size];
		}

		@Override
		protected void setField(
			App app, String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					app.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "appDeployments")) {
				if (jsonParserFieldValue != null) {
					app.setAppDeployments(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> AppDeploymentSerDes.toDTO((String)object)
						).toArray(
							size -> new AppDeployment[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataDefinitionId")) {
				if (jsonParserFieldValue != null) {
					app.setDataDefinitionId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "dataDefinitionName")) {

				if (jsonParserFieldValue != null) {
					app.setDataDefinitionName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataLayoutId")) {
				if (jsonParserFieldValue != null) {
					app.setDataLayoutId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dataListViewId")) {
				if (jsonParserFieldValue != null) {
					app.setDataListViewId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "dataRecordCollectionId")) {

				if (jsonParserFieldValue != null) {
					app.setDataRecordCollectionId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					app.setDateCreated(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					app.setDateModified(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					app.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					app.setName(
						(Map)AppSerDes.toMap((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "scope")) {
				if (jsonParserFieldValue != null) {
					app.setScope((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					app.setSiteId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "userId")) {
				if (jsonParserFieldValue != null) {
					app.setUserId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "version")) {
				if (jsonParserFieldValue != null) {
					app.setVersion((String)jsonParserFieldValue);
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