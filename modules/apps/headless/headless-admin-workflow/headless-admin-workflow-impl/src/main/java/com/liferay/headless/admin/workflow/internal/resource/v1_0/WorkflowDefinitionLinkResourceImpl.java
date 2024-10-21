/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.internal.resource.v1_0;

import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowDefinitionLink;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowDefinitionLinkResource;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Victor Kammerer
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/workflow-definition-link.properties",
	scope = ServiceScope.PROTOTYPE,
	service = WorkflowDefinitionLinkResource.class
)
public class WorkflowDefinitionLinkResourceImpl
	extends BaseWorkflowDefinitionLinkResourceImpl {

	@Override
	public Page<WorkflowDefinitionLink> getWorkflowDefinitionLinks(
			Long workflowDefinitionId, Pagination pagination)
		throws Exception {

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.getWorkflowDefinition(
				workflowDefinitionId);

		List<com.liferay.portal.kernel.model.WorkflowDefinitionLink>
			workflowDefinitionLinks =
				_workflowDefinitionLinkLocalService.getWorkflowDefinitionLinks(
					contextCompany.getCompanyId(), workflowDefinition.getName(),
					workflowDefinition.getVersion());

		return Page.of(
			transform(
				ListUtil.subList(
					workflowDefinitionLinks, pagination.getStartPosition(),
					pagination.getEndPosition()),
				workflowDefinitionLink -> _toWorkflowDefinitionLink(
					workflowDefinitionLink)),
			pagination, workflowDefinitionLinks.size());
	}

	@Override
	public WorkflowDefinitionLink postWorkflowDefinitionLink(
			Long workflowDefinitionId,
			WorkflowDefinitionLink workflowDefinitionLink)
		throws Exception {

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.getWorkflowDefinition(
				workflowDefinitionId);

		return _toWorkflowDefinitionLink(
			_workflowDefinitionLinkLocalService.addWorkflowDefinitionLink(
				contextUser.getUserId(), contextCompany.getCompanyId(),
				workflowDefinitionLink.getGroupId(),
				workflowDefinitionLink.getClassName(), 0, 0,
				workflowDefinition.getName(), workflowDefinition.getVersion()));
	}

	private WorkflowDefinitionLink _toWorkflowDefinitionLink(
			com.liferay.portal.kernel.model.WorkflowDefinitionLink
				workflowDefinitionLink)
		throws Exception {

		return new WorkflowDefinitionLink() {
			{
				setClassName(workflowDefinitionLink::getClassName);
				setGroupId(workflowDefinitionLink::getGroupId);
				setId(workflowDefinitionLink::getWorkflowDefinitionLinkId);
				setWorkflowDefinitionName(
					workflowDefinitionLink::getWorkflowDefinitionName);
				setWorkflowDefinitionVersion(
					workflowDefinitionLink::getWorkflowDefinitionVersion);
			}
		};
	}

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

}