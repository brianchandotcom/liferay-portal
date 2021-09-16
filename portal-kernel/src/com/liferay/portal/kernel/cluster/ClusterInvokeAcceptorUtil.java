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