/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			long companyId, long userId, long workflowTaskId,
			String transitionName)
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