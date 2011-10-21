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

package com.liferay.portalweb.portal.dbupgrade.transfersampledatalatest.blogs.pagescopelarcp;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PageScopeLARCPTests extends BaseTests {

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
		testSuite.addTestSuite(
			ConfigurePortlet2BlogsScopeLayoutCurrentPageTest.class);
		testSuite.addTestSuite(
			ConfigurePortlet3BlogsScopeLayoutPage2Test.class);
		testSuite.addTestSuite(
			ConfigurePortlet2MaximumItemsToDisplay1Test.class);
		testSuite.addTestSuite(
			ImportExportCPLARDefaultBlogsPageScopeTest.class);
		testSuite.addTestSuite(ImportExportCPLARPage2BlogsPageScopeTest.class);

		return testSuite;
	}

}