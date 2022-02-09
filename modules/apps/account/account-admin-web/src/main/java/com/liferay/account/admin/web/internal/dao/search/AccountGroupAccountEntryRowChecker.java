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

import com.liferay.account.admin.web.internal.display.AccountEntryDisplay;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountGroupRelLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.portlet.PortletResponse;

/**
 * @author Albert Lee
 */
public class AccountGroupAccountEntryRowChecker extends EmptyOnClickRowChecker {

	public AccountGroupAccountEntryRowChecker(
		PortletResponse portletResponse, long accountGroupId) {

		super(portletResponse);

		_accountGroupId = accountGroupId;
	}

	@Override
	public boolean isChecked(Object object) {
		return isDisabled(object);
	}

	@Override
	public boolean isDisabled(Object object) {
		AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)object;

		try {
			AccountGroupRel accountGroupRel =
				AccountGroupRelLocalServiceUtil.fetchAccountGroupRel(
					_accountGroupId, AccountEntry.class.getName(),
					accountEntryDisplay.getAccountEntryId());

			if (accountGroupRel != null) {
				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountGroupAccountEntryRowChecker.class);

	private final long _accountGroupId;

}