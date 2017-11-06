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

package com.liferay.staging.processes.web.internal.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.BaseControlPanelEntry;
import com.liferay.portal.kernel.portlet.ControlPanelEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.staging.constants.StagingProcessesPortletKeys;

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

		if (!PropsValues.STAGING_LIVE_GROUP_REMOTE_STAGING_ENABLED &&
			group.hasLocalOrRemoteStagingGroup()) {

			return true;
		}

		if (group.isLayoutPrototype() || group.isLayoutSetPrototype()) {
			return true;
		}

		if (!group.isStaged() && !group.hasLocalOrRemoteStagingGroup()) {
			if (!GroupPermissionUtil.contains(
					permissionChecker, group, ActionKeys.MANAGE_STAGING) ||
				!GroupPermissionUtil.contains(
					permissionChecker, group, ActionKeys.VIEW_STAGING)) {

				return true;
			}
		}

		if (!GroupPermissionUtil.contains(
				permissionChecker, group, ActionKeys.VIEW_STAGING)) {

			return true;
		}

		return super.hasAccessPermissionDenied(
			permissionChecker, group, portlet);
	}

	@Override
	protected boolean hasAccessPermissionExplicitlyGranted(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws PortalException {

		if (GroupPermissionUtil.contains(
				permissionChecker, group, ActionKeys.VIEW_STAGING)) {

			return true;
		}

		return super.hasAccessPermissionExplicitlyGranted(
			permissionChecker, group, portlet);
	}

}