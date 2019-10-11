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

package com.liferay.portal.settings.web.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.settings.configuration.admin.display.PortalSettingsConfigurationScreenContributor;
import com.liferay.portal.settings.web.internal.constants.PortalSettingsWebKeys;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Drew Brokke
 */
public class PortalSettingsConfigurationScreen implements ConfigurationScreen {

	public PortalSettingsConfigurationScreen(
		PortalSettingsConfigurationScreenContributor
			portalSettingsConfigurationScreenContributor,
		ServletContext servletContext) {

		_portalSettingsConfigurationScreenContributor =
			portalSettingsConfigurationScreenContributor;
		_servletContext = servletContext;
	}

	@Override
	public String getCategoryKey() {
		return _portalSettingsConfigurationScreenContributor.getCategoryKey();
	}

	@Override
	public String getKey() {
		return _portalSettingsConfigurationScreenContributor.getKey();
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(
			ResourceBundleUtil.getBundle(
				locale, PortalSettingsConfigurationScreen.class),
			_portalSettingsConfigurationScreenContributor.getName(locale));
	}

	@Override
	public String getScope() {
		return "company";
	}

	@Override
	public boolean isVisible() {
		return _portalSettingsConfigurationScreenContributor.isVisible();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			httpServletRequest.setAttribute(
				PortalSettingsWebKeys.
					PORTAL_SETTINGS_CONFIGURATION_SCREEN_CONTRIBUTOR,
				_portalSettingsConfigurationScreenContributor);
			httpServletRequest.setAttribute(
				PortalSettingsWebKeys.DELETE_CONFIRMATION_TEXT,
				UnicodeLanguageUtil.get(
					ResourceBundleUtil.getBundle(
						httpServletRequest.getLocale(),
						PortalSettingsConfigurationScreen.class),
					"are-you-sure-you-want-to-reset-the-configured-values"));

			_portalSettingsConfigurationScreenContributor.setAttributes(
				httpServletRequest, httpServletResponse);

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/configuration/screen/entry.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (ServletException se) {
			throw new IOException(
				"Unable to render /configuration/screen/entry.jsp", se);
		}
	}

	private final PortalSettingsConfigurationScreenContributor
		_portalSettingsConfigurationScreenContributor;
	private final ServletContext _servletContext;

}