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
import com.liferay.account.service.AccountEntryUserRelLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;

import javax.portlet.PortletResponse;

/**
 * @author Albert Lee
 */
public class AccountUserAccountEntryRowChecker extends EmptyOnClickRowChecker {

	public AccountUserAccountEntryRowChecker(
		PortletResponse portletResponse, long accountUserId) {

		super(portletResponse);

		_accountUserId = accountUserId;
	}

	@Override
	public boolean isChecked(Object object) {
		return isDisabled(object);
	}

	@Override
	public boolean isDisabled(Object object) {
		AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)object;

		return AccountEntryUserRelLocalServiceUtil.hasAccountEntryUserRel(
			accountEntryDisplay.getAccountEntryId(), _accountUserId);
	}

	private final long _accountUserId;

}