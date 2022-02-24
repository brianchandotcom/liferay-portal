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

package com.liferay.headless.commerce.admin.pricing.client.serdes.v1_0;

import com.liferay.headless.commerce.admin.pricing.client.dto.v1_0.PriceEntry;
import com.liferay.headless.commerce.admin.pricing.client.dto.v1_0.PriceList;
import com.liferay.headless.commerce.admin.pricing.client.dto.v1_0.PriceListAccountGroup;
import com.liferay.headless.commerce.admin.pricing.client.json.BaseJSONParser;

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
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public class PriceListSerDes {

	public static PriceList toDTO(String json) {
		PriceListJSONParser priceListJSONParser = new PriceListJSONParser();

		return priceListJSONParser.parseToDTO(json);
	}

	public static PriceList[] toDTOs(String json) {
		PriceListJSONParser priceListJSONParser = new PriceListJSONParser();

		return priceListJSONParser.parseToDTOs(json);
	}

	public static String toJSON(PriceList priceList) {
		if (priceList == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (priceList.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(priceList.getActive());
		}

		if (priceList.getCatalogId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"catalogId\": ");

			sb.append(priceList.getCatalogId());
		}

		if (priceList.getCurrencyCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"currencyCode\": ");

			sb.append("\"");

			sb.append(_escape(priceList.getCurrencyCode()));

			sb.append("\"");
		}

		if (priceList.getCustomFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append(_toJSON(priceList.getCustomFields()));
		}

		if (priceList.getDisplayDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(priceList.getDisplayDate()));

			sb.append("\"");
		}

		if (priceList.getExpirationDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expirationDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(priceList.getExpirationDate()));

			sb.append("\"");
		}

		if (priceList.getExternalReferenceCode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"externalReferenceCode\": ");

			sb.append("\"");

			sb.append(_escape(priceList.getExternalReferenceCode()));

			sb.append("\"");
		}

		if (priceList.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(priceList.getId());
		}

		if (priceList.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(priceList.getName()));

			sb.append("\"");
		}

		if (priceList.getNeverExpire() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"neverExpire\": ");

			sb.append(priceList.getNeverExpire());
		}

		if (priceList.getPriceEntries() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceEntries\": ");

			sb.append("[");

			for (int i = 0; i < priceList.getPriceEntries().length; i++) {
				sb.append(String.valueOf(priceList.getPriceEntries()[i]));

				if ((i + 1) < priceList.getPriceEntries().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (priceList.getPriceListAccountGroups() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListAccountGroups\": ");

			sb.append("[");

			for (int i = 0; i < priceList.getPriceListAccountGroups().length;
				 i++) {

				sb.append(
					String.valueOf(priceList.getPriceListAccountGroups()[i]));

				if ((i + 1) < priceList.getPriceListAccountGroups().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (priceList.getPriority() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priority\": ");

			sb.append(priceList.getPriority());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PriceListJSONParser priceListJSONParser = new PriceListJSONParser();

		return priceListJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(PriceList priceList) {
		if (priceList == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssXX");

		if (priceList.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(priceList.getActive()));
		}

		if (priceList.getCatalogId() == null) {
			map.put("catalogId", null);
		}
		else {
			map.put("catalogId", String.valueOf(priceList.getCatalogId()));
		}

		if (priceList.getCurrencyCode() == null) {
			map.put("currencyCode", null);
		}
		else {
			map.put(
				"currencyCode", String.valueOf(priceList.getCurrencyCode()));
		}

		if (priceList.getCustomFields() == null) {
			map.put("customFields", null);
		}
		else {
			map.put(
				"customFields", String.valueOf(priceList.getCustomFields()));
		}

		if (priceList.getDisplayDate() == null) {
			map.put("displayDate", null);
		}
		else {
			map.put(
				"displayDate",
				liferayToJSONDateFormat.format(priceList.getDisplayDate()));
		}

		if (priceList.getExpirationDate() == null) {
			map.put("expirationDate", null);
		}
		else {
			map.put(
				"expirationDate",
				liferayToJSONDateFormat.format(priceList.getExpirationDate()));
		}

		if (priceList.getExternalReferenceCode() == null) {
			map.put("externalReferenceCode", null);
		}
		else {
			map.put(
				"externalReferenceCode",
				String.valueOf(priceList.getExternalReferenceCode()));
		}

		if (priceList.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(priceList.getId()));
		}

		if (priceList.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(priceList.getName()));
		}

		if (priceList.getNeverExpire() == null) {
			map.put("neverExpire", null);
		}
		else {
			map.put("neverExpire", String.valueOf(priceList.getNeverExpire()));
		}

		if (priceList.getPriceEntries() == null) {
			map.put("priceEntries", null);
		}
		else {
			map.put(
				"priceEntries", String.valueOf(priceList.getPriceEntries()));
		}

		if (priceList.getPriceListAccountGroups() == null) {
			map.put("priceListAccountGroups", null);
		}
		else {
			map.put(
				"priceListAccountGroups",
				String.valueOf(priceList.getPriceListAccountGroups()));
		}

		if (priceList.getPriority() == null) {
			map.put("priority", null);
		}
		else {
			map.put("priority", String.valueOf(priceList.getPriority()));
		}

		return map;
	}

	public static class PriceListJSONParser extends BaseJSONParser<PriceList> {

		@Override
		protected PriceList createDTO() {
			return new PriceList();
		}

		@Override
		protected PriceList[] createDTOArray(int size) {
			return new PriceList[size];
		}

		@Override
		protected void setField(
			PriceList priceList, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					priceList.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "catalogId")) {
				if (jsonParserFieldValue != null) {
					priceList.setCatalogId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "currencyCode")) {
				if (jsonParserFieldValue != null) {
					priceList.setCurrencyCode((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "customFields")) {
				if (jsonParserFieldValue != null) {
					priceList.setCustomFields(
						(Map)PriceListSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "displayDate")) {
				if (jsonParserFieldValue != null) {
					priceList.setDisplayDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "expirationDate")) {
				if (jsonParserFieldValue != null) {
					priceList.setExpirationDate(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "externalReferenceCode")) {

				if (jsonParserFieldValue != null) {
					priceList.setExternalReferenceCode(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					priceList.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					priceList.setName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "neverExpire")) {
				if (jsonParserFieldValue != null) {
					priceList.setNeverExpire((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priceEntries")) {
				if (jsonParserFieldValue != null) {
					priceList.setPriceEntries(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> PriceEntrySerDes.toDTO((String)object)
						).toArray(
							size -> new PriceEntry[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "priceListAccountGroups")) {

				if (jsonParserFieldValue != null) {
					priceList.setPriceListAccountGroups(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> PriceListAccountGroupSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new PriceListAccountGroup[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priority")) {
				if (jsonParserFieldValue != null) {
					priceList.setPriority(
						Double.valueOf((String)jsonParserFieldValue));
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