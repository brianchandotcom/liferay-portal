/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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