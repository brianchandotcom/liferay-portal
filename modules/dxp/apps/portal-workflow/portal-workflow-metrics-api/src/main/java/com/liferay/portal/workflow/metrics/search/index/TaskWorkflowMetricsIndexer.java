/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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