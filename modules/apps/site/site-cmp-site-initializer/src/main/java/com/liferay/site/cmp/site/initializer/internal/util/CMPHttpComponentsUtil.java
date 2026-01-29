/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.util;

import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Carolina Barbosa
 */
public class CMPHttpComponentsUtil {

	public static String addParameter(
		HttpServletRequest httpServletRequest, String parameterName,
		String url) {

		String parameterValue = ParamUtil.getString(
			httpServletRequest, parameterName);

		if (Validator.isNull(parameterValue)) {
			return url;
		}

		return HttpComponentsUtil.addParameter(
			url, parameterName, parameterValue);
	}

}