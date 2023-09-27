/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.search.index;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.workflow.metrics.model.AddTaskRequest;
import com.liferay.portal.workflow.metrics.model.CompleteTaskRequest;
import com.liferay.portal.workflow.metrics.model.DeleteTaskRequest;
import com.liferay.portal.workflow.metrics.model.UpdateTaskRequest;

/**
 * @author Rafael Praxedes
 */
public interface TaskWorkflowMetricsIndexer {

	public Document addTask(AddTaskRequest addTaskRequest);

	public Document completeTask(CompleteTaskRequest completeTaskRequest);

	public void deleteTask(DeleteTaskRequest deleteTaskRequest);

	public Document updateTask(UpdateTaskRequest updateTaskRequest);

}