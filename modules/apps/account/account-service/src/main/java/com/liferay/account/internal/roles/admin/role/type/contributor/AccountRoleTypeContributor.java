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

package com.liferay.account.internal.roles.admin.role.type.contributor;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributor;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "service.ranking:Integer=500",
	service = RoleTypeContributor.class
)
public class AccountRoleTypeContributor implements RoleTypeContributor {

	@Override
	public String getClassName() {
		return AccountRole.class.getName();
	}

	@Override
	public String getIcon() {
		return "briefcase";
	}

	@Override
	public String getName() {
		return "account";
	}

	@Override
	public String[] getSubtypes() {
		return new String[0];
	}

	@Override
	public String getTabTitle(Locale locale) {
		return _language.get(locale, "account-roles");
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "account-role");
	}

	@Override
	public int getType() {
		return RoleConstants.TYPE_ACCOUNT;
	}

	@Override
	public boolean isAllowAssignMembers(Role role) {
		return false;
	}

	@Override
	public boolean isAllowDefinePermissions(Role role) {
		return true;
	}

	@Override
	public boolean isAllowDelete(Role role) {
		if ((role == null) || AccountRoleConstants.isRequiredRole(role)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isAutomaticallyAssigned(Role role) {
		if (AccountRoleConstants.isImpliedRole(role)) {
			return true;
		}

		return false;
	}

	@Override
	public BaseModelSearchResult<Role> searchRoles(
		long companyId, String keywords, int start, int end,
		OrderByComparator<Role> orderByComparator) {

		BaseModelSearchResult<AccountRole> accountRoleBaseModelSearchResult =
			_accountRoleLocalService.searchAccountRoles(
				companyId,
				new long[] {AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT},
				keywords, null, start, end, orderByComparator);

		return new BaseModelSearchResult<>(
			TransformUtil.transform(
				accountRoleBaseModelSearchResult.getBaseModels(),
				AccountRole::getRole),
			accountRoleBaseModelSearchResult.getLength());
	}

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private Language _language;

}