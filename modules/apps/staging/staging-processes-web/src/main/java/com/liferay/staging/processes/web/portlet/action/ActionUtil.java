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

package com.liferay.staging.processes.web.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletConfigurationLayoutUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.portletconfiguration.util.ConfigurationActionRequest;
import com.liferay.portlet.portletconfiguration.util.ConfigurationRenderRequest;
import com.liferay.portlet.portletconfiguration.util.ConfigurationResourceRequest;
import com.liferay.portlet.portletconfiguration.util.PortletConfigurationUtil;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Daniel Kocsis
 */
public class ActionUtil {

	public static Group getGroup(HttpServletRequest request) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String cmd = ParamUtil.getString(request, Constants.CMD);

		long groupId = ParamUtil.getLong(request, "groupId");

		Group group = null;

		if (groupId > 0) {
			group = GroupLocalServiceUtil.getGroup(groupId);
		}
		else if (!cmd.equals(Constants.ADD)) {
			group = themeDisplay.getSiteGroup();
		}

		request.setAttribute(WebKeys.GROUP, group);

		return group;
	}

	public static Group getGroup(PortletRequest portletRequest)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return getGroup(request);
	}

	public static PortletPreferences getLayoutPortletSetup(
		PortletRequest portletRequest, Portlet portlet) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		return PortletPreferencesFactoryUtil.getLayoutPortletSetup(
			layout, portlet.getPortletId());
	}

	public static Portlet getPortlet(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		String portletId = ParamUtil.getString(
			portletRequest, "portletResource");

		Layout layout = PortletConfigurationLayoutUtil.getLayout(themeDisplay);

		if (!PortletPermissionUtil.contains(
				permissionChecker, themeDisplay.getScopeGroupId(), layout,
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

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		PortletPreferences portletSetup = getLayoutPortletSetup(
			renderRequest, portlet);

		portletSetup = getPortletSetup(
			request, renderRequest.getPreferences(), portletSetup);

		String title = PortletConfigurationUtil.getPortletTitle(
			portletSetup, themeDisplay.getLanguageId());

		if (Validator.isNull(title)) {
			title = PortalUtil.getPortletTitle(
				portlet, servletContext, themeDisplay.getLocale());
		}

		return title;
	}

	public static ActionRequest getWrappedActionRequest(
			ActionRequest actionRequest, PortletPreferences portletPreferences)
		throws PortalException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		portletPreferences = getPortletPreferences(
			request, actionRequest.getPreferences(), portletPreferences);

		return new ConfigurationActionRequest(
			actionRequest, portletPreferences);
	}

	public static RenderRequest getWrappedRenderRequest(
			RenderRequest renderRequest, PortletPreferences portletPreferences)
		throws PortalException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			renderRequest);

		portletPreferences = getPortletPreferences(
			request, renderRequest.getPreferences(), portletPreferences);

		renderRequest = new ConfigurationRenderRequest(
			renderRequest, portletPreferences);

		request.setAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST, renderRequest);

		return renderRequest;
	}

	public static ResourceRequest getWrappedResourceRequest(
			ResourceRequest resourceRequest,
			PortletPreferences portletPreferences)
		throws PortalException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			resourceRequest);

		portletPreferences = getPortletPreferences(
			request, resourceRequest.getPreferences(), portletPreferences);

		return new ConfigurationResourceRequest(
			resourceRequest, portletPreferences);
	}

	protected static PortletPreferences getPortletPreferences(
			HttpServletRequest request,
			PortletPreferences portletConfigPortletPreferences,
			PortletPreferences portletPreferences)
		throws PortalException {

		String portletResource = ParamUtil.getString(
			request, "portletResource");

		if (Validator.isNull(portletResource)) {
			return portletConfigPortletPreferences;
		}

		if (portletPreferences != null) {
			return portletPreferences;
		}

		return PortletPreferencesFactoryUtil.getPortletPreferences(
			request, portletResource);
	}

	protected static PortletPreferences getPortletSetup(
			HttpServletRequest request,
			PortletPreferences portletConfigPortletSetup,
			PortletPreferences portletSetup)
		throws PortalException {

		String portletResource = ParamUtil.getString(
			request, "portletResource");

		if (Validator.isNull(portletResource)) {
			return portletConfigPortletSetup;
		}

		if (portletSetup != null) {
			return portletSetup;
		}

		return PortletPreferencesFactoryUtil.getPortletSetup(
			request, portletResource);
	}

}