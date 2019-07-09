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

package com.liferay.portal.cluster.multiple.internal;

import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMasterTokenTransitionListener;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Shuyang Zhou
 */
@Component(enabled = false, immediate = true, service = {})
public class ClusterMasterTokenTransitionListenerTracker {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = new ServiceTracker<>(
			bundleContext, ClusterMasterTokenTransitionListener.class,
			new ServiceTrackerCustomizer
				<ClusterMasterTokenTransitionListener,
				 ClusterMasterTokenTransitionListener>() {

				@Override
				public ClusterMasterTokenTransitionListener addingService(
					ServiceReference<ClusterMasterTokenTransitionListener>
						serviceReference) {

					ClusterMasterTokenTransitionListener
						clusterMasterTokenTransitionListener =
							bundleContext.getService(serviceReference);

					_clusterMasterExecutor.
						addClusterMasterTokenTransitionListener(
							clusterMasterTokenTransitionListener);

					return clusterMasterTokenTransitionListener;
				}

				@Override
				public void modifiedService(
					ServiceReference<ClusterMasterTokenTransitionListener>
						serviceReference,
					ClusterMasterTokenTransitionListener
						clusterMasterTokenTransitionListener) {
				}

				@Override
				public void removedService(
					ServiceReference<ClusterMasterTokenTransitionListener>
						serviceReference,
					ClusterMasterTokenTransitionListener
						clusterMasterTokenTransitionListener) {

					_clusterMasterExecutor.
						removeClusterMasterTokenTransitionListener(
							clusterMasterTokenTransitionListener);

					bundleContext.ungetService(serviceReference);
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	@Reference
	private ClusterMasterExecutor _clusterMasterExecutor;

	private ServiceTracker
		<ClusterMasterTokenTransitionListener,
		 ClusterMasterTokenTransitionListener> _serviceTracker;

}