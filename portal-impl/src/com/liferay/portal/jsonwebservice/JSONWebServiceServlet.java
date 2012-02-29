/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.ContextPathUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
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
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceServlet extends JSONServlet {

	@Override
	public void destroy() {
		_jsonWebServiceServiceAction.destroy();

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

		String apiPath = PortalUtil.getPathMain() + "/portal/api/jsonws";

		HttpSession session = request.getSession();

		ServletContext servletContext = session.getServletContext();

		if (servletContext.getContext(PropsValues.PORTAL_CTX) != null) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(
				apiPath);

			requestDispatcher.forward(request, response);
		}
		else {
			String requestURI = request.getRequestURI();
			String requestURL = String.valueOf(request.getRequestURL());

			String serverURL = requestURL.substring(
				0, requestURL.length() - requestURI.length());

			String queryString = request.getQueryString();

			if (Validator.isNull(queryString)) {
				queryString = StringPool.BLANK;
			}
			else {
				queryString += '&';
			}
			String servletContextPath = ContextPathUtil.getContextPath(
					servletContext);

			queryString +=
					"contextPath=" + HttpUtil.encodeURL(servletContextPath);

			apiPath = serverURL + apiPath + StringPool.QUESTION + queryString;

			URL url = new URL(apiPath);

			InputStream inputStream = null;

			try {
				inputStream = url.openStream();

				OutputStream outputStream = response.getOutputStream();

				StreamUtil.transfer(inputStream, outputStream);
			}
			finally {
				StreamUtil.cleanUp(inputStream);
			}
		}
	}

	@Override
	protected JSONAction getJSONAction(ServletContext servletContext) {
		ClassLoader portletClassLoader =
			(ClassLoader)servletContext.getAttribute(
				PortletServlet.PORTLET_CLASS_LOADER);

		_jsonWebServiceServiceAction = new JSONWebServiceServiceAction(
			ContextPathUtil.getContextPath(servletContext), portletClassLoader);

		_jsonWebServiceServiceAction.setServletContext(servletContext);

		return _jsonWebServiceServiceAction;
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
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			request.setAttribute("user", user);
			request.setAttribute("userId", user.getUserId());
		}
	}

	private JSONWebServiceServiceAction _jsonWebServiceServiceAction;

}