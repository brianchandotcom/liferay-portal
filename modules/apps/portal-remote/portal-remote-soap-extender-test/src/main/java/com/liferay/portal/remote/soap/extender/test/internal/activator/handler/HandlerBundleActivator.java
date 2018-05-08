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

package com.liferay.portal.remote.soap.extender.test.internal.activator.handler;

import com.liferay.portal.remote.soap.extender.test.internal.activator.configuration.GreeterBundleActivator;
import com.liferay.portal.remote.soap.extender.test.internal.handler.SampleHandler;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.xml.ws.handler.Handler;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carlos Sierra Andrés
 */
public class HandlerBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("soap.address", "/greeter");

		_serviceRegistration = bundleContext.registerService(
			Handler.class, new SampleHandler(), properties);

		_greeterBundleActivator = new GreeterBundleActivator();

		try {
			_greeterBundleActivator.start(bundleContext);
		}
		catch (Exception e) {
			cleanUp(bundleContext);

			throw e;
		}
	}

	@Override
	public void stop(BundleContext bundleContext) {
		cleanUp(bundleContext);
	}

	protected void cleanUp(BundleContext bundleContext) {
		try {
			_greeterBundleActivator.stop(bundleContext);
		}
		catch (Exception e) {
		}

		_serviceRegistration.unregister();
	}

	private GreeterBundleActivator _greeterBundleActivator;

	@SuppressWarnings("rawtypes")
	private ServiceRegistration<Handler> _serviceRegistration;

}