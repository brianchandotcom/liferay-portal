/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.service.tracker.collections;

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

	@Override
	public boolean equals(Object object) {
		if ((object == null) ||
			!(object instanceof ServiceReferenceServiceTuple)) {

			return false;
		}

		ServiceReferenceServiceTuple<S, T> serviceReferenceServiceTuple =
			(ServiceReferenceServiceTuple<S, T>)object;

		return _serviceReference.equals(
			serviceReferenceServiceTuple.getServiceReference());
	}

	public T getService() {
		return _service;
	}

	public ServiceReference<S> getServiceReference() {
		return _serviceReference;
	}

	@Override
	public int hashCode() {
		return _serviceReference.hashCode();
	}

	private final T _service;
	private final ServiceReference<S> _serviceReference;

}