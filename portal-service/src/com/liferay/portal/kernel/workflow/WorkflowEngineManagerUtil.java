/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
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

	public static WorkflowEngineManager getWorkflowEngineManager() {
		return _workflowEngineManager;
	}

	public static boolean isDeployed() {
		if (MessageBusUtil.hasMessageListener(
				DestinationNames.WORKFLOW_ENGINE)) {

			return true;
		}
		else {
			return false;
		}
	}

	public void setWorkflowEngineManager(
		WorkflowEngineManager workflowEngineManager) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_workflowEngineManager = workflowEngineManager;
	}

	private static WorkflowEngineManager _workflowEngineManager;

}