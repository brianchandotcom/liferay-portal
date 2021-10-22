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
			_liferayPortletRequest, "accountEntryId");
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

	public boolean isOpenModalOnRedirect() {
		return ParamUtil.getBoolean(
			_liferayPortletRequest, "openModalOnRedirect");
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