/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.ThemeFactory;
import com.liferay.portal.kernel.util.ThemeFactoryUtil;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.impl.ThemeImpl;

/**
 * @author Harrison Schueler
 */
public class ThemeFactoryImpl implements ThemeFactory {

	@Override
	public Theme getDefaultRegularTheme(long companyId) throws SystemException {
		return new ThemeImpl(
			ThemeFactoryUtil.getDefaultRegularThemeId(companyId),
			StringPool.BLANK);
	}

	@Override
	public String getDefaultRegularThemeId(long companyId)
		throws SystemException {

		String defaultRegularThemeId = PrefsPropsUtil.getString(
			companyId, PropsKeys.DEFAULT_REGULAR_THEME_ID);

		return PortalUtil.getJsSafePortletId(defaultRegularThemeId);
	}

	@Override
	public Theme getDefaultWapTheme(long companyId) throws SystemException {
		return new ThemeImpl(
			ThemeFactoryUtil.getDefaultWapThemeId(companyId), StringPool.BLANK);
	}

	@Override
	public String getDefaultWapThemeId(long companyId) throws SystemException {
		String defaultWapThemeId = PrefsPropsUtil.getString(
			companyId, PropsKeys.DEFAULT_WAP_THEME_ID);

		return PortalUtil.getJsSafePortletId(defaultWapThemeId);
	}

	@Override
	public Theme getTheme() {
		return new ThemeImpl();
	}

	@Override
	public Theme getTheme(String themeId) {
		return new ThemeImpl(themeId);
	}

	@Override
	public Theme getTheme(String themeId, String name) {
		return new ThemeImpl(themeId, name);
	}

}