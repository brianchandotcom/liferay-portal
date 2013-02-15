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
public class HttpServiceWrapper implements ExtendedHttpService, HttpService {

	public HttpServiceWrapper(BundleServletContext bundleServletContext) {
		_bundleServletContext = bundleServletContext;
	}

	public HttpContext createDefaultHttpContext() {
		return _bundleServletContext.getHttpContext();
	}

	public void registerFilter(
			String filterMapping, Filter filter,
			Map<String, String> initParameters, HttpContext httpContext)
		throws NamespaceException, ServletException {

		if (httpContext == null) {
			httpContext = createDefaultHttpContext();
		}

		_bundleServletContext.registerFilter(
			filterMapping, filter, initParameters, httpContext);
	}

	@SuppressWarnings({ "unchecked" })
	public void registerListener(
			Object listener, Map<String, String> initParameters,
			HttpContext httpContext)
		throws ServletException {

		if (httpContext == null) {
			httpContext = createDefaultHttpContext();
		}

		_bundleServletContext.registerListener(
			listener, initParameters, httpContext);
	}

	public void registerResources(
			String alias, String name, HttpContext httpContext)
		throws NamespaceException {

		if (httpContext == null) {
			httpContext = createDefaultHttpContext();
		}

		_bundleServletContext.registerResources(alias, name, httpContext);
	}

	@SuppressWarnings({ "rawtypes" })
	public void registerServlet(
			String alias, Servlet servlet, Dictionary initparams,
			HttpContext httpContext)
		throws NamespaceException, ServletException {

		if (httpContext == null) {
			httpContext = createDefaultHttpContext();
		}

		_bundleServletContext.registerServlet(
			alias, servlet, initparams, httpContext);
	}

	public void unregister(String alias) {
		_bundleServletContext.unregister(alias);
	}

	public void unregisterFilter(String filterMapping) {
		_bundleServletContext.unregisterFilter(filterMapping);
	}

	public void unregisterListener(Object listener) {
		_bundleServletContext.unregisterListener(listener);
	}

	private BundleServletContext _bundleServletContext;

}