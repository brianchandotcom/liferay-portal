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