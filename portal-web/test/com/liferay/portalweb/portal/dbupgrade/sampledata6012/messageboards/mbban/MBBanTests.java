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

package com.liferay.portalweb.portal.dbupgrade.sampledata6012.messageboards.mbban;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MBBanTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddCommunityMBBanTest.class);
		testSuite.addTestSuite(AddUserMBBanTest.class);
		testSuite.addTestSuite(AddUserMBBanPasswordTest.class);
		testSuite.addTestSuite(AssignMembersCommunityUserActionTest.class);
		testSuite.addTestSuite(AddPageMBTest.class);
		testSuite.addTestSuite(AddPortletMBTest.class);
		testSuite.addTestSuite(AddMBCategoryTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(MB_SignInTest.class);
		testSuite.addTestSuite(MB_AddMBCategoryThreadTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(BanThisUserTest.class);
		testSuite.addTestSuite(ViewBannedUsersUserTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(MB_SignInTest.class);
		testSuite.addTestSuite(MB_ViewMBPortletBanTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);

		return testSuite;
	}
}