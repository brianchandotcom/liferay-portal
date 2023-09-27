/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_10_2.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Carolina Barbosa
 */
public class DDMFormFieldUpgradeProcessUtil {

	public static JSONArray getNormalizedJSONArray(JSONArray jsonArray) {
		JSONArray normalizedJSONArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			normalizedJSONArray.put(getNormalizedName(jsonArray.getString(i)));
		}

		return normalizedJSONArray;
	}

	public static String getNormalizedName(String name) {
		if (_startsWithDigit(name)) {
			return StringPool.UNDERLINE + name;
		}

		return name;
	}

	public static boolean isDDMFormFieldWithOptions(String type) {
		if (StringUtil.equals(type, "checkbox_multiple") ||
			StringUtil.equals(type, "radio") ||
			StringUtil.equals(type, "select")) {

			return true;
		}

		return false;
	}

	private static boolean _startsWithDigit(String name) {
		if (Validator.isNull(name)) {
			return false;
		}

		return Character.isDigit(name.charAt(0));
	}

}