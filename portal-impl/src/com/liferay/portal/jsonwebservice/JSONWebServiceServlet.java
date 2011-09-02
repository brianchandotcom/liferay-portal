/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.servlet.JSONServlet;
import com.liferay.portal.servlet.UserResolver;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

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
	public void destroy() {
		_jsonAction.destroy();
		super.destroy();
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		if (PortalUtil.isMultipartRequest(request)) {
			UploadServletRequest uploadServletRequest =
				new UploadServletRequestImpl(request);

			request = uploadServletRequest;
		}

		String path = GetterUtil.getString(request.getPathInfo());

		if (!path.equals(StringPool.SLASH) && !path.equals(StringPool.BLANK)) {
			super.service(request, response);

			return;
		}

		String uri = request.getRequestURI();

		int pos = uri.indexOf("/secure/");

		if (pos != -1) {
			uri = uri.substring(0, pos) + uri.substring(pos + 7);

			String queryString = request.getQueryString();

			if (queryString != null) {
				uri = uri.concat(StringPool.QUESTION).concat(queryString);
			}

			response.sendRedirect(uri);

			return;
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(
			"/jsonws.jsp");

		requestDispatcher.forward(request, response);
	}

	@Override
	protected JSONAction getJSONAction(ServletContext servletContext) {
		ClassLoader portletClassLoader =
			(ClassLoader)servletContext.getAttribute(
				PortletServlet.PORTLET_CLASS_LOADER);

		String portletServletContextName = null;

		PortletApp portletApp = (PortletApp)servletContext.getAttribute(
			PortletServlet.PORTLET_APP);

		if (portletApp != null) {
			portletServletContextName = portletApp.getServletContextName();
		}

		_jsonAction = new JSONWebServiceServiceAction(
			portletClassLoader, portletServletContextName);

		_jsonAction.setServletContext(servletContext);

		return _jsonAction;
	}

	@Override
	protected void resolveRemoteUser(HttpServletRequest request)
		throws Exception {

		UserResolver userResolver = new UserResolver(request);

		CompanyThreadLocal.setCompanyId(userResolver.getCompanyId());

		request.setAttribute("companyId", userResolver.getCompanyId());

		User user = userResolver.getUser();

		if (user != null) {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user, true);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			request.setAttribute("user", user);
			request.setAttribute("userId", user.getUserId());
		}
	}

	private JSONWebServiceServiceAction _jsonAction;

}