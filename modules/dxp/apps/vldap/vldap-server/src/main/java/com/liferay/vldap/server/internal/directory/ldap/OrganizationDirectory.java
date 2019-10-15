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
import com.liferay.portal.kernel.model.Organization;

import java.util.LinkedHashMap;

/**
 * @author Brian Wing Shun Chan
 * @author Jonathan Potter
 */
public class OrganizationDirectory extends Directory {

	public OrganizationDirectory(
		String top, Company company, Organization organization) {

		addAttribute("cn", organization.getName());
		addAttribute("objectclass", "groupOfNames");
		addAttribute("objectclass", "liferayOrganization");
		addAttribute("objectclass", "organizationalUnit");
		addAttribute("objectclass", "top");
		addAttribute("ou", organization.getName());

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("usersOrgs", organization.getOrganizationId());

		addMemberAttributes(top, company, params);

		setName(top, company, "Organizations", organization.getName());
	}

}