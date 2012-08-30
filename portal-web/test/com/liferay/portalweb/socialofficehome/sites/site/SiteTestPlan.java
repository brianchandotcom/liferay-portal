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

package com.liferay.portalweb.socialofficehome.sites.site;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.socialofficehome.sites.site.addsitesdefaultpagenone.AddSitesDefaultPageNoneTests;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessite.AddSitesSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessitetypeprivate.AddSitesSiteTypePrivateTests;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessitetypeprivaterestricted.AddSitesSiteTypePrivateRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessitetypepublicrestricted.AddSitesSiteTypePublicRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.site.searchsitesdirectory.SearchSitesDirectoryTests;
import com.liferay.portalweb.socialofficehome.sites.site.searchsitessite.SearchSitesSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.searchsitessitetypeprivate.SearchSitesSiteTypePrivateTests;
import com.liferay.portalweb.socialofficehome.sites.site.searchsitessitetypeprivaterestricted.SearchSitesSiteTypePrivateRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.site.searchsitessitetypepublicrestricted.SearchSitesSiteTypePublicRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.site.sousjoinsitessite.SOUs_JoinSitesSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.soussearchsitessite.SOUs_SearchSitesSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.soussearchsitessitetypeprivate.SOUs_SearchSitesSiteTypePrivateTests;
import com.liferay.portalweb.socialofficehome.sites.site.soussearchsitessitetypeprivaterestricted.SOUs_SearchSitesSiteTypePrivateRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.site.soussearchsitessitetypepublicrestricted.SOUs_SearchSitesSiteTypePublicRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.site.sousviewsiteslinksauserprofile.SOUs_ViewSitesLinkSAUserProfileTests;
import com.liferay.portalweb.socialofficehome.sites.site.viewsitesdirectory.ViewSitesDirectoryTests;
import com.liferay.portalweb.socialofficehome.sites.site.viewsitesdirectorymysites.ViewSitesDirectoryMySitesTests;
import com.liferay.portalweb.socialofficehome.sites.site.viewsitessite.ViewSitesSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.viewsitessitetypeprivate.ViewSitesSiteTypePrivateTests;
import com.liferay.portalweb.socialofficehome.sites.site.viewsitessitetypeprivaterestricted.ViewSitesSiteTypePrivateRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.site.viewsitessitetypepublicrestricted.ViewSitesSiteTypePublicRestrictedTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class SiteTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddSitesDefaultPageNoneTests.suite());
		testSuite.addTest(AddSitesSiteTests.suite());
		testSuite.addTest(AddSitesSiteTypePrivateTests.suite());
		testSuite.addTest(AddSitesSiteTypePrivateRestrictedTests.suite());
		testSuite.addTest(AddSitesSiteTypePublicRestrictedTests.suite());
		testSuite.addTest(SearchSitesDirectoryTests.suite());
		testSuite.addTest(SearchSitesSiteTests.suite());
		testSuite.addTest(SearchSitesSiteTypePrivateTests.suite());
		testSuite.addTest(SearchSitesSiteTypePrivateRestrictedTests.suite());
		testSuite.addTest(SearchSitesSiteTypePublicRestrictedTests.suite());
		testSuite.addTest(SOUs_JoinSitesSiteTests.suite());
		testSuite.addTest(SOUs_SearchSitesSiteTests.suite());
		testSuite.addTest(SOUs_SearchSitesSiteTypePrivateTests.suite());
		testSuite.addTest(
			SOUs_SearchSitesSiteTypePrivateRestrictedTests.suite());
		testSuite.addTest(
			SOUs_SearchSitesSiteTypePublicRestrictedTests.suite());
		testSuite.addTest(SOUs_ViewSitesLinkSAUserProfileTests.suite());
		testSuite.addTest(ViewSitesDirectoryTests.suite());
		testSuite.addTest(ViewSitesDirectoryMySitesTests.suite());
		testSuite.addTest(ViewSitesSiteTests.suite());
		testSuite.addTest(ViewSitesSiteTypePrivateTests.suite());
		testSuite.addTest(ViewSitesSiteTypePrivateRestrictedTests.suite());
		testSuite.addTest(ViewSitesSiteTypePublicRestrictedTests.suite());

		return testSuite;
	}

}