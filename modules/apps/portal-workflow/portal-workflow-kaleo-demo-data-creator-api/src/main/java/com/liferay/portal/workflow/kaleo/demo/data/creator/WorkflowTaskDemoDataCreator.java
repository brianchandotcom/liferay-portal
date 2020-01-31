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

package com.liferay.portal.workflow.kaleo.demo.data.creator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowTask;

import java.util.Date;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Inácio Nery
 */
@ProviderType
public interface WorkflowTaskDemoDataCreator {

	public void completeWorkflowTask(
			long companyId, String transitionName, long userId,
			long workflowTaskId)
		throws Exception;

	public List<String> getNextTransitionNames(
			long companyId, long workflowTaskId)
		throws WorkflowException;

	public WorkflowTask getWorkflowTask(long companyId, long workflowInstanceId)
		throws Exception;

	public void updateCompletionDate(long workflowTaskId, Date completionDate)
		throws Exception;

	public void updateCreateDate(long workflowTaskId, Date createDate)
		throws PortalException;

}