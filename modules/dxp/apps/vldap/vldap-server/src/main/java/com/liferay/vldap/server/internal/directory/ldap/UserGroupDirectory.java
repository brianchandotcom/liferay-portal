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
import com.liferay.portal.kernel.model.UserGroup;

import java.util.LinkedHashMap;

/**
 * @author Raymond Augé
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class UserGroupDirectory extends Directory {

	public UserGroupDirectory(
		String top, Company company, UserGroup userGroup) {

		addAttribute("cn", userGroup.getName());
		addAttribute("description", userGroup.getDescription());
		addAttribute("objectclass", "groupOfNames");
		addAttribute("objectclass", "liferayUserGroup");
		addAttribute("objectclass", "organizationalUnit");
		addAttribute("objectclass", "top");
		addAttribute("ou", userGroup.getName());

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("usersUserGroups", userGroup.getUserGroupId());

		addMemberAttributes(top, company, params);

		setName(top, company, "User Groups", userGroup.getName());
	}

}