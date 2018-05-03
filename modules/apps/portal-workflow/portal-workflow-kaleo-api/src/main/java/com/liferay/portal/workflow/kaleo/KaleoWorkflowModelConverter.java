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

package com.liferay.portal.workflow.kaleo;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskAssignee;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public interface KaleoWorkflowModelConverter {

	public List<WorkflowTaskAssignee> getWorkflowTaskAssignees(
		KaleoTaskInstanceToken kaleoTaskInstanceToken);

	public WorkflowDefinition toWorkflowDefinition(
		KaleoDefinition kaleoDefinition);

	public WorkflowDefinition toWorkflowDefinition(
			KaleoDefinitionVersion kaleoDefinitionVersion)
		throws PortalException;

	public WorkflowInstance toWorkflowInstance(
			KaleoInstance kaleoInstance, KaleoInstanceToken kaleoInstanceToken)
		throws PortalException;

	public WorkflowInstance toWorkflowInstance(
			KaleoInstance kaleoInstance, KaleoInstanceToken kaleoInstanceToken,
			Map<String, Serializable> workflowContext)
		throws PortalException;

	public WorkflowLog toWorkflowLog(KaleoLog kaleoLog);

	public WorkflowTask toWorkflowTask(
			KaleoTaskInstanceToken kaleoTaskInstanceToken,
			Map<String, Serializable> workflowContext)
		throws PortalException;

}