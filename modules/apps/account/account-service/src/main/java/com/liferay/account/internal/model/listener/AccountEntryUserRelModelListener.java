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
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

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

		_updateDefaultAccountAssignment(accountEntryUserRel.getAccountUserId());
	}

	@Override
	public void onAfterRemove(AccountEntryUserRel accountEntryUserRel)
		throws ModelListenerException {

		_updateDefaultAccountAssignment(accountEntryUserRel.getAccountUserId());
	}

	private void _updateDefaultAccountAssignment(long accountUserId)
		throws ModelListenerException {

		List<AccountEntryUserRel> accountEntryUserRels =
			_accountEntryUserRelPersistence.findByAUI(accountUserId);

		if (ListUtil.isEmpty(accountEntryUserRels)) {
			try {
				_accountEntryUserRelLocalService.addAccountEntryUserRel(
					AccountConstants.DEFAULT_ACCOUNT_ENTRY_ID, accountUserId);
			}
			catch (PortalException pe) {
				throw new ModelListenerException(pe);
			}
		}
		else if (accountEntryUserRels.size() > 1) {
			accountEntryUserRels.stream(
			).filter(
				accountEntryUserRel ->
					accountEntryUserRel.getAccountEntryId() ==
						AccountConstants.DEFAULT_ACCOUNT_ENTRY_ID
			).forEach(
				_accountEntryUserRelLocalService::deleteAccountEntryUserRel
			);
		}
	}

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private AccountEntryUserRelPersistence _accountEntryUserRelPersistence;

}