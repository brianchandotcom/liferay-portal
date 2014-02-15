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
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Raymond Augé
 */
public class ServiceTrackerCollection <S> implements Collection<S> {

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
		ServiceTrackerCustomizer<S, S> customizer) {

		this(clazz, filter, customizer, new HashMap<String, Object>());
	}

	@SuppressWarnings("unchecked")
	public ServiceTrackerCollection(
		Class<S> clazz, Filter filter,
		ServiceTrackerCustomizer<S, S> customizer,
		Map<String, Object> properties) {

		_clazz = clazz;
		_filter = filter;
		_properties = Collections.unmodifiableMap(properties);

		_cachedCollection = new CopyOnWriteArrayList<S>();

		if (filter != null) {
			filter = fixFilter(filter, _clazz);

			_serviceTracker = getRegistry().trackServices(
				filter, new CachingCustomizer(customizer));
		}
		else {
			_serviceTracker = getRegistry().trackServices(
				clazz, new CachingCustomizer(customizer));
		}

		_serviceTracker.open();

		_serviceRegistrations =
			new ConcurrentHashMap<S, ServiceRegistration<S>>();
	}

	public ServiceTrackerCollection(
		Class<S> clazz, Map<String, Object> properties) {

		this(clazz, (Filter)null, null, properties);
	}

	public ServiceTrackerCollection(
		Class<S> clazz, ServiceTrackerCustomizer<S, S> customizer) {

		this(clazz, (Filter)null, customizer, new HashMap<String, Object>());
	}

	public ServiceTrackerCollection(
		Class<S> clazz, ServiceTrackerCustomizer<S, S> customizer,
		Map<String, Object> properties) {

		this(clazz, (Filter)null, customizer, properties);
	}

	public ServiceTrackerCollection(Class<S> clazz, String filterString) {
		this(
			clazz, getRegistry().getFilter(filterString), null,
			new HashMap<String, Object>());
	}

	public ServiceTrackerCollection(
		Class<S> clazz, String filterString, Map<String, Object> properties) {

		this(clazz, getRegistry().getFilter(filterString), null, properties);
	}

	public ServiceTrackerCollection(
		Class<S> clazz, String filterString,
		ServiceTrackerCustomizer<S, S> customizer) {

		this(
			clazz, getRegistry().getFilter(filterString), customizer,
			new HashMap<String, Object>());
	}

	public ServiceTrackerCollection(
		Class<S> clazz, String filterString,
		ServiceTrackerCustomizer<S, S> customizer,
		Map<String, Object> properties) {

		this(
			clazz, getRegistry().getFilter(filterString), customizer,
			properties);
	}

	@Override
	public boolean add(S element) {
		Map<String, Object> map = new HashMap<String, Object>(_properties);

		if ((_filter != null) && (!_filter.matches(map))) {
			return false;
		}

		ServiceRegistration<S> serviceRegistration =
			getRegistry().registerService(_clazz, element, map);

		_serviceRegistrations.put(element, serviceRegistration);

		return true;
	}

	public boolean add(S element, Map<String, Object> properties) {
		Map<String, Object> map = new HashMap<String, Object>(properties);

		map.putAll(_properties);

		if ((_filter != null) && !_filter.matches(map)) {
			return false;
		}

		ServiceRegistration<S> serviceRegistration =
			getRegistry().registerService(_clazz, element, map);

		_serviceRegistrations.put(element, serviceRegistration);

		return true;
	}

	@Override
	public boolean addAll(Collection<? extends S> collection) {
		if (this == collection) {
			return false;
		}

		boolean modified = false;

		for (S element : collection) {
			modified = add(element);
		}

		return modified;
	}

	@Override
	public void clear() {
		Iterator<Entry<S, ServiceRegistration<S>>> iterator =
			_serviceRegistrations.entrySet().iterator();

		while (iterator.hasNext()) {
			Entry<S, ServiceRegistration<S>> entry = iterator.next();

			entry.getValue().unregister();
			iterator.remove();
		}
	}

	@Override
	public boolean contains(Object element) {
		return _cachedCollection.contains(element);
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		if (this == collection) {
			return true;
		}

		for (Object element : collection) {
			if (!contains(element)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean isEmpty() {
		return _cachedCollection.isEmpty();
	}

	@Override
	public Iterator<S> iterator() {
		return _cachedCollection.iterator();
	}

	@Override
	public boolean remove(Object element) {
		ServiceRegistration<S> serviceRegistration =
			_serviceRegistrations.remove(element);

		if (serviceRegistration == null) {
			return false;
		}

		serviceRegistration.unregister();

		return true;
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean retainAll(Collection<?> collection) {
		return false;
	}

	@Override
	public int size() {
		return _cachedCollection.size();
	}

	@Override
	public Object[] toArray() {
		return _cachedCollection.toArray();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] array) {
		return _cachedCollection.toArray(array);
	}

	private static Registry getRegistry() {
		return RegistryUtil.getRegistry();
	}

	private Filter fixFilter(Filter filter, Class<S> clazz) {
		String className = clazz.getName();

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("objectClass", className);

		if (filter.matches(map)) {
			return filter;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("(&(objectClass=");
		sb.append(className);
		sb.append(")");
		sb.append(filter.toString());
		sb.append(")");

		return RegistryUtil.getRegistry().getFilter(sb.toString());
	}

	private final CopyOnWriteArrayList<S> _cachedCollection;
	private final Class<S> _clazz;
	private final Filter _filter;
	private final Map<String, Object> _properties;
	private final Map<S, ServiceRegistration<S>> _serviceRegistrations;
	private final ServiceTracker<S, S> _serviceTracker;

	private class CachingCustomizer
		implements ServiceTrackerCustomizer<S, S> {

		public CachingCustomizer(
			ServiceTrackerCustomizer<S, S> serviceTrackerCustomizer) {

			_delegate = serviceTrackerCustomizer;
		}

		@Override
		public S addingService(ServiceReference<S> serviceReference) {
			S service;

			if (_delegate != null) {
				service = _delegate.addingService(serviceReference);
			}
			else {
				service = getRegistry().getService(serviceReference);
			}

			ServiceTrackerCollection.this._cachedCollection.add(service);

			return service;
		}

		@Override
		public void modifiedService(
			ServiceReference<S> serviceReference, S service) {

			if (_delegate != null) {
				_delegate.modifiedService(serviceReference, service);
			}
		}

		@Override
		public void removedService(
			ServiceReference<S> serviceReference, S service) {

			if (_delegate != null) {
				_delegate.removedService(serviceReference, service);
			}
			else {
				getRegistry().ungetService(serviceReference);
			}

			ServiceTrackerCollection.this._cachedCollection.remove(service);
		}

		private final ServiceTrackerCustomizer<S, S> _delegate;

	}

}