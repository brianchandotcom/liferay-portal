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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class Autocomplete {

	public static JSONArray arrayToJSONArray(
		List<?> list, String textParam, String valueParam) {

		return arrayToJSONArray(listToArray(list, textParam, valueParam), -1);
	}

	public static JSONArray arrayToJSONArray(String[] array, int max) {
		return arrayToJSONArray(_singleToPairArray(array), max);
	}

	public static JSONArray arrayToJSONArray(String[][] array, int max) {
		if (max <= 0) {
			max = array.length;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; (i < array.length) && (i < max); i++) {
			String text = array[i][0];
			String value = array[i][1];

			Map<String, String> map = new HashMap<>();

			map.put("text", text);
			map.put("value", value);

			jsonArray.put(map);
		}

		return jsonArray;
	}

	public static String arrayToXml(String[] array, int max) {
		return arrayToXml(_singleToPairArray(array), max);
	}

	public static String arrayToXml(String[][] array, int max) {
		if (max <= 0) {
			max = array.length;
		}

		StringBundler sb = new StringBundler(array.length * 8 + 3);

		sb.append("<?xml version=\"1.0\"?>");

		sb.append("<ajaxresponse>");

		for (int i = 0; (i < array.length) && (i < max); i++) {
			String text = array[i][0];
			String value = array[i][1];

			sb.append("<item>");
			sb.append("<text><![CDATA[");
			sb.append(text);
			sb.append("]]></text>");
			sb.append("<value><![CDATA[");
			sb.append(value);
			sb.append("]]></value>");
			sb.append("</item>");
		}

		sb.append("</ajaxresponse>");

		return sb.toString();
	}

	public static String[][] listToArray(
		List<?> list, String textParam, String valueParam) {

		String[][] array = new String[list.size()][2];

		for (int i = 0; i < list.size(); i++) {
			Object bean = list.get(i);

			Object text = BeanPropertiesUtil.getObject(bean, textParam);

			if (text == null) {
				text = StringPool.BLANK;
			}

			Object value = BeanPropertiesUtil.getObject(bean, valueParam);

			if (value == null) {
				value = StringPool.BLANK;
			}

			array[i][0] = text.toString();
			array[i][1] = value.toString();
		}

		return array;
	}

	public static String listToXml(
		List<?> list, String textParam, String valueParam) {

		return arrayToXml(listToArray(list, textParam, valueParam), -1);
	}

	private static String[][] _singleToPairArray(String[] array) {
		String[][] pairArray = new String[array.length][2];

		for (int i = 0; i < array.length; i++) {
			pairArray[i][0] = HtmlUtil.escape(array[i]);
			pairArray[i][1] = array[i];
		}

		return pairArray;
	}

}