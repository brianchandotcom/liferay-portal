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

package com.liferay.portal.workflow.kaleo.runtime.integration.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.workflow.kaleo.KaleoWorkflowModelConverter;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;
import com.liferay.portal.workflow.security.permission.WorkflowTaskPermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.workflow.WorkflowTask",
	service = ModelResourcePermission.class
)
public class WorkflowTaskModelResourcePermission
	implements ModelResourcePermission<KaleoTaskInstanceToken> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			KaleoTaskInstanceToken kaleoTaskInstanceToken, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, kaleoTaskInstanceToken, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, WorkflowTask.class.getName(),
				kaleoTaskInstanceToken.getKaleoInstanceId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		check(
			permissionChecker,
			_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
				primaryKey),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			KaleoTaskInstanceToken kaleoTaskInstanceToken, String actionId)
		throws PortalException {

		WorkflowTask workflowTask = _kaleoWorkflowModelConverter.toWorkflowTask(
			kaleoTaskInstanceToken,
			WorkflowContextUtil.convert(
				kaleoTaskInstanceToken.getWorkflowContext()));

		return _workflowTaskPermission.contains(
			permissionChecker, workflowTask,
			MapUtil.getLong(workflowTask.getOptionalAttributes(), "groupId"));
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceToken(
				primaryKey),
			actionId);
	}

	@Override
	public String getModelName() {
		return KaleoInstance.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Reference
	private KaleoWorkflowModelConverter _kaleoWorkflowModelConverter;

	@Reference(
		target = "(resource.name=" + WorkflowConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private WorkflowTaskPermission _workflowTaskPermission;

}