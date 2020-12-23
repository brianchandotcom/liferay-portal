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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.ContentPage;
import com.liferay.headless.delivery.client.dto.v1_0.CustomField;
import com.liferay.headless.delivery.client.dto.v1_0.RenderedPage;
import com.liferay.headless.delivery.client.dto.v1_0.TaxonomyCategoryBrief;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ContentPageSerDes {

	public static ContentPage toDTO(String json) {
		ContentPageJSONParser contentPageJSONParser =
			new ContentPageJSONParser();

		return contentPageJSONParser.parseToDTO(json);
	}

	public static ContentPage[] toDTOs(String json) {
		ContentPageJSONParser contentPageJSONParser =
			new ContentPageJSONParser();

		return contentPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ContentPage contentPage) {
		if (contentPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (contentPage.getActions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"actions\": ");

			sb.append(_toJSON(contentPage.getActions()));
		}

		if (contentPage.getAggregateRating() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"aggregateRating\": ");

			sb.append(String.valueOf(contentPage.getAggregateRating()));
		}

		if (contentPage.getAvailableLanguages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"availableLanguages\": ");

			sb.append("[");

			for (int i = 0; i < contentPage.getAvailableLanguages().length;
				 i++) {

				sb.append("\"");

				sb.append(_escape(contentPage.getAvailableLanguages()[i]));

				sb.append("\"");

				if ((i + 1) < contentPage.getAvailableLanguages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (contentPage.getCreator() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creator\": ");

			sb.append(String.valueOf(contentPage.getCreator()));
		}

		if (contentPage.getCustomFields() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"customFields\": ");

			sb.append("[");

			for (int i = 0; i < contentPage.getCustomFields().length; i++) {
				sb.append(String.valueOf(contentPage.getCustomFields()[i]));

				if ((i + 1) < contentPage.getCustomFields().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (contentPage.getDateCreated() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateCreated\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(contentPage.getDateCreated()));

			sb.append("\"");
		}

		if (contentPage.getDateModified() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateModified\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(contentPage.getDateModified()));

			sb.append("\"");
		}

		if (contentPage.getDatePublished() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"datePublished\": ");

			sb.append("\"");

			sb.append(
				liferayToJSONDateFormat.format(contentPage.getDatePublished()));

			sb.append("\"");
		}

		if (contentPage.getDefaultRenderedPage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"defaultRenderedPage\": ");

			sb.append(String.valueOf(contentPage.getDefaultRenderedPage()));
		}

		if (contentPage.getFriendlyUrlPath() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"friendlyUrlPath\": ");

			sb.append("\"");

			sb.append(_escape(contentPage.getFriendlyUrlPath()));

			sb.append("\"");
		}

		if (contentPage.getFriendlyUrlPath_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"friendlyUrlPath_i18n\": ");

			sb.append(_toJSON(contentPage.getFriendlyUrlPath_i18n()));
		}

		if (contentPage.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(contentPage.getId());
		}

		if (contentPage.getKeywords() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"keywords\": ");

			sb.append("[");

			for (int i = 0; i < contentPage.getKeywords().length; i++) {
				sb.append("\"");

				sb.append(_escape(contentPage.getKeywords()[i]));

				sb.append("\"");

				if ((i + 1) < contentPage.getKeywords().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (contentPage.getPageDefinition() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pageDefinition\": ");

			sb.append(String.valueOf(contentPage.getPageDefinition()));
		}

		if (contentPage.getPageSettings() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pageSettings\": ");

			sb.append(String.valueOf(contentPage.getPageSettings()));
		}

		if (contentPage.getPrivatePage() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"privatePage\": ");

			sb.append(contentPage.getPrivatePage());
		}

		if (contentPage.getRenderedPages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"renderedPages\": ");

			sb.append("[");

			for (int i = 0; i < contentPage.getRenderedPages().length; i++) {
				sb.append(String.valueOf(contentPage.getRenderedPages()[i]));

				if ((i + 1) < contentPage.getRenderedPages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (contentPage.getSiteId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"siteId\": ");

			sb.append(contentPage.getSiteId());
		}

		if (contentPage.getTaxonomyCategoryBriefs() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryBriefs\": ");

			sb.append("[");

			for (int i = 0; i < contentPage.getTaxonomyCategoryBriefs().length;
				 i++) {

				sb.append(
					String.valueOf(contentPage.getTaxonomyCategoryBriefs()[i]));

				if ((i + 1) < contentPage.getTaxonomyCategoryBriefs().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (contentPage.getTaxonomyCategoryIds() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryIds\": ");

			sb.append("[");

			for (int i = 0; i < contentPage.getTaxonomyCategoryIds().length;
				 i++) {

				sb.append(contentPage.getTaxonomyCategoryIds()[i]);

				if ((i + 1) < contentPage.getTaxonomyCategoryIds().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (contentPage.getTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title\": ");

			sb.append("\"");

			sb.append(_escape(contentPage.getTitle()));

			sb.append("\"");
		}

		if (contentPage.getTitle_i18n() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"title_i18n\": ");

			sb.append(_toJSON(contentPage.getTitle_i18n()));
		}

		if (contentPage.getUuid() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"uuid\": ");

			sb.append("\"");

			sb.append(_escape(contentPage.getUuid()));

			sb.append("\"");
		}

		if (contentPage.getViewableBy() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"viewableBy\": ");

			sb.append("\"");

			sb.append(contentPage.getViewableBy());

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ContentPageJSONParser contentPageJSONParser =
			new ContentPageJSONParser();

		return contentPageJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(ContentPage contentPage) {
		if (contentPage == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		if (contentPage.getActions() == null) {
			map.put("actions", null);
		}
		else {
			map.put("actions", String.valueOf(contentPage.getActions()));
		}

		if (contentPage.getAggregateRating() == null) {
			map.put("aggregateRating", null);
		}
		else {
			map.put(
				"aggregateRating",
				String.valueOf(contentPage.getAggregateRating()));
		}

		if (contentPage.getAvailableLanguages() == null) {
			map.put("availableLanguages", null);
		}
		else {
			map.put(
				"availableLanguages",
				String.valueOf(contentPage.getAvailableLanguages()));
		}

		if (contentPage.getCreator() == null) {
			map.put("creator", null);
		}
		else {
			map.put("creator", String.valueOf(contentPage.getCreator()));
		}

		if (contentPage.getCustomFields() == null) {
			map.put("customFields", null);
		}
		else {
			map.put(
				"customFields", String.valueOf(contentPage.getCustomFields()));
		}

		if (contentPage.getDateCreated() == null) {
			map.put("dateCreated", null);
		}
		else {
			map.put(
				"dateCreated",
				liferayToJSONDateFormat.format(contentPage.getDateCreated()));
		}

		if (contentPage.getDateModified() == null) {
			map.put("dateModified", null);
		}
		else {
			map.put(
				"dateModified",
				liferayToJSONDateFormat.format(contentPage.getDateModified()));
		}

		if (contentPage.getDatePublished() == null) {
			map.put("datePublished", null);
		}
		else {
			map.put(
				"datePublished",
				liferayToJSONDateFormat.format(contentPage.getDatePublished()));
		}

		if (contentPage.getDefaultRenderedPage() == null) {
			map.put("defaultRenderedPage", null);
		}
		else {
			map.put(
				"defaultRenderedPage",
				String.valueOf(contentPage.getDefaultRenderedPage()));
		}

		if (contentPage.getFriendlyUrlPath() == null) {
			map.put("friendlyUrlPath", null);
		}
		else {
			map.put(
				"friendlyUrlPath",
				String.valueOf(contentPage.getFriendlyUrlPath()));
		}

		if (contentPage.getFriendlyUrlPath_i18n() == null) {
			map.put("friendlyUrlPath_i18n", null);
		}
		else {
			map.put(
				"friendlyUrlPath_i18n",
				String.valueOf(contentPage.getFriendlyUrlPath_i18n()));
		}

		if (contentPage.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(contentPage.getId()));
		}

		if (contentPage.getKeywords() == null) {
			map.put("keywords", null);
		}
		else {
			map.put("keywords", String.valueOf(contentPage.getKeywords()));
		}

		if (contentPage.getPageDefinition() == null) {
			map.put("pageDefinition", null);
		}
		else {
			map.put(
				"pageDefinition",
				String.valueOf(contentPage.getPageDefinition()));
		}

		if (contentPage.getPageSettings() == null) {
			map.put("pageSettings", null);
		}
		else {
			map.put(
				"pageSettings", String.valueOf(contentPage.getPageSettings()));
		}

		if (contentPage.getPrivatePage() == null) {
			map.put("privatePage", null);
		}
		else {
			map.put(
				"privatePage", String.valueOf(contentPage.getPrivatePage()));
		}

		if (contentPage.getRenderedPages() == null) {
			map.put("renderedPages", null);
		}
		else {
			map.put(
				"renderedPages",
				String.valueOf(contentPage.getRenderedPages()));
		}

		if (contentPage.getSiteId() == null) {
			map.put("siteId", null);
		}
		else {
			map.put("siteId", String.valueOf(contentPage.getSiteId()));
		}

		if (contentPage.getTaxonomyCategoryBriefs() == null) {
			map.put("taxonomyCategoryBriefs", null);
		}
		else {
			map.put(
				"taxonomyCategoryBriefs",
				String.valueOf(contentPage.getTaxonomyCategoryBriefs()));
		}

		if (contentPage.getTaxonomyCategoryIds() == null) {
			map.put("taxonomyCategoryIds", null);
		}
		else {
			map.put(
				"taxonomyCategoryIds",
				String.valueOf(contentPage.getTaxonomyCategoryIds()));
		}

		if (contentPage.getTitle() == null) {
			map.put("title", null);
		}
		else {
			map.put("title", String.valueOf(contentPage.getTitle()));
		}

		if (contentPage.getTitle_i18n() == null) {
			map.put("title_i18n", null);
		}
		else {
			map.put("title_i18n", String.valueOf(contentPage.getTitle_i18n()));
		}

		if (contentPage.getUuid() == null) {
			map.put("uuid", null);
		}
		else {
			map.put("uuid", String.valueOf(contentPage.getUuid()));
		}

		if (contentPage.getViewableBy() == null) {
			map.put("viewableBy", null);
		}
		else {
			map.put("viewableBy", String.valueOf(contentPage.getViewableBy()));
		}

		return map;
	}

	public static class ContentPageJSONParser
		extends BaseJSONParser<ContentPage> {

		@Override
		protected ContentPage createDTO() {
			return new ContentPage();
		}

		@Override
		protected ContentPage[] createDTOArray(int size) {
			return new ContentPage[size];
		}

		@Override
		protected void setField(
			ContentPage contentPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "actions")) {
				if (jsonParserFieldValue != null) {
					contentPage.setActions(
						(Map)ContentPageSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "aggregateRating")) {
				if (jsonParserFieldValue != null) {
					contentPage.setAggregateRating(
						AggregateRatingSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "availableLanguages")) {

				if (jsonParserFieldValue != null) {
					contentPage.setAvailableLanguages(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creator")) {
				if (jsonParserFieldValue != null) {
					contentPage.setCreator(
						CreatorSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "customFields")) {
				if (jsonParserFieldValue != null) {
					contentPage.setCustomFields(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> CustomFieldSerDes.toDTO((String)object)
						).toArray(
							size -> new CustomField[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateCreated")) {
				if (jsonParserFieldValue != null) {
					contentPage.setDateCreated(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "dateModified")) {
				if (jsonParserFieldValue != null) {
					contentPage.setDateModified(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "datePublished")) {
				if (jsonParserFieldValue != null) {
					contentPage.setDatePublished(
						toDate((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "defaultRenderedPage")) {

				if (jsonParserFieldValue != null) {
					contentPage.setDefaultRenderedPage(
						RenderedPageSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "friendlyUrlPath")) {
				if (jsonParserFieldValue != null) {
					contentPage.setFriendlyUrlPath(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "friendlyUrlPath_i18n")) {

				if (jsonParserFieldValue != null) {
					contentPage.setFriendlyUrlPath_i18n(
						(Map)ContentPageSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					contentPage.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "keywords")) {
				if (jsonParserFieldValue != null) {
					contentPage.setKeywords(
						toStrings((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageDefinition")) {
				if (jsonParserFieldValue != null) {
					contentPage.setPageDefinition(
						PageDefinitionSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageSettings")) {
				if (jsonParserFieldValue != null) {
					contentPage.setPageSettings(
						PageSettingsSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "privatePage")) {
				if (jsonParserFieldValue != null) {
					contentPage.setPrivatePage((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "renderedPages")) {
				if (jsonParserFieldValue != null) {
					contentPage.setRenderedPages(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> RenderedPageSerDes.toDTO((String)object)
						).toArray(
							size -> new RenderedPage[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "siteId")) {
				if (jsonParserFieldValue != null) {
					contentPage.setSiteId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategoryBriefs")) {

				if (jsonParserFieldValue != null) {
					contentPage.setTaxonomyCategoryBriefs(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> TaxonomyCategoryBriefSerDes.toDTO(
								(String)object)
						).toArray(
							size -> new TaxonomyCategoryBrief[size]
						));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategoryIds")) {

				if (jsonParserFieldValue != null) {
					contentPage.setTaxonomyCategoryIds(
						toLongs((Object[])jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title")) {
				if (jsonParserFieldValue != null) {
					contentPage.setTitle((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "title_i18n")) {
				if (jsonParserFieldValue != null) {
					contentPage.setTitle_i18n(
						(Map)ContentPageSerDes.toMap(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "uuid")) {
				if (jsonParserFieldValue != null) {
					contentPage.setUuid((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewableBy")) {
				if (jsonParserFieldValue != null) {
					contentPage.setViewableBy(
						ContentPage.ViewableBy.create(
							(String)jsonParserFieldValue));
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