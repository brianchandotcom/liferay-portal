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

package com.liferay.portalweb.plugins.drools.user.usecase;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class UseCaseTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddPageSDTest.class);
		testSuite.addTestSuite(AddPortletSDTest.class);
		testSuite.addTestSuite(AddUser1Test.class);
		testSuite.addTestSuite(AddUser1AddressTest.class);
		testSuite.addTestSuite(AssignMembersUser1SiteTest.class);
		testSuite.addTestSuite(ViewAssignMembersUser1SiteTest.class);
		testSuite.addTestSuite(AddUser2Test.class);
		testSuite.addTestSuite(AddUser2AddressTest.class);
		testSuite.addTestSuite(AssignMembersUser2SiteTest.class);
		testSuite.addTestSuite(ViewAssignMembersUser2SiteTest.class);
		testSuite.addTestSuite(AddUser3Test.class);
		testSuite.addTestSuite(AddUser3AddressTest.class);
		testSuite.addTestSuite(AssignMembersUser3SiteTest.class);
		testSuite.addTestSuite(ViewAssignMembersUser3SiteTest.class);
		testSuite.addTestSuite(AddPageBlogsTest.class);
		testSuite.addTestSuite(AddPortletBlogsTest.class);
		testSuite.addTestSuite(AddBlogsEntry1TagESTest.class);
		testSuite.addTestSuite(AddBlogsEntry2TagISTest.class);
		testSuite.addTestSuite(AddBlogsEntry3TagWCSTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User1_SignInTest.class);
		testSuite.addTestSuite(User1_ViewBlogsEntry1TagESSDTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User2_SignInTest.class);
		testSuite.addTestSuite(User2_ViewBlogsEntry2TagISSDTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User3_SignInTest.class);
		testSuite.addTestSuite(User3_ViewBlogsEntry3TagWCSSDTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(TearDownBlogsEntryTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);

		return testSuite;
	}

}