/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Murilo Stodolni
 */
public class JSONObjectSanitizerUtil {

	public static void sanitize(JSONObject jsonObject, String[] keys) {
		if (jsonObject.length() == 0) {
			return;
		}

		JSONArray jsonArray = jsonObject.names();

		for (int i = 0; i < jsonArray.length(); ++i) {
			String key = jsonArray.getString(i);

			if (ArrayUtil.contains(keys, key)) {
				jsonObject.remove(key);
			}
			else {
				_sanitize(jsonObject.get(key), keys);
			}
		}
	}

	private static void _sanitize(Object object, String[] keys) {
		if (object instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray)object;

			for (int i = 0; i < jsonArray.length(); ++i) {
				_sanitize(jsonArray.get(i), keys);
			}
		}
		else if (object instanceof JSONObject) {
			sanitize((JSONObject)object, keys);
		}
	}

}