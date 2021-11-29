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

package com.liferay.object.web.internal.object.definitions.portlet.action.util;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Arrays;

/**
 * @author Gabriel Albuquerque
 */
public class ExportImportObjectDefinitiontUtil {

	public static void applyObjectLayoutColumnJSONObject(
		JSONObject objectDefinitionJSONObject,
		UnsafeFunction<JSONObject, JSONObject, Exception> unsafeFunction) {

		_apply(
			objectDefinitionJSONObject, unsafeFunction, "objectLayouts",
			"objectLayoutTabs", "objectLayoutBoxes", "objectLayoutRows",
			"objectLayoutColumns");
	}

	private static void _apply(
		JSONObject jsonObject,
		UnsafeFunction<JSONObject, JSONObject, Exception> unsafeFunction,
		String... properties) {

		JSONArray jsonArray = (JSONArray)jsonObject.get(properties[0]);

		if (properties.length != 1) {
			for (int i = 0; i < jsonArray.length(); i++) {
				_apply(
					(JSONObject)jsonArray.get(i), unsafeFunction,
					Arrays.copyOfRange(properties, 1, properties.length));
			}

			return;
		}

		JSONArray newJSONArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject internalJSONObject = (JSONObject)jsonArray.get(i);

			newJSONArray.put(() -> unsafeFunction.apply(internalJSONObject));
		}

		jsonObject.put(properties[0], newJSONArray);
	}

}