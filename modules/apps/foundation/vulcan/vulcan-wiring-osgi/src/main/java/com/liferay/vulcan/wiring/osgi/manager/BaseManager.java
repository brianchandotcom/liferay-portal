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

import com.liferay.vulcan.error.VulcanDeveloperError;
import com.liferay.vulcan.result.Try;
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
 * Manages services that have a generic type.
 *
 * @author Alejandro Hernández
 */
public abstract class BaseManager<T> {

	public BaseManager() {
		Bundle bundle = FrameworkUtil.getBundle(BaseManager.class);

		_bundleContext = bundle.getBundleContext();
	}

	/**
	 * Adds a new {@code serviceReference/service} tuple to the internal map, if
	 * a valid service can be obtained. Returns {@code Optional#empty()}
	 * otherwise.
	 *
	 * @param  serviceReference the service reference
	 * @return the generic inner class of the service reference's service, if a
	 *         valid service can be obtained; {@code Optional#empty()} otherwise
	 */
	protected <U> Optional<Class<U>> addService(
		ServiceReference<T> serviceReference) {

		T service = _bundleContext.getService(serviceReference);

		if (service == null) {
			return Optional.empty();
		}

		Class<U> genericClass = _getGenericClass(service);

		_services.computeIfAbsent(
			genericClass.getName(), name -> new TreeSet<>());

		TreeSet<ServiceReferenceServiceTuple<T>> serviceReferenceServiceTuples =
			_services.get(genericClass.getName());

		ServiceReferenceServiceTuple<T> serviceReferenceServiceTuple =
			new ServiceReferenceServiceTuple<>(serviceReference, service);

		serviceReferenceServiceTuples.add(serviceReferenceServiceTuple);

		return Optional.of(genericClass);
	}

	/**
	 * Returns a service from the inner map based on the service's generic inner
	 * class, if the service exists. Returns {@code Optional#empty()} otherwise.
	 *
	 * @param  clazz the generic inner class
	 * @return the service, if present; {@code Optional#empty()} otherwise
	 */
	protected <U> Optional<T> getServiceOptional(Class<U> clazz) {
		return getServiceOptional(clazz.getName());
	}

	/**
	 * Returns a service from the inner map based on the service's generic inner
	 * class name, if the service exists. Returns {@code Optional#empty()}
	 * otherwise.
	 *
	 * @param  className the generic inner class name
	 * @return the service, if present; {@code Optional#empty()} otherwise
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
	 * Removes a {@code serviceReference/service} tuple from the internal map,
	 * if the service exists. Returns {@code Optional#empty()} otherwise.
	 *
	 * @param  serviceReference the service reference
	 * @return the generic inner class of the service reference's service, if a
	 *         valid service can be obtained; {@code Optional#empty()} otherwise
	 */
	protected <U> Optional<Class<U>> removeService(
		ServiceReference<T> serviceReference) {

		Consumer<T> identityConsumer = t -> {
		};

		return removeService(serviceReference, identityConsumer);
	}

	/**
	 * Removes a {@code serviceReference/service} tuple from the internal map,
	 * after calling a consumer. Returns {@code Optional#empty()} if the service
	 * doesn't exist.
	 *
	 * @param  serviceReference the service reference
	 * @param  beforeRemovingConsumer the consumer called prior to removing the
	 *         service
	 * @return the generic inner class of the service reference's service, if a
	 *         valid service can be obtained; {@code Optional#empty()} otherwise
	 */
	protected <U> Optional<Class<U>> removeService(
		ServiceReference<T> serviceReference,
		Consumer<T> beforeRemovingConsumer) {

		T service = _bundleContext.getService(serviceReference);

		if (service == null) {
			return Optional.empty();
		}

		Class<U> genericClass = _getGenericClass(service);

		TreeSet<ServiceReferenceServiceTuple<T>> serviceReferenceServiceTuples =
			_services.get(genericClass.getName());

		beforeRemovingConsumer.accept(service);

		if (serviceReferenceServiceTuples != null) {
			serviceReferenceServiceTuples.removeIf(
				serviceReferenceServiceTuple -> {
					if (serviceReferenceServiceTuple.getService() == service) {
						return true;
					}

					return false;
				});
		}

		return Optional.of(genericClass);
	}

	private <U> Class<U> _getGenericClass(T service) {
		Class<?> serviceClass = service.getClass();

		Try<Class<U>> classTry = GenericUtil.getFirstGenericTypeArgumentTry(
			serviceClass);

		return classTry.orElseThrow(
			() -> new VulcanDeveloperError.MustHaveValidGenericType(
				serviceClass));
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

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (obj == null) {
				return false;
			}

			if (!(obj instanceof ServiceReferenceServiceTuple)) {
				return false;
			}

			if (compareTo((ServiceReferenceServiceTuple)obj) == 0) {
				return true;
			}

			return false;
		}

		public T getService() {
			return _service;
		}

		@Override
		public int hashCode() {
			return System.identityHashCode(_service);
		}

		private final T _service;
		private final ServiceReference<T> _serviceReference;

	}

}