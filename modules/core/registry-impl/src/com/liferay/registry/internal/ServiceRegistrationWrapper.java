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

package com.liferay.registry.internal;

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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ServiceRegistrationWrapper)) {
			return false;
		}

		ServiceRegistrationWrapper<T> serviceReferenceWrapper =
			(ServiceRegistrationWrapper<T>)object;

		return _serviceRegistration.equals(
			serviceReferenceWrapper.getServiceRegistration());
	}

	@Override
	public ServiceReference<T> getServiceReference() {
		return new ServiceReferenceWrapper<>(
			_serviceRegistration.getReference());
	}

	public org.osgi.framework.ServiceRegistration<T> getServiceRegistration() {
		return _serviceRegistration;
	}

	@Override
	public int hashCode() {
		return _serviceRegistration.hashCode();
	}

	@Override
	public void setProperties(Map<String, Object> properties) {
		_serviceRegistration.setProperties(new MapWrapper(properties));
	}

	@Override
	public String toString() {
		return _serviceRegistration.toString();
	}

	@Override
	public void unregister() {
		_serviceRegistration.unregister();
	}

	private final org.osgi.framework.ServiceRegistration<T>
		_serviceRegistration;

}