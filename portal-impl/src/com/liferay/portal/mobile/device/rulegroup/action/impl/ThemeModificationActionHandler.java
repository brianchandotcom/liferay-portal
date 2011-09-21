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

package com.liferay.portal.mobile.device.rulegroup.action.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.mobile.device.rulegroup.action.ActionHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.impl.ColorSchemeImpl;
import com.liferay.portal.service.ThemeLocalService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward Han
 */
public class ThemeModificationActionHandler implements ActionHandler {

	public void applyAction(
			MDRAction action, HttpServletRequest request,
			HttpServletResponse response)
		throws PortalException, SystemException {

		UnicodeProperties typeSettingsProperties =
			action.getTypeSettingsProperties();

		String themeId = GetterUtil.get(
			typeSettingsProperties.getProperty(THEME_ID), StringPool.BLANK);

		String colorSchemeId = GetterUtil.get(
			typeSettingsProperties.getProperty(COLOR_SCHEME_ID),
			StringPool.BLANK);

		long companyId = PortalUtil.getCompanyId(request);

		Theme theme = _themeLocalService.fetchTheme(companyId, themeId);

		if (theme == null) {
			return;
		}

		ColorScheme colorScheme = _themeLocalService.fetchColorScheme(
			companyId, themeId, colorSchemeId);

		if (colorScheme == null) {
			colorScheme = ColorSchemeImpl.getNullColorScheme();
		}

		request.setAttribute(WebKeys.THEME, theme);
		request.setAttribute(WebKeys.COLOR_SCHEME, colorScheme);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

		String contextPath = PortalUtil.getPathContext();

		themeDisplay.setLookAndFeel(contextPath, theme, colorScheme);
	}

	public static String getHandlerType() {
		return ThemeModificationActionHandler.class.getName();
	}

	public Collection<String> getPropertyNames() {
		return _propertyNames;
	}

	public String getType() {
		return getHandlerType();
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