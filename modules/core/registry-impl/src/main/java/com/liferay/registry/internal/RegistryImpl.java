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
import com.liferay.registry.ServiceRegistrar;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.dependency.ServiceDependencyManager;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleReference;
import org.osgi.framework.InvalidSyntaxException;

/**
 * @author Raymond Augé
 */
public class RegistryImpl implements Registry {

	public RegistryImpl(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	public <S, R> R callService(
		Class<S> serviceClass, Function<S, R> function) {

		org.osgi.framework.ServiceReference<S> serviceReference =
			_bundleContext.getServiceReference(serviceClass);

		if (serviceReference == null) {
			return function.apply(null);
		}

		S service = _bundleContext.getService(serviceReference);

		try {
			return function.apply(service);
		}
		finally {
			_bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public <S, R> R callService(String className, Function<S, R> function) {
		org.osgi.framework.ServiceReference<S> serviceReference =
			(org.osgi.framework.ServiceReference<S>)
				_bundleContext.getServiceReference(className);

		if (serviceReference == null) {
			return function.apply(null);
		}

		S service = _bundleContext.getService(serviceReference);

		try {
			return function.apply(service);
		}
		finally {
			_bundleContext.ungetService(serviceReference);
		}
	}

	public void closeServiceTrackers() {
		Iterator<Reference<org.osgi.util.tracker.ServiceTracker<?, ?>>>
			iterator = _serviceTrackerReferences.iterator();

		while (iterator.hasNext()) {
			Reference<org.osgi.util.tracker.ServiceTracker<?, ?>>
				serviceTrackerReference = iterator.next();

			iterator.remove();

			org.osgi.util.tracker.ServiceTracker<?, ?> serviceTracker =
				serviceTrackerReference.get();

			if (serviceTracker != null) {
				try {
					serviceTracker.close();
				}
				catch (Throwable t) {
				}
			}
		}

		// Drain the reference queue since there are no more service tracker
		// references

		while (_referenceQueue.poll() != null);
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
	public Filter getFilter(String filterString) throws RuntimeException {
		try {
			return new FilterWrapper(_bundleContext.createFilter(filterString));
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(ise);
		}
	}

	@Override
	public Registry getRegistry() throws SecurityException {
		return this;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public <T> T getService(Class<T> clazz) {
		org.osgi.framework.ServiceReference<T> serviceReference =
			_bundleContext.getServiceReference(clazz);

		if (serviceReference == null) {
			return null;
		}

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

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
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
	public Collection<ServiceDependencyManager> getServiceDependencyManagers() {
		return Collections.unmodifiableCollection(_serviceDependencyManagers);
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
	public <T> Collection<ServiceReference<T>> getServiceReferences(
			Class<T> clazz, String filterString)
		throws Exception {

		Collection<org.osgi.framework.ServiceReference<T>>
			osgiServiceReferences = _bundleContext.getServiceReferences(
				clazz, filterString);

		if (osgiServiceReferences.isEmpty()) {
			return Collections.emptyList();
		}

		Collection<ServiceReference<T>> serviceReferences = new ArrayList<>(
			osgiServiceReferences.size());

		Iterator<org.osgi.framework.ServiceReference<T>> iterator =
			osgiServiceReferences.iterator();

		while (iterator.hasNext()) {
			org.osgi.framework.ServiceReference<T> osgiServiceReference =
				iterator.next();

			ServiceReference<T> serviceReference =
				new ServiceReferenceWrapper<>(osgiServiceReference);

			serviceReferences.add(serviceReference);
		}

		return serviceReferences;
	}

	@Override
	public <T> ServiceReference<T>[] getServiceReferences(
			String className, String filterString)
		throws Exception {

		org.osgi.framework.ServiceReference<T>[] osgiServiceReferences =
			(org.osgi.framework.ServiceReference<T>[])
				_bundleContext.getServiceReferences(className, filterString);

		if (osgiServiceReferences == null) {
			return null;
		}

		return _toServiceReferences(osgiServiceReferences);
	}

	@Override
	public <T> ServiceRegistrar<T> getServiceRegistrar(Class<T> clazz) {
		return new ServiceRegistrar<>(this, clazz);
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
	public String getSymbolicName(ClassLoader classLoader) {
		if (classLoader instanceof BundleReference) {
			BundleReference bundleReference = (BundleReference)classLoader;

			Bundle bundle = bundleReference.getBundle();

			return bundle.getSymbolicName();
		}

		return null;
	}

	@Override
	public <T> ServiceRegistration<T> registerService(
		Class<T> clazz, T service) {

		return registerService(clazz, service, null);
	}

	@Override
	public <T> ServiceRegistration<T> registerService(
		Class<T> clazz, T service, Map<String, Object> properties) {

		properties = _addBundleContextProperties(properties);

		org.osgi.framework.ServiceRegistration<T> serviceRegistration =
			_bundleContext.registerService(
				clazz, service, new MapWrapper(properties));

		return new ServiceRegistrationWrapper<>(serviceRegistration);
	}

	@Override
	public <T> ServiceRegistration<T> registerService(
		String className, T service) {

		return registerService(className, service, null);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public <T> ServiceRegistration<T> registerService(
		String className, T service, Map<String, Object> properties) {

		properties = _addBundleContextProperties(properties);

		org.osgi.framework.ServiceRegistration<?> serviceRegistration =
			_bundleContext.registerService(
				className, service, new MapWrapper(properties));

		return new ServiceRegistrationWrapper(serviceRegistration);
	}

	@Override
	public <T> ServiceRegistration<T> registerService(
		String[] classNames, T service) {

		return registerService(classNames, service, null);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public <T> ServiceRegistration<T> registerService(
		String[] classNames, T service, Map<String, Object> properties) {

		properties = _addBundleContextProperties(properties);

		org.osgi.framework.ServiceRegistration<?> serviceRegistration =
			_bundleContext.registerService(
				classNames, service, new MapWrapper(properties));

		return new ServiceRegistrationWrapper(serviceRegistration);
	}

	@Override
	public synchronized void registerServiceDependencyManager(
		ServiceDependencyManager serviceDependencyManager) {

		_serviceDependencyManagers.add(serviceDependencyManager);
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

		addServiceTracker(serviceTracker);

		return new ServiceTrackerWrapper<>(serviceTracker);
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

		addServiceTracker(serviceTracker);

		return new ServiceTrackerWrapper<>(serviceTracker);
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

		addServiceTracker(serviceTracker);

		return new ServiceTrackerWrapper<>(serviceTracker);
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

		addServiceTracker(serviceTracker);

		return new ServiceTrackerWrapper<>(serviceTracker);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(String className) {
		org.osgi.util.tracker.ServiceTracker<S, T> serviceTracker =
			new org.osgi.util.tracker.ServiceTracker<S, T>(
				_bundleContext, className, null);

		addServiceTracker(serviceTracker);

		return new ServiceTrackerWrapper<>(serviceTracker);
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

		addServiceTracker(serviceTracker);

		return new ServiceTrackerWrapper<>(serviceTracker);
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

	@Override
	public void unregisterServiceDependencyManager(
		ServiceDependencyManager serviceDependencyManager) {

		_serviceDependencyManagers.remove(serviceDependencyManager);
	}

	protected void addServiceTracker(
		org.osgi.util.tracker.ServiceTracker<?, ?> serviceTracker) {

		Reference<org.osgi.util.tracker.ServiceTracker<?, ?>> reference =
			new WeakReference<org.osgi.util.tracker.ServiceTracker<?, ?>>(
				serviceTracker, _referenceQueue);

		_serviceTrackerReferences.add(reference);

		while (true) {
			reference =
				(Reference<org.osgi.util.tracker.ServiceTracker<?, ?>>)
					_referenceQueue.poll();

			if (reference == null) {
				return;
			}

			_serviceTrackerReferences.remove(reference);
		}
	}

	private static <T> ServiceReference<T>[] _toServiceReferences(
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

	private Map<String, Object> _addBundleContextProperties(
		Map<String, Object> properties) {

		Bundle bundle = _bundleContext.getBundle();

		if (properties == null) {
			properties = new HashMap<>();
		}

		properties.put("bundle.id", bundle.getBundleId());
		properties.put("bundle.symbolic.name", bundle.getSymbolicName());
		properties.put("bundle.version", bundle.getVersion());

		return properties;
	}

	private final BundleContext _bundleContext;
	private final ReferenceQueue<org.osgi.util.tracker.ServiceTracker<?, ?>>
		_referenceQueue = new ReferenceQueue<>();
	private final Set<ServiceDependencyManager> _serviceDependencyManagers =
		new HashSet<>();
	private final Set<Reference<org.osgi.util.tracker.ServiceTracker<?, ?>>>
		_serviceTrackerReferences = Collections.newSetFromMap(
			new ConcurrentHashMap
				<Reference<org.osgi.util.tracker.ServiceTracker<?, ?>>,
				 Boolean>());

}