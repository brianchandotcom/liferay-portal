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

package com.liferay.account.service.impl;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.exception.DuplicateAccountGroupAccountEntryRelException;
import com.liferay.account.model.AccountGroupAccountEntryRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.base.AccountGroupAccountEntryRelLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the account group account entry rel local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.account.service.AccountGroupAccountEntryRelLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupAccountEntryRelLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.account.model.AccountGroupAccountEntryRel",
	service = AopService.class
)
public class AccountGroupAccountEntryRelLocalServiceImpl
	extends AccountGroupAccountEntryRelLocalServiceBaseImpl {

	@Override
	public AccountGroupAccountEntryRel addAccountGroupAccountEntryRel(
			long accountGroupId, long accountEntryId)
		throws PortalException {

		AccountGroupAccountEntryRel accountGroupAccountEntryRel =
			accountGroupAccountEntryRelPersistence.fetchByAGI_AEI(
				accountGroupId, accountEntryId);

		if (accountGroupAccountEntryRel != null) {
			throw new DuplicateAccountGroupAccountEntryRelException();
		}

		if (accountEntryId != AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT) {
			_accountEntryLocalService.getAccountEntry(accountEntryId);
		}

		_accountGroupLocalService.getAccountGroup(accountGroupId);

		accountGroupAccountEntryRel = createAccountGroupAccountEntryRel(
			counterLocalService.increment());

		accountGroupAccountEntryRel.setAccountGroupId(accountGroupId);
		accountGroupAccountEntryRel.setAccountEntryId(accountEntryId);

		return addAccountGroupAccountEntryRel(accountGroupAccountEntryRel);
	}

	@Override
	public void addAccountGroupAccountEntryRels(
			long accountGroupId, long[] accountEntryIds)
		throws PortalException {

		for (long accountEntryId : accountEntryIds) {
			addAccountGroupAccountEntryRel(accountGroupId, accountEntryId);
		}
	}

	@Override
	public void deleteAccountGroupAccountEntryRels(
			long accountGroupId, long[] accountEntryIds)
		throws PortalException {

		for (long accountEntryId : accountEntryIds) {
			accountGroupAccountEntryRelPersistence.removeByAGI_AEI(
				accountGroupId, accountEntryId);
		}
	}

	@Override
	public List<AccountGroupAccountEntryRel>
		getAccountGroupAccountEntryRelsByAccountEntryId(long accountEntryId) {

		return accountGroupAccountEntryRelPersistence.findByAccountEntryId(
			accountEntryId);
	}

	@Override
	public List<AccountGroupAccountEntryRel>
		getAccountGroupAccountEntryRelsByAccountGroupId(long accountGroupId) {

		return accountGroupAccountEntryRelPersistence.findByAccountGroupId(
			accountGroupId);
	}

	@Override
	public long getAccountGroupAccountEntryRelsCountByAccountGroupId(
		long accountGroupId) {

		return accountGroupAccountEntryRelPersistence.countByAccountGroupId(
			accountGroupId);
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

}