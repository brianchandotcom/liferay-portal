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

import java.util.List;
import java.util.Locale;

/**
 * @author Andrew Betts
 */
public class BackgroundTaskDisplayJSONTransformer {

	public static JSONArray toJSONArray(
		List<? extends BackgroundTaskDisplayJSONObject>
			backgroundTaskDisplayJSONObjects) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (BackgroundTaskDisplayJSONObject backgroundTaskDisplayJSONObject :
				backgroundTaskDisplayJSONObjects) {

			jsonArray.put(backgroundTaskDisplayJSONObject.toJSONObject());
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<? extends BackgroundTaskDisplayJSONObject>
			backgroundTaskDisplayJSONObjects,
		Locale locale) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (BackgroundTaskDisplayJSONObject backgroundTaskDisplayJSONObject :
				backgroundTaskDisplayJSONObjects) {

			jsonArray.put(backgroundTaskDisplayJSONObject.toJSONObject(locale));
		}

		return jsonArray;
	}

}