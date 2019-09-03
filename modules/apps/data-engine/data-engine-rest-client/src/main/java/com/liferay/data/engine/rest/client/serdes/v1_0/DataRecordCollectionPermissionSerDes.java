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

import com.liferay.data.engine.rest.client.dto.v1_0.DataRecordCollectionPermission;
import com.liferay.data.engine.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class DataRecordCollectionPermissionSerDes {

	public static DataRecordCollectionPermission toDTO(String json) {
		DataRecordCollectionPermissionJSONParser
			dataRecordCollectionPermissionJSONParser =
				new DataRecordCollectionPermissionJSONParser();

		return dataRecordCollectionPermissionJSONParser.parseToDTO(json);
	}

	public static DataRecordCollectionPermission[] toDTOs(String json) {
		DataRecordCollectionPermissionJSONParser
			dataRecordCollectionPermissionJSONParser =
				new DataRecordCollectionPermissionJSONParser();

		return dataRecordCollectionPermissionJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		DataRecordCollectionPermission dataRecordCollectionPermission) {

		if (dataRecordCollectionPermission == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataRecordCollectionPermission.getAddDataRecord() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addDataRecord\": ");

			sb.append(dataRecordCollectionPermission.getAddDataRecord());
		}

		if (dataRecordCollectionPermission.getAddDataRecordCollection() !=
				null) {

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"addDataRecordCollection\": ");

			sb.append(
				dataRecordCollectionPermission.getAddDataRecordCollection());
		}

		if (dataRecordCollectionPermission.getDefinePermissions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"definePermissions\": ");

			sb.append(dataRecordCollectionPermission.getDefinePermissions());
		}

		if (dataRecordCollectionPermission.getDelete() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"delete\": ");

			sb.append(dataRecordCollectionPermission.getDelete());
		}

		if (dataRecordCollectionPermission.getDeleteDataRecord() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"deleteDataRecord\": ");

			sb.append(dataRecordCollectionPermission.getDeleteDataRecord());
		}

		if (dataRecordCollectionPermission.getExportDataRecord() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"exportDataRecord\": ");

			sb.append(dataRecordCollectionPermission.getExportDataRecord());
		}

		if (dataRecordCollectionPermission.getRoleNames() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"roleNames\": ");

			sb.append("[");

			for (int i = 0;
				 i < dataRecordCollectionPermission.getRoleNames().length;
				 i++) {

				sb.append("\"");

				sb.append(
					_escape(dataRecordCollectionPermission.getRoleNames()[i]));

				sb.append("\"");

				if ((i + 1) <
						dataRecordCollectionPermission.getRoleNames().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (dataRecordCollectionPermission.getUpdate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"update\": ");

			sb.append(dataRecordCollectionPermission.getUpdate());
		}

		if (dataRecordCollectionPermission.getUpdateDataRecord() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"updateDataRecord\": ");

			sb.append(dataRecordCollectionPermission.getUpdateDataRecord());
		}

		if (dataRecordCollectionPermission.getView() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"view\": ");

			sb.append(dataRecordCollectionPermission.getView());
		}

		if (dataRecordCollectionPermission.getViewDataRecord() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"viewDataRecord\": ");

			sb.append(dataRecordCollectionPermission.getViewDataRecord());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DataRecordCollectionPermissionJSONParser
			dataRecordCollectionPermissionJSONParser =
				new DataRecordCollectionPermissionJSONParser();

		return dataRecordCollectionPermissionJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		DataRecordCollectionPermission dataRecordCollectionPermission) {

		if (dataRecordCollectionPermission == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (dataRecordCollectionPermission.getAddDataRecord() == null) {
			map.put("addDataRecord", null);
		}
		else {
			map.put(
				"addDataRecord",
				String.valueOf(
					dataRecordCollectionPermission.getAddDataRecord()));
		}

		if (dataRecordCollectionPermission.getAddDataRecordCollection() ==
				null) {

			map.put("addDataRecordCollection", null);
		}
		else {
			map.put(
				"addDataRecordCollection",
				String.valueOf(
					dataRecordCollectionPermission.
						getAddDataRecordCollection()));
		}

		if (dataRecordCollectionPermission.getDefinePermissions() == null) {
			map.put("definePermissions", null);
		}
		else {
			map.put(
				"definePermissions",
				String.valueOf(
					dataRecordCollectionPermission.getDefinePermissions()));
		}

		if (dataRecordCollectionPermission.getDelete() == null) {
			map.put("delete", null);
		}
		else {
			map.put(
				"delete",
				String.valueOf(dataRecordCollectionPermission.getDelete()));
		}

		if (dataRecordCollectionPermission.getDeleteDataRecord() == null) {
			map.put("deleteDataRecord", null);
		}
		else {
			map.put(
				"deleteDataRecord",
				String.valueOf(
					dataRecordCollectionPermission.getDeleteDataRecord()));
		}

		if (dataRecordCollectionPermission.getExportDataRecord() == null) {
			map.put("exportDataRecord", null);
		}
		else {
			map.put(
				"exportDataRecord",
				String.valueOf(
					dataRecordCollectionPermission.getExportDataRecord()));
		}

		if (dataRecordCollectionPermission.getRoleNames() == null) {
			map.put("roleNames", null);
		}
		else {
			map.put(
				"roleNames",
				String.valueOf(dataRecordCollectionPermission.getRoleNames()));
		}

		if (dataRecordCollectionPermission.getUpdate() == null) {
			map.put("update", null);
		}
		else {
			map.put(
				"update",
				String.valueOf(dataRecordCollectionPermission.getUpdate()));
		}

		if (dataRecordCollectionPermission.getUpdateDataRecord() == null) {
			map.put("updateDataRecord", null);
		}
		else {
			map.put(
				"updateDataRecord",
				String.valueOf(
					dataRecordCollectionPermission.getUpdateDataRecord()));
		}

		if (dataRecordCollectionPermission.getView() == null) {
			map.put("view", null);
		}
		else {
			map.put(
				"view",
				String.valueOf(dataRecordCollectionPermission.getView()));
		}

		if (dataRecordCollectionPermission.getViewDataRecord() == null) {
			map.put("viewDataRecord", null);
		}
		else {
			map.put(
				"viewDataRecord",
				String.valueOf(
					dataRecordCollectionPermission.getViewDataRecord()));
		}

		return map;
	}

	public static class DataRecordCollectionPermissionJSONParser
		extends BaseJSONParser<DataRecordCollectionPermission> {

		@Override
		protected DataRecordCollectionPermission createDTO() {
			return new DataRecordCollectionPermission();
		}

		@Override
		protected DataRecordCollectionPermission[] createDTOArray(int size) {
			return new DataRecordCollectionPermission[size];
		}

		@Override
		protected void setField(
			DataRecordCollectionPermission dataRecordCollectionPermission,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "addDataRecord")) {
				if (jsonParserFieldValue != null) {
					dataRecordCollectionPermission.setAddDataRecord(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "addDataRecordCollection")) {

				if (jsonParserFieldValue != null) {
					dataRecordCollectionPermission.setAddDataRecordCollection(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "definePermissions")) {
				if (jsonParserFieldValue != null) {
					dataRecordCollectionPermission.setDefinePermissions(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "delete")) {
				if (jsonParserFieldValue != null) {
					dataRecordCollectionPermission.setDelete(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "deleteDataRecord")) {
				if (jsonParserFieldValue != null) {
					dataRecordCollectionPermission.setDeleteDataRecord(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "exportDataRecord")) {
				if (jsonParserFieldValue != null) {
					dataRecordCollectionPermission.setExportDataRecord(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "roleNames")) {
				if (jsonParserFieldValue != null) {
					dataRecordCollectionPermission.setRoleNames(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "update")) {
				if (jsonParserFieldValue != null) {
					dataRecordCollectionPermission.setUpdate(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "updateDataRecord")) {
				if (jsonParserFieldValue != null) {
					dataRecordCollectionPermission.setUpdateDataRecord(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "view")) {
				if (jsonParserFieldValue != null) {
					dataRecordCollectionPermission.setView(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewDataRecord")) {
				if (jsonParserFieldValue != null) {
					dataRecordCollectionPermission.setViewDataRecord(
						(Boolean)jsonParserFieldValue);
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