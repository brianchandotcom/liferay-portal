/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;

import java.util.Objects;

/**
 * @author Jorge Ferrer
 */
public class WorkflowPermissionUtil {

	public static Boolean hasPermission(
		PermissionChecker permissionChecker, long groupId, String className,
		long classPK, String actionId) {

		try {
			return _hasPermission(
				permissionChecker, groupId, className, classPK, actionId);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return null;
	}

	private static boolean _hasImplicitPermission(
			PermissionChecker permissionChecker,
			WorkflowInstance workflowInstance)
		throws WorkflowException {

		int count =
			WorkflowTaskManagerUtil.getWorkflowTaskCountByWorkflowInstance(
				permissionChecker.getCompanyId(), permissionChecker.getUserId(),
				workflowInstance.getWorkflowInstanceId(), Boolean.FALSE);

		if (count > 0) {
			return true;
		}

		count = WorkflowTaskManagerUtil.getWorkflowTaskCountByUserRoles(
			permissionChecker.getCompanyId(), permissionChecker.getUserId(),
			workflowInstance.getWorkflowInstanceId(), Boolean.FALSE);

		if (count > 0) {
			return true;
		}

		return false;
	}

	private static Boolean _hasPermission(
			PermissionChecker permissionChecker, long groupId, String className,
			long classPK, String actionId)
		throws PortalException {

		long companyId = permissionChecker.getCompanyId();

		if (permissionChecker.isContentReviewer(companyId, groupId)) {
			return Boolean.TRUE;
		}

		if (WorkflowInstanceLinkLocalServiceUtil.hasWorkflowInstanceLink(
				companyId, groupId, className, classPK)) {

			WorkflowInstanceLink workflowInstanceLink =
				WorkflowInstanceLinkLocalServiceUtil.getWorkflowInstanceLink(
					companyId, groupId, className, classPK);

			if (Objects.equals(actionId, ActionKeys.VIEW) &&
				(permissionChecker.getUserId() ==
					workflowInstanceLink.getUserId())) {

				return Boolean.TRUE;
			}

			WorkflowInstance workflowInstance =
				WorkflowInstanceManagerUtil.getWorkflowInstance(
					companyId, workflowInstanceLink.getWorkflowInstanceId());

			if (workflowInstance.isComplete()) {
				return null;
			}

			boolean hasPermission = _hasImplicitPermission(
				permissionChecker, workflowInstance);

			if (!hasPermission && actionId.equals(ActionKeys.VIEW)) {
				return Boolean.FALSE;
			}

			return hasPermission;
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowPermissionUtil.class);

}