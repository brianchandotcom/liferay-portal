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

package com.liferay.portalweb.portal.permissions.imagegallery.portlet;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageTest.class);
		testSuite.addTestSuite(AddPortletTest.class);
		testSuite.addTestSuite(ConfigureMediaGalleryTest.class);
		testSuite.addTestSuite(AddUserPortletTest.class);
		testSuite.addTestSuite(AddPortletRoleTest.class);
		testSuite.addTestSuite(RemovePortletRolePowerUserTest.class);
		testSuite.addTestSuite(RemoveGuestViewTest.class);
		testSuite.addTestSuite(DefinePortletRoleTest.class);
		testSuite.addTestSuite(AssignUserPortletRolesTest.class);
		testSuite.addTestSuite(SA_RemoveViewPortletPermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotViewPortletTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_AllowViewPortletPermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertViewPortletTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_RemoveConfigurePortletPermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotConfigurePortletTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_AllowConfigurePortletPermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertConfigurePortletTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_RemoveConfigurePortletPermissionsTest.class);
		testSuite.addTestSuite(SA_AddTemporaryFolderTest.class);
		testSuite.addTestSuite(SA_RemoveViewFolderPermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotViewFolderTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_AllowViewFolderPermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertViewFolderTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotAddImageTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_AllowAddImagePermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AddImageTest.class);
		testSuite.addTestSuite(Portlet_DeleteOwnImageTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_RemoveAddImagePermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotAddSubfolderTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_AllowAddSubfolderPermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AddSubfolderTest.class);
		testSuite.addTestSuite(Portlet_DeleteOwnSubfolderTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_RemoveAddSubfolderPermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotEditFolderPermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_AllowPermissionsFolderPermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertEditFolderPermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_RemovePermissionsFolderPermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotEditFolderTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_AllowEditFolderPermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_EditFolderTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_RemoveEditFolderPermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotDeleteFolderTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_AllowDeleteFolderPermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_DeleteFolderTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_AddTemporaryFolderTest.class);
		testSuite.addTestSuite(SA_AddTemporaryImageTest.class);
		testSuite.addTestSuite(SA_RemoveViewImagePermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotViewImageTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_AllowViewImagePermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertViewImageTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotEditImagePermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_AllowPermissionsImagePermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertEditImagePermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_RemovePermissionsImagePermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotEditImageTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_AllowEditImagePermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_EditImageTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_RemoveEditImagePermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_AssertCannotDeleteImageTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_AllowDeleteImagePermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Portlet_LoginTest.class);
		testSuite.addTestSuite(Portlet_DeleteImageTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(TearDownMediaGalleryTest.class);
		testSuite.addTestSuite(TearDownRolesTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}