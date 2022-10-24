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

package com.liferay.account.service.test.util;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.account.service.AccountEntryOrganizationRelLocalServiceUtil;
import com.liferay.account.service.AccountEntryUserRelLocalServiceUtil;
import com.liferay.account.service.AccountGroupRelLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Drew Brokke
 */
public class AccountEntryTestUtil {

	public static List<AccountEntry> addAccountEntries(
			int number, AccountEntryMod... accountEntryMods)
		throws Exception {

		List<AccountEntry> accountEntries = new ArrayList<>();

		for (int i = 0; i < number; i++) {
			accountEntries.add(addAccountEntry(accountEntryMods));
		}

		return accountEntries;
	}

	public static AccountEntry addAccountEntry(
			AccountEntryMod... accountEntryMods)
		throws Exception {

		AccountEntryInfo accountEntryInfo = new AccountEntryInfo();

		if (ArrayUtil.isNotEmpty(accountEntryMods)) {
			for (AccountEntryMod accountEntryMod : accountEntryMods) {
				accountEntryMod.modify(accountEntryInfo);
			}
		}

		return _addAccountEntry(accountEntryInfo);
	}

	private static AccountEntry _addAccountEntry(
			AccountEntryInfo accountEntryInfo)
		throws Exception {

		ServiceContext serviceContext = accountEntryInfo.serviceContext;

		if (serviceContext == null) {
			User user = UserLocalServiceUtil.getUser(accountEntryInfo.userId);

			Group group = GroupLocalServiceUtil.getGroup(
				user.getCompanyId(), GroupConstants.GUEST);

			serviceContext = ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());
		}

		if (ArrayUtil.isNotEmpty(accountEntryInfo.assetTagNames)) {
			serviceContext.setAssetTagNames(accountEntryInfo.assetTagNames);
		}

		AccountEntry accountEntry =
			AccountEntryLocalServiceUtil.addAccountEntry(
				accountEntryInfo.userId, accountEntryInfo.parentAccountEntryId,
				accountEntryInfo.name, accountEntryInfo.description,
				accountEntryInfo.domains, accountEntryInfo.emailAddress,
				accountEntryInfo.logoBytes, accountEntryInfo.taxIdNumber,
				accountEntryInfo.type, accountEntryInfo.status, serviceContext);

		if (ArrayUtil.isNotEmpty(accountEntryInfo.accountGroups)) {
			for (AccountGroup accountGroup : accountEntryInfo.accountGroups) {
				AccountGroupRelLocalServiceUtil.addAccountGroupRel(
					accountGroup.getAccountGroupId(),
					AccountEntry.class.getName(),
					accountEntry.getAccountEntryId());
			}
		}

		if (ArrayUtil.isNotEmpty(accountEntryInfo.organizations)) {
			AccountEntryOrganizationRelLocalServiceUtil.
				addAccountEntryOrganizationRels(
					accountEntry.getAccountEntryId(),
					ListUtil.toLongArray(
						Arrays.asList(accountEntryInfo.organizations),
						Organization.ORGANIZATION_ID_ACCESSOR));
		}

		if (ArrayUtil.isNotEmpty(accountEntryInfo.users)) {
			AccountEntryUserRelLocalServiceUtil.addAccountEntryUserRels(
				accountEntry.getAccountEntryId(),
				ListUtil.toLongArray(
					Arrays.asList(accountEntryInfo.users),
					User.USER_ID_ACCESSOR));
		}

		return accountEntry;
	}

}