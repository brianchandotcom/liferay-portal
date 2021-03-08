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

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.spi.model.permission.SearchPermissionFilterContributor;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = SearchPermissionFilterContributor.class)
public class AccountEntrySearchPermissionFilterContributor
	implements SearchPermissionFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, long companyId, long[] groupIds,
		long userId, PermissionChecker permissionChecker, String className) {

		if (!className.equals(AccountEntry.class.getName())) {
			return;
		}

		List<AccountEntryUserRel> accountEntryUserRels =
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountUserId(userId);

		if (ListUtil.isEmpty(accountEntryUserRels)) {
			return;
		}

		TermsFilter primaryKeysTermsFilter = new TermsFilter(
			Field.ENTRY_CLASS_PK);

		List<String> accountEntryIdStrings = ListUtil.toList(
			accountEntryUserRels,
			accountEntryUserRel -> String.valueOf(
				accountEntryUserRel.getAccountEntryId()));

		primaryKeysTermsFilter.addValues(
			accountEntryIdStrings.toArray(
				new String[accountEntryUserRels.size()]));

		booleanFilter.add(primaryKeysTermsFilter, BooleanClauseOccur.MUST);
	}

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

}