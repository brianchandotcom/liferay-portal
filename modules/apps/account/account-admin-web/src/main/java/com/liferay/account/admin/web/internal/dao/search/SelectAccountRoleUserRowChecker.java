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

import com.liferay.account.admin.web.internal.display.AccountUserDisplay;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.account.service.AccountRoleLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;

import javax.portlet.PortletResponse;

/**
 * @author Pei-Jung Lan
 */
public class SelectAccountRoleUserRowChecker extends EmptyOnClickRowChecker {

	public SelectAccountRoleUserRowChecker(
		PortletResponse portletResponse, long accountEntryId,
		long accountRoleId) {

		super(portletResponse);

		_accountEntryId = accountEntryId;
		_accountRoleId = accountRoleId;
	}

	@Override
	public boolean isChecked(Object obj) {
		AccountUserDisplay accountUserDisplay = (AccountUserDisplay)obj;

		AccountEntry accountEntry =
			AccountEntryLocalServiceUtil.fetchAccountEntry(_accountEntryId);

		AccountRole accountRole = AccountRoleLocalServiceUtil.fetchAccountRole(
			_accountRoleId);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				accountUserDisplay.getUserId(),
				accountEntry.getAccountEntryGroupId(),
				accountRole.getRoleId())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isDisabled(Object obj) {
		return isChecked(obj);
	}

	private final long _accountEntryId;
	private final long _accountRoleId;

}