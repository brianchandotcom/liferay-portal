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

package com.liferay.portalweb.stagingorganization.blogs;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.TearDownSiteTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class BlogsTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(DefinePermissionsBlogsManagePagesOrganizationUserTest.class);
		testSuite.addTestSuite(AddOrganizationTest.class);
		testSuite.addTestSuite(AddOrganizationSiteTest.class);
		testSuite.addTestSuite(AddUserOrganizationUserTest.class);
		testSuite.addTestSuite(AddUserOrganizationContentReviewerTest.class);
		testSuite.addTestSuite(AddUserOrganizationAdministratorTest.class);
		testSuite.addTestSuite(ActivateStagingOrganizationSiteTest.class);
		testSuite.addTestSuite(ConfigureBlogsEntrySingleApproverOrganizationSiteTest.class);
		testSuite.addTestSuite(LogoutTest.class);
		testSuite.addTestSuite(OU_LoginTest.class);
		testSuite.addTestSuite(OU_AddStagedPageOrganizationSiteTest.class);
		testSuite.addTestSuite(OU_AddStagedPortletOrganizationSiteTest.class);
		testSuite.addTestSuite(OU_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertNoPagePrePublishOrganizationSiteTest.class);
		testSuite.addTestSuite(OA_LoginTest.class);
		testSuite.addTestSuite(OA_PublishToLiveStagedPageOrganizationSiteTest.class);
		testSuite.addTestSuite(OA_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertPagePresentOrganizationSiteTest.class);
		testSuite.addTestSuite(OU_LoginTest.class);
		testSuite.addTestSuite(OU_AddStagedBlogsEntryOrganizationSiteTest.class);
		testSuite.addTestSuite(OU_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertNoBlogsEntryPreApprovalOrganizationSiteTest.class);
		testSuite.addTestSuite(OCR_LoginTest.class);
		testSuite.addTestSuite(OCR_AssignToMeStagedBlogsEntryOrganizationSiteActionsTest.class);
		testSuite.addTestSuite(OCR_ApproveStagedBlogsEntryOrganizationSiteActionsTest.class);
		testSuite.addTestSuite(OCR_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertNoBlogsEntryPrePublishOrganizationSiteTest.class);
		testSuite.addTestSuite(OA_LoginTest.class);
		testSuite.addTestSuite(OA_PublishToLiveStagedBlogsEntryOrganizationSiteTest.class);
		testSuite.addTestSuite(OA_LogoutTest.class);
		testSuite.addTestSuite(Guest_AssertBlogsEntryOrganizationSiteTest.class);
		testSuite.addTestSuite(LoginTest.class);
		testSuite.addTestSuite(TearDownWorkflowConfigurationOrganizationSiteTest.class);
		testSuite.addTestSuite(TearDownBlogsEntryOrganizationSiteTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownPermissionsTest.class);
		testSuite.addTestSuite(TearDownSiteTest.class);
		testSuite.addTestSuite(TearDownOrganizationTest.class);

		return testSuite;
	}
}