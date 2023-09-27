/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.initializer.util.internal;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceInitializerUtil {

	public static String getValue(
		JSONObject jsonObject, String propertyName, String key) {

		String value = jsonObject.getString(propertyName);

		if (Validator.isNull(value)) {
			value = TextFormatter.format(key, TextFormatter.J);
		}

		return value;
	}

}