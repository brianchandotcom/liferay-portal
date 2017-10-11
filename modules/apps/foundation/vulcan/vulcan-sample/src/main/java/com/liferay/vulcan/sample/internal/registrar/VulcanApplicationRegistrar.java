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

package com.liferay.vulcan.sample.internal.registrar;

import java.util.Hashtable;

import javax.ws.rs.core.Application;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * This component registers the {@code VulcanApplication} from the {@code
 * vulcan-application} module in a OSGi JAX-RS Whiteboard following the standard
 * specifications.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class VulcanApplicationRegistrar {

	@Activate
	public void activate(BundleContext bundleContext) {
		String[] propertyKeys = _serviceReference.getPropertyKeys();

		Hashtable<String, Object> properties = new Hashtable<>();

		for (String key : propertyKeys) {
			Object value = _serviceReference.getProperty(key);

			properties.put(key, value);
		}

		properties.put("osgi.jaxrs.name", ".default");
		properties.put("osgi.jaxrs.application.base", "/");

		Application service = bundleContext.getService(_serviceReference);

		_serviceRegistration = bundleContext.registerService(
			Application.class, service, properties);
	}

	@Deactivate
	public void deactivate(BundleContext bundleContext) {
		bundleContext.ungetService(_serviceReference);
		_serviceRegistration.unregister();
	}

	@Reference(target = "(liferay.vulcan.application=true)")
	private ServiceReference<Application> _serviceReference;

	private ServiceRegistration<Application> _serviceRegistration;

}