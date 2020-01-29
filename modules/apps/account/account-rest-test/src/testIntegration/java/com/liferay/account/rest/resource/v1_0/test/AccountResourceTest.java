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

package com.liferay.account.rest.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.rest.client.dto.v1_0.Account;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class AccountResourceTest extends BaseAccountResourceTestCase {

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected Account testGetAccount_addAccount() throws Exception {
		return _addAccount();
	}

	@Override
	protected Account testGraphQLAccount_addAccount() throws Exception {
		return _addAccount();
	}

	private Account _addAccount() throws Exception {
		return _toAccount(_addAccountEntry());
	}

	private AccountEntry _addAccountEntry() throws PortalException {
		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			TestPropsValues.getUserId(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, WorkflowConstants.STATUS_APPROVED);

		_accountEntries.add(accountEntry);

		return accountEntry;
	}

	private String[] _getDomainsArray(AccountEntry accountEntry) {
		List<String> domains = StringUtil.split(accountEntry.getDomains());

		return domains.toArray(new String[0]);
	}

	private Account _toAccount(AccountEntry accountEntry) throws Exception {
		if (accountEntry == null) {
			return null;
		}

		return new Account() {
			{
				setDescription(accountEntry.getDescription());
				setDomains(_getDomainsArray(accountEntry));
				setId(accountEntry.getAccountEntryId());
				setName(accountEntry.getName());
				setParentAccountId(accountEntry.getParentAccountEntryId());
				setStatus(accountEntry.getStatus());
			}
		};
	}

	@DeleteAfterTestRun
	private final List<AccountEntry> _accountEntries = new ArrayList<>();

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

}