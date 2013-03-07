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

package com.liferay.httpservice.internal.http;

import com.liferay.httpservice.internal.servlet.BundleServletContext;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class NonWABHttpServiceWrapper
	implements ExtendedHttpService, HttpService {

	public NonWABHttpServiceWrapper(BundleServletContext bundleServletContext) {
	}

	public HttpContext createDefaultHttpContext() {
		return null;
	}

	public void registerFilter(
		String urlPattern, Filter filter, Map<String, String> initParameters,
		HttpContext httpContext) {
	}

	public void registerListener(
		Object listener, Map<String, String> initParameters,
		HttpContext httpContext) {
	}

	public void registerResources(
		String alias, String name, HttpContext httpContext) {
	}

	public void registerServlet(
			String servletName, List<String> aliases, Servlet servlet,
			@SuppressWarnings("rawtypes") Dictionary initParameters,
			HttpContext context)
		throws NamespaceException, ServletException {
	}

	public void registerServlet(
		String alias, Servlet servlet,
		@SuppressWarnings("rawtypes") Dictionary initParameters,
		HttpContext httpContext) {
	}

	public void unregister(String alias) {
	}

	public void unregisterFilter(String name) {
	}

	public void unregisterListener(Object listener) {
	}

}