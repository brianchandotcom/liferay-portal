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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.function.UnaryOperator;

/**
 * @author Drew Brokke
 */
public class AccountEntryArgs {

	public static final BuilderOperator STATUS_INACTIVE =
		builder -> builder.setStatus(WorkflowConstants.STATUS_INACTIVE);

	public static final BuilderOperator TYPE_PERSON =
		builder -> builder.setType(AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON);

	public static Builder builder() throws PortalException {
		return new Builder(new AccountEntryArgs());
	}

	public static BuilderOperator withAccountGroups(
		AccountGroup... accountGroups) {

		return builder -> builder.setAccountGroups(accountGroups);
	}

	public static BuilderOperator withDescription(String description) {
		return builder -> builder.setDescription(description);
	}

	public static BuilderOperator withDomains(String... domains) {
		return builder -> builder.setDomains(domains);
	}

	public static BuilderOperator withName(String name) {
		return builder -> builder.setName(name);
	}

	public static BuilderOperator withOrganizations(
		Organization... organizations) {

		return builder -> builder.setOrganizations(organizations);
	}

	public static BuilderOperator withOwner(User user) {
		return builder -> builder.setUserId(user.getUserId());
	}

	public static BuilderOperator withUsers(User... users) {
		return builder -> builder.setUsers(users);
	}

	public AccountGroup[] accountGroups = null;
	public String[] assetTagNames = null;
	public String description = RandomTestUtil.randomString(50);
	public String[] domains = null;
	public String emailAddress = null;
	public byte[] logoBytes = null;
	public String name = RandomTestUtil.randomString(50);
	public Organization[] organizations = null;
	public AccountEntry parentAccountEntry = null;
	public boolean restrictMembership = true;
	public ServiceContext serviceContext = null;
	public int status = WorkflowConstants.STATUS_APPROVED;
	public String taxIdNumber = RandomTestUtil.randomString(50);
	public String type = AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS;
	public long userId = TestPropsValues.getUserId();
	public User[] users = null;

	public static class Builder {

		public AccountEntryArgs build() {
			return _accountEntryArgs;
		}

		public Builder setAccountGroups(AccountGroup... accountGroups) {
			_accountEntryArgs.accountGroups = accountGroups;

			return this;
		}

		public Builder setAssetTagNames(String... assetTagNames) {
			_accountEntryArgs.assetTagNames = assetTagNames;

			return this;
		}

		public Builder setDescription(String description) {
			_accountEntryArgs.description = description;

			return this;
		}

		public Builder setDomains(String... domains) {
			_accountEntryArgs.domains = domains;

			return this;
		}

		public Builder setEmailAddress(String emailAddress) {
			_accountEntryArgs.emailAddress = emailAddress;

			return this;
		}

		public Builder setLogoBytes(byte[] logoBytes) {
			_accountEntryArgs.logoBytes = logoBytes;

			return this;
		}

		public Builder setName(String name) {
			_accountEntryArgs.name = name;

			return this;
		}

		public Builder setOrganizations(Organization... organizations) {
			_accountEntryArgs.organizations = organizations;

			return this;
		}

		public Builder setParentAccountEntry(AccountEntry parentAccountEntry) {
			_accountEntryArgs.parentAccountEntry = parentAccountEntry;

			return this;
		}

		public Builder setRestrictMembership(boolean restrictMembership) {
			_accountEntryArgs.restrictMembership = restrictMembership;

			return this;
		}

		public Builder setServiceContext(ServiceContext serviceContext) {
			_accountEntryArgs.serviceContext = serviceContext;

			return this;
		}

		public Builder setStatus(int status) {
			_accountEntryArgs.status = status;

			return this;
		}

		public Builder setTaxIdNumber(String taxIdNumber) {
			_accountEntryArgs.taxIdNumber = taxIdNumber;

			return this;
		}

		public Builder setType(String type) {
			_accountEntryArgs.type = type;

			return this;
		}

		public Builder setUserId(long userId) {
			_accountEntryArgs.userId = userId;

			return this;
		}

		public Builder setUsers(User... users) {
			_accountEntryArgs.users = users;

			return this;
		}

		private Builder(AccountEntryArgs accountEntryArgs) {
			_accountEntryArgs = accountEntryArgs;
		}

		private final AccountEntryArgs _accountEntryArgs;

	}

	@FunctionalInterface
	public interface BuilderOperator extends UnaryOperator<Builder> {
	}

	private AccountEntryArgs() throws PortalException {
	}

}