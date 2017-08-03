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
import com.liferay.portal.kernel.model.Organization;

/**
 * @author Minhchau Dang
 */
public class SambaGroupDirectory extends RoleDirectory {

	public SambaGroupDirectory(
		String top, Company company, Organization organization,
		SambaGroup sambaGroup) {

		super(sambaGroup.getName());

		addAttribute("displayName", sambaGroup.getName());
		addAttribute("objectclass", "sambaGroupMapping");
		addAttribute("sambaGroupType", "4");
		addAttribute("sambaSID", sambaGroup.getSambaSID());

		String gidNumber = sambaGroup.getGIDNumber();

		if (gidNumber != null) {
			addAttribute("objectclass", "posixGroup");
			addAttribute("gidNumber", gidNumber);
		}

		setName(
			top, company, "Organizations", organization.getName(),
			"Samba Groups", "cn=" + sambaGroup.getName());
	}

}