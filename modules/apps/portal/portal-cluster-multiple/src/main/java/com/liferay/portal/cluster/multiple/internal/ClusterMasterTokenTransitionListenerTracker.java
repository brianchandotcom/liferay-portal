/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(enabled = false, service = {})
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

		_serviceTracker.open();
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