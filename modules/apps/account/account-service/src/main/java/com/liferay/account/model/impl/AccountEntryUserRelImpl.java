/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.model.impl;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AccountEntryUserRelImpl extends AccountEntryUserRelBaseImpl {

	@Override
	public AccountEntry fetchAccountEntry() {
		return AccountEntryLocalServiceUtil.fetchAccountEntry(
			getAccountEntryId());
	}

	@Override
	public User fetchUser() {
		return UserLocalServiceUtil.fetchUser(getAccountUserId());
	}

	@Override
	public AccountEntry getAccountEntry() throws PortalException {
		return AccountEntryLocalServiceUtil.getAccountEntry(
			getAccountEntryId());
	}

	@Override
	public User getUser() throws PortalException {
		return UserLocalServiceUtil.getUser(getAccountUserId());
	}

	@Override
	public List<UserGroupRole> getUserGroupRoles() throws PortalException {
		AccountEntry accountEntry =
			AccountEntryLocalServiceUtil.getAccountEntry(getAccountEntryId());

		return UserGroupRoleLocalServiceUtil.getUserGroupRoles(
			getAccountUserId(), accountEntry.getAccountEntryGroupId());
	}

}