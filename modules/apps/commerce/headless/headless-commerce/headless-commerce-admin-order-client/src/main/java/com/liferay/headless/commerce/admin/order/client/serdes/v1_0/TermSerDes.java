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

package com.liferay.headless.commerce.admin.order.client.serdes.v1_0;

import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Term;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.TermOrderType;
import com.liferay.headless.commerce.admin.order.client.json.BaseJSONParser;

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
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public class TermSerDes {

	public static Term toDTO(String json) {
		TermJSONParser termJSONParser = new TermJSONParser();

		return termJSONParser.parseToDTO(json);
	}

	public static Term[] toDTOs(String json) {
		TermJSONParser termJSONParser = new TermJSONParser();

		return termJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Term term) {
		if (term == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (term.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(term.getActions()));
		}

		if (term.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(term.getActive());
		}

		if (term.getCreateDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(term.getCreateDate()));

			sb.append("\"");
		}

		if (term.getDescription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append(_toJSON(term.getDescription()));
		}

		if (term.getDisplayDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(term.getDisplayDate()));

			sb.append("\"");
		}

		if (term.getExpirationDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expirationDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(term.getExpirationDate()));

			sb.append("\"");
		}

		if (term.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(term.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (term.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(term.getId());
		}

		if (term.getLabel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"label\": ");

			sb.append(_toJSON(term.getLabel()));
		}

		if (term.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(term.getName()));

			sb.append("\"");
		}

		if (term.getNeverExpire() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"neverExpire\": ");

			sb.append(term.getNeverExpire());
		}

		if (term.getPriority() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priority\": ");

			sb.append(term.getPriority());
		}

		if (term.getTermOrderType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"termOrderType\": ");

			sb.append("[");

			for (int i = 0; i < term.getTermOrderType().length; i++) {
				sb.append(String.valueOf(term.getTermOrderType()[i]));

				if ((i + 1) < term.getTermOrderType().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (term.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(_escape(term.getType()));

			sb.append("\"");
		}

		if (term.getTypeLocalized() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"typeLocalized\": ");

			sb.append("\"");

			sb.append(_escape(term.getTypeLocalized()));

			sb.append("\"");
		}

		if (term.getTypeSettings() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"typeSettings\": ");

			sb.append("\"");

			sb.append(_escape(term.getTypeSettings()));

			sb.append("\"");
		}

		if (term.getWorkflowStatusInfo() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowStatusInfo\": ");

			sb.append(String.valueOf(term.getWorkflowStatusInfo()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		TermJSONParser termJSONParser = new TermJSONParser();

		return termJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Term term) {
		if (term == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (term.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(term.getActions()));
		}

		if (term.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(term.getActive()));
		}

		if (term.getCreateDate() == null) {
			map.put("createDate", null);
		}
		else {
			map.put(
				"createDate",
				liferayToJSONDateFormat.format(term.getCreateDate()));
		}

		if (term.getDescription() == null) {
			map.put("description", null);
		}
		else {
			map.put("description", String.valueOf(term.getDescription()));
		}

		if (term.getDisplayDate() == null) {
			map.put("displayDate", null);
		}
		else {
			map.put(
				"displayDate",
				liferayToJSONDateFormat.format(term.getDisplayDate()));
		}

		if (term.getExpirationDate() == null) {
			map.put("expirationDate", null);
		}
		else {
			map.put(
				"expirationDate",
				liferayToJSONDateFormat.format(term.getExpirationDate()));
		}

		if (term.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(term.getExternalReferenceCode()));
		}

		if (term.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(term.getId()));
		}

		if (term.getLabel() == null) {
			map.put("label", null);
		}
		else {
			map.put("label", String.valueOf(term.getLabel()));
		}

		if (term.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(term.getName()));
		}

		if (term.getNeverExpire() == null) {
			map.put("neverExpire", null);
		}
		else {
			map.put("neverExpire", String.valueOf(term.getNeverExpire()));
		}

		if (term.getPriority() == null) {
			map.put("priority", null);
		}
		else {
			map.put("priority", String.valueOf(term.getPriority()));
		}

		if (term.getTermOrderType() == null) {
			map.put("termOrderType", null);
		}
		else {
			map.put("termOrderType", String.valueOf(term.getTermOrderType()));
		}

		if (term.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(term.getType()));
		}

		if (term.getTypeLocalized() == null) {
			map.put("typeLocalized", null);
		}
		else {
			map.put("typeLocalized", String.valueOf(term.getTypeLocalized()));
		}

		if (term.getTypeSettings() == null) {
			map.put("typeSettings", null);
		}
		else {
			map.put("typeSettings", String.valueOf(term.getTypeSettings()));
		}

		if (term.getWorkflowStatusInfo() == null) {
			map.put("workflowStatusInfo", null);
		}
		else {
			map.put(
				"workflowStatusInfo",
				String.valueOf(term.getWorkflowStatusInfo()));
		}

		return map;
	}

	public static class TermJSONParser extends BaseJSONParser<Term> {

		@Override
		protected Term createDTO() {
			return new Term();
		}

		@Override
		protected Term[] createDTOArray(int size) {
			return new Term[size];
		}

		@Override
		protected void setField(
			Term term, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					term.setActions(
						(Map)TermSerDes.toMap((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					term.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "createDate")) {
				if (jsonParserFieldValue != null) {
					term.setCreateDate(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "description")) {
				if (jsonParserFieldValue != null) {
					term.setDescription(
						(Map)TermSerDes.toMap((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "displayDate")) {
				if (jsonParserFieldValue != null) {
					term.setDisplayDate(toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "expirationDate")) {
				if (jsonParserFieldValue != null) {
					term.setExpirationDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					term.setExternalReferenceCode((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					term.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "label")) {
				if (jsonParserFieldValue != null) {
					term.setLabel(
						(Map)TermSerDes.toMap((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					term.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "neverExpire")) {
				if (jsonParserFieldValue != null) {
					term.setNeverExpire((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priority")) {
				if (jsonParserFieldValue != null) {
					term.setPriority(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "termOrderType")) {
				if (jsonParserFieldValue != null) {
					term.setTermOrderType(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> TermOrderTypeSerDes.toDTO((String)object)
						).toArray(
							size -> new TermOrderType[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					term.setType((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "typeLocalized")) {
				if (jsonParserFieldValue != null) {
					term.setTypeLocalized((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "typeSettings")) {
				if (jsonParserFieldValue != null) {
					term.setTypeSettings((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "workflowStatusInfo")) {

				if (jsonParserFieldValue != null) {
					term.setWorkflowStatusInfo(
						StatusSerDes.toDTO((String)jsonParserFieldValue));
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