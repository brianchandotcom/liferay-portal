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

import com.liferay.account.exception.DefaultAccountGroupException;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = ModelListener.class)
public class AccountGroupModelListener extends BaseModelListener<AccountGroup> {

	@Override
	public void onAfterRemove(AccountGroup accountGroup) {
		if (CompanyThreadLocal.isDeleteInProcess()) {
			return;
		}

		if (accountGroup.isDefaultAccountGroup()) {
			throw new ModelListenerException(
				new DefaultAccountGroupException.
					MustNotDeleteDefaultAccountGroup(
						accountGroup.getAccountGroupId()));
		}
	}

	@Override
	public void onBeforeCreate(AccountGroup accountGroup)
		throws ModelListenerException {

		if (accountGroup.isDefaultAccountGroup() &&
			_accountGroupLocalService.hasDefaultAccountGroup(
				accountGroup.getCompanyId())) {

			throw new ModelListenerException(
				new DefaultAccountGroupException.
					MustNotDuplicateDefaultAccountGroup(
						accountGroup.getCompanyId()));
		}
	}

	@Override
	public void onBeforeUpdate(
			AccountGroup originalAccountGroup, AccountGroup accountGroup)
		throws ModelListenerException {

		if (accountGroup.isDefaultAccountGroup()) {
			throw new ModelListenerException(
				new DefaultAccountGroupException.
					MustNotUpdateDefaultAccountGroup(
						accountGroup.getAccountGroupId()));
		}
	}

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

}