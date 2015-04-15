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
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class RandomLookAndFeelAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		try {

			// Do not randomize look and feel unless the user is logged in

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			if (!themeDisplay.isSignedIn()) {
				return;
			}

			// Do not randomize look and feel unless the user is accessing the
			// portal

			String requestURI = GetterUtil.getString(request.getRequestURI());

			if (!requestURI.endsWith("/portal/layout")) {
				return;
			}

			// Do not randomize look and feel unless the user is accessing a
			// personal layout

			Layout layout = themeDisplay.getLayout();

			if (layout == null) {
				return;
			}

			boolean wapTheme = BrowserSnifferUtil.isWap(request);

			List<Theme> themes = ThemeLocalServiceUtil.getPageThemes(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				themeDisplay.getUserId(), wapTheme);

			if (!themes.isEmpty()) {
				Theme theme = themes.get(RandomUtil.nextInt(themes.size()));

				List<ColorScheme> colorSchemes = theme.getColorSchemes();

				ColorScheme colorScheme = colorSchemes.get(
					RandomUtil.nextInt(colorSchemes.size()));

				LayoutServiceUtil.updateLookAndFeel(
					layout.getGroupId(), layout.getPlid(), theme.getThemeId(),
					colorScheme.getColorSchemeId(), layout.getCss(), wapTheme);

				themeDisplay.setLookAndFeel(theme, colorScheme);

				request.setAttribute(WebKeys.THEME, theme);
				request.setAttribute(WebKeys.COLOR_SCHEME, colorScheme);
			}
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new ActionException(e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RandomLookAndFeelAction.class);

}