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

package com.liferay.mobile.device.rules.web.internal.rule.group.action;

import com.liferay.mobile.device.rules.action.ActionHandler;
import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Edward Han
 */
@Component(immediate = true, service = ActionHandler.class)
public class ThemeModificationActionHandler implements ActionHandler {

	public static String getHandlerType() {
		return ThemeModificationActionHandler.class.getName();
	}

	@Override
	public void applyAction(
		MDRAction mdrAction, HttpServletRequest request,
		HttpServletResponse response) {

		long companyId = _portal.getCompanyId(request);

		UnicodeProperties typeSettingsProperties =
			mdrAction.getTypeSettingsProperties();

		String themeId = GetterUtil.getString(
			typeSettingsProperties.getProperty("themeId"));

		Theme theme = _themeLocalService.fetchTheme(companyId, themeId);

		if (theme == null) {
			theme = _themeLocalService.getTheme(companyId, themeId);
		}

		if (theme == null) {
			return;
		}

		request.setAttribute(WebKeys.THEME, theme);

		String colorSchemeId = GetterUtil.getString(
			typeSettingsProperties.getProperty("colorSchemeId"));

		ColorScheme colorScheme = _themeLocalService.fetchColorScheme(
			companyId, themeId, colorSchemeId);

		if (colorScheme == null) {
			colorScheme = _themeLocalService.getColorScheme(
				companyId, themeId, colorSchemeId);
		}

		request.setAttribute(WebKeys.COLOR_SCHEME, colorScheme);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		themeDisplay.setLookAndFeel(theme, colorScheme);
	}

	@Override
	public String getEditorJSP() {
		return "/action/theme.jsp";
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

	private static final Collection<String> _propertyNames =
		Collections.unmodifiableCollection(
			Arrays.asList("colorSchemeId", "themeId"));

	@Reference
	private Portal _portal;

	@Reference
	private ThemeLocalService _themeLocalService;

}