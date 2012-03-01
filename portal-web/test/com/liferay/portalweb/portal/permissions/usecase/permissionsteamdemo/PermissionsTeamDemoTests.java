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
		testSuite.addTestSuite(AddUser1PasswordTest.class);
		testSuite.addTestSuite(AddUser2Test.class);
		testSuite.addTestSuite(AddUser2PasswordTest.class);
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