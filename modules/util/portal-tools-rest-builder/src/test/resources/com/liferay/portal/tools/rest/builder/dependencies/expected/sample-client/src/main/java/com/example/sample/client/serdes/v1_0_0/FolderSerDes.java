/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.client.serdes.v1_0_0;

import com.example.sample.client.dto.v1_0_0.ExternalReferenceElement1;
import com.example.sample.client.dto.v1_0_0.Folder;
import com.example.sample.client.json.BaseJSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author John Doe
 * @generated
 */
@Generated("")
public class FolderSerDes {

	public static Folder toDTO(String json) {
		FolderJSONParser folderJSONParser = new FolderJSONParser();

		return folderJSONParser.parseToDTO(json);
	}

	public static Folder[] toDTOs(String json) {
		FolderJSONParser folderJSONParser = new FolderJSONParser();

		return folderJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Folder folder) {
		if (folder == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (folder.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(folder.getDateCreated()));

			sb.append("\"");
		}

		if (folder.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(folder.getDateModified()));

			sb.append("\"");
		}

		if (folder.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(folder.getDescription()));

			sb.append("\"");
		}

		if (folder.getDocumentsRepository() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"documentsRepository\": ");

			sb.append(String.valueOf(folder.getDocumentsRepository()));
		}

		if (folder.getExternalReferenceElement1s() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceElement1s\": ");

			sb.append("[");

			for (int i = 0; i < folder.getExternalReferenceElement1s().length;
				 i++) {

				sb.append(folder.getExternalReferenceElement1s()[i]);

				if ((i + 1) < folder.getExternalReferenceElement1s().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (folder.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(folder.getId());
		}

		if (folder.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(folder.getName()));

			sb.append("\"");
		}

		if (folder.getSelf() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"self\": ");

			sb.append("\"");

			sb.append(_escape(folder.getSelf()));

			sb.append("\"");
		}

		if (folder.getSubFolders() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"subFolders\": ");

			sb.append(String.valueOf(folder.getSubFolders()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FolderJSONParser folderJSONParser = new FolderJSONParser();

		return folderJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Folder folder) {
		if (folder == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (folder.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(folder.getDateCreated()));
		}

		if (folder.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(folder.getDateModified()));
		}

		if (folder.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(folder.getDescription()));
		}

		if (folder.getDocumentsRepository() == null) {
			map.put("documentsRepository", null);
		}
		else {
			map.put(
				"documentsRepository",
				String.valueOf(folder.getDocumentsRepository()));
		}

		if (folder.getExternalReferenceElement1s() == null) {
			map.put("externalReferenceElement1s", null);
		}
		else {
			map.put(
				"externalReferenceElement1s",
				String.valueOf(folder.getExternalReferenceElement1s()));
		}

		if (folder.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(folder.getId()));
		}

		if (folder.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(folder.getName()));
		}

		if (folder.getSelf() == null) {
			map.put("self", null);
		}
		else {
			map.put("self", String.valueOf(folder.getSelf()));
		}

		if (folder.getSubFolders() == null) {
			map.put("subFolders", null);
		}
		else {
			map.put("subFolders", String.valueOf(folder.getSubFolders()));
		}

		return map;
	}

	public static class FolderJSONParser extends BaseJSONParser<Folder> {

		@Override
		protected Folder createDTO() {
			return new Folder();
		}

		@Override
		protected Folder[] createDTOArray(int size) {
			return new Folder[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "documentsRepository")) {

				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceElement1s")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "self")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "subFolders")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			Folder folder, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					folder.setDateCreated(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					folder.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					folder.setDescription((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "documentsRepository")) {

				if (jsonParserFieldValue != null) {
					folder.setDocumentsRepository(
						FolderSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceElement1s")) {

				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					ExternalReferenceElement1[]
						externalReferenceElement1sArray =
							new ExternalReferenceElement1
								[jsonParserFieldValues.length];

					for (int i = 0; i < externalReferenceElement1sArray.length;
						 i++) {

						externalReferenceElement1sArray[i] =
							ExternalReferenceElement1SerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					folder.setExternalReferenceElement1s(
						externalReferenceElement1sArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					folder.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					folder.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "self")) {
				if (jsonParserFieldValue != null) {
					folder.setSelf((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "subFolders")) {
				if (jsonParserFieldValue != null) {
					folder.setSubFolders(
						FolderSerDes.toDTO((String)jsonParserFieldValue));
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

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}