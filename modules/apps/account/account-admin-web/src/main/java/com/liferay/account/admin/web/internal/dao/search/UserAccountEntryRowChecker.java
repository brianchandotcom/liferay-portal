/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.dao.search;

import com.liferay.account.admin.web.internal.display.AccountEntryDisplay;
import com.liferay.account.service.AccountEntryUserRelLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;

import jakarta.portlet.PortletResponse;

/**
 * @author Stefano Motta
 */
public class UserAccountEntryRowChecker extends EmptyOnClickRowChecker {

	public UserAccountEntryRowChecker(
		PortletResponse portletResponse, long userId) {

		super(portletResponse);

		_userId = userId;
	}

	@Override
	public boolean isChecked(Object object) {
		AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)object;

		return AccountEntryUserRelLocalServiceUtil.hasAccountEntryUserRel(
			accountEntryDisplay.getAccountEntryId(), _userId);
	}

	@Override
	public boolean isDisabled(Object object) {
		return isChecked(object);
	}

	private final long _userId;

}