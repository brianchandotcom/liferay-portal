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

package com.liferay.frontend.js.loader.modules.extender.internal.config.generator;

import com.liferay.frontend.js.loader.modules.extender.internal.configuration.Details;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.net.URL;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
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
@Component(
	configurationPid = "com.liferay.frontend.js.loader.modules.extender.internal.configuration.Details",
	immediate = true, service = JSConfigGeneratorPackagesTracker.class
)
public class JSConfigGeneratorPackagesTracker
	implements ServiceTrackerCustomizer
		<ServletContext, JSConfigGeneratorPackage> {

	@Activate
	public void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		_details = ConfigurableUtil.createConfigurable(
			Details.class, properties);

		_serviceTracker = ServiceTrackerFactory.open(
			componentContext.getBundleContext(),
			"(&(objectClass=" + ServletContext.class.getName() +
				")(osgi.web.contextpath=*))",
			this);
	}

	@Override
	public JSConfigGeneratorPackage addingService(
		ServiceReference<ServletContext> serviceReference) {

		Bundle bundle = serviceReference.getBundle();

		URL url = bundle.getEntry(Details.CONFIG_JSON);

		if (url == null) {
			return null;
		}

		_modifiedCount.incrementAndGet();

		return new JSConfigGeneratorPackage(
			_details.applyVersioning(), serviceReference.getBundle(),
			(String)serviceReference.getProperty("osgi.web.contextpath"));
	}

	public Collection<JSConfigGeneratorPackage> getJSConfigGeneratorPackages() {
		Map<ServiceReference<ServletContext>, JSConfigGeneratorPackage>
			tracked = _serviceTracker.getTracked();

		return tracked.values();
	}

	public long getModifiedCount() {
		return _modifiedCount.get();
	}

	@Override
	public void modifiedService(
		ServiceReference<ServletContext> serviceReference,
		JSConfigGeneratorPackage jsConfigGeneratorPackage) {
	}

	@Override
	public void removedService(
		ServiceReference<ServletContext> serviceReference,
		JSConfigGeneratorPackage jsConfigGeneratorPackage) {

		_modifiedCount.incrementAndGet();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private Details _details;
	private final AtomicLong _modifiedCount = new AtomicLong();
	private ServiceTracker<ServletContext, JSConfigGeneratorPackage>
		_serviceTracker;

}