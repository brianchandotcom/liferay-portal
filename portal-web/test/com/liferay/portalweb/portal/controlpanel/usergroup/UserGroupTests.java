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

package com.liferay.portalweb.portal.controlpanel.usergroup;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class UserGroupTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(AddUser2Test.class);
		testSuite.addTestSuite(AddUserGroupTest.class);
		testSuite.addTestSuite(AddAsteriskUserGroupNameTest.class);
		testSuite.addTestSuite(AddCommaUserGroupNameTest.class);
		testSuite.addTestSuite(AddDuplicateUserGroupNameTest.class);
		testSuite.addTestSuite(AddNullUserGroupNameTest.class);
		testSuite.addTestSuite(AddNumberUserGroupNameTest.class);
		testSuite.addTestSuite(ApplyUserGroupTest.class);
		testSuite.addTestSuite(AssertApplyUserGroupTest.class);
		testSuite.addTestSuite(AssertCannotDeleteApplyUserGroupTest.class);
		testSuite.addTestSuite(RemoveApplyUserGroupTest.class);
		testSuite.addTestSuite(AssertRemoveApplyUserGroupTest.class);
		testSuite.addTestSuite(SearchUserGroupTest.class);
		testSuite.addTestSuite(AddTemporaryUserGroupTest.class);
		testSuite.addTestSuite(DeleteTemporaryUserGroupTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownUserGroupTest.class);

		return testSuite;
	}

}