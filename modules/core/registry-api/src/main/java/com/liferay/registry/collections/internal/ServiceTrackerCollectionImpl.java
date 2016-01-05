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

package com.liferay.registry.collections.internal;

import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;
import com.liferay.registry.collections.ServiceTrackerList;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Raymond Augé
 */
public class ServiceTrackerCollectionImpl<S> implements ServiceTrackerList<S> {

	public ServiceTrackerCollectionImpl(
		Class<S> clazz, Filter filter,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer,
		Map<String, Object> properties) {

		_clazz = clazz;
		_filter = filter;
		_properties = Collections.unmodifiableMap(properties);

		Registry registry = RegistryUtil.getRegistry();

		if (filter != null) {
			filter = _getFilter(filter, _clazz);

			_serviceTracker = registry.trackServices(
				filter,
				new DefaultServiceTrackerCustomizer(serviceTrackerCustomizer));
		}
		else {
			_serviceTracker = registry.trackServices(
				clazz,
				new DefaultServiceTrackerCustomizer(serviceTrackerCustomizer));
		}
	}

	@Override
	public void add(int index, S service) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(S service) {
		if (service == null) {
			throw new IllegalArgumentException("Service is null");
		}

		if ((_filter != null) && !_filter.matches(_properties)) {
			throw new IllegalStateException();
		}

		Map<String, Object> properties = new HashMap<>(_properties);

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<S> serviceRegistration = registry.registerService(
			_clazz, service, properties);

		_serviceRegistrations.put(service, serviceRegistration);

		return true;
	}

	@Override
	public boolean add(S service, Map<String, Object> properties) {
		if (service == null) {
			throw new IllegalArgumentException("Service is null");
		}

		properties = new HashMap<>(properties);

		properties.putAll(_properties);

		if ((_filter != null) && !_filter.matches(properties)) {
			throw new IllegalArgumentException(
				"Filter does not match properties " + properties);
		}

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<S> serviceRegistration = registry.registerService(
			_clazz, service, properties);

		_serviceRegistrations.put(service, serviceRegistration);

		return true;
	}

	@Override
	public boolean addAll(Collection<? extends S> services) {
		boolean modified = false;

		for (S service : services) {
			if (add(service)) {
				modified = true;
			}
		}

		return modified;
	}

	@Override
	public boolean addAll(int index, Collection<? extends S> services) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		Set<Map.Entry<S, ServiceRegistration<S>>> set =
			_serviceRegistrations.entrySet();

		Iterator<Entry<S, ServiceRegistration<S>>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Entry<S, ServiceRegistration<S>> entry = iterator.next();

			ServiceRegistration<S> serviceRegistration = entry.getValue();

			serviceRegistration.unregister();

