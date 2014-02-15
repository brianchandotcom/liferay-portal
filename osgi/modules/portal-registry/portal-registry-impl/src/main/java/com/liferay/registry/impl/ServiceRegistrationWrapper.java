/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.registry.impl;

import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;

import java.util.Map;

/**
 * @author Raymond Augé
 */
public class ServiceRegistrationWrapper<T> implements ServiceRegistration<T> {

	public ServiceRegistrationWrapper(
		org.osgi.framework.ServiceRegistration<T> serviceRegistration) {

		_serviceRegistration = serviceRegistration;
	}

	@Override
	public ServiceReference<T> getReference() {
		return new ServiceReferenceWrapper<T>(
			_serviceRegistration.getReference());
	}

	public org.osgi.framework.ServiceRegistration<T> getServiceRegistration() {
		return _serviceRegistration;
	}

	@Override
	public void setProperties(Map<String, Object> map) {
		_serviceRegistration.setProperties(new MapWrapper(map));
	}

	@Override
	public void unregister() {
		_serviceRegistration.unregister();
	}

	private org.osgi.framework.ServiceRegistration<T> _serviceRegistration;

}