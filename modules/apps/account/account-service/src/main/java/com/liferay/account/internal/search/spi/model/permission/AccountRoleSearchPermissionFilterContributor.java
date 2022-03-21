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

package com.liferay.account.internal.search.spi.model.permission;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.role.AccountRolePermissionThreadLocal;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.search.spi.model.permission.SearchPermissionFilterContributor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = SearchPermissionFilterContributor.class)
public class AccountRoleSearchPermissionFilterContributor
	implements SearchPermissionFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, long companyId, long[] groupIds,
		long userId, PermissionChecker permissionChecker, String className) {

		if (!className.equals(AccountRole.class.getName())) {
			return;
		}

		try {
			_addAccountRoleIdsFilter(booleanFilter, userId, permissionChecker);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private void _addAccountRoleIdsFilter(
			BooleanFilter booleanFilter, long userId,
			PermissionChecker permissionChecker)
		throws PortalException {

		TermsFilter classPksFilter = new TermsFilter(Field.ENTRY_CLASS_PK);

		Set<Long> accountRoleIds = new HashSet<>();

		List<Long> accountEntryIds = new ArrayList<>();

		long permissionAccountEntryId =
			AccountRolePermissionThreadLocal.getAccountEntryId();

		if (permissionAccountEntryId !=
				AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT) {

			accountEntryIds.add(permissionAccountEntryId);
		}

		if (accountEntryIds.isEmpty()) {
			List<AccountEntry> accountEntries =
				_accountEntryLocalService.getUserAccountEntries(
					userId, AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT,
					null,
					new String[] {
						AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
						AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON
					},
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (AccountEntry accountEntry : accountEntries) {
				accountEntryIds.add(accountEntry.getAccountEntryId());
			}
		}

		for (long accountEntryId : accountEntryIds) {
			List<AccountRole> accountRoles =
				_accountRoleLocalService.getAccountRolesByAccountEntryIds(
					new long[] {
						AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
						accountEntryId
					});

			try (SafeCloseable safeCloseable =
					AccountRolePermissionThreadLocal.setWithSafeCloseable(
						accountEntryId)) {

				for (AccountRole accountRole : accountRoles) {
					if (!accountRoleIds.contains(accountRole.getRoleId()) &&
						_accountRoleModelResourcePermission.contains(
							permissionChecker, accountRole.getAccountRoleId(),
							ActionKeys.VIEW)) {

						accountRoleIds.add(accountRole.getAccountRoleId());
					}
				}
			}
		}

		for (long accountRoleId : accountRoleIds) {
			classPksFilter.addValue(String.valueOf(accountRoleId));
		}

		if (!classPksFilter.isEmpty()) {
			booleanFilter.add(classPksFilter, BooleanClauseOccur.SHOULD);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountRoleSearchPermissionFilterContributor.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountRole)"
	)
	private ModelResourcePermission<AccountRole>
		_accountRoleModelResourcePermission;

}