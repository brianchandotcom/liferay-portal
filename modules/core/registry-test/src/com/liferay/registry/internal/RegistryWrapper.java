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
import com.liferay.registry.Registry;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Carlos Sierra Andrés
 */
public class RegistryWrapper implements Registry {

	public RegistryWrapper(Registry registry) {
		_registry = registry;
	}

	@Override
	public Filter getFilter(String filterString) throws RuntimeException {
		return _registry.getFilter(filterString);
	}

	@Override
	public Registry getRegistry() throws SecurityException {
		return this;
	}

	@Override
	public <T> T getService(Class<T> clazz) {
		ServiceReference<T> serviceReference = _registry.getServiceReference(
			clazz);

		return _registry.getService(serviceReference);
	}

	@Override
	public <T> T getService(ServiceReference<T> serviceReference) {
		AtomicInteger serviceReferenceCount = _serviceReferenceCountsMap.get(
			serviceReference);

		if (serviceReferenceCount == null) {
			serviceReferenceCount = new AtomicInteger(0);

			AtomicInteger previousServiceReferenceCount =
				_serviceReferenceCountsMap.putIfAbsent(
					serviceReference, serviceReferenceCount);

			if (previousServiceReferenceCount != null) {
				serviceReferenceCount = previousServiceReferenceCount;
			}
		}

		serviceReferenceCount.incrementAndGet();

		return _registry.getService(serviceReference);
	}

	@Override
	public <T> T getService(String className) {
		ServiceReference<Object> serviceReference =
			_registry.getServiceReference(className);

		return (T)_registry.getService(serviceReference);
	}

	@Override
	public <T> ServiceReference<T> getServiceReference(Class<T> clazz) {
		return _registry.getServiceReference(clazz);
	}

	@Override
	public <T> ServiceReference<T> getServiceReference(String className) {
		return _registry.getServiceReference(className);
	}

	public Map<ServiceReference<?>, AtomicInteger>
		getServiceReferenceCountsMap() {

		return _serviceReferenceCountsMap;
	}

	@Override
	public <T> Collection<ServiceReference<T>> getServiceReferences(
			Class<T> clazz, String filterString)
		throws Exception {

		return _registry.getServiceReferences(clazz, filterString);
	}

	@Override
	public <T> ServiceReference<T>[] getServiceReferences(
			String className, String filterString)
		throws Exception {

		return _registry.getServiceReferences(className, filterString);
	}

	@Override
	public <T> Collection<T> getServices(Class<T> clazz, String filterString)
		throws Exception {

		return _registry.getServices(clazz, filterString);
	}

	@Override
	public <T> T[] getServices(String className, String filterString)
		throws Exception {

		return _registry.getServices(className, filterString);
	}

	public Registry getWrappedRegistry() {
		return _registry;
	}

	@Override
	public <T> com.liferay.registry.ServiceRegistration<T> registerService(
		Class<T> clazz, T service) {

		return _registry.registerService(clazz, service);
	}

	@Override
	public <T> com.liferay.registry.ServiceRegistration<T> registerService(
		Class<T> clazz, T service, Map<String, Object> properties) {

		return _registry.registerService(clazz, service, properties);
	}

	@Override
	public <T> com.liferay.registry.ServiceRegistration<T> registerService(
		String className, T service) {

		return _registry.registerService(className, service);
	}

	@Override
	public <T> com.liferay.registry.ServiceRegistration<T> registerService(
		String className, T service, Map<String, Object> properties) {

		return _registry.registerService(className, service, properties);
	}

	@Override
	public <T> com.liferay.registry.ServiceRegistration<T> registerService(
		String[] classNames, T service) {

		return _registry.registerService(classNames, service);
	}

	@Override
	public <T> com.liferay.registry.ServiceRegistration<T> registerService(
		String[] classNames, T service, Map<String, Object> properties) {

		return _registry.registerService(classNames, service, properties);
	}

	@Override
	public Registry setRegistry(Registry registry) throws SecurityException {
		return _registry.setRegistry(registry);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(Class<S> clazz) {
		return _registry.trackServices(clazz);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(
		Class<S> clazz,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		return _registry.trackServices(clazz, serviceTrackerCustomizer);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(Filter filter) {
		return _registry.trackServices(filter);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(
		Filter filter,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		return _registry.trackServices(filter, serviceTrackerCustomizer);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(String className) {
		return _registry.trackServices(className);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(
		String className,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		return _registry.trackServices(className, serviceTrackerCustomizer);
	}

	@Override
	public <T> boolean ungetService(ServiceReference<T> serviceReference) {
		AtomicInteger serviceReferenceCount = _serviceReferenceCountsMap.get(
			serviceReference);

		if (serviceReferenceCount != null) {
			serviceReferenceCount.decrementAndGet();
		}

		return _registry.ungetService(serviceReference);
	}

	private final Registry _registry;
	private final ConcurrentHashMap<ServiceReference<?>, AtomicInteger>
		_serviceReferenceCountsMap =
			new ConcurrentHashMap<ServiceReference<?>, AtomicInteger>();

}