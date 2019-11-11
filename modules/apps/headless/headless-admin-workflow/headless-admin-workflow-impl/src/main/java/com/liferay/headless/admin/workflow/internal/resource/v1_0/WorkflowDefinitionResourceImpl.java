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

package com.liferay.headless.admin.workflow.internal.resource.v1_0;

import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowDefinitionResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/workflow-definition.properties",
	scope = ServiceScope.PROTOTYPE, service = WorkflowDefinitionResource.class
)
public class WorkflowDefinitionResourceImpl
	extends BaseWorkflowDefinitionResourceImpl {

	@Override
	public void deleteWorkflowDefinitionUndeploy(String name, String version)
		throws Exception {

		_workflowDefinitionManager.undeployWorkflowDefinition(
			contextCompany.getCompanyId(), contextUser.getUserId(), name,
			GetterUtil.getInteger(version));
	}

	@Override
	public WorkflowDefinition getWorkflowDefinitionByName(String name)
		throws Exception {

		return _toWorkflowDefinition(
			_workflowDefinitionManager.getLatestWorkflowDefinition(
				contextCompany.getCompanyId(), name));
	}

	@Override
	public Page<WorkflowDefinition> getWorkflowDefinitionsPage(
			Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_workflowDefinitionManager.getLatestWorkflowDefinitions(
					contextCompany.getCompanyId(),
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toWorkflowDefinition),
			pagination,
			_workflowDefinitionManager.getLatestWorkflowDefinitionCount(
				contextCompany.getCompanyId()));
	}

	@Override
	public WorkflowDefinition postWorkflowDefinitionDeploy(
			WorkflowDefinition workflowDefinition)
		throws Exception {

		String content = workflowDefinition.getContent();

		return _toWorkflowDefinition(
			_workflowDefinitionManager.deployWorkflowDefinition(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				workflowDefinition.getTitle(), workflowDefinition.getName(),
				content.getBytes()));
	}

	@Override
	public WorkflowDefinition postWorkflowDefinitionSave(
			WorkflowDefinition workflowDefinition)
		throws Exception {

		String content = workflowDefinition.getContent();

		return _toWorkflowDefinition(
			_workflowDefinitionManager.saveWorkflowDefinition(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				workflowDefinition.getTitle(), workflowDefinition.getName(),
				content.getBytes()));
	}

	@Override
	public WorkflowDefinition postWorkflowDefinitionUpdateActive(
			Boolean active, String name, String version)
		throws Exception {

		return _toWorkflowDefinition(
			_workflowDefinitionManager.updateActive(
				contextCompany.getCompanyId(), contextUser.getUserId(), name,
				GetterUtil.getInteger(version), active));
	}

	@Override
	public WorkflowDefinition postWorkflowDefinitionUpdateTitle(
			String name, String title, String version)
		throws Exception {

		return _toWorkflowDefinition(
			_workflowDefinitionManager.updateTitle(
				contextCompany.getCompanyId(), contextUser.getUserId(), name,
				GetterUtil.getInteger(version), title));
	}

	private WorkflowDefinition _toWorkflowDefinition(
		com.liferay.portal.kernel.workflow.WorkflowDefinition
			workflowDefinition) {

		return new WorkflowDefinition() {
			{
				active = workflowDefinition.isActive();
				content = workflowDefinition.getContent();
				dateModified = workflowDefinition.getModifiedDate();
				description = workflowDefinition.getDescription();
				name = workflowDefinition.getName();
				title = workflowDefinition.getTitle();
				version = GetterUtil.getString(workflowDefinition.getVersion());
			}
		};
	}

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

}