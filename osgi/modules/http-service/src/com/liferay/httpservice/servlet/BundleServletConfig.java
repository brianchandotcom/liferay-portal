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

package com.liferay.httpservice.servlet;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.osgi.service.http.HttpContext;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class BundleServletConfig implements ServletConfig {

	public BundleServletConfig(
		ServletContext servletContext, String servletName,
		Dictionary<String, String> initParameters, HttpContext httpContext) {

		_servletContext = servletContext;
		_servletName = servletName;
		_initParameters = initParameters;
		_httpContext = httpContext;
	}

	public HttpContext getHttpContext() {
		return _httpContext;
	}

	public String getInitParameter(String name) {
		return getInitParameters().get(name);
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getInitParameterNames() {
		return getInitParameters().keys();
	}

	public Dictionary<String, String> getInitParameters() {
		if (_initParameters == null) {
			_initParameters = new Hashtable<String, String>();
		}

		return _initParameters;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	public String getServletName() {
		return _servletName;
	}

	public void setInitParameter(String paramName, String value) {
		getInitParameters().put(paramName, value);
	}

	private HttpContext _httpContext;
	private Dictionary<String, String> _initParameters;
	private ServletContext _servletContext;
	private String _servletName;

}