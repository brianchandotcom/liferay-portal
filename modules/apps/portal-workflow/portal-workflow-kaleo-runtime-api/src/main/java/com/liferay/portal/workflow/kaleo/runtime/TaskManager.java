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

package com.liferay.portal.workflow.kaleo.runtime;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowTask;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface TaskManager {

	public WorkflowTask assignWorkflowTaskToRole(
			long workflowTaskId, long roleId, String comment, Date dueDate,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws WorkflowException;

	public WorkflowTask assignWorkflowTaskToUser(
			long workflowTaskId, long assigneeUserId, String comment,
			Date dueDate, Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws WorkflowException;

	public WorkflowTask completeWorkflowTask(
			long workflowTaskInstanceId, long workflowTaskFormId,
			String formValues, Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws WorkflowException;

	public WorkflowTask completeWorkflowTask(
			long workflowTaskInstanceId, long workflowTaskFormId,
			String formValues, String transitionName,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws WorkflowException;

	public WorkflowTask completeWorkflowTask(
			long workflowTaskId, String transitionName, String comment,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws WorkflowException;

	public List<String> getWorkflowTaskFormDefinitions(
			long workflowTaskInstanceId, ServiceContext serviceContext)
		throws WorkflowException;

	public WorkflowTask updateDueDate(
			long workflowTaskId, String comment, Date dueDate,
			ServiceContext serviceContext)
		throws WorkflowException;

}