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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.dummy.DummyIndexer;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.StringServiceRegistrationMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Raymond Augé
 */
public class IndexerRegistryUtil {

	public static Indexer getIndexer(Class<?> clazz) {
		return _instance._indexers.get(clazz.getName());
	}

	public static Indexer getIndexer(String className) {
		return _instance._indexers.get(className);
	}

	public static List<Indexer> getIndexers() {
		List<Indexer> indexers = new ArrayList<>(_instance._indexers.values());

		return Collections.unmodifiableList(indexers);
	}

	public static Indexer nullSafeGetIndexer(Class<?> clazz) {
		return _instance._nullSafeGetIndexer(clazz.getName());
	}

	public static Indexer nullSafeGetIndexer(String className) {
		return _instance._nullSafeGetIndexer(className);
	}

	public static void register(Indexer indexer) {
		_instance._register(indexer);
	}

	public static void register(String className, Indexer indexer) {
		_instance._register(indexer);
	}

	public static void unregister(Indexer indexer) {
		_instance._unregister(indexer);
	}

	public static void unregister(String className) {
		_instance._unregister(className);
	}

	private IndexerRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			Indexer.class, new IndexerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private Indexer _nullSafeGetIndexer(String className) {
		Indexer indexer = _indexers.get(className);

		if (indexer != null) {
			return indexer;
		}

		if (_log.isWarnEnabled()) {
			_log.warn("No indexer found for " + className);
		}

		return _dummyIndexer;
	}

	private void _register(Indexer indexer) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<Indexer> serviceRegistration =
			registry.registerService(Indexer.class, indexer);

		_serviceRegistrations.put(indexer.getClassName(), serviceRegistration);
	}

	private void _unregister(Indexer indexer) {
		_unregister(indexer.getClassName());
	}

	private void _unregister(String className) {
		ServiceRegistration<Indexer> serviceRegistration =
			_serviceRegistrations.remove(className);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexerRegistryUtil.class);

	private static final IndexerRegistryUtil _instance =
		new IndexerRegistryUtil();

	private static final Indexer _dummyIndexer = new DummyIndexer();

	private final Map<String, Indexer> _indexers = new ConcurrentHashMap<>();
	private final StringServiceRegistrationMap<Indexer> _serviceRegistrations =
		new StringServiceRegistrationMap<>();
	private final ServiceTracker<Indexer, Indexer> _serviceTracker;

	private class IndexerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Indexer, Indexer> {

		@Override
		public Indexer addingService(
			ServiceReference<Indexer> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			Indexer indexer = registry.getService(serviceReference);

			Class<?> clazz = indexer.getClass();

			_indexers.put(clazz.getName(), indexer);

			_indexers.put(indexer.getClassName(), indexer);

			return indexer;
		}

		@Override
		public void modifiedService(
			ServiceReference<Indexer> serviceReference, Indexer indexer) {
		}

		@Override
		public void removedService(
			ServiceReference<Indexer> serviceReference, Indexer indexer) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			Class<?> clazz = indexer.getClass();

			_indexers.remove(clazz.getName());

			_indexers.remove(indexer.getClassName());
		}

	}

}