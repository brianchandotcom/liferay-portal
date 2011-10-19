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

package com.liferay.portalweb.plugins.droolsweb;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DroolsWebTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddPageTest.class);
		testSuite.addTestSuite(AddPortletTest.class);

		testSuite.addTestSuite(AddUser1Test.class);
		testSuite.addTestSuite(AddUser1AddressTest.class);
		testSuite.addTestSuite(AssignUser1MembersSitesTest.class);
		testSuite.addTestSuite(AssertUser1AssignMembersSitesTest.class);

		testSuite.addTestSuite(AddUser2Test.class);
		testSuite.addTestSuite(AddUser2AddressTest.class);
		testSuite.addTestSuite(AssignUser2MembersSitesTest.class);
		testSuite.addTestSuite(AssertUser2AssignMembersSitesTest.class);

		testSuite.addTestSuite(AddUser3Test.class);
		testSuite.addTestSuite(AddUser3AddressTest.class);
		testSuite.addTestSuite(AssignUser3MembersSitesTest.class);
		testSuite.addTestSuite(AssertUser3AssignMembersSitesTest.class);

		testSuite.addTestSuite(AddPageBlogsTest.class);
		testSuite.addTestSuite(AddPortletBlogsTest.class);
		testSuite.addTestSuite(AddBlogsEntry1Test.class);
		testSuite.addTestSuite(AddBlogsEntry2Test.class);
		testSuite.addTestSuite(AddBlogsEntry3Test.class);

		testSuite.addTestSuite(AssertDroolsUser1Test.class);
		testSuite.addTestSuite(AssertDroolsUser2Test.class);
		testSuite.addTestSuite(AssertDroolsUser3Test.class);

		testSuite.addTestSuite(SignInTest.class);

		testSuite.addTestSuite(TearDownBlogsEntryTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);
		testSuite.addTestSuite(DeletePageTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);

		return testSuite;
	}

}