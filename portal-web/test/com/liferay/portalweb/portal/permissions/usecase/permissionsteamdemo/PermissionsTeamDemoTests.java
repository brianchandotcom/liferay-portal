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

package com.liferay.portalweb.portal.permissions.usecase.permissionsteamdemo;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.AddUser1Test;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.AddUser2Test;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.TearDownUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.edituserpassword.EditUser1PasswordTest;
import com.liferay.portalweb.portal.controlpanel.users.user.edituserpassword.EditUser2PasswordTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.User1_SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.User2_SignInTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionsTeamDemoTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSiteTest.class);
		testSuite.addTestSuite(AddPageSiteTest.class);
		testSuite.addTestSuite(AddUser1Test.class);
		testSuite.addTestSuite(EditUser1PasswordTest.class);
		testSuite.addTestSuite(AddUser2Test.class);
		testSuite.addTestSuite(EditUser2PasswordTest.class);
		testSuite.addTestSuite(AssignMembersUser1SiteTest.class);
		testSuite.addTestSuite(AssignMembersUser2SiteTest.class);
		testSuite.addTestSuite(AssignRoleSiteAdministratorUser1Test.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User1_SignInTest.class);
		testSuite.addTestSuite(User1_AddSiteTeamTest.class);
		testSuite.addTestSuite(User1_AssignMembersUser2SiteTeamTest.class);
		testSuite.addTestSuite(User1_AddPortletMBSiteTest.class);
		testSuite.addTestSuite(User1_ConfigureMBPermissionsTeamAddCategoryOnTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User2_SignInTest.class);
		testSuite.addTestSuite(User2_AddMBCategoryTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownSitesTest.class);

		return testSuite;
	}
}