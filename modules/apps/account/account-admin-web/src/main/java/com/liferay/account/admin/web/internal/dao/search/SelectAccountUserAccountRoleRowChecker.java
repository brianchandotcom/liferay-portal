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

package com.liferay.account.admin.web.internal.dao.search;

import com.liferay.account.admin.web.internal.display.AccountRoleDisplay;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;

import javax.portlet.PortletResponse;

/**
 * @author Albert Lee
 */
public class SelectAccountUserAccountRoleRowChecker
	extends EmptyOnClickRowChecker {

	public SelectAccountUserAccountRoleRowChecker(
		PortletResponse portletResponse, long accountEntryId,
		long accountUserId) {

		super(portletResponse);

		_accountEntryId = accountEntryId;
		_accountUserId = accountUserId;
	}

	@Override
	public boolean isChecked(Object object) {
		AccountRoleDisplay accountRoleDisplay = (AccountRoleDisplay)object;

		AccountEntry accountEntry =
			AccountEntryLocalServiceUtil.fetchAccountEntry(_accountEntryId);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				_accountUserId, accountEntry.getAccountEntryGroupId(),
				accountRoleDisplay.getRoleId())) {

			return true;
		}

		return false;
	}

	private final long _accountEntryId;
	private final long _accountUserId;

}