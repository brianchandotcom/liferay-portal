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

package com.liferay.workflow.instance.web.portlet;

import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.workflow.WorkflowControlPanelEntry;
import com.liferay.portlet.ControlPanelEntry;
import com.liferay.workflow.instance.web.constants.WorkflowInstancePortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WorkflowInstancePortletKeys.WORKFLOW_INSTANCE
	},
	service = ControlPanelEntry.class
)
public class WorkflowInstanceControlPanelEntry
	extends WorkflowControlPanelEntry {

	@Override
	protected boolean hasPermissionImplicitlyGranted(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception {

		if (WorkflowInstanceManagerUtil.getWorkflowInstanceCount(
				permissionChecker.getCompanyId(), permissionChecker.getUserId(),
				null, null, null) > 0) {

			return true;
		}

		return false;
	}

}