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

package com.liferay.portal.workflow.kaleo.demo.data.creator.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.demo.data.creator.WorkflowDefinitionDemoDataCreator;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;

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
			byte[] bytes, long companyId, String name, String title,
			long userId)
		throws WorkflowException {

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.deployWorkflowDefinition(
				companyId, userId, title, name, bytes);

		_workflowDefinitions.add(workflowDefinition);

		return workflowDefinition;
	}

	@Override
	public WorkflowDefinition create(
			long companyId, Date createDate, long userId)
		throws PortalException {

		String content = StringUtil.read(
			WorkflowDefinitionDemoDataCreatorImpl.class,
			"dependencies/auto-insurance-application-definition.xml");

		WorkflowDefinition workflowDefinition = create(
			content.getBytes(), companyId, "Auto Insurance Application",
			"Auto Insurance Application", userId);

		if (createDate != null) {
			KaleoDefinition kaleoDefinition =
				_kaleoDefinitionLocalService.getKaleoDefinition(
					workflowDefinition.getWorkflowDefinitionId());

			kaleoDefinition.setCreateDate(createDate);
			kaleoDefinition.setModifiedDate(createDate);

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
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

	private final List<WorkflowDefinition> _workflowDefinitions =
		new CopyOnWriteArrayList<>();

}