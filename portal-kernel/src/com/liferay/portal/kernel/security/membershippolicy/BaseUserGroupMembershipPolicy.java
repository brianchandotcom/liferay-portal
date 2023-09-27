/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.membershippolicy;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public abstract class BaseUserGroupMembershipPolicy
	implements UserGroupMembershipPolicy {

	@Override
	public boolean isMembershipAllowed(long userId, long userGroupId)
		throws PortalException {

		try {
			checkMembership(
				new long[] {userId}, new long[] {userGroupId}, null);
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
	public boolean isMembershipRequired(long userId, long userGroupId)
		throws PortalException {

		try {
			checkMembership(
				new long[] {userId}, null, new long[] {userGroupId});
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
			UserGroupLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(UserGroup userGroup) -> verifyPolicy(userGroup));

		actionableDynamicQuery.performActions();
	}

	@Override
	public void verifyPolicy(UserGroup userGroup) throws PortalException {
		verifyPolicy(userGroup, null, null);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseUserGroupMembershipPolicy.class);

}