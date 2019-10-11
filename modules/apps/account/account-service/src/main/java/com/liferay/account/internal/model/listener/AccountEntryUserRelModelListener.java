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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.persistence.AccountEntryUserRelPersistence;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = ModelListener.class)
public class AccountEntryUserRelModelListener
	extends BaseModelListener<AccountEntryUserRel> {

	@Override
	public void onAfterCreate(AccountEntryUserRel accountEntryUserRel)
		throws ModelListenerException {

		if (accountEntryUserRel.getAccountEntryId() ==
				AccountConstants.DEFAULT_ACCOUNT_ENTRY_ID) {

			return;
		}

		AccountEntryUserRel defaultAccountEntryUserRel =
			_accountEntryUserRelPersistence.fetchByAEI_AUI(
				AccountConstants.DEFAULT_ACCOUNT_ENTRY_ID,
				accountEntryUserRel.getAccountUserId());

		if (defaultAccountEntryUserRel != null) {
			_accountEntryUserRelLocalService.deleteAccountEntryUserRel(
				defaultAccountEntryUserRel);
		}
	}

	@Override
	public void onAfterRemove(AccountEntryUserRel accountEntryUserRel)
		throws ModelListenerException {

		int userAccountsCount = _accountEntryUserRelPersistence.countByAUI(
			accountEntryUserRel.getAccountUserId());

		if (userAccountsCount > 0) {
			return;
		}

		try {
			_accountEntryUserRelLocalService.addAccountEntryUserRel(
				AccountConstants.DEFAULT_ACCOUNT_ENTRY_ID,
				accountEntryUserRel.getAccountUserId());
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private AccountEntryUserRelPersistence _accountEntryUserRelPersistence;

}