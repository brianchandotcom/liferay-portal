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
public class SambaMachineDirectory extends Directory {

	public SambaMachineDirectory(
		String top, Company company, Organization organization,
		String sambaDomainName) {

		addAttribute("objectclass", "sambaDomain");
		addAttribute("objectclass", "top");
		addAttribute("sambaAlgorithmicRidBase", "1000");
		addAttribute("sambaDomainName", sambaDomainName);
		addAttribute("sambaLockoutThreshold", "0");
		addAttribute("sambaNextUserRid", "1000");
		addAttribute("sambaPwdHistoryLength", "0");
		addAttribute("sambaSID", "S-1-5-21-" + company.getCompanyId());

		setName(
			top, company, "Organizations", organization.getName(),
			"Samba Machines", "sambaDomainName=" + sambaDomainName);
	}

}