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

package com.liferay.portal.workflow.web.internal.portlet;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.ControlPanelEntry;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.workflow.WorkflowControlPanelEntry;
import com.liferay.portal.workflow.web.internal.constants.WorkflowPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adam Brandizzi
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + WorkflowPortletKeys.USER_WORKFLOW,
	service = ControlPanelEntry.class
)
public class UserWorkflowControlPanelEntry extends WorkflowControlPanelEntry {

	@Override
	protected boolean hasPermissionImplicitlyGranted(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception {

		int count = WorkflowInstanceManagerUtil.getWorkflowInstanceCount(
			permissionChecker.getCompanyId(), permissionChecker.getUserId(),
			null, null, null);

		if (count > 0) {
			return true;
		}

		return super.hasPermissionImplicitlyGranted(
			permissionChecker, group, portlet);
	}

}