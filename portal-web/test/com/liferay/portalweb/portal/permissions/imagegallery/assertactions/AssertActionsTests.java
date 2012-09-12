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

package com.liferay.portalweb.portal.permissions.imagegallery.assertactions;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertActionsTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageTest.class);
		testSuite.addTestSuite(AddPortletTest.class);
		testSuite.addTestSuite(ConfigureMediaGalleryTest.class);
		testSuite.addTestSuite(AddSiteAdminTest.class);
		testSuite.addTestSuite(AddSiteAdminRoleTest.class);
		testSuite.addTestSuite(DefineSiteAdminRoleTest.class);
		testSuite.addTestSuite(RemoveSiteAdminRolePowerUserTest.class);
		testSuite.addTestSuite(AssignUserSiteAdminRolesTest.class);
		testSuite.addTestSuite(AddMemberTest.class);
		testSuite.addTestSuite(AddMemberRoleTest.class);
		testSuite.addTestSuite(DefineMemberRoleTest.class);
		testSuite.addTestSuite(RemoveMemberRolePowerUserTest.class);
		testSuite.addTestSuite(AssignUserMemberRolesTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SiteAdmin_LoginTest.class);
		testSuite.addTestSuite(SiteAdmin_AddFolderTest.class);
		testSuite.addTestSuite(SiteAdmin_AddSubfolderTest.class);
		testSuite.addTestSuite(SiteAdmin_AddImageTest.class);
		testSuite.addTestSuite(SiteAdmin_AddMoveFoldersTest.class);
		testSuite.addTestSuite(SiteAdmin_MoveImageTest.class);
		testSuite.addTestSuite(SiteAdmin_EditFolderTest.class);
		testSuite.addTestSuite(SiteAdmin_EditImageTest.class);
		testSuite.addTestSuite(SiteAdmin_RemoveGuestViewFolderPermissionsTest.class);
		testSuite.addTestSuite(SiteAdmin_RemoveGuestViewImagePermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Guest_AssertCannotViewFolderTest.class);
		testSuite.addTestSuite(Guest_AssertCannotViewImageTest.class);
		testSuite.addTestSuite(SiteAdmin_LoginTest.class);
		testSuite.addTestSuite(SiteAdmin_RemoveGuestViewPortletPermissionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Guest_AssertCannotViewPortletTest.class);
		testSuite.addTestSuite(SiteAdmin_LoginTest.class);
		testSuite.addTestSuite(SiteAdmin_RestoreGuestViewFolderTest.class);
		testSuite.addTestSuite(SiteAdmin_RestoreGuestViewImageTest.class);
		testSuite.addTestSuite(SiteAdmin_RestoreGuestViewPortletTest.class);
		testSuite.addTestSuite(SiteAdmin_AssertActionTest.class);
		testSuite.addTestSuite(SiteAdmin_DeleteImageTest.class);
		testSuite.addTestSuite(SiteAdmin_DeleteFolderTest.class);
		testSuite.addTestSuite(SiteAdmin_AddSecondImageTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(SA_GrantMemberAddImageTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Member_LoginTest.class);
		testSuite.addTestSuite(Member_AddImageTest.class);
		testSuite.addTestSuite(Member_MoveImageTest.class);
		testSuite.addTestSuite(Member_EditImageTest.class);
		testSuite.addTestSuite(Member_AssertCannotAddFolderTest.class);
		testSuite.addTestSuite(Member_AssertCannotAddSubfolderTest.class);
		testSuite.addTestSuite(Member_AssertCannotEditFolderTest.class);
		testSuite.addTestSuite(Member_AssertCannotEditImageTest.class);
		testSuite.addTestSuite(Member_AssertCannotEditPermissionsTest.class);
		testSuite.addTestSuite(Member_AssertActionTest.class);
		testSuite.addTestSuite(Member_DeleteImageTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Guest_AssertNotSignedInTest.class);
		testSuite.addTestSuite(Guest_AssertCannotAddFolderTest.class);
		testSuite.addTestSuite(Guest_AssertCannotAddSubfolderTest.class);
		testSuite.addTestSuite(Guest_AssertCannotAddImageTest.class);
		testSuite.addTestSuite(Guest_AssertCannotEditFolderTest.class);
		testSuite.addTestSuite(Guest_AssertCannotEditImageTest.class);
		testSuite.addTestSuite(Guest_AssertActionTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(TearDownMediaGalleryTest.class);
		testSuite.addTestSuite(TearDownRolesTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}