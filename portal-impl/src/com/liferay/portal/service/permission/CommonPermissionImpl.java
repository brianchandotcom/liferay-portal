/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.CommonPermission;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Charles May
 */
public class CommonPermissionImpl implements CommonPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			String actionId)
		throws PortalException {

		check(
			permissionChecker, PortalUtil.getClassName(classNameId), classPK,
			actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, String className, long classPK,
			String actionId)
		throws PortalException {

		if (className.equals(Company.class.getName())) {
			long companyId = permissionChecker.getCompanyId();

			if (classPK > 0) {
				companyId = classPK;
			}

			if (!RoleLocalServiceUtil.hasUserRole(
					permissionChecker.getUserId(), companyId,
					RoleConstants.ADMINISTRATOR, true)) {

				throw new PrincipalException.MustBeCompanyAdmin(
					permissionChecker);
			}
		}
		else if (className.equals(Contact.class.getName())) {
			User user = UserLocalServiceUtil.getUserByContactId(classPK);

			UserPermissionUtil.check(
				permissionChecker, user.getUserId(), actionId);
		}
		else if (className.equals(Organization.class.getName())) {
			OrganizationPermissionUtil.check(
				permissionChecker, classPK, actionId);
		}
		else if (className.equals(User.class.getName())) {
			UserPermissionUtil.check(permissionChecker, classPK, actionId);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn("Invalid class name " + className);
			}

			throw new PrincipalException.MustHavePermission(
				permissionChecker, className, classPK, actionId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommonPermissionImpl.class);

}