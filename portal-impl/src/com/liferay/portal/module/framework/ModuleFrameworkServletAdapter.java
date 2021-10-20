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

package com.liferay.portal.module.framework;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.IOException;

import java.util.function.Supplier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 */
public class ModuleFrameworkServletAdapter extends HttpServlet {

	@Override
	protected void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		HttpServlet httpServlet = _supplier.get();

		if (httpServlet == null) {
			PortalUtil.sendError(
				HttpServletResponse.SC_SERVICE_UNAVAILABLE,
				new ServletException("Module framework is unavailable"),
				httpServletRequest, httpServletResponse);

			return;
		}

		httpServlet.service(httpServletRequest, httpServletResponse);
	}

	private static final Supplier<HttpServlet> _supplier;

	static {
		ServiceTracker<HttpServlet, HttpServlet> serviceTracker =
			new ServiceTracker<>(
				SystemBundleUtil.getBundleContext(),
				SystemBundleUtil.createFilter(
					"(&(bean.id=" + HttpServlet.class.getName() +
						")(original.bean=*))"),
				null);

		serviceTracker.open();

		_supplier = serviceTracker::getService;
	}

}