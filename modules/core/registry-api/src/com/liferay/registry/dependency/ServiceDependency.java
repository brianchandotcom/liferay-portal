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
public class ServiceDependency {

	public ServiceDependency(Class clazz) {
		_serviceDependencyVerifier = new ClassBasedServiceDependencyVerifier(
			clazz);
	}

	public ServiceDependency(Filter filter) {
		_serviceDependencyVerifier = new FilterBasedServiceDependencyVerifier(
			filter);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if ((object == null) || (getClass() != object.getClass())) {
			return false;
		}

		ServiceDependency serviceDependency = (ServiceDependency)object;

		return _serviceDependencyVerifier.equals(
			serviceDependency._serviceDependencyVerifier);
	}

	public boolean fulfilled(ServiceReference<?> serviceReference) {
		_fulfilled = _serviceDependencyVerifier.verify(serviceReference);

		return _fulfilled;
	}

	@Override
	public int hashCode() {
		return _serviceDependencyVerifier.hashCode();
	}

	public boolean isFulfilled() {
		return _fulfilled;
	}

	private boolean _fulfilled;
	private final ServiceDependencyVerifier _serviceDependencyVerifier;

}