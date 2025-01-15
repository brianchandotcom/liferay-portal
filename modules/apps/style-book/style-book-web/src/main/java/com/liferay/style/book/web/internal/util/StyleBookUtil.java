/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.web.internal.util;

import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.ThemeLocalServiceUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Evan Thibodeau
 */
public class StyleBookUtil {

	public static String getThemeName(
		CETManager cetManager, long companyId,
		HttpServletRequest httpServletRequest, String themeId) {

		String name = themeId;

		Theme theme = ThemeLocalServiceUtil.fetchTheme(companyId, themeId);

		if (theme != null) {
			name = LanguageUtil.format(
				httpServletRequest, "x-theme", theme.getName());
		}
		else {
			CET cet = cetManager.getCET(companyId, themeId);

			if (cet != null) {
				name = LanguageUtil.format(
					httpServletRequest, "x-theme-css-client-extension",
					cet.getName());
			}
		}

		return name;
	}

}