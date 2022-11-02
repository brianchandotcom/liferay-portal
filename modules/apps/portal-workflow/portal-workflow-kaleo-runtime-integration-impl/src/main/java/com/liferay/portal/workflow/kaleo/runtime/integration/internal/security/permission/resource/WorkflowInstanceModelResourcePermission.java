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
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermission;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.workflow.WorkflowInstance",
	service = ModelResourcePermission.class
)
public class WorkflowInstanceModelResourcePermission
	implements ModelResourcePermission<KaleoInstance> {

	@Override
	public void check(
			PermissionChecker permissionChecker, KaleoInstance kaleoInstance,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, kaleoInstance, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, WorkflowInstance.class.getName(),
				kaleoInstance.getKaleoInstanceId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		check(
			permissionChecker,
			_kaleoInstanceLocalService.getKaleoInstance(primaryKey), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, KaleoInstance kaleoInstance,
			String actionId)
		throws PortalException {

		return _workflowPermission.hasPermission(
			permissionChecker, kaleoInstance.getGroupId(),
			kaleoInstance.getClassName(), kaleoInstance.getClassPK(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_kaleoInstanceLocalService.getKaleoInstance(primaryKey), actionId);
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
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Reference(
		target = "(resource.name=" + WorkflowConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private WorkflowPermission _workflowPermission;

}