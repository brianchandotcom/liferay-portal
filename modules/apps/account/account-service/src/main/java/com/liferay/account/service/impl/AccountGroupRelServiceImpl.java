/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public AccountGroupRel deleteAccountGroupRel(long accountGroupRelId)
		throws PortalException {

		AccountGroupRel accountGroupRel =
			accountGroupRelLocalService.fetchAccountGroupRel(accountGroupRelId);

		if (accountGroupRel != null) {
			_accountGroupModelResourcePermission.check(
				getPermissionChecker(), accountGroupRel.getAccountGroupId(),
				AccountActionKeys.ASSIGN_ACCOUNTS);
		}

		return accountGroupRelLocalService.deleteAccountGroupRel(
			accountGroupRelId);
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

	@Override
	public AccountGroupRel fetchAccountGroupRel(
			long accountGroupId, String className, long classPK)
		throws PortalException {

		if (Objects.equals(AccountEntry.class.getName(), className)) {
			_accountGroupModelResourcePermission.check(
				getPermissionChecker(), accountGroupId,
				AccountActionKeys.VIEW_ACCOUNTS);
		}

		return accountGroupRelLocalService.fetchAccountGroupRel(
			accountGroupId, className, classPK);
	}

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountGroup)"
	)
	private ModelResourcePermission<AccountGroup>
		_accountGroupModelResourcePermission;

}