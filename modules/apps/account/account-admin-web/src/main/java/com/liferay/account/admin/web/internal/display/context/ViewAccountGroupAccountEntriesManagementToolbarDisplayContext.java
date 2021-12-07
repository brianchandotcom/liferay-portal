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

import com.liferay.account.admin.web.internal.constants.AccountWebKeys;
import com.liferay.account.admin.web.internal.display.AccountEntryDisplay;
import com.liferay.account.admin.web.internal.display.AccountGroupDisplay;
import com.liferay.account.admin.web.internal.security.permission.resource.AccountGroupPermission;
import com.liferay.account.constants.AccountActionKeys;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Albert Lee
 */
public class ViewAccountGroupAccountEntriesManagementToolbarDisplayContext
	extends ViewAccountEntriesManagementToolbarDisplayContext {

	public ViewAccountGroupAccountEntriesManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<AccountEntryDisplay> searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		if (!_hasAssignAccountsPermission()) {
			return null;
		}

		return DropdownItemList.of(
			DropdownItemBuilder.putData(
				"action", "removeAccountGroupAccountEntries"
			).setIcon(
				"times-circle"
			).setLabel(
				LanguageUtil.get(httpServletRequest, "remove")
			).setQuickAction(
				true
			).build());
	}

	@Override
	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData(
					"action", "selectAccountGroupAccountEntries");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "assign-accounts"));
			}
		).build();
	}

	@Override
	public Boolean isSelectable() {
		return _hasAssignAccountsPermission();
	}

	@Override
	public Boolean isShowCreationMenu() {
		return _hasAssignAccountsPermission();
	}

	private long _getAccountGroupId() {
		AccountGroupDisplay accountGroupDisplay =
			(AccountGroupDisplay)httpServletRequest.getAttribute(
				AccountWebKeys.ACCOUNT_GROUP_DISPLAY);

		if (accountGroupDisplay != null) {
			return accountGroupDisplay.getAccountGroupId();
		}

		return 0;
	}

	private boolean _hasAssignAccountsPermission() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return AccountGroupPermission.contains(
			themeDisplay.getPermissionChecker(), _getAccountGroupId(),
			AccountActionKeys.ASSIGN_ACCOUNTS);
	}

}