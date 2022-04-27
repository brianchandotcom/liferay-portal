/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.cookies.banner.web.internal.display.context;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo García
 */
public class CookiesBannerConfigurationDisplayContext {

	public CookiesBannerConfigurationDisplayContext(
		RenderRequest renderRequest) {

		_renderRequest = renderRequest;
	}

	public String getCookieDescription(
		String cookie, HttpServletRequest httpServletRequest) {

		return LanguageUtil.get(
			httpServletRequest, "cookies-description[" + cookie + "]");
	}

	public String getCookieTitle(
		String cookie, HttpServletRequest httpServletRequest) {

		return LanguageUtil.get(
			httpServletRequest, "cookies-title[" + cookie + "]");
	}

	public boolean isShowButtons() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isStatePopUp()) {
			return true;
		}

		return false;
	}

	private final RenderRequest _renderRequest;

}