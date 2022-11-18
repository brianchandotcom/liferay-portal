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
 * @author Albert Gomes Cabral
 * @author Renato Rego
 */
@Component(service = PortalSettingsConfigurationScreenContributor.class)
public class
	LocalePrependFriendlyURLStylePortalSettingsConfigurationScreenContributor
		extends BaseEditCompanyPortalSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "localization";
	}

	@Override
	public String getJspPath() {
		return "/locale_prepend_friendly_url_style.jsp";
	}

	@Override
	public String getKey() {
		return "locale-prepend-friendly-url-style";
	}

	@Override
	public void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		httpServletRequest.setAttribute(
			PropsKeys.LOCALE_PREPEND_FRIENDLY_URL_STYLE,
			PrefsPropsUtil.getInteger(
				themeDisplay.getCompanyId(),
				PropsKeys.LOCALE_PREPEND_FRIENDLY_URL_STYLE));
	}

}