/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.custom.filter.portlet;

import com.liferay.portal.kernel.util.Validator;

/**
 * @author André de Oliveira
 */
public class CustomFilterPortletUtil {

	public static String getParameterName(
		CustomFilterPortletPreferences customFilterPortletPreferences) {

		String parameterName =
			customFilterPortletPreferences.getParameterName();

		if (Validator.isNotNull(parameterName)) {
			return parameterName;
		}

		String filterField = customFilterPortletPreferences.getFilterField();

		if (Validator.isNotNull(filterField)) {
			return filterField;
		}

		return "customfilter";
	}

}