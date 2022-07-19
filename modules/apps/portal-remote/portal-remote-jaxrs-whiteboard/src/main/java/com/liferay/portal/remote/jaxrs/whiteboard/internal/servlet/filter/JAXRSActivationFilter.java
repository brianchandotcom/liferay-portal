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

package com.liferay.portal.remote.jaxrs.whiteboard.internal.servlet.filter;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Application;

import org.osgi.framework.BundleContext;

/**
 * @author Shuyang Zhou
 */
public class JAXRSActivationFilter implements Filter {

	public JAXRSActivationFilter(
		BundleContext bundleContext,
		JAXRSActivationFilterTracker jaxrsActivationFilterTracker) {

		_bundleContext = bundleContext;
		_jaxrsActivationFilterTracker = jaxrsActivationFilterTracker;
	}

	@Override
	public void destroy() {
		_serviceTrackerMap.close();
	}

	@Override
	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain)
		throws IOException, ServletException {

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)servletRequest;

		String key = httpServletRequest.getPathInfo();

		int index = key.indexOf('/', 1);

		if (index != -1) {
			key = key.substring(0, index);
		}

		if (_serviceTrackerMap.containsKey(key) ||
			_serviceTrackerMap.containsKey("/")) {

			_jaxrsActivationFilterTracker.setReady();
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, Application.class,
			"(osgi.jaxrs.application.base=*)",
			(serviceReference, emitter) -> {
				String applicationBase = GetterUtil.getString(
					serviceReference.getProperty(
						"osgi.jaxrs.application.base"));

				int index = applicationBase.indexOf('/', 1);

				if (index == -1) {
					emitter.emit(applicationBase);
				}
				else {
					emitter.emit(applicationBase.substring(0, index));
				}
			});
	}

	private final BundleContext _bundleContext;
	private final JAXRSActivationFilterTracker _jaxrsActivationFilterTracker;
	private ServiceTrackerMap<String, Application> _serviceTrackerMap;

}