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

package com.liferay.roles.admin.internal.exportimport.data.handler;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.ResourceTypePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionConversionFilter;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;

/**
 * @author Michael C. Han
 */
public class ImportExportPermissionConversionFilter
	implements PermissionConversionFilter {

	@Override
	public boolean accept(Role role, ResourcePermission resourcePermission) {
		int scope = resourcePermission.getScope();

		if ((scope == ResourceConstants.SCOPE_COMPANY) ||
			(scope == ResourceConstants.SCOPE_GROUP_TEMPLATE)) {

			return true;
		}
		else if (resourcePermission.getScope() ==
					ResourceConstants.SCOPE_GROUP) {

			Group group = GroupLocalServiceUtil.fetchGroup(
				Long.valueOf(resourcePermission.getPrimKey()));

			if (group.isCompany() || group.isUserPersonalSite()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean accept(
		Role role, ResourceTypePermission resourceTypePermission) {

		if (role.getType() != RoleConstants.TYPE_REGULAR) {
			return true;
		}
		else if (resourceTypePermission.isCompanyScope()) {
			return true;
		}

		Group group = GroupLocalServiceUtil.fetchGroup(
			resourceTypePermission.getGroupId());

		if ((group != null) &&
			(group.isCompany() || group.isUserPersonalSite())) {

			return true;
		}

		return false;
	}

}