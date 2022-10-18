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

package com.liferay.account.internal.model;

import com.liferay.account.model.AccountEntry;
import com.liferay.asset.kernel.model.BaseAssetRenderer;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Drew Brokke
 */
public class AccountEntryAssetRenderer extends BaseAssetRenderer<AccountEntry> {

	public AccountEntryAssetRenderer(AccountEntry accountEntry) {
		_accountEntry = accountEntry;
	}

	@Override
	public AccountEntry getAssetObject() {
		return _accountEntry;
	}

	@Override
	public String getClassName() {
		return AccountEntry.class.getName();
	}

	@Override
	public long getClassPK() {
		return _accountEntry.getAccountEntryId();
	}

	@Override
	public long getGroupId() {
		return 0;
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return _accountEntry.getDescription();
	}

	@Override
	public String getTitle(Locale locale) {
		return _accountEntry.getName();
	}

	@Override
	public long getUserId() {
		return _accountEntry.getUserId();
	}

	@Override
	public String getUserName() {
		return _accountEntry.getUserName();
	}

	@Override
	public String getUuid() {
		return _accountEntry.getUuid();
	}

	@Override
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String template)
		throws Exception {

		return false;
	}

	private final AccountEntry _accountEntry;

}