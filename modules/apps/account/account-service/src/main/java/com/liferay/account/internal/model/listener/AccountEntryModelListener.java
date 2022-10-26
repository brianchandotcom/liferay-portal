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

package com.liferay.account.internal.model.listener;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = ModelListener.class)
public class AccountEntryModelListener extends BaseModelListener<AccountEntry> {

	@Override
	public void onAfterRemove(AccountEntry accountEntry)
		throws ModelListenerException {

		// Account Groups

		List<AccountGroupRel> accountGroupRels =
			_accountGroupRelLocalService.getAccountGroupRels(
				AccountEntry.class.getName(), accountEntry.getAccountEntryId());

		for (AccountGroupRel accountGroupRel : accountGroupRels) {
			_accountGroupRelLocalService.deleteAccountGroupRel(accountGroupRel);
		}

		// Account Organizations

		List<AccountEntryOrganizationRel> accountEntryOrganizationRels =
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRels(
					accountEntry.getAccountEntryId());

		for (AccountEntryOrganizationRel accountEntryOrganizationRel :
				accountEntryOrganizationRels) {

			_accountEntryOrganizationRelLocalService.
				deleteAccountEntryOrganizationRel(accountEntryOrganizationRel);
		}

		// Account Roles

		List<AccountRole> accountRoles =
			_accountRoleLocalService.getAccountRolesByAccountEntryIds(
				new long[] {accountEntry.getAccountEntryId()});

		try {
			for (AccountRole accountRole : accountRoles) {
				_accountRoleLocalService.deleteAccountRole(accountRole);
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}

		// Account Users

		List<AccountEntryUserRel> accountEntryUserRels =
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountEntryId(
					accountEntry.getAccountEntryId());

		for (AccountEntryUserRel accountEntryUserRel : accountEntryUserRels) {
			_accountEntryUserRelLocalService.deleteAccountEntryUserRel(
				accountEntryUserRel);
		}
	}

	@Override
	public void onAfterUpdate(
		AccountEntry originalAccountEntry, AccountEntry accountEntry) {

		_reindexAccountEntry(accountEntry);
	}

	private void _reindexAccountEntry(AccountEntry accountEntry) {
		try {
			Indexer<AccountEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(AccountEntry.class);

			indexer.reindex(accountEntry);
		}
		catch (SearchException searchException) {
			throw new ModelListenerException(searchException);
		}
	}

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private AccountGroupRelLocalService _accountGroupRelLocalService;

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

}