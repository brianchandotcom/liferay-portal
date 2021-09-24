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

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
	public <T> Collection<T> getServices(Class<T> clazz, String filterString)
		throws Exception {

		Collection<org.osgi.framework.ServiceReference<T>> serviceReferences =
			_bundleContext.getServiceReferences(clazz, filterString);

		if (serviceReferences.isEmpty()) {
			return Collections.emptyList();
		}

		List<T> services = new ArrayList<>();

		Iterator<org.osgi.framework.ServiceReference<T>> iterator =
			serviceReferences.iterator();

		while (iterator.hasNext()) {
			org.osgi.framework.ServiceReference<T> serviceReference =
				iterator.next();

			T service = _bundleContext.getService(serviceReference);

			if (service != null) {
				services.add(service);
			}
		}

		return services;
	}

	@Override
	public <T> T[] getServices(String className, String filterString)
		throws Exception {

		org.osgi.framework.ServiceReference<?>[] serviceReferences =
			_bundleContext.getServiceReferences(className, filterString);

		if (serviceReferences == null) {
			return null;
		}

		Object service = _bundleContext.getService(serviceReferences[0]);

		T[] services = (T[])Array.newInstance(
			service.getClass(), serviceReferences.length);

		services[0] = (T)service;

		for (int i = 1; i < serviceReferences.length; i++) {
			org.osgi.framework.ServiceReference<?> serviceReference =
				serviceReferences[i];

			service = _bundleContext.getService(serviceReference);

			if (service != null) {
				services[i] = (T)service;
			}
		}

		return services;
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