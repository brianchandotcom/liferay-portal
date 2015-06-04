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

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
 * @author Andrew Betts
 */
public class BackgroundTaskDisplayJSONTransformer {

	public static void addDetailItem(
		JSONArray detailItems, String message, JSONArray itemsList) {

		JSONObject detailItem = JSONFactoryUtil.createJSONObject();

		detailItem.put("message", message);
		detailItem.put("itemsList", itemsList);

		detailItems.put(detailItem);
	}

	public static void addListItem(
		JSONArray itemsList, String info, String errorMessage,
		String errorStrongMessage) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("info", info);
		jsonObject.put("errorMessage", errorMessage);
		jsonObject.put("errorStrongMessage", errorStrongMessage);

		itemsList.put(jsonObject);
	}

	public static JSONObject createDetailsJSONObject(
		String detailHeader, JSONArray detailItems, int status) {

		JSONObject detailsJSON = JSONFactoryUtil.createJSONObject();

		detailsJSON.put("detailHeader", detailHeader);
		detailsJSON.put("detailItems", detailItems);
		detailsJSON.put("status", status);

		return detailsJSON;
	}

	public static JSONObject translateDetailsJSON(
		Locale locale, JSONObject detailsJSON) {

		if (detailsJSON == null) {
			return null;
		}

		JSONArray detailItems = detailsJSON.getJSONArray("detailItems");

		if (detailItems == null) {
			return detailsJSON;
		}

		for (int i = 0; i < detailItems.length(); i++) {
			JSONObject detailItem = detailItems.getJSONObject(i);

			String message = detailItem.getString("message");

			detailItem.put("message", LanguageUtil.get(locale, message));

			JSONArray itemsList = detailItem.getJSONArray("itemsList");

			if (itemsList == null) {
				continue;
			}

			for (int j = 0; j < itemsList.length(); j++) {
				JSONObject listItem = itemsList.getJSONObject(j);

				String errorMessage = listItem.getString("errorMessage");
				String errorStrongMessage = listItem.getString(
					"errorStrongMessage");
				String info = listItem.getString("info");

				listItem.put(
					"errorMessage",
					LanguageUtil.get(locale, errorMessage));
				listItem.put(
					"errorStrongMessage",
					LanguageUtil.get(locale, errorStrongMessage));
				listItem.put("info", LanguageUtil.get(locale, info));
			}

			detailItem.put("itemsList", itemsList);
		}

		String detailHeader = detailsJSON.getString("detailHeader");

		detailsJSON.put("detailHeader", LanguageUtil.get(locale, detailHeader));
		detailsJSON.put("detailItems", detailItems);

		return detailsJSON;
	}

}