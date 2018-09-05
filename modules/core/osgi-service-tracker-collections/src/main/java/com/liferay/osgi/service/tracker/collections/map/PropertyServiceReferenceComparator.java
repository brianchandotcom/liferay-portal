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

package com.liferay.osgi.service.tracker.collections.map;

import java.io.Serializable;

import java.util.Comparator;

import org.osgi.framework.ServiceReference;

/**
 * @author Carlos Sierra Andrés
 */
public class PropertyServiceReferenceComparator<T>
	implements Comparator<ServiceReference<T>>, Serializable {

	public PropertyServiceReferenceComparator(String propertyKey) {
		_propertyKey = propertyKey;
	}

	@Override
	public int compare(
		ServiceReference<T> serviceReference1,
		ServiceReference<T> serviceReference2) {

		if (serviceReference1 == null) {
			if (serviceReference2 == null) {
				return 0;
			}

			return 1;
		}
		else if (serviceReference2 == null) {
			return -1;
		}

		Object propertyValue1 = serviceReference1.getProperty(_propertyKey);
		Object propertyValue2 = serviceReference2.getProperty(_propertyKey);

		if (propertyValue1 == null) {
			if (propertyValue2 == null) {
				return 0;
			}

			return 1;
		}
		else if (propertyValue2 == null) {
			return -1;
		}

		if (!(propertyValue2 instanceof Comparable)) {
			return serviceReference2.compareTo(serviceReference1);
		}

		Comparable<Object> propertyValueComparable2 =
			(Comparable<Object>)propertyValue2;

		return propertyValueComparable2.compareTo(propertyValue1);
	}

	private final String _propertyKey;

}