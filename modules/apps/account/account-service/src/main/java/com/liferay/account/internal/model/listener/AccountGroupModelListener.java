/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.model.listener;

import com.liferay.account.exception.DefaultAccountGroupException;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.util.PortalInstances;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = ModelListener.class)
public class AccountGroupModelListener extends BaseModelListener<AccountGroup> {

	@Override
	public void onAfterRemove(AccountGroup accountGroup) {
		if (PortalInstances.isCurrentCompanyInDeletionProcess()) {
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