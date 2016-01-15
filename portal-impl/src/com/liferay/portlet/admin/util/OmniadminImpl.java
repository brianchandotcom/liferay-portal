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

import com.liferay.admin.kernel.util.Omniadmin;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;

/**
 * @author Michael C. Han
 */
public class OmniadminImpl implements Omniadmin {

	@Override
	public boolean isOmniadmin(long userId) {
		try {
			User user = UserLocalServiceUtil.fetchUser(userId);

			if (user == null) {
				return false;
			}

			return isOmniadmin(user);
		}
		catch (SystemException se) {
			return false;
		}
	}

	@Override
	public boolean isOmniadmin(User user) {
		long userId = user.getUserId();

		if (userId <= 0) {
			return false;
		}

		try {
			if (PropsValues.OMNIADMIN_USERS.length > 0) {
				for (int i = 0; i < PropsValues.OMNIADMIN_USERS.length; i++) {
					if (PropsValues.OMNIADMIN_USERS[i] == userId) {
						if (user.getCompanyId() !=
								PortalInstances.getDefaultCompanyId()) {

							return false;
						}

						return true;
					}
				}

				return false;
			}

			if (user.getCompanyId() != PortalInstances.getDefaultCompanyId()) {
				return false;
			}

			return RoleLocalServiceUtil.hasUserRole(
				userId, user.getCompanyId(), RoleConstants.ADMINISTRATOR, true);
		}
		catch (Exception e) {
			_log.error(e);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(OmniadminImpl.class);

}