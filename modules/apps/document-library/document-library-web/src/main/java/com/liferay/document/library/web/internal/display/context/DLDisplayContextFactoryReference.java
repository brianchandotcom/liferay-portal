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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.display.context.DLDisplayContextFactory;

import java.util.Objects;

import org.osgi.framework.ServiceReference;

/**
 * @author Iván Zaera
 */
public class DLDisplayContextFactoryReference
	implements Comparable<DLDisplayContextFactoryReference> {

	public DLDisplayContextFactoryReference(
		DLDisplayContextFactory dlDisplayContextFactory,
		ServiceReference<DLDisplayContextFactory> serviceReference) {

		_dlDisplayContextFactory = dlDisplayContextFactory;
		_serviceReference = serviceReference;
	}

	@Override
	public int compareTo(DLDisplayContextFactoryReference that) {
		return _serviceReference.compareTo(that._serviceReference);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLDisplayContextFactoryReference)) {
			return false;
		}

		DLDisplayContextFactoryReference dlDisplayContextFactoryReference =
			(DLDisplayContextFactoryReference)obj;

		if (Objects.equals(
				_serviceReference,
				dlDisplayContextFactoryReference._serviceReference)) {

			return true;
		}

		return false;
	}

	public DLDisplayContextFactory getDLDisplayContextFactory() {
		return _dlDisplayContextFactory;
	}

	public ServiceReference<DLDisplayContextFactory> getServiceReference() {
		return _serviceReference;
	}

	@Override
	public int hashCode() {
		return _serviceReference.hashCode();
	}

	private final DLDisplayContextFactory _dlDisplayContextFactory;
	private final ServiceReference<DLDisplayContextFactory> _serviceReference;

}