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

import java.util.Dictionary;

import javax.servlet.Filter;
import javax.servlet.ServletException;

import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public interface ExtendedHttpService extends HttpService {

	public void registerFilter(
			String filterMapping, Filter filter,
			Dictionary<String, String> initParams, HttpContext httpContext)
		throws NamespaceException, ServletException;

	public void registerListener(
			Object listener, Dictionary<String, String> initParams,
			HttpContext httpContext)
		throws ServletException;

	public void unregisterFilter(String filterMapping);

	public void unregisterListener(Object listener);

}