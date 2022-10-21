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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Drew Brokke
 */
@FunctionalInterface
public interface AccountEntryMod {

	public static AccountEntryMod withAccountGroups(
		AccountGroup... accountGroups) {

		return accountEntryInfo ->
			accountEntryInfo.accountGroups = accountGroups;
	}

	public static AccountEntryMod withAssetTagNames(String... assetTagNames) {
		return accountEntryInfo ->
			accountEntryInfo.assetTagNames = assetTagNames;
	}

	public static AccountEntryMod withDescription(String description) {
		return accountEntryInfo -> accountEntryInfo.description = description;
	}

	public static AccountEntryMod withDomains(String... domains) {
		return accountEntryInfo -> accountEntryInfo.domains = domains;
	}

	public static AccountEntryMod withName(String name) {
		return accountEntryInfo -> accountEntryInfo.name = name;
	}

	public static AccountEntryMod withOrganizations(
		Organization... organizations) {

		return accountEntryInfo ->
			accountEntryInfo.organizations = organizations;
	}

	public static AccountEntryMod withOwner(User user) {
		return accountEntryInfo -> accountEntryInfo.userId = user.getUserId();
	}

	public static AccountEntryMod withParentAccount(
		AccountEntry parentAccountEntry) {

		return accountEntryInfo ->
			accountEntryInfo.parentAccountEntryId =
				parentAccountEntry.getAccountEntryId();
	}

	public static AccountEntryMod withStatus(int status) {
		return accountEntryInfo -> accountEntryInfo.status = status;
	}

	public static AccountEntryMod withStatusInactive() {
		return withStatus(WorkflowConstants.STATUS_INACTIVE);
	}

	public static AccountEntryMod withType(String type) {
		return accountEntryInfo -> accountEntryInfo.type = type;
	}

	public static AccountEntryMod withTypePerson() {
		return withType(AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON);
	}

	public static AccountEntryMod withUsers(User... users) {
		return accountEntryInfo -> accountEntryInfo.users = users;
	}

	public void modify(AccountEntryInfo accountEntryInfo) throws Exception;

}