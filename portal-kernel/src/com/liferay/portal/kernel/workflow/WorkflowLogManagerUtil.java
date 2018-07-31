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

import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class WorkflowLogManagerUtil {

	public static int getWorkflowLogCountByWorkflowInstance(
			long companyId, long workflowInstanceId, List<Integer> logTypes)
		throws WorkflowException {

		return getWorkflowLogManager().getWorkflowLogCountByWorkflowInstance(
			companyId, workflowInstanceId, logTypes);
	}

	public static int getWorkflowLogCountByWorkflowTask(
			long companyId, long workflowTaskId, List<Integer> logTypes)
		throws WorkflowException {

		return getWorkflowLogManager().getWorkflowLogCountByWorkflowTask(
			companyId, workflowTaskId, logTypes);
	}

	public static WorkflowLogManager getWorkflowLogManager() {
		return _workflowLogManager;
	}

	public static List<WorkflowLog> getWorkflowLogsByWorkflowInstance(
			long companyId, long workflowInstanceId, List<Integer> logTypes,
			int start, int end,
			OrderByComparator<WorkflowLog> orderByComparator)
		throws WorkflowException {

		return getWorkflowLogManager().getWorkflowLogsByWorkflowInstance(
			companyId, workflowInstanceId, logTypes, start, end,
			orderByComparator);
	}

	public static List<WorkflowLog> getWorkflowLogsByWorkflowTask(
			long companyId, long workflowTaskId, List<Integer> logTypes,
			int start, int end,
			OrderByComparator<WorkflowLog> orderByComparator)
		throws WorkflowException {

		return getWorkflowLogManager().getWorkflowLogsByWorkflowTask(
			companyId, workflowTaskId, logTypes, start, end, orderByComparator);
	}

	public void setWorkflowLogManager(WorkflowLogManager workflowLogManager) {
		_workflowLogManager = workflowLogManager;
	}

	private static WorkflowLogManager _workflowLogManager;

}