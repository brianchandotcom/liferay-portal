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

import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalServiceUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Objects;

/**
 * @author Drew Brokke
 */
public class AccountRoleConstants {

	public static final String REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR =
		"Account Administrator";

	public static final String REQUIRED_ROLE_NAME_ACCOUNT_MANAGER =
		"Account Manager";

	public static final String REQUIRED_ROLE_NAME_ACCOUNT_MEMBER =
		"Account Member";

	public static final String[] REQUIRED_ROLE_NAMES = {
		REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR,
		REQUIRED_ROLE_NAME_ACCOUNT_MANAGER, REQUIRED_ROLE_NAME_ACCOUNT_MEMBER
	};

	public static final String ROLE_NAME_ACCOUNT_BUYER = "Buyer";

	public static final String ROLE_NAME_ACCOUNT_DISCOUNT_MANAGER =
		"Discount Manager";

	public static final String ROLE_NAME_ACCOUNT_ORDER_MANAGER =
		"Order Manager";

	public static final String ROLE_NAME_ACCOUNT_SUPPLIER = "Account Supplier";

	public static final String ROLE_NAME_SUPPLIER = "Supplier";

	public static boolean isImpliedRole(Role role) {
		if (Objects.equals(REQUIRED_ROLE_NAME_ACCOUNT_MEMBER, role.getName())) {
			return true;
		}

		return false;
	}

	public static boolean isRequiredRole(Role role) {
		return ArrayUtil.contains(REQUIRED_ROLE_NAMES, role.getName());
	}

	public static boolean isSharedRole(Role role) {
		AccountRole accountRole =
			AccountRoleLocalServiceUtil.fetchAccountRoleByRoleId(
				role.getRoleId());

		if (Objects.equals(
				accountRole.getAccountEntryId(),
				AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT)) {

			return true;
		}

		return false;
	}

}