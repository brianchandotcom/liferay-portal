/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.internal.node.util;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.workflow.kaleo.runtime.node.NodeExecutor;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Michael C. Han
 */
public class NodeExecutorRegistryUtil {

	public static NodeExecutor getNodeExecutor(String nodeTypeString) {
		return _serviceTrackerMap.getService(nodeTypeString);
	}

	private static final ServiceTrackerMap<String, NodeExecutor>
		_serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(NodeExecutorRegistryUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, NodeExecutor.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(nodeExecutor, emitter) -> emitter.emit(
					String.valueOf(nodeExecutor.getNodeType()))));
	}

}