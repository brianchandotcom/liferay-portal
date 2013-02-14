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

import com.liferay.httpservice.internal.definition.FilterDefinition;
import com.liferay.httpservice.internal.definition.ListenerDefinition;
import com.liferay.httpservice.internal.definition.ServletDefinition;
import com.liferay.httpservice.internal.servlet.WebXML;
import com.liferay.httpservice.internal.servlet.WebXMLLoader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class HttpServiceTracker
	extends ServiceTracker<HttpService, HttpService> {

	public HttpServiceTracker(BundleContext bundleContext, Bundle bundle) {
		super(bundleContext, HttpService.class, null);

		_bundleContext = bundleContext;
		_bundle = bundle;
		_webXMLLoader = new WebXMLLoader();
	}

	@Override
	public HttpService addingService(ServiceReference<HttpService> reference) {
		_serviceReference = reference;

		HttpService httpService = _bundleContext.getService(_serviceReference);

		if (httpService == null) {
			return httpService;
		}

		HttpContext httpContext = httpService.createDefaultHttpContext();

		readConfiguration(_bundle);

		initListeners((ExtendedHttpService)httpService, httpContext);
		initServlets(httpService, httpContext);
		initFilters((ExtendedHttpService)httpService, httpContext);

		return httpService;
	}

	@Override
	public void removedService(
		ServiceReference<HttpService> serviceReference,
		HttpService httpService) {

		if (_webXML == null) {
			return;
		}

		destroyFilters((ExtendedHttpService)httpService);
		destroyServlets(httpService);
		destroyListeners((ExtendedHttpService)httpService);

		_webXML = null;
	}

	protected void destroyFilters(ExtendedHttpService httpService) {
		Map<String, FilterDefinition> filters = _webXML.getFilters();

		for (String filterMapping : filters.keySet()) {
			try {
				httpService.unregisterFilter(filterMapping);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	protected void destroyListeners(ExtendedHttpService httpService) {
		List<ListenerDefinition> listeners = _webXML.getListeners();

		for (ListenerDefinition listenerDefinition : listeners) {
			try {
				httpService.unregisterListener(
					listenerDefinition.getListener());
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	protected void destroyServlets(HttpService httpService) {
		Map<String, ServletDefinition> servlets = _webXML.getServlets();

		for (String servletMapping : servlets.keySet()) {
			httpService.unregister(servletMapping);
		}
	}

	protected void initFilters(
		ExtendedHttpService httpService, HttpContext httpContext) {

		Map<String, FilterDefinition> filters = _webXML.getFilters();

		for (String filterMapping : filters.keySet()) {
			FilterDefinition filterDefinition = filters.get(filterMapping);

			try {
				httpService.registerFilter(
					filterMapping, filterDefinition.getFilter(),
					filterDefinition.getInitParams(), httpContext);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	protected void initListeners(
		ExtendedHttpService httpService, HttpContext httpContext) {

		List<ListenerDefinition> listeners = _webXML.getListeners();

		for (ListenerDefinition listenerDefinition : listeners) {
			try {
				httpService.registerListener(
					listenerDefinition.getListener(),
					listenerDefinition.getContextParams(), httpContext);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	protected void initServlets(
		HttpService httpService, HttpContext httpContext) {

		Map<String, ServletDefinition> servlets = _webXML.getServlets();

		for (String servletMapping : servlets.keySet()) {
			ServletDefinition servletDefinition = servlets.get(servletMapping);

			try {
				httpService.registerServlet(
					servletMapping, servletDefinition.getServlet(),
					servletDefinition.getInitParams(), httpContext);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	protected void readConfiguration(Bundle bundle) {
		_webXML = _webXMLLoader.loadWebXML(bundle);
	}

	protected WebXML _webXML;
	protected WebXMLLoader _webXMLLoader;

	private static Log _log = LogFactoryUtil.getLog(HttpServiceTracker.class);

	private Bundle _bundle;
	private BundleContext _bundleContext;
	private ServiceReference<HttpService> _serviceReference;

}