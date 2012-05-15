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

package com.liferay.portalweb.portlet.webcontentdisplay.portlet;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.addportletwcd.AddPortletWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.addportletwcdsite.AddPortletWCDSiteTests;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletenablecommentratings.ConfigurePortletEnableCommentRatingsTests;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletenablecomments.ConfigurePortletEnableCommentsTests;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletenableratings.ConfigurePortletEnableRatingsTests;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletscopecurrentpage.ConfigurePortletScopeCurrentPageTests;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletscopedefault.ConfigurePortletScopeDefaultTests;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletscopepage2.ConfigurePortletScopePage2Tests;
import com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletshowavailablelocales.ConfigurePortletShowAvailableLocalesTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPortletWCDTests.suite());
		testSuite.addTest(AddPortletWCDSiteTests.suite());
		testSuite.addTest(ConfigurePortletEnableCommentRatingsTests.suite());
		testSuite.addTest(ConfigurePortletEnableCommentsTests.suite());
		testSuite.addTest(ConfigurePortletEnableRatingsTests.suite());
		testSuite.addTest(ConfigurePortletScopeCurrentPageTests.suite());
		testSuite.addTest(ConfigurePortletScopeDefaultTests.suite());
		testSuite.addTest(ConfigurePortletScopePage2Tests.suite());
		testSuite.addTest(ConfigurePortletShowAvailableLocalesTests.suite());

		return testSuite;
	}

}