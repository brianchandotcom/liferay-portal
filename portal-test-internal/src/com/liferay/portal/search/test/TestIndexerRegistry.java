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

package com.liferay.portal.search.test;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.dummy.DummyIndexer;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.StringServiceRegistrationMap;
import com.liferay.registry.collections.StringServiceRegistrationMapImpl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michael C. Han
 */
public class TestIndexerRegistry implements IndexerRegistry {

	public TestIndexerRegistry() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			(Class<Indexer<?>>)(Class<?>)Indexer.class,
			new IndexerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void destroy() {
		if (_serviceTracker != null) {
			_serviceTracker.close();
		}

		_serviceTracker = null;
	}

	@Override
	public <T> Indexer<T> getIndexer(Class<T> clazz) {
		return getIndexer(clazz.getName());
	}

	@Override
	public <T> Indexer<T> getIndexer(String className) {
		return (Indexer<T>)_indexers.get(className);
	}

	@Override
	public Set<Indexer<?>> getIndexers() {
		return new HashSet<>(_indexers.values());
	}

	@Override
	public <T> Indexer<T> nullSafeGetIndexer(Class<T> clazz) {
		return nullSafeGetIndexer(clazz.getName());
	}

	@Override
	public <T> Indexer<T> nullSafeGetIndexer(String className) {
		Indexer<T> indexer = getIndexer(className);

		if (indexer != null) {
			return indexer;
		}

		if (_log.isInfoEnabled()) {
			_log.info("No indexer found for " + className);
		}

		return (Indexer<T>)_dummyIndexer;
	}

	@Override
	public void register(Indexer<?> indexer) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<Indexer<?>> serviceRegistration =
			registry.registerService(
				(Class<Indexer<?>>)(Class<?>)Indexer.class, indexer);

		_serviceRegistrations.put(indexer.getClassName(), serviceRegistration);
	}

	@Override
	public void unregister(Indexer<?> indexer) {
		unregister(indexer.getClassName());
	}

	@Override
	public void unregister(String className) {
		ServiceRegistration<Indexer<?>> serviceRegistration =
			_serviceRegistrations.remove(className);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TestIndexerRegistry.class);

	private final Indexer<?> _dummyIndexer = new DummyIndexer();
	private final Map<String, Indexer<? extends Object>> _indexers =
		new ConcurrentHashMap<>();
	private final StringServiceRegistrationMap<Indexer<?>>
		_serviceRegistrations = new StringServiceRegistrationMapImpl<>();
	private ServiceTracker<Indexer<?>, Indexer<?>> _serviceTracker;

	private class IndexerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Indexer<?>, Indexer<?>> {

		@Override
		public Indexer<?> addingService(
			ServiceReference<Indexer<?>> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			Indexer<?> indexer = registry.getService(serviceReference);

			Class<?> clazz = indexer.getClass();

			_indexers.put(clazz.getName(), indexer);

			_indexers.put(indexer.getClassName(), indexer);

			return indexer;
		}

		@Override
		public void modifiedService(
			ServiceReference<Indexer<?>> serviceReference, Indexer<?> indexer) {
		}

		@Override
		public void removedService(
			ServiceReference<Indexer<?>> serviceReference, Indexer<?> indexer) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			Class<?> clazz = indexer.getClass();

			_indexers.remove(clazz.getName());

			_indexers.remove(indexer.getClassName());
		}

	}

}