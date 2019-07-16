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

package com.liferay.staging.configuration.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletConfigurationLayoutUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.configuration.kernel.util.PortletConfigurationUtil;
import com.liferay.portlet.portletconfiguration.util.ConfigurationRenderRequest;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Levente Hudák
 */
public class ActionUtil {

	public static PortletPreferences getLayoutPortletSetup(
		PortletRequest portletRequest, Portlet portlet) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return PortletPreferencesFactoryUtil.getLayoutPortletSetup(
			themeDisplay.getLayout(), portlet.getPortletId());
	}

	public static Portlet getPortlet(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		String portletId = ParamUtil.getString(
			portletRequest, "portletResource");

		if (!PortletPermissionUtil.contains(
				permissionChecker, themeDisplay.getScopeGroupId(),
				PortletConfigurationLayoutUtil.getLayout(themeDisplay),
				portletId, ActionKeys.CONFIGURATION)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, Portlet.class.getName(), portletId,
				ActionKeys.CONFIGURATION);
		}

		return PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), portletId);
	}

	public static String getTitle(Portlet portlet, RenderRequest renderRequest)
		throws Exception {

		ServletContext servletContext =
			(ServletContext)renderRequest.getAttribute(WebKeys.CTX);

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(renderRequest);

		PortletPreferences portletSetup = getLayoutPortletSetup(
			renderRequest, portlet);

		portletSetup = getPortletSetup(
			httpServletRequest, renderRequest.getPreferences(), portletSetup);

		String title = PortletConfigurationUtil.getPortletTitle(
			portletSetup, themeDisplay.getLanguageId());

		if (Validator.isNull(title)) {
			title = PortalUtil.getPortletTitle(
				portlet, servletContext, themeDisplay.getLocale());
		}

		return title;
	}

	public static RenderRequest getWrappedRenderRequest(
			RenderRequest renderRequest, PortletPreferences portletPreferences)
		throws PortalException {

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(renderRequest);

		portletPreferences = getPortletPreferences(
			httpServletRequest, renderRequest.getPreferences(),
			portletPreferences);

		renderRequest = new ConfigurationRenderRequest(
			renderRequest, portletPreferences);

		httpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST, renderRequest);

		return renderRequest;
	}

	protected static PortletPreferences getPortletPreferences(
			HttpServletRequest httpServletRequest,
			PortletPreferences portletConfigPortletPreferences,
			PortletPreferences portletPreferences)
		throws PortalException {

		String portletResource = ParamUtil.getString(
			httpServletRequest, "portletResource");

		if (Validator.isNull(portletResource)) {
			return portletConfigPortletPreferences;
		}

		if (portletPreferences != null) {
			return portletPreferences;
		}

		return PortletPreferencesFactoryUtil.getPortletPreferences(
			httpServletRequest, portletResource);
	}

	protected static PortletPreferences getPortletSetup(
			HttpServletRequest httpServletRequest,
			PortletPreferences portletConfigPortletSetup,
			PortletPreferences portletSetup)
		throws PortalException {

		String portletResource = ParamUtil.getString(
			httpServletRequest, "portletResource");

		if (Validator.isNull(portletResource)) {
			return portletConfigPortletSetup;
		}

		if (portletSetup != null) {
			return portletSetup;
		}

		return PortletPreferencesFactoryUtil.getPortletSetup(
			httpServletRequest, portletResource);
	}

}