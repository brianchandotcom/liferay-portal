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

package com.liferay.portal.dependency.manager.component.executor.factory.internal.activator;

import com.liferay.portal.dependency.manager.component.executor.factory.internal.ComponentExecutorFactoryImpl;
import com.liferay.portal.dependency.manager.component.executor.factory.internal.DependencyManagerSyncImpl;
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSync;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
			bundleContext.getProperty("dependency.manager.thread.pool.enabled"),
			true);

		if (!threadPoolEnabled) {
			return;
		}

		long syncTimeout = GetterUtil.getInteger(
			bundleContext.getProperty("dependency.manager.sync.timeout"), 60);

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			0, 1, 1, TimeUnit.MINUTES, new LinkedBlockingDeque<>(),
			new NamedThreadFactory(
				"Portal Dependency Manager Component Executor-",
				Thread.NORM_PRIORITY,
				ComponentExecutorFactory.class.getClassLoader()));

		threadPoolExecutor.allowCoreThreadTimeOut(true);

		_serviceRegistration = bundleContext.registerService(
			ComponentExecutorFactory.class,
			new ComponentExecutorFactoryImpl(threadPoolExecutor), null);

		_dependencyManagerSyncServiceRegistration =
			bundleContext.registerService(
				DependencyManagerSync.class,
				new DependencyManagerSyncImpl(
					threadPoolExecutor, _serviceRegistration, syncTimeout),
				null);
	}

	@Override
	public void stop(BundleContext bundleContext) {
		if (_serviceRegistration == null) {
			return;
		}

		try {
			_serviceRegistration.unregister();
		}
		catch (IllegalStateException illegalStateException) {
			if (_log.isDebugEnabled()) {
				_log.debug(illegalStateException, illegalStateException);
			}

			// Concurrent unregister, no need to do anything.

		}

		_dependencyManagerSyncServiceRegistration.unregister();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ComponentExecutorFactoryBundleActivator.class);

	private ServiceRegistration<DependencyManagerSync>
		_dependencyManagerSyncServiceRegistration;
	private ServiceRegistration<ComponentExecutorFactory> _serviceRegistration;

}