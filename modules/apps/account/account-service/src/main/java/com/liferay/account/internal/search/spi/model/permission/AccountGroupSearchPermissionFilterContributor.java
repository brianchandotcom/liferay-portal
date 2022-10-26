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

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.search.spi.model.permission.SearchPermissionFilterContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = SearchPermissionFilterContributor.class)
public class AccountGroupSearchPermissionFilterContributor
	implements SearchPermissionFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, long companyId, long[] groupIds,
		long userId, PermissionChecker permissionChecker, String className) {

		if (!className.equals(AccountGroup.class.getName())) {
			return;
		}

		_addAccountEntryIdsFilter(booleanFilter, userId, permissionChecker);
	}

	private void _addAccountEntryIdsFilter(
		BooleanFilter booleanFilter, long userId,
		PermissionChecker permissionChecker) {

		TermsFilter accountEntryIdsTermsFilter = new TermsFilter(
			"accountEntryIds");

		try {
			for (AccountEntryUserRel accountEntryUserRel :
					_accountEntryUserRelLocalService.
						getAccountEntryUserRelsByAccountUserId(userId)) {

				if (_accountEntryModelResourcePermission.contains(
						permissionChecker,
						accountEntryUserRel.getAccountEntryId(),
						AccountActionKeys.VIEW_ACCOUNT_GROUPS)) {

					accountEntryIdsTermsFilter.addValue(
						String.valueOf(
							accountEntryUserRel.getAccountEntryId()));
				}
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		if (!accountEntryIdsTermsFilter.isEmpty()) {
			booleanFilter.add(
				accountEntryIdsTermsFilter, BooleanClauseOccur.SHOULD);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountGroupSearchPermissionFilterContributor.class);

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private ModelResourcePermission<AccountRole>
		_accountEntryModelResourcePermission;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

}