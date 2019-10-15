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