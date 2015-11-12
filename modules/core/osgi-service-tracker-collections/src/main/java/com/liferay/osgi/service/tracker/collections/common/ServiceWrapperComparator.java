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

package com.liferay.osgi.service.tracker.collections.common;

import java.util.Comparator;

import org.osgi.framework.ServiceReference;

/**
 * @author Carlos Sierra Andrés
 */
public class ServiceWrapperComparator<S>
	implements Comparator<ServiceWrapper<S, ?>> {

	public ServiceWrapperComparator(
		Comparator<ServiceReference<S>> comparator) {

		_comparator = comparator;
	}

	@Override
	public int compare(
		ServiceWrapper<S, ?> serviceWrapper1,
		ServiceWrapper<S, ?> serviceWrapper2) {

		if (serviceWrapper1 == null) {
			if (serviceWrapper2 == null) {
				return 0;
			}

			return -1;
		}

		ServiceReference<S> serviceReference1 =
			serviceWrapper1.getServiceReference();
		ServiceReference<S> serviceReference2 =
			serviceWrapper2.getServiceReference();

		int value = _comparator.compare(serviceReference1, serviceReference2);

		if (value == 0) {
			return serviceReference1.compareTo(serviceReference2);
		}

		return value;
	}

	private final Comparator<ServiceReference<S>> _comparator;

}