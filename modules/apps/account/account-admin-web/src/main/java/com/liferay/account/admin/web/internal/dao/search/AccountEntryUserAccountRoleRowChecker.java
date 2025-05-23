/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.dao.search;

import com.liferay.account.admin.web.internal.display.AccountRoleDisplay;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;

import jakarta.portlet.PortletResponse;

/**
 * @author Albert Lee
 */
public class AccountEntryUserAccountRoleRowChecker
	extends EmptyOnClickRowChecker {

	public AccountEntryUserAccountRoleRowChecker(
		long accountEntryId, PortletResponse portletResponse, long userId) {

		super(portletResponse);

		_accountEntryId = accountEntryId;
		_userId = userId;
	}

	@Override
	public boolean isChecked(Object object) {
		AccountRoleDisplay accountRoleDisplay = (AccountRoleDisplay)object;

		AccountEntry accountEntry =
			AccountEntryLocalServiceUtil.fetchAccountEntry(_accountEntryId);

		return UserGroupRoleLocalServiceUtil.hasUserGroupRole(
			_userId, accountEntry.getAccountEntryGroupId(),
			accountRoleDisplay.getRoleId());
	}

	private final long _accountEntryId;
	private final long _userId;

}