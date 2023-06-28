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

package com.liferay.portlet.admin.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;

/**
 * Provides utility methods for determining if a user is a universal
 * administrator. Universal administrators have administrator permissions in
 * every company.
 *
 * <p>
 * A user can be made a universal administrator by adding their primary key to
 * the list in <code>portal.properties</code> under the key
 * <code>omniadmin.users</code>. If this property is left blank, administrators
 * of the default company will automatically be universal administrators.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class OmniadminUtil {

	public static boolean isOmniadmin(long userId) {
		if (userId <= 0) {
			return false;
		}

		try {
			User user = UserLocalServiceUtil.fetchUser(userId);

			if (user == null) {
				return false;
			}

			return isOmniadmin(user);
		}
		catch (SystemException systemException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(systemException);
			}

			return false;
		}
	}

	public static boolean isOmniadmin(User user) {
		try {
			if (PropsValues.OMNIADMIN_USERS.length > 0) {
				for (int i = 0; i < PropsValues.OMNIADMIN_USERS.length; i++) {
					if (PropsValues.OMNIADMIN_USERS[i] == user.getUserId()) {
						if (user.getCompanyId() !=
								PortalInstances.getDefaultCompanyId()) {

							return false;
						}

						return true;
					}
				}

				return false;
			}

			if (user.isGuestUser() ||
				(user.getCompanyId() !=
					PortalInstances.getDefaultCompanyId())) {

				return false;
			}

			return RoleLocalServiceUtil.hasUserRole(
				user.getUserId(), user.getCompanyId(),
				RoleConstants.ADMINISTRATOR, true);
		}
		catch (Exception exception) {
			_log.error("Unable to check if a user is an omniadmin", exception);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(OmniadminUtil.class);

}