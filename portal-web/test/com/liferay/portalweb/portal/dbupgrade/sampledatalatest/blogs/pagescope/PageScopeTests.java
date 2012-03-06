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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.blogs.pagescope;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PageScopeTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddCustomSiteBlogsPageScopeTest.class);
		testSuite.addTestSuite(AddPage1BlogsPageScopeTest.class);
		testSuite.addTestSuite(AddPage1PortletBlogsPageScopeTest.class);
		testSuite.addTestSuite(AddPage2BlogsPageScopeTest.class);
		testSuite.addTestSuite(AddPage2PortletBlogsPageScopeTest.class);
		testSuite.addTestSuite(AddPage3BlogsPageScopeTest.class);
		testSuite.addTestSuite(AddPage3PortletBlogsPageScopeTest.class);
		testSuite.addTestSuite(ConfigurePortlet1BlogsScopeDefaultTest.class);
		testSuite.addTestSuite(ConfigurePortlet2BlogsScopeLayoutCurrentPageTest.class);
		testSuite.addTestSuite(ConfigurePortlet3BlogsScopeLayoutPage2Test.class);
		testSuite.addTestSuite(AddPage1BlogsEntry1Test.class);
		testSuite.addTestSuite(AddPage1BlogsEntry1Comment1Test.class);
		testSuite.addTestSuite(AddPage1BlogsEntry1Comment2Test.class);
		testSuite.addTestSuite(RatePage1BlogsEntry1Test.class);
		testSuite.addTestSuite(RatePage1BlogsEntry1Comment1Test.class);
		testSuite.addTestSuite(RatePage1BlogsEntry1Comment2Test.class);
		testSuite.addTestSuite(AddPage2BlogsEntry2Test.class);
		testSuite.addTestSuite(AddPage2BlogsEntry2Comment1Test.class);
		testSuite.addTestSuite(AddPage2BlogsEntry2Comment2Test.class);
		testSuite.addTestSuite(RatePage2BlogsEntry2Test.class);
		testSuite.addTestSuite(RatePage2BlogsEntry2Comment1Test.class);
		testSuite.addTestSuite(RatePage2BlogsEntry2Comment2Test.class);
		testSuite.addTestSuite(AddPage2BlogsEntry3Test.class);
		testSuite.addTestSuite(AddPage2BlogsEntry4Test.class);
		testSuite.addTestSuite(ConfigurePortlet2MaximumItemsToDisplay1Test.class);
		testSuite.addTestSuite(PermissionsPage2BlogsEntry4GuestViewOffTest.class);
		testSuite.addTestSuite(ViewPage1BlogsEntry1Test.class);
		testSuite.addTestSuite(ViewPage1BlogsEntry1Comment1Test.class);
		testSuite.addTestSuite(ViewPage1BlogsEntry1Comment2Test.class);
		testSuite.addTestSuite(ViewRatePage1BlogsEntry1Test.class);
		testSuite.addTestSuite(ViewRatePage1BlogsEntry1Comment1Test.class);
		testSuite.addTestSuite(ViewRatePage1BlogsEntry1Comment2Test.class);
		testSuite.addTestSuite(ViewPage2BlogsEntry2Test.class);
		testSuite.addTestSuite(ViewPage2BlogsEntry2Comment1Test.class);
		testSuite.addTestSuite(ViewPage2BlogsEntry2Comment2Test.class);
		testSuite.addTestSuite(ViewRatePage2BlogsEntry2Test.class);
		testSuite.addTestSuite(ViewRatePage2BlogsEntry2Comment1Test.class);
		testSuite.addTestSuite(ViewRatePage2BlogsEntry2Comment2Test.class);
		testSuite.addTestSuite(ViewPage2BlogsEntry3Test.class);
		testSuite.addTestSuite(ViewPage2BlogsEntry4Test.class);
		testSuite.addTestSuite(ViewConfigurePortlet2MaximumItemsToDisplay1Test.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Guest_ViewPage1BlogsEntry1Test.class);
		testSuite.addTestSuite(Guest_ViewPage2BlogsEntry2Test.class);
		testSuite.addTestSuite(Guest_ViewPage2BlogsEntry3Test.class);
		testSuite.addTestSuite(Guest_ViewPage2BlogsEntry4Test.class);
		testSuite.addTestSuite(Guest_ViewConfigurePortlet2MaximumItemsToDisplay1Test.class);
		testSuite.addTestSuite(SignInTest.class);

		return testSuite;
	}
}