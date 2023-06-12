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

package com.liferay.commerce.price.list.internal.search.spi.model.permission;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.search.spi.model.permission.SearchPermissionFilterContributor;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(service = SearchPermissionFilterContributor.class)
public class CommercePriceListSearchPermissionFilterContributor
	implements SearchPermissionFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, long companyId, long[] groupIds,
		long userId, PermissionChecker permissionChecker, String className) {

		if (!className.equals(CommercePriceList.class.getName())) {
			return;
		}

		try {
			if (!_roleLocalService.hasUserRole(
					permissionChecker.getUserId(),
					permissionChecker.getCompanyId(),
					AccountRoleConstants.ROLE_NAME_SUPPLIER, true)) {

				return;
			}

			List<AccountEntry> accountEntries =
				_accountEntryLocalService.getUserAccountEntries(
					permissionChecker.getUserId(), 0L, StringPool.BLANK,
					new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_SUPPLIER},
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			TermsFilter termsFilter = new TermsFilter("accountEntryId");

			for (AccountEntry accountEntry : accountEntries) {
				termsFilter.addValue(
					String.valueOf(accountEntry.getAccountEntryId()));
			}

			if (!termsFilter.isEmpty()) {
				booleanFilter.add(termsFilter, BooleanClauseOccur.SHOULD);
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceListSearchPermissionFilterContributor.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}