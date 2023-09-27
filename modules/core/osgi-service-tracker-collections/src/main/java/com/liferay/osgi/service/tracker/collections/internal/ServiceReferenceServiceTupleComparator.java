/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.service.tracker.collections.internal;

import com.liferay.osgi.service.tracker.collections.ServiceReferenceServiceTuple;

import java.util.Comparator;

import org.osgi.framework.ServiceReference;

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