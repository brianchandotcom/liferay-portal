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

import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;

import java.util.Collections;
import java.util.Map;
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
		org.osgi.framework.ServiceReference<S> osgiServiceReference =
			getServiceReference(serviceReference);

		return _serviceTracker.addingService(osgiServiceReference);
	}

	@Override
	public void close() {
		_serviceTracker.close();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ServiceTrackerWrapper)) {
			return false;
		}

		ServiceTrackerWrapper<S, T> serviceReferenceWrapper =
			(ServiceTrackerWrapper<S, T>)object;

		return _serviceTracker.equals(
			serviceReferenceWrapper.getServiceTracker());
	}

	@Override
	public T getService() {
		return _serviceTracker.getService();
	}

	@Override
	public T getService(ServiceReference<S> serviceReference) {
		org.osgi.framework.ServiceReference<S> osgiServiceReference =
			getServiceReference(serviceReference);

		return _serviceTracker.getService(osgiServiceReference);
	}

	@Override
	public ServiceReference<S> getServiceReference() {
		return new ServiceReferenceWrapper<>(
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
	public ServiceReference<S>[] getServiceReferences() {
		org.osgi.framework.ServiceReference<S>[] osgiServiceReferences =
			_serviceTracker.getServiceReferences();

		if (osgiServiceReferences == null) {
			return null;
		}

		ServiceReference<S>[] serviceReferences =
			new ServiceReference[osgiServiceReferences.length];

		for (int i = 0; i < osgiServiceReferences.length; i++) {
			org.osgi.framework.ServiceReference<S> osgiServiceReference =
				osgiServiceReferences[i];

			serviceReferences[i] = new ServiceReferenceWrapper<>(
				osgiServiceReference);
		}

		return serviceReferences;
	}

	@Override
	public Object[] getServices() {
		return _serviceTracker.getServices();
	}

	@Override
	public T[] getServices(T[] services) {
		return _serviceTracker.getServices(services);
	}

	public org.osgi.util.tracker.ServiceTracker<S, T> getServiceTracker() {
		return _serviceTracker;
	}

	@Override
	public SortedMap<ServiceReference<S>, T> getTrackedServiceReferences() {
		SortedMap<ServiceReference<S>, T> trackedServiceReferences =
			new TreeMap<>(Collections.reverseOrder());

		SortedMap<org.osgi.framework.ServiceReference<S>, T>
			trackedOSGiServiceReferences = _serviceTracker.getTracked();

		for (Map.Entry<org.osgi.framework.ServiceReference<S>, T> entry :
				trackedOSGiServiceReferences.entrySet()) {

			org.osgi.framework.ServiceReference<S> osgiServiceReference =
				entry.getKey();
			T service = entry.getValue();

			ServiceReferenceWrapper<S> serviceReferenceWrapper =
				new ServiceReferenceWrapper<>(osgiServiceReference);

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

		org.osgi.framework.ServiceReference<S> osgiServiceReference =
			getServiceReference(serviceReference);

		_serviceTracker.modifiedService(osgiServiceReference, service);
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
		org.osgi.framework.ServiceReference<S> osgiServiceReference =
			getServiceReference(serviceReference);

		_serviceTracker.remove(osgiServiceReference);
	}

	@Override
	public void removedService(
		ServiceReference<S> serviceReference, T service) {

		org.osgi.framework.ServiceReference<S> osgiServiceReference =
			getServiceReference(serviceReference);

		_serviceTracker.removedService(osgiServiceReference, service);
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

	private final org.osgi.util.tracker.ServiceTracker<S, T> _serviceTracker;

}