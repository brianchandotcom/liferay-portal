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

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.base.AccountGroupRelServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=account",
		"json.web.service.context.path=AccountGroupRel"
	},
	service = AopService.class
)
public class AccountGroupRelServiceImpl extends AccountGroupRelServiceBaseImpl {

	@Override
	public AccountGroupRel addAccountGroupRel(
			long accountGroupId, String className, long classPK)
		throws PortalException {

		if (Objects.equals(AccountEntry.class.getName(), className)) {
			_accountGroupModelResourcePermission.check(
				getPermissionChecker(), accountGroupId,
				AccountActionKeys.ASSIGN_ACCOUNTS);
		}

		return accountGroupRelLocalService.addAccountGroupRel(
			accountGroupId, className, classPK);
	}

	@Override
	public void addAccountGroupRels(
			long accountGroupId, String className, long[] classPKs)
		throws PortalException {

		if (Objects.equals(AccountEntry.class.getName(), className)) {
			_accountGroupModelResourcePermission.check(
				getPermissionChecker(), accountGroupId,
				AccountActionKeys.ASSIGN_ACCOUNTS);
		}

		accountGroupRelLocalService.addAccountGroupRels(
			accountGroupId, className, classPKs);
	}

	@Override
	public void deleteAccountGroupRels(
			long accountGroupId, String className, long[] classPKs)
		throws PortalException {

		if (Objects.equals(AccountEntry.class.getName(), className)) {
			_accountGroupModelResourcePermission.check(
				getPermissionChecker(), accountGroupId,
				AccountActionKeys.ASSIGN_ACCOUNTS);
		}

		accountGroupRelLocalService.deleteAccountGroupRels(
			accountGroupId, className, classPKs);
	}

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountGroup)"
	)
	private ModelResourcePermission<AccountGroup>
		_accountGroupModelResourcePermission;

}