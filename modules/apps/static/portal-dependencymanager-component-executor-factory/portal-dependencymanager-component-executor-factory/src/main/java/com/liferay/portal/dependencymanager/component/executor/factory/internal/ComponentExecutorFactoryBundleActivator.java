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

package com.liferay.portal.dependencymanager.component.executor.factory.internal;

import com.liferay.portal.kernel.dependency.DependencyManagerSync;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.NamedThreadFactory;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.felix.dm.ComponentExecutorFactory;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Shuyang Zhou
 */
public class ComponentExecutorFactoryBundleActivator
	implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		boolean threadPoolEnabled = GetterUtil.getBoolean(
			bundleContext.getProperty("dependencymanager.threadpool.enabled"),
			true);

		if (!threadPoolEnabled) {
			return;
		}

		long syncTimeout = GetterUtil.getInteger(
			bundleContext.getProperty("dependencymanager.sync.timeout"), 60);

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			0, 1, 1, TimeUnit.MINUTES, new LinkedBlockingDeque<>(),
			new NamedThreadFactory(
				"Portal Dependencymanager Component Executor-",
				Thread.NORM_PRIORITY,
				ComponentExecutorFactory.class.getClassLoader()));

		threadPoolExecutor.allowCoreThreadTimeOut(true);

		_serviceRegistration = bundleContext.registerService(
			ComponentExecutorFactory.class,
			new ComponentExecutorFactoryImpl(threadPoolExecutor), null);

		DependencyManagerSyncImpl dependencyManagerSyncImpl =
			new DependencyManagerSyncImpl(
				threadPoolExecutor, _serviceRegistration, syncTimeout);

		_dependencyManagerSyncServiceRegistration =
			bundleContext.registerService(
				DependencyManagerSync.class, dependencyManagerSyncImpl, null);

		dependencyManagerSyncImpl.setDependencyManagerSyncServiceRegistration(
			_dependencyManagerSyncServiceRegistration);
	}

	@Override
	public void stop(BundleContext bundleContext) {
		if (_serviceRegistration == null) {
			return;
		}

		try {
			_serviceRegistration.unregister();
		}
		catch (IllegalStateException ise) {

			// Concurrent unregister, no need to do anything.

		}

		try {
			_dependencyManagerSyncServiceRegistration.unregister();
		}
		catch (IllegalStateException ise) {

			// Concurrent unregister, no need to do anything.

		}
	}

	private ServiceRegistration<DependencyManagerSync>
		_dependencyManagerSyncServiceRegistration;
	private ServiceRegistration<ComponentExecutorFactory> _serviceRegistration;

}