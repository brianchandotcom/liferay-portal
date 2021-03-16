/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;

/**
 * @author Petteri Karttunen
 */
public class BlueprintJSONUtil {

	public static JSONArray getConfigurationSectionJSONArray(
			Blueprint blueprint, String key)
		throws JSONException {

		if ((blueprint == null) ||
			Validator.isBlank(blueprint.getConfiguration())) {

			return null;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			blueprint.getConfiguration());

		if (jsonObject == null) {
			return null;
		}

		return jsonObject.getJSONArray(key);
	}

	public static JSONObject getConfigurationSectionJSONObject(
			Blueprint blueprint, String key)
		throws JSONException {

		if ((blueprint == null) ||
			Validator.isBlank(blueprint.getConfiguration())) {

			return null;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			blueprint.getConfiguration());

		if (jsonObject == null) {
			return null;
		}

		return jsonObject.getJSONObject(key);
	}

	public static String[] getSelectedElementsSection(Blueprint blueprint)
		throws JSONException {

		JSONArray jsonArray = _getElementSectionJSONArray(blueprint);

		if (jsonArray == null) {
			return new String[0];
		}

		String[] array = new String[jsonArray.length()];

		for (int i = 0; i < jsonArray.length(); i++) {
			array[i] = jsonArray.getJSONObject(
				i
			).toString(
				4
			);
		}

		return array;
	}

	private static JSONArray _getElementSectionJSONArray(Blueprint blueprint)
		throws JSONException {

		if ((blueprint == null) ||
			Validator.isBlank(blueprint.getSelectedElements())) {

			return null;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			blueprint.getSelectedElements());

		if (jsonArray == null) {
			return null;
		}

		return jsonArray;
	}

}