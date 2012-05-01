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

package com.liferay.portalweb.demo.useradmin.permissionsuserpersonalsite;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.AddUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.TearDownUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.edituserpassword.EditUserPasswordTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.User_SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.User_SignOutTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.blogs.portlet.addportletblogs.AddPageBlogsTest;
import com.liferay.portalweb.portlet.blogs.portlet.addportletblogs.AddPortletBlogsTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionsUserPersonalSiteTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(ConfigureServerAdminCleanPermissionsTest.class);
		testSuite.addTestSuite(DeletePermissionsPUBlogsAddEntryUPSTest.class);
		testSuite.addTestSuite(DeletePermissionsPUBlogsPermissionsUPSTest.class);
		testSuite.addTestSuite(DeletePermissionsPUBlogsSubscribeUPSTest.class);
		testSuite.addTestSuite(DeletePermissionsPUBlogsAccessInCPUPSTest.class);
		testSuite.addTestSuite(DeletePermissionsUserBlogsAddToPageUPSTest.class);
		testSuite.addTestSuite(DeletePermissionsUserBlogsViewUPSTest.class);
		testSuite.addTestSuite(DeletePermissionsUserBlogsAddEntryUPSTest.class);
		testSuite.addTestSuite(DeletePermissionsUserBlogsPermissionsUPSTest.class);
		testSuite.addTestSuite(DeletePermissionsUserBlogsSubscribeUPSTest.class);
		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(EditUserPasswordTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User_SignInTest.class);
		testSuite.addTestSuite(User_ViewOffPermissionsBlogsUSPTest.class);
		testSuite.addTestSuite(User_ViewOffPermissionsBlogsCPTest.class);
		testSuite.addTestSuite(User_SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(AddPermissionsPUBlogsAddToPageUPSTest.class);
		testSuite.addTestSuite(AddPermissionsPUBlogsViewUPSTest.class);
		testSuite.addTestSuite(AddPermissionsPUBlogsAddEntryUPSTest.class);
		testSuite.addTestSuite(AddPermissionsPUBlogsPermissionsUPSTest.class);
		testSuite.addTestSuite(AddPermissionsPUBlogsSubscribeUPSTest.class);
		testSuite.addTestSuite(AddPermissionsPUBlogsAcessInCPUPSTest.class);
		testSuite.addTestSuite(AddPageBlogsTest.class);
		testSuite.addTestSuite(AddPortletBlogsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User_SignInTest.class);
		testSuite.addTestSuite(User_ViewOffPermissionsBlogsGuestCommunityTest.class);
		testSuite.addTestSuite(User_ViewOnPermissionsBlogsUPSTest.class);
		testSuite.addTestSuite(User_SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownPermissionsUserTest.class);
		testSuite.addTestSuite(TearDownPermissionsPUTest.class);

		return testSuite;
	}
}