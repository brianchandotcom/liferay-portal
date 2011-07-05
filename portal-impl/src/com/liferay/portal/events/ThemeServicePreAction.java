/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.impl.ColorSchemeImpl;
import com.liferay.portal.model.impl.ThemeImpl;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward Han
 */
public class ThemeServicePreAction extends Action {
	public void run(
			HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(
				WebKeys.THEME_DISPLAY);

			// Theme and color scheme

			Theme theme = themeDisplay.getTheme();
			ColorScheme colorScheme = themeDisplay.getColorScheme();

			if (theme != null) {
				if (_log.isInfoEnabled()) {
					_log.info("Theme already set, skipping");
				}

				return;
			}

			Layout layout = themeDisplay.getLayout();

			long companyId = themeDisplay.getCompanyId();

			boolean wapTheme = BrowserSnifferUtil.isWap(request);

			String contextPath = PortalUtil.getPathContext();

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
					themeId = ThemeImpl.getDefaultWapThemeId(companyId);
					colorSchemeId = ColorSchemeImpl.getDefaultWapColorSchemeId();
				}
				else {
					themeId = ThemeImpl.getDefaultRegularThemeId(companyId);
					colorSchemeId =
						ColorSchemeImpl.getDefaultRegularColorSchemeId();
				}

				theme = ThemeLocalServiceUtil.getTheme(
					companyId, themeId, wapTheme);
				colorScheme = ThemeLocalServiceUtil.getColorScheme(
					companyId, theme.getThemeId(), colorSchemeId, wapTheme);
			}

			request.setAttribute(WebKeys.THEME, theme);
			request.setAttribute(WebKeys.COLOR_SCHEME, colorScheme);

			themeDisplay.setLookAndFeel(contextPath, theme, colorScheme);
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ThemeServicePreAction.class);
}