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

/**
 * @author Marcellus Tavares
 */
public abstract class BaseAnalyticsConfigurationScreen
	implements ConfigurationScreen {

	@Override
	public String getCategoryKey() {
		return "analytics-cloud";
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(
			ResourceBundleUtil.getBundle(locale, getClass()), getKey());
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
			ServletContext servletContext = getServletContext();

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(getJspPath());

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (ServletException se) {
			throw new IOException("Unable to render " + getJspPath(), se);
		}
	}

	protected abstract String getJspPath();

	protected abstract ServletContext getServletContext();

}