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

package com.liferay.portalweb.portlet.assetpublisher.wcwebcontent;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.assetpublisher.wcwebcontent.addnewwcwebcontentapactions.AddNewWCWebContentAPActionsTests;
import com.liferay.portalweb.portlet.assetpublisher.wcwebcontent.deletewcwebcontentap.DeleteWCWebContentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.wcwebcontent.ratewcwebcontentap.RateWCWebContentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.wcwebcontent.selectexistingwcwebcontentapactions.SelectExistingWCWebContentAPActionsTests;
import com.liferay.portalweb.portlet.assetpublisher.wcwebcontent.viewconfigureportletabstractswebcontentap.ViewConfigurePortletAbstractsWebContentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.wcwebcontent.viewconfigureportletavailablewebcontentap.ViewConfigurePortletAvailableWebContentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.wcwebcontent.viewconfigureportletcurrentwebcontentap.ViewConfigurePortletCurrentWebContentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.wcwebcontent.viewconfigureportletfullcontentwebcontentap.ViewConfigurePortletFullContentWebContentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.wcwebcontent.viewconfigureportlettablewebcontentap.ViewConfigurePortletTableWebContentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.wcwebcontent.viewconfigureportlettitlelistwebcontentap.ViewConfigurePortletTitleListWebContentAPTests;
import com.liferay.portalweb.portlet.assetpublisher.wcwebcontent.viewwcwebcontentviewcountap.ViewWCWebContentViewCountAPTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WCWebContentTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddNewWCWebContentAPActionsTests.suite());
		testSuite.addTest(DeleteWCWebContentAPTests.suite());
		testSuite.addTest(RateWCWebContentAPTests.suite());
		testSuite.addTest(SelectExistingWCWebContentAPActionsTests.suite());
		testSuite.addTest(
			ViewConfigurePortletAbstractsWebContentAPTests.suite());
		testSuite.addTest(
			ViewConfigurePortletAvailableWebContentAPTests.suite());
		testSuite.addTest(ViewConfigurePortletCurrentWebContentAPTests.suite());
		testSuite.addTest(
			ViewConfigurePortletFullContentWebContentAPTests.suite());
		testSuite.addTest(ViewConfigurePortletTableWebContentAPTests.suite());
		testSuite.addTest(
			ViewConfigurePortletTitleListWebContentAPTests.suite());
		testSuite.addTest(ViewWCWebContentViewCountAPTests.suite());

		return testSuite;
	}

}