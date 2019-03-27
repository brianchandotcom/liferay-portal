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
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.settings.portlet.action.PortalSettingsConfigurationScreenContributor;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Drew Brokke
 */
public class PortalSettingsConfigurationScreen implements ConfigurationScreen {

	public PortalSettingsConfigurationScreen(
		PortalSettingsConfigurationScreenContributor
			portalSettingsConfigurationScreenContributor,
		JSPRenderer jspRenderer, ServletContext servletContext) {

		_portalSettingsConfigurationScreenContributor =
			portalSettingsConfigurationScreenContributor;
		_jspRenderer = jspRenderer;
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
	public void render(HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		request.setAttribute(
			"deleteConfirmationText",
			UnicodeLanguageUtil.get(
				ResourceBundleUtil.getBundle(
					request.getLocale(),
					PortalSettingsConfigurationScreen.class),
				"are-you-sure-you-want-to-reset-the-configured-values"));
		request.setAttribute(
			"portalSettingsConfigurationScreenContributor",
			_portalSettingsConfigurationScreenContributor);

		_portalSettingsConfigurationScreenContributor.setAttributes(
			request, response);

		_jspRenderer.renderJSP(
			_servletContext, request, response,
			"/configuration/screen/entry.jsp");
	}

	private final JSPRenderer _jspRenderer;
	private final PortalSettingsConfigurationScreenContributor
		_portalSettingsConfigurationScreenContributor;
	private final ServletContext _servletContext;

}