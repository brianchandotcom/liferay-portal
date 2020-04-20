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

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountRole;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributor;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true, property = "service.ranking:Integer=500",
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
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, AccountRoleTypeContributor.class);

		return _language.get(resourceBundle, "account-roles");
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, AccountRoleTypeContributor.class);

		return _language.get(resourceBundle, "account-role");
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
		if (Objects.equals(
				role.getName(),
				AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MEMBER)) {

			return true;
		}

		return false;
	}

	@Reference
	private Language _language;

}