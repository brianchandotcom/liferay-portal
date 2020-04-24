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

package com.liferay.depot.internal.roles.admin.role.type.contributor;

import com.liferay.depot.constants.DepotRolesConstants;
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
 * @author Drew Brokke
 */
@Component(
	immediate = true, property = "service.ranking:Integer=400",
	service = RoleTypeContributor.class
)
public class DepotRoleTypeContributor implements RoleTypeContributor {

	@Override
	public String[] getExcludedRoleNames() {
		return new String[] {
			DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR,
			DepotRolesConstants.ASSET_LIBRARY_OWNER
		};
	}

	@Override
	public String getIcon() {
		return "globe";
	}

	@Override
	public String getName() {
		return "asset-library";
	}

	@Override
	public String[] getSubtypes() {
		return new String[0];
	}

	@Override
	public String getTabTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, DepotRoleTypeContributor.class);

		return _language.get(resourceBundle, "asset-library-roles");
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, DepotRoleTypeContributor.class);

		return _language.get(resourceBundle, "asset-library-role");
	}

	@Override
	public int getType() {
		return RoleConstants.TYPE_DEPOT;
	}

	@Override
	public boolean isAllowAssignMembers(Role role) {
		return false;
	}

	@Override
	public boolean isAllowDelete(Role role) {
		if (role == null) {
			return false;
		}

		if (Objects.equals(
				role.getName(),
				DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR) ||
			Objects.equals(
				role.getName(), DepotRolesConstants.ASSET_LIBRARY_MEMBER) ||
			Objects.equals(
				role.getName(),
				DepotRolesConstants.ASSET_LIBRARY_CONNECTED_SITE_MEMBER) ||
			Objects.equals(
				role.getName(), DepotRolesConstants.ASSET_LIBRARY_OWNER)) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isAutomaticallyAssigned(Role role) {
		if (Objects.equals(
				role.getName(), DepotRolesConstants.ASSET_LIBRARY_MEMBER)) {

			return true;
		}

		return false;
	}

	@Reference
	private Language _language;

}