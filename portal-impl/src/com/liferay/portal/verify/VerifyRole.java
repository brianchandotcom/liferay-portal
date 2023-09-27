/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.util.PortalInstances;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyRole extends VerifyProcess {

	protected void addViewSiteAdministrationPermission(Role role)
		throws Exception {

		String name = Group.class.getName();

		Group group = GroupLocalServiceUtil.getGroup(
			role.getCompanyId(), GroupConstants.USER_PERSONAL_SITE);

		String primKey = String.valueOf(group.getGroupId());

		if (!ResourcePermissionLocalServiceUtil.hasResourcePermission(
				role.getCompanyId(), name, ResourceConstants.SCOPE_GROUP,
				primKey, role.getRoleId(), ActionKeys.MANAGE_LAYOUTS) ||
			ResourcePermissionLocalServiceUtil.hasResourcePermission(
				role.getCompanyId(), name, ResourceConstants.SCOPE_GROUP,
				primKey, role.getRoleId(),
				ActionKeys.VIEW_SITE_ADMINISTRATION)) {

			return;
		}

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			role.getCompanyId(), name, ResourceConstants.SCOPE_GROUP, primKey,
			role.getRoleId(), ActionKeys.VIEW_SITE_ADMINISTRATION);
	}

	@Override
	protected void doVerify() throws Exception {
		CompanyLocalServiceUtil.forEachCompanyId(
			companyId -> verifyRoles(companyId),
			PortalInstances.getCompanyIdsBySQL());
	}

	protected void verifyRoles(long companyId) throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer(
				String.valueOf(companyId))) {

			try {
				Role powerUserRole = RoleLocalServiceUtil.getRole(
					companyId, RoleConstants.POWER_USER);

				addViewSiteAdministrationPermission(powerUserRole);
			}
			catch (NoSuchRoleException noSuchRoleException) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(noSuchRoleException);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(VerifyRole.class);

}