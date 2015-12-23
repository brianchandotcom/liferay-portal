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

package com.liferay.staging.processes.web.portlet;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.BaseControlPanelEntry;
import com.liferay.portlet.ControlPanelEntry;
import com.liferay.staging.processes.web.constants.StagingProcessesPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + StagingProcessesPortletKeys.STAGING_PROCESSES},
	service = ControlPanelEntry.class
)
public class StagingProcessesControlPanelEntry extends BaseControlPanelEntry {

	@Override
	protected boolean hasAccessPermissionDenied(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception {

		if (group.isLayoutPrototype() || group.isLayoutSetPrototype()) {
			return true;
		}

		return super.hasAccessPermissionDenied(
			permissionChecker, group, portlet);
	}

}