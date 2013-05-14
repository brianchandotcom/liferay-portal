/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.workflow;

import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowLogManager;

import java.util.List;

/**
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class WorkflowLogManagerProxyBean
	extends BaseProxyBean implements WorkflowLogManager {

	@Override
	public int getWorkflowLogCountByWorkflowInstance(
		long companyId, long workflowInstanceId, List<Integer> logTypes) {

		throw new UnsupportedOperationException();
	}

	@Override
	public int getWorkflowLogCountByWorkflowTask(
		long companyId, long workflowTaskId, List<Integer> logTypes) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<WorkflowLog> getWorkflowLogsByWorkflowInstance(
		long companyId, long workflowInstanceId, List<Integer> logTypes,
		int start, int end, OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<WorkflowLog> getWorkflowLogsByWorkflowTask(
		long companyId, long workflowTaskId, List<Integer> logTypes, int start,
		int end, OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

}