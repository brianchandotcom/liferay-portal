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

import com.liferay.registry.dependency.ServiceDependencyManager;
import com.liferay.registry.util.StringPlus;
import com.liferay.registry.util.UnmodifiableCaseInsensitiveMapDictionary;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
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
	public <T> T getService(ServiceReference<T> serviceReference) {
		BasicServiceReference<?> basicServiceReference =
			(BasicServiceReference<?>)serviceReference;

		for (Entry<ServiceReference<?>, Object> entry : _services.entrySet()) {
			if (basicServiceReference.matches(entry.getKey())) {
				return (T)entry.getValue();
			}
		}

		return null;
	}

	@Override
	public <T> T getService(String className) {
		Filter filter = getFilter("(objectClass=" + className + ")");

		for (Entry<ServiceReference<?>, Object> entry : _services.entrySet()) {
			if (filter.matches(entry.getKey())) {
				return (T)entry.getValue();
			}
		}

		return null;
	}

	@Override
	public Collection<ServiceDependencyManager> getServiceDependencyManagers() {
		return Collections.unmodifiableCollection(_serviceDependencyManagers);
	}

	@Override
	public <T> ServiceReference<T> getServiceReference(Class<T> clazz) {
		return getServiceReference(clazz.getName());
	}

	@Override
	public <T> ServiceReference<T> getServiceReference(String className) {
		Filter filter = getFilter("(objectClass=" + className + ")");

		for (Entry<ServiceReference<?>, Object> entry : _services.entrySet()) {
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
	public <T> ServiceReference<T>[] getServiceReferences(
			String className, String filterString)
		throws Exception {

		List<ServiceReference<T>> serviceReferences = new ArrayList<>();

		Filter filter = new BasicFilter(filterString);

		for (Entry<ServiceReference<?>, Object> entry : _services.entrySet()) {
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
	public <T> ServiceRegistrar<T> getServiceRegistrar(Class<T> clazz) {
		return new ServiceRegistrar<>(this, clazz);
	}

	@Override
	public <T> Collection<T> getServices(Class<T> clazz, String filterString)
		throws Exception {

		T[] services = getServices(clazz.getName(), filterString);

		if (services == null) {
			return Collections.emptyList();
		}

		return Arrays.asList(services);
	}

	@Override
	public <T> T[] getServices(String className, String filterString)
		throws Exception {

		List<T> services = new ArrayList<>();

		if ((filterString == null) || filterString.equals("")) {
			filterString = "(objectClass=" + className + ")";
		}

		Filter filter = new BasicFilter(filterString);

		for (Entry<ServiceReference<?>, Object> entry : _services.entrySet()) {
			if (filter.matches(entry.getKey())) {
				services.add((T)entry.getValue());
			}
		}

		if (services.isEmpty()) {
			return null;
		}

		T service = services.get(0);

		Class<?> clazz = service.getClass();

		T[] array = (T[])Array.newInstance(clazz, services.size());

		return services.toArray(array);
	}

	@Override
	public <T> ServiceRegistration<T> registerService(
		Class<T> clazz, T service) {

		BasicServiceReference<T> basicServiceReference =
			new BasicServiceReference<T>(
				clazz.getName(), _serviceIdCounter.incrementAndGet(), 0,
				new HashMap<String, Object>());

		_addingService(basicServiceReference, service);

		return new BasicServiceRegistration<>(basicServiceReference);
	}

	@Override
	public <T> ServiceRegistration<T> registerService(
		Class<T> clazz, T service, Map<String, Object> properties) {

		Integer serviceRanking = (Integer)properties.get("service.ranking");

		if (serviceRanking == null) {
			serviceRanking = Integer.valueOf(0);
		}

		BasicServiceReference<T> basicServiceReference =
			new BasicServiceReference<T>(
				clazz.getName(), _serviceIdCounter.incrementAndGet(),
				serviceRanking.intValue(), properties);

		_addingService(basicServiceReference, service);

		return new BasicServiceRegistration<>(basicServiceReference);
	}

	@Override
	public <T> ServiceRegistration<T> registerService(
		String className, T service) {

		BasicServiceReference<T> basicServiceReference =
			new BasicServiceReference<T>(
				className, _serviceIdCounter.incrementAndGet(), 0,
				new HashMap<String, Object>());

		_addingService(basicServiceReference, service);

		return new BasicServiceRegistration<>(basicServiceReference);
	}

	@Override
	public <T> ServiceRegistration<T> registerService(
		String className, T service, Map<String, Object> properties) {

		Integer serviceRanking = (Integer)properties.get("service.ranking");

		if (serviceRanking == null) {
			serviceRanking = Integer.valueOf(0);
		}

		BasicServiceReference<T> basicServiceReference =
			new BasicServiceReference<T>(
				className, _serviceIdCounter.incrementAndGet(),
				serviceRanking.intValue(), properties);

		_addingService(basicServiceReference, service);

		return new BasicServiceRegistration<>(basicServiceReference);
	}

	@Override
	public <T> ServiceRegistration<T> registerService(
		String[] classNames, T service) {

		if ((classNames == null) || (classNames.length == 0)) {
			throw new IllegalArgumentException();
		}

		Map<String, Object> properties = new HashMap<>();

		properties.put("objectClass", classNames);

		BasicServiceReference<T> basicServiceReference =
			new BasicServiceReference<T>(
				classNames[0], _serviceIdCounter.incrementAndGet(), 0,
				properties);

		_addingService(basicServiceReference, service);

		return new BasicServiceRegistration<>(basicServiceReference);
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
			serviceRanking = Integer.valueOf(0);
		}

		BasicServiceReference<T> basicServiceReference =
			new BasicServiceReference<T>(
				classNames[0], _serviceIdCounter.incrementAndGet(),
				serviceRanking.intValue(), properties);

		_addingService(basicServiceReference, service);

		return new BasicServiceRegistration<>(basicServiceReference);
	}

	@Override
	public synchronized void registerServiceDependencyManager(
		ServiceDependencyManager serviceDependencyManager) {

		_serviceDependencyManagers.add(serviceDependencyManager);
	}

	@Override
	public Registry setRegistry(Registry registry) throws SecurityException {
		return registry;
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(Class<S> clazz) {
		Filter filter = new BasicFilter(
			"(objectClass=" + clazz.getName() + ")");

		return new BasicServiceTracker<>(filter);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(
		Class<S> clazz,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		Filter filter = new BasicFilter(
			"(objectClass=" + clazz.getName() + ")");

		return new BasicServiceTracker<>(filter, serviceTrackerCustomizer);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(Filter filter) {
		return new BasicServiceTracker<>(filter);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(
		Filter filter,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		return new BasicServiceTracker<>(filter, serviceTrackerCustomizer);
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(String className) {
		return new BasicServiceTracker<>(
			new BasicFilter("(objectClass=" + className + ")"));
	}

	@Override
	public <S, T> ServiceTracker<S, T> trackServices(
		String className,
		ServiceTrackerCustomizer<S, T> serviceTrackerCustomizer) {

		Filter filter = new BasicFilter("(objectClass=" + className + ")");

		return new BasicServiceTracker<>(filter, serviceTrackerCustomizer);
	}

	@Override
	public <T> boolean ungetService(ServiceReference<T> serviceReference) {
		return true;
	}

	@Override
	public void unregisterServiceDependencyManager(
		ServiceDependencyManager serviceDependencyManager) {

		_serviceDependencyManagers.remove(serviceDependencyManager);
	}

	private <S, T> void _addingService(
		BasicServiceReference<S> basicServiceReference, S service) {

		_services.put(basicServiceReference, service);

		for (Map.Entry<ServiceTracker<?, ?>, Filter> entry :
				_serviceTrackers.entrySet()) {

			Filter filter = entry.getValue();

			if (!filter.matches(basicServiceReference)) {
				continue;
			}

			ServiceTracker<S, T> serviceTracker =
				(ServiceTracker<S, T>)entry.getKey();

			try {
				serviceTracker.addingService(basicServiceReference);
			}
			catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	private <S, T> void _modifiedService(
		BasicServiceReference<S> basicServiceReference) {

		for (Map.Entry<ServiceTracker<?, ?>, Filter> entry :
				_serviceTrackers.entrySet()) {

			Filter filter = entry.getValue();

			if (!filter.matches(basicServiceReference)) {
				continue;
			}

			ServiceTracker<S, T> serviceTracker =
				(ServiceTracker<S, T>)entry.getKey();

			T service = serviceTracker.getService(basicServiceReference);

			if (service == null) {
				continue;
			}

			try {
				serviceTracker.modifiedService(basicServiceReference, service);
			}
			catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	private <S, T> void _removedService(
		BasicServiceReference<S> basicServiceReference) {

		for (Map.Entry<ServiceTracker<?, ?>, Filter> entry :
				_serviceTrackers.entrySet()) {

			Filter filter = entry.getValue();

			if (!filter.matches(basicServiceReference)) {
				continue;
			}

			ServiceTracker<S, T> serviceTracker =
				(ServiceTracker<S, T>)entry.getKey();

			try {
				serviceTracker.remove(basicServiceReference);
			}
			catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	private final Set<ServiceDependencyManager> _serviceDependencyManagers =
		new HashSet<>();
	private final AtomicLong _serviceIdCounter = new AtomicLong();
	private final Map<ServiceReference<?>, Object> _services =
		new ConcurrentSkipListMap<>();
	private final Map<ServiceTracker<?, ?>, Filter> _serviceTrackers =
		new ConcurrentHashMap<>();

	private static class BasicFilter implements Filter {

		public BasicFilter(String filterString) {
			_filter = new aQute.lib.filter.Filter(filterString);
		}

		@Override
		public boolean matches(Map<String, Object> properties) {
			Dictionary<String, Object> dictionary =
				new UnmodifiableCaseInsensitiveMapDictionary<>(properties);

			return _filter.match(dictionary);
		}

		@Override
		public boolean matches(ServiceReference<?> serviceReference) {
			BasicServiceReference<?> basicServiceReference =
				(BasicServiceReference<?>)serviceReference;

			Dictionary<String, Object> dictionary =
				new UnmodifiableCaseInsensitiveMapDictionary<>(
					basicServiceReference._properties);

			return _filter.match(dictionary);
		}

		@Override
		public boolean matchesCase(Map<String, Object> properties) {
			return matches(properties);
		}

		@Override
		public String toString() {
			return _filter.toString();
		}

		private aQute.lib.filter.Filter _filter;

	}

	private static class LowerCaseKeyTreeMap extends TreeMap<String, Object> {

		@Override
		public Object put(String key, Object value) {
			return super.put(key.toLowerCase(), value);
		}

	}

	private class BasicServiceReference<T> implements ServiceReference<T> {

		public BasicServiceReference(
			String className, long id, int ranking,
			Map<String, Object> properties) {

			_properties.put("service.id", id);
			_properties.put("service.ranking", ranking);

			List<String> classNames = new ArrayList<>();

			classNames.add(className);
			classNames.addAll(StringPlus.asList(properties.get("objectClass")));

			_properties.putAll(properties);

			_properties.put("objectClass", classNames);
		}

		@Override
		public int compareTo(Object serviceReference) {
			BasicServiceReference<?> otherServiceReference =
				(BasicServiceReference<?>)serviceReference;

			int thisServiceRanking = (Integer)_properties.get(
				"service.ranking");

			Map<String, Object> otherProperties =
				otherServiceReference._properties;

			int otherServiceRanking = (Integer)otherProperties.get(
				"service.ranking");

			if (thisServiceRanking != otherServiceRanking) {
				if (thisServiceRanking < otherServiceRanking) {
					return -1;
				}

				return 1;
			}

			long thisServiceId = (Long)_properties.get("service.id");
			long otherServiceId = (Long)otherProperties.get("service.id");

			if (thisServiceId == otherServiceId) {
				return 0;
			}

			if (thisServiceId < otherServiceId) {
				return 1;
			}

			return -1;
		}

		@Override
		public Map<String, Object> getProperties() {
			return new HashMap<>(_properties);
		}

		@Override
		public Object getProperty(String key) {
			return _properties.get(key.toLowerCase());
		}

		@Override
		public String[] getPropertyKeys() {
			Set<String> set = _properties.keySet();

			return set.toArray(new String[set.size()]);
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

				Class<?> clazz = value.getClass();

				if (clazz.isArray()) {
					array = (Object[])value;
				}
				else if (value instanceof Collection) {
					Collection<?> collection = (Collection<?>)value;

					array = collection.toArray();
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

		private final Map<String, Object> _properties =
			new LowerCaseKeyTreeMap();

	}

	private class BasicServiceRegistration<S>
		implements ServiceRegistration<S> {

		public BasicServiceRegistration(
			BasicServiceReference<S> basicServiceReference) {

			_basicServiceReference = basicServiceReference;
		}

		@Override
		public ServiceReference<S> getServiceReference() {
			return _basicServiceReference;
		}

		@Override
		public void setProperties(Map<String, Object> properties) {
			_basicServiceReference._properties.putAll(properties);

			_modifiedService(_basicServiceReference);
		}

		@Override
		public void unregister() {
			_services.remove(_basicServiceReference);

			_removedService(_basicServiceReference);
		}

		private final BasicServiceReference<S> _basicServiceReference;

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

				_trackedServices.put(serviceReference, service);

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

			Set<Entry<ServiceReference<S>, T>> set =
				_trackedServices.entrySet();

			Iterator<Entry<ServiceReference<S>, T>> iterator = set.iterator();

			while (iterator.hasNext()) {
				Entry<ServiceReference<S>, T> entry = iterator.next();

				iterator.remove();

				removedService(entry.getKey(), entry.getValue());
			}

			_trackedServices.clear();
		}

		@Override
		public T getService() {
			Entry<ServiceReference<S>, T> firstEntry =
				_trackedServices.firstEntry();

			if (firstEntry == null) {
				return null;
			}

			return firstEntry.getValue();
		}

		@Override
		public T getService(ServiceReference<S> serviceReference) {
			BasicServiceReference<S> basicServiceReference =
				(BasicServiceReference<S>)serviceReference;

			for (ServiceReference<S> curServiceReference :
					_trackedServices.keySet()) {

				if (basicServiceReference.matches(curServiceReference)) {
					return _trackedServices.get(curServiceReference);
				}
			}

			return null;
		}

		@Override
		public ServiceReference<S> getServiceReference() {
			return _trackedServices.firstKey();
		}

		@Override
		public ServiceReference<S>[] getServiceReferences() {
			Set<S> set = (Set<S>)_trackedServices.keySet();

			return set.toArray(new ServiceReference[_trackedServices.size()]);
		}

		@Override
		public Object[] getServices() {
			Collection<T> values = _trackedServices.values();

			return values.toArray();
		}

		@Override
		public T[] getServices(T[] services) {
			Collection<T> values = _trackedServices.values();

			return values.toArray(services);
		}

		@Override
		public SortedMap<ServiceReference<S>, T> getTrackedServiceReferences() {
			return _trackedServices;
		}

		@Override
		public int getUpdateMarker() {
			return _stateCounter.get();
		}

		@Override
		public boolean isEmpty() {
			return _trackedServices.isEmpty();
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
		public void open() {
			_serviceTrackers.put(this, _filter);

			Set<Entry<ServiceReference<?>, Object>> set = _services.entrySet();

			Iterator<Entry<ServiceReference<?>, Object>> iterator =
				set.iterator();

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
			T service = _trackedServices.remove(serviceReference);

			if (_trackedServices.isEmpty()) {
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
			return _trackedServices.size();
		}

		@Override
		public T waitForService(long timeout) throws InterruptedException {
			_countDownLatch.await(timeout, TimeUnit.MILLISECONDS);

			return getService();
		}

		private volatile CountDownLatch _countDownLatch = new CountDownLatch(1);
		private Filter _filter;
		private final ServiceTrackerCustomizer<S, T> _serviceTrackerCustomizer;
		private final AtomicInteger _stateCounter = new AtomicInteger();
		private final NavigableMap<ServiceReference<S>, T> _trackedServices =
			new ConcurrentSkipListMap<>();

	}

}