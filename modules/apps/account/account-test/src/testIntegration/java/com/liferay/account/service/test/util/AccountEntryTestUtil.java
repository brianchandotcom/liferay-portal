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
			int number, AccountEntryArgs.Consumer... consumers)
		throws Exception {

		List<AccountEntry> accountEntries = new ArrayList<>();

		for (int i = 0; i < number; i++) {
			accountEntries.add(addAccountEntry(consumers));
		}

		return accountEntries;
	}

	public static AccountEntry addAccountEntry(
			AccountEntryArgs.Consumer... consumers)
		throws Exception {

		AccountEntryArgs accountEntryArgs = new AccountEntryArgs();

		for (AccountEntryArgs.Consumer consumer : consumers) {
			consumer.accept(accountEntryArgs);
		}

		return _addAccountEntry(accountEntryArgs);
	}

	private static AccountEntry _addAccountEntry(
			AccountEntryArgs accountEntryArgs)
		throws Exception {

		ServiceContext serviceContext = accountEntryArgs.serviceContext;

		if (serviceContext == null) {
			User user = UserLocalServiceUtil.getUser(accountEntryArgs.userId);

			Group group = GroupLocalServiceUtil.getGroup(
				user.getCompanyId(), GroupConstants.GUEST);

			serviceContext = ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());
		}

		if (ArrayUtil.isNotEmpty(accountEntryArgs.assetTagNames)) {
			serviceContext.setAssetTagNames(accountEntryArgs.assetTagNames);
		}

		long parentAccountEntryId = 0;

		AccountEntry parentAccountEntry = accountEntryArgs.parentAccountEntry;

		if (parentAccountEntry != null) {
			parentAccountEntryId = parentAccountEntry.getAccountEntryId();
		}

		AccountEntry accountEntry =
			AccountEntryLocalServiceUtil.addAccountEntry(
				accountEntryArgs.userId, parentAccountEntryId,
				accountEntryArgs.name, accountEntryArgs.description,
				accountEntryArgs.domains, accountEntryArgs.emailAddress,
				accountEntryArgs.logoBytes, accountEntryArgs.taxIdNumber,
				accountEntryArgs.type, accountEntryArgs.status, serviceContext);

		if (ArrayUtil.isNotEmpty(accountEntryArgs.accountGroups)) {
			for (AccountGroup accountGroup : accountEntryArgs.accountGroups) {
				AccountGroupRelLocalServiceUtil.addAccountGroupRel(
					accountGroup.getAccountGroupId(),
					AccountEntry.class.getName(),
					accountEntry.getAccountEntryId());
			}
		}

		if (ArrayUtil.isNotEmpty(accountEntryArgs.organizations)) {
			AccountEntryOrganizationRelLocalServiceUtil.
				addAccountEntryOrganizationRels(
					accountEntry.getAccountEntryId(),
					ListUtil.toLongArray(
						Arrays.asList(accountEntryArgs.organizations),
						Organization.ORGANIZATION_ID_ACCESSOR));
		}

		if (ArrayUtil.isNotEmpty(accountEntryArgs.users)) {
			AccountEntryUserRelLocalServiceUtil.addAccountEntryUserRels(
				accountEntry.getAccountEntryId(),
				ListUtil.toLongArray(
					Arrays.asList(accountEntryArgs.users),
					User.USER_ID_ACCESSOR));
		}

		if (accountEntryArgs.restrictMembership !=
				accountEntry.isRestrictMembership()) {

			accountEntry =
				AccountEntryLocalServiceUtil.updateRestrictMembership(
					accountEntry.getAccountEntryId(),
					accountEntryArgs.restrictMembership);
		}

		return accountEntry;
	}

}