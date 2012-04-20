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

package com.liferay.portalweb.demo.useradmin.usermanagementuserprofile;

import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class UserManagementUserProfileTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(ConfigureServerAdministrationMailTest.class);
		testSuite.addTestSuite(GmailServer_TearDownEmailTest.class);
		testSuite.addTestSuite(AddUser1Test.class);
		testSuite.addTestSuite(Gmail_ViewCPEmailTest.class);
		testSuite.addTestSuite(AssignUser1SitesTest.class);
		testSuite.addTestSuite(AssignUser1RolesTest.class);
		testSuite.addTestSuite(AddUser2Test.class);
		testSuite.addTestSuite(AddUserGroup1Test.class);
		testSuite.addTestSuite(AddUserGroup2Test.class);
		testSuite.addTestSuite(AssignUser1UserGroup1Test.class);
		testSuite.addTestSuite(AssignUser2UserGroup2Test.class);
		testSuite.addTestSuite(AddCustomFieldTest.class);
		testSuite.addTestSuite(EditUser1CustomFieldTest.class);
		testSuite.addTestSuite(DeactivateUser2Test.class);
		testSuite.addTestSuite(ReactivateUser2Test.class);
		testSuite.addTestSuite(ExportUserTest.class);
		testSuite.addTestSuite(GmailServer_TearDownEmailTest.class);
		testSuite.addTestSuite(TearDownServerTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownUserGroupTest.class);
		testSuite.addTestSuite(TearDownCustomFieldTest.class);
		testSuite.addTestSuite(EvaluateUserCSVFileTest.class);

		return testSuite;
	}
}