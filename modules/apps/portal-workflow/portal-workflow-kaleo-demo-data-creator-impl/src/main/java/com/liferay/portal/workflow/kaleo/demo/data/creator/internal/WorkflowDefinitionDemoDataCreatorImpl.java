/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.demo.data.creator.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.workflow.kaleo.demo.data.creator.WorkflowDefinitionDemoDataCreator;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(service = WorkflowDefinitionDemoDataCreator.class)
public class WorkflowDefinitionDemoDataCreatorImpl
	implements WorkflowDefinitionDemoDataCreator {

	@Override
	public WorkflowDefinition create(
			long companyId, long userId, Date createDate)
		throws PortalException {

		String content = StringUtil.read(
			WorkflowDefinitionDemoDataCreatorImpl.class,
			"dependencies/auto-insurance-application-workflow-definition.xml");

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.deployWorkflowDefinition(
				companyId, userId, "Auto Insurance Application",
				"Auto Insurance Application", content.getBytes());

		_workflowDefinitions.add(workflowDefinition);

		if (createDate != null) {
			KaleoDefinition kaleoDefinition =
				_kaleoDefinitionLocalService.getKaleoDefinition(
					workflowDefinition.getWorkflowDefinitionId());

			kaleoDefinition.setCreateDate(createDate);

			_kaleoDefinitionLocalService.updateKaleoDefinition(kaleoDefinition);
		}

		return workflowDefinition;
	}

	@Override
	public void delete() throws PortalException {
		for (WorkflowDefinition workflowDefinition : _workflowDefinitions) {
			_workflowDefinitionManager.updateActive(
				workflowDefinition.getCompanyId(),
				workflowDefinition.getUserId(), workflowDefinition.getName(),
				workflowDefinition.getVersion(), false);

			_workflowDefinitionManager.undeployWorkflowDefinition(
				workflowDefinition.getCompanyId(),
				workflowDefinition.getUserId(), workflowDefinition.getName(),
				workflowDefinition.getVersion());
		}
	}

	@Reference
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

	private final List<WorkflowDefinition> _workflowDefinitions =
		new CopyOnWriteArrayList<>();

}