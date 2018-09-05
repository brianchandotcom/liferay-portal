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

package com.liferay.marketplace.app.manager.web.internal.util.comparator;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;

import org.osgi.framework.ServiceReference;

/**
 * @author Douglas Wong
 */
public class ModuleServiceReferenceComparator<T>
	extends PropertyServiceReferenceComparator<T> {

	public ModuleServiceReferenceComparator(
		String propertyKey, String orderByType) {

		super(propertyKey);

		if (!orderByType.equals("asc")) {
			_ascending = false;
		}
		else {
			_ascending = true;
		}
	}

	@Override
	public int compare(
		ServiceReference<T> serviceReference1,
		ServiceReference<T> serviceReference2) {

		int value = super.compare(serviceReference1, serviceReference2);

		if (_ascending) {
			return -value;
		}

		return value;
	}

	private final boolean _ascending;

}