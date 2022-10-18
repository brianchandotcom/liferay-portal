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

import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
	service = AssetRendererFactory.class
)
public class AccountEntryAssetRendererFactory
	extends BaseAssetRendererFactory<AccountEntry> {

	public static final String TYPE = "account";

	public AccountEntryAssetRendererFactory() {
		setCategorizable(false);
		setClassName(AccountEntry.class.getName());
		setPortletId(AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN);
		setSearchable(false);
	}

	@Override
	public AssetRenderer<AccountEntry> getAssetRenderer(long classPK, int type)
		throws PortalException {

		return new AccountEntryAssetRenderer(
			_accountEntryLocalService.getAccountEntry(classPK));
	}

	@Override
	public String getIconCssClass() {
		return "briefcase";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

}