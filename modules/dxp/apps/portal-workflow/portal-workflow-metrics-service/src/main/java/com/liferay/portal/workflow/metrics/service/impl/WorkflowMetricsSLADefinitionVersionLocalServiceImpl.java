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

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.metrics.exception.NoSuchSLADefinitionVersionException;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;
import com.liferay.portal.workflow.metrics.service.base.WorkflowMetricsSLADefinitionVersionLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion",
	service = AopService.class
)
public class WorkflowMetricsSLADefinitionVersionLocalServiceImpl
	extends WorkflowMetricsSLADefinitionVersionLocalServiceBaseImpl {

	@Override
	public WorkflowMetricsSLADefinitionVersion
			getWorkflowMetricsSLADefinitionVersion(
				long workflowMetricsSLADefinitionId, String version)
		throws NoSuchSLADefinitionVersionException {

		return workflowMetricsSLADefinitionVersionPersistence.findByV_WMSLAD(
			version, workflowMetricsSLADefinitionId);
	}

	@Override
	public List<WorkflowMetricsSLADefinitionVersion>
		getWorkflowMetricsSLADefinitionVersions(
			long workflowMetricsSLADefinitionId) {

		return workflowMetricsSLADefinitionVersionPersistence.
			findByWorkflowMetricsSLADefinitionId(
				workflowMetricsSLADefinitionId);
	}

	@Override
	public List<WorkflowMetricsSLADefinitionVersion>
		getWorkflowMetricsSLADefinitionVersions(
			long companyId, Date createDate, Long processId, int status) {

		return workflowMetricsSLADefinitionVersionFinder.findByC_CD_P_S(
			companyId, createDate, processId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	@Override
	public List<WorkflowMetricsSLADefinitionVersion>
		getWorkflowMetricsSLADefinitionVersions(
			long workflowMetricsSLADefinitionId,
			OrderByComparator<WorkflowMetricsSLADefinitionVersion>
				orderByComparator) {

		return workflowMetricsSLADefinitionVersionPersistence.
			findByWorkflowMetricsSLADefinitionId(
				workflowMetricsSLADefinitionId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, orderByComparator);
	}

}