			iterator.remove();
		}
	}

	@Override
	public void close() {
		clear();

		_serviceTracker.close();
	}

	@Override
	public boolean contains(Object service) {
		return _services.contains(service);
	}

	@Override
	public boolean containsAll(Collection<?> services) {
		throw new UnsupportedOperationException();
	}

	@Override
	public S get(int index) {
		EntryWrapper entryWrapper = _services.get(index);

		return entryWrapper._service;
	}

	@Override
	public int indexOf(Object service) {
		return _services.indexOf(service);
	}

	@Override
	public boolean isEmpty() {
		return _services.isEmpty();
	}

	@Override
	public Iterator<S> iterator() {
		return new ServiceTrackerIterator(_services.listIterator());
	}

	@Override
	public int lastIndexOf(Object service) {
		return _services.lastIndexOf(service);
	}

	@Override
	public ListIterator<S> listIterator() {
		return new ServiceTrackerIterator(_services.listIterator());
	}

	@Override
	public ListIterator<S> listIterator(int index) {
		return new ServiceTrackerIterator(_services.listIterator(index));
	}

	@Override
	public void open() {
		_serviceTracker.open();
	}

	@Override
	public S remove(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object service) {
		ServiceRegistration<S> serviceRegistration =
			_serviceRegistrations.remove(service);

		if (serviceRegistration == null) {
			return false;
		}

		serviceRegistration.unregister();

		return true;
	}

	@Override
	public boolean removeAll(Collection<?> services) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> services) {
		throw new UnsupportedOperationException();
	}

	@Override
	public S set(int index, S service) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return _services.size();
	}

	@Override
	public List<S> subList(int fromIndex, int toIndex) {
		List<S> list = new ArrayList<>();

		List<EntryWrapper> subList = _services.subList(fromIndex, toIndex);

		for (EntryWrapper entryWrapper : subList) {
			list.add(entryWrapper._service);
		}

		return list;
	}

	@Override
	public Object[] toArray() {
		return toArray(new Object[0]);
	}

	@Override
	public <T> T[] toArray(T[] services) {
		if (services.length < _services.size()) {
			Class<?> clazz = services.getClass();

			services = (T[])Array.newInstance(
				clazz.getComponentType(), _services.size());
		}

		for (int i = 0; i < _services.size(); i++) {
			EntryWrapper entryWrapper = _services.get(i);

			services[i] = (T)entryWrapper._service;
		}

		if (services.length > _services.size()) {
			services[_services.size()] = null;
		}

		return services;
	}

	private Filter _getFilter(Filter filter, Class<S> clazz) {
		Map<String, Object> properties =
			Collections.<String, Object>singletonMap(
				"objectClass", clazz.getName());

		if (filter.matches(properties)) {
			return filter;
		}

		Registry registry = RegistryUtil.getRegistry();

		StringBuilder sb = new StringBuilder(5);

		sb.append("(&(objectClass=");
		sb.append(clazz.getName());
		sb.append(")");
		sb.append(filter.toString());
		sb.append(")");

		return registry.getFilter(sb.toString());
	}

	private final Class<S> _clazz;
	private final Filter _filter;
	private final Map<String, Object> _properties;
	private final ServiceRegistrationMap<S> _serviceRegistrations =
		new ServiceRegistrationMapImpl<>();
	private final List<EntryWrapper> _services = new CopyOnWriteArrayList<>();
	private final ServiceTracker<S, S> _serviceTracker;

	private class DefaultServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<S, S> {

		public DefaultServiceTrackerCustomizer(
			ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer) {

			_serviceTrackerCustomizer = serviceTrackerCustomizer;
		}

		@Override
		public S addingService(ServiceReference<S> serviceReference) {
			S service = null;

			if (_serviceTrackerCustomizer != null) {
				service = _serviceTrackerCustomizer.addingService(
					serviceReference);
			}
			else {
				Registry registry = RegistryUtil.getRegistry();

				service = registry.getService(serviceReference);
			}

			update(serviceReference, service, false);

			return service;
		}

		@Override
		public void modifiedService(
			ServiceReference<S> serviceReference, S service) {

			if (_serviceTrackerCustomizer != null) {
				_serviceTrackerCustomizer.modifiedService(
					serviceReference, service);
			}

			update(serviceReference, service, false);
		}

		@Override
		public void removedService(
			ServiceReference<S> serviceReference, S service) {

			if (_serviceTrackerCustomizer != null) {
				_serviceTrackerCustomizer.removedService(
					serviceReference, service);
			}

			update(serviceReference, service, true);

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);
		}

		private void update(
			ServiceReference<S> serviceReference, S service, boolean remove) {

			if (service == null) {
				return;
			}

			EntryWrapper entryWrapper = new EntryWrapper(
				serviceReference, service);

			synchronized(_services) {
				int index = Collections.binarySearch(_services, entryWrapper);

				if (remove) {
					if (index >= 0) {
						_services.remove(index);
					}
				}
				else if (index < 0) {
					_services.add(((-index) - 1), entryWrapper);
				}
			}
		}

		private final ServiceTrackerCustomizer<S, S> _serviceTrackerCustomizer;

	}

	private class EntryWrapper implements Comparable<EntryWrapper> {

		public EntryWrapper(ServiceReference<S> serviceReference, S service) {
			_serviceReference = serviceReference;
			_service = service;
		}

		@Override
		public int compareTo(EntryWrapper entryWrapper) {

			// The order is deliberately reversed

			return entryWrapper._serviceReference.compareTo(_serviceReference);
		}

		private final S _service;
		private final ServiceReference<S> _serviceReference;

	}

	private class ServiceTrackerIterator implements ListIterator<S> {

		public ServiceTrackerIterator(ListIterator<EntryWrapper> listIterator) {
			_listIterator = listIterator;
		}

		@Override
		public void add(S service) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean hasNext() {
			return _listIterator.hasNext();
		}

		@Override
		public boolean hasPrevious() {
			return _listIterator.hasPrevious();
		}

		@Override
		public S next() {
			EntryWrapper entryWrapper = _listIterator.next();

			return entryWrapper._service;
		}

		@Override
		public int nextIndex() {
			return _listIterator.nextIndex();
		}

		@Override
		public S previous() {
			EntryWrapper entryWrapper = _listIterator.previous();

			return entryWrapper._service;
		}

		@Override
		public int previousIndex() {
			return _listIterator.previousIndex();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void set(S service) {
			throw new UnsupportedOperationException();
		}

		private final ListIterator<EntryWrapper> _listIterator;

	}

}