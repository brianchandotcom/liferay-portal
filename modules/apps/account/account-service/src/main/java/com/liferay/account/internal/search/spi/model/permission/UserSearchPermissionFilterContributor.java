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
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.permission.OrganizationPermission;
import com.liferay.portal.search.spi.model.permission.SearchPermissionFilterContributor;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.kernel.model.User",
	service = SearchPermissionFilterContributor.class
)
public class UserSearchPermissionFilterContributor
	implements SearchPermissionFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, long companyId, long[] groupIds,
		long userId, PermissionChecker permissionChecker, String className) {

		if (!className.equals(User.class.getName())) {
			return;
		}

		try {
			TermsFilter termsFilter = new TermsFilter("accountEntryIds");

			List<AccountEntryUserRel> accountEntryUserRels =
				_accountEntryUserRelLocalService.
					getAccountEntryUserRelsByAccountUserId(
						permissionChecker.getUserId());

			for (AccountEntryUserRel accountEntryUserRel :
					accountEntryUserRels) {

				if (_accountEntryModelResourcePermission.contains(
						permissionChecker,
						accountEntryUserRel.getAccountEntryId(),
						AccountActionKeys.VIEW_USERS)) {

					termsFilter.addValue(
						String.valueOf(
							accountEntryUserRel.getAccountEntryId()));
				}
			}

			UserBag userBag = permissionChecker.getUserBag();

			long[] userOrgIds = userBag.getUserOrgIds();

			for (long userOrgId : userOrgIds) {
				if (_organizationPermission.contains(
						permissionChecker, userOrgId,
						AccountActionKeys.MANAGE_ACCOUNTS)) {

					List<AccountEntryOrganizationRel>
						accountEntryOrganizationRels =
							_accountEntryOrganizationRelLocalService.
								getAccountEntryOrganizationRelsByOrganizationId(
									userOrgId);

					for (AccountEntryOrganizationRel
							accountEntryOrganizationRel :
								accountEntryOrganizationRels) {

						termsFilter.addValue(
							String.valueOf(
								accountEntryOrganizationRel.
									getAccountEntryId()));
					}
				}
			}

			if (!termsFilter.isEmpty()) {
				booleanFilter.add(termsFilter);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}
	}

	@Reference
	protected RoleLocalService roleLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		UserSearchPermissionFilterContributor.class);

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountEntry)"
	)
	private ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Reference
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private OrganizationPermission _organizationPermission;

}