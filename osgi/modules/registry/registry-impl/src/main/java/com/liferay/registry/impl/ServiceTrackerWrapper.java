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

import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;

import java.util.Collections;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Raymond Augé
 */
public class ServiceTrackerWrapper<S, T> implements ServiceTracker<S, T> {

	public ServiceTrackerWrapper(
		org.osgi.util.tracker.ServiceTracker<S, T> serviceTracker) {

		_serviceTracker = serviceTracker;
	}

	@Override
	public T addingService(ServiceReference<S> serviceReference) {
		org.osgi.framework.ServiceReference<S> wrappedServiceReference =
			getServiceReference(serviceReference);

		return _serviceTracker.addingService(wrappedServiceReference);
	}

	@Override
	public void close() {
		_serviceTracker.close();
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object serviceTracker) {
		if (!(serviceTracker instanceof ServiceTrackerWrapper)) {
			return false;
		}

		ServiceTrackerWrapper<S, T> serviceReferenceWrapper =
			(ServiceTrackerWrapper<S, T>)serviceTracker;

		return _serviceTracker.equals(
			serviceReferenceWrapper.getServiceTracker());
	}

	@Override
	public T getService() {
		return _serviceTracker.getService();
	}

	@Override
	public T getService(ServiceReference<S> serviceReference) {
		org.osgi.framework.ServiceReference<S> wrappedServiceReference =
			getServiceReference(serviceReference);

		return _serviceTracker.getService(wrappedServiceReference);
	}

	@Override
	public ServiceReference<S> getServiceReference() {
		return new ServiceReferenceWrapper<S>(
			_serviceTracker.getServiceReference());
	}

	public org.osgi.framework.ServiceReference<S> getServiceReference(
		ServiceReference<S> serviceReference) {

		if (!(serviceReference instanceof ServiceReferenceWrapper)) {
			throw new IllegalArgumentException();
		}

		ServiceReferenceWrapper<S> serviceReferenceWrapper =
			(ServiceReferenceWrapper<S>)serviceReference;

		return serviceReferenceWrapper.getServiceReference();
	}

	@Override
	@SuppressWarnings("unchecked")
	public ServiceReference<S>[] getServiceReferences() {
		org.osgi.framework.ServiceReference<S>[] serviceReferences =
			_serviceTracker.getServiceReferences();

		if (serviceReferences == null) {
			return null;
		}

		ServiceReference<S>[] array =
			new ServiceReference[serviceReferences.length];

		for (int i = 0; i < serviceReferences.length; i++) {
			org.osgi.framework.ServiceReference<S> serviceReference =
				serviceReferences[i];

			array[i] = new ServiceReferenceWrapper<S>(serviceReference);
		}

		return array;
	}

	@Override
	public Object[] getServices() {
		return _serviceTracker.getServices();
	}

	@Override
	public T[] getServices(T[] array) {
		return _serviceTracker.getServices(array);
	}

	public org.osgi.util.tracker.ServiceTracker<S, T> getServiceTracker() {
		return _serviceTracker;
	}

	@Override
	public SortedMap<ServiceReference<S>, T> getTrackedServiceReferences() {
		SortedMap<ServiceReference<S>, T> trackedServiceReferences =
			new TreeMap<ServiceReference<S>, T>(Collections.reverseOrder());

		SortedMap<org.osgi.framework.ServiceReference<S>, T>
			curTrackedServiceReferences = _serviceTracker.getTracked();

		for (Entry<org.osgi.framework.ServiceReference<S>, T> entry :
				curTrackedServiceReferences.entrySet()) {

			org.osgi.framework.ServiceReference<S> serviceReference =
				entry.getKey();
			T service = entry.getValue();

			ServiceReferenceWrapper<S> serviceReferenceWrapper =
				new ServiceReferenceWrapper<S>(serviceReference);

			trackedServiceReferences.put(serviceReferenceWrapper, service);
		}

		return trackedServiceReferences;
	}

	@Override
	public int getUpdateMarker() {
		return _serviceTracker.getTrackingCount();
	}

	@Override
	public int hashCode() {
		return _serviceTracker.hashCode();
	}

	@Override
	public boolean isEmpty() {
		return _serviceTracker.isEmpty();
	}

	@Override
	public void modifiedService(
		ServiceReference<S> serviceReference, T service) {

		org.osgi.framework.ServiceReference<S> wrappedServiceReference =
			getServiceReference(serviceReference);

		_serviceTracker.modifiedService(wrappedServiceReference, service);
	}

	@Override
	public void open() {
		_serviceTracker.open();
	}

	@Override
	public void open(boolean trackAllServices) {
		_serviceTracker.open(trackAllServices);
	}

	@Override
	public void remove(ServiceReference<S> serviceReference) {
		org.osgi.framework.ServiceReference<S> wrappedServiceReference =
			getServiceReference(serviceReference);

		_serviceTracker.remove(wrappedServiceReference);
	}

	@Override
	public void removedService(
		ServiceReference<S> serviceReference, T service) {

		org.osgi.framework.ServiceReference<S> wrappedServiceReference =
			getServiceReference(serviceReference);

		_serviceTracker.removedService(wrappedServiceReference, service);
	}

	@Override
	public int size() {
		return _serviceTracker.size();
	}

	@Override
	public String toString() {
		return _serviceTracker.toString();
	}

	@Override
	public T waitForService(long timeout) throws InterruptedException {
		return _serviceTracker.waitForService(timeout);
	}

	private org.osgi.util.tracker.ServiceTracker<S, T> _serviceTracker;

}