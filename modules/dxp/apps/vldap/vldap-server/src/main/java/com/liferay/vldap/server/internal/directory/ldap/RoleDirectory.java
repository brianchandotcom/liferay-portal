/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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