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

package com.liferay.portal.background.task.internal;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutorRegistry;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = BackgroundTaskExecutorRegistry.class)
public class BackgroundTaskExecutorRegistryImpl
	implements BackgroundTaskExecutorRegistry {

	@Override
	public synchronized BackgroundTaskExecutor getBackgroundTaskExecutor(
		String className) {

		return _backgroundTaskExecutors.get(className);
	}

	@Override
	public synchronized void registerBackgroundTaskExecutor(
		String backgroundTaskExecutorClassName,
		BackgroundTaskExecutor backgroundTaskExecutor) {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			BACKGROUND_TASK_EXECUTOR_REGISTRY_KEY, backgroundTaskExecutor);

		ServiceRegistration<BackgroundTaskExecutor> serviceRegistration =
			_bundleContext.registerService(
				BackgroundTaskExecutor.class, backgroundTaskExecutor,
				properties);

		_backgroundTaskExecutorRegistrations.put(
			backgroundTaskExecutorClassName, serviceRegistration);
	}

	@Override
	public synchronized void unregisterBackgroundTaskExecutor(
		String backgroundTaskExecutorClassName) {

		if (!_backgroundTaskExecutorRegistrations.containsKey(
				backgroundTaskExecutorClassName)) {

			return;
		}

		ServiceRegistration<BackgroundTaskExecutor> serviceRegistration =
			_backgroundTaskExecutorRegistrations.get(
				backgroundTaskExecutorClassName);

		serviceRegistration.unregister();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeBackgroundTaskExecutor"
	)
	protected synchronized void addBackgroundTaskExecutor(
		BackgroundTaskExecutor backgroundTaskExecutor,
		Map<String, Object> properties) {

		String backgroundTaskExecutorClassName = (String)properties.get(
			BACKGROUND_TASK_EXECUTOR_REGISTRY_KEY);

		if (Validator.isNull(backgroundTaskExecutorClassName)) {
			throw new IllegalArgumentException(
				"Property \"" + BACKGROUND_TASK_EXECUTOR_REGISTRY_KEY +
					"\" is not set for " + backgroundTaskExecutor);
		}

		_backgroundTaskExecutors.put(
			backgroundTaskExecutorClassName, backgroundTaskExecutor);
	}

	@Deactivate
	protected synchronized void deactivate() {
		_bundleContext = null;

		for (ServiceRegistration<BackgroundTaskExecutor> serviceRegistration :
				_backgroundTaskExecutorRegistrations.values()) {

			serviceRegistration.unregister();
		}

		_backgroundTaskExecutorRegistrations.clear();
	}

	protected synchronized void removeBackgroundTaskExecutor(
		BackgroundTaskExecutor backgroundTaskExecutor,
		Map<String, Object> properties) {

		String className = (String)properties.get(
			BACKGROUND_TASK_EXECUTOR_REGISTRY_KEY);

		if (Validator.isNull(className)) {
			return;
		}

		_backgroundTaskExecutors.remove(className);
	}

	private final Map<String, ServiceRegistration<BackgroundTaskExecutor>>
		_backgroundTaskExecutorRegistrations = new HashMap<>();
	private final Map<String, BackgroundTaskExecutor> _backgroundTaskExecutors =
		new HashMap<>();
	private BundleContext _bundleContext;

}