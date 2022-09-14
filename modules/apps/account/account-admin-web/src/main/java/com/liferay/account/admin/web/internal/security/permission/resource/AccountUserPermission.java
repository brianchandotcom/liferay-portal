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

package com.liferay.account.admin.web.internal.security.permission.resource;

import com.liferay.account.admin.web.internal.util.AccountEntryEmailAddressValidatorFactoryUtil;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.validator.AccountEntryEmailAddressValidator;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;

import java.util.Objects;

/**
 * @author Drew Brokke
 */
public class AccountUserPermission {

	public static void checkEditUserPermission(
			PermissionChecker permissionChecker, String portletId,
			AccountEntry accountEntry, User accountUser)
		throws PrincipalException {

		if (!hasEditUserPermission(
				permissionChecker, portletId, accountEntry, accountUser)) {

			throw new PrincipalException();
		}
	}

	public static boolean hasEditUserPermission(
		PermissionChecker permissionChecker, String portletId,
		AccountEntry accountEntry, User accountUser) {

		if ((accountEntry == null) || (accountUser == null) ||
			!Objects.equals(
				portletId, AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT)) {

			return false;
		}

		if (UserPermissionUtil.contains(
				permissionChecker, accountUser.getUserId(),
				ActionKeys.UPDATE)) {

			return true;
		}

		AccountEntryEmailAddressValidator accountEntryEmailAddressValidator =
			AccountEntryEmailAddressValidatorFactoryUtil.create(
				accountEntry.getCompanyId(), accountEntry.getDomainsArray());

		if (accountEntryEmailAddressValidator.isValidDomainStrict(
				accountUser.getEmailAddress()) &&
			AccountEntryPermission.contains(
				permissionChecker, accountEntry.getAccountEntryId(),
				ActionKeys.MANAGE_USERS)) {

			return true;
		}

		return false;
	}

}