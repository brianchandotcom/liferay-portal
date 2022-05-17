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

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.web.internal.constants.AnalyticsSettingsWebKeys;
import com.liferay.analytics.settings.web.internal.display.context.DisplayContext;
import com.liferay.analytics.settings.web.internal.user.AnalyticsUsersManager;
import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;

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
		return LanguageUtil.get(locale, getKey());
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

			_setHttpServletRequestAttributes(
				httpServletRequest, httpServletResponse);

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new IOException(
				"Unable to render " + getJspPath(), exception);
		}
	}

	protected abstract String getDefaultJspPath();

	protected abstract DisplayContext getDisplayContext(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException;

	protected String getJspPath() {
		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LRAC-10757"))) {
			return "/LRAC-10757" + getDefaultJspPath();
		}

		return getDefaultJspPath();
	}

	protected abstract ServletContext getServletContext();

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.analytics.settings.web)(release.schema.version>=1.0.1))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	@Reference
	protected AnalyticsUsersManager analyticsUsersManager;

	@Reference
	protected ConfigurationProvider configurationProvider;

	private void _setHttpServletRequestAttributes(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		httpServletRequest.setAttribute(
			AnalyticsSettingsWebKeys.ANALYTICS_CONFIGURATION,
			configurationProvider.getCompanyConfiguration(
				AnalyticsConfiguration.class, themeDisplay.getCompanyId()));

		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LRAC-10757"))) {
			DisplayContext displayContext = getDisplayContext(
				httpServletRequest, httpServletResponse);

			if (displayContext != null) {
				httpServletRequest.setAttribute(
					AnalyticsSettingsWebKeys.ANALYTICS_DISPLAY_CONTEXT,
					getDisplayContext(httpServletRequest, httpServletResponse));
			}
		}

		httpServletRequest.setAttribute(
			AnalyticsSettingsWebKeys.ANALYTICS_USERS_MANAGER,
			analyticsUsersManager);
	}

}