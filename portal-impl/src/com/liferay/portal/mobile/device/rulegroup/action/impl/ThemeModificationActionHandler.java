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

package com.liferay.portal.mobile.device.rulegroup.action.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.mobile.device.rulegroup.action.ActionHandler;
import com.liferay.portal.kernel.util.ColorSchemeFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Theme;
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

	public static String getHandlerType() {
		return ThemeModificationActionHandler.class.getName();
	}

	@Override
	public void applyAction(
		MDRAction mdrAction, HttpServletRequest request,
		HttpServletResponse response) {

		long companyId = PortalUtil.getCompanyId(request);

		UnicodeProperties typeSettingsProperties =
			mdrAction.getTypeSettingsProperties();

		String themeId = GetterUtil.getString(
			typeSettingsProperties.getProperty("themeId"));

		Theme theme = _themeLocalService.fetchTheme(companyId, themeId);

		if (theme == null) {
			return;
		}

		request.setAttribute(WebKeys.THEME, theme);

		String colorSchemeId = GetterUtil.getString(
			typeSettingsProperties.getProperty("colorSchemeId"));

		ColorScheme colorScheme = _themeLocalService.fetchColorScheme(
			companyId, themeId, colorSchemeId);

		if (colorScheme == null) {
			colorScheme = ColorSchemeFactoryUtil.getColorScheme();
		}

		request.setAttribute(WebKeys.COLOR_SCHEME, colorScheme);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		themeDisplay.setLookAndFeel(theme, colorScheme);
	}

	@Override
	public Collection<String> getPropertyNames() {
		return _propertyNames;
	}

	@Override
	public String getType() {
		return getHandlerType();
	}

	public void setThemeLocalService(ThemeLocalService themeLocalService) {
		_themeLocalService = themeLocalService;
	}

	private static Collection<String> _propertyNames;

	@BeanReference(type = ThemeLocalService.class)
	private ThemeLocalService _themeLocalService;

	static {
		_propertyNames = new ArrayList<String>(2);

		_propertyNames.add("colorSchemeId");
		_propertyNames.add("themeId");

		_propertyNames = Collections.unmodifiableCollection(_propertyNames);
	}

}