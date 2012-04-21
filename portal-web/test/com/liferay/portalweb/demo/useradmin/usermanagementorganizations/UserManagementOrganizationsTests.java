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

package com.liferay.portalweb.demo.useradmin.usermanagementorganizations;

import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class UserManagementOrganizationsTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(AssignUserOrganizationLiferayTest.class);
		testSuite.addTestSuite(ViewOrganizationLiferayTest.class);
		testSuite.addTestSuite(AddOrganizationTest.class);
		testSuite.addTestSuite(EditOrganizationNameTest.class);
		testSuite.addTestSuite(AddOrganizationSiteTest.class);
		testSuite.addTestSuite(AddOrganizationCategorizationTest.class);
		testSuite.addTestSuite(AddOrganizationAddressTest.class);
		testSuite.addTestSuite(AddOrganizationPhoneNumberTest.class);
		testSuite.addTestSuite(AddOrganizationInvalidEmailTest.class);
		testSuite.addTestSuite(AddOrganizationInvalidURLTest.class);
		testSuite.addTestSuite(AddOrganizationServicesTest.class);
		testSuite.addTestSuite(AddOrganizationCommentsTest.class);
		testSuite.addTestSuite(AddOrganizationReminderTest.class);
		testSuite.addTestSuite(ViewOrganizationTest.class);
		testSuite.addTestSuite(SearchOrganizationCategorizationTest.class);
		testSuite.addTestSuite(AddSubOrganization1Test.class);
		testSuite.addTestSuite(DeleteSubOrganization1Test.class);
		testSuite.addTestSuite(AddSubOrganization2Test.class);
		testSuite.addTestSuite(AssignUserSubOrganization2Test.class);
		testSuite.addTestSuite(DeleteSubOrganization2Test.class);
		testSuite.addTestSuite(AssignUserOrganizationTest.class);
		testSuite.addTestSuite(ViewSiteOrganizationTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownSiteTest.class);
		testSuite.addTestSuite(TearDownOrganizationTest.class);

		return testSuite;
	}
}