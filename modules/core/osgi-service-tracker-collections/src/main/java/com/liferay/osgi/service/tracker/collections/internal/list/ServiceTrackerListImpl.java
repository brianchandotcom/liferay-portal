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

package com.liferay.osgi.service.tracker.collections.internal.list;

import com.liferay.osgi.service.tracker.collections.internal.common.ServiceReferenceServiceTuple;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Adolfo Pérez
 */
public class ServiceTrackerListImpl<S, T> implements ServiceTrackerList<S, T> {

	public ServiceTrackerListImpl(
			BundleContext bundleContext, Class<S> clazz, String filterString,
			ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer,
			Comparator<ServiceReference<S>> comparator)
		throws InvalidSyntaxException {

		_bundleContext = bundleContext;
		_serviceTrackerCustomizer = serviceTrackerCustomizer;

		if (comparator == null) {
			_comparator = Collections.reverseOrder();
		}
		else {
			_comparator = new EntryServiceReferenceComparator<>(comparator);
		}

		if (filterString != null) {
			Filter filter = bundleContext.createFilter(
				"(&(objectClass=" + clazz.getName() + ")" + filterString + ")");

			_serviceTracker = new ServiceTracker<>(
				bundleContext, filter,
				new ServiceReferenceServiceTrackerCustomizer());
		}
		else {
			_serviceTracker = new ServiceTracker<>(
				bundleContext, clazz,
				new ServiceReferenceServiceTrackerCustomizer());
		}
	}

	@Override
	public void close() {
		_serviceTracker.close();
	}

	@Override
	public Iterator<T> iterator() {
		return new ServiceTrackerListIterator<>(_services.iterator());
	}

	@Override
	public void open() {
		_serviceTracker.open();
	}

	@Override
	public int size() {
		return _services.size();
	}

	private final BundleContext _bundleContext;
	private final Comparator<ServiceReferenceServiceTuple<S, T>> _comparator;
	private final List<ServiceReferenceServiceTuple<S, T>> _services =
		new CopyOnWriteArrayList<>();
	private final ServiceTracker<S, T> _serviceTracker;
	private final ServiceTrackerCustomizer<S, T> _serviceTrackerCustomizer;

	private static class ServiceTrackerListIterator<S, T>
		implements Iterator<T> {

		public ServiceTrackerListIterator(
			Iterator<ServiceReferenceServiceTuple<S, T>> iterator) {

			_iterator = iterator;
		}

		@Override
		public boolean hasNext() {
			return _iterator.hasNext();
		}

		@Override
		public T next() {
			ServiceReferenceServiceTuple<S, T> serviceReferenceServiceTuple =
				_iterator.next();

			return serviceReferenceServiceTuple.getService();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		private final Iterator<ServiceReferenceServiceTuple<S, T>> _iterator;

	}

	private class EntryServiceReferenceComparator<S, T>
		implements Comparator<ServiceReferenceServiceTuple<S, T>> {

		public EntryServiceReferenceComparator(
			Comparator<ServiceReference<S>> comparator) {

			_comparator = comparator;
		}

		@Override
		public int compare(
			ServiceReferenceServiceTuple<S, T> serviceReferenceServiceTuple1,
			ServiceReferenceServiceTuple<S, T> serviceReferenceServiceTuple2) {

			int value = _comparator.compare(
				serviceReferenceServiceTuple1.getServiceReference(),
				serviceReferenceServiceTuple2.getServiceReference());

			if (value != 0) {
				return value;
			}

			return serviceReferenceServiceTuple1.compareTo(
				serviceReferenceServiceTuple2);
		}

		private final Comparator<ServiceReference<S>> _comparator;

	}

	private class ServiceReferenceServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<S, T> {

		@Override
		public T addingService(ServiceReference<S> serviceReference) {
			return update(
				serviceReference, getService(serviceReference), false);
		}

		@Override
		public void modifiedService(
			ServiceReference<S> serviceReference, T service) {

			if (_serviceTrackerCustomizer != null) {
				_serviceTrackerCustomizer.modifiedService(
					serviceReference, service);
			}

			update(serviceReference, service, false);
		}

		@Override
		public void removedService(
			ServiceReference<S> serviceReference, T service) {

			if (_serviceTrackerCustomizer != null) {
				_serviceTrackerCustomizer.removedService(
					serviceReference, service);
			}

			update(serviceReference, service, true);

			_bundleContext.ungetService(serviceReference);
		}

		protected T getService(ServiceReference<S> serviceReference) {
			return _serviceTrackerCustomizer.addingService(serviceReference);
		}

		private T update(
			ServiceReference<S> serviceReference, T service, boolean remove) {

			if (service == null) {
				return service;
			}

			ServiceReferenceServiceTuple<S, T> serviceReferenceServiceTuple =
				new ServiceReferenceServiceTuple<>(serviceReference, service);

			synchronized(_services) {
				int index = Collections.binarySearch(
					_services, serviceReferenceServiceTuple, _comparator);

				if (remove) {
					if (index >= 0) {
						_services.remove(index);
					}
				}
				else if (index < 0) {
					_services.add(((-index) - 1), serviceReferenceServiceTuple);
				}
			}

			return service;
		}

	}

}