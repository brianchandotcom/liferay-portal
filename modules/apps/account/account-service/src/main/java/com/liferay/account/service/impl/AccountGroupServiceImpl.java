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
import com.liferay.account.service.base.AccountGroupServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.PortalPermission;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=account",
		"json.web.service.context.path=AccountGroup"
	},
	service = AopService.class
)
public class AccountGroupServiceImpl extends AccountGroupServiceBaseImpl {

	@Override
	public AccountGroup addAccountGroup(
			long userId, String description, String name,
			ServiceContext serviceContext)
		throws PortalException {

		_portalPermission.check(
			getPermissionChecker(), AccountActionKeys.ADD_ACCOUNT_GROUP);

		return accountGroupLocalService.addAccountGroup(
			userId, description, name, serviceContext);
	}

	@Override
	public AccountGroup deleteAccountGroup(long accountGroupId)
		throws PortalException {

		_accountGroupModelResourcePermission.check(
			getPermissionChecker(), accountGroupId, ActionKeys.DELETE);

		return accountGroupLocalService.deleteAccountGroup(accountGroupId);
	}

	@Override
	public void deleteAccountGroups(long[] accountGroupIds)
		throws PortalException {

		for (long accountGroupId : accountGroupIds) {
			deleteAccountGroup(accountGroupId);
		}
	}

	@Override
	public AccountGroup fetchAccountGroupByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		AccountGroup accountGroup =
			accountGroupLocalService.fetchAccountGroupByExternalReferenceCode(
				externalReferenceCode, companyId);

		if (accountGroup != null) {
			_accountGroupModelResourcePermission.check(
				getPermissionChecker(), accountGroup, ActionKeys.VIEW);
		}

		return accountGroup;
	}

	@Override
	public AccountGroup getAccountGroup(long accountGroupId)
		throws PortalException {

		_accountGroupModelResourcePermission.check(
			getPermissionChecker(), accountGroupId, ActionKeys.VIEW);

		return accountGroupLocalService.getAccountGroup(accountGroupId);
	}

	@Override
	public List<AccountGroup> getAccountGroupsByAccountEntryId(
			long accountEntryId, int start, int end)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), accountEntryId,
			AccountActionKeys.VIEW_ACCOUNT_GROUPS);

		return accountGroupLocalService.getAccountGroupsByAccountEntryId(
			accountEntryId, start, end);
	}

	@Override
	public int getAccountGroupsCountByAccountEntryId(long accountEntryId)
		throws PortalException {

		_accountEntryModelResourcePermission.check(
			getPermissionChecker(), accountEntryId,
			AccountActionKeys.VIEW_ACCOUNT_GROUPS);

		return accountGroupLocalService.getAccountGroupsCountByAccountEntryId(
			accountEntryId);
	}

	@Override
	public BaseModelSearchResult<AccountGroup> searchAccountGroups(
			long companyId, String keywords, int start, int end,
			OrderByComparator<AccountGroup> orderByComparator)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		return accountGroupLocalService.searchAccountGroups(
			companyId, keywords,
			LinkedHashMapBuilder.<String, Object>put(
				"permissionUserId", permissionChecker.getUserId()
			).build(),
			start, end, orderByComparator);
	}

	@Override
	public AccountGroup updateAccountGroup(
			long accountGroupId, String description, String name,
			ServiceContext serviceContext)
		throws PortalException {

		_accountGroupModelResourcePermission.check(
			getPermissionChecker(), accountGroupId, ActionKeys.UPDATE);

		return accountGroupLocalService.updateAccountGroup(
			accountGroupId, description, name, serviceContext);
	}

	@Override
	public AccountGroup updateExternalReferenceCode(
			long accountGroupId, String externalReferenceCode)
		throws PortalException {

		_accountGroupModelResourcePermission.check(
			getPermissionChecker(), accountGroupId, ActionKeys.UPDATE);

		return accountGroupLocalService.updateExternalReferenceCode(
			accountGroupId, externalReferenceCode);
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private volatile ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountGroup)"
	)
	private ModelResourcePermission<AccountGroup>
		_accountGroupModelResourcePermission;

	@Reference
	private PortalPermission _portalPermission;

}