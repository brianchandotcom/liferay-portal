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

package com.liferay.account.constants;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Drew Brokke
 */
public class AccountRoleConstants {

	public static final String REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR =
		"Account Administrator";

	public static final String REQUIRED_ROLE_NAME_ACCOUNT_OWNER =
		"Account Owner";

	public static final String REQUIRED_ROLE_NAME_ACCOUNT_POWER_USER =
		"Account Power User";

	public static final String REQUIRED_ROLE_NAME_ACCOUNT_USER = "Account User";

	public static final String[] REQUIRED_ROLE_NAMES = {
		REQUIRED_ROLE_NAME_ACCOUNT_USER, REQUIRED_ROLE_NAME_ACCOUNT_POWER_USER,
		REQUIRED_ROLE_NAME_ACCOUNT_OWNER,
		REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR
	};

	public static boolean isRequiredRole(Role role) {
		return ArrayUtil.contains(REQUIRED_ROLE_NAMES, role.getName());
	}

}