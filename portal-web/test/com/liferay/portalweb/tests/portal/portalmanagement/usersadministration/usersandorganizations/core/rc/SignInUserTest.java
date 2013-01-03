/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.tests.portal.portalmanagement.usersadministration.usersandorganizations.core.rc;

import com.liferay.portalweb.blocks.portal.signin.page.macros.rc.SignInUserMacros;
import com.liferay.portalweb.blocks.portal.usersandorganizations.controlpanel.macros.rc.CPUsersAndOrganizationsUsersMacros;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class SignInUserTest extends BaseTestCase {
	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		CPUsersAndOrganizationsUsersMacros cPUsersAndOrganizationsUsersMacros = new CPUsersAndOrganizationsUsersMacros(selenium);
		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		signInUserMacros.signIn("test@liferay.com", "test");
		cPUsersAndOrganizationsUsersMacros.add("usersn", "userea@liferay.com",
			"userfn", "userln");
		cPUsersAndOrganizationsUsersMacros.changePassword("userfn", "password");
	}

	public void test() throws Exception {
		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		signInUserMacros.signOut();
		signInUserMacros.signInNewPassword("userea@liferay.com", "password",
			"test");
		signInUserMacros.signOut();
		signInUserMacros.signIn("test@liferay.com", "test");
	}

	@Override
	public void tearDown() throws Exception {
		CPUsersAndOrganizationsUsersMacros cPUsersAndOrganizationsUsersMacros = new CPUsersAndOrganizationsUsersMacros(selenium);
		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		cPUsersAndOrganizationsUsersMacros.tearDown();
		signInUserMacros.signOut();
	}
}