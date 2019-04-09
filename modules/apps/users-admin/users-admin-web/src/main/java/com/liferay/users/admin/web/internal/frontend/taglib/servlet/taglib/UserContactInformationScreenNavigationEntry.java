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

package com.liferay.users.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.portal.kernel.model.User;
import com.liferay.users.admin.constants.UserScreenNavigationEntryConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "screen.navigation.entry.order:Integer=20",
	service = ScreenNavigationEntry.class
)
public class UserContactInformationScreenNavigationEntry
	extends BaseUserScreenNavigationEntry {

	@Override
	public String getActionCommandName() {
		return "/users_admin/update_user_contact_information_form";
	}

	@Override
	public String getCategoryKey() {
		return UserScreenNavigationEntryConstants.CATEGORY_KEY_CONTACT;
	}

	@Override
	public String getEntryKey() {
		return UserScreenNavigationEntryConstants.ENTRY_KEY_CONTACT_INFORMATION;
	}

	@Override
	public String getJspPath() {
		return "/user/contact_information.jsp";
	}

	@Override
	public boolean isVisible(User user, User selUser) {
		if (selUser == null) {
			return false;
		}

		return true;
	}

}