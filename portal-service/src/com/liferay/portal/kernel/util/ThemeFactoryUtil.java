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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.Theme;

/**
 * @author Harrison Schueler
 */
public class ThemeFactoryUtil {

	public static Theme getDefaultRegularTheme(long companyId) {
		return getThemeFactory().getDefaultRegularTheme(companyId);
	}

	public static String getDefaultRegularThemeId(long companyId) {
		return getThemeFactory().getDefaultRegularThemeId(companyId);
	}

	public static Theme getDefaultWapTheme(long companyId) {
		return getThemeFactory().getDefaultWapTheme(companyId);
	}

	public static String getDefaultWapThemeId(long companyId) {
		return getThemeFactory().getDefaultWapThemeId(companyId);
	}

	public static Theme getTheme() {
		return getThemeFactory().getTheme();
	}

	public static Theme getTheme(String themeId) {
		return getThemeFactory().getTheme(themeId);
	}

	public static Theme getTheme(String themeId, String name) {
		return getThemeFactory().getTheme(themeId, name);
	}

	public static ThemeFactory getThemeFactory() {
		PortalRuntimePermission.checkGetBeanProperty(ThemeFactoryUtil.class);

		return _ThemeFactory;
	}

	public void setThemeFactory(ThemeFactory ThemeFactory) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ThemeFactory = ThemeFactory;
	}

	private static ThemeFactory _ThemeFactory;

}