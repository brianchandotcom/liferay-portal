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

package com.liferay.portalweb.portlet.assetpublisher.portlet.addwcwebcontent2displaypageap2pageap2wcd;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontent.TearDownWCWebContentTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.assetpublisher.portlet.addportletap.AddPageAP1PortletAPTest;
import com.liferay.portalweb.portlet.assetpublisher.portlet.addportletap.AddPageAP1Test;
import com.liferay.portalweb.portlet.assetpublisher.portlet.addportletap.AddPageAP2PortletAPTest;
import com.liferay.portalweb.portlet.assetpublisher.portlet.addportletap.AddPageAP2Test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCWebContent2DisplayPageAP2PageAPp2WCDTests
	extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageAP1Test.class);
		testSuite.addTestSuite(AddPageAP2Test.class);
		testSuite.addTestSuite(AddPageAP1PortletAPTest.class);
		testSuite.addTestSuite(AddPageAP2PortletAPTest.class);
		testSuite.addTestSuite(AddPageAP2PortletWCDTest.class);
		testSuite.addTestSuite(ConfigurePageAP1PortletAPDisplayPageTest.class);
		testSuite.addTestSuite(ConfigurePageAP1PortletAPSetAsDefaultTest.class);
		testSuite.addTestSuite(ConfigurePageAP2PortletAPSetAsDefaultTest.class);
		testSuite.addTestSuite(AddWCWebContent1DisplayPageAP1PageAP2WCDTest.class);
		testSuite.addTestSuite(ViewWCWebContent1DisplayPageAP1PageAP2WCDTest.class);
		testSuite.addTestSuite(AddWCWebContent2DisplayPageAP2PageAP2WCDTest.class);
		testSuite.addTestSuite(ViewWCWebContent2DisplayPageAP2PageAp2WCDTest.class);
		testSuite.addTestSuite(TearDownWCWebContentTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}