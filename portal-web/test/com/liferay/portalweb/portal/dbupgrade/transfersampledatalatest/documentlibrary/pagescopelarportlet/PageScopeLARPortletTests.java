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

package com.liferay.portalweb.portal.dbupgrade.transfersampledatalatest.documentlibrary.pagescopelarportlet;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.pagescope.AddCustomSiteDLPageScopeTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.pagescope.AddPage1DLTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.pagescope.AddPage1PortletDLTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.pagescope.AddPage2DLTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.pagescope.AddPage2PortletDLTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.pagescope.AddPage3DLTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.pagescope.AddPage3PortletDLTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.pagescope.ConfigurePage1PortletDLScopeDefaultTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.pagescope.ConfigurePage2PortletDLScopeLayoutCurrentPageTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.pagescope.ConfigurePage3PortletDLScopeLayoutPage2Test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PageScopeLARPortletTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddCustomSiteDLPageScopeTest.class);
		testSuite.addTestSuite(AddPage1DLTest.class);
		testSuite.addTestSuite(AddPage1PortletDLTest.class);
		testSuite.addTestSuite(AddPage2DLTest.class);
		testSuite.addTestSuite(AddPage2PortletDLTest.class);
		testSuite.addTestSuite(AddPage3DLTest.class);
		testSuite.addTestSuite(AddPage3PortletDLTest.class);
		testSuite.addTestSuite(ConfigurePage1PortletDLScopeDefaultTest.class);
		testSuite.addTestSuite(ConfigurePage2PortletDLScopeLayoutCurrentPageTest.class);
		testSuite.addTestSuite(ConfigurePage3PortletDLScopeLayoutPage2Test.class);
		testSuite.addTestSuite(ImportExportPortletLARDefaultDLPageScopeTest.class);
		testSuite.addTestSuite(ImportExportPortletLARPage2DLPageScopeTest.class);

		return testSuite;
	}
}