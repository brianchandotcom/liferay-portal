/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			long workflowTaskId, long workflowTaskFormId, String formValues,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws WorkflowException;

	public WorkflowTask completeWorkflowTask(
			long workflowTaskId, long workflowTaskFormId, String formValues,
			String transitionName, Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws WorkflowException;

	public WorkflowTask completeWorkflowTask(
			long workflowTaskId, String transitionName, String comment,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws WorkflowException;

	public List<String> getWorkflowTaskFormDefinitions(
			long workflowTaskId, ServiceContext serviceContext)
		throws WorkflowException;

	public WorkflowTask updateDueDate(
			long workflowTaskId, String comment, Date dueDate,
			ServiceContext serviceContext)
		throws WorkflowException;

}