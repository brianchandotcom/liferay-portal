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

package com.liferay.registry.collections;

import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Deliberately package private.
 *
 * @author Raymond Augé
 */
class ServiceTrackerCollectionImpl<S> implements ServiceTrackerList<S> {

	ServiceTrackerCollectionImpl(
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

		_serviceTracker.open();
	}

	@Override
	public void add(int index, S element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(S element) {
		Map<String, Object> properties = new HashMap<String, Object>(
			_properties);

		if ((_filter != null) && !_filter.matches(properties)) {
			return false;
		}

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<S> serviceRegistration = registry.registerService(
			_clazz, element, properties);

		_serviceRegistrations.put(element, serviceRegistration);

		return true;
	}

	@Override
	public boolean add(S service, Map<String, Object> properties) {
		properties = new HashMap<String, Object>(properties);

		properties.putAll(_properties);

		if ((_filter != null) && !_filter.matches(properties)) {
			return false;
		}

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<S> serviceRegistration = registry.registerService(
			_clazz, service, properties);

		_serviceRegistrations.put(service, serviceRegistration);

		return true;
	}

	@Override
	public boolean addAll(Collection<? extends S> services) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends S> collection) {
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
	public boolean contains(Object service) {
		return _services.contains(service);
	}

	@Override
	public boolean containsAll(Collection<?> services) {
		throw new UnsupportedOperationException();
	}

	@Override
	public S get(int index) {
		return _services.get(index);
	}

	@Override
	public int indexOf(Object object) {
		return _services.indexOf(object);
	}

	@Override
	public boolean isEmpty() {
		return _services.isEmpty();
	}

	@Override
	public Iterator<S> iterator() {
		return _services.iterator();
	}

	@Override
	public int lastIndexOf(Object object) {
		return _services.lastIndexOf(object);
	}

	@Override
	public ListIterator<S> listIterator() {
		return Collections.unmodifiableList(_services).listIterator();
	}

	@Override
	public ListIterator<S> listIterator(int index) {
		return Collections.unmodifiableList(_services).listIterator(index);
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
	public S set(int index, S element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return _services.size();
	}

	@Override
	public java.util.List<S> subList(int fromIndex, int toIndex) {
		return Collections.unmodifiableList(_services).subList(
			fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return _services.toArray();
	}

	@Override
	public <T> T[] toArray(T[] services) {
		return _services.toArray(services);
	}

	private Filter _getFilter(Filter filter, Class<S> clazz) {
		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("objectClass", clazz.getName());

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
	private final Map<S, ServiceRegistration<S>> _serviceRegistrations =
		new ConcurrentHashMap<S, ServiceRegistration<S>>();
	private final List<S> _services = new CopyOnWriteArrayList<S>();
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

			if (service != null) {
				_services.add(service);
			}

			return service;
		}

		@Override
		public void modifiedService(
			ServiceReference<S> serviceReference, S service) {

			if (_serviceTrackerCustomizer != null) {
				_serviceTrackerCustomizer.modifiedService(
					serviceReference, service);
			}
		}

		@Override
		public void removedService(
			ServiceReference<S> serviceReference, S service) {

			if (_serviceTrackerCustomizer != null) {
				_serviceTrackerCustomizer.removedService(
					serviceReference, service);
			}

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_services.remove(service);
		}

		private final ServiceTrackerCustomizer<S, S> _serviceTrackerCustomizer;

	}

}