/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
		return getGuestOrUser(getUser(getUserId()));
	}

	public static User getGuestOrUser(long companyId) throws PortalException {
		try {
			return getUser(getUserId());
		}
		catch (PrincipalException principalException) {
			try {
				return UserLocalServiceUtil.getGuestUser(companyId);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				throw principalException;
			}
		}
	}

	public static User getGuestOrUser(User user) throws PortalException {
		try {
			return getUser(user.getUserId());
		}
		catch (PrincipalException principalException) {
			try {
				return UserLocalServiceUtil.getGuestUser(
					CompanyThreadLocal.getCompanyId());
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				throw principalException;
			}
		}
	}

	public static long getGuestOrUserId() throws PrincipalException {
		try {
			return getUserId();
		}
		catch (PrincipalException principalException) {
			try {
				return UserLocalServiceUtil.getGuestUserId(
					CompanyThreadLocal.getCompanyId());
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				throw principalException;
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

	private static final Log _log = LogFactoryUtil.getLog(
		GuestOrUserUtil.class);

}