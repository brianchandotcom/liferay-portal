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

package com.liferay.portal.js.loader.modules.extender;

import aQute.lib.converter.Converter;

import java.net.URL;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
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
	implements
	ServiceTrackerCustomizer<ServletContext, ServiceReference<ServletContext>> {

	@Activate
	public void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		if (_serviceTracker != null) {
			_serviceTracker.close();
		}

		setDetails(Converter.cnv(Details.class, properties));

		Filter filter = FrameworkUtil.createFilter(
			"(&(objectClass=" + ServletContext.class.getName() +
				")(osgi.web.contextpath=*))");

		_serviceTracker = new ServiceTracker<>(
			componentContext.getBundleContext(), filter, this);

		_serviceTracker.open();
	}

	@Override
	public ServiceReference<ServletContext> addingService(
		ServiceReference<ServletContext> serviceReference) {

		Bundle bundle = serviceReference.getBundle();

		Dictionary<String, String> dictionary = bundle.getHeaders();

		String jsConfig = dictionary.get("JS-Config");

		if (jsConfig != null) {
			URL url = bundle.getEntry(jsConfig);

			if (url != null) {
				_jsConfigURLs.put(serviceReference, url);

				return serviceReference;
			}
		}

		return null;
	}

	public Collection<URL> getJSConfigURLs() {
		return _jsConfigURLs.values();
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

		_jsConfigURLs.remove(serviceReference);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_serviceTracker = null;
	}

	protected void setDetails(Details details) {
		_details = details;
	}

	private volatile Details _details;
	private final Map<ServiceReference<ServletContext>, URL>
		_jsConfigURLs = new ConcurrentSkipListMap<>();
	private ServiceTracker<ServletContext, ServiceReference<ServletContext>>
		_serviceTracker;

}