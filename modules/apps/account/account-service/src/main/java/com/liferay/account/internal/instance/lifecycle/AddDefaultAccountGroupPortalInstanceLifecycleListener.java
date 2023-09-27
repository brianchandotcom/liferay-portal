/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.instance.lifecycle;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class AddDefaultAccountGroupPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		AccountGroup accountGroup =
			_accountGroupLocalService.checkGuestAccountGroup(
				company.getCompanyId());

		AccountGroupRel accountGroupRel =
			_accountGroupRelLocalService.fetchAccountGroupRel(
				accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
				AccountConstants.ACCOUNT_ENTRY_ID_GUEST);

		if (accountGroupRel == null) {
			_accountGroupRelLocalService.addAccountGroupRel(
				accountGroup.getAccountGroupId(), AccountEntry.class.getName(),
				AccountConstants.ACCOUNT_ENTRY_ID_GUEST);
		}
	}

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private AccountGroupRelLocalService _accountGroupRelLocalService;

}