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

package com.liferay.portalweb.portal.controlpanel.sites.siteportlet.addsitepublicpageschildpageportlets;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.AddSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.TearDownSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.sitepage.addsitepublicpages.AddSitePublicPage1CPTest;
import com.liferay.portalweb.portal.controlpanel.sites.sitepage.addsitepublicpages.AddSitePublicPage2CPTest;
import com.liferay.portalweb.portal.controlpanel.sites.sitepage.addsitepublicpages.AddSitePublicPage3CPTest;
import com.liferay.portalweb.portal.controlpanel.sites.sitepage.addsitepublicpageschildpage.AddSitePublicPage1ChildPageCPTest;
import com.liferay.portalweb.portal.controlpanel.sites.sitepage.addsitepublicpageschildpage.AddSitePublicPage2ChildPageCPTest;
import com.liferay.portalweb.portal.controlpanel.sites.sitepage.addsitepublicpageschildpage.AddSitePublicPage3ChildPageCPTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSitePublicPagesChildPagePortletsTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSiteTest.class);
		testSuite.addTestSuite(AddSitePublicPage1CPTest.class);
		testSuite.addTestSuite(AddSitePublicPage1ChildPageCPTest.class);
		testSuite.addTestSuite(AddSitePublicPage2CPTest.class);
		testSuite.addTestSuite(AddSitePublicPage2ChildPageCPTest.class);
		testSuite.addTestSuite(AddSitePublicPage3CPTest.class);
		testSuite.addTestSuite(AddSitePublicPage3ChildPageCPTest.class);
		testSuite.addTestSuite(AddPortletWCDPage1SiteTest.class);
		testSuite.addTestSuite(AddPortletLanguagePage1SiteTest.class);
		testSuite.addTestSuite(AddPortletAPPage1SiteTest.class);
		testSuite.addTestSuite(ViewPortletsPage1SiteTest.class);
		testSuite.addTestSuite(AddPortletRAPage1ChildPageSiteTest.class);
		testSuite.addTestSuite(ViewPortletsPage1ChildPageSiteTest.class);
		testSuite.addTestSuite(AddPortletWCDPage2SiteTest.class);
		testSuite.addTestSuite(AddPortletLanguagePage2SiteTest.class);
		testSuite.addTestSuite(AddPortletAPPage2SiteTest.class);
		testSuite.addTestSuite(ViewPortletsPage2SiteTest.class);
		testSuite.addTestSuite(AddPortletRAPage2ChildPageSiteTest.class);
		testSuite.addTestSuite(ViewPortletsPage2ChildPageSiteTest.class);
		testSuite.addTestSuite(AddPortletWCDPage3SiteTest.class);
		testSuite.addTestSuite(AddPortletLanguagePage3SiteTest.class);
		testSuite.addTestSuite(AddPortletAPPage3SiteTest.class);
		testSuite.addTestSuite(ViewPortletsPage3SiteTest.class);
		testSuite.addTestSuite(AddPortletRAPage3ChildPageSiteTest.class);
		testSuite.addTestSuite(ViewPortletsPage3ChildPageSiteTest.class);
		testSuite.addTestSuite(TearDownSiteTest.class);

		return testSuite;
	}
}