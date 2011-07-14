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

package com.liferay.portal.mobile.device.action.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.mobile.device.action.DeviceProfileActionHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.mobile.model.DeviceProfileAction;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.ThemeLocalService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward Han
 */
public class ThemeDeviceActionHandler implements DeviceProfileActionHandler {
	public void applyDeviceAction(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			DeviceProfileAction deviceProfileAction)
		throws PortalException, SystemException {

		UnicodeProperties deviceProfileTypeSettings = deviceProfileAction.
			getTypeSettingsProperties();

		String themeId = GetterUtil.get(
			deviceProfileTypeSettings.getProperty(THEME_ID), StringPool.BLANK);

		String colorSchemeId = GetterUtil.get(
			deviceProfileTypeSettings.getProperty(
				COLOR_SCHEME_ID), StringPool.BLANK);

		long companyId = PortalUtil.getCompanyId(httpServletRequest);

		Theme theme = _themeLocalService.findTheme(companyId, themeId);

		ColorScheme colorScheme = _themeLocalService.findColorScheme(
			companyId, themeId, colorSchemeId);

		if ((theme == null) || (colorScheme == null)) {
			return;
		}

		httpServletRequest.setAttribute(WebKeys.THEME, theme);
		httpServletRequest.setAttribute(WebKeys.COLOR_SCHEME, colorScheme);

		ThemeDisplay themeDisplay = (ThemeDisplay) httpServletRequest.
			getAttribute(WebKeys.THEME_DISPLAY);

		String contextPath = PortalUtil.getPathContext();

		themeDisplay.setLookAndFeel(contextPath, theme, colorScheme);
	}

	public Collection<String> getPropertyNames() {
		return _propertyNames;
	}

	public String getType() {
		return ThemeDeviceActionHandler.class.getName();
	}

	public void setThemeLocalService(ThemeLocalService themeLocalService) {
		_themeLocalService = themeLocalService;
	}

	private static final String COLOR_SCHEME_ID = "colorSchemeId";
	private static final String THEME_ID = "themeId";

	private static Collection<String> _propertyNames;

	private ThemeLocalService _themeLocalService;

	static {
		_propertyNames = new ArrayList<String>(2);

		_propertyNames.add(THEME_ID);
		_propertyNames.add(COLOR_SCHEME_ID);

		_propertyNames = Collections.unmodifiableCollection(_propertyNames);
	}
}