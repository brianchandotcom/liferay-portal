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

package com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletfilterdisplaypage;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontent.TearDownWCWebContentTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.assetpublisher.portlet.addportletap.AddPageAP2PortletAPTest;
import com.liferay.portalweb.portlet.assetpublisher.portlet.addportletap.AddPageAP2Test;
import com.liferay.portalweb.portlet.assetpublisher.portlet.addportletap.AddPageAPTest;
import com.liferay.portalweb.portlet.assetpublisher.portlet.addportletap.AddPortletAPTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletFilterDisplayPageTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageAPTest.class);
		testSuite.addTestSuite(AddPortletAPTest.class);
		testSuite.addTestSuite(ConfigurePortletFilterDisplayPageTest.class);
		testSuite.addTestSuite(AddPageAP2Test.class);
		testSuite.addTestSuite(AddPageAP2PortletAPTest.class);
		testSuite.addTestSuite(ConfigurePortletDefaultTest.class);
		testSuite.addTestSuite(AddPageAP2PortletWCDTest.class);
		testSuite.addTestSuite(AddPageAP2PortletWCDContentTest.class);
		testSuite.addTestSuite(ViewPageAP2PortletWCDContentTest.class);
		testSuite.addTestSuite(AddPageAP2PortletWCDContent2Test.class);
		testSuite.addTestSuite(ViewPageAP2PortletWCDContent2Test.class);
		testSuite.addTestSuite(TearDownWCWebContentTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}