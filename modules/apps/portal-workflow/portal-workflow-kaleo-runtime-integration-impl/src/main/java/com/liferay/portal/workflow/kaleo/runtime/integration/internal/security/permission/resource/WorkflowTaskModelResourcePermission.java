/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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