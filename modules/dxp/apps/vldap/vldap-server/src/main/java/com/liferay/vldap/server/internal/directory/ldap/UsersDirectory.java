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

import java.util.LinkedHashMap;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class UsersDirectory extends Directory {

	public UsersDirectory(String top, Company company) {
		addAttribute("cn", "Users");
		addAttribute("objectclass", "groupOfNames");

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		addMemberAttributes(top, company, params);

		setName(top, company, "Users");
	}

}