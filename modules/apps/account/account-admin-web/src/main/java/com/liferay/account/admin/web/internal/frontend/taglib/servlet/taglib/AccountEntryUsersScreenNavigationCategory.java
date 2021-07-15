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

package com.liferay.account.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.account.admin.web.internal.constants.AccountScreenNavigationEntryConstants;
import com.liferay.account.admin.web.internal.display.AccountEntryDisplay;
import com.liferay.account.admin.web.internal.security.permission.resource.AccountEntryPermission;
import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;

import java.util.Locale;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Albert Lee
 */
@Component(
	property = {
		"screen.navigation.category.order:Integer=20",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class AccountEntryUsersScreenNavigationCategory
	extends BaseAccountEntryScreenNavigationEntry
	implements ScreenNavigationCategory {

	@Override
	public String getCategoryKey() {
		return AccountScreenNavigationEntryConstants.CATEGORY_KEY_USERS;
	}

	@Override
	public String getEntryKey() {
		return AccountScreenNavigationEntryConstants.ENTRY_KEY_USERS;
	}

	@Override
	public String getJspPath() {
		return "/account_entries_admin/account_entry/view_account_users.jsp";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "users");
	}

	@Override
	public boolean isVisible(
		User user, AccountEntryDisplay accountEntryDisplay) {

		if ((accountEntryDisplay.getAccountEntryId() == 0) ||
			!Objects.equals(
				accountEntryDisplay.getType(),
				AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS)) {

			return false;
		}

		try {
			if (AccountEntryPermission.contains(
					PermissionCheckerFactoryUtil.create(user),
					accountEntryDisplay.getAccountEntryId(),
					AccountActionKeys.VIEW_USERS)) {

				return true;
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEntryUsersScreenNavigationCategory.class);

}