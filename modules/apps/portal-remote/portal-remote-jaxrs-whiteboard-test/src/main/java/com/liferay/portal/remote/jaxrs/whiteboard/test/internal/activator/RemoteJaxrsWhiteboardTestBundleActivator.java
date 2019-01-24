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

package com.liferay.portal.remote.jaxrs.whiteboard.test.internal.activator;

import com.liferay.portal.remote.jaxrs.whiteboard.test.internal.service.Addon;
import com.liferay.portal.remote.jaxrs.whiteboard.test.internal.service.Greeter;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.ws.rs.core.Application;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carlos Sierra Andrés
 */
public class RemoteJaxrsWhiteboardTestBundleActivator
	implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		Hashtable<String, Object> properties = new Hashtable<>();

		properties.put("liferay.auth.verifier", false);
		properties.put("liferay.oauth2", false);
		properties.put("osgi.jaxrs.application.base", "/test-rest/greeter1");

		_serviceRegistrations.add(
			context.registerService(
				Application.class, new Greeter(), properties));

		properties.put("liferay.auth.verifier", false);
		properties.put("liferay.oauth2", false);
		properties.put("osgi.jaxrs.application.base", "/test-rest/greeter2");

		_serviceRegistrations.add(
			context.registerService(
				Application.class, new Greeter(), properties));

		properties.put("addonable", Boolean.TRUE);
		properties.put("liferay.auth.verifier", false);
		properties.put("liferay.oauth2", false);
		properties.put("osgi.jaxrs.application.base", "/test-rest/greeter3");

		_serviceRegistrations.add(
			context.registerService(
				Application.class, new Greeter(), properties));

		properties = new Hashtable<>();

		properties.put("osgi.jaxrs.application.select", "(addonable=true)");
		properties.put("osgi.jaxrs.resource", Boolean.TRUE);

		_serviceRegistrations.add(
			context.registerService(Object.class, new Addon(), properties));
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			try {
				serviceRegistration.unregister();
			}
			catch (Exception e) {
				continue;
			}
		}
	}

	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

}