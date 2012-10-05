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

package com.liferay.portalweb.asset.webcontent.wcwebcontent.addwcwebcontent2displaypageap2;

import com.liferay.portalweb.asset.assetpublisher.portlet.addportletap.AddPageAP1Test;
import com.liferay.portalweb.asset.assetpublisher.portlet.addportletap.AddPageAP2Test;
import com.liferay.portalweb.asset.assetpublisher.portlet.addportletap.AddPortletAPPageAP1Test;
import com.liferay.portalweb.asset.assetpublisher.portlet.addportletap.AddPortletAPPageAP2Test;
import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontent.TearDownWCWebContentTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCWebContent2DisplayPageAP2Tests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageAP1Test.class);
		testSuite.addTestSuite(AddPageAP2Test.class);
		testSuite.addTestSuite(AddPortletAPPageAP1Test.class);
		testSuite.addTestSuite(AddPortletAPPageAP2Test.class);
		testSuite.addTestSuite(ConfigurePageAP1PortletAPDisplayPageTest.class);
		testSuite.addTestSuite(ConfigurePageAP1PortletAPSetAsDefaultTest.class);
		testSuite.addTestSuite(ConfigurePageAP2PortletAPSetAsDefaultTest.class);
		testSuite.addTestSuite(AddWCWebContent1DisplayPageAP1Test.class);
		testSuite.addTestSuite(AddWCWebContent2DisplayPageAP2Test.class);
		testSuite.addTestSuite(ViewWCWebContent1DisplayPageAP1Test.class);
		testSuite.addTestSuite(ViewWCWebContent2DisplayPageAP2Test.class);
		testSuite.addTestSuite(TearDownWCWebContentTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}