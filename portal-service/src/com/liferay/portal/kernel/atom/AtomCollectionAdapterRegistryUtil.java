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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Igor Spasic
 */
@ProviderType
public class AtomCollectionAdapterRegistryUtil {

	public static AtomCollectionAdapter<?> getAtomCollectionAdapter(
		String collectionName) {

		return _instance._getAtomCollectionAdapter(collectionName);
	}

	public static List<AtomCollectionAdapter<?>> getAtomCollectionAdapters() {
		return _instance._getAtomCollectionAdapters();
	}

	public static void register(
		AtomCollectionAdapter<?> atomCollectionAdapter) {

		_instance._register(atomCollectionAdapter);
	}

	public static void register(
		List<AtomCollectionAdapter<?>> atomCollectionAdapters) {

		for (AtomCollectionAdapter<?> atomCollectionAdapter :
				atomCollectionAdapters) {

			register(atomCollectionAdapter);
		}
	}

	public static void unregister(
		AtomCollectionAdapter<?> atomCollectionAdapter) {

		_instance._unregister(atomCollectionAdapter);
	}

	public static void unregister(
		List<AtomCollectionAdapter<?>> atomCollectionAdapters) {

		for (AtomCollectionAdapter<?> atomCollectionAdapter :
				atomCollectionAdapters) {

			unregister(atomCollectionAdapter);
		}
	}

	private AtomCollectionAdapterRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			(Class<AtomCollectionAdapter<?>>)(Class<?>)
				AtomCollectionAdapter.class,
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

	private void _register(AtomCollectionAdapter<?> atomCollectionAdapter) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<AtomCollectionAdapter<?>> serviceRegistration =
			registry.registerService(
				(Class<AtomCollectionAdapter<?>>)(Class<?>)
					AtomCollectionAdapter.class,
				atomCollectionAdapter);

		_serviceRegistrations.put(atomCollectionAdapter, serviceRegistration);
	}

	private void _unregister(AtomCollectionAdapter<?> atomCollectionAdapter) {
		ServiceRegistration<AtomCollectionAdapter<?>> serviceRegistration =
			_serviceRegistrations.remove(atomCollectionAdapter);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AtomCollectionAdapterRegistryUtil.class);

	private static final AtomCollectionAdapterRegistryUtil _instance =
		new AtomCollectionAdapterRegistryUtil();

	private final Map<String, AtomCollectionAdapter<?>>
		_atomCollectionAdapters =
			new ConcurrentHashMap<String, AtomCollectionAdapter<?>>();
	private final ServiceRegistrationMap<AtomCollectionAdapter<?>>
		_serviceRegistrations =
			new ServiceRegistrationMap<AtomCollectionAdapter<?>>();
	private final
		ServiceTracker<AtomCollectionAdapter<?>, AtomCollectionAdapter<?>>
			_serviceTracker;

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