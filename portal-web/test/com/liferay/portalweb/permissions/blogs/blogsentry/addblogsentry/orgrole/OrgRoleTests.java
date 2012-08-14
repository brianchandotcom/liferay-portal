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

package com.liferay.portalweb.permissions.blogs.blogsentry.addblogsentry.orgrole;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization.AddOrganizationTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization.TearDownOrganizationTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.assignmembersorganizationuser.AssignMembersOrganizationUserTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.editorganizationsite.EditOrganizationSiteTest;
import com.liferay.portalweb.portal.controlpanel.roles.role.addorgrole.AddOrgRoleTest;
import com.liferay.portalweb.portal.controlpanel.roles.role.addregrole.TearDownRoleTest;
import com.liferay.portalweb.portal.controlpanel.roles.role.assignmembersorgroleuser.AssignMembersOrgRoleUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.AddUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.TearDownUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.edituserpassword.EditUserPasswordTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.User_SignInTest;
import com.liferay.portalweb.portlet.blogs.portlet.addportletblogsorganization.AddPageBlogsOrganizationTest;
import com.liferay.portalweb.portlet.blogs.portlet.addportletblogsorganization.AddPortletBlogsOrganizationTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class OrgRoleTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(EditUserPasswordTest.class);
		testSuite.addTestSuite(AddOrganizationTest.class);
		testSuite.addTestSuite(EditOrganizationSiteTest.class);
		testSuite.addTestSuite(AddPageBlogsOrganizationTest.class);
		testSuite.addTestSuite(AddPortletBlogsOrganizationTest.class);
		testSuite.addTestSuite(AssignMembersOrganizationUserTest.class);
		testSuite.addTestSuite(AddOrgRoleTest.class);
		testSuite.addTestSuite(AssignMembersOrgRoleUserTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User_SignInTest.class);
		testSuite.addTestSuite(User_AddBlogsEntryNoOrganizationTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(DefineOrgRoleContentBlogsAddEntryTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User_SignInTest.class);
		testSuite.addTestSuite(User_AddBlogsEntryOrganizationTest.class);
		testSuite.addTestSuite(User_ViewBlogsEntryOrganizationTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(ViewBlogsEntryOrganizationTest.class);
		testSuite.addTestSuite(RemoveOrgRoleContentBlogsAddEntryTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User_SignInTest.class);
		testSuite.addTestSuite(User_AddBlogsEntryNoOrganizationTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownRoleTest.class);
		testSuite.addTestSuite(TearDownOrganizationTest.class);

		return testSuite;
	}
}