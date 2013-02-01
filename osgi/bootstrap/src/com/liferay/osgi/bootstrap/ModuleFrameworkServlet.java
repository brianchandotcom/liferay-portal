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

package com.liferay.osgi.bootstrap;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.Framework;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Raymond Augé
 */
public class ModuleFrameworkServlet extends HttpServlet
	implements ServiceTrackerCustomizer<HttpServlet, HttpServlet> {

	public HttpServlet addingService(
		ServiceReference<HttpServlet> serviceReference) {

		_httpServlet = _bundleContext.getService(serviceReference);

		return _httpServlet;
	}

	@Override
	public void destroy() {
		super.destroy();

		_httpServletTracker.close();
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		Framework framework = ModuleFrameworkUtil.getFramework();

		if (framework == null) {
			return;
		}

		_bundleContext = framework.getBundleContext();

		StringBundler sb = new StringBundler(3);

		sb.append("(&(bean.id=");
		sb.append(HttpServlet.class.getName());
		sb.append(")(original.bean=*))");

		try {
			Filter filter = _bundleContext.createFilter(sb.toString());

			_httpServletTracker = new ServiceTracker<HttpServlet, HttpServlet>(
				_bundleContext, filter, this);

			_httpServletTracker.open();
		}
		catch (InvalidSyntaxException e) {
			_log.error(e, e);
		}
	}

	public void modifiedService(
		ServiceReference<HttpServlet> serviceReference,
		HttpServlet httpService) {

		_httpServlet = _bundleContext.getService(serviceReference);
	}

	public void removedService(
		ServiceReference<HttpServlet> serviceReference,
		HttpServlet httpService) {

		_httpServlet = null;

		_bundleContext.ungetService(serviceReference);
	}

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		if (_httpServlet == null) {
			response.sendError(
				HttpServletResponse.SC_SERVICE_UNAVAILABLE,
				"Framework unavailable or not configured");

			return;
		}

		_httpServlet.service(request, response);
	}

	private static Log _log = LogFactoryUtil.getLog(
		ModuleFrameworkServlet.class);

	private BundleContext _bundleContext;
	private HttpServlet _httpServlet;
	private ServiceTracker<HttpServlet, HttpServlet> _httpServletTracker;

}