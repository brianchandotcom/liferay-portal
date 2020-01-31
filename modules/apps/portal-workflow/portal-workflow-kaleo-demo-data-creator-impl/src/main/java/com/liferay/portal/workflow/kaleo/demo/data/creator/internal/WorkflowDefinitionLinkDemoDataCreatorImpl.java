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
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.workflow.kaleo.demo.data.creator.WorkflowDefinitionLinkDemoDataCreator;

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
	public WorkflowDefinitionLink assign(
			String className, long classPK, long companyId, long groupId,
			long typePK, long userId)
		throws PortalException {

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.getWorkflowDefinition(
				companyId, "Auto Insurance Application", 1);

		return assign(
			className, classPK, companyId, groupId, typePK, userId,
			workflowDefinition);
	}

	@Override
	public WorkflowDefinitionLink assign(
			String className, long classPK, long companyId, long groupId,
			long typePK, long userId, WorkflowDefinition workflowDefinition)
		throws PortalException {

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
	public void deleted() throws PortalException {
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