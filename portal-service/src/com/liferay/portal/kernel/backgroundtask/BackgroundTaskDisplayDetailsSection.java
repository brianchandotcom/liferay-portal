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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
* @author Andrew Betts
*/
public class BackgroundTaskDisplayDetailsSection
	implements BackgroundTaskDisplayJSONObject {

	public BackgroundTaskDisplayDetailsSection(
		String message, JSONArray jsonArray) {

		_message = message;

		_itemsList = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject item = jsonArray.getJSONObject(i);

			BackgroundTaskDisplayDetailsItem backgroundTaskDisplayDetailsItem =
				new BackgroundTaskDisplayDetailsItem(item);

			_itemsList.add(backgroundTaskDisplayDetailsItem);
		}
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("message", _message);
		jsonObject.put(
			"itemsList",
			BackgroundTaskDisplayJSONTransformer.toJSONArray(_itemsList));

		return jsonObject;
	}

	public JSONObject toJSONObject(Locale locale) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("message", LanguageUtil.get(locale, _message));
		jsonObject.put(
			"itemsList",
			BackgroundTaskDisplayJSONTransformer.toJSONArray(
				_itemsList, locale));

		return jsonObject;
	}

	private final List<BackgroundTaskDisplayDetailsItem> _itemsList;
	private final String _message;

}