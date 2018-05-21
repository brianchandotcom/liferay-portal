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