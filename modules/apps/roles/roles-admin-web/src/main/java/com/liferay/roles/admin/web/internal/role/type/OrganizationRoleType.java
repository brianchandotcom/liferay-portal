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

package com.liferay.roles.admin.web.internal.role.type;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.util.PropsValues;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true, property = "service.ranking:Integer=300",
	service = RoleType.class
)
public class OrganizationRoleType implements RoleType {

	@Override
	public boolean allowAssignMembers(Role role) {
		return false;
	}

	@Override
	public boolean allowDefinePermissions(Role role) {
		String name = role.getName();

		if (name.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
			name.equals(RoleConstants.ORGANIZATION_OWNER)) {

			return false;
		}

		return true;
	}

	@Override
	public boolean allowDelete(Role role) {
		if (role == null) {
			return false;
		}

		return !_portal.isSystemRole(role.getName());
	}

	@Override
	public String getIcon() {
		return "community";
	}

	@Override
	public String getName() {
		return "organization";
	}

	@Override
	public String[] getSubtypes() {
		return PropsValues.ROLES_ORGANIZATION_SUBTYPES;
	}

	@Override
	public String getTabTitle(Locale locale) {
		return "organization-roles";
	}

	@Override
	public String getTitle(Locale locale) {
		return "organization-role";
	}

	@Override
	public int getType() {
		return RoleConstants.TYPE_ORGANIZATION;
	}

	@Reference
	private Portal _portal;

}