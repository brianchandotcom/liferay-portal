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

package com.liferay.registry.internal;

import com.liferay.registry.Filter;
import com.liferay.registry.ServiceReference;

import java.util.Map;

/**
 * @author Raymond Augé
 */
public class FilterWrapper implements Filter {

	public FilterWrapper(org.osgi.framework.Filter filter) {
		_filter = filter;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FilterWrapper)) {
			throw new IllegalArgumentException();
		}

		FilterWrapper filterWrapper = (FilterWrapper)object;

		return _filter.equals(filterWrapper.getFilter());
	}

	public org.osgi.framework.Filter getFilter() {
		return _filter;
	}

	@Override
	public int hashCode() {
		return _filter.hashCode();
	}

	@Override
	public boolean matches(Map<String, Object> properties) {
		return _filter.match(new MapWrapper(properties));
	}

	@Override
	public boolean matches(ServiceReference<?> serviceReference) {
		if (!(serviceReference instanceof ServiceReferenceWrapper)) {
			throw new IllegalArgumentException();
		}

		ServiceReferenceWrapper<?> serviceReferenceWrapper =
			(ServiceReferenceWrapper<?>)serviceReference;

		return _filter.match(serviceReferenceWrapper.getServiceReference());
	}

	@Override
	public boolean matchesCase(Map<String, Object> properties) {
		return _filter.matchCase(new MapWrapper(properties));
	}

	@Override
	public String toString() {
		return _filter.toString();
	}

	private final org.osgi.framework.Filter _filter;

}