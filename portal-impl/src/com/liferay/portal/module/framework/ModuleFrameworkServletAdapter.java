/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.module.framework;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 * @see    ModuleFrameworkClassLoader
 */
public class ModuleFrameworkServletAdapter extends HttpServlet {

	public HttpServlet addingService(Object serviceReference) {
		return (HttpServlet)_moduleFrameworkAdapterHelper.execute(
			"addingService", serviceReference);
	}

	@Override
	public void destroy() {
		_moduleFrameworkAdapterHelper.execute("destroy");
	}

	@Override
	public void init(ServletConfig servletConfig) {
		_moduleFrameworkAdapterHelper.exec(
			"init", new Class[] {ServletConfig.class}, servletConfig);
	}

	public void modifiedService(
		Object serviceReference, HttpServlet httpService) {

		_moduleFrameworkAdapterHelper.execute(
			"modifiedService", serviceReference, httpService);
	}

	public void removedService(
		Object serviceReference, HttpServlet httpService) {

		_moduleFrameworkAdapterHelper.execute(
			"removedService", serviceReference, httpService);
	}

	@Override
	protected void service(
		HttpServletRequest request, HttpServletResponse response) {

		_moduleFrameworkAdapterHelper.exec(
			"service",
			new Class[] {HttpServletRequest.class, HttpServletResponse.class},
			request, response);
	}

	private static ModuleFrameworkAdapterHelper _moduleFrameworkAdapterHelper =
		new ModuleFrameworkAdapterHelper(
			"com.liferay.osgi.bootstrap.ModuleFrameworkServlet");

}