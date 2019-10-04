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

package com.liferay.portal.kernel.atom;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Igor Spasic
 */
public class AtomCollectionAdapterRegistryUtil {

	public static AtomCollectionAdapter<?> getAtomCollectionAdapter(
		String collectionName) {

		return _atomCollectionAdapterRegistryUtil._getAtomCollectionAdapter(
			collectionName);
	}

	public static List<AtomCollectionAdapter<?>> getAtomCollectionAdapters() {
		return _atomCollectionAdapterRegistryUtil._getAtomCollectionAdapters();
	}

	private AtomCollectionAdapterRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			(Class<AtomCollectionAdapter<?>>)
				(Class<?>)AtomCollectionAdapter.class,
			new AtomCollectionAdapterServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private AtomCollectionAdapter<?> _getAtomCollectionAdapter(
		String collectionName) {

		return _atomCollectionAdapters.get(collectionName);
	}

	private List<AtomCollectionAdapter<?>> _getAtomCollectionAdapters() {
		return ListUtil.fromMapValues(_atomCollectionAdapters);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AtomCollectionAdapterRegistryUtil.class);

	private static final AtomCollectionAdapterRegistryUtil
		_atomCollectionAdapterRegistryUtil =
			new AtomCollectionAdapterRegistryUtil();

	private final Map<String, AtomCollectionAdapter<?>>
		_atomCollectionAdapters = new ConcurrentHashMap<>();
	private final ServiceTracker
		<AtomCollectionAdapter<?>, AtomCollectionAdapter<?>> _serviceTracker;

	private class AtomCollectionAdapterServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<AtomCollectionAdapter<?>, AtomCollectionAdapter<?>> {

		@Override
		public AtomCollectionAdapter<?> addingService(
			ServiceReference<AtomCollectionAdapter<?>> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			AtomCollectionAdapter<?> atomCollectionAdapter =
				registry.getService(serviceReference);

			if (_atomCollectionAdapters.containsKey(
					atomCollectionAdapter.getCollectionName())) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						"Duplicate collection name " +
							atomCollectionAdapter.getCollectionName());
				}

				return null;
			}

			_atomCollectionAdapters.put(
				atomCollectionAdapter.getCollectionName(),
				atomCollectionAdapter);

			return atomCollectionAdapter;
		}

		@Override
		public void modifiedService(
			ServiceReference<AtomCollectionAdapter<?>> serviceReference,
			AtomCollectionAdapter<?> atomCollectionAdapter) {
		}

		@Override
		public void removedService(
			ServiceReference<AtomCollectionAdapter<?>> serviceReference,
			AtomCollectionAdapter<?> atomCollectionAdapter) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_atomCollectionAdapters.remove(
				atomCollectionAdapter.getCollectionName());
		}

	}

}