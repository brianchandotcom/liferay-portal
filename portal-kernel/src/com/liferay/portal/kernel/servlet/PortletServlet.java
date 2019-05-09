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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletSession;
import com.liferay.portal.kernel.portlet.PortletFilterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.filter.FilterChain;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletServlet extends HttpServlet {

	public static final String PORTLET_APP =
		"com.liferay.portal.kernel.model.PortletApp";

	public static final String PORTLET_SERVLET_CONFIG =
		"com.liferay.portal.kernel.servlet.PortletServletConfig";

	public static final String PORTLET_SERVLET_FILTER_CHAIN =
		"com.liferay.portal.kernel.servlet.PortletServletFilterChain";

	public static final String PORTLET_SERVLET_REQUEST =
		"com.liferay.portal.kernel.servlet.PortletServletRequest";

	public static final String PORTLET_SERVLET_RESPONSE =
		"com.liferay.portal.kernel.servlet.PortletServletResponse";

	@Override
	public void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		if (httpServletRequest.getAttribute(WebKeys.EXTEND_SESSION) != null) {
			httpServletRequest.removeAttribute(WebKeys.EXTEND_SESSION);

			HttpSession session = httpServletRequest.getSession(false);

			if (session != null) {
				session.setAttribute(WebKeys.EXTEND_SESSION, Boolean.TRUE);

				session.removeAttribute(WebKeys.EXTEND_SESSION);
			}

			return;
		}

		String portletId = (String)httpServletRequest.getAttribute(
			WebKeys.PORTLET_ID);

		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		String lifecycle = (String)httpServletRequest.getAttribute(
			PortletRequest.LIFECYCLE_PHASE);

		FilterChain filterChain = (FilterChain)httpServletRequest.getAttribute(
			PORTLET_SERVLET_FILTER_CHAIN);

		LiferayPortletSession portletSession =
			(LiferayPortletSession)portletRequest.getPortletSession();

		portletRequest.setAttribute(PORTLET_SERVLET_CONFIG, getServletConfig());
		portletRequest.setAttribute(
			PORTLET_SERVLET_REQUEST, httpServletRequest);
		portletRequest.setAttribute(
			PORTLET_SERVLET_RESPONSE, httpServletResponse);
		portletRequest.setAttribute(WebKeys.PORTLET_ID, portletId);

		// LPS-66826

		HttpSession session = _getSharedSession(
			httpServletRequest, portletRequest);

		portletSession.setHttpSession(session);

		try {
			PortletFilterUtil.doFilter(
				portletRequest, portletResponse, lifecycle, filterChain);
		}
		catch (PortletException pe) {
			_log.error(pe, pe);

			throw new ServletException(pe);
		}
	}

	private HttpSession _getSharedSession(
		HttpServletRequest httpServletRequest, PortletRequest portletRequest) {

		LiferayPortletRequest liferayPortletRequest =
			PortalUtil.getLiferayPortletRequest(portletRequest);

		Portlet portlet = liferayPortletRequest.getPortlet();

		HttpServletRequest originalHttpServletRequest =
			liferayPortletRequest.getOriginalHttpServletRequest();

		HttpSession portalSession = originalHttpServletRequest.getSession();

		if (!portlet.isPrivateSessionAttributes()) {
			return portalSession;
		}

		return SharedSessionUtil.getSharedSessionWrapper(
			portalSession, httpServletRequest);
	}

	private static final Log _log = LogFactoryUtil.getLog(PortletServlet.class);

}