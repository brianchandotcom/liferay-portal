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

package com.liferay.portalweb.socialofficehome.sites.site;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.socialofficehome.sites.site.addsitesdefaultpagenone.AddSitesDefaultPageNoneTests;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessite.AddSitesSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessitetypeprivate.AddSitesSiteTypePrivateTests;
import com.liferay.portalweb.socialofficehome.sites.site.addsitessitetyperestricted.AddSitesSiteTypeRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.site.searchsitesdirectory.SearchSitesDirectoryTests;
import com.liferay.portalweb.socialofficehome.sites.site.searchsitessite.SearchSitesSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.searchsitessitetypeprivate.SearchSitesSiteTypePrivateTests;
import com.liferay.portalweb.socialofficehome.sites.site.searchsitessitetyperestricted.SearchSitesSiteTypeRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.site.sofrjoinsitessite.SOFr_JoinSitesSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.sofrsearchsitessite.SOFr_SearchSitesSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.sofrsearchsitessitetypeprivate.SOFr_SearchSitesSiteTypePrivateTests;
import com.liferay.portalweb.socialofficehome.sites.site.sofrsearchsitessitetyperestricted.SOFr_SearchSitesSiteTypeRestrictedTests;
import com.liferay.portalweb.socialofficehome.sites.site.viewsitesdirectory.ViewSitesDirectoryTests;
import com.liferay.portalweb.socialofficehome.sites.site.viewsitesdirectorymysites.ViewSitesDirectoryMySitesTests;
import com.liferay.portalweb.socialofficehome.sites.site.viewsitessite.ViewSitesSiteTests;
import com.liferay.portalweb.socialofficehome.sites.site.viewsitessitetypeprivate.ViewSitesSiteTypePrivateTests;
import com.liferay.portalweb.socialofficehome.sites.site.viewsitessitetyperestricted.ViewSitesSiteTypeRestrictedTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class SiteTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddSitesDefaultPageNoneTests.suite());
		testSuite.addTest(AddSitesSiteTests.suite());
		testSuite.addTest(AddSitesSiteTypePrivateTests.suite());
		testSuite.addTest(AddSitesSiteTypeRestrictedTests.suite());
		testSuite.addTest(SearchSitesDirectoryTests.suite());
		testSuite.addTest(SearchSitesSiteTests.suite());
		testSuite.addTest(SearchSitesSiteTypePrivateTests.suite());
		testSuite.addTest(SearchSitesSiteTypeRestrictedTests.suite());
		testSuite.addTest(SOFr_JoinSitesSiteTests.suite());
		testSuite.addTest(SOFr_SearchSitesSiteTests.suite());
		testSuite.addTest(SOFr_SearchSitesSiteTypePrivateTests.suite());
		testSuite.addTest(SOFr_SearchSitesSiteTypeRestrictedTests.suite());
		testSuite.addTest(ViewSitesDirectoryTests.suite());
		testSuite.addTest(ViewSitesDirectoryMySitesTests.suite());
		testSuite.addTest(ViewSitesSiteTests.suite());
		testSuite.addTest(ViewSitesSiteTypePrivateTests.suite());
		testSuite.addTest(ViewSitesSiteTypeRestrictedTests.suite());

		return testSuite;
	}

}