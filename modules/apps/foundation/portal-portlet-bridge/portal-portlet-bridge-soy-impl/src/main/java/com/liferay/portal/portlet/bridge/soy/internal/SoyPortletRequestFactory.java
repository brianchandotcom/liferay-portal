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

package com.liferay.portal.portlet.bridge.soy.internal;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletInstanceFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.ActionRequestFactory;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseFactory;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.RenderRequestFactory;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.RenderResponseFactory;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletResponse;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Bruno Basto
 */
public class SoyPortletRequestFactory {

	public SoyPortletRequestFactory(Portlet portlet) {
		_portlet = portlet;
	}

	public ActionRequestImpl createActionRequest(
			ResourceRequest resourceRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			resourceRequest);

		ServletContext servletContext = (ServletContext)request.getAttribute(
			WebKeys.CTX);

		InvokerPortlet invokerPortlet = PortletInstanceFactoryUtil.create(
			_portlet, servletContext);

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				request, _portlet.getPortletId());

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.getStrictPreferences(
				portletPreferencesIds);

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			_portlet, servletContext);

		PortletContext portletContext = portletConfig.getPortletContext();

		ActionRequestImpl actionRequestImpl = ActionRequestFactory.create(
			request, _portlet, invokerPortlet, portletContext,
			resourceRequest.getWindowState(), resourceRequest.getPortletMode(),
			portletPreferences, themeDisplay.getPlid());

		actionRequestImpl.setPortletRequestDispatcherRequest(request);

		return actionRequestImpl;
	}

	public ActionResponseImpl createActionResponse(
			ActionRequestImpl actionRequestImpl,
			ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)actionRequestImpl.getAttribute(WebKeys.THEME_DISPLAY);

		HttpServletResponse httpServletResponse =
			PortalUtil.getHttpServletResponse(resourceResponse);

		User user = PortalUtil.getUser(actionRequestImpl);

		return ActionResponseFactory.create(
			actionRequestImpl, httpServletResponse, _portlet.getPortletId(),
			user, themeDisplay.getLayout(), actionRequestImpl.getWindowState(),
			actionRequestImpl.getPortletMode());
	}

	public RenderRequestImpl createRenderRequest(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(resourceRequest);

		ServletContext servletContext =
			(ServletContext)httpServletRequest.getAttribute(WebKeys.CTX);

		InvokerPortlet invokerPortlet = PortletInstanceFactoryUtil.create(
			_portlet, servletContext);

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				httpServletRequest, _portlet.getPortletId());

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.getStrictPreferences(
				portletPreferencesIds);

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			_portlet, servletContext);

		PortletContext portletContext = portletConfig.getPortletContext();

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		RenderRequestImpl renderRequestImpl = RenderRequestFactory.create(
			httpServletRequest, _portlet, invokerPortlet, portletContext,
			resourceRequest.getWindowState(), resourceRequest.getPortletMode(),
			portletPreferences, themeDisplay.getPlid());

		renderRequestImpl.setPortletRequestDispatcherRequest(
			httpServletRequest);

		PortletResponse portletResponse = createRenderResponse(
			renderRequestImpl, resourceResponse);

		renderRequestImpl.defineObjects(portletConfig, portletResponse);

		return renderRequestImpl;
	}

	public RenderResponse createRenderResponse(
			RenderRequestImpl renderRequestImpl,
			ResourceResponse resourceResponse)
		throws Exception {

		HttpServletResponse httpServletResponse =
			PortalUtil.getHttpServletResponse(resourceResponse);

		return RenderResponseFactory.create(
			renderRequestImpl, httpServletResponse, _portlet.getPortletId(),
			_portlet.getCompanyId());
	}

	private final Portlet _portlet;

}