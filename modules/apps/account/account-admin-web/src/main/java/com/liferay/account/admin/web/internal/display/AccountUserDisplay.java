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

package com.liferay.account.admin.web.internal.display;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;

/**
 * @author Pei-Jung Lan
 */
public class AccountUserDisplay {

	public static AccountUserDisplay of(User user) {
		if (user == null) {
			return null;
		}

		return new AccountUserDisplay(user);
	}

	public String getAccountRoles() {
		return _accountRoles;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public String getJobTitle() {
		return _jobTitle;
	}

	public String getName() {
		return _name;
	}

	public long getUserId() {
		return _userId;
	}

	private AccountUserDisplay(User user) {
		_userId = user.getUserId();
		_name = user.getFullName();
		_emailAddress = user.getEmailAddress();
		_jobTitle = user.getJobTitle();
		_accountRoles = StringPool.BLANK;
	}

	private final String _accountRoles;
	private final String _emailAddress;
	private final String _jobTitle;
	private final String _name;
	private final long _userId;

}