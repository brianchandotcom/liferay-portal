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
package com.liferay.registry;

import com.liferay.registry.util.StringPlus;
import com.liferay.registry.util.UMMapDictionary;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Raymond Augé
 */
public class BasicRegistryImpl implements Registry {

	@Override
	public Filter getFilter(String filterString) throws RuntimeException {
		return new BasicFilter(filterString);
	}

	@Override
	public Registry getRegistry() throws SecurityException {
		return this;
	}

	@Override
	public <T> T getService(Class<T> clazz) {
		return getService(clazz.getName());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getService(ServiceReference<T> serviceReference) {
		BasicServiceReference<?> dummyServiceReference =
			(BasicServiceReference<?>)serviceReference;

		for (Entry<ServiceReference<?>, Object> entry :
				getServices().entrySet()) {

			if (dummyServiceReference.matches(entry.getKey())) {
				return (T)entry.getValue();
			}
		}

		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getService(String className) {
		Filter filter = getFilter("(objectClass=" + className + ")");

		for (Entry<ServiceReference<?>, Object> entry :
				getServices().entrySet()) {

			if (filter.matches(entry.getKey())) {
				return (T)entry.getValue();
			}
		}

		return null;
	}

	@Override
	public <T> ServiceReference<T> getServiceReference(Class<T> clazz) {
		return getServiceReference(clazz.getName());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> ServiceReference<T> getServiceReference(String className) {
		Filter filter = getFilter("(objectClass=" + className + ")");

		for (Entry<ServiceReference<?>, Object> entry :
				getServices().entrySet()) {

			if (filter.matches(entry.getKey())) {
				return (ServiceReference<T>)entry.getKey();
			}
		}

		return null;
	}

	@Override
	public <T> Collection<ServiceReference<T>> getServiceReferences(
			Class<T> clazz, String filterString)
		throws Exception {

		return Arrays.asList(
			this.<T>getServiceReferences(clazz.getName(), filterString));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> ServiceReference<T>[] getServiceReferences(
			String className, String filterString)
		throws Exception {

		Filter filter = new BasicFilter(filterString);

		List<ServiceReference<T>> serviceReferences =
			new ArrayList<ServiceReference<T>>();

		for (Entry<ServiceReference<?>, Object> entry :
				getServices().entrySet()) {

			if (filter.matches(entry.getKey())) {
				serviceReferences.add((ServiceReference<T>)entry.getKey());
			}
		}

		if (serviceReferences.isEmpty()) {
			return null;
		}

		return serviceReferences.toArray(new ServiceReference[0]);
	}

	@Override
	public <T> Collection<T> getServices(Class<T> clazz, String filterString)
		throws Exception {

		return getServices(clazz, filterString);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T[] getServices(String className, String filterString)
		throws Exception {

		Filter filter = new BasicFilter(filterString);

		List<T> services = new ArrayList<T>();

		for (Entry<ServiceReference<?>, Object> entry :
				getServices().entrySet()) {

			if (filter.matches(entry.getKey())) {
				services.add((T)entry.getValue());
			}
		}

		if (services.isEmpty()) {
			return null;
		}

		T[] array = (T[])Array.newInstance(
			services.get(0).getClass(), services.size());

		return services.toArray(array);
	}

	@Override
	public <T> ServiceRegistration<T> registerService(
		Class<T> clazz, T service) {

		BasicServiceReference<T> dummyServiceReference =
			new BasicServiceReference<T>(
				clazz.getName(), _serviceIdCounter.incrementAndGet(), 0,
				new HashMap<String, Object>());

		doTrackers_addingService(dummyServiceReference, service);

		return new BasicServiceRegistration<T>(dummyServiceReference);
	}

	@Override
	public <T> ServiceRegistration<T> registerService(
		Class<T> clazz, T service, Map<String, Object> properties) {

		Integer serviceRanking = (Integer)properties.get("service.ranking");

		if (serviceRanking == null) {
			serviceRanking = new Integer(0);
		}

		BasicServiceReference<T> dummyServiceReference =
			new BasicServiceReference<T>(
				clazz.getName(), _serviceIdCounter.incrementAndGet(),
				serviceRanking.intValue(), properties);

		doTrackers_addingService(dummyServiceReference, service);

		return new BasicServiceRegistration<T>(dummyServiceReference);
	}

	@Override
	public <T> ServiceRegistration<T> registerService(
		String className, T service) {

		BasicServiceReference<T> dummyServiceReference =
			new BasicServiceReference<T>(
				className, _serviceIdCounter.incrementAndGet(), 0,
				new HashMap<String, Object>());

		doTrackers_addingService(dummyServiceReference, service);

		return new BasicServiceRegistration<T>(dummyServiceReference);
	}

	@Override
	public <T> ServiceRegistration<T> registerService(
		String className, T service, Map<String, Object> properties) {

		Integer serviceRanking = (Integer)properties.get("service.ranking");

		if (serviceRanking == null) {
			serviceRanking = new Integer(0);
		}

		BasicServiceReference<T> dummyServiceReference =
			new BasicServiceReference<T>(
				className, _serviceIdCounter.incrementAndGet(),
				serviceRanking.intValue(), properties);

		doTrackers_addingService(dummyServiceReference, service);

		return new BasicServiceRegistration<T>(dummyServiceReference);
	}

	@Override
	public <T> ServiceRegistration<T> registerService(
		String[] classNames, T service) {

		if ((classNames == null) || (classNames.length == 0)) {
			throw new IllegalArgumentException();
		}

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("objectClass", classNames);

		BasicServiceReference<T> dummyServiceReference =
			new BasicServiceReference<T>(
				classNames[0], _serviceIdCounter.incrementAndGet(), 0,
				properties);

		doTrackers_addingService(dummyServiceReference, service);

		return new BasicServiceRegistration<T>(dummyServiceReference);
	}

	@Override
	public <T> ServiceRegistration<T> registerService(
		String[] classNames, T service, Map<String, Object> properties) {

		if ((classNames == null) || (classNames.length == 0)) {
			throw new IllegalArgumentException();
		}

		properties.put("objectClass", classNames);

		Integer serviceRanking = (Integer)properties.get("service.ranking");

		if (serviceRanking == null) {
			serviceRanking = new Integer(0);
		}

		BasicServiceReference<T> dummyServiceReference =
			new BasicServiceReference<T>(
				classNames[0], _serviceIdCounter.incrementAndGet(),
				serviceRanking.intValue(), properties);

		doTrackers_addingService(dummyServiceReference, service);

		return new BasicServiceRegistration<T>(dummyServiceReference);
	}

	@Override
	public Registry setRegistry(Registry registry) throws SecurityException {
		return registry;
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(Class<S> clazz) {
		Filter filter = new BasicFilter(
			"(objectClass=" + clazz.getName() + ")");

		return new BasicServiceTracker<S, T>(filter);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(
		Class<S> clazz,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		Filter filter = new BasicFilter(
			"(objectClass=" + clazz.getName() + ")");

		return new BasicServiceTracker<S, T>(filter, serviceTrackerCustomizer);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(Filter filter) {
		return new BasicServiceTracker<S, T>(filter);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(
		Filter filter,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		return new BasicServiceTracker<S, T>(filter, serviceTrackerCustomizer);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(String className) {
		return new BasicServiceTracker<S, T>(
			new BasicFilter("(objectClass=" + className + ")"));
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(
		String className,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		Filter filter = new BasicFilter("(objectClass=" + className + ")");

		return new BasicServiceTracker<S, T>(filter, serviceTrackerCustomizer);
	}

	@Override
	public <T> boolean ungetService(ServiceReference<T> serviceReference) {
		return true;
	}

	public class BasicServiceReference<T> implements ServiceReference<T> {

		public BasicServiceReference(
			String className, long id, int ranking,
			Map<String, Object> properties) {

			List<String> classes = new ArrayList<String>();

			classes.add(className);
			classes.addAll(StringPlus.asList(properties.get("objectClass")));

			_properties.put("service.id", id);
			_properties.put("service.ranking", ranking);
			_properties.putAll(properties);
			_properties.put("objectClass", classes);
		}

		@Override
		public int compareTo(Object serviceReference) {
			BasicServiceReference<?> otherServiceReference =
				(BasicServiceReference<?>)serviceReference;

			int thisRanking = (Integer)_properties.get("service.ranking");
			int otherRanking = (Integer)otherServiceReference._properties.get(
				"service.ranking");

			if (thisRanking != otherRanking) {
				if (thisRanking < otherRanking) {
					return -1;
				}

				return 1;
			}

			long thisId = (Long)_properties.get("service.id");
			long otherId = (Long)otherServiceReference._properties.get(
				"service.id");

			if (thisId == otherId) {
				return 0;
			}

			if (thisId < otherId) {
				return 1;
			}

			return -1;
		}

		@Override
		public Object getProperty(String key) {
			return _properties.get(key.toLowerCase());
		}

		@Override
		public String[] getPropertyKeys() {
			NavigableSet<String> navigableKeySet =
				_properties.navigableKeySet();

			return navigableKeySet.toArray(new String[navigableKeySet.size()]);
		}

		public boolean matches(ServiceReference<?> serviceReference) {
			Filter filter = new BasicFilter(toString());

			return filter.matches(serviceReference);
		}

		@Override
		public String toString() {
			StringBuilder stringBuilder = new StringBuilder();

			Set<Entry<String, Object>> entrySet = _properties.entrySet();

			if (entrySet.size() > 1) {
				stringBuilder.append('(');
				stringBuilder.append('&');
			}

			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object value = entry.getValue();

				Object[] array = null;

				if (value.getClass().isArray()) {
					array = (Object[])value;
				}
				else if (Collection.class.isInstance(value)) {
					array = ((Collection<?>)value).toArray();
				}
				else {
					array = new Object[] {value};
				}

				if (array.length > 0) {
					for (Object object : array) {
						stringBuilder.append('(');
						stringBuilder.append(key);
						stringBuilder.append('=');
						stringBuilder.append(object);
						stringBuilder.append(')');
					}
				}
			}

			if (entrySet.size() > 1) {
				stringBuilder.append(')');
			}

			return stringBuilder.toString();
		}

		private NavigableMap<String, Object> _properties =
			new BasicLowerCaseKeyTreeMap();

	}

	public class BasicServiceRegistration<S> implements ServiceRegistration<S> {

		public BasicServiceRegistration(
			BasicServiceReference<S> dummyServiceReference) {

			_dummyServiceReference = dummyServiceReference;
		}

		@Override
		public ServiceReference<S> getServiceReference() {
			return _dummyServiceReference;
		}

		@Override
		public void setProperties(Map<String, Object> properties) {
			_dummyServiceReference._properties.putAll(properties);

			BasicRegistryImpl.this.doTrackers_modifiedService(
				_dummyServiceReference);
		}

		@Override
		public void unregister() {
			getServices().remove(_dummyServiceReference);

			BasicRegistryImpl.this.doTrackers_removedService(
				_dummyServiceReference);
		}

		private BasicServiceReference<S> _dummyServiceReference;

	}

	@SuppressWarnings("unchecked")
	<S, T> void doTrackers_addingService(
		BasicServiceReference<S> dummyServiceReference, S service) {

		getServices().put(dummyServiceReference, service);

		for (Map.Entry<ServiceTracker<?, ?>, Filter> entry :
				_serviceTrackers.entrySet()) {

			Filter filter = entry.getValue();

			if (!filter.matches(dummyServiceReference)) {
				continue;
			}

			ServiceTracker<S, T> serviceTracker =
				(ServiceTracker<S, T>)entry.getKey();

			try {
				serviceTracker.addingService(dummyServiceReference);
			}
			catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	<S, T> void doTrackers_modifiedService(
		BasicServiceReference<S> dummyServiceReference) {

		for (Map.Entry<ServiceTracker<?, ?>, Filter> entry :
				_serviceTrackers.entrySet()) {

			Filter filter = entry.getValue();

			if (!filter.matches(dummyServiceReference)) {
				continue;
			}

			ServiceTracker<S, T> serviceTracker =
				(ServiceTracker<S, T>)entry.getKey();

			T tracked = serviceTracker.getService(dummyServiceReference);

			if (tracked == null) {
				continue;
			}

			try {
				serviceTracker.modifiedService(dummyServiceReference, tracked);
			}
			catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	<S, T> void doTrackers_removedService(
		BasicServiceReference<S> dummyServiceReference) {

		for (Map.Entry<ServiceTracker<?, ?>, Filter> entry :
				_serviceTrackers.entrySet()) {

			Filter filter = entry.getValue();

			if (!filter.matches(dummyServiceReference)) {
				continue;
			}

			ServiceTracker<S, T> serviceTracker =
				(ServiceTracker<S, T>)entry.getKey();

			T tracked = serviceTracker.getService(dummyServiceReference);

			if (tracked == null) {
				continue;
			}

			serviceTracker.remove(dummyServiceReference);

			try {
				serviceTracker.removedService(dummyServiceReference, tracked);
			}
			catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	private ConcurrentMap<ServiceReference<?>, Object> getServices() {
		return _services;
	}

	private AtomicLong _serviceIdCounter = new AtomicLong();
	private ConcurrentMap<ServiceReference<?>, Object> _services =
		new ConcurrentSkipListMap<ServiceReference<?>, Object>();
	private ConcurrentMap<ServiceTracker<?, ?>, Filter> _serviceTrackers =
		new ConcurrentHashMap<ServiceTracker<?, ?>, Filter>();

	private class BasicFilter implements Filter {

		public BasicFilter(String filterString) {
			_filter = new aQute.lib.filter.Filter(filterString);
		}

		@Override
		public boolean matches(Map<String, Object> properties) {
			UMMapDictionary<String, Object> umMapDictionary =
				new UMMapDictionary<String, Object>(properties);

			return _filter.match(umMapDictionary);
		}

		@Override
		public boolean matches(ServiceReference<?> serviceReference) {
			BasicServiceReference<?> dummyServiceReference =
				(BasicServiceReference<?>)serviceReference;

			UMMapDictionary<String, Object> umMapDictionary =
				new UMMapDictionary<String, Object>(
					dummyServiceReference._properties);

			return _filter.match(umMapDictionary);
		}

		@Override
		public boolean matchesCase(Map<String, Object> properties) {
			return matches(properties);
		}

		private aQute.lib.filter.Filter _filter;

	}

	private class BasicLowerCaseKeyTreeMap extends TreeMap<String, Object> {

		public BasicLowerCaseKeyTreeMap() {
			super();
		}

		@Override
		public Object put(String key, Object value) {
			return super.put(key.toLowerCase(), value);
		}

	}

	private class BasicServiceTracker<S, T> implements ServiceTracker<S, T> {

		public BasicServiceTracker(Filter filter) {
			this(filter, null);
		}

		public BasicServiceTracker(
			Filter filter,
			ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

			_filter = filter;
			_serviceTrackerCustomizer = serviceTrackerCustomizer;
		}

		@Override
		@SuppressWarnings("unchecked")
		public T addingService(ServiceReference<S> serviceReference) {
			T service = null;

			try {
				if (_serviceTrackerCustomizer != null) {
					service = _serviceTrackerCustomizer.addingService(
						serviceReference);
				}
				else {
					service = (T)BasicRegistryImpl.this.getService(
						serviceReference);
				}

				if (service == null) {
					return null;
				}

				_tracked.put(serviceReference, service);

				return service;
			}
			finally {
				if (service != null) {
					_stateCounter.incrementAndGet();
					_countDownLatch.countDown();
				}
			}
		}

		@Override
		public void close() {
			_serviceTrackers.remove(this);

			Iterator<Entry<ServiceReference<S>, T>> iterator =
				_tracked.entrySet().iterator();

			while (iterator.hasNext()) {
				Entry<ServiceReference<S>, T> entry = iterator.next();

				iterator.remove();

				removedService(entry.getKey(), entry.getValue());
			}

			_tracked.clear();
		}

		@Override
		public T getService() {
			return _tracked.get(_tracked.firstKey());
		}

		@Override
		public T getService(ServiceReference<S> serviceReference) {
			BasicServiceReference<S> dummyServiceReference =
				(BasicServiceReference<S>)serviceReference;

			for (ServiceReference<S> curServiceReference : _tracked.keySet()) {
				if (dummyServiceReference.matches(curServiceReference)) {
					return _tracked.get(curServiceReference);
				}
			}

			return null;
		}

		@Override
		public ServiceReference<S> getServiceReference() {
			return _tracked.firstKey();
		}

		@Override
		@SuppressWarnings("unchecked")
		public ServiceReference<S>[] getServiceReferences() {
			return _tracked.keySet().toArray(
				new ServiceReference[_tracked.size()]);
		}

		@Override
		public Object[] getServices() {
			return _tracked.values().toArray();
		}

		@Override
		public T[] getServices(T[] services) {
			return _tracked.values().toArray(services);
		}

		@Override
		public SortedMap<ServiceReference<S>, T> getTrackedServiceReferences() {
			return _tracked;
		}

		@Override
		public int getUpdateMarker() {
			return _stateCounter.get();
		}

		@Override
		public boolean isEmpty() {
			return _tracked.isEmpty();
		}

		@Override
		public void modifiedService(
			ServiceReference<S> serviceReference, T service) {

			try {
				if (_serviceTrackerCustomizer != null) {
					_serviceTrackerCustomizer.modifiedService(
						serviceReference, service);
				}
			}
			finally {
				_stateCounter.incrementAndGet();
			}
		}

		@Override
		@SuppressWarnings("unchecked")
		public void open() {
			_serviceTrackers.put(this, _filter);

			Iterator<Entry<ServiceReference<?>, Object>> iterator =
				BasicRegistryImpl.this.getServices().entrySet().iterator();

			while (iterator.hasNext()) {
				Entry<ServiceReference<?>, Object> entry = iterator.next();

				BasicServiceReference<?> basicServiceReference =
					(BasicServiceReference<?>)entry.getKey();

				if (_filter.matches(basicServiceReference._properties)) {
					ServiceReference<S> serviceReference =
						(ServiceReference<S>)entry.getKey();

					addingService(serviceReference);
				}
			}
		}

		@Override
		public void open(boolean trackAllServices) {
			open();
		}

		@Override
		public void remove(ServiceReference<S> serviceReference) {
			T service = _tracked.remove(serviceReference);

			if (_tracked.isEmpty()) {
				_countDownLatch = new CountDownLatch(1);
			}

			removedService(serviceReference, service);
		}

		@Override
		public void removedService(
			ServiceReference<S> serviceReference, T service) {

			try {
				if (_serviceTrackerCustomizer != null) {
					_serviceTrackerCustomizer.removedService(
						serviceReference, service);
				}
			}
			finally {
				_stateCounter.incrementAndGet();
			}
		}

		@Override
		public int size() {
			return _tracked.size();
		}

		@Override
		public T waitForService(long timeout) throws InterruptedException {
			_countDownLatch.await(timeout, TimeUnit.MILLISECONDS);

			return getService();
		}

		private volatile CountDownLatch _countDownLatch = new CountDownLatch(1);
		private Filter _filter;
		private ServiceTrackerCustomizer<S, T> _serviceTrackerCustomizer;
		private AtomicInteger _stateCounter = new AtomicInteger();
		private ConcurrentNavigableMap<ServiceReference<S>, T> _tracked =
			new ConcurrentSkipListMap<ServiceReference<S>, T>();

	}

}