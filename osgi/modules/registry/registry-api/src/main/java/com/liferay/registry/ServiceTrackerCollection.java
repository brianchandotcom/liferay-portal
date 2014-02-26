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

package com.liferay.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Raymond Augé
 */
public class ServiceTrackerCollection<S> implements Collection<S> {

	public ServiceTrackerCollection(Class<S> clazz) {
		this(clazz, (Filter)null, null, new HashMap<String, Object>());
	}

	public ServiceTrackerCollection(Class<S> clazz, Filter filter) {
		this(clazz, filter, null, new HashMap<String, Object>());
	}

	public ServiceTrackerCollection(
		Class<S> clazz, Filter filter, Map<String, Object> properties) {

		this(clazz, filter, null, properties);
	}

	public ServiceTrackerCollection(
		Class<S> clazz, Filter filter,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer) {

		this(
			clazz, filter, serviceTrackerCustomizer,
			new HashMap<String, Object>());
	}

	public ServiceTrackerCollection(
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

	public ServiceTrackerCollection(
		Class<S> clazz, Map<String, Object> properties) {

		this(clazz, (Filter)null, null, properties);
	}

	public ServiceTrackerCollection(
		Class<S> clazz,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer) {

		this(
			clazz, (Filter)null, serviceTrackerCustomizer,
			new HashMap<String, Object>());
	}

	public ServiceTrackerCollection(
		Class<S> clazz, ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer,
		Map<String, Object> properties) {

		this(clazz, (Filter)null, serviceTrackerCustomizer, properties);
	}

	public ServiceTrackerCollection(Class<S> clazz, String filterString) {
		this(
			clazz, _getFilter(filterString), null,
			new HashMap<String, Object>());
	}

	public ServiceTrackerCollection(
		Class<S> clazz, String filterString, Map<String, Object> properties) {

		this(clazz, _getFilter(filterString), null, properties);
	}

	public ServiceTrackerCollection(
		Class<S> clazz, String filterString,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer) {

		this(
			clazz, _getFilter(filterString), serviceTrackerCustomizer,
			new HashMap<String, Object>());
	}

	public ServiceTrackerCollection(
		Class<S> clazz, String filterString,
		ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer,
		Map<String, Object> properties) {

		this(
			clazz, _getFilter(filterString), serviceTrackerCustomizer,
			properties);
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
		if (this == services) {
			return false;
		}

		boolean modified = false;

		for (S service : services) {
			modified = add(service);
		}

		return modified;
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
		if (this == services) {
			return true;
		}

		for (Object element : services) {
			if (!contains(element)) {
				return false;
			}
		}

		return true;
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
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> services) {
		return false;
	}

	@Override
	public int size() {
		return _services.size();
	}

	@Override
	public Object[] toArray() {
		return _services.toArray();
	}

	@Override
	public <T> T[] toArray(T[] services) {
		return _services.toArray(services);
	}

	private static Filter _getFilter(String filterString) {
		Registry registry = RegistryUtil.getRegistry();

		return registry.getFilter(filterString);
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

			_services.add(service);

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
			else {
				Registry registry = RegistryUtil.getRegistry();

				registry.ungetService(serviceReference);
			}

			_services.remove(service);
		}

		private final ServiceTrackerCustomizer<S, S> _serviceTrackerCustomizer;

	}

}