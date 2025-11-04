/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.frontend.esm;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Iván Zaera Avellón
 */
public class FrontendESMUtil {

	public static String buildExportsURL(
		ThemeDisplay themeDisplay, String contextPath, String exportModule) {

		exportModule = exportModule.replaceAll("/", "\\$");

		return StringBundler.concat(
			themeDisplay.getPathContext(), "/o/", contextPath,
			"/__liferay__/exports/", exportModule, ".js");
	}

	public static String buildURL(
		ThemeDisplay themeDisplay, String contextPath) {

		return buildURL(themeDisplay, contextPath, "index");
	}

	public static String buildURL(
		ThemeDisplay themeDisplay, String contextPath, String submodule) {

		return StringBundler.concat(
			themeDisplay.getPathContext(), "/o/", contextPath, "/__liferay__/",
			submodule, ".js");
	}

}