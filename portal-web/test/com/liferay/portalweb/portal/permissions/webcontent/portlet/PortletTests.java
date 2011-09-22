/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.permissions.webcontent.portlet;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddPageWCDTest.class);
		testSuite.addTestSuite(AddPageWCLTest.class);
		testSuite.addTestSuite(AddPageWCSTest.class);
		testSuite.addTestSuite(AddPortletWCDTest.class);
		testSuite.addTestSuite(AddPortletWCLTest.class);
		testSuite.addTestSuite(AddPortletWCSTest.class);
		testSuite.addTestSuite(AddWCATest.class);
		testSuite.addTestSuite(AddWCARoleTest.class);
		testSuite.addTestSuite(DefineWCARoleTest.class);
		testSuite.addTestSuite(AddMemberTest.class);
		testSuite.addTestSuite(AddMemberRoleTest.class);
		testSuite.addTestSuite(DefineMemberRoleTest.class);
		testSuite.addTestSuite(AssignUserRolesTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(LoginUsersTest.class);
		testSuite.addTestSuite(WCA_LoginTest.class);
		testSuite.addTestSuite(
			WCA_AddMemberWCDConfigurationPermissionsTest.class);
		testSuite.addTestSuite(
			WCA_AddMemberWCLConfigurationPermissionsTest.class);
		testSuite.addTestSuite(
			WCA_AddMemberWCSConfigurationPermissionsTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AssertEditWCDConfigurationTest.class);
		testSuite.addTestSuite(Member_AssertEditWCLConfigurationTest.class);
		testSuite.addTestSuite(Member_AssertEditWCSConfigurationTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(WCA_LoginTest.class);
		testSuite.addTestSuite(
			WCA_RemoveMemberWCDConfigurationPermissionsTest.class);
		testSuite.addTestSuite(
			WCA_RemoveMemberWCLConfigurationPermissionsTest.class);
		testSuite.addTestSuite(
			WCA_RemoveMemberWCSConfigurationPermissionsTest.class);
		testSuite.addTestSuite(WCA_RemoveGuestWCDViewPermissionsTest.class);
		testSuite.addTestSuite(WCA_RemoveGuestWCLViewPermissionsTest.class);
		testSuite.addTestSuite(WCA_RemoveGuestWCSViewPermissionsTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AssertCannotViewWCDTest.class);
		testSuite.addTestSuite(Member_AssertCannotViewWCLTest.class);
		testSuite.addTestSuite(Member_AssertCannotViewWCSTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(WCA_LoginTest.class);
		testSuite.addTestSuite(WCA_AddMemberWCDViewPermissionsTest.class);
		testSuite.addTestSuite(WCA_AddMemberWCLViewPermissionsTest.class);
		testSuite.addTestSuite(WCA_AddMemberWCSViewPermissionsTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AssertViewWCDTest.class);
		testSuite.addTestSuite(Member_AssertViewWCLTest.class);
		testSuite.addTestSuite(Member_AssertViewWCSTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(TearDownWCRolesTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}

}