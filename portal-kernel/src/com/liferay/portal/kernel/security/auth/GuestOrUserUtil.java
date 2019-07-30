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

package com.liferay.portal.kernel.security.auth;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Jürgen Kappler
 */
public class GuestOrUserUtil {

	public static User getGuestOrUser() throws PortalException {
		User user = getUser(getUserId());

		return getGuestOrUser(user);
	}

	public static User getGuestOrUser(User user) throws PortalException {
		try {
			return getUser(user.getUserId());
		}
		catch (PrincipalException pe) {
			try {
				return UserLocalServiceUtil.getDefaultUser(
					CompanyThreadLocal.getCompanyId());
			}
			catch (Exception e) {
				throw pe;
			}
		}
	}

	public static long getGuestOrUserId() throws PrincipalException {
		try {
			return getUserId();
		}
		catch (PrincipalException pe) {
			try {
				return UserLocalServiceUtil.getDefaultUserId(
					CompanyThreadLocal.getCompanyId());
			}
			catch (Exception e) {
				throw pe;
			}
		}
	}

	public static PermissionChecker getPermissionChecker()
		throws PrincipalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			throw new PrincipalException("PermissionChecker not initialized");
		}

		return permissionChecker;
	}

	public static User getUser(long userId) throws PortalException {
		return UserLocalServiceUtil.getUserById(userId);
	}

	public static long getUserId() throws PrincipalException {
		String name = PrincipalThreadLocal.getName();

		if (Validator.isNull(name)) {
			throw new PrincipalException("Principal is null");
		}

		for (String anonymousName : BaseServiceImpl.ANONYMOUS_NAMES) {
			if (StringUtil.equalsIgnoreCase(name, anonymousName)) {
				throw new PrincipalException(
					"Principal cannot be " + anonymousName);
			}
		}

		return GetterUtil.getLong(name);
	}

}