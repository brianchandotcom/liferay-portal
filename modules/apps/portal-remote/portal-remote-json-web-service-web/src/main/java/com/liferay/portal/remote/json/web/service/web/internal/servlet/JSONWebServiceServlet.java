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

package com.liferay.portal.remote.json.web.service.web.internal.servlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.remote.json.web.service.web.internal.JSONWebServiceServiceAction;
import com.liferay.portal.servlet.JSONServlet;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.net.URLDecoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Spasic
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.context.path=/portal/api/jsonws",
		"osgi.http.whiteboard.servlet.name=com.liferay.portal.remote.json.web.service.extender.internal.servlet.JSONWebServiceServlet",
		"osgi.http.whiteboard.servlet.pattern=/portal/api/jsonws/*"
	},
	service = Servlet.class
)
public class JSONWebServiceServlet extends JSONServlet {

	@Override
	public void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		if (httpServletRequest instanceof HttpServletRequestWrapper) {
			HttpServletRequestWrapper httpServletRequestWrapper =
				(HttpServletRequestWrapper)httpServletRequest;

			httpServletRequest =
				(HttpServletRequest)httpServletRequestWrapper.getRequest();
		}

		String path = _getPathInfo(httpServletRequest);

		httpServletRequest.setAttribute(WebKeys.ORIGINAL_PATH_INFO, path);

		if (!PropsValues.JSONWS_WEB_SERVICE_API_DISCOVERABLE ||
			(!path.equals(StringPool.BLANK) &&
			 !path.equals(StringPool.SLASH)) ||
			(httpServletRequest.getParameter("discover") != null)) {

			LocaleThreadLocal.setThemeDisplayLocale(
				_portal.getLocale(
					httpServletRequest, httpServletResponse, true));

			super.service(httpServletRequest, httpServletResponse);

			return;
		}

		ServletContext servletContext = ServletContextPool.get(
			StringPool.BLANK);

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(
				Portal.PATH_MAIN + "/portal/api/jsonws");

		requestDispatcher.forward(httpServletRequest, httpServletResponse);
	}

	@Override
	protected JSONAction getJSONAction(ServletContext servletContext) {
		JSONWebServiceServiceAction jsonWebServiceServiceAction =
			new JSONWebServiceServiceAction();

		jsonWebServiceServiceAction.setServletContext(servletContext);

		return jsonWebServiceServiceAction;
	}

	private String _getPathInfo(HttpServletRequest httpServletRequest)
		throws IOException {

		String currentURL = URLDecoder.decode(
			_portal.getCurrentURL(httpServletRequest), StringPool.UTF8);

		Matcher matcher = _pathInfoPattern.matcher(currentURL);

		if (!matcher.find()) {
			throw new IllegalStateException(
				"Unable to extract pathInfo from " + currentURL);
		}

		return matcher.group(1);
	}

	private static final Pattern _pathInfoPattern = Pattern.compile(
		"/api/jsonws([^\\?]*)");

	@Reference
	private Portal _portal;

}