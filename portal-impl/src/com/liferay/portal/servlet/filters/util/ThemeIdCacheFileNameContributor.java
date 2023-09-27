/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.servlet.filters.util;

import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.ThemeLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Sierra Andrés
 */
public class ThemeIdCacheFileNameContributor
	implements CacheFileNameContributor {

	@Override
	public String getParameterName() {
		return "themeId";
	}

	@Override
	public String getParameterValue(HttpServletRequest httpServletRequest) {
		String themeId = httpServletRequest.getParameter(getParameterName());

		Theme theme = ThemeLocalServiceUtil.fetchTheme(
			PortalUtil.getCompanyId(httpServletRequest), themeId);

		if (theme == null) {
			return null;
		}

		return themeId;
	}

}