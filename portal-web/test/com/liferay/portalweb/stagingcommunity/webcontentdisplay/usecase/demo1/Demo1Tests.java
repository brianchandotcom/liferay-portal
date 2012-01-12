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

package com.liferay.portalweb.stagingcommunity.webcontentdisplay.usecase.demo1;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class Demo1Tests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddCommunitySiteTest.class);
		testSuite.addTestSuite(ActivateStagingSitesTest.class);
		testSuite.addTestSuite(EditSettingsSitePageVersioningEnabledTest.class);
		testSuite.addTestSuite(ConfigurePageRevisionSingleApproverTest.class);
		testSuite.addTestSuite(ConfigureWebContentSingleApproverTest.class);
		testSuite.addTestSuite(AddSARoleTest.class);
		testSuite.addTestSuite(DefineSARoleTest.class);
		testSuite.addTestSuite(AddWCCRoleTest.class);
		testSuite.addTestSuite(DefineWCCRoleTest.class);
		testSuite.addTestSuite(AddWCCUserTest.class);
		testSuite.addTestSuite(AssignWCCUserSitesTest.class);
		testSuite.addTestSuite(AssignWCCUserRolesTest.class);
		testSuite.addTestSuite(AddWCAUserTest.class);
		testSuite.addTestSuite(AssignWCAUserSitesTest.class);
		testSuite.addTestSuite(AssignWCAUserRolesTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(WCC_SignInTest.class);
		testSuite.addTestSuite(WCC_AddPortletWCDSiteStagingTest.class);
		testSuite.addTestSuite(WCC_AddWebContentSiteStagingTest.class);
		testSuite.addTestSuite(WCC_SignOutTest.class);
		testSuite.addTestSuite(WCA_SignInTest.class);
		testSuite.addTestSuite(
			WCA_AssignToMeWebContentActionsSiteStagingTest.class);
		testSuite.addTestSuite(
			WCA_ApproveTaskWebContentDetailsSiteStagingTest.class);
		testSuite.addTestSuite(WCA_SignOutTest.class);
		testSuite.addTestSuite(WCC_SignInTest.class);
		testSuite.addTestSuite(
			WCC_SubmitForPublicationMainVariationHomeSiteStagingTest.class);
		testSuite.addTestSuite(
			WCC_PublishToLiveNowWebContentSiteStagingTest.class);
		testSuite.addTestSuite(
			WCC_ViewPublishToLiveNowWebContentNullSiteStagingTest.class);
		testSuite.addTestSuite(WCC_SignOutTest.class);
		testSuite.addTestSuite(WCA_SignInTest.class);
		testSuite.addTestSuite(
			WCA_AssignToMeMainVariationActionsSiteStagingTest.class);
		testSuite.addTestSuite(
			WCA_ApproveTaskMainVariationActionsSiteStagingTest.class);
		testSuite.addTestSuite(WCA_SignOutTest.class);
		testSuite.addTestSuite(WCC_SignInTest.class);
		testSuite.addTestSuite(
			WCC_PublishToLiveNowWebContentSiteStagingTest.class);
		testSuite.addTestSuite(
			WCC_ViewPublishToLiveNowWebContentSiteStagingTest.class);
		testSuite.addTestSuite(WCC_ViewStagedWebContentSiteStagingTest.class);
		testSuite.addTestSuite(WCC_SignOutTest.class);
		testSuite.addTestSuite(WCA_SignInTest.class);
		testSuite.addTestSuite(
			WCA_ViewPublishToLiveNowWebContentSiteStagingTest.class);
		testSuite.addTestSuite(WCA_SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(
			ViewPublishToLiveNowWebContentSiteStagingTest.class);
		testSuite.addTestSuite(ViewStagedWebContentSiteStagingTest.class);
		testSuite.addTestSuite(DeactivateStagingTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownWebContentTest.class);
		testSuite.addTestSuite(TearDownStagingWorkflowRolesTest.class);
		testSuite.addTestSuite(TearDownWorkflowConfigurationTest.class);
		testSuite.addTestSuite(TearDownSitesTest.class);

		return testSuite;
	}

}