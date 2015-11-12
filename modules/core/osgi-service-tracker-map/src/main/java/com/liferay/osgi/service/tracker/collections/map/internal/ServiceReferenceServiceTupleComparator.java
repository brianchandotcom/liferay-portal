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

package com.liferay.osgi.service.tracker.collections.map.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceServiceTuple;

import java.util.Comparator;

import org.osgi.framework.ServiceReference;

/**
 * @author Carlos Sierra Andrés
 */
class ServiceReferenceServiceTupleComparator<S>
	implements Comparator<ServiceReferenceServiceTuple<S, ?, ?>> {

	public ServiceReferenceServiceTupleComparator(
		Comparator<ServiceReference<S>> comparator) {

		_comparator = comparator;
	}

	@Override
	public int compare(
		ServiceReferenceServiceTuple<S, ?, ?> serviceReferenceServiceTuple1,
		ServiceReferenceServiceTuple<S, ?, ?> serviceReferenceServiceTuple2) {

		if (serviceReferenceServiceTuple1 == null) {
			if (serviceReferenceServiceTuple2 == null) {
				return 0;
			}

			return -1;
		}

		ServiceReference<S> serviceReference1 =
			serviceReferenceServiceTuple1.getServiceReference();
		ServiceReference<S> serviceReference2 =
			serviceReferenceServiceTuple2.getServiceReference();

		int value = _comparator.compare(serviceReference1, serviceReference2);

		if (value == 0) {
			return serviceReference1.compareTo(serviceReference2);
		}

		return value;
	}

	private final Comparator<ServiceReference<S>> _comparator;

}