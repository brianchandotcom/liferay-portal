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
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.ThemeLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ColorSchemeFactoryUtil;
import com.liferay.portal.kernel.util.ThemeFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward Han
 */
public class ThemeServicePreAction extends Action {

	@Override
	public void run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ActionException {

		try {
			servicePre(httpServletRequest, httpServletResponse);
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void servicePre(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Theme theme = themeDisplay.getTheme();

		if (theme != null) {
			if (_log.isInfoEnabled()) {
				_log.info("Theme is already set");
			}

			return;
		}

		ColorScheme colorScheme = themeDisplay.getColorScheme();
		Layout layout = themeDisplay.getLayout();

		if (layout != null) {
			theme = layout.getTheme();
			colorScheme = layout.getColorScheme();
		}
		else {
			String themeId = ThemeFactoryUtil.getDefaultRegularThemeId(
				themeDisplay.getCompanyId());
			String colorSchemeId =
				ColorSchemeFactoryUtil.getDefaultRegularColorSchemeId();

			theme = ThemeLocalServiceUtil.getTheme(
				themeDisplay.getCompanyId(), themeId);

			colorScheme = ThemeLocalServiceUtil.getColorScheme(
				themeDisplay.getCompanyId(), theme.getThemeId(), colorSchemeId);
		}

		httpServletRequest.setAttribute(WebKeys.COLOR_SCHEME, colorScheme);
		httpServletRequest.setAttribute(WebKeys.THEME, theme);

		themeDisplay.setLookAndFeel(theme, colorScheme);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ThemeServicePreAction.class);

}