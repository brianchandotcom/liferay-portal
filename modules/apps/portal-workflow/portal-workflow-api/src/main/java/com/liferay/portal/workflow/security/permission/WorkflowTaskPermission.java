/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.security.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.workflow.WorkflowTask;

/**
 * @author Rafael Praxedes
 */
public interface WorkflowTaskPermission {

	public void check(
			PermissionChecker permissionChecker, WorkflowTask workflowTask,
			long groupId)
		throws PortalException;

	public boolean contains(
		PermissionChecker permissionChecker, WorkflowTask workflowTask,
		long groupId);

}