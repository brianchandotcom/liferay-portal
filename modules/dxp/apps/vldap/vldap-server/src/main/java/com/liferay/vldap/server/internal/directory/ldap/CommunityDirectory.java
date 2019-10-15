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