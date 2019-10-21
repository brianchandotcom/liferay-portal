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

package com.liferay.analytics.settings.web.internal.portal.settings.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = ConfigurationScreen.class)
public class AnalyticsWorkspaceConnectionConfigurationScreen
	implements ConfigurationScreen {

	@Override
	public String getCategoryKey() {
		return "analytics-cloud";
	}

	@Override
	public String getKey() {
		return "workspace-connection";
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(
			ResourceBundleUtil.getBundle(locale, getClass()),
			"workspace-connection");
	}

	@Override
	public String getScope() {
		return "company";
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/edit_workspace_connection.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (ServletException se) {
			throw new IOException(
				"Unable to render /edit_workspace_connection.jsp", se);
		}
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.analytics.settings.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}