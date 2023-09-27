/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.demo.data.creator.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.workflow.kaleo.demo.data.creator.WorkflowDefinitionLinkDemoDataCreator;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(service = WorkflowDefinitionLinkDemoDataCreator.class)
public class WorkflowDefinitionLinkDemoDataCreatorImpl
	implements WorkflowDefinitionLinkDemoDataCreator {

	@Override
	public WorkflowDefinitionLink create(
			long userId, long companyId, long groupId, String className,
			long classPK, long typePK)
		throws PortalException {

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.getWorkflowDefinition(
				companyId, "Auto Insurance Application", 1);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(
					_userLocalService.getUser(userId)));

			WorkflowDefinitionLink workflowDefinitionLink =
				_workflowDefinitionLinkLocalService.
					updateWorkflowDefinitionLink(
						userId, companyId, groupId, className, classPK, typePK,
						workflowDefinition.getName(),
						workflowDefinition.getVersion());

			_workflowDefinitionLinks.add(workflowDefinitionLink);

			return workflowDefinitionLink;
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	@Override
	public void delete() throws PortalException {
		for (WorkflowDefinitionLink workflowDefinitionLink :
				_workflowDefinitionLinks) {

			_workflowDefinitionLinks.remove(workflowDefinitionLink);

			_workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(
				workflowDefinitionLink.getCompanyId(),
				workflowDefinitionLink.getGroupId(),
				workflowDefinitionLink.getClassName(),
				workflowDefinitionLink.getClassPK(),
				workflowDefinitionLink.getTypePK());
		}
	}

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	private final List<WorkflowDefinitionLink> _workflowDefinitionLinks =
		new CopyOnWriteArrayList<>();

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

}