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

package com.liferay.vulcan.wiring.osgi.manager;

import com.liferay.vulcan.wiring.osgi.util.GenericUtil;

import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Use descendants of this class to manage services that have a generic type.
 *
 * @author Alejandro Hernández
 */
public abstract class BaseManager<T> {

	public BaseManager() {
		Bundle bundle = FrameworkUtil.getBundle(BaseManager.class);

		_bundleContext = bundle.getBundleContext();
	}

	/**
	 * Adds a new serviceReference/service tuple to the internal map.
	 *
	 * @param  serviceReference a service reference.
	 * @param  clazz class of the service reference service.
	 * @return the generic inner class of the service reference service.
	 */
	protected <U> Class<U> addService(
		ServiceReference<T> serviceReference, Class<T> clazz) {

		T service = _bundleContext.getService(serviceReference);

		Class<U> genericClass = GenericUtil.getGenericClass(service, clazz);

		_services.computeIfAbsent(
			genericClass.getName(), name -> new TreeSet<>());

		TreeSet<ServiceReferenceServiceTuple<T>> serviceReferenceServiceTuples =
			_services.get(genericClass.getName());

		ServiceReferenceServiceTuple<T> serviceReferenceServiceTuple =
			new ServiceReferenceServiceTuple<>(serviceReference, service);

		serviceReferenceServiceTuples.add(serviceReferenceServiceTuple);

		return genericClass;
	}

	/**
	 * Retrieves a service from the inner map based on its generic inner class.
	 * Returns <code>Optional#empty()</code> if no service is found.
	 *
	 * @param  clazz the class.
	 * @return the service, if present; <code>Optional#empty()</code> otherwise.
	 */
	protected <U> Optional<T> getServiceOptional(Class<U> clazz) {
		return getServiceOptional(clazz.getName());
	}

	/**
	 * Retrieves a service from the inner map based on its generic inner class
	 * name. Returns <code>Optional#empty()</code> if no service is found.
	 *
	 * @param  className the class name.
	 * @return the service, if present; <code>Optional#empty()</code> otherwise.
	 */
	protected Optional<T> getServiceOptional(String className) {
		TreeSet<ServiceReferenceServiceTuple<T>> serviceReferenceServiceTuples =
			_services.get(className);

		Optional<TreeSet<ServiceReferenceServiceTuple<T>>> optional =
			Optional.ofNullable(serviceReferenceServiceTuples);

		return optional.filter(
			treeSet -> !treeSet.isEmpty()
		).map(
			TreeSet::first
		).map(
			ServiceReferenceServiceTuple::getService
		);
	}

	/**
	 * Removes a serviceReference/service tuple to the internal map.
	 *
	 * @param  serviceReference a service reference.
	 * @param  clazz class of the service reference service.
	 * @return the generic inner class of the service reference service.
	 */
	protected <U> Class<U> removeService(
		ServiceReference<T> serviceReference, Class<T> clazz) {

		Consumer<T> identityConsumer = t -> {
		};

		return removeService(serviceReference, clazz, identityConsumer);
	}

	/**
	 * Removes a serviceReference/service tuple to the internal map.
	 *
	 * @param  serviceReference a service reference.
	 * @param  clazz class of the service reference service.
	 * @param  beforeRemovingConsumer consumer that will be called before
	 *         removing the service.
	 * @return the generic inner class of the service reference service.
	 */
	protected <U> Class<U> removeService(
		ServiceReference<T> serviceReference, Class<T> clazz,
		Consumer<T> beforeRemovingConsumer) {

		T service = _bundleContext.getService(serviceReference);

		Class<U> genericClass = GenericUtil.getGenericClass(service, clazz);

		TreeSet<ServiceReferenceServiceTuple<T>> serviceReferenceServiceTuples =
			_services.get(genericClass.getName());

		beforeRemovingConsumer.accept(service);

		serviceReferenceServiceTuples.removeIf(
			serviceReferenceServiceTuple -> {
				if (serviceReferenceServiceTuple.getService() == service) {
					return true;
				}

				return false;
			});

		return genericClass;
	}

	private final BundleContext _bundleContext;
	private final Map<String, TreeSet<ServiceReferenceServiceTuple<T>>>
		_services = new ConcurrentHashMap<>();

	private static class ServiceReferenceServiceTuple<T>
		implements Comparable<ServiceReferenceServiceTuple> {

		public ServiceReferenceServiceTuple(
			ServiceReference<T> serviceReference, T service) {

			_serviceReference = serviceReference;
			_service = service;
		}

		@Override
		public int compareTo(
			ServiceReferenceServiceTuple serviceReferenceServiceTuple) {

			return _serviceReference.compareTo(
				serviceReferenceServiceTuple._serviceReference);
		}

		public T getService() {
			return _service;
		}

		private final T _service;
		private final ServiceReference<T> _serviceReference;

	}

}