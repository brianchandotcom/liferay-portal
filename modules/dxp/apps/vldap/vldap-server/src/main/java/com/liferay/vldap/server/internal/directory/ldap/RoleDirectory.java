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

package com.liferay.vldap.server.internal.directory.ldap;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;

import java.util.LinkedHashMap;

/**
 * @author Brian Wing Shun Chan
 * @author Jonathan Potter
 */
public class RoleDirectory extends Directory {

	public RoleDirectory(String top, Company company, Role role) {
		this(role.getName());

		addAttribute("description", role.getDescription());
		addRoleMembers(top, company, role.getRoleId());
		setName(top, company, "Roles", role.getName());
	}

	public void addRoleMembers(String top, Company company, long roleId) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("usersRoles", roleId);

		addMemberAttributes(top, company, params);
	}

	protected RoleDirectory(String name) {
		addAttribute("cn", name);
		addAttribute("objectclass", "groupOfNames");
		addAttribute("objectclass", "liferayRole");
		addAttribute("objectclass", "organizationalRole");
		addAttribute("objectclass", "organizationalUnit");
		addAttribute("objectclass", "top");
		addAttribute("ou", name);
	}

}