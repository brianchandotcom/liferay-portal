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

package com.liferay.account.internal.instance.lifecycle;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class AddDefaultAccountGroupPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (_accountGroupLocalService.hasDefaultAccountGroup(
				company.getCompanyId())) {

			return;
		}

		AccountGroup accountGroup =
			_accountGroupLocalService.createAccountGroup(
				_counterLocalService.increment());

		accountGroup.setCompanyId(company.getCompanyId());

		User user = _userLocalService.getDefaultUser(company.getCompanyId());

		accountGroup.setUserId(user.getUserId());
		accountGroup.setUserName(user.getFullName());

		accountGroup.setDefaultAccountGroup(true);
		accountGroup.setDescription(
			"This account group is used for guest users.");
		accountGroup.setName(AccountConstants.ACCOUNT_GROUP_NAME_GUEST);

		_accountGroupLocalService.addAccountGroup(accountGroup);
	}

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private UserLocalService _userLocalService;

}