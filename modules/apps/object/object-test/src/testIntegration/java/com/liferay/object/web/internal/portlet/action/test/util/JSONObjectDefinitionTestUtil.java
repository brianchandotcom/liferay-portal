/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.portlet.action.test.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Iterator;
import java.util.Objects;

/**
 * @author Eva Budai
 */
public class JSONObjectDefinitionTestUtil {

	public static void addLocalizedLabel(
		JSONObject jsonObject, String localization, String value) {

		if (jsonObject == null) {
			return;
		}

		JSONObject labelJSONObject = (JSONObject)jsonObject.get("label");

		labelJSONObject.put(localization, value);
	}

	public static void addLocalizedPluralLabel(
		JSONObject jsonObject, String localization, String value) {

		JSONObject labelJSONObject = (JSONObject)jsonObject.get("pluralLabel");

		labelJSONObject.put(localization, value);
	}

	public static JSONObject getObjectFieldJSONObject(
		JSONObject jsonObject, String name) {

		JSONArray jsonArray = jsonObject.getJSONArray("objectFields");

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject1 = (JSONObject)jsonArray.get(i);

			if (Objects.equals(jsonObject1.getString("name"), name)) {
				return jsonObject1;
			}
		}

		return null;
	}

	public static void setDefaultLanguageId(
		JSONObject jsonObject, String value) {

		jsonObject.put("defaultLanguageId", value);
	}

	public static void setLocalizedLabel(
		JSONObject jsonObject, String localization, String value) {

		if (jsonObject == null) {
			return;
		}

		JSONObject labelJSONObject = (JSONObject)jsonObject.get("label");

		for (Iterator<String> iterator = labelJSONObject.keys();
			 iterator.hasNext();) {

			String key = iterator.next();

			labelJSONObject.remove(key);
		}

		labelJSONObject.put(localization, value);
	}

	public static void setPluralLabel(
		JSONObject jsonObject, String localization, String value) {

		JSONObject labelJSONObject = (JSONObject)jsonObject.get("pluralLabel");

		for (Iterator<String> iterator = labelJSONObject.keys();
			 iterator.hasNext();) {

			String key = iterator.next();

			labelJSONObject.remove(key);
		}

		labelJSONObject.put(localization, value);
	}

}