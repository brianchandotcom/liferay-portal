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

package com.liferay.exportimport.kernel.lar;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.collections.ServiceRegistrationMapImpl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provides a utility facade to the staged model data handler registry
 * framework.
 *
 * @author Máté Thurzó
 * @author Brian Wing Shun Chan
 * @since  6.2
 */
public class StagedModelDataHandlerRegistryUtil {

	/**
	 * Returns the registered staged model data handler with the class name.
	 *
	 * @param  className the name of the staged model class
	 * @return the registered staged model data handler with the class name, or
	 *         <code>null</code> if none are registered with the class name
	 */
	public static StagedModelDataHandler<?> getStagedModelDataHandler(
		String className) {

		return _stagedModelDataHandlerRegistryUtil._getStagedModelDataHandler(
			className);
	}

	/**
	 * Returns all the registered staged model data handlers.
	 *
	 * @return the registered staged model data handlers
	 */
	public static List<StagedModelDataHandler<?>> getStagedModelDataHandlers() {
		return _stagedModelDataHandlerRegistryUtil.
			_getStagedModelDataHandlers();
	}

	/**
	 * Registers the staged model data handler.
	 *
	 * @param stagedModelDataHandler the staged model data handler to register
	 */
	public static void register(
		StagedModelDataHandler<?> stagedModelDataHandler) {

		_stagedModelDataHandlerRegistryUtil._register(stagedModelDataHandler);
	}

	/**
	 * Unregisters the staged model data handlers.
	 *
	 * @param stagedModelDataHandlers the staged model data handlers to
	 *        unregister
	 */
	public static void unregister(
		List<StagedModelDataHandler<?>> stagedModelDataHandlers) {

		for (StagedModelDataHandler<?> stagedModelDataHandler :
				stagedModelDataHandlers) {

			unregister(stagedModelDataHandler);
		}
	}

	/**
	 * Unregisters the staged model data handler.
	 *
	 * @param stagedModelDataHandler the staged model data handler to unregister
	 */
	public static void unregister(
		StagedModelDataHandler<?> stagedModelDataHandler) {

		_stagedModelDataHandlerRegistryUtil._unregister(stagedModelDataHandler);
	}

	private StagedModelDataHandlerRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			(Class<StagedModelDataHandler<?>>)
				(Class<?>)StagedModelDataHandler.class,
			new StagedModelDataHandlerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private StagedModelDataHandler<?> _getStagedModelDataHandler(
		String className) {

		return _stagedModelDataHandlers.get(className);
	}

	private List<StagedModelDataHandler<?>> _getStagedModelDataHandlers() {
		Collection<StagedModelDataHandler<?>> values =
			_stagedModelDataHandlers.values();

		return ListUtil.fromCollection(values);
	}

	private void _register(StagedModelDataHandler<?> stagedModelDataHandler) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<StagedModelDataHandler<?>> serviceRegistration =
			registry.registerService(
				(Class<StagedModelDataHandler<?>>)
					(Class<?>)StagedModelDataHandler.class,
				stagedModelDataHandler);

		_serviceRegistrations.put(stagedModelDataHandler, serviceRegistration);
	}

	private void _unregister(StagedModelDataHandler<?> stagedModelDataHandler) {
		ServiceRegistration<StagedModelDataHandler<?>> serviceRegistration =
			_serviceRegistrations.remove(stagedModelDataHandler);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final StagedModelDataHandlerRegistryUtil
		_stagedModelDataHandlerRegistryUtil =
			new StagedModelDataHandlerRegistryUtil();

	private final ServiceRegistrationMap<StagedModelDataHandler<?>>
		_serviceRegistrations = new ServiceRegistrationMapImpl<>();
	private final ServiceTracker
		<StagedModelDataHandler<?>, StagedModelDataHandler<?>> _serviceTracker;
	private final Map<String, StagedModelDataHandler<?>>
		_stagedModelDataHandlers = new ConcurrentHashMap<>();

	private class StagedModelDataHandlerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<StagedModelDataHandler<?>, StagedModelDataHandler<?>> {

		@Override
		public StagedModelDataHandler<?> addingService(
			ServiceReference<StagedModelDataHandler<?>> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			StagedModelDataHandler<?> stagedModelDataHandler =
				registry.getService(serviceReference);

			for (String className : stagedModelDataHandler.getClassNames()) {
				_stagedModelDataHandlers.put(className, stagedModelDataHandler);
			}

			return stagedModelDataHandler;
		}

		@Override
		public void modifiedService(
			ServiceReference<StagedModelDataHandler<?>> serviceReference,
			StagedModelDataHandler<?> stagedModelDataHandler) {
		}

		@Override
		public void removedService(
			ServiceReference<StagedModelDataHandler<?>> serviceReference,
			StagedModelDataHandler<?> stagedModelDataHandler) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			for (String className : stagedModelDataHandler.getClassNames()) {
				_stagedModelDataHandlers.remove(className);
			}
		}

	}

}