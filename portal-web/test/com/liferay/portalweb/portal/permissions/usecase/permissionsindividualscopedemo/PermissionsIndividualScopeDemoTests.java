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

package com.liferay.portalweb.portal.permissions.usecase.permissionsindividualscopedemo;

import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionsIndividualScopeDemoTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddUser1Test.class);
		testSuite.addTestSuite(AddUser2Test.class);
		testSuite.addTestSuite(EditUser2SitesLiferayTest.class);
		testSuite.addTestSuite(AddPageDMTest.class);
		testSuite.addTestSuite(AddPortletDMTest.class);
		testSuite.addTestSuite(AddFolder1Test.class);
		testSuite.addTestSuite(AddFolder1Image1Test.class);
		testSuite.addTestSuite(AddFolder2Test.class);
		testSuite.addTestSuite(ConfigureViewPermissionFolder2Test.class);
		testSuite.addTestSuite(AddFolder2Image2Test.class);
		testSuite.addTestSuite(AddFolder2Image3ViewableBySiteMemberTest.class);
		testSuite.addTestSuite(AddFolder2SubfolderViewableByOwnerTest.class);
		testSuite.addTestSuite(AddFolder2SubfolderImage4ViewableBySiteMemberTest.class);
		testSuite.addTestSuite(SearchFolderImageTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Guest_AssertNotViewableFolder2Test.class);
		testSuite.addTestSuite(Guest_SearchFolderImageTest.class);
		testSuite.addTestSuite(User1_SignInTest.class);
		testSuite.addTestSuite(User1_AssertNotViewableFolder2Image3Test.class);
		testSuite.addTestSuite(User1_SearchFolderImageTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User2_SignInTest.class);
		testSuite.addTestSuite(User2_AssertViewableFolder2Image3Test.class);
		testSuite.addTestSuite(User2_AssertNotViewableFolder2SubfolderTest.class);
		testSuite.addTestSuite(User2_SearchFolderImageTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(TearDownDLDocumentTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);

		return testSuite;
	}
}