/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.account.admin.web.internal.constants.AccountScreenNavigationEntryConstants;
import com.liferay.account.admin.web.internal.security.permission.resource.AccountEntryPermission;
import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = "screen.navigation.entry.order:Integer=10",
	service = ScreenNavigationEntry.class
)
public class OrganizationsAccountEntryScreenNavigationEntry
	extends BaseAccountEntryScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return AccountScreenNavigationEntryConstants.CATEGORY_KEY_ORGANIZATIONS;
	}

	@Override
	public String getJspPath() {
		return "/account_entries_admin/account_entry" +
			"/view_account_organizations.jsp";
	}

	@Override
	public boolean isVisible(User user, AccountEntry accountEntry) {
		if (accountEntry.isNew()) {
			return false;
		}

		return AccountEntryPermission.contains(
			PermissionCheckerFactoryUtil.create(user),
			accountEntry.getAccountEntryId(),
			AccountActionKeys.VIEW_ORGANIZATIONS);
	}

}