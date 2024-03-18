/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.constants;

import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalServiceUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
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

	public static final String ROLE_NAME_RETURNS_MANAGER = "Returns Manager";

	public static final String ROLE_NAME_SUPPLIER = "Supplier";

	public static final Map<String, Map<Locale, String>> roleDescriptionsMap =
		HashMapBuilder.<String, Map<Locale, String>>put(
			REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR,
			Collections.singletonMap(
				LocaleUtil.US,
				"Account Administrators are super users of their account.")
		).put(
			REQUIRED_ROLE_NAME_ACCOUNT_MANAGER,
			Collections.singletonMap(
				LocaleUtil.US,
				"Account Managers who belong to an organization can " +
					"administer all accounts associated to that organization.")
		).put(
			REQUIRED_ROLE_NAME_ACCOUNT_MEMBER,
			Collections.singletonMap(
				LocaleUtil.US,
				"All users who belong to an account have this role within " +
					"that account.")
		).build();

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