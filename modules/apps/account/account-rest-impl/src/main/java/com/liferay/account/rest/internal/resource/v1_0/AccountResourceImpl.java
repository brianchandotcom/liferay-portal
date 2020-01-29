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

package com.liferay.account.rest.internal.resource.v1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.rest.dto.v1_0.Account;
import com.liferay.account.rest.resource.v1_0.AccountResource;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.petra.string.StringUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Drew Brokke
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/account.properties",
	scope = ServiceScope.PROTOTYPE, service = AccountResource.class
)
public class AccountResourceImpl extends BaseAccountResourceImpl {

	@Override
	public Account getAccount(Long accountId) throws Exception {
		return _toAccount(_accountEntryLocalService.getAccountEntry(accountId));
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

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

}