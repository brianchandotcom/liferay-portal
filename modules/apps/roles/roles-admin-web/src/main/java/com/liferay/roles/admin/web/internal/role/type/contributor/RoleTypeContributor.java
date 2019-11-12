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

import java.util.Locale;

/**
 * Used to contribute a role type entry in the Roles Admin portlet. Each role
 * type will create a tab in the user interface.
 *
 * @author Drew Brokke
 * @review
 */
public interface RoleTypeContributor {

	/**
	 * Optionally returns a className to be used when creating a new role of
	 * this type. If none is given, the class name of Role will be used.
	 *
	 * @return
	 */
	public default String getClassName() {
		return null;
	}

	/**
	 * Returns the css class of the icon that represents the role type.
	 *
	 * @return
	 */
	public String getIcon();

	/**
	 * Returns the role type name.
	 *
	 * Example: "regular"
	 *
	 * @return
	 */
	public String getName();

	/**
	 * Optionally returns an array of subtypes for the given role type. If
	 * given, a subtype may be chosen for a role of this type when adding or
	 * updating the role.
	 *
	 * @return
	 */
	public default String[] getSubtypes() {
		return new String[0];
	}

	/**
	 * Returns the display title for this role type's section.
	 *
	 * Example: "Regular Roles"
	 *
	 * @param locale
	 * @return
	 */
	public String getTabTitle(Locale locale);

	/**
	 * Returns the display title for this role in the creation menu.
	 *
	 * Example: "Regular Role"
	 *
	 * @param locale
	 * @return
	 */
	public String getTitle(Locale locale);

	/**
	 * Returns an integer that corresponds to this role's type. This is used as
	 * a key to retrieve the RoleTypeContributor.
	 *
	 * @return
	 */
	public int getType();

	/**
	 * Returns whether or not to allow a user to manually assign members to the
	 * given role.
	 *
	 * @param role
	 * @return
	 */
	public boolean isAllowAssignMembers(Role role);

	/**
	 * Returns whether or not to allow a user to manually define the permissions
	 * granted by the given role.
	 *
	 * @param role
	 * @return
	 */
	public boolean isAllowDefinePermissions(Role role);

	/**
	 * Returns whether or not the given role can be deleted.
	 *
	 * @param role
	 * @return
	 */
	public boolean isAllowDelete(Role role);

}