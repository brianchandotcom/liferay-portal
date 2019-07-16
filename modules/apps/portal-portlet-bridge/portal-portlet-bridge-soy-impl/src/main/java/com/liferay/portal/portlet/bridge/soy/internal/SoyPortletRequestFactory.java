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
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.LiferayActionRequest;
import com.liferay.portal.kernel.portlet.LiferayActionResponse;
import com.liferay.portal.kernel.portlet.LiferayRenderRequest;
import com.liferay.portal.kernel.portlet.LiferayRenderResponse;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletInstanceFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.ActionRequestFactory;
import com.liferay.portlet.ActionResponseFactory;
import com.liferay.portlet.RenderRequestFactory;
import com.liferay.portlet.RenderResponseFactory;

import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Bruno Basto
 * @author Neil Griffin
 */
public class SoyPortletRequestFactory {

	public SoyPortletRequestFactory(Portlet portlet) {
		_portlet = portlet;
	}

	public LiferayActionRequest createActionRequest(
			ResourceRequest resourceRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

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

		LiferayActionRequest liferayActionRequest = ActionRequestFactory.create(
			httpServletRequest, _portlet, invokerPortlet,
			portletConfig.getPortletContext(), resourceRequest.getWindowState(),
			resourceRequest.getPortletMode(), portletPreferences,
			themeDisplay.getPlid());

		liferayActionRequest.setPortletRequestDispatcherRequest(
			httpServletRequest);

		return liferayActionRequest;
	}

	public LiferayActionResponse createActionResponse(
			ActionRequest actionRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return ActionResponseFactory.create(
			actionRequest, PortalUtil.getHttpServletResponse(resourceResponse),
			PortalUtil.getUser(actionRequest), themeDisplay.getLayout());
	}

	public LiferayRenderRequest createRenderRequest(
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

		LiferayRenderRequest liferayRenderRequest = RenderRequestFactory.create(
			httpServletRequest, _portlet, invokerPortlet, portletContext,
			resourceRequest.getWindowState(), resourceRequest.getPortletMode(),
			portletPreferences, themeDisplay.getPlid());

		liferayRenderRequest.setPortletRequestDispatcherRequest(
			httpServletRequest);

		PortletResponse portletResponse = createRenderResponse(
			liferayRenderRequest, resourceResponse);

		liferayRenderRequest.defineObjects(portletConfig, portletResponse);

		return liferayRenderRequest;
	}

	public LiferayRenderResponse createRenderResponse(
			RenderRequest renderRequest, ResourceResponse resourceResponse)
		throws Exception {

		return RenderResponseFactory.create(
			renderRequest, PortalUtil.getHttpServletResponse(resourceResponse));
	}

	private final Portlet _portlet;

}