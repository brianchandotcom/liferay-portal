/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.permission;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.spi.model.permission.SearchPermissionFilterContributor;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 * @author Gabriel Albuquerque
 */
@Component(service = SearchPermissionFilterContributor.class)
public class ObjectEntrySearchPermissionFilterContributor
	implements SearchPermissionFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, long companyId, long[] groupIds,
		long userId, PermissionChecker permissionChecker, String className) {

		if (!className.startsWith(ObjectDefinition.class.getName())) {
			return;
		}

		List<Long> accountEntryIds = TransformUtil.transform(
			_accountEntryUserRelLocalService.
				getAccountEntryUserRelsByAccountUserId(
					permissionChecker.getUserId()),
			accountEntryUserRel -> {
				AccountEntry accountEntry =
					accountEntryUserRel.getAccountEntry();

				return accountEntry.getAccountEntryId();
			});

		if (ListUtil.isEmpty(accountEntryIds)) {
			return;
		}

		TermsFilter termsFilter = new TermsFilter(
			"accountEntryRestrictedObjectFieldValue");

		for (long accountEntryId : accountEntryIds) {
			termsFilter.addValue(String.valueOf(accountEntryId));
		}

		booleanFilter.add(termsFilter, BooleanClauseOccur.SHOULD);
	}

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

}