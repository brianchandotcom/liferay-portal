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

package com.liferay.account.service.helper;

import com.liferay.account.constants.AccountEntryConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.function.Consumer;

/**
 * @author Drew Brokke
 */
public class AccountEntryTestHelper {

	public AccountEntryTestHelper(
		AccountEntryLocalService accountEntryLocalService, long defaultUserId) {

		_accountEntryLocalService = accountEntryLocalService;
		_defaultUerId = defaultUserId;
	}

	public AccountEntry addAccountEntry() throws Exception {
		return buildAccountEntry(
			accountEntryBuilder -> {
			});
	}

	public AccountEntry addInactiveAccountEntry() throws Exception {
		return buildAccountEntry(
			accountEntryBuilder -> accountEntryBuilder.setStatus(
				WorkflowConstants.STATUS_INACTIVE));
	}

	public AccountEntry buildAccountEntry(
			Consumer<AccountEntryBuilder> accountEntryBuilderConsumer)
		throws Exception {

		AccountEntryBuilder accountEntryBuilder = new AccountEntryBuilder();

		accountEntryBuilderConsumer.accept(accountEntryBuilder);

		return accountEntryBuilder.build();
	}

	public class AccountEntryBuilder {

		public AccountEntry build() throws Exception {
			return _accountEntryLocalService.addAccountEntry(
				getUserId(), getParentAccountEntryId(), getName(),
				getDescription(), getLogoId(), getStatus());
		}

		public String getDescription() {
			return _description;
		}

		public long getLogoId() {
			return _logoId;
		}

		public String getName() {
			return _name;
		}

		public long getParentAccountEntryId() {
			return _parentAccountEntryId;
		}

		public int getStatus() {
			return _status;
		}

		public long getUserId() {
			return _userId;
		}

		public AccountEntryBuilder setDescription(String description) {
			_description = description;

			return this;
		}

		public AccountEntryBuilder setLogoId(long logoId) {
			_logoId = logoId;

			return this;
		}

		public AccountEntryBuilder setName(String name) {
			_name = name;

			return this;
		}

		public AccountEntryBuilder setParentAccountEntryId(
			long parentAccountEntryId) {

			_parentAccountEntryId = parentAccountEntryId;

			return this;
		}

		public AccountEntryBuilder setStatus(int status) {
			_status = status;

			return this;
		}

		public AccountEntryBuilder setUserId(long userId) {
			_userId = userId;

			return this;
		}

		private AccountEntryBuilder() {
			setDescription(RandomTestUtil.randomString(50));
			setLogoId(0L);
			setName(RandomTestUtil.randomString(50));
			setParentAccountEntryId(
				AccountEntryConstants.DEFAULT_PARENT_ACCOUNT_ENTRY_ID);
			setStatus(WorkflowConstants.STATUS_APPROVED);
			setUserId(_defaultUerId);
		}

		private String _description;
		private long _logoId;
		private String _name;
		private long _parentAccountEntryId;
		private int _status;
		private long _userId;

	}

	private final AccountEntryLocalService _accountEntryLocalService;
	private final long _defaultUerId;

}