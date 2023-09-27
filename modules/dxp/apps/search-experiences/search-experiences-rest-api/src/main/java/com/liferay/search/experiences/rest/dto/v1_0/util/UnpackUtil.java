/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.dto.v1_0.util;

import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.util.Collection;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class UnpackUtil {

	public static Object unpack(Object value) {
		if (value instanceof Collection) {
			return JSONFactoryUtil.createJSONArray((Collection<?>)value);
		}

		if (value instanceof Map) {
			return JSONFactoryUtil.createJSONObject((Map<?, ?>)value);
		}

		if (value instanceof Object[]) {
			return JSONFactoryUtil.createJSONArray((Object[])value);
		}

		return value;
	}

}