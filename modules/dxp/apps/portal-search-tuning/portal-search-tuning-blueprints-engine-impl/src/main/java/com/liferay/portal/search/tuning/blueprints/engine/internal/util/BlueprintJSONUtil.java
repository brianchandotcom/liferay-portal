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

package com.liferay.portal.search.tuning.blueprints.engine.internal.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Optional;

/**
 * @author Petteri Karttunen
 */
public class BlueprintJSONUtil {

	public static Optional<String[]> getStringArrayOptional(
		JSONObject jsonObject, String key) {

		String[] stringArray = jsonArrayToStringArray(
			jsonObject.getJSONArray(key));

		if ((stringArray == null) || (stringArray.length == 0)) {
			return Optional.empty();
		}

		return Optional.of(stringArray);
	}

	public static String[] jsonArrayToStringArray(JSONArray jsonArray) {
		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return new String[0];
		}

		String[] stringArray = new String[jsonArray.length()];

		for (int i = 0; i < jsonArray.length(); i++) {
			stringArray[i] = jsonArray.getString(i);
		}

		return stringArray;
	}

}