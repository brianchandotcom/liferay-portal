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

package com.liferay.headless.admin.workflow.resource.v1_0.test.util;

import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.client.pagination.Page;
import com.liferay.headless.admin.workflow.client.pagination.Pagination;
import com.liferay.headless.admin.workflow.client.resource.v1_0.WorkflowTaskResource;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;

/**
 * @author Rafael Praxedes
 */
public class WorkflowTaskTestUtil {

	public static WorkflowTask getWorkflowTask(long workflowInstanceId)
		throws Exception {

		List<WorkflowTask> workflowTasks = getWorkflowTasks(workflowInstanceId);

		return workflowTasks.get(workflowTasks.size() - 1);
	}

	public static List<WorkflowTask> getWorkflowTasks(long workflowInstanceId)
		throws Exception {

		WorkflowTaskResource.Builder workflowTaskResourceBuilder =
			WorkflowTaskResource.builder();

		WorkflowTaskResource workflowTaskResource =
			workflowTaskResourceBuilder.authentication(
				"test@liferay.com", "test"
			).locale(
				LocaleUtil.getDefault()
			).build();

		Page<WorkflowTask> page =
			workflowTaskResource.getWorkflowInstanceWorkflowTasksPage(
				workflowInstanceId, false, Pagination.of(-1, -1));

		return (List<WorkflowTask>)page.getItems();
	}

}