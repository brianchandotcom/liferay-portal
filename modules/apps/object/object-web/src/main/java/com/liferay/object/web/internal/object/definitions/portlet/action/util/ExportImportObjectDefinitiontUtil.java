/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static void apply(
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