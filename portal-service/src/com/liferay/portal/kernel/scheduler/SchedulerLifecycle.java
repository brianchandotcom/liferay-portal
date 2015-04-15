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

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

/**
 * @author Tina Tian
 */
public class SchedulerLifecycle extends BasePortalLifecycle {

	public SchedulerLifecycle() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			ClusterMasterExecutor.class,
			new ClusterMasterExecutorServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	protected synchronized void clusteredInit() throws Exception {
		if ((_clusterMasterExecutor == null) || !_portalInitialized) {
			return;
		}

		_clusterMasterExecutor.initialize();

		SchedulerEngineHelperUtil.start();
	}

	@Override
	protected void doPortalDestroy() throws Exception {
		_serviceTracker.close();
	}

	@Override
	protected void doPortalInit() throws Exception {
		_portalInitialized = true;

		if (!SchedulerEngineHelperUtil.isClusteredSchedulerEngine()) {
			SchedulerEngineHelperUtil.start();
		}
		else {
			clusteredInit();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SchedulerLifecycle.class);

	private volatile ClusterMasterExecutor _clusterMasterExecutor;
	private volatile boolean _portalInitialized;
	private final ServiceTracker<ClusterMasterExecutor, ClusterMasterExecutor>
		_serviceTracker;

	private class ClusterMasterExecutorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ClusterMasterExecutor, ClusterMasterExecutor> {

		@Override
		public ClusterMasterExecutor addingService(
			ServiceReference<ClusterMasterExecutor> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			_clusterMasterExecutor = registry.getService(serviceReference);

			try {
				clusteredInit();
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to start scheduling engine", e);
				}
			}

			return _clusterMasterExecutor;
		}

		@Override
		public void modifiedService(
			ServiceReference<ClusterMasterExecutor> serviceReference,
			ClusterMasterExecutor clusterMasterExecutor) {
		}

		@Override
		public void removedService(
			ServiceReference<ClusterMasterExecutor> serviceReference,
			ClusterMasterExecutor clusterMasterExecutor) {

			_clusterMasterExecutor = null;
		}

	}

}