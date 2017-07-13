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

package com.liferay.frontend.js.loader.modules.extender.internal;

import aQute.lib.converter.Converter;

import com.liferay.osgi.util.ServiceTrackerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.servlet.ServletContext;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	configurationPid = "com.liferay.frontend.js.loader.modules.extender.internal.Details",
	immediate = true, service = JSLoaderModulesTracker.class
)
public class JSLoaderModulesTracker
	implements ServiceTrackerCustomizer
		<ServletContext, ServiceReference<ServletContext>> {

	@Activate
	@Modified
	public void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		if (_serviceTracker != null) {
			_serviceTracker.close();
		}

		setDetails(Converter.cnv(Details.class, properties));

		_jsLoaderModules.clear();

		_serviceTracker = ServiceTrackerFactory.open(
			componentContext.getBundleContext(),
			"(&(objectClass=" + ServletContext.class.getName() +
				")(osgi.web.contextpath=*))",
			this);
	}

	@Override
	public ServiceReference<ServletContext> addingService(
		ServiceReference<ServletContext> serviceReference) {

		String contextPath = (String)serviceReference.getProperty(
			"osgi.web.contextpath");

		JSLoaderModule jsLoaderModule = new JSLoaderModule(
			_details.applyVersioning(), serviceReference.getBundle(),
			contextPath);

		_jsLoaderModules.put(serviceReference, jsLoaderModule);

		_lastModified = System.currentTimeMillis();

		return serviceReference;
	}

	public Collection<JSLoaderModule> getJSLoaderModules() {
		return _jsLoaderModules.values();
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

		_jsLoaderModules.remove(serviceReference);

		_lastModified = System.currentTimeMillis();
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
	private final Map<ServiceReference<ServletContext>, JSLoaderModule>
		_jsLoaderModules = new ConcurrentSkipListMap<>();
	private volatile long _lastModified = System.currentTimeMillis();
	private ServiceTracker<ServletContext, ServiceReference<ServletContext>>
		_serviceTracker;

}