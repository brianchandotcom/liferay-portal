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

import java.util.Comparator;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceReferenceServiceTupleComparator<S>
	implements Comparator<ServiceReferenceServiceTuple<S, ?>> {

	public ServiceReferenceServiceTupleComparator(
		Comparator<ServiceReference<S>> comparator) {

		_comparator = comparator;
	}

	@Override
	public int compare(
		ServiceReferenceServiceTuple<S, ?> serviceReferenceServiceTuple1,
		ServiceReferenceServiceTuple<S, ?> serviceReferenceServiceTuple2) {

		if (serviceReferenceServiceTuple1 == null) {
			if (serviceReferenceServiceTuple2 == null) {
				return 0;
			}

			return -1;
		}

		return _comparator.compare(
			serviceReferenceServiceTuple1.getServiceReference(),
			serviceReferenceServiceTuple2.getServiceReference());
	}

	private final Comparator<ServiceReference<S>> _comparator;

}