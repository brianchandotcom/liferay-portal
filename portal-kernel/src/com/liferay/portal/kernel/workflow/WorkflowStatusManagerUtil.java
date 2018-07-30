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

import java.io.Serializable;

import java.util.Map;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class WorkflowStatusManagerUtil {

	public static WorkflowStatusManager getWorkflowStatusManager() {
		return _workflowStatusManager;
	}

	public static void updateStatus(
			int status, Map<String, Serializable> workflowContext)
		throws WorkflowException {

		getWorkflowStatusManager().updateStatus(status, workflowContext);
	}

	public void setWorkflowStatusManager(
		WorkflowStatusManager workflowStatusManager) {

		_workflowStatusManager = workflowStatusManager;
	}

	private static WorkflowStatusManager _workflowStatusManager;

}