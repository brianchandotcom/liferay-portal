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

import com.liferay.headless.delivery.client.dto.v1_0.RenderedPage;
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
public class RenderedPageSerDes {

	public static RenderedPage toDTO(String json) {
		RenderedPageJSONParser renderedPageJSONParser =
			new RenderedPageJSONParser();

		return renderedPageJSONParser.parseToDTO(json);
	}

	public static RenderedPage[] toDTOs(String json) {
		RenderedPageJSONParser renderedPageJSONParser =
			new RenderedPageJSONParser();

		return renderedPageJSONParser.parseToDTOs(json);
	}

	public static String toJSON(RenderedPage renderedPage) {
		if (renderedPage == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (renderedPage.getMasterPageId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"masterPageId\": ");

			sb.append("\"");

			sb.append(_escape(renderedPage.getMasterPageId()));

			sb.append("\"");
		}

		if (renderedPage.getMasterPageName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"masterPageName\": ");

			sb.append("\"");

			sb.append(_escape(renderedPage.getMasterPageName()));

			sb.append("\"");
		}

		if (renderedPage.getPageTemplateId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pageTemplateId\": ");

			sb.append("\"");

			sb.append(_escape(renderedPage.getPageTemplateId()));

			sb.append("\"");
		}

		if (renderedPage.getPageTemplateName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"pageTemplateName\": ");

			sb.append("\"");

			sb.append(_escape(renderedPage.getPageTemplateName()));

			sb.append("\"");
		}

		if (renderedPage.getRenderedPageURL() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"renderedPageURL\": ");

			sb.append("\"");

			sb.append(_escape(renderedPage.getRenderedPageURL()));

			sb.append("\"");
		}

		if (renderedPage.getViewPortType() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"viewPortType\": ");

			sb.append("\"");

			sb.append(_escape(renderedPage.getViewPortType()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		RenderedPageJSONParser renderedPageJSONParser =
			new RenderedPageJSONParser();

		return renderedPageJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(RenderedPage renderedPage) {
		if (renderedPage == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (renderedPage.getMasterPageId() == null) {
			map.put("masterPageId", null);
		}
		else {
			map.put(
				"masterPageId", String.valueOf(renderedPage.getMasterPageId()));
		}

		if (renderedPage.getMasterPageName() == null) {
			map.put("masterPageName", null);
		}
		else {
			map.put(
				"masterPageName",
				String.valueOf(renderedPage.getMasterPageName()));
		}

		if (renderedPage.getPageTemplateId() == null) {
			map.put("pageTemplateId", null);
		}
		else {
			map.put(
				"pageTemplateId",
				String.valueOf(renderedPage.getPageTemplateId()));
		}

		if (renderedPage.getPageTemplateName() == null) {
			map.put("pageTemplateName", null);
		}
		else {
			map.put(
				"pageTemplateName",
				String.valueOf(renderedPage.getPageTemplateName()));
		}

		if (renderedPage.getRenderedPageURL() == null) {
			map.put("renderedPageURL", null);
		}
		else {
			map.put(
				"renderedPageURL",
				String.valueOf(renderedPage.getRenderedPageURL()));
		}

		if (renderedPage.getViewPortType() == null) {
			map.put("viewPortType", null);
		}
		else {
			map.put(
				"viewPortType", String.valueOf(renderedPage.getViewPortType()));
		}

		return map;
	}

	public static class RenderedPageJSONParser
		extends BaseJSONParser<RenderedPage> {

		@Override
		protected RenderedPage createDTO() {
			return new RenderedPage();
		}

		@Override
		protected RenderedPage[] createDTOArray(int size) {
			return new RenderedPage[size];
		}

		@Override
		protected void setField(
			RenderedPage renderedPage, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "masterPageId")) {
				if (jsonParserFieldValue != null) {
					renderedPage.setMasterPageId((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "masterPageName")) {
				if (jsonParserFieldValue != null) {
					renderedPage.setMasterPageName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageTemplateId")) {
				if (jsonParserFieldValue != null) {
					renderedPage.setPageTemplateId(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "pageTemplateName")) {
				if (jsonParserFieldValue != null) {
					renderedPage.setPageTemplateName(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "renderedPageURL")) {
				if (jsonParserFieldValue != null) {
					renderedPage.setRenderedPageURL(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "viewPortType")) {
				if (jsonParserFieldValue != null) {
					renderedPage.setViewPortType((String)jsonParserFieldValue);
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