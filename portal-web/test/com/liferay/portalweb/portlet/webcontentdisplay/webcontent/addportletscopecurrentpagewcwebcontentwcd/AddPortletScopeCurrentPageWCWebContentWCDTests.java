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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.addportletscopecurrentpagewcwebcontentwcd;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPortletScopeCurrentPageWCWebContentWCDTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddSiteTest.class);
		testSuite.addTestSuite(AddSitePublicPageTest.class);
		testSuite.addTestSuite(AddPageWCD1Test.class);
		testSuite.addTestSuite(AddPortletWCD1Test.class);
		testSuite.addTestSuite(AddPageWCD2Test.class);
		testSuite.addTestSuite(AddPortletWCD2Test.class);
		testSuite.addTestSuite(AddPageWCD3Test.class);
		testSuite.addTestSuite(AddPortletWCD3Test.class);
		testSuite.addTestSuite(ConfigurePortletScopeDefaultTest.class);
		testSuite.addTestSuite(ConfigurePortletScopeCurrentPageTest.class);
		testSuite.addTestSuite(ConfigurePortletScopePage2Test.class);
		testSuite.addTestSuite(
			AddPortletScopeCurrentPageWCWebContentWCDTest.class);
		testSuite.addTestSuite(
			ViewScopeCurrentPageWCWebContentNullDefaultTest.class);
		testSuite.addTestSuite(ViewScopeCurrentPageWCWebContentTest.class);
		testSuite.addTestSuite(
			ViewScopeCurrentPageWCWebContentNullPage2Test.class);
		testSuite.addTestSuite(
			ViewScopeCurrentPageWebContentListDefaultTest.class);
		testSuite.addTestSuite(
			ViewScopeCurrentPageWebContentListCurrentPageTest.class);
		testSuite.addTestSuite(
			ViewScopeCurrentPageWebContentListPage2Test.class);
		testSuite.addTestSuite(
			ViewScopeCurrentPageWCWebContentListDefaultCPTest.class);
		testSuite.addTestSuite(
			ViewScopeCurrentPageWCWebContentListCurrentPageCPTest.class);
		testSuite.addTestSuite(TearDownPortletScopeTest.class);
		testSuite.addTestSuite(TearDownScopeWebContentTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);
		testSuite.addTestSuite(TearDownSiteTest.class);

		return testSuite;
	}

}