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

	public ConcurrentHashMap<ServiceReference, AtomicInteger>
		getReferences() {

		return _references;
	}

	RegistryWrapper(Registry instance) {
		_instance = instance;
	}

	public Filter getFilter(String filterString) throws RuntimeException {
		return _instance.getFilter(filterString);
	}

	public <T> com.liferay.registry.ServiceRegistration<T> registerService(
		Class<T> clazz, T service, Map<String, Object> properties) {

		return _instance.registerService(clazz, service, properties);
	}

	public <S, T> ServiceTracker<S, T> trackServices(
		Class<S> clazz,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		return _instance.trackServices(clazz, serviceTrackerCustomizer);
	}

	public <T> T getService(Class<T> clazz) {
		ServiceReference<T> serviceReference = _instance.getServiceReference(
			clazz);

		return _instance.getService(serviceReference);
	}

	public <T> ServiceReference<T>[] getServiceReferences(
			String className, String filterString)
		throws Exception {

		return _instance.getServiceReferences(className, filterString);
	}

	public <T> boolean ungetService(ServiceReference<T> serviceReference) {
		return _instance.ungetService(serviceReference);
	}

	public <T> Collection<T> getServices(Class<T> clazz, String filterString)
		throws Exception {

		return _instance.getServices(clazz, filterString);
	}

	public <T> T getService(ServiceReference<T> serviceReference) {
		AtomicInteger atomicInteger = _references.get(serviceReference);

		if (atomicInteger == null) {
			atomicInteger = new AtomicInteger(0);

			AtomicInteger integer = _references.putIfAbsent(
				serviceReference, atomicInteger);

			if (integer != null) {
				atomicInteger = integer;
			}
		}

		atomicInteger.incrementAndGet();

		return _instance.getService(serviceReference);
	}

	public <T> T getService(String className) {
		ServiceReference<Object> serviceReference =
			_instance.getServiceReference(className);

		return (T)_instance.getService(serviceReference);
	}

	public <S, T> ServiceTracker<S, T> trackServices(
		String className,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		return _instance.trackServices(className, serviceTrackerCustomizer);
	}

	public Registry setRegistry(Registry registry) throws SecurityException {
		return _instance.setRegistry(registry);
	}

	public <T> ServiceReference<T> getServiceReference(Class<T> clazz) {
		return _instance.getServiceReference(clazz);
	}

	public <S, T> ServiceTracker<S, T> trackServices(
		Filter filter,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		return _instance.trackServices(filter, serviceTrackerCustomizer);
	}

	public <S, T> ServiceTracker<S, T> trackServices(Filter filter) {
		return _instance.trackServices(filter);
	}

	public <T> ServiceReference<T> getServiceReference(String className) {
		return _instance.getServiceReference(className);
	}

	public <T> com.liferay.registry.ServiceRegistration<T> registerService(
		String className, T service, Map<String, Object> properties) {

		return _instance.registerService(className, service, properties);
	}

	public <T> com.liferay.registry.ServiceRegistration<T> registerService(
		String[] classNames, T service) {

		return _instance.registerService(classNames, service);
	}

	public <T> T[] getServices(String className, String filterString)
		throws Exception {

		return _instance.getServices(className, filterString);
	}

	public <T> com.liferay.registry.ServiceRegistration<T> registerService(
		String[] classNames, T service, Map<String, Object> properties) {

		return _instance.registerService(classNames, service, properties);
	}

	public <T> com.liferay.registry.ServiceRegistration<T> registerService(
		String className, T service) {

		return _instance.registerService(className, service);
	}

	public <S, T> ServiceTracker<S, T> trackServices(String className) {
		return _instance.trackServices(className);
	}

	public <T> com.liferay.registry.ServiceRegistration<T> registerService(
		Class<T> clazz, T service) {

		return _instance.registerService(clazz, service);
	}

	public <T> Collection<ServiceReference<T>> getServiceReferences(
			Class<T> clazz, String filterString)
		throws Exception {

		return _instance.getServiceReferences(clazz, filterString);
	}

	public Registry getRegistry() throws SecurityException {
		return this;
	}

	public <S, T> ServiceTracker<S, T> trackServices(Class<S> clazz) {
		return _instance.trackServices(clazz);
	}

	private ConcurrentHashMap<ServiceReference, AtomicInteger> _references =
		new ConcurrentHashMap<ServiceReference, AtomicInteger>();

	private Registry _instance;

}
