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

package com.liferay.roles.admin.web.internal.role.type.contributor;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.util.PropsValues;
import com.liferay.roles.admin.role.type.contributor.RoleTypeContributor;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true, property = "service.ranking:Integer=100",
	service = RoleTypeContributor.class
)
public class RegularRoleTypeContributor implements RoleTypeContributor {

	@Override
	public String getIcon() {
		return "user";
	}

	@Override
	public String getName() {
		return "regular";
	}

	@Override
	public String[] getSubtypes() {
		return PropsValues.ROLES_REGULAR_SUBTYPES;
	}

	@Override
	public String getTabTitle(Locale locale) {
		return "regular-roles";
	}

	@Override
	public String getTitle(Locale locale) {
		return "regular-role";
	}

	@Override
	public int getType() {
		return RoleConstants.TYPE_REGULAR;
	}

	@Override
	public boolean isAllowAssignMembers(Role role) {
		String name = role.getName();

		if (name.equals(RoleConstants.GUEST) ||
			name.equals(RoleConstants.OWNER) ||
			name.equals(RoleConstants.USER)) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isAllowDefinePermissions(Role role) {
		String name = role.getName();

		if (name.equals(RoleConstants.ADMINISTRATOR) ||
			name.equals(RoleConstants.ANALYTICS_ADMINISTRATOR) ||
			name.equals(RoleConstants.OWNER)) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isAllowDelete(Role role) {
		if (role == null) {
			return false;
		}

		return !_portal.isSystemRole(role.getName());
	}

	@Reference
	private Portal _portal;

}