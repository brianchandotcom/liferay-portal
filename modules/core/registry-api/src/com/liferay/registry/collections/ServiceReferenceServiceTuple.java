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

package com.liferay.registry.collections;

import com.liferay.registry.ServiceReference;

/**
* @author Carlos Sierra Andrés
*/
public class ServiceReferenceServiceTuple<S>
	implements Comparable<ServiceReferenceServiceTuple<S>> {

	public ServiceReferenceServiceTuple(
		ServiceReference<S> serviceReference, S service) {

		_serviceReference = serviceReference;
		_service = service;
	}

	@Override
	public int compareTo(ServiceReferenceServiceTuple<S> o) {
		return getServiceReference().compareTo(o.getServiceReference());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof ServiceReferenceServiceTuple)) {
			return false;
		}

		ServiceReferenceServiceTuple serviceReferenceServiceTuple =
			(ServiceReferenceServiceTuple)obj;

		return _serviceReference.equals(
			serviceReferenceServiceTuple.getServiceReference());
	}

	public S getService() {
		return _service;
	}

	public ServiceReference<S> getServiceReference() {
		return _serviceReference;
	}

	@Override
	public int hashCode() {
		return _serviceReference.hashCode();
	}

	private S _service;
	private ServiceReference<S> _serviceReference;

}