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

package com.liferay.osgi.service.tracker.collections.internal.common;

import org.osgi.framework.ServiceReference;

/**
 * @author Adolfo Pérez
 */
public class ServiceReferenceServiceTuple<S, T>
	implements Comparable<ServiceReferenceServiceTuple<S, T>> {

	public ServiceReferenceServiceTuple(
		ServiceReference<S> serviceReference, T service) {

		_serviceReference = serviceReference;
		_service = service;
	}

	@Override
	public int compareTo(
		ServiceReferenceServiceTuple<S, T> serviceReferenceServiceTuple) {

		return _serviceReference.compareTo(
			serviceReferenceServiceTuple.getServiceReference());
	}

	public T getService() {
		return _service;
	}

	public ServiceReference<S> getServiceReference() {
		return _serviceReference;
	}

	private final T _service;
	private final ServiceReference<S> _serviceReference;

}