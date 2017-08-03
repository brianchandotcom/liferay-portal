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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.LinkedHashMap;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 * @author Jonathan Potter
 */
public class CommunityDirectory extends Directory {

	public CommunityDirectory(String top, Company company, Group community) {
		Locale locale = LocaleUtil.getDefault();

		addAttribute("cn", community.getName(locale));
		addAttribute("description", community.getDescription(locale));

		addAttribute("objectclass", "groupOfNames");
		addAttribute("objectclass", "liferayCommunity");
		addAttribute("objectclass", "organizationalUnit");
		addAttribute("objectclass", "top");
		addAttribute("ou", community.getName(locale));

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("usersGroups", community.getGroupId());

		addMemberAttributes(top, company, params);

		setName(top, company, "Communities", community.getName(locale));
	}

}