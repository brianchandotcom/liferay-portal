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

package com.liferay.portal.kernel.display.context;

import com.liferay.registry.ServiceReference;

import java.util.Objects;

/**
 * @author Iván Zaera
 */
public class DisplayContextFactoryReference<T extends DisplayContextFactory>
	implements Comparable<DisplayContextFactoryReference<?>> {

	public DisplayContextFactoryReference(
		T displayContextFactory, ServiceReference<T> serviceReference) {

		_displayContextFactory = displayContextFactory;
		_serviceReference = serviceReference;
	}

	@Override
	public int compareTo(
		DisplayContextFactoryReference<?> displayContextFactoryReference) {

		return _serviceReference.compareTo(
			displayContextFactoryReference._serviceReference);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DisplayContextFactoryReference)) {
			return false;
		}

		DisplayContextFactoryReference<?> displayContextFactoryReference =
			(DisplayContextFactoryReference<?>)obj;

		if (Objects.equals(
				_serviceReference,
				displayContextFactoryReference._serviceReference)) {

			return true;
		}

		return false;
	}

	public T getDisplayContextFactory() {
		return _displayContextFactory;
	}

	public ServiceReference<T> getServiceReference() {
		return _serviceReference;
	}

	@Override
	public int hashCode() {
		return _serviceReference.hashCode();
	}

	private final T _displayContextFactory;
	private final ServiceReference<T> _serviceReference;

}