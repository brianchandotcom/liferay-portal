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

package com.liferay.registry.dependency;

import com.liferay.registry.Filter;
import com.liferay.registry.ServiceReference;

/**
 * @author Michael C. Han
 */
public class FilterServiceDependencyVerifier
	implements ServiceDependencyVerifier {

	public FilterServiceDependencyVerifier(Filter filter) {
		_filter = filter;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if ((object == null) || (getClass() != object.getClass())) {
			return false;
		}

		return _filter.equals(object);
	}

	@Override
	public int hashCode() {
		return _filter.hashCode();
	}

	@Override
	public String toString() {
		return _filter.toString();
	}

	@Override
	public boolean verify(ServiceReference<?> serviceReference) {
		return _filter.matches(serviceReference);
	}

	private final Filter _filter;

}