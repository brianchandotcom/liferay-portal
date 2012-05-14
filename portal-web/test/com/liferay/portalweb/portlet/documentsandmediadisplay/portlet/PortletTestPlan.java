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

package com.liferay.portalweb.portlet.documentsandmediadisplay.portlet;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.addportletdmd.AddPortletDMDTests;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.addportletsdmd.AddPortletsDMDTests;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshowactions.ConfigurePortletShowActionsTests;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshowfoldermenu.ConfigurePortletShowFolderMenuTests;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshownavigationlinks.ConfigurePortletShowNavigationLinksTests;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.configureportletdmdshowsearch.ConfigurePortletShowSearchTests;
import com.liferay.portalweb.portlet.documentsandmediadisplay.portlet.removeportlet.RemovePortletDMDTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPortletDMDTests.suite());
		testSuite.addTest(AddPortletsDMDTests.suite());
		testSuite.addTest(ConfigurePortletShowActionsTests.suite());
		testSuite.addTest(ConfigurePortletShowFolderMenuTests.suite());
		testSuite.addTest(ConfigurePortletShowNavigationLinksTests.suite());
		testSuite.addTest(ConfigurePortletShowSearchTests.suite());
		testSuite.addTest(RemovePortletDMDTests.suite());

		return testSuite;
	}

}