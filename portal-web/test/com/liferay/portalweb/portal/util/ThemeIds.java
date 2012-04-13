/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.util;

import com.liferay.portal.model.Theme;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.ThemeServiceHttp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ThemeIds {

	public static int getCount() {
		return _instance._getCount();
	}

	public static String getThemeId() {
		return _instance._getThemeId();
	}

	public static void iterate() {
		_instance._iterate();
	}

	private ThemeIds() {
		try {
			if (TestPropsValues.THEME_IDS.length > 0) {
				_themeIds = TestPropsValues.THEME_IDS;
			}
			else {
				HttpPrincipal httpPrincipal = new HttpPrincipal(
					TestPropsValues.PORTAL_URL);

				List<String> themeIds = new ArrayList<String>();

				List<Theme> themes = ThemeServiceHttp.getThemes(
					httpPrincipal, TestPropsValues.getCompanyId());

				for (Theme theme : themes) {
					if (!theme.isWapTheme()) {
						themeIds.add(theme.getThemeId());
					}
				}

				_themeIds = themeIds.toArray(new String[themeIds.size()]);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int _getCount() {
		return _themeIds.length;
	}

	private String _getThemeId() {
		return _themeIds[_pos];
	}

	private void _iterate() {
		_pos++;
	}

	private static ThemeIds _instance = new ThemeIds();

	private int _pos;
	private String[] _themeIds = new String[0];

}