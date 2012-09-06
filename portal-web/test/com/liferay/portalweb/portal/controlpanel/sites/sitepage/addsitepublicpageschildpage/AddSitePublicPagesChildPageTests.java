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

package com.liferay.portalweb.portal.controlpanel.sites.sitepage.addsitepublicpageschildpage;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.AddSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.TearDownSiteTest;
import com.liferay.portalweb.portal.controlpanel.sites.sitepage.addsitepublicpages.AddSitePublicPage1CPTest;
import com.liferay.portalweb.portal.controlpanel.sites.sitepage.addsitepublicpages.AddSitePublicPage2CPTest;
import com.liferay.portalweb.portal.controlpanel.sites.sitepage.addsitepublicpages.AddSitePublicPage3CPTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSitePublicPagesChildPageTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSiteTest.class);
		testSuite.addTestSuite(AddSitePublicPage1CPTest.class);
		testSuite.addTestSuite(AddSitePublicPage2CPTest.class);
		testSuite.addTestSuite(AddSitePublicPage3CPTest.class);
		testSuite.addTestSuite(AddSitePublicPage1ChildPageCPTest.class);
		testSuite.addTestSuite(AddSitePublicPage2ChildPageCPTest.class);
		testSuite.addTestSuite(AddSitePublicPage3ChildPageCPTest.class);
		testSuite.addTestSuite(TearDownSiteTest.class);

		return testSuite;
	}
}