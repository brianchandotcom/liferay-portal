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

	public static void addDetailsItem(
		JSONArray detailsItems, String message, JSONArray detailsItemsList) {

		JSONObject detailItem = JSONFactoryUtil.createJSONObject();

		detailItem.put("message", message);
		detailItem.put("itemsList", detailsItemsList);

		detailsItems.put(detailItem);
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
		String detailsHeader, JSONArray detailsItems, int status) {

		JSONObject detailsJSON = JSONFactoryUtil.createJSONObject();

		detailsJSON.put("detailsHeader", detailsHeader);
		detailsJSON.put("detailsItems", detailsItems);
		detailsJSON.put("status", status);

		return detailsJSON;
	}

	public static JSONObject translateDetailsJSON(
		Locale locale, JSONObject detailsJSONObject) {

		if (detailsJSONObject == null) {
			return null;
		}

		JSONArray detailsItems = detailsJSONObject.getJSONArray("detailsItems");

		if (detailsItems == null) {
			return detailsJSONObject;
		}

		for (int i = 0; i < detailsItems.length(); i++) {
			JSONObject detailsItem = detailsItems.getJSONObject(i);

			String message = detailsItem.getString("message");

			detailsItem.put("message", LanguageUtil.get(locale, message));

			JSONArray itemsListJSONArray = detailsItem.getJSONArray(
				"itemsList");

			if (itemsListJSONArray == null) {
				continue;
			}

			for (int j = 0; j < itemsListJSONArray.length(); j++) {
				JSONObject listItemJSONObject =
					itemsListJSONArray.getJSONObject(j);

				String errorMessage = listItemJSONObject.getString(
					"errorMessage");
				String errorStrongMessage = listItemJSONObject.getString(
					"errorStrongMessage");
				String info = listItemJSONObject.getString("info");

				listItemJSONObject.put(
					"errorMessage",
					LanguageUtil.get(locale, errorMessage));
				listItemJSONObject.put(
					"errorStrongMessage",
					LanguageUtil.get(locale, errorStrongMessage));
				listItemJSONObject.put("info", LanguageUtil.get(locale, info));
			}

			detailsItem.put("itemsList", itemsListJSONArray);
		}

		String detailsHeader = detailsJSONObject.getString("detailsHeader");

		detailsJSONObject.put(
			"detailsHeader", LanguageUtil.get(locale, detailsHeader));
		detailsJSONObject.put("detailsItems", detailsItems);

		return detailsJSONObject;
	}

}