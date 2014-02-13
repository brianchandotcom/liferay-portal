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

/**
 * @author Raymond Augé
 */
public class ServiceReferenceWrapper<T> implements ServiceReference<T> {

	public ServiceReferenceWrapper(
		org.osgi.framework.ServiceReference<T> serviceReference) {

		_serviceReference = serviceReference;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int compareTo(Object reference) {
		if (!(reference instanceof ServiceReferenceWrapper)) {
			throw new IllegalArgumentException();
		}

		ServiceReferenceWrapper<T> serviceReferenceWrapper =
			(ServiceReferenceWrapper<T>)reference;

		return _serviceReference.compareTo(
			serviceReferenceWrapper.getServiceReference());
	}

	@Override
	public Object getProperty(String key) {
		return _serviceReference.getProperty(key);
	}

	@Override
	public String[] getPropertyKeys() {
		return _serviceReference.getPropertyKeys();
	}

	public org.osgi.framework.ServiceReference<T> getServiceReference() {
		return _serviceReference;
	}

	private org.osgi.framework.ServiceReference<T> _serviceReference;

}