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

package com.liferay.portalweb.demo.sitemanagement.brazilianworldcup;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignInTest;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class BrazilianWorldCupTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSiteBWCTest.class);
		testSuite.addTestSuite(AddFriendlyURLVirtualHostsSiteBWCTest.class);
		testSuite.addTestSuite(AddPublicPageHomeSiteBWCTest.class);
		testSuite.addTestSuite(AddPublicPageArenasSiteBWCTest.class);
		testSuite.addTestSuite(AddPrivatePageAccommodationsSiteBWCTest.class);
		testSuite.addTestSuite(AddPrivatePageMapsSiteBWCTest.class);
		testSuite.addTestSuite(AddPublicPageArenasChildPagePernambucoSiteBWCTest.class);
		testSuite.addTestSuite(AddPublicPageArenasChildPageBaixadaSiteBWCTest.class);
		testSuite.addTestSuite(AddPublicPageArenasChildPageMaracanaSiteBWCTest.class);
		testSuite.addTestSuite(EditColorSchemeGreenPublicPagesSiteBWCTest.class);
		testSuite.addTestSuite(EditLogoPublicPagesSiteBWCTest.class);
		testSuite.addTestSuite(AddJavaScriptCodePublicPagesSiteBWCTest.class);
		testSuite.addTestSuite(Guest_ViewPublicPagesSiteBWCTest.class);
		testSuite.addTestSuite(Guest_ViewPrivatePagesSiteBWCTest.class);
		testSuite.addTestSuite(SignInPrivatePagesSiteBWCTest.class);
		testSuite.addTestSuite(ViewPrivatePagesSiteBWCTest.class);
		testSuite.addTestSuite(SignOutPrivatePagesSiteBWCTest.class);
		testSuite.addTestSuite(AddUserSoccerAdminTest.class);
		testSuite.addTestSuite(AddUserSoccerAdminPasswordTest.class);
		testSuite.addTestSuite(AddMembersSoccerAdminSiteBWCTest.class);
		testSuite.addTestSuite(AssignRoleSiteAdministratorSoccerAdminTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SA_SignInPublicPagesSiteBWCTest.class);
		testSuite.addTestSuite(SA_ViewPublicPagesSiteBWCTest.class);
		testSuite.addTestSuite(SA_SignOutPublicPagesSiteBWCTest.class);
		testSuite.addTestSuite(SA_SignInPrivatePagesSiteBWCTest.class);
		testSuite.addTestSuite(SA_ViewPrivatePagesSiteBWCTest.class);
		testSuite.addTestSuite(SA_SignOutPrivatePagesSiteBWCTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(ExportLARPublicPagesSiteBWCTest.class);
		testSuite.addTestSuite(ExportLARPrivatePagesSiteBWCTest.class);
		testSuite.addTestSuite(AddSiteLARImportSiteTest.class);
		testSuite.addTestSuite(ImportExportLARPublicPagesSiteBWCTest.class);
		testSuite.addTestSuite(ImportExportLARPrivatePagesSiteBWCTest.class);
		testSuite.addTestSuite(ViewImportExportLARPublicPagesSiteLARImportSiteTest.class);
		testSuite.addTestSuite(ViewImportExportLARPrivatePagesSiteLARImportSiteTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownSitesTest.class);

		return testSuite;
	}
}