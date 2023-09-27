/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.membershippolicy;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public abstract class BaseRoleMembershipPolicy implements RoleMembershipPolicy {

	@Override
	public boolean isRoleAllowed(long userId, long roleId)
		throws PortalException {

		try {
			checkRoles(new long[] {userId}, new long[] {roleId}, null);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return false;
		}

		return true;
	}

	@Override
	public boolean isRoleRequired(long userId, long roleId)
		throws PortalException {

		try {
			checkRoles(new long[] {userId}, null, new long[] {roleId});
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return true;
		}

		return false;
	}

	@Override
	public void verifyPolicy() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			RoleLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(Role role) -> verifyPolicy(role));

		actionableDynamicQuery.performActions();
	}

	@Override
	public void verifyPolicy(Role role) throws PortalException {
		verifyPolicy(role, null, null);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseRoleMembershipPolicy.class);

}