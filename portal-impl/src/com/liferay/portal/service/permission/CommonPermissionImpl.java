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
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

/**
 * @author Charles May
 */
public class CommonPermissionImpl implements CommonPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			String actionId)
		throws PortalException {

		String className = PortalUtil.getClassName(classNameId);

		check(permissionChecker, className, classPK, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, String className, long classPK,
			String actionId)
		throws PortalException {

		if (className.equals(Account.class.getName())) {
		}
		else if (className.equals(Company.class.getName())) {
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

			throw new PrincipalException();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommonPermissionImpl.class);

}