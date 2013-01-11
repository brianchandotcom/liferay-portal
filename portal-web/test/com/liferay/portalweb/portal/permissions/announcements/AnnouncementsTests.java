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

package com.liferay.portalweb.portal.permissions.announcements;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.TearDownSiteTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnouncementsTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(SA_AddSiteTest.class);
		testSuite.addTestSuite(SA_AddAATest.class);
		testSuite.addTestSuite(SA_AddMemberTest.class);
		testSuite.addTestSuite(SA_AddAARoleTest.class);
		testSuite.addTestSuite(SA_DefineAARoleTest.class);
		testSuite.addTestSuite(SA_AddMemberRoleTest.class);
		testSuite.addTestSuite(SA_DefineMemberRoleTest.class);
		testSuite.addTestSuite(SA_AssignUserRolesTest.class);
		testSuite.addTestSuite(SA_AddSiteMembersTest.class);
		testSuite.addTestSuite(SA_AddPageTest.class);
		testSuite.addTestSuite(SA_AddPortletTest.class);
		testSuite.addTestSuite(SA_AddGeneralAnnouncementTest.class);
		testSuite.addTestSuite(SA_AddAAAnnouncementTest.class);
		testSuite.addTestSuite(SA_AddMemberAnnouncementTest.class);
		testSuite.addTestSuite(SA_AddGuestAnnouncementTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(LoginUsersTest.class);
		testSuite.addTestSuite(AA_LoginTest.class);
		testSuite.addTestSuite(AA_AssertViewTest.class);
		testSuite.addTestSuite(AA_AssertActionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AssertViewTest.class);
		testSuite.addTestSuite(Member_DismissAnnouncementTest.class);
		testSuite.addTestSuite(Member_AssertActionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Guest_AssertViewTest.class);
		testSuite.addTestSuite(Guest_AssertActionsTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(TearDownSiteTest.class);
		testSuite.addTestSuite(TearDownRolesTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);

		return testSuite;
	}
}