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

package com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0;

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceEntry;
import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceList;
import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceListAccount;
import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceListAccountGroup;
import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceListChannel;
import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceListDiscount;
import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceListOrderType;
import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceModifier;
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

		if (priceList.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(priceList.getActions()));
		}

		if (priceList.getActive() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"active\": ");

			sb.append(priceList.getActive());
		}

		if (priceList.getAuthor() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"author\": ");

			sb.append("\"");

			sb.append(_escape(priceList.getAuthor()));

			sb.append("\"");
		}

		if (priceList.getCatalogBasePriceList() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"catalogBasePriceList\": ");

			sb.append(priceList.getCatalogBasePriceList());
		}

		if (priceList.getCatalogId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"catalogId\": ");

			sb.append(priceList.getCatalogId());
		}

		if (priceList.getCatalogName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"catalogName\": ");

			sb.append("\"");

			sb.append(_escape(priceList.getCatalogName()));

			sb.append("\"");
		}

		if (priceList.getCreateDate() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(priceList.getCreateDate()));

			sb.append("\"");
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

		if (priceList.getNetPrice() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"netPrice\": ");

			sb.append(priceList.getNetPrice());
		}

		if (priceList.getNeverExpire() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"neverExpire\": ");

			sb.append(priceList.getNeverExpire());
		}

		if (priceList.getParentPriceListId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"parentPriceListId\": ");

			sb.append(priceList.getParentPriceListId());
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

		if (priceList.getPriceListAccounts() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListAccounts\": ");

			sb.append("[");

			for (int i = 0; i < priceList.getPriceListAccounts().length; i++) {
				sb.append(String.valueOf(priceList.getPriceListAccounts()[i]));

				if ((i + 1) < priceList.getPriceListAccounts().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (priceList.getPriceListChannels() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListChannels\": ");

			sb.append("[");

			for (int i = 0; i < priceList.getPriceListChannels().length; i++) {
				sb.append(String.valueOf(priceList.getPriceListChannels()[i]));

				if ((i + 1) < priceList.getPriceListChannels().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (priceList.getPriceListDiscounts() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListDiscounts\": ");

			sb.append("[");

			for (int i = 0; i < priceList.getPriceListDiscounts().length; i++) {
				sb.append(String.valueOf(priceList.getPriceListDiscounts()[i]));

				if ((i + 1) < priceList.getPriceListDiscounts().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (priceList.getPriceListOrderTypes() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceListOrderTypes\": ");

			sb.append("[");

			for (int i = 0; i < priceList.getPriceListOrderTypes().length;
				 i++) {

				sb.append(
					String.valueOf(priceList.getPriceListOrderTypes()[i]));

				if ((i + 1) < priceList.getPriceListOrderTypes().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (priceList.getPriceModifiers() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priceModifiers\": ");

			sb.append("[");

			for (int i = 0; i < priceList.getPriceModifiers().length; i++) {
				sb.append(String.valueOf(priceList.getPriceModifiers()[i]));

				if ((i + 1) < priceList.getPriceModifiers().length) {
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

		if (priceList.getType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"type\": ");

			sb.append("\"");

			sb.append(priceList.getType());

			sb.append("\"");
		}

		if (priceList.getWorkflowStatusInfo() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"workflowStatusInfo\": ");

			sb.append(String.valueOf(priceList.getWorkflowStatusInfo()));
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

		if (priceList.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(priceList.getActions()));
		}

		if (priceList.getActive() == null) {
			map.put("active", null);
		}
		else {
			map.put("active", String.valueOf(priceList.getActive()));
		}

		if (priceList.getAuthor() == null) {
			map.put("author", null);
		}
		else {
			map.put("author", String.valueOf(priceList.getAuthor()));
		}

		if (priceList.getCatalogBasePriceList() == null) {
			map.put("catalogBasePriceList", null);
		}
		else {
			map.put(
				"catalogBasePriceList",
				String.valueOf(priceList.getCatalogBasePriceList()));
		}

		if (priceList.getCatalogId() == null) {
			map.put("catalogId", null);
		}
		else {
			map.put("catalogId", String.valueOf(priceList.getCatalogId()));
		}

		if (priceList.getCatalogName() == null) {
			map.put("catalogName", null);
		}
		else {
			map.put("catalogName", String.valueOf(priceList.getCatalogName()));
		}

		if (priceList.getCreateDate() == null) {
			map.put("createDate", null);
		}
		else {
			map.put(
				"createDate",
				liferayToJSONDateFormat.format(priceList.getCreateDate()));
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

		if (priceList.getNetPrice() == null) {
			map.put("netPrice", null);
		}
		else {
			map.put("netPrice", String.valueOf(priceList.getNetPrice()));
		}

		if (priceList.getNeverExpire() == null) {
			map.put("neverExpire", null);
		}
		else {
			map.put("neverExpire", String.valueOf(priceList.getNeverExpire()));
		}

		if (priceList.getParentPriceListId() == null) {
			map.put("parentPriceListId", null);
		}
		else {
			map.put(
				"parentPriceListId",
				String.valueOf(priceList.getParentPriceListId()));
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

		if (priceList.getPriceListAccounts() == null) {
			map.put("priceListAccounts", null);
		}
		else {
			map.put(
				"priceListAccounts",
				String.valueOf(priceList.getPriceListAccounts()));
		}

		if (priceList.getPriceListChannels() == null) {
			map.put("priceListChannels", null);
		}
		else {
			map.put(
				"priceListChannels",
				String.valueOf(priceList.getPriceListChannels()));
		}

		if (priceList.getPriceListDiscounts() == null) {
			map.put("priceListDiscounts", null);
		}
		else {
			map.put(
				"priceListDiscounts",
				String.valueOf(priceList.getPriceListDiscounts()));
		}

		if (priceList.getPriceListOrderTypes() == null) {
			map.put("priceListOrderTypes", null);
		}
		else {
			map.put(
				"priceListOrderTypes",
				String.valueOf(priceList.getPriceListOrderTypes()));
		}

		if (priceList.getPriceModifiers() == null) {
			map.put("priceModifiers", null);
		}
		else {
			map.put(
				"priceModifiers",
				String.valueOf(priceList.getPriceModifiers()));
		}

		if (priceList.getPriority() == null) {
			map.put("priority", null);
		}
		else {
			map.put("priority", String.valueOf(priceList.getPriority()));
		}

		if (priceList.getType() == null) {
			map.put("type", null);
		}
		else {
			map.put("type", String.valueOf(priceList.getType()));
		}

		if (priceList.getWorkflowStatusInfo() == null) {
			map.put("workflowStatusInfo", null);
		}
		else {
			map.put(
				"workflowStatusInfo",
				String.valueOf(priceList.getWorkflowStatusInfo()));
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

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					priceList.setActions(
						(Map)PriceListSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "active")) {
				if (jsonParserFieldValue != null) {
					priceList.setActive((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "author")) {
				if (jsonParserFieldValue != null) {
					priceList.setAuthor((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "catalogBasePriceList")) {

				if (jsonParserFieldValue != null) {
					priceList.setCatalogBasePriceList(
						(Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "catalogId")) {
				if (jsonParserFieldValue != null) {
					priceList.setCatalogId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "catalogName")) {
				if (jsonParserFieldValue != null) {
					priceList.setCatalogName((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "createDate")) {
				if (jsonParserFieldValue != null) {
					priceList.setCreateDate(
						toDate((String)jsonParserFieldValue));
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
			else if (Objects.equals(jsonParserFieldName, "netPrice")) {
				if (jsonParserFieldValue != null) {
					priceList.setNetPrice((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "neverExpire")) {
				if (jsonParserFieldValue != null) {
					priceList.setNeverExpire((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "parentPriceListId")) {
				if (jsonParserFieldValue != null) {
					priceList.setParentPriceListId(
						Long.valueOf((String)jsonParserFieldValue));
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
			else if (Objects.equals(jsonParserFieldName, "priceListAccounts")) {
				if (jsonParserFieldValue != null) {
					priceList.setPriceListAccounts(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> PriceListAccountSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new PriceListAccount[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priceListChannels")) {
				if (jsonParserFieldValue != null) {
					priceList.setPriceListChannels(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> PriceListChannelSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new PriceListChannel[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "priceListDiscounts")) {

				if (jsonParserFieldValue != null) {
					priceList.setPriceListDiscounts(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> PriceListDiscountSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new PriceListDiscount[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "priceListOrderTypes")) {

				if (jsonParserFieldValue != null) {
					priceList.setPriceListOrderTypes(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> PriceListOrderTypeSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new PriceListOrderType[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priceModifiers")) {
				if (jsonParserFieldValue != null) {
					priceList.setPriceModifiers(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> PriceModifierSerDes.toDTO((String)object)
						).toArray(
							size -> new PriceModifier[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priority")) {
				if (jsonParserFieldValue != null) {
					priceList.setPriority(
						Double.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "type")) {
				if (jsonParserFieldValue != null) {
					priceList.setType(
						PriceList.Type.create((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "workflowStatusInfo")) {

				if (jsonParserFieldValue != null) {
					priceList.setWorkflowStatusInfo(
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