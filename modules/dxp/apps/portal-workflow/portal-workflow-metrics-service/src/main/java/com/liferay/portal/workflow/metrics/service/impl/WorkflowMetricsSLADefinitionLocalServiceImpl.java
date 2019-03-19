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

package com.liferay.portal.workflow.metrics.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionDuplicateNameException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionDurationException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionNameException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.service.base.WorkflowMetricsSLADefinitionLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class WorkflowMetricsSLADefinitionLocalServiceImpl
	extends WorkflowMetricsSLADefinitionLocalServiceBaseImpl {

	public WorkflowMetricsSLADefinition addWorkflowMetricsSLADefinition(
			String name, String description, long duration, long processId,
			String[] pauseNodeNames, String[] startNodeNames,
			String[] stopNodeNames, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getGuestOrUserId());
		Date now = new Date();

		validate(
			0, serviceContext.getCompanyId(), processId, name, duration,
			pauseNodeNames, startNodeNames, stopNodeNames);

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			workflowMetricsSLADefinitionPersistence.create(
				counterLocalService.increment());

		workflowMetricsSLADefinition.setGroupId(
			serviceContext.getScopeGroupId());
		workflowMetricsSLADefinition.setCompanyId(
			serviceContext.getCompanyId());
		workflowMetricsSLADefinition.setUserId(user.getUserId());
		workflowMetricsSLADefinition.setUserName(user.getFullName());
		workflowMetricsSLADefinition.setCreateDate(now);
		workflowMetricsSLADefinition.setModifiedDate(now);
		workflowMetricsSLADefinition.setName(name);
		workflowMetricsSLADefinition.setDescription(description);
		workflowMetricsSLADefinition.setDuration(duration);
		workflowMetricsSLADefinition.setProcessId(processId);
		workflowMetricsSLADefinition.setPauseNodeNames(
			StringUtil.merge(pauseNodeNames));
		workflowMetricsSLADefinition.setStartNodeNames(
			StringUtil.merge(startNodeNames));
		workflowMetricsSLADefinition.setStopNodeNames(
			StringUtil.merge(stopNodeNames));

		workflowMetricsSLADefinitionPersistence.update(
			workflowMetricsSLADefinition);

		return workflowMetricsSLADefinition;
	}

	public List<WorkflowMetricsSLADefinition> getWorkflowMetricsSLADefinitions(
		long companyId, long processId) {

		return workflowMetricsSLADefinitionPersistence.findByC_P(
			companyId, processId);
	}

	public List<WorkflowMetricsSLADefinition> getWorkflowMetricsSLADefinitions(
		long companyId, long processId, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return workflowMetricsSLADefinitionPersistence.findByC_P(
			companyId, processId, start, end, orderByComparator);
	}

	public int getWorkflowMetricsSLADefinitionsCount(
		long companyId, long processId) {

		return workflowMetricsSLADefinitionPersistence.countByC_P(
			companyId, processId);
	}

	@Override
	public WorkflowMetricsSLADefinition updateWorkflowMetricsSLADefinition(
			long workflowMetricsSLADefinitiontId, String name,
			String description, long duration, String[] pauseNodeNames,
			String[] startNodeNames, String[] stopNodeNames,
			ServiceContext serviceContext)
		throws PortalException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			workflowMetricsSLADefinitionPersistence.findByPrimaryKey(
				workflowMetricsSLADefinitiontId);

		validate(
			workflowMetricsSLADefinition.getWorkflowMetricsSLADefinitionId(),
			workflowMetricsSLADefinition.getCompanyId(),
			workflowMetricsSLADefinition.getProcessId(), name, duration,
			pauseNodeNames, startNodeNames, stopNodeNames);

		workflowMetricsSLADefinition.setModifiedDate(new Date());
		workflowMetricsSLADefinition.setName(name);
		workflowMetricsSLADefinition.setDescription(description);
		workflowMetricsSLADefinition.setDuration(duration);
		workflowMetricsSLADefinition.setPauseNodeNames(
			StringUtil.merge(pauseNodeNames));
		workflowMetricsSLADefinition.setStartNodeNames(
			StringUtil.merge(startNodeNames));
		workflowMetricsSLADefinition.setStopNodeNames(
			StringUtil.merge(stopNodeNames));

		return workflowMetricsSLADefinitionPersistence.update(
			workflowMetricsSLADefinition);
	}

	protected void validate(
			long workflowMetricsSLADefinitionId, long companyId, long processId,
			String name, long duration, String[] pauseNodeNames,
			String[] startNodeNames, String[] stopNodeNames)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new WorkflowMetricsSLADefinitionNameException();
		}

		if (duration <= 0) {
			throw new WorkflowMetricsSLADefinitionDurationException();
		}

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			workflowMetricsSLADefinitionPersistence.fetchByC_N_P(
				companyId, name, processId);

		if ((workflowMetricsSLADefinition != null) &&
			(workflowMetricsSLADefinitionId !=
				workflowMetricsSLADefinition.
					getWorkflowMetricsSLADefinitionId())) {

			throw new WorkflowMetricsSLADefinitionDuplicateNameException();
		}
	}

}