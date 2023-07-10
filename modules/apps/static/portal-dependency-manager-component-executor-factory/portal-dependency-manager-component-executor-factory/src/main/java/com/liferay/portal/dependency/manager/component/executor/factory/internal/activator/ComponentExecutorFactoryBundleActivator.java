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
import com.liferay.portal.kernel.concurrent.SystemExecutorServiceUtil;
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSync;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.tools.DBUpgrader;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;

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
		BlockingQueue<Future<Void>> blockingQueue = new LinkedBlockingQueue<>();

		if (GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.DEPENDENCY_MANAGER_THREAD_POOL_ENABLED),
				true) &&
			!DBUpgrader.isUpgradeDatabaseAutoRunEnabled()) {

			ExecutorService executorService =
				SystemExecutorServiceUtil.getExecutorService();

			_serviceRegistration = bundleContext.registerService(
				ComponentExecutorFactory.class,
				new ComponentExecutorFactoryImpl(
					runnable -> {
						FutureTask<Void> futureTask = new FutureTask<>(
							runnable, null);

						blockingQueue.add(futureTask);

						executorService.submit(futureTask);
					}),
				null);
		}

		long syncTimeout = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.DEPENDENCY_MANAGER_SYNC_TIMEOUT), 60);

		_dependencyManagerSyncServiceRegistration =
			bundleContext.registerService(
				DependencyManagerSync.class,
				new DependencyManagerSyncImpl(
					blockingQueue, _serviceRegistration, syncTimeout),
				null);
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_dependencyManagerSyncServiceRegistration.unregister();

		if (_serviceRegistration != null) {
			try {
				_serviceRegistration.unregister();
			}
			catch (IllegalStateException illegalStateException) {
				if (_log.isDebugEnabled()) {
					_log.debug(illegalStateException);
				}

				// Concurrent unregister, no need to do anything.

			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ComponentExecutorFactoryBundleActivator.class);

	private ServiceRegistration<DependencyManagerSync>
		_dependencyManagerSyncServiceRegistration;
	private ServiceRegistration<ComponentExecutorFactory> _serviceRegistration;

}