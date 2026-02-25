/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.frontend.esm;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Iván Zaera Avellón
 */
public class FrontendESMUtil {

	public static String buildExportsURL(
		ThemeDisplay themeDisplay, String contextPath, String exportModule) {

		return buildURL(
			themeDisplay, contextPath,
			"exports/" + exportModule.replaceAll("/", "\\$"));
	}

	public static String buildURL(
		ThemeDisplay themeDisplay, String contextPath) {

		return buildURL(themeDisplay, contextPath, "index");
	}

	public static String buildURL(
		ThemeDisplay themeDisplay, String contextPath, String submodule) {

		FrontendESM frontendESM = _frontendESMSnapshot.get();

		return frontendESM.buildURL(themeDisplay, contextPath, submodule);
	}

	private static final Snapshot<FrontendESM> _frontendESMSnapshot =
		new Snapshot<>(FrontendESMUtil.class, FrontendESM.class);

}