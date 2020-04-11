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
import com.liferay.account.service.AccountEntryUserRelLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;

import javax.portlet.PortletResponse;

/**
 * @author Pei-Jung Lan
 */
public class SelectAccountUserRowChecker extends EmptyOnClickRowChecker {

	public SelectAccountUserRowChecker(
		PortletResponse portletResponse, long accountEntryId) {

		super(portletResponse);

		_accountEntryId = accountEntryId;
	}

	@Override
	public boolean isChecked(Object obj) {
		AccountUserDisplay accountUserDisplay = (AccountUserDisplay)obj;

		if (AccountEntryUserRelLocalServiceUtil.hasAccountEntryUserRel(
				_accountEntryId, accountUserDisplay.getUserId())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isDisabled(Object obj) {
		return isChecked(obj);
	}

	private final long _accountEntryId;

}