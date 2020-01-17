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

package com.liferay.analytics.settings.web.internal.portlet.action;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.web.internal.constants.AnalyticsSettingsWebKeys;
import com.liferay.analytics.settings.web.internal.user.AnalyticsUsersManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Francisco Dias
 */
public abstract class BaseAnalyticsMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(getJspPath());

		try {
			setHttpServletRequestAttributes(
				PortalUtil.getHttpServletRequest(renderRequest));

			requestDispatcher.include(
				PortalUtil.getHttpServletRequest(renderRequest),
				PortalUtil.getHttpServletResponse(renderResponse));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to include JSP " + getJspPath(), exception);
			}

			throw new PortletException(
				"Unable to include JSP " + getJspPath(), exception);
		}

		return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
	}

	protected abstract String getJspPath();

	protected void setHttpServletRequestAttributes(
			HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		httpServletRequest.setAttribute(
			AnalyticsSettingsWebKeys.ANALYTICS_CONFIGURATION,
			configurationProvider.getCompanyConfiguration(
				AnalyticsConfiguration.class, themeDisplay.getCompanyId()));

		httpServletRequest.setAttribute(
			AnalyticsSettingsWebKeys.ANALYTICS_USERS_MANAGER,
			analyticsUsersManager);
	}

	@Reference
	protected AnalyticsUsersManager analyticsUsersManager;

	@Reference
	protected ConfigurationProvider configurationProvider;

	protected volatile ServletContext servletContext;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAnalyticsMVCRenderCommand.class);

}