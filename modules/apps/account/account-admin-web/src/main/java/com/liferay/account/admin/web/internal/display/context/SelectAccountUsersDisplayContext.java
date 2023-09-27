/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.display.context;

import com.liferay.account.admin.web.internal.dao.search.AccountUserRowChecker;
import com.liferay.account.admin.web.internal.dao.search.SelectAccountRoleUserRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;

/**
 * @author Drew Brokke
 */
public class SelectAccountUsersDisplayContext {

	public SelectAccountUsersDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_accountEntryId = ParamUtil.getLong(
			liferayPortletRequest, "accountEntryId");
	}

	public long getAccountEntryId() {
		return _accountEntryId;
	}

	public RowChecker getRowChecker() {
		if (isSingleSelect()) {
			return null;
		}

		long accountRoleId = ParamUtil.getLong(
			_liferayPortletRequest, "accountRoleId");

		if (accountRoleId > 0) {
			return new SelectAccountRoleUserRowChecker(
				_liferayPortletResponse, _accountEntryId, accountRoleId);
		}

		return new AccountUserRowChecker(
			_accountEntryId, _liferayPortletResponse);
	}

	public boolean isShowCreateButton() {
		return ParamUtil.getBoolean(_liferayPortletRequest, "showCreateButton");
	}

	public boolean isShowFilter() {
		return ParamUtil.getBoolean(_liferayPortletRequest, "showFilter", true);
	}

	public boolean isSingleSelect() {
		return ParamUtil.getBoolean(_liferayPortletRequest, "singleSelect");
	}

	private final long _accountEntryId;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}