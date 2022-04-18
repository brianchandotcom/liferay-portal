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

package com.liferay.frontend.icons.web.internal.portal.settings.configuration.admin.display;

import com.liferay.frontend.icons.web.internal.configuration.FrontendIconPacksConfiguration;
import com.liferay.frontend.icons.web.internal.display.context.FrontendIconsSiteSettingsConfigurationDisplayContext;
import com.liferay.frontend.icons.web.internal.repository.FrontendIconsResourcePackRepository;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.settings.configuration.admin.display.SiteSettingsConfigurationScreenContributor;

import java.util.Locale;

import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryce Osterhaus
 */
@Component(service = SiteSettingsConfigurationScreenContributor.class)
public class FrontendIconsSiteSettingsConfigurationScreenContributor
	implements SiteSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "frontend-icons";
	}

	@Override
	public String getJspPath() {
		return "/site_settings/frontend_icons.jsp";
	}

	@Override
	public String getKey() {
		return "site-configuration-frontend-icons";
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(
			locale, "frontend-icons-site-configuration-name");
	}

	@Override
	public String getSaveMVCActionCommandName() {
		return null;
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		FrontendIconPacksConfiguration frontendIconPacksConfiguration =
			_getFrontendIconPacksConfiguration(themeDisplay.getSiteGroupId());

		httpServletRequest.setAttribute(
			FrontendIconsSiteSettingsConfigurationDisplayContext.class.
				getName(),
			new FrontendIconsSiteSettingsConfigurationDisplayContext(
				frontendIconPacksConfiguration.selectedIconPacks(),
				_frontendIconsResourcePackRepository, httpServletRequest,
				(RenderResponse)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE)));
	}

	private FrontendIconPacksConfiguration _getFrontendIconPacksConfiguration(
		long groupId) {

		try {
			return ConfigurationProviderUtil.getGroupConfiguration(
				FrontendIconPacksConfiguration.class, groupId);
		}
		catch (ConfigurationException configurationException) {
			_log.error(
				"Unable to get icon pack configuration for site." +
					configurationException.toString());

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FrontendIconsSiteSettingsConfigurationScreenContributor.class);

	@Reference
	private FrontendIconsResourcePackRepository
		_frontendIconsResourcePackRepository;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.icons.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}