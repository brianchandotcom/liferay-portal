/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cluster;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import org.osgi.framework.BundleContext;

/**
 * @author Tina Tian
 */
public class ClusterInvokeAcceptorUtil {

	public static ClusterInvokeAcceptor getClusterInvokeAcceptor(
		String clusterInvokeAcceptorName) {

		return _clusterInvokeAcceptor.getService(clusterInvokeAcceptorName);
	}

	private ClusterInvokeAcceptorUtil() {
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private static final ServiceTrackerMap<String, ClusterInvokeAcceptor>
		_clusterInvokeAcceptor = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, ClusterInvokeAcceptor.class, null,
			(serviceReference, emitter) -> {
				ClusterInvokeAcceptor clusterInvokeAcceptor =
					_bundleContext.getService(serviceReference);

				Class<?> clazz = clusterInvokeAcceptor.getClass();

				emitter.emit(clazz.getName());

				_bundleContext.ungetService(serviceReference);
			});

}