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

import com.liferay.registry.Registry;
import com.liferay.registry.ServiceReference;

import org.osgi.framework.BundleContext;

/**
 * @author Raymond Augé
 */
public class RegistryImpl implements Registry {

	public RegistryImpl(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public <T> ServiceReference<T>[] getAllServiceReferences(
			String className, String filterString)
		throws Exception {

		org.osgi.framework.ServiceReference<T>[] osgiServiceReferences =
			(org.osgi.framework.ServiceReference<T>[])
				_bundleContext.getAllServiceReferences(className, filterString);

		if (osgiServiceReferences == null) {
			return null;
		}

		return _toServiceReferences(osgiServiceReferences);
	}

	@Override
	public <T> T getService(ServiceReference<T> serviceReference) {
		if (!(serviceReference instanceof ServiceReferenceWrapper)) {
			throw new IllegalArgumentException();
		}

		ServiceReferenceWrapper<T> serviceReferenceWrapper =
			(ServiceReferenceWrapper<T>)serviceReference;

		return _bundleContext.getService(
			serviceReferenceWrapper.getServiceReference());
	}

	@Override
	public <T> ServiceReference<T> getServiceReference(Class<T> clazz) {
		org.osgi.framework.ServiceReference<T> serviceReference =
			_bundleContext.getServiceReference(clazz);

		if (serviceReference == null) {
			return null;
		}

		return new ServiceReferenceWrapper<>(serviceReference);
	}

	@Override
	public <T> ServiceReference<T> getServiceReference(String className) {
		org.osgi.framework.ServiceReference<T> serviceReference =
			(org.osgi.framework.ServiceReference<T>)
				_bundleContext.getServiceReference(className);

		if (serviceReference == null) {
			return null;
		}

		return new ServiceReferenceWrapper<>(serviceReference);
	}

	@Override
	public <T> boolean ungetService(ServiceReference<T> serviceReference) {
		if (!(serviceReference instanceof ServiceReferenceWrapper)) {
			throw new IllegalArgumentException();
		}

		ServiceReferenceWrapper<T> serviceReferenceWrapper =
			(ServiceReferenceWrapper<T>)serviceReference;

		return _bundleContext.ungetService(
			serviceReferenceWrapper.getServiceReference());
	}

	private <T> ServiceReference<T>[] _toServiceReferences(
		org.osgi.framework.ServiceReference<T>[] osgiServiceReferences) {

		ServiceReference<T>[] serviceReferences =
			new ServiceReference[osgiServiceReferences.length];

		for (int i = 0; i < osgiServiceReferences.length; i++) {
			org.osgi.framework.ServiceReference<T> osgiServiceReference =
				osgiServiceReferences[i];

			serviceReferences[i] = new ServiceReferenceWrapper<>(
				osgiServiceReference);
		}

		return serviceReferences;
	}

	private final BundleContext _bundleContext;

}