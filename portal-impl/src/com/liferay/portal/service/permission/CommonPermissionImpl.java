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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Account;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.AccountLocalServiceUtil;
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

		if (className.equals(Account.class.getName())) {
			long companyId = permissionChecker.getCompanyId();

			if (classPK > 0) {
				Account account = AccountLocalServiceUtil.getAccount(classPK);

				companyId = account.getCompanyId();
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