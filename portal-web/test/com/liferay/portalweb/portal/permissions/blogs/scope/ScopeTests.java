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

package com.liferay.portalweb.portal.permissions.blogs.scope;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ScopeTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(SA_AddPageTest.class);
		testSuite.addTestSuite(SA_AddPortletTest.class);
		testSuite.addTestSuite(SA_AddUserScopeTest.class);
		testSuite.addTestSuite(SA_AddScopeRoleTest.class);
		testSuite.addTestSuite(SA_DefineScopeRoleTest.class);
		testSuite.addTestSuite(SA_AddScopeSiteTest.class);
		testSuite.addTestSuite(SA_AddScopeSitePageTest.class);
		testSuite.addTestSuite(SA_AddPortletScopeSiteTest.class);
		testSuite.addTestSuite(SA_AssignScopeMemberToScopeSiteTest.class);
		testSuite.addTestSuite(SA_AssignUserRolesTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(LoginUsersTest.class);
		testSuite.addTestSuite(Scope_LoginTest.class);
		testSuite.addTestSuite(Scope_AddGuestSiteScopeEntryTest.class);
		testSuite.addTestSuite(Scope_AddScopeSiteScopeEntryTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(SA_LimitScopePermissionsScopeSiteTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(Scope_LoginTest.class);
		testSuite.addTestSuite(Scope_AssertCannotAddSiteScopeEntryTest.class);
		testSuite.addTestSuite(Scope_AddScopeSiteScopeEntryTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(SA_LoginTest.class);
		testSuite.addTestSuite(TearDownBlogsEntryTest.class);
		testSuite.addTestSuite(TearDownBlogsRolesTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}

}