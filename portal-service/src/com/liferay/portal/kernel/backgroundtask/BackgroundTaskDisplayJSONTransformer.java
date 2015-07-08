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
		JSONArray detailsItemsJSONArray, String message,
		JSONArray detailsItemsListJSONArray) {

		JSONObject detailsItemJSONObject = JSONFactoryUtil.createJSONObject();

		detailsItemJSONObject.put("message", message);
		detailsItemJSONObject.put("itemsList", detailsItemsListJSONArray);

		detailsItemsJSONArray.put(detailsItemJSONObject);
	}

	public static void addListItem(
		JSONArray itemsListJSONArray, String info, String errorMessage,
		String errorStrongMessage) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("info", info);
		jsonObject.put("errorMessage", errorMessage);
		jsonObject.put("errorStrongMessage", errorStrongMessage);

		itemsListJSONArray.put(jsonObject);
	}

	public static JSONObject createDetailsJSONObject(
		String detailsHeader, JSONArray detailsItemsJSONArray, int status) {

		JSONObject detailsJSONObject = JSONFactoryUtil.createJSONObject();

		detailsJSONObject.put("detailsHeader", detailsHeader);
		detailsJSONObject.put("detailsItems", detailsItemsJSONArray);
		detailsJSONObject.put("status", status);

		return detailsJSONObject;
	}

	public static JSONObject translateDetailsJSON(
		Locale locale, JSONObject detailsJSONObject) {

		if (detailsJSONObject == null) {
			return null;
		}

		JSONArray detailsItemsJSONArray = detailsJSONObject.getJSONArray(
			"detailsItems");

		detailsItemsJSONArray = translateDetailsItemsJSONArray(
			locale, detailsItemsJSONArray);

		String detailsHeader = detailsJSONObject.getString("detailsHeader");

		detailsJSONObject.put(
			"detailsHeader", LanguageUtil.get(locale, detailsHeader));
		detailsJSONObject.put("detailsItems", detailsItemsJSONArray);

		return detailsJSONObject;
	}

	protected static JSONArray translateDetailsItemsJSONArray(
		Locale locale, JSONArray detailsItemsJSONArray) {

		if (detailsItemsJSONArray == null) {
			return null;
		}

		JSONArray translatedDetailsItemsJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < detailsItemsJSONArray.length(); i++) {
			JSONObject detailsItemJSONObject =
				detailsItemsJSONArray.getJSONObject(i);

			String message = detailsItemJSONObject.getString("message");

			detailsItemJSONObject.put(
				"message", LanguageUtil.get(locale, message));

			JSONArray itemsListJSONArray = detailsItemJSONObject.getJSONArray(
				"itemsList");

			itemsListJSONArray = translateItemsListJSONArray(
				locale, itemsListJSONArray);

			detailsItemJSONObject.put("itemsList", itemsListJSONArray);

			translatedDetailsItemsJSONArray.put(detailsItemJSONObject);
		}

		return translatedDetailsItemsJSONArray;
	}

	protected static JSONArray translateItemsListJSONArray(
		Locale locale, JSONArray itemsListJSONArray) {

		if (itemsListJSONArray == null) {
			return null;
		}

		JSONArray translatedItemsListJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (int j = 0; j < itemsListJSONArray.length(); j++) {
			JSONObject listItemJSONObject = itemsListJSONArray.getJSONObject(j);

			String info = listItemJSONObject.getString("info");
			String errorMessage = listItemJSONObject.getString("errorMessage");
			String errorStrongMessage = listItemJSONObject.getString(
				"errorStrongMessage");

			listItemJSONObject.put("info", LanguageUtil.get(locale, info));
			listItemJSONObject.put(
				"errorMessage",
				LanguageUtil.get(locale, errorMessage));
			listItemJSONObject.put(
				"errorStrongMessage",
				LanguageUtil.get(locale, errorStrongMessage));

			translatedItemsListJSONArray.put(listItemJSONObject);
		}

		return translatedItemsListJSONArray;
	}

}