/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.task.web.internal.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.ControlPanelEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.workflow.MyWorkflowTasksControlPanelEntry;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	property = "jakarta.portlet.name=" + PortletKeys.MY_WORKFLOW_TASK,
	service = ControlPanelEntry.class
)
public class MyWorkflowTaskControlPanelEntry
	extends MyWorkflowTasksControlPanelEntry {

	@Override
	protected boolean hasAccessPermissionExplicitlyGranted(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws PortalException {

		if (PortletPermissionUtil.contains(
				permissionChecker, group.getGroupId(), 0,
				portlet.getPortletId(), ActionKeys.ACCESS_IN_CONTROL_PANEL,
				true)) {

			return true;
		}

		return super.hasAccessPermissionExplicitlyGranted(
			permissionChecker, group, portlet);
	}

}