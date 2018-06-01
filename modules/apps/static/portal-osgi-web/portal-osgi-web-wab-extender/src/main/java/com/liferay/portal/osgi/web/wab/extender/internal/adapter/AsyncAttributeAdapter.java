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

package com.liferay.portal.osgi.web.wab.extender.internal.adapter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Dante Wang
 */
public class AsyncAttributeAdapter implements Servlet {

	public AsyncAttributeAdapter(Servlet servlet) {
		_servlet = servlet;
	}

	@Override
	public void destroy() {
		_servlet.destroy();
	}

	@Override
	public ServletConfig getServletConfig() {
		return _servlet.getServletConfig();
	}

	@Override
	public String getServletInfo() {
		return _servlet.getServletInfo();
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		_servlet.init(servletConfig);
	}

	@Override
	public void service(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		if (servletRequest.isAsyncSupported() &&
			(servletRequest.getDispatcherType() == DispatcherType.ASYNC) &&
			servletRequest instanceof HttpServletRequest) {

			HttpServletRequest httpServletRequest =
				(HttpServletRequest)servletRequest;

			httpServletRequest.setAttribute(
				"javax.servlet.async.context_path",
				httpServletRequest.getContextPath());
			httpServletRequest.setAttribute(
				"javax.servlet.async.path_info",
				httpServletRequest.getPathInfo());
			httpServletRequest.setAttribute(
				"javax.servlet.async.query_string",
				httpServletRequest.getQueryString());
			httpServletRequest.setAttribute(
				"javax.servlet.async.request_uri",
				httpServletRequest.getRequestURI());
			httpServletRequest.setAttribute(
				"javax.servlet.async.servlet_path",
				httpServletRequest.getServletPath());
		}

		_servlet.service(servletRequest, servletResponse);
	}

	private final Servlet _servlet;

}