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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

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

	@Override
	public Page<Account> getAccountsPage(
			String keywords, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> {
			},
			filter, AccountEntry.class, keywords, pagination,
			queryConfig -> {
			},
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());

				if (Validator.isNotNull(keywords)) {
					searchContext.setKeywords(keywords);
				}
			},
			document -> {
				long accountEntryId = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				return _toAccount(
					_accountEntryLocalService.getAccountEntry(accountEntryId));
			},
			sorts);
	}

	private Account _toAccount(AccountEntry accountEntry) throws Exception {
		return new Account() {
			{
				description = accountEntry.getDescription();
				domains = StringUtil.split(accountEntry.getDomains());
				id = accountEntry.getAccountEntryId();
				name = accountEntry.getName();
				parentAccountId = accountEntry.getParentAccountEntryId();
				status = accountEntry.getStatus();
			}
		};
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

}