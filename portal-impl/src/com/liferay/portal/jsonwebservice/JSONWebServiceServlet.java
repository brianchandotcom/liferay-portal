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

package com.liferay.portal.jsonwebservice;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.servlet.JSONServlet;
import com.liferay.portal.spring.context.PortalContextLoaderListener;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceServlet extends JSONServlet {

	@Override
	public void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		String path = GetterUtil.getString(httpServletRequest.getPathInfo());

		if (!PropsValues.JSONWS_WEB_SERVICE_API_DISCOVERABLE ||
			(!path.equals(StringPool.BLANK) &&
			 !path.equals(StringPool.SLASH)) ||
			(httpServletRequest.getParameter("discover") != null)) {

			Locale locale = PortalUtil.getLocale(
				httpServletRequest, httpServletResponse, true);

			LocaleThreadLocal.setThemeDisplayLocale(locale);

			super.service(httpServletRequest, httpServletResponse);

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Servlet context " + httpServletRequest.getContextPath());
		}

		String portalContextPath =
			PortalContextLoaderListener.getPortalServletContextPath();

		String requestContextPath = httpServletRequest.getContextPath();

		if (requestContextPath.equals(portalContextPath)) {
			RequestDispatcher requestDispatcher =
				httpServletRequest.getRequestDispatcher(
					Portal.PATH_MAIN + "/portal/api/jsonws");

			requestDispatcher.forward(httpServletRequest, httpServletResponse);
		}
		else {
			ServletContext servletContext = getServletContext();

			String redirectPath =
				PortalUtil.getPathContext() + "/api/jsonws?contextName=" +
					URLCodec.encodeURL(servletContext.getServletContextName());

			httpServletResponse.sendRedirect(redirectPath);
		}
	}

	@Override
	protected JSONAction getJSONAction(ServletContext servletContext) {
		JSONWebServiceServiceAction jsonWebServiceServiceAction =
			new JSONWebServiceServiceAction();

		jsonWebServiceServiceAction.setServletContext(servletContext);

		return jsonWebServiceServiceAction;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSONWebServiceServlet.class);

}