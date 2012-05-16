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

package com.liferay.portalweb.portal.permissions.blogs.portlet;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(SA_AddPortletMemberTest.class);
		testSuite.addTestSuite(SA_AddPortletRoleTest.class);
		testSuite.addTestSuite(SA_AssignUserRolesTest.class);
		testSuite.addTestSuite(SA_AddPageTest.class);
		testSuite.addTestSuite(SA_AddPortletTest.class);
		testSuite.addTestSuite(SA_RemoveViewPortletPermissionsTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(LoginUsersTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotViewPortletTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowViewPortletPermissionsTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertViewPortletTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RegInlineBlogsConfigurationNotCheckedTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotConfigurePortletTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowConfigurePortletPermissionsTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertConfigurePortletTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveConfigurePortletPermissionsTest.class);
		testSuite.addTestSuite(SA_RegInlineBlogsAddEntryNotCheckedTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotAddEntryTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowAddEntryPermissionsTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AddEntryTest.class);
		testSuite.addTestSuite(Portlet_DeleteOwnEntryTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveAddEntryPermissionsTest.class);
		testSuite.addTestSuite(SA_AddTemporaryEntryTest.class);
		testSuite.addTestSuite(SA_RemoveViewEntryPermissionsTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotViewEntryTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowViewEntryPermissionsTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertViewEntryTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveAddCommentPermissionsTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotAddCommentTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowAddCommentPermissionsTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AddCommentTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveAddCommentPermissionsTest.class);
		testSuite.addTestSuite(SA_RegInlineBlogsEditCommentNotCheckedTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotEditCommentTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowEditCommentPermissionsTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_EditCommentTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveEditCommentPermissionsTest.class);
		testSuite.addTestSuite(SA_RegInlineBlogsDeleteCommentNotCheckedTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotDeleteCommentTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowDeleteCommentPermissionsTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_DeleteCommentTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveDeleteCommentPermissionsTest.class);
		testSuite.addTestSuite(SA_RegInlineBlogsEntryPermissionsNotCheckedTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotEditEntryPermissionsTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowPermissionsEntryPermissionsTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertEditEntryPermissionsTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemovePermissionsEntryPermissionsTest.class);
		testSuite.addTestSuite(SA_RegInlineBlogsEditEntryNotCheckedTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotEditEntryTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowEditEntryPermissionsTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_EditEntryTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_RemoveEditEntryPermissionsTest.class);
		testSuite.addTestSuite(SA_RegInlineBlogsDeleteEntryNotCheckedTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotDeleteEntryTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_AllowDeleteEntryPermissionsTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_DeleteEntryTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownBlogsEntryTest.class);
		testSuite.addTestSuite(TearDownBlogsRolesTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}