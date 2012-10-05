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

package com.liferay.portalweb.asset.messageboards.mbthread;

import com.liferay.portalweb.asset.messageboards.mbthread.viewmbcategorythreadmessageviewcountap.ViewMBCategoryThreadMessageViewCountAPTests;
import com.liferay.portalweb.asset.messageboards.mbthread.viewportletabstractsmbcategorythreadmessageap.ViewPortletAbstractsMBCategoryThreadMessageAPTests;
import com.liferay.portalweb.asset.messageboards.mbthread.viewportletavailablembcategorythreadmessageap.ViewPortletAvailableMBCategoryThreadMessageAPTests;
import com.liferay.portalweb.asset.messageboards.mbthread.viewportletcurrentmbcategorythreadmessageap.ViewPortletCurrentMBCategoryThreadMessageAPTests;
import com.liferay.portalweb.asset.messageboards.mbthread.viewportletfullcontentmbcategorythreadmessageap.ViewPortletFullContentMBCategoryThreadMessageAPTests;
import com.liferay.portalweb.asset.messageboards.mbthread.viewportletmaximumitems5mbcategorymessage6ap.ViewPortletMaximumItems5MBCategoryMessage6APTests;
import com.liferay.portalweb.asset.messageboards.mbthread.viewportletpaginationregularmbcategorythread6ap.ViewPortletPaginationRegularMBCategoryThread6APTests;
import com.liferay.portalweb.asset.messageboards.mbthread.viewportletpaginationsimplembcategorythread6ap.ViewPortletPaginationSimpleMBCategoryThread6APTests;
import com.liferay.portalweb.asset.messageboards.mbthread.viewportlettablembcategorythreadmessageap.ViewPortletTableMBCategoryThreadMessageAPTests;
import com.liferay.portalweb.asset.messageboards.mbthread.viewportlettitlelistmbcategorythreadmessageap.ViewPortletTitleListMBCategoryThreadMessageAPTests;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MBThreadTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ViewMBCategoryThreadMessageViewCountAPTests.suite());
		testSuite.addTest(
			ViewPortletAbstractsMBCategoryThreadMessageAPTests.suite());
		testSuite.addTest(
			ViewPortletAvailableMBCategoryThreadMessageAPTests.suite());
		testSuite.addTest(
			ViewPortletCurrentMBCategoryThreadMessageAPTests.suite());
		testSuite.addTest(
			ViewPortletFullContentMBCategoryThreadMessageAPTests.suite());
		testSuite.addTest(
			ViewPortletMaximumItems5MBCategoryMessage6APTests.suite());
		testSuite.addTest(
			ViewPortletPaginationRegularMBCategoryThread6APTests.suite());
		testSuite.addTest(
			ViewPortletPaginationSimpleMBCategoryThread6APTests.suite());
		testSuite.addTest(
			ViewPortletTableMBCategoryThreadMessageAPTests.suite());
		testSuite.addTest(
			ViewPortletTitleListMBCategoryThreadMessageAPTests.suite());

		return testSuite;
	}

}