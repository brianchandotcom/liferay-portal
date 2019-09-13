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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.ServiceRegistration;

/**
 * @author Shuyang Zhou
 */
public class DependencyManagerSyncImpl implements DependencyManagerSync {

	public DependencyManagerSyncImpl(
		ExecutorService executorService,
		ServiceRegistration<?> componentExecutorFactoryRegistration,
		long syncTimeout) {

		_executorService = executorService;
		_componentExecutorFactoryRegistration =
			componentExecutorFactoryRegistration;
		_syncTimeout = syncTimeout;
	}

	public void setDependencyManagerSyncServiceRegistration(
		ServiceRegistration<?> dependencyManagerSyncServiceRegistration) {

		_dependencyManagerSyncServiceRegistration =
			dependencyManagerSyncServiceRegistration;
	}

	@Override
	public void sync() {
		ServiceRegistration<?> dependencyManagerSyncServiceRegistration =
			_dependencyManagerSyncServiceRegistration;

		if (dependencyManagerSyncServiceRegistration != null) {
			try {
				dependencyManagerSyncServiceRegistration.unregister();
			}
			catch (IllegalStateException ise) {

				// Concurrent unregister, no need to do anything.

			}

			_dependencyManagerSyncServiceRegistration = null;
		}

		try {
			_componentExecutorFactoryRegistration.unregister();
		}
		catch (IllegalStateException ise) {

			// Concurrent unregister, no need to do anything.

		}

		_executorService.shutdown();

		try {
			if (!_executorService.awaitTermination(
					_syncTimeout, TimeUnit.SECONDS)) {

				_executorService.shutdownNow();

				if (_log.isWarnEnabled()) {
					_log.warn(
						"DependencyManage sync timeout after waited " +
							_syncTimeout + "s");
				}
			}
		}
		catch (InterruptedException ie) {
			_log.error("DependencyManage sync got interrupted", ie);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DependencyManagerSyncImpl.class);

	private final ServiceRegistration<?> _componentExecutorFactoryRegistration;
	private volatile ServiceRegistration<?>
		_dependencyManagerSyncServiceRegistration;
	private final ExecutorService _executorService;
	private final long _syncTimeout;

}