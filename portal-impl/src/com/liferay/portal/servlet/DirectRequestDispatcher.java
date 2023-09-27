/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.servlet.DynamicServletRequest;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Shuyang Zhou
 */
public class DirectRequestDispatcher implements RequestDispatcher {

	public DirectRequestDispatcher(
		Servlet servlet, String path, String queryString) {

		_servlet = servlet;
		_path = path;
		_queryString = queryString;
	}

	@Override
	public void forward(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		servletRequest = DynamicServletRequest.addQueryString(
			(HttpServletRequest)servletRequest, _queryString);

		_servlet.service(servletRequest, servletResponse);
	}

	@Override
	public void include(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		servletRequest.setAttribute(RequestDispatcher.INCLUDE_PATH_INFO, null);
		servletRequest.setAttribute(
			RequestDispatcher.INCLUDE_SERVLET_PATH, _path);

		servletRequest = DynamicServletRequest.addQueryString(
			(HttpServletRequest)servletRequest, _queryString);

		_servlet.service(servletRequest, servletResponse);
	}

	private final String _path;
	private final String _queryString;
	private final Servlet _servlet;

}