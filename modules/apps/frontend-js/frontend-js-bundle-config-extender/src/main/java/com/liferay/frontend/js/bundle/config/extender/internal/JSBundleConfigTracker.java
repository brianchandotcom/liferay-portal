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

package com.liferay.frontend.js.bundle.config.extender.internal;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.StringPool;

import java.net.URL;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, service = JSBundleConfigTracker.class)
public class JSBundleConfigTracker
	implements ServiceTrackerCustomizer
		<ServletContext, ServiceReference<ServletContext>> {

	@Activate
	protected void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		if (_serviceTracker != null) {
			_serviceTracker.close();
		}

		_bundleContext = componentContext.getBundleContext();

		_serviceTracker = ServiceTrackerFactory.open(
			_bundleContext,
			"(&(objectClass=" + ServletContext.class.getName() +
				")(osgi.web.contextpath=*))",
			this);
	}

	@Override
	public ServiceReference<ServletContext> addingService(
		ServiceReference<ServletContext> serviceReference) {

		Bundle bundle = serviceReference.getBundle();

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String jsConfig = headers.get("Liferay-JS-Config");

		if (jsConfig != null) {
			URL url = bundle.getEntry(jsConfig);

			if (url != null) {
				ServletContext servletContext = _bundleContext.getService(
					serviceReference);

				_jsConfigs.put(
					serviceReference, new JSConfig(servletContext, url));

				_lastModified = System.currentTimeMillis();

				return serviceReference;
			}
		}

		return null;
	}

	public Collection<JSConfig> getJSConfigs() {
		return _jsConfigs.values();
	}

	public long getLastModified() {
		return _lastModified;
	}

	public long getTrackingCount() {
		return _serviceTracker.getTrackingCount();
	}

	@Override
	public void modifiedService(
		ServiceReference<ServletContext> serviceReference,
		ServiceReference<ServletContext> trackedServiceReference) {

		removedService(serviceReference, trackedServiceReference);

		addingService(serviceReference);
	}

	@Override
	public void removedService(
		ServiceReference<ServletContext> serviceReference,
		ServiceReference<ServletContext> trackedServiceReference) {

		JSConfig jsConfig = _jsConfigs.remove(serviceReference);

		if (jsConfig != null) {
			_bundleContext.ungetService(serviceReference);

			_lastModified = System.currentTimeMillis();
		}
	}

	public static class JSConfig {

		public ServletContext getServletContext() {
			return _servletContext;
		}

		public URL getURL() {
			return _url;
		}

		private JSConfig(ServletContext servletContext, URL url) {
			_servletContext = servletContext;
			_url = url;
		}

		private final ServletContext _servletContext;
		private final URL _url;

	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_serviceTracker = null;
	}

	private BundleContext _bundleContext;
	private final Map<ServiceReference<ServletContext>, JSConfig> _jsConfigs =
		new ConcurrentSkipListMap<>();
	private volatile long _lastModified = System.currentTimeMillis();
	private ServiceTracker<ServletContext, ServiceReference<ServletContext>>
		_serviceTracker;

}