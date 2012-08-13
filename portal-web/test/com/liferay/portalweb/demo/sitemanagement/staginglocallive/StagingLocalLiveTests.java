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

package com.liferay.portalweb.demo.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class StagingLocalLiveTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddCommunitySiteTest.class);
		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(AssignUserCommunitySiteTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User_SignInTest.class);
		testSuite.addTestSuite(User_AssertCannotAddContentTest.class);
		testSuite.addTestSuite(User_SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(AddRoleStagingAdminTest.class);
		testSuite.addTestSuite(DefinePermissionsSiteAdministrationStagingAdminTest.class);
		testSuite.addTestSuite(AssignRoleStagingAdminUserTest.class);
		testSuite.addTestSuite(ActivateStagingCommunitySiteTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User_SignInTest.class);
		testSuite.addTestSuite(User_DragAndDropPortletMBColumn2SiteStagingTest.class);
		testSuite.addTestSuite(User_DragAndDropPortletUSColumn1SiteStagingTest.class);
		testSuite.addTestSuite(User_PublishToLiveNowSiteStagingTest.class);
		testSuite.addTestSuite(User_ViewPublishToLiveNowSiteStagingTest.class);
		testSuite.addTestSuite(User_SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(AddMBCategorySiteStagingTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User_SignInTest.class);
		testSuite.addTestSuite(User_AddMBCategoryMessage1SiteStagingTest.class);
		testSuite.addTestSuite(User_ViewMBCategoryMessage1LiveTest.class);
		testSuite.addTestSuite(User_AddMBCategoryMessage2LiveTest.class);
		testSuite.addTestSuite(User_ViewMBCategoryMessage2SiteStagingTest.class);
		testSuite.addTestSuite(User_SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(DefinePermissionsDocumentsStagingAdminTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User_SignInTest.class);
		testSuite.addTestSuite(User_AddPortletDLSiteStagingTest.class);
		testSuite.addTestSuite(User_AddDLImageSiteStagingTest.class);
		testSuite.addTestSuite(User_PublishToLiveNowNoDLDocumentsSiteStagingTest.class);
		testSuite.addTestSuite(User_ViewPublishToLiveNowNoDLDocumentsLiveTest.class);
		testSuite.addTestSuite(User_PublishToLiveNowDLDocumentsSiteStagingTest.class);
		testSuite.addTestSuite(User_ViewPublishToLiveNowDLDocumentsLiveTest.class);
		testSuite.addTestSuite(User_EditSiteSettingsPublicPageVersioningEnabledTest.class);
		testSuite.addTestSuite(User_DragAndDropPortletDLColumn2SiteStagingTest.class);
		testSuite.addTestSuite(User_DragAndDropPortletMBColumn1SiteStagingTest.class);
		testSuite.addTestSuite(User_ViewHistoryVersionNumbersTest.class);
		testSuite.addTestSuite(User_SelectPreviousVersionNumberHistoryTest.class);
		testSuite.addTestSuite(User_ViewSelectPreviousVersionNumberHistoryTest.class);
		testSuite.addTestSuite(User_SelectTopVersionNumberHistoryTest.class);
		testSuite.addTestSuite(User_ViewSelectTopVersionNumberHistoryTest.class);
		testSuite.addTestSuite(User_MarkAsReadyForPublicationPageLayoutOneColumnTest.class);
		testSuite.addTestSuite(User_PublishToLiveNowPageLayoutOneColumnTest.class);
		testSuite.addTestSuite(User_ViewPublishToLiveNowPageLayoutOneColumnTest.class);
		testSuite.addTestSuite(User_SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(DefinePermissionsWebContentStagingAdminTest.class);
		testSuite.addTestSuite(DefinePermissionsWCDStagingAdminTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(User_SignInTest.class);
		testSuite.addTestSuite(User_AddPortletWCDSiteStagingTest.class);
		testSuite.addTestSuite(User_AddWCWebContentWCDSiteStagingTest.class);
		testSuite.addTestSuite(User_MarkAsReadyForPublicationWCDSiteStagingTest.class);
		testSuite.addTestSuite(User_PublishToLiveNowWCDSiteStagingTest.class);
		testSuite.addTestSuite(User_ViewPublishToLiveNowWCDSiteStagingTest.class);
		testSuite.addTestSuite(User_RenameMainSPVariationSeasonTest.class);
		testSuite.addTestSuite(User_AddSPVariationChristmasCopyFromSeasonTest.class);
		testSuite.addTestSuite(User_ViewSPVariationChristmasCopyFromSeasonTest.class);
		testSuite.addTestSuite(User_AddPageWhiteElephantSPVariationChristmasTest.class);
		testSuite.addTestSuite(User_AddPortletNavigationPageWhiteElephantTest.class);
		testSuite.addTestSuite(User_MarkAsReadyForPublicationPageWhiteElephantTest.class);
		testSuite.addTestSuite(User_PublishToLiveNowSPVariationChristmasTest.class);
		testSuite.addTestSuite(User_ViewPublishToLiveNowSPVariationChristmasTest.class);
		testSuite.addTestSuite(User_EnableInSeasonPageWhiteElephantSiteStagingTest.class);
		testSuite.addTestSuite(User_ViewEnableInSeasonPageWhiteElephantTest.class);
		testSuite.addTestSuite(User_DeletePageWhiteElephantSPVariationSeasonTest.class);
		testSuite.addTestSuite(User_ViewDeletePageWhiteElephantSPVariationSeasonTest.class);
		testSuite.addTestSuite(User_RenameMainPageVariationRegularSPSeasonTest.class);
		testSuite.addTestSuite(User_AddPageVariationSantaSPVariationSeasonTest.class);
		testSuite.addTestSuite(User_AddPageVariationFrostySPVariationSeasonTest.class);
		testSuite.addTestSuite(User_MarkAsReadyForPublicationPageVariationSantaTest.class);
		testSuite.addTestSuite(User_PublishToLivePageVariationSantaSPSeasonTest.class);
		testSuite.addTestSuite(User_ViewPublishToLivePageVariationSantaSPSeasonTest.class);
		testSuite.addTestSuite(User_DeletePageVariationFrostySPVariationSeasonTest.class);
		testSuite.addTestSuite(User_ViewDeletePageVariationFrostySPSeasonTest.class);
		testSuite.addTestSuite(User_AddSPVariationChristmas2CopyChristmasTest.class);
		testSuite.addTestSuite(User_ViewSPVariationChristmas2CopyChristmasTest.class);
		testSuite.addTestSuite(User_AddSPVariationValentinesCopyFromNoneTest.class);
		testSuite.addTestSuite(User_ViewSPVariationValentinesCopyFromNoneTest.class);
		testSuite.addTestSuite(User_AddPagePricesSPVariationValentinesTest.class);
		testSuite.addTestSuite(User_MarkAsReadyForPublicationSPValentinesTest.class);
		testSuite.addTestSuite(User_PublishToLiveNowSPVariationValentinesTest.class);
		testSuite.addTestSuite(User_ViewPublishToLiveNowSPVariationValentinesTest.class);
		testSuite.addTestSuite(User_MergeSPChristmasSPValentinesTest.class);
		testSuite.addTestSuite(User_ViewMergeSPChristmasSPValentinesTest.class);
		testSuite.addTestSuite(User_MarkAsReadyForPublicationPagePricesSPChristmasTest.class);
		testSuite.addTestSuite(User_PublishToLiveSPChristmasPagePricesTest.class);
		testSuite.addTestSuite(User_MergeSPChristmas2SPChristmasTest.class);
		testSuite.addTestSuite(User_ViewMergeSPChristmas2SPChristmasTest.class);
		testSuite.addTestSuite(User_MarkAsReadyForPublicationPagePricesSPChristmas2Test.class);
		testSuite.addTestSuite(User_PublishToLiveSPChristmas2DeleteWhiteElephantTest.class);
		testSuite.addTestSuite(User_ViewPublishToLiveSPChristmas2NoWhiteElephantTest.class);
		testSuite.addTestSuite(User_SelectTimeZonePacificStandardTimeCPMATest.class);
		testSuite.addTestSuite(User_AddPageBlogsSPVariationChristmasTest.class);
		testSuite.addTestSuite(User_AddPortletBlogsSPVariationChristmasTest.class);
		testSuite.addTestSuite(User_MarkAsReadyForPublicationSPChristmasBlogsTest.class);
		testSuite.addTestSuite(User_AddEventThreeMinutesSPChristmasBlogsSPTLTest.class);
		testSuite.addTestSuite(User_ViewEventThreeMinutesSPChristmasBlogsSPTLTest.class);
		testSuite.addTestSuite(User_SignOutTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownRolesTest.class);
		testSuite.addTestSuite(TearDownSiteTest.class);

		return testSuite;
	}
}