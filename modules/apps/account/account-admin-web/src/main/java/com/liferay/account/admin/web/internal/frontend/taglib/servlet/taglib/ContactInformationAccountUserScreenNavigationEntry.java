/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.account.admin.web.internal.constants.AccountScreenNavigationEntryConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "screen.navigation.entry.order:Integer=20",
	service = ScreenNavigationEntry.class
)
public class ContactInformationAccountUserScreenNavigationEntry
	extends BaseAccountUserScreenNavigationEntry {

	@Override
	public String getActionCommandName() {
		return "/users_admin/update_user_contact_information_form";
	}

	@Override
	public String getCategoryKey() {
		return AccountScreenNavigationEntryConstants.CATEGORY_KEY_CONTACT;
	}

	@Override
	public String getEntryKey() {
		return AccountScreenNavigationEntryConstants.
			ENTRY_KEY_CONTACT_INFORMATION;
	}

	@Override
	public String getJspPath() {
		return "/user/contact_information.jsp";
	}

}