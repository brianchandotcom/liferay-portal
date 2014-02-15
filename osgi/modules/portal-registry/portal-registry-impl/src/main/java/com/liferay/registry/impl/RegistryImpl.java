/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.registry.impl;

import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;

/**
 * @author Raymond Augé
 */
public class RegistryImpl implements Registry {

	public RegistryImpl(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public Filter getFilter(String filterString) throws RuntimeException {
		try {
			return new FilterWrapper(_bundleContext.createFilter(filterString));
		}
		catch (org.osgi.framework.InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}
	}

	@Override
	public Registry getRegistry() throws SecurityException {
		return this;
	}

	@Override
	public <T> T getService(Class<T> clazz) {
		org.osgi.framework.ServiceReference<T> serviceReference =
			_bundleContext.getServiceReference(clazz);

		return _bundleContext.getService(serviceReference);
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

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getService(String className) {
		org.osgi.framework.ServiceReference<?> serviceReference =
			_bundleContext.getServiceReference(className);

		if (serviceReference == null) {
			return null;
		}

		return (T)_bundleContext.getService(serviceReference);
	}

	@Override
	public <T> ServiceReference<T> getServiceReference(Class<T> clazz) {
		org.osgi.framework.ServiceReference<T> serviceReference =
			_bundleContext.getServiceReference(clazz);

		if (serviceReference == null) {
			return null;
		}

		return new ServiceReferenceWrapper<T>(serviceReference);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> ServiceReference<T> getServiceReference(String className) {
		org.osgi.framework.ServiceReference<T> serviceReference =
			(org.osgi.framework.ServiceReference<T>)
				_bundleContext.getServiceReference(className);

		if (serviceReference == null) {
			return null;
		}

		return new ServiceReferenceWrapper<T>(serviceReference);
	}

	@Override
	public <T> Collection<ServiceReference<T>> getServiceReferences(
			Class<T> clazz, String filter)
		throws Exception {

		Collection<org.osgi.framework.ServiceReference<T>>
			originalServiceReferences = _bundleContext.getServiceReferences(
				clazz, filter);

		if (originalServiceReferences.isEmpty()) {
			return Collections.emptyList();
		}

		Collection<ServiceReference<T>> newServiceReferences =
			new ArrayList<ServiceReference<T>>(
				originalServiceReferences.size());

		Iterator<org.osgi.framework.ServiceReference<T>> itr =
			originalServiceReferences.iterator();

		while (itr.hasNext()) {
			newServiceReferences.add(
				new ServiceReferenceWrapper<T>(itr.next()));
		}

		return newServiceReferences;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> ServiceReference<T>[] getServiceReferences(
			String className, String filter)
		throws Exception {

		org.osgi.framework.ServiceReference<T>[] originalServiceReferences =
			(org.osgi.framework.ServiceReference<T>[])
				_bundleContext.getServiceReferences(className, filter);

		if (originalServiceReferences == null) {
			return null;
		}

		ServiceReference<T>[] newServiceReferences = new ServiceReference[
			originalServiceReferences.length];

		for (int i = 0; i < originalServiceReferences.length; i++) {
			newServiceReferences[i] = new ServiceReferenceWrapper<T>(
				originalServiceReferences[i]);
		}

		return newServiceReferences;
	}

	@Override
	public <T> Collection<T> getServices(Class<T> clazz, String filter)
		throws Exception {

		Collection<org.osgi.framework.ServiceReference<T>> serviceReferences =
			_bundleContext.getServiceReferences(clazz, filter);

		if (serviceReferences.isEmpty()) {
			return Collections.emptyList();
		}

		List<T> services = new ArrayList<T>();

		Iterator<org.osgi.framework.ServiceReference<T>> itr =
			serviceReferences.iterator();

		while (itr.hasNext()) {
			org.osgi.framework.ServiceReference<T> serviceReference =
				itr.next();

			T service = _bundleContext.getService(serviceReference);

			if (service != null) {
				services.add(service);
			}
		}

		return services;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] getServices(String className, String filter)
		throws Exception {

		org.osgi.framework.ServiceReference<?>[] serviceReferences =
			_bundleContext.getServiceReferences(className, filter);

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
	public <T> ServiceRegistration<T> registerService(
		Class<T> clazz, T service) {

		return registerService(clazz, service, null);
	}

	@Override
	public <T> ServiceRegistration<T> registerService(
		Class<T> clazz, T service, Map<String, Object> map) {

		org.osgi.framework.ServiceRegistration<T> serviceRegistration =
			_bundleContext.registerService(clazz, service, new MapWrapper(map));

		return new ServiceRegistrationWrapper<T>(serviceRegistration);
	}

	@Override
	public <T> ServiceRegistration<T> registerService(
		String className, T service) {

		return registerService(className, service, null);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> ServiceRegistration<T> registerService(
		String className, T service, Map<String, Object> map) {

		org.osgi.framework.ServiceRegistration<?> serviceRegistration =
			_bundleContext.registerService(
				className, service, new MapWrapper(map));

		return new ServiceRegistrationWrapper(serviceRegistration);
	}

	@Override
	public <T> ServiceRegistration<T> registerService(
		String[] classNames, T service) {

		return registerService(classNames, service, null);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> ServiceRegistration<T> registerService(
		String[] classNames, T service, Map<String, Object> map) {

		org.osgi.framework.ServiceRegistration<?> serviceRegistration =
			_bundleContext.registerService(
				classNames, service, new MapWrapper(map));

		return new ServiceRegistrationWrapper(serviceRegistration);
	}

	@Override
	public Registry setRegistry(Registry registry) throws SecurityException {
		return registry;
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(Class<S> clazz) {
		org.osgi.util.tracker.ServiceTracker<S, T> serviceTracker =
			new org.osgi.util.tracker.ServiceTracker<S, T>(
				_bundleContext, clazz, null);

		return new ServiceTrackerWrapper<S, T>(serviceTracker);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(
		Class<S> clazz,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		org.osgi.util.tracker.ServiceTracker<S, T> serviceTracker =
			new org.osgi.util.tracker.ServiceTracker<S, T>(
				_bundleContext, clazz,
				new ServiceTrackerCustomizerAdapter<S, T>(
					serviceTrackerCustomizer));

		return new ServiceTrackerWrapper<S, T>(serviceTracker);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(Filter filter) {
		if (!(filter instanceof FilterWrapper)) {
			throw new IllegalArgumentException();
		}

		FilterWrapper filterWrapper = (FilterWrapper)filter;

		org.osgi.util.tracker.ServiceTracker<S, T> serviceTracker =
			new org.osgi.util.tracker.ServiceTracker<S, T>(
				_bundleContext, filterWrapper.getFilter(), null);

		return new ServiceTrackerWrapper<S, T>(serviceTracker);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(
		Filter filter,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		if (!(filter instanceof FilterWrapper)) {
			throw new IllegalArgumentException();
		}

		FilterWrapper filterWrapper = (FilterWrapper)filter;

		org.osgi.util.tracker.ServiceTracker<S, T> serviceTracker =
			new org.osgi.util.tracker.ServiceTracker<S, T>(
				_bundleContext, filterWrapper.getFilter(),
				new ServiceTrackerCustomizerAdapter<S, T>(
					serviceTrackerCustomizer));

		return new ServiceTrackerWrapper<S, T>(serviceTracker);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(String className) {
		org.osgi.util.tracker.ServiceTracker<S, T> serviceTracker =
			new org.osgi.util.tracker.ServiceTracker<S, T>(
				_bundleContext, className, null);

		return new ServiceTrackerWrapper<S, T>(serviceTracker);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(
		String className,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		org.osgi.util.tracker.ServiceTracker<S, T> serviceTracker =
			new org.osgi.util.tracker.ServiceTracker<S, T>(
				_bundleContext, className,
				new ServiceTrackerCustomizerAdapter<S, T>(
					serviceTrackerCustomizer));

		return new ServiceTrackerWrapper<S, T>(serviceTracker);
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

	private final BundleContext _bundleContext;

}