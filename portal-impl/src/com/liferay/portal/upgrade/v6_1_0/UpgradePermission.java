/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Alexander Chow
 */
public class UpgradePermission extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {

		// LPS-14202 and LPS-17841

		updatePermissions("com.liferay.portlet.bookmarks", true, true);
		updatePermissions("com.liferay.portlet.documentlibrary", false, true);
		updatePermissions("com.liferay.portlet.imagegallery", true, false);
		updatePermissions("com.liferay.portlet.messageboards", true, true);
		updatePermissions("com.liferay.portlet.shopping", true, true);
	}

	protected void updatePermissions(
			String name, boolean community, boolean guest)
		throws Exception {

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			updatePermissions_6(name, community, guest);
		}
		else {
			updatePermissions_1to5(name, community, guest);
		}
	}

	protected void updatePermissions_1to5(
			String name, boolean community, boolean guest)
		throws Exception {

		if (community) {
			PermissionLocalServiceUtil.setContainerResourcePermissions(
				name, RoleConstants.ORGANIZATION_USER, ActionKeys.VIEW);
			PermissionLocalServiceUtil.setContainerResourcePermissions(
				name, RoleConstants.SITE_MEMBER, ActionKeys.VIEW);
		}

		if (guest) {
			PermissionLocalServiceUtil.setContainerResourcePermissions(
				name, RoleConstants.GUEST, ActionKeys.VIEW);
		}

		PermissionLocalServiceUtil.setContainerResourcePermissions(
			name, RoleConstants.OWNER, ActionKeys.VIEW);
	}

	protected void updatePermissions_6(
			String name, boolean community, boolean guest)
		throws Exception {

		List<String> modelActions =
			ResourceActionsUtil.getModelResourceActions(name);

		ResourceActionLocalServiceUtil.checkResourceActions(name, modelActions);

		long actionIdsLong = 1;

		if (community) {
			ResourcePermissionLocalServiceUtil.setContainerResourcePermissions(
				name, RoleConstants.ORGANIZATION_USER, actionIdsLong);
			ResourcePermissionLocalServiceUtil.setContainerResourcePermissions(
				name, RoleConstants.SITE_MEMBER, actionIdsLong);
		}

		if (guest) {
			ResourcePermissionLocalServiceUtil.setContainerResourcePermissions(
				name, RoleConstants.GUEST, actionIdsLong);
		}

		ResourcePermissionLocalServiceUtil.setContainerResourcePermissions(
			name, RoleConstants.OWNER, actionIdsLong);
	}

}