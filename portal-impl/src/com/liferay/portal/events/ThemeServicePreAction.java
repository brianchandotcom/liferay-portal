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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.ColorSchemeFactoryUtil;
import com.liferay.portal.kernel.util.ThemeFactoryUtil;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward Han
 */
public class ThemeServicePreAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		try {
			servicePre(request, response);
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void servicePre(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Theme theme = themeDisplay.getTheme();
		ColorScheme colorScheme = themeDisplay.getColorScheme();

		if (theme != null) {
			if (_log.isInfoEnabled()) {
				_log.info("Theme is already set");
			}

			return;
		}

		Layout layout = themeDisplay.getLayout();

		boolean wapTheme = BrowserSnifferUtil.isWap(request);

		if (layout != null) {
			if (wapTheme) {
				theme = layout.getWapTheme();
				colorScheme = layout.getWapColorScheme();
			}
			else {
				theme = layout.getTheme();
				colorScheme = layout.getColorScheme();
			}
		}
		else {
			String themeId = null;
			String colorSchemeId = null;

			if (wapTheme) {
				themeId = ThemeFactoryUtil.getDefaultWapThemeId(
					themeDisplay.getCompanyId());
				colorSchemeId =
					ColorSchemeFactoryUtil.getDefaultWapColorSchemeId();
			}
			else {
				themeId = ThemeFactoryUtil.getDefaultRegularThemeId(
					themeDisplay.getCompanyId());
				colorSchemeId =
					ColorSchemeFactoryUtil.getDefaultRegularColorSchemeId();
			}

			theme = ThemeLocalServiceUtil.getTheme(
				themeDisplay.getCompanyId(), themeId, wapTheme);
			colorScheme = ThemeLocalServiceUtil.getColorScheme(
				themeDisplay.getCompanyId(), theme.getThemeId(), colorSchemeId,
				wapTheme);
		}

		request.setAttribute(WebKeys.THEME, theme);
		request.setAttribute(WebKeys.COLOR_SCHEME, colorScheme);

		themeDisplay.setLookAndFeel(theme, colorScheme);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ThemeServicePreAction.class);

}