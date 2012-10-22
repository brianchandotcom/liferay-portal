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

package com.liferay.portalweb.stagingsite.blogs;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.blogs.blogsentry.addblogsentry.TearDownBlogsEntryTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(DefinePermissionsBlogsManagePagesPowerUserTest.class);
		testSuite.addTestSuite(AddUserPowerUserTest.class);
		testSuite.addTestSuite(AddUserPortalContentReviewerTest.class);
		testSuite.addTestSuite(AddUserAdministratorTest.class);
		testSuite.addTestSuite(ActivateStagingTest.class);
		testSuite.addTestSuite(ConfigureBlogsEntrySingleApproverTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(PU_LoginTest.class);
		testSuite.addTestSuite(PU_AddStagedPageTest.class);
		testSuite.addTestSuite(PU_AddStagedPortletTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Guest_AssertNoPagePrePublishTest.class);
		testSuite.addTestSuite(Administrator_LoginTest.class);
		testSuite.addTestSuite(Administrator_PublishToLiveStagedPageTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Guest_AssertPagePresentTest.class);
		testSuite.addTestSuite(PU_LoginTest.class);
		testSuite.addTestSuite(PU_AddStagedBlogsEntryTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Guest_AssertNoBlogsEntryPreApprovalTest.class);
		testSuite.addTestSuite(PCR_LoginTest.class);
		testSuite.addTestSuite(PCR_AssignToMeStagedBlogsEntryActionsTest.class);
		testSuite.addTestSuite(PCR_ApproveStagedBlogsEntryActionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Guest_AssertNoBlogsEntryPrePublishTest.class);
		testSuite.addTestSuite(Administrator_LoginTest.class);
		testSuite.addTestSuite(Administrator_PublishToLiveStagedBlogsEntryTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Guest_AssertBlogsEntryTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(DeactivateStagingTest.class);
		testSuite.addTestSuite(TearDownWorkflowConfigurationTest.class);
		testSuite.addTestSuite(TearDownBlogsEntryTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownPermissionsTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}