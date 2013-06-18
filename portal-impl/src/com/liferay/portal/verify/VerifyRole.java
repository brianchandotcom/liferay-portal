/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyRole extends VerifyProcess {

	protected void addViewSiteAdministrationPermission(Role role)
		throws Exception {

		String name = Group.class.getName();

		Group userPersonalSite = GroupLocalServiceUtil.getGroup(
			role.getCompanyId(), GroupConstants.USER_PERSONAL_SITE);

		String primKey = String.valueOf(userPersonalSite.getGroupId());

		if (ResourcePermissionLocalServiceUtil.hasResourcePermission(
				role.getCompanyId(), name, ResourceConstants.SCOPE_GROUP,
				primKey, role.getRoleId(),
				ActionKeys.VIEW_SITE_ADMINISTRATION) ||
			!ResourcePermissionLocalServiceUtil.hasResourcePermission(
				role.getCompanyId(), name, ResourceConstants.SCOPE_GROUP,
				primKey, role.getRoleId(), ActionKeys.MANAGE_LAYOUTS)) {

			return;
		}

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			role.getCompanyId(), name, ResourceConstants.SCOPE_GROUP, primKey,
			role.getRoleId(), ActionKeys.VIEW_SITE_ADMINISTRATION);
	}

	protected void deleteImplicitAssociations(Role role) throws Exception {
		runSQL(
			"delete from UserGroupGroupRole where roleId = " +
				role.getRoleId());
		runSQL("delete from UserGroupRole where roleId = " + role.getRoleId());
	}

	@Override
	protected void doVerify() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			RoleLocalServiceUtil.checkSystemRoles(companyId);

			try {
				Role organizationUserRole = RoleLocalServiceUtil.getRole(
					companyId, RoleConstants.ORGANIZATION_USER);

				deleteImplicitAssociations(organizationUserRole);
			}
			catch (NoSuchRoleException nsre) {
			}

			try {
				Role powerUser = RoleLocalServiceUtil.getRole(
					companyId, RoleConstants.POWER_USER);

				addViewSiteAdministrationPermission(powerUser);
			}
			catch (NoSuchRoleException nsre) {
			}

			try {
				Role siteMemberRole = RoleLocalServiceUtil.getRole(
					companyId, RoleConstants.SITE_MEMBER);

				deleteImplicitAssociations(siteMemberRole);
			}
			catch (NoSuchRoleException nsre) {
			}
		}
	}

}