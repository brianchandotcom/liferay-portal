/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.hashed.files.HashedFilesRegistry;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	property = "key=servlet.service.events.pre", service = LifecycleAction.class
)
public class FrontendResourcePreAction extends Action {

	@Override
	public void run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ActionException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return;
		}

		Theme theme = themeDisplay.getTheme();

		String contextPath = theme.getContextPath();

		boolean rtl = _portal.isRightToLeft(httpServletRequest);

		String hashedFileURI = _hashedFilesRegistry.getHashedFileURI(
			contextPath + (rtl ? "/css/clay_rtl.css" : "/css/clay.css"));

		if (hashedFileURI != null) {
			themeDisplay.setDefaultClayCSSURL(hashedFileURI);
		}

		hashedFileURI = _hashedFilesRegistry.getHashedFileURI(
			contextPath + (rtl ? "/css/main_rtl.css" : "/css/main.css"));

		if (hashedFileURI != null) {
			themeDisplay.setDefaultMainCSSURL(hashedFileURI);
		}

		hashedFileURI = _hashedFilesRegistry.getHashedFileURI(
			contextPath + "/js/main.js");

		if (hashedFileURI != null) {
			themeDisplay.setDefaultMainJSURL(hashedFileURI);
		}
	}

	@Reference
	private HashedFilesRegistry _hashedFilesRegistry;

	@Reference
	private Portal _portal;

}