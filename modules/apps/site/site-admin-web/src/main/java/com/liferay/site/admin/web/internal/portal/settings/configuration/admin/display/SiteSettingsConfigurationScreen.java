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

package com.liferay.site.admin.web.internal.portal.settings.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.site.admin.web.internal.constants.SiteAdminWebKeys;
import com.liferay.site.settings.configuration.admin.display.SiteSettingsConfigurationScreenContributor;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
public class SiteSettingsConfigurationScreen implements ConfigurationScreen {

	public SiteSettingsConfigurationScreen(
		SiteSettingsConfigurationScreenContributor
			siteSettingsConfigurationScreenContributor,
		ServletContext servletContext) {

		_siteSettingsConfigurationScreenContributor =
			siteSettingsConfigurationScreenContributor;
		_servletContext = servletContext;
	}

	@Override
	public String getCategoryKey() {
		return _siteSettingsConfigurationScreenContributor.getCategoryKey();
	}

	@Override
	public String getKey() {
		return _siteSettingsConfigurationScreenContributor.getKey();
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(
			ResourceBundleUtil.getBundle(
				locale, SiteSettingsConfigurationScreen.class),
			_siteSettingsConfigurationScreenContributor.getName(locale));
	}

	@Override
	public String getScope() {
		return ExtendedObjectClassDefinition.Scope.GROUP.getValue();
	}

	@Override
	public boolean isVisible() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		return _siteSettingsConfigurationScreenContributor.isVisible(
			themeDisplay.getSiteGroup());
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			httpServletRequest.setAttribute(
				SiteAdminWebKeys.SITE_SETTINGS_CONFIGURATION_SCREEN_CONTRIBUTOR,
				_siteSettingsConfigurationScreenContributor);

			_siteSettingsConfigurationScreenContributor.setAttributes(
				httpServletRequest, httpServletResponse);

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/configuration/screen/entry.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (ServletException servletException) {
			throw new IOException(
				"Unable to render /configuration/screen/entry.jsp",
				servletException);
		}
	}

	private final ServletContext _servletContext;
	private final SiteSettingsConfigurationScreenContributor
		_siteSettingsConfigurationScreenContributor;

}