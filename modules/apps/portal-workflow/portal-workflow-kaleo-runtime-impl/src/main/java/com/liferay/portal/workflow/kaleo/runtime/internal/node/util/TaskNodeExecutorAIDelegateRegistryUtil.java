/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.internal.node.util;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.workflow.kaleo.runtime.node.TaskNodeExecutorAIDelegate;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Feliphe Marinho
 */
public class TaskNodeExecutorAIDelegateRegistryUtil {

	public static TaskNodeExecutorAIDelegate getTaskNodeExecutorDelegate(
		String key) {

		return _serviceTrackerMap.getService(key);
	}

	private static final ServiceTrackerMap<String, TaskNodeExecutorAIDelegate>
		_serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			TaskNodeExecutorAIDelegateRegistryUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, TaskNodeExecutorAIDelegate.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(taskNodeExecutorAIDelegate, emitter) -> emitter.emit(
					taskNodeExecutorAIDelegate.getKey())));
	}

}