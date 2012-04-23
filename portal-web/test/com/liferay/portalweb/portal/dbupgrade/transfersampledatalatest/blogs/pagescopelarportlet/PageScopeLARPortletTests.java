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

package com.liferay.portalweb.portal.dbupgrade.transfersampledatalatest.blogs.pagescopelarportlet;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.blogs.pagescope.AddCustomSiteBlogsPageScopeTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.blogs.pagescope.AddPage1BlogsPageScopeTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.blogs.pagescope.AddPage1PortletBlogsPageScopeTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.blogs.pagescope.AddPage2BlogsPageScopeTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.blogs.pagescope.AddPage2PortletBlogsPageScopeTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.blogs.pagescope.AddPage3BlogsPageScopeTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.blogs.pagescope.AddPage3PortletBlogsPageScopeTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.blogs.pagescope.ConfigurePortlet1BlogsScopeDefaultTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.blogs.pagescope.ConfigurePortlet2BlogsScopeLayoutCurrentPageTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.blogs.pagescope.ConfigurePortlet3BlogsScopeLayoutPage2Test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PageScopeLARPortletTests extends BaseTestSuite {
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
		testSuite.addTestSuite(ImportExportPortletLARDefaultBlogsPageScopeTest.class);
		testSuite.addTestSuite(ImportExportPortletLARPage2BlogsPageScopeTest.class);

		return testSuite;
	}
}