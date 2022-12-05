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

package com.liferay.portal.settings.web.internal.portal.settings.configuration.admin.display;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Fernando Vilela
 * @author João Victor Torres
 */
@Component(service = PortalSettingsConfigurationScreenContributor.class)
public class UserLayoutPortalSettingsConfigurationScreenContributor
	extends BaseEditCompanyPortalSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "users";
	}

	@Override
	public String getJspPath() {
		return "/user_layout_configuration.jsp";
	}

	@Override
	public String getKey() {
		return "user-layout-configuration";
	}

	@Override
	public void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		httpServletRequest.setAttribute(
			PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE,
			PrefsPropsUtil.getBoolean(
				themeDisplay.getCompanyId(),
				PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE));

		httpServletRequest.setAttribute(
			PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED,
			PrefsPropsUtil.getBoolean(
				themeDisplay.getCompanyId(),
				PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED));

		httpServletRequest.setAttribute(
			PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE,
			PrefsPropsUtil.getBoolean(
				themeDisplay.getCompanyId(),
				PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE));

		httpServletRequest.setAttribute(
			PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED,
			PrefsPropsUtil.getBoolean(
				themeDisplay.getCompanyId(),
				PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED));
	}

}