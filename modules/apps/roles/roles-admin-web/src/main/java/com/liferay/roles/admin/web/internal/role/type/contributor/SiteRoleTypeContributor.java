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

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true, property = "service.ranking:Integer=200",
	service = RoleTypeContributor.class
)
public class SiteRoleTypeContributor implements RoleTypeContributor {

	@Override
	public String getIcon() {
		return "globe";
	}

	@Override
	public String getName() {
		return "site";
	}

	@Override
	public String[] getSubtypes() {
		return PropsValues.ROLES_SITE_SUBTYPES;
	}

	@Override
	public String getTabTitle(Locale locale) {
		return "site-roles";
	}

	@Override
	public String getTitle(Locale locale) {
		return "site-role";
	}

	@Override
	public int getType() {
		return RoleConstants.TYPE_SITE;
	}

	@Override
	public boolean isAllowAssignMembers(Role role) {
		return false;
	}

	@Override
	public boolean isAllowDefinePermissions(Role role) {
		String name = role.getName();

		if (name.equals(RoleConstants.SITE_ADMINISTRATOR) ||
			name.equals(RoleConstants.SITE_OWNER)) {

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