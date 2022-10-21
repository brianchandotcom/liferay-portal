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

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class WorkflowEngineManagerUtil {

	public static String getKey() {
		return _workflowEngineManager.getKey();
	}

	public static String getName() {
		return _workflowEngineManager.getName();
	}

	public static Map<String, Object> getOptionalAttributes() {
		return _workflowEngineManager.getOptionalAttributes();
	}

	public static String getVersion() {
		return _workflowEngineManager.getVersion();
	}

	public static boolean isDeployed() {
		return _workflowEngineManager.isDeployed();
	}

	private static volatile WorkflowEngineManager _workflowEngineManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			WorkflowEngineManager.class, WorkflowEngineManagerUtil.class,
			"_workflowEngineManager", true);

}