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

package com.liferay.change.tracking.internal.definition;

import com.liferay.change.tracking.definition.CTDefinition;
import com.liferay.change.tracking.definition.CTDefinitionRegistrar;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Gergely Mathe
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = CTDefinitionRegistrar.class)
public class CTDefinitionRegistrarImpl implements CTDefinitionRegistrar {

	public void register(CTDefinition<?, ?> ctDefinition) {
		if (ctDefinition == null) {
			return;
		}

		ServiceRegistration<CTDefinition> serviceRegistration =
			_bundleContext.registerService(
				CTDefinition.class, ctDefinition, new Hashtable<>());

		_serviceRegistrations.put(ctDefinition, serviceRegistration);
	}

	@Override
	public void unregister(CTDefinition<?, ?> ctDefinition) {
		if (ctDefinition == null) {
			return;
		}

		ServiceRegistration<CTDefinition> serviceRegistration =
			_serviceRegistrations.remove(ctDefinition);

		serviceRegistration.unregister();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<CTDefinition> serviceRegistration :
				_serviceRegistrations.values()) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();

		_bundleContext = null;
	}

	private BundleContext _bundleContext;
	private final Map<CTDefinition, ServiceRegistration<CTDefinition>>
		_serviceRegistrations = new HashMap<>();

}