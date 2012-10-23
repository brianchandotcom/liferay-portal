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

package com.liferay.portalweb.asset.assetpublisher.portlet;

import com.liferay.portalweb.asset.assetpublisher.portlet.addportletap.AddPortletAPTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.addportletapsite.AddPortletAPSiteTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.addportletmultipleap.AddPortletMultipleAPTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletapdisplaypage.ConfigurePortletAPDisplayPageTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletapenableratings.ConfigurePortletAPEnableRatingsTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletaporderbycolumnratings.ConfigurePortletAPOrderByColumnRatingsTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletapscopeblogspage.ConfigurePortletAPScopeBlogsPageTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletapsetasdefault.ConfigurePortletAPSetAsDefaultTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletassetselectiondynamic.ConfigurePortletAssetSelectionDynamicTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletassetselectionmanual.ConfigurePortletAssetSelectionManualTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletavailableblogsentry.ConfigurePortletAvailableBlogsEntryTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletavailablebookmarksentry.ConfigurePortletAvailableBookmarksEntryTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletavailabledmdocument.ConfigurePortletAvailableDMDocumentTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletavailablembmessage.ConfigurePortletAvailableMBMessageTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletavailablewebcontent.ConfigurePortletAvailableWebContentTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletavailablewikipage.ConfigurePortletAvailableWikiPageTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletcurrentblogsentry.ConfigurePortletCurrentBlogsEntryTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletcurrentbookmarksentry.ConfigurePortletCurrentBookmarksEntryTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletcurrentdmdocument.ConfigurePortletCurrentDMDocumentTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletcurrentmbmessage.ConfigurePortletCurrentMBMessageTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletcurrentwebcontent.ConfigurePortletCurrentWebContentTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletcurrentwikipage.ConfigurePortletCurrentWikiPageTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletdisplaystyleabstracts.ConfigurePortletDisplayStyleAbstractsTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletdisplaystylefullcontent.ConfigurePortletDisplayStyleFullContentTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletdisplaystyletable.ConfigurePortletDisplayStyleTableTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletdisplaystyletitlelist.ConfigurePortletDisplayStyleTitleListTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletmaximumitemstodisplay2.ConfigurePortletMaximumItemsToDisplay2Tests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletmaximumitemstodisplay5.ConfigurePortletMaximumItemsToDisplay5Tests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletpaginationtyperegular.ConfigurePortletPaginationTypeRegularTests;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletpaginationtypesimple.ConfigurePortletPaginationTypeSimpleTests;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPortletAPTests.suite());
		testSuite.addTest(AddPortletAPSiteTests.suite());
		testSuite.addTest(AddPortletMultipleAPTests.suite());
		testSuite.addTest(ConfigurePortletAPDisplayPageTests.suite());
		testSuite.addTest(ConfigurePortletAPEnableRatingsTests.suite());
		testSuite.addTest(ConfigurePortletAPOrderByColumnRatingsTests.suite());
		testSuite.addTest(ConfigurePortletAPScopeBlogsPageTests.suite());
		testSuite.addTest(ConfigurePortletAPSetAsDefaultTests.suite());
		testSuite.addTest(ConfigurePortletAssetSelectionDynamicTests.suite());
		testSuite.addTest(ConfigurePortletAssetSelectionManualTests.suite());
		testSuite.addTest(ConfigurePortletAvailableBlogsEntryTests.suite());
		testSuite.addTest(ConfigurePortletAvailableBookmarksEntryTests.suite());
		testSuite.addTest(ConfigurePortletAvailableDMDocumentTests.suite());
		testSuite.addTest(ConfigurePortletAvailableMBMessageTests.suite());
		testSuite.addTest(ConfigurePortletAvailableWebContentTests.suite());
		testSuite.addTest(ConfigurePortletAvailableWikiPageTests.suite());
		testSuite.addTest(ConfigurePortletCurrentBlogsEntryTests.suite());
		testSuite.addTest(ConfigurePortletCurrentBookmarksEntryTests.suite());
		testSuite.addTest(ConfigurePortletCurrentDMDocumentTests.suite());
		testSuite.addTest(ConfigurePortletCurrentMBMessageTests.suite());
		testSuite.addTest(ConfigurePortletCurrentWebContentTests.suite());
		testSuite.addTest(ConfigurePortletCurrentWikiPageTests.suite());
		testSuite.addTest(ConfigurePortletDisplayStyleAbstractsTests.suite());
		testSuite.addTest(ConfigurePortletDisplayStyleFullContentTests.suite());
		testSuite.addTest(ConfigurePortletDisplayStyleTableTests.suite());
		testSuite.addTest(ConfigurePortletDisplayStyleTitleListTests.suite());
		testSuite.addTest(ConfigurePortletDisplayStyleTitleListTests.suite());
		testSuite.addTest(ConfigurePortletMaximumItemsToDisplay2Tests.suite());
		testSuite.addTest(ConfigurePortletMaximumItemsToDisplay5Tests.suite());
		testSuite.addTest(ConfigurePortletPaginationTypeRegularTests.suite());
		testSuite.addTest(ConfigurePortletPaginationTypeSimpleTests.suite());

		return testSuite;
	}

